/*
 *  Copyright (c) 2018 masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
package reserve;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
 */
public class ReserveData {

    private int reserveNum; // 予約番号
    private int usrNum;     // ユーザーの管理番号
    private long isbn;      // ISBN
    private java.sql.Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setReserveNum(int reserveNum) {
        this.reserveNum = reserveNum;
    }

    public void setUsrNum(int usrNum) {
        this.usrNum = usrNum;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getReserveNum() {
        return reserveNum;
    }

    public int getUsrNum() {
        return usrNum;
    }

    public long getIsbn() {
        return isbn;
    }

}
