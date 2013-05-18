import java.util.*;

/**
 * Project: RandomPasswordCodeIQ
 * User: makoto
 * Date: 4/20/13
 */
public class PassWord {

    /**
     * 生成されたパスワードが正しいかの検証を行い。
     * 不備がある場合は補正処理を実施するクラス。
     */
    private class PasswordVerifier {
        // key: 文字種類、value: パスワードで使用している位置のリスト
        private EnumMap<PasswordFactor, LinkedList<Integer>> status;
        private List<Character> passwordCandidate;

        private static final int AT_LEAST_FACTOR = 3;

        PasswordVerifier(List<Character> passwordCandidate) {
            this.passwordCandidate = passwordCandidate;
            this.status = new EnumMap<>(PasswordFactor.class);
        }

        /**
         * パスワード検証のメソッド、
         * 要求通りのパスワードを返すために検証・補正処理を実施する。
         * @return パスワード
         */
        public String verifyPassword() {

            // パスワードの状態を確認する。後続の補正種類で使用する情報の下準備
            for(int i = 0; i < passwordCandidate.size(); i++) {
                makePasswordStatus(i, passwordCandidate.get(i));
            }

            // 補正種類の実施
            // パスワードの文字種類が3種類未満だった場合に実施
            while(status.size() < AT_LEAST_FACTOR) {
                // 使用していない種類を検索し、対応する種類からランダムに一文字取得
                PasswordFactor none = findUnusedPasswordFactor();
                Character replace = randomPickup(none.factor);
                correctionPassword(replace);
            }

            // パスワード整形
            StringBuilder sb = new StringBuilder();
            for(Character c : passwordCandidate) {
                sb.append(c);
            }
            return sb.toString();
        }

        /**
         * パスワードに含まれる文字種類が足りなかった場合、
         * 現在のパスワードに含まれる文字種類がなくならないように引数で渡された置き換え後の文字で置き換える。
         * @param replace 置き換え後の文字
         */
        private void correctionPassword(Character replace) {
            // 使用している種類で、置き換えても0にならない文字種類を置き換え
            for(LinkedList<Integer> index : status.values()) {
                if(index.size() > 1) {
                    int i = index.pollFirst();
                    passwordCandidate.set(i, replace);
                    makePasswordStatus(i, replace);
                    break;
                }
            }
        }
        /**
         * passwordStatusを更新する、与えられた文字がどの種類かを判定し、文字の場所をpasswordStatusに記憶する。
         * @param i 文字の場所
         * @param c 文字
         */
        private void makePasswordStatus(int i, Character c) {
            for(PasswordFactor p : PasswordFactor.values()) {
                if(p.factor.contains(c.toString())) {
                    LinkedList<Integer> index = status.containsKey(p) ? status.get(p) : new LinkedList();
                    index.add(i);
                    status.put(p, index);
                    break;
                }
            }
        }

        /**
         *
         * @param seq 任意の文字列
         * @return 引数で渡された文字列からランダムに1文字
         */
        private Character randomPickup(String seq) {
            int index = randomValue(0, seq.length());
            return seq.charAt(index);
        }

        /**
         * 渡されているパスワードで使用していない文字種類を検索する。
         * @return 使用していない文字種類
         */
        private PasswordFactor findUnusedPasswordFactor() {
            PasswordFactor f = null;
            for(PasswordFactor none : PasswordFactor.values()) {
                if(!status.containsKey(none)) {
                    f = none;
                    break;
                }
            }
            return f;
        }
    }

    private enum PasswordFactor {
        SMALL_LETTER("abcdefghijklmnopqrstuvwxyz"),
        LARGE_LETTER("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
        DIGIT("1234567890"),
        SYMBOL("!\"#$%&'()");

        private PasswordFactor(String factor) {
            this.factor = factor;
        }
        private String factor;
    }


    /**
     * ランダムパスワードの生成機能
     * ・使用文字種は、英大文字、英小文字、数字、記号『!"#$%&'()』
     * ・そのうち3つ以上の文字種を組み合わせて生成
     * ・12文字以上16文字以下
     *
     * @return generatedPassword
     */
    String generate() {
        int length = passwordLength();

        // すべての文字種類を結合しシャッフルしパスワードの候補を作成。
        StringBuilder all = validseq();
        List<Character> allList = toList(all);
        Collections.shuffle(allList);
        List<Character> password = allList.subList(0, length);
        // シャッフル後に要件を満たしているかチェックをし補正したパスワードを返す。
        return verifiedPassword(password);
    }


    /**
     * 生成したパスワード候補を要求通りになっているか確認し、
     * 要求どおりになっていなければ補正する。
     * @param passwordCandidate
     * @return パスワード
     */
    private String verifiedPassword(List<Character> passwordCandidate) {
        // パスワード候補の文字の種類の確認
        PasswordVerifier verifier = new PasswordVerifier(passwordCandidate);
        // 生成済みのパスワードの状態を確認し補正を行う。
        return verifier.verifyPassword();
    }


    private StringBuilder validseq() {
        StringBuilder sb = new StringBuilder();
        for(PasswordFactor p : PasswordFactor.values()) {
            sb.append(p.factor);
        }
        return sb;
    }

    /**
     * パスワードの長さをランダムに返す。
     * @return 12〜16
     */
    private int passwordLength() {
        return randomValue(12, 16);
    }

    /**
     * @param lower 下限値
     * @param upper 上限値
     * @return lower 以上、 upper 以下の値を返す
     */
    private int randomValue(int lower, int upper) {
        Random rand = new Random();
        return rand.nextInt(upper - lower + 1) + lower;
    }

    /**
     * 引数に与えられたString配列をすべて結合し,一要素一文字のStringのリストとする
     * @param strings
     * @return List&lt;String&gt;
     */
    private List<Character> toList(StringBuilder strings) {
        List<Character> ret = new ArrayList<>();
        for(int i = 0; i < strings.length(); i++) {
            ret.add(strings.charAt(i));
        }

        Collections.shuffle(ret);
        return ret;
    }
}
