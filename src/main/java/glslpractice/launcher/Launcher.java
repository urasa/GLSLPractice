package glslpractice.launcher;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            System.exit(1);
        }
        List<Class<?>> classes = getMainClasses(classNames);
        System.out.println("select to launch: ");
        for (int i = 0; i < classes.size(); i++) {
            System.out.println(String.format("%3d: ", i)
                    + classes.get(i).getName());
        }
        System.out.print(": ");
        try (Scanner scanner = new Scanner(System.in)) {
            int num = scanner.nextInt();
            if (0 <= num && num < classes.size()) {
                instantiate(classes.get(num));
            }
            else {
                System.out.println("exit");
                System.exit(0);
            }
        }
    }

    private static List<Class<?>> getMainClasses(
            List<String> classNames) {
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
     * mainを持ったクラスかを調べる TODO mainがオーバーロードされている場合に誤検出について検証・対処
     */
    private static boolean isMainClass(Class<?> c) {
        for (Method method : c.getMethods()) {
            if (method.getName() == "main") {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定されたクラスをインスタンス化する
     */
    private static void instantiate(Class<?> className) {
        try {
            className.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
