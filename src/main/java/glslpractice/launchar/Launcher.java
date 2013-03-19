package glslpractice.launcher;

import java.io.IOException;

/**
 * 指定されたパスからmainを持ったクラスの一覧を取得する
 */
public class Launcher {
    public static void main(String[] args) {
        try {
            ClassIndexer.getIndex("glslpractice");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
