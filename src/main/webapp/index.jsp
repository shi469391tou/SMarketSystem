<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
    <%-- 准备一个首页面，自动跳转到登录页面 --%>
    <script>
        window.location.href="${pageContext.request.contextPath }/login.jsp";
    </script>
</body>
</html>
