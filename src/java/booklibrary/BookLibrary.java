/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package booklibrary;

/**
 * 蔵書の管理を行うためのクラス．
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
public class BookLibrary {

    /* フィールド */
    private long isbn;              // ISBNコード
    private String title;           // タイトル
    private String author;          // 著者
    private String publisher;       // 出版社
    private int publicationYear;    // 出版年
    private int holdingCt;          // 蔵書数

    /**
     * コンストラクタ．
     */
    public BookLibrary() {
    }

    /**
     * ISBNコードの設定．ISBN-13で指定されている13桁のISBNコードを入力してください．
     * 引数に指定された数値が13桁でない場合は{@code IndexOutOfBoundsException}がスローされます．
     *
     * @param isbn ISBNコード
     * @throws IndexOutOfBoundsException この数値が13桁でない場合
     */
    public void setIsbn(long isbn) throws IndexOutOfBoundsException {
        validateIsbn(String.valueOf(isbn));
        this.isbn = isbn;
    }

    /**
     * タイトルの設定．
     *
     * @param title タイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 著者の設定．
     *
     * @param author 著者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 出版社の設定．
     *
     * @param publisher 出版社．
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 出版年の設定．出版年は西暦で指定してください．
     *
     * @param publicationYear 西暦で表された出版年
     */
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * 蔵書数の設定．非負整数を指定してください． 負数を入力したときは{@code Illegal}がスローされます．
     *
     * @param holdingCount 蔵書数
     * @throws IllegalArgumentException 蔵書数が負の数であるとき
     */
    public void setHoldingCt(int holdingCount) throws IllegalArgumentException {
        if (holdingCount >= 0) {
            this.holdingCt = holdingCount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * ISBNコードの取得．
     *
     * @return ISBNコード．
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * タイトルの取得．
     *
     * @return タイトル．
     */
    public String getTitle() {
        return title;
    }

    /**
     * 著者の取得．
     *
     * @return 著者．
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 出版社の取得．
     *
     * @return 出版社．
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 出版年の取得．
     *
     * @return 出版年．
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * 蔵書数の取得．
     *
     * @return 蔵書数．
     */
    public int getHoldingCt() {
        return holdingCt;
    }

    public boolean isLendable() {
        return holdingCt >= 1; // 在庫がある場合のみtrue
    }

    /* ISBNの桁数チェックを行うメソッド．13桁でない場合はスロー */
    private void validateIsbn(String isbn) throws IndexOutOfBoundsException {
        if (isbn.length() != 13) { // 13桁でない場合はスロー
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * 引数に指定された文字列が数値であるかどうかを判定します． 文字列sが数値である場合はこの文字列から先頭と最後の空白を削除したものが返されます．
     * ただし，文字列sが数字でない場合は{@code IllegalArgumentException}がスローされます．
     *
     * @param s 数値判定を行う文字列
     * @return この文字列の先頭と空白を削除したもの．
     * @throws IllegalArgumentException この文字列が0から9の数値でない場合
     */
    public static String validateNum(String s) throws IllegalArgumentException {
        String tmp = s.trim(); // 文字列の先頭と末尾の空白を削除
        if (!tmp.matches("^[0-9]+$")) { // 数字でなければ
            throw new IllegalArgumentException();
        }
        return tmp;
    }
}
