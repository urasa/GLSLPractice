package glslpractice.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 * 指定されたパッケージからクラスの一覧を作成する
 */
public class ClassIndexer {
    public static List<String> getIndex(String packageName) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fm = compiler.getStandardFileManager(null, null, null);
        Set<JavaFileObject.Kind> kinds = EnumSet.of(JavaFileObject.Kind.CLASS);
        for (JavaFileObject f : fm.list(StandardLocation.CLASS_PATH, packageName, kinds, false)) {
            System.out.println(f.getName());
        }

        // パッケージはリソース中のディレクトリ構造を表しているので
        // クラスファイルはそのディレクトリからリソースとして取得できる
        String classdir = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(classdir);
        List<File> dirs = new ArrayList<>();
        for (URL url : Collections.list(resources)) {
            dirs.add(new File(URLDecoder.decode(url.getFile(), "UTF-8")));
        }
        ArrayList<String> classes = new ArrayList<>();
        for (File dir : dirs) {
            classes.addAll(getClassFiles(dir, packageName));
        }
        return classes;
    }

    /**
     * 指定されたディレクトリからクラスファイルのパスの一覧を取得する
     */
    private static List<String> getClassFiles(File dir, String packageName) {
        List<String> classes = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            String filename = file.getName();
            if (file.isDirectory()) {
                classes.addAll(getClassFiles(file, packageName + "." + filename));
            }
            else if (file.isFile()) {
                if (filename.endsWith(".class") && !filename.contains("$")) {
                    classes.add(packageName + "." + filename.replaceAll("\\.class$", ""));
                }
            }
        }
        return classes;
    }
}
