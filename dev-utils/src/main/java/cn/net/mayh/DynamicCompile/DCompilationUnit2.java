package cn.net.mayh.DynamicCompile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DCompilationUnit2 {
    private static Logger logger = LoggerFactory.getLogger(DCompilationUnit2.class);

    public static Runnable getInstance(String source, String fullQualifiedName, ApplicationContext applicationContext) throws Exception {
        Class<? extends Runnable> clazz = getClass(source, fullQualifiedName);
        Constructor<? extends Runnable> constructor = clazz.getConstructor();
        Runnable runnable = constructor.newInstance();
        Field applicationContextField = clazz.getField("applicationContext");
        if(applicationContextField != null){
            applicationContextField.setAccessible(true);
            applicationContextField.set(runnable,applicationContext);
        }
        return runnable;
    }

    public static Class<? extends Runnable> getClass(String source,String fullQualifiedName) {
        CustomJavaCompiler2 compiler = new CustomJavaCompiler2(source,fullQualifiedName);
        boolean res = compiler.compiler();
        if (res) {
            System.out.println("compilerSuccess:" + compiler.getCompilerMessage());
            System.out.println("compilerTime:" + compiler.getCompilerTime());
            try {
                return (Class<? extends Runnable>) compiler.getCompilerClass(DCompilationUnit2.class.getClassLoader());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("compilerFailed:" + compiler.getCompilerMessage());
        }
        return null;
    }
    public static void main(String[] args) {
        String str = "package org.neris.pfrs.api;\n" +
                "\n" +
                "\n" +
                "import org.neris.pfrs.maopao.services.util.RunnableMap;\n" +
                "\n" +
                "public class DynamicClassTest extends RunnableMap {\n" +
                "    @Override\n" +
                "    public void run() {\n" +
                "        Object dynamicSqlSessionTemplate = applicationContext.getBean(\"dynamicSqlSessionTemplate\");\n" +
                "        System.out.println(\"获取bean对象\"+dynamicSqlSessionTemplate);\n" +
                "    }\n" +
                "}";
        try {
            getInstance(str, "org.neris.pfrs.api.DynamicClassTest",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class CustomJavaCompiler2 {
    //源码
    private String sourceCode;
    //类全名
    private String fullClassName;
    //获取java的编译器
    private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    //存放编译之后的字节码(key:类全名，value:编译之后输出的字节码)
    private Map<String, ByteJavaFileObject> javaFileObjectMap = new ConcurrentHashMap<>();
    //存放编译过程中输出的信息
    private DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
    //编译耗时(单位ms)
    private long compilerTime;

    public CustomJavaCompiler2(String sourceCode,String fullQualifiedName) {
        this.sourceCode = sourceCode;
        this.fullClassName = fullQualifiedName;
    }

    /**
     * 编译字符串源代码,编译失败在 diagnosticsCollector 中获取提示信息
     *
     * @return true:编译成功 false:编译失败
     */
    public boolean compiler() {
        if(compiler == null) {
            return false;
        }

        long startTime = System.currentTimeMillis();
        //标准的内容管理器,更换成自己的实现，覆盖部分方法
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        JavaFileManager javaFileManager = new StringJavaFileManage(standardFileManager);
        //构造源代码对象
        JavaFileObject javaFileObject = new StringJavaFileObject(fullClassName, sourceCode);
        List<String> optionList = new ArrayList<String>();
        // set compiler's classpath to be same as the runtime's
        String property = System.getProperty("java.class.path");
        String classPath = getClassPath();
        optionList.addAll(Arrays.asList("-classpath", "E:\\code\\prfs\\pfrs-new\\pfrs\\target\\pfrs\\WEB-INF\\lib\\*,E:\\code\\prfs\\pfrs-new\\pfrs\\target\\pfrs\\WEB-INF\\classes"));
        //获取一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, diagnosticsCollector, optionList, null, Arrays.asList(javaFileObject));
        //设置编译耗时
        compilerTime = System.currentTimeMillis() - startTime;
        return task.call();
    }
    public static String getClassPath(){
        String systemName = System.getProperty("os.name");

        //判断当前环境，如果是Windows 要截取路径的第一个 '/'
        return DCompilationUnit2.class.getResource("/").getFile().toString().substring(1);
    }
    /**
     * 获取编译后的Class
     * @return
     */
    public Class<?> getCompilerClass(ClassLoader classLoader) {
        StringClassLoader scl = new StringClassLoader(classLoader);
        Class<?> clz = null;
        try {
            clz = scl.findClass(fullClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clz;
    }

    /**
     * 获取编译时产生的信息
     * @return 编译信息(错误 警告)
     */
    public String getCompilerMessage() {
        if(compiler == null) {
            return "JRE环境未配置(请复制JDK路径下lib目录内的tools.jar到JRE路径下lib目录里)";
        }

        StringBuilder sb = new StringBuilder();
        List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
        for (Diagnostic diagnostic : diagnostics) {
            sb.append(diagnostic.toString()).append("\r\n");
        }
        return sb.toString();
    }

    public long getCompilerTime() {
        return compilerTime;
    }

    /**
     * 获取类的全名称
     * @param sourceCode 源码
     * @return 类的全名称
     */
    public static String getFullClassName(String sourceCode) {
        String className = "";
        Pattern pattern = Pattern.compile("package\\s+\\S+\\s*;");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className = matcher.group().replaceFirst("package", "").replace(";", "").trim() + ".";
        }

        pattern = Pattern.compile("class\\s+\\S+\\s+\\{");
        matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className += matcher.group().replaceFirst("class", "").replace("{", "").trim();
        }
        return className;
    }

    /**
     * 自定义一个字符串的源码对象
     */
    private class StringJavaFileObject extends SimpleJavaFileObject {
        //等待编译的源码字段
        private String contents;

        //java源代码 => StringJavaFileObject对象 的时候使用
        public StringJavaFileObject(String className, String contents) {
            super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }

        //字符串源码会调用该方法
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return contents;
        }

    }

    /**
     * 自定义一个编译之后的字节码对象
     */
    private class ByteJavaFileObject extends SimpleJavaFileObject {
        //存放编译后的字节码
        private ByteArrayOutputStream outPutStream;

        public ByteJavaFileObject(String className, Kind kind) {
            super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), kind);
        }

        //StringJavaFileManage 编译之后的字节码输出会调用该方法(把字节码输出到outputStream)
        @Override
        public OutputStream openOutputStream() {
            outPutStream = new ByteArrayOutputStream();
            return outPutStream;
        }

        //在类加载器加载的时候需要用到
        public byte[] getCompiledBytes() {
            return outPutStream.toByteArray();
        }
    }

    /**
     * 自定义一个JavaFileManage来控制编译之后字节码的输出位置
     */
    private class StringJavaFileManage extends ForwardingJavaFileManager {
        StringJavaFileManage(JavaFileManager fileManager) {
            super(fileManager);
        }

        //获取输出的文件对象，它表示给定位置处指定类型的指定类
        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            ByteJavaFileObject javaFileObject = new ByteJavaFileObject(className, kind);
            javaFileObjectMap.put(className, javaFileObject);
            return javaFileObject;
        }
    }

    /**
     * 自定义类加载器, 用来加载动态的字节码
     */
    private class StringClassLoader extends ClassLoader {
        public StringClassLoader(){}
        public StringClassLoader(ClassLoader classLoader){
            super(classLoader);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            ByteJavaFileObject fileObject = javaFileObjectMap.get(name);
            if (fileObject != null) {
                byte[] bytes = fileObject.getCompiledBytes();
                return defineClass(name, bytes, 0, bytes.length);
            }
            try {
                return ClassLoader.getSystemClassLoader().loadClass(name);
            } catch (Exception e) {
                return super.findClass(name);
            }
        }

    }

    public static <T> T invokeMethod(Object object, String methodName, Class<?>[] classes, Object... args)
            throws Exception {
        Method method = object.getClass().getMethod(methodName, classes);
        return (T) method.invoke(object, args);
    }



}