<%-- 
    Document   : mypage
    Created on : 2018/01/17, 10:01:55
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>マイページ</title>
    </head>
    <body>
        <h2>マイページ</h2>
        <%
            List<user.User> usrData
                    = (List<user.User>) session.getAttribute("usrData");
            String usrName = null;
            String id = null;
            int lendCt = 0;
            for (user.User usr : usrData) {
                usrName = usr.getUsrName();
                id = usr.getId();
                lendCt = usr.getLendCt();
            }
        %>
        <h4>ようこそ，<%= usrName%>さん．</h4>
        <p>あなたが今までに借りた回数は<%= lendCt %>回です．</p>
        <% // 管理者ならば
        if(id.equals("admin")) {
        %>
        <p><a href="user/UserRegist">ユーザー登録</a></p>
        <p><a href="user/UserList">ユーザーデータ一覧</a></p>
        <p><a href="booklibrary/Regist">蔵書登録</a></p>
        <%
        }
        %>
        <p><a href="booklibrary/Search">蔵書検索</a></p>
        <p><a href="booklibrary/DisplayBooksAll">蔵書一覧</a></p>
        <p><a href="reserve/SearchBook">図書予約</a></p>
        <p><a href="reserve/ReserveList">予約一覧</a></p>
        <p><a href="Logout">ログアウト</a></p>
    </body>
</html>
