package cn.net.mayh.DynamicCompile;

import javax.tools.*;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class CompilerTest {
    public static void main(String[] args) throws Exception {
        String source = "package com.ma.meta;\n" +
                "\n" +
                "public class DynamicClassTest implements Runnable{\n" +
                "    @Override\n" +
                "    public void run() {\n" +
                "        System.out.println(\"123\");\n" +
                "    }\n" +
                "}";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        StringSourceJavaObject sourceObject = new StringSourceJavaObject("DynamicClassTest", source);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
        Writer charArrayWriter = new CharArrayWriter();
        JavaCompiler.CompilationTask task = compiler.getTask(charArrayWriter, fileManager, null, null, null, fileObjects);
        boolean result = task.call();




        if (result) {
            System.out.println("编译成功。");
        }else{
            System.out.println(charArrayWriter.toString());
        }
        CompilerTest.class.getClassLoader().loadClass("cn.net.mayh.DynamicClassTest");
    }

    static class StringSourceJavaObject extends SimpleJavaFileObject {

        private String content = null;
        public StringSourceJavaObject(String name, String content) throws URISyntaxException {
            super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return content;
        }
    }
}
