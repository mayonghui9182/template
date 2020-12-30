package cn.net.mayh.DynamicCompile;


import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Stack;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * @ClassName: CompilerTest
 * @Description: JAVA源文件动态编译
 * @date: 2018年7月16日 上午9:45:32
 *
 */

@Component
public class TestCompiler {
    public static void main(String[] args) throws Exception {
        String classPath = "G:/deleted/apktool/dynamicClass" ;
        System.out.println( calculate("1.0+1.0",classPath));
    }

    /**
     * @Title: testHello
     * @Description: 测试动态编译java文件
     * @param: @param classPath
     * @return: void
     * @throws
     */
    public static void testHello(String classPath){
        String className = "Main" ;
        String source = "public class "+className+" { public static void main(String[] args) {System.out.println(\"Hello World!\");} }";
        boolean isSuccess = complie(source,className,classPath) ;
        System.out.println( "Complie successfully："+isSuccess );
    }


    /**
     * @Title: calculate
     * @Description: 测试动态编译并且加载类并执行对应方法
     * @param: @param expr
     * @param: @param classPath
     * @param: @return
     * @param: @throws Exception
     * @return: double
     * @throws
     */
    private static double calculate(String expr,String classPath) throws  Exception {
        String className = "Main";
        String methodName = "calculate";
        String source = "public class " + className + " { public static double " + methodName + "() { return " + expr
                + "; } }";
        // 省略动态编译Java源代码的相关代码，参见上一节
        boolean result = complie(source,className, classPath);
        if (result) {
            loadClass( new File(classPath) );
            ClassLoader loader = CompilerTest.class.getClassLoader();
            try {
                Class<?> clazz = loader.loadClass(className);
                Method method = clazz.getMethod(methodName, new Class<?>[] {});
                Object value = method.invoke(null, new Object[] {});
                return (Double) value;
            } catch (Exception e) {
                throw new  Exception("内部错误。",e);
            }
        } else {
            throw new  Exception("错误的表达式。");
        }
    }

    /**
     * @Title: complie
     * @Description: 动态编译java类成成class文件放入指定目录
     * @param: @param expr
     * @param: @param classPath
     * @param: @return
     * @param: @throws Exception
     * @throws
     */
    public static boolean complie(String source,String className,String classPath){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
         try {
            Iterable<String> options = Arrays.asList("-d", classPath);
            StringSourceJavaObject sourceObject = new StringSourceJavaObject(className, source);
            Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
            CompilationTask task = compiler.getTask(null, fileManager, null, options, null, fileObjects);
            boolean result = task.call();
            return result ;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false ;
    }

    private static class StringSourceJavaObject extends SimpleJavaFileObject {

        private String content = null;

        public StringSourceJavaObject(String name, String content) throws URISyntaxException {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return content;
        }
    }

    /**
     * @Title: loadClass
     * @Description: 动态加载class文件
     * @param: @param clazzPath
     * @param: @throws Exception
     * @return: void
     * @throws
     */
    public static void loadClass(File clazzPath) throws Exception{
        // 设置class文件所在根路径
        // 例如/usr/java/classes下有一个test.App类，则/usr/java/classes即这个类的根路径，而.class文件的实际位置是/usr/java/classes/test/App.class
//		File clazzPath = new File(class文件所在根路径);

        // 记录加载.class文件的数量
        int clazzCount = 0;
        //only handle the folder
        if( clazzPath.isFile() ){
            clazzPath = clazzPath.getParentFile() ;
        }

        if (clazzPath.exists() && clazzPath.isDirectory()) {
            // 获取路径长度
            int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;

            Stack<File> stack = new Stack<>();
            stack.push(clazzPath);

            // 遍历类路径
            while (stack.isEmpty() == false) {
                File path = stack.pop();
                File[] classFiles = path.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.isDirectory() || pathname.getName().endsWith(".class");
                    }
                });
                for (File subFile : classFiles) {
                    if (subFile.isDirectory()) {
                        stack.push(subFile);
                    } else {
                        if (clazzCount++ == 0) {
                            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                            boolean accessible = method.isAccessible();
                            try {
                                if (accessible == false) {
                                    method.setAccessible(true);
                                }
                                // 设置类加载器
                                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                                // 将当前类路径加入到类加载器中
                                method.invoke(classLoader, clazzPath.toURI().toURL());
                            } finally {
                                method.setAccessible(accessible);
                            }
                        }
                        // 文件名称
                        String className = subFile.getAbsolutePath();
                        className = className.substring(clazzPathLen, className.length() - 6);
                        className = className.replace(File.separatorChar, '.');
                        // 加载Class类
                        Class.forName(className);
                        System.out.println( String.format( "读取应用程序类文件[class=%s]", className) );
                    }
                }
            }
        }
    }
}
