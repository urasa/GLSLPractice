package glslpractice.launcher;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 指定されたパッケージからクラスの一覧を作成する
 */
public class ClassIndexer {
    public static List<String> getIndex(String path) throws IOException {
        // パッケージはリソース中のディレクトリ構造を表しているので
        // クラスファイルはそのディレクトリからリソースとして取得できる
        String classdir = path.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(classdir);
        for (URL url : Collections.list(resources)) {
            System.out.println(url.toString());
        }

        return null;
    }
    public static void main(String[] args) {
        try {
            getIndex(args[1]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
