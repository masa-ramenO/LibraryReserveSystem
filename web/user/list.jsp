<%-- 
    Document   : list
    Created on : 2018/01/17, 22:13:02
    Author     : masa-ramenO(45961656+masa-ramenO@users.noreply.github.com)
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザーデータ</title>
    </head>
    <body>

        <%
            List<user.User> usrData
                    = (List<user.User>) request.getAttribute("usrData");
        %>
        <form action="Delete" method="POST">
            <table border="1">
                <tr>
                    <th>削除</th>
                    <th>管理番号</th><th>ユーザーネーム</th><th>ID</th><th>貸出回数</th><th>延滞回数</th>
                </tr>
                <%
                    for (user.User usr : usrData) {
                %>
                <tr>
                    <td>
                        <input type="radio" name="delData" value="<%= usr.getUsrNum() %>"
                               <%
                               if (usr.getId().equals("admin")) {
                                      out.print(" disabled");
                                   }
                               %>
                               >
                    </td>
                    <td><%= usr.getUsrNum()%></td>
                    <td><%= usr.getUsrName()%></td>
                    <td><%= usr.getId()%></td>
                    <td><%= usr.getLendCt()%></td>
                    <td><%= usr.getOverdueCt()%></td>
                </tr>
                <%
                    }
                %>
            </table>
            <p>※削除するとデータの復元はできません．</p>
            <p><input type="submit" value="削除"></p>
        </form>

        <p><a href="../Mypage">マイページ</a></p>
    </body>
</html>
