<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />
    <script src="/assets/js/jquery.min.js"></script>

    <script>
        // 获取当前准确时间
        function getNowTime() {
            let date = new Date();
            let year = date.getFullYear();
            let month = date.getMonth()+1;
            let day = date.getDate();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();
            let days = date.getDay();
            switch(days) {
                case 0:
                    days = '星期日';
                    break;
                case 1:
                    days = '星期一';
                    break;
                case 2:
                    days = '星期二';
                    break;
                case 3:
                    days = '星期三';
                    break;
                case 4:
                    days = '星期四';
                    break;
                case 5:
                    days = '星期五';
                    break;
                case 6:
                    days = '星期六';
                    break;
            }
            var str = year+"年"+buchong(month)+"月" + buchong(day) + "日 "+ buchong(hours) + ":"+ buchong(minutes) + ":"+ buchong(seconds) + " " + days;
            // 填充指定位置
            $("#time").html(str)
        }
        // 对时间数字补0
        function buchong(str){
            if(str<=9){
                return "0"+str;
            }else {
                return str;
            }
        }
        window.setInterval(getNowTime,1000)
    </script>

</head>
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>超市订单管理系统</h1>
        <div class="publicHeaderR">
            <p><span><span>${login_user.userName}</span>,下午好！</span><span style="color: #fff21b"> ${userSession.userName }</span> , 欢迎你！</p>
            <a href="${pageContext.request.contextPath }/user/logout">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time">2015年1月1日 11:11  星期一</span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
    </section>
 <!--主体内容-->
 <section class="publicMian ">
     <div class="left">
         <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
         <nav>
             <ul class="list">
                 <li ><a href="${pageContext.request.contextPath }/bill/findAll">订单管理</a></li>
              <li><a href="${pageContext.request.contextPath }/provider/findAllByPage">供应商管理</a></li>
              <li><a href="${pageContext.request.contextPath }/user/findAll">用户管理</a></li>
              <li><a href="${pageContext.request.contextPath }/jsp/pwdmodify.jsp">密码修改</a></li>
              <li><a href="${pageContext.request.contextPath }/user/logout">退出系统</a></li>
             </ul>
         </nav>
     </div>
     <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
     <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>
