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
            Email Address: <input type="text" name="email"><br>
            <input type="hidden" name="action" value="email">
            <input type="submit" value="Send Email">
        </form>
    </body>
    ${message}
</html>
