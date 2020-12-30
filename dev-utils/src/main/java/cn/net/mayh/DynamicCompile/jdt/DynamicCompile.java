package cn.net.mayh.DynamicCompile.jdt;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import com.dynamic.compile.DCompile;
 public class DynamicCompile {

    private static String java_file_path = "D:/MyEclipse/DynamicCompile/com/compile/Test.java";

    private static String java_basic_path = "D:/MyEclipse/DynamicCompile";

    private static String ecode = "utf8";

    public void execute() throws Exception{
        DCompile dc = new DCompile();
        dc.initialize(java_file_path, java_basic_path, ecode);
        Class clazz = dc.compile();
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("getName", new Class[]{String.class});
        String name = (String)method.invoke(obj, "dbdxj");
        System.out.println(name);
    }

    public static void main(String[] args) {
        Main m = new Main();
        try{
            m.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}