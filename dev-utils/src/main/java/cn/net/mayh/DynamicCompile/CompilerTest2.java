package cn.net.mayh.DynamicCompile;//package com.ma.meta.DynamicCompile;
//
//import org.eclipse.jdt.internal.compiler.CompilationResult;
//
//import javax.annotation.processing.Processor;
//import javax.tools.*;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.Arrays;
//import java.util.List;
//
//public class CompilerTest2 {
//    public static CompilationResult compile(String qualifiedName, String sourceCode,
//                                            Iterable<? extends Processor> processors) {
//        JavaStringSource source = new JavaStringSource(qualifiedName, sourceCode);
//        List<JavaStringSource> ss = Arrays.asList(source);
//        List<String> options = Arrays.asList("-classpath", HotCompileConstants.CLASSPATH);
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//        MemClsFileManager fileManager = null;
//        Map<String, JavaMemCls> clses = new HashMap<String, JavaMemCls>();
//        Map<String, JavaStringSource> srcs = new HashMap<String, JavaStringSource>();
//        srcs.put(source.getClsName(), source);
//        try {
//            fileManager = new MemClsFileManager(compiler.getStandardFileManager(null, null, null), clses, srcs);
//            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//            StringWriter out = new StringWriter();
//            CompilationTask task = compiler.getTask(out, fileManager, diagnostics, options, null, ss);
//            if (processors != null) task.setProcessors(processors);
//            boolean sucess = task.call();
//            if (!sucess) {
//                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
//                    out.append("Error on line " + diagnostic.getLineNumber() + " in " + diagnostic).append('\n');
//                }
//                return new CompilationResult(out.toString());
//            }
//        } finally {
//            try {
//                fileManager.close();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        // every parser class should be loaded by a new specific class loader
//        HotCompileClassLoader loader = new HotCompileClassLoader(Util.getParentClsLoader(), clses);
//        Class<?> cls = null;
//        try {
//            cls = loader.loadClass(qualifiedName);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return new CompilationResult(cls, loader);
//    }
//    static class JavaStringSource extends SimpleJavaFileObject {
//
//        private String content = null;
//        public JavaStringSource(String name, String content) {
//            super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension), Kind.SOURCE);
//            this.content = content;
//        }
//
//        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
//            return content;
//        }
//    }
//}
//class MemClsFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
//
//    private Map<String, JavaMemCls>       destFiles;
//    private Map<String, CompilerTest2.JavaStringSource> srcFiles;
//
//    protected MemClsFileManager(StandardJavaFileManager fileManager, Map<String, JavaMemCls> destFiles,
//                                Map<String, JavaStringSource> srcFiles){
//        super(fileManager);
//        this.destFiles = destFiles;
//        this.srcFiles = srcFiles;
//    }
//
//    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling)
//            throws IOException {
//        if (!(JavaFileObject.Kind.CLASS.equals(kind) && StandardLocation.CLASS_OUTPUT.equals(location))) return super.getJavaFileForOutput(location,
//                className,
//                kind,
//                sibling);
//        if (destFiles.containsKey(className)) {
//            return destFiles.get(className);
//        } else {
//            JavaMemCls file = new JavaMemCls(className);
//            this.destFiles.put(className, file);
//            return file;
//        }
//    }
//
//    public void close() throws IOException {
//        super.close();
//        this.destFiles = null;
//    }
//
//    public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
//            throws IOException {
//        List<JavaFileObject> ret = new ArrayList<JavaFileObject>();
//        if ((StandardLocation.CLASS_OUTPUT.equals(location) || StandardLocation.CLASS_PATH.equals(location))
//                && kinds.contains(Kind.CLASS)) {
//            for (Map.Entry<String, JavaMemCls> e : destFiles.entrySet()) {
//                String pkgName = resolvePkgName(e.getKey());
//                if (recurse) {
//                    if (pkgName.contains(packageName)) ret.add(e.getValue());
//                } else {
//                    if (pkgName.equals(packageName)) ret.add(e.getValue());
//                }
//            }
//        } else if (StandardLocation.SOURCE_PATH.equals(location) && kinds.contains(Kind.SOURCE)) {
//            for (Map.Entry<String, JavaStringSource> e : srcFiles.entrySet()) {
//                String pkgName = resolvePkgName(e.getKey());
//                if (recurse) {
//                    if (pkgName.contains(packageName)) ret.add(e.getValue());
//                } else {
//                    if (pkgName.equals(packageName)) ret.add(e.getValue());
//                }
//            }
//        }
//        // 也包含super.list
//        Iterable<JavaFileObject> superList = super.list(location, packageName, kinds, recurse);
//        if (superList != null) for (JavaFileObject f : superList)
//            ret.add(f);
//        return ret;
//    }
//
//    private String resolvePkgName(String fullQualifiedClsName) {
//        return fullQualifiedClsName.substring(0, fullQualifiedClsName.lastIndexOf('.'));
//    }
//
//    public String inferBinaryName(Location location, JavaFileObject file) {
//        if (file instanceof JavaMemCls) {
//            return ((JavaMemCls) file).getClsName();
//        } else if (file instanceof JavaStringSource) {
//            return ((JavaStringSource) file).getClsName();
//        } else {
//            return super.inferBinaryName(location, file);
//        }
//    }
//
//}