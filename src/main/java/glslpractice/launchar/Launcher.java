package glslpractice.launcher;

import IOException;

/**
 * 指定されたパスからmainを持ったクラスの一覧を取得する
 */
public class Launcher {
    public static void main(String[] args) {
        try {
            ClassIndexer.getIndex(args[1]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
