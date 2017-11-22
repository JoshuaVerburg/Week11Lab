<%-- 
    Document   : login
    Created on : 10-Nov-2017, 8:26:48 AM
    Author     : awarsyle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <form action="reset" method="post">
            New Password: <input type="text" name="pass"><br>
            <input type="hidden" name="action" value="reset">
            <input type="hidden" name="uuid" value=${uuid}>
            <input type="submit" value="reset">
        </form>
    </body>
</html>