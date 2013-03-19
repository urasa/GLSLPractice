package glslpractice.launcher;

import java.lang.reflect.Method;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 指定されたパスからmainを持ったクラスの一覧を取得する
 */
public class Launcher {
    public static void main(String[] args) {
        List<String> classNames = null;
        try {
            classNames = ClassIndexer.getIndex("glslpractice");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (classNames != null && classNames.isEmpty()) {
            System.out.println("No classes found.");
            return;
        }
        List<Class<?>> classes = getMainClasses(classNames);
        for (Class c : classes) {
            System.out.println(c.getName() + " has main()");
        }
    }
    private static List<Class<?>> getMainClasses(List<String> classNames) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> c = null;
        for (String className : classNames) {
            try {
                c = Class.forName(className);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (c != null && isMainClass(c)) {
                classes.add(c);
            }
        }
        return classes;
    }
    /**
     * mainを持ったクラスかを調べる
     * TODO mainがオーバーロードされている場合に誤検出について検証・対処
     */
    private static boolean isMainClass(Class<?> c) {
        for (Method method : c.getMethods()) {
            if (method.getName() == "main") {
                return true;
            }
        }
        return false;
    }
}
