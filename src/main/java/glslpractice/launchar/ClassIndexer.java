package glslpractice.launcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
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
    public static List<String> getIndex(String packageName, boolean enableSubPackages) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fm = compiler.getStandardFileManager(null, null, null);
        Set<JavaFileObject.Kind> kinds = EnumSet.of(JavaFileObject.Kind.CLASS);

        ArrayList<String> classes = new ArrayList<>();
        for (JavaFileObject f : fm.list(StandardLocation.CLASS_PATH, packageName, kinds, enableSubPackages)) {
            System.out.println(f.toUri());
            classes.add(f.toUri().toString()
                                 .replaceAll(".*\\.jar!/", "")
                                 .replaceAll("\\.class.*$", "")
                                 .replace('/', '.')
                                 .replaceAll(".*"+packageName, packageName));
        }
        return classes;
    }
}
