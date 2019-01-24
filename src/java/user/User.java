/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package user;

/**
 * ユーザーを管理するクラス．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
public class User {

    /* フィールド */
    private int usrNum;     // 管理番号
    private String usrName; // ユーザーネーム
    private String id;      // ログインID
    private String pass;    // パスワード
    private int overdueCt;  // 延滞回数
    private int lendCt;     // 貸出回数

    /**
     * コンストラクタ．
     */
    public User() {
    }

    /**
     * 管理番号の設定．
     *
     * @param userNum 管理番号
     */
    public void setUsrNum(int userNum) {
        this.usrNum = userNum;
    }

    /**
     * ユーザーネームの設定
     *
     * @param userName ユーザーネーム
     */
    public void setUsrName(String userName) {
        this.usrName = userName;
    }

    /**
     * ログインIDの設定．
     *
     * @param id ログインID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * パスワードの設定．半角英数字を入力してください．
     * 半角英数字以外を入力した場合は，{@code IllegalArgumentException}がスローされます．
     *
     * @param password パスワード
     * @throws IllegalArgumentException 半角英数字以外を入力したとき
     */
    public void setPass(String password) throws IllegalArgumentException {
        validatePass(password);
        this.pass = password;
    }

    /**
     * 延滞回数の設定．0以上の値を設定してください．
     * 負の値を入力した場合は{@code IllegalArgumentException}がスローされます．
     *
     * @param overdueCount 延滞回数
     * @throws IllegalArgumentException 負の数を入力したとき
     */
    public void setOverdueCt(int overdueCount) throws IllegalArgumentException {
        if (overdueCount >= 0) {
            this.overdueCt = overdueCount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 貸出回数の設定．0以上の値を設定してください．
     * 負の値を入力した場合は{@code IllegalArgumentException}がスローされます．
     *
     * @param lendCount 貸出回数
     * @throws IllegalArgumentException 負の数を入力したとき
     */
    public void setLendCt(int lendCount) throws IllegalArgumentException {
        if (lendCount >= 0) {
            this.lendCt = lendCount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 管理番号の取得．
     *
     * @return 管理番号．
     */
    public int getUsrNum() {
        return usrNum;
    }

    /**
     * ユーザーネームの取得．
     *
     * @return ユーザーネーム．
     */
    public String getUsrName() {
        return usrName;
    }

    /**
     * ログインIDの取得．
     *
     * @return ログインID．
     */
    public String getId() {
        return id;
    }

    /**
     * パスワードの取得．
     *
     * @return パスワード．
     */
    public String getPass() {
        return pass;
    }

    /**
     * 延滞回数の取得．
     *
     * @return 延滞回数．
     */
    public int getOverdueCt() {
        return overdueCt;
    }

    /**
     * 貸出回数の取得．
     *
     * @return 貸出回数．
     */
    public int getLendCt() {
        return lendCt;
    }

    /* passwordの文字列が半角英数字かどうかチェック */
    private void validatePass(String password)
            throws IllegalArgumentException {
        if (!password.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException();
        }
    }
}
