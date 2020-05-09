<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />

    <script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery.min.js"></script>
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>超市订单管理系统</h1>
    <div class="publicHeaderR">
        <p><span><span>${login_user.userName}</span>先生/女士,下午好！</span><span style="color: #fff21b"> </span> , 欢迎你！</p>
        <a href="${pageContext.request.contextPath}/user/logout">退出</a>
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
    <input type="hidden" id="path" name="path" value=""/>
    <input type="hidden" id="referer" name="referer" value="null"/>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                type:"get",
                url:"${pageContext.request.contextPath}/role/findAll",
                dataType:"json",
                success:function (data) {
                    // 遍历
                    var str="";
                    for (var role of data){
                        str+='<option value="'+role.id+'">--'+role.roleName+'--</option>';
                    }
                    // 追加到指定位置
                    $("#queryUserRole").append(str);
                    $("#queryUserRole").val($("#roleid").val());
                }
            });
        })
    </script>

    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面</span>
        </div>
        <div class="search">
            <form id="queryForm" method="post" action="${pageContext.request.contextPath}/user/findAll">

                <span>用户名：</span>
                <input name="userName" class="input-text" type="text" value="${userName}">
                <input type="hidden" id="roleid" value="${roleId}"/>
                <span>用户角色：</span>
                <select id="queryUserRole" name="role.id">
                    <option value="0">--请选择--</option>
                </select>

                <input type="hidden" id="pageNo" name="pageNo" value="1"/>
                <input	value="查 询" type="submit" id="searchbutton">
                <a href="${pageContext.request.contextPath}/user/toUserAddPage" >添加用户</a>
            </form>
        </div>
        <!--用户-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">用户编码</th>
                <th width="20%">用户名称</th>
                <th width="10%">性别</th>
                <th width="10%">生日</th>
                <th width="10%">电话</th>
                <th width="10%">用户角色</th>
                <th width="30%">操作</th>
            </tr>
            <%-- 使用遍历循环 --%>
            <c:forEach items="${pageBean.list}" var="user">
                <tr>
                    <td>
                        <span>${user.userCode}</span>
                    </td>
                    <td>
                        <span>${user.userName}</span>
                    </td>
                    <td>
							<span>
								${user.gender}
							</span>
                    </td>
                    <td>
						<span>
                            <fmt:formatDate value="${user.birthday}" pattern="yyyy年MM月dd日"></fmt:formatDate>
						</span>
                    </td>
                    <td>
                        <span>${user.phone}</span>
                    </td>
                    <td>
                        <span>${user.role.roleName}</span>
                    </td>
                    <td>
                        <span><a href="/users/userview/1"><img src="/images/read.png" alt="查看" title="查看"/></a></span>
                        <span><a  href="/users/toUpdate/1"><img src="/images/xiugai.png" alt="修改" title="修改"/></a></span>
                        <span><a class="deleteUser" href="/users/delete/1" onclick="return confirm('真的删除吗？')"><img src="/images/schu.png" alt="删除" title="删除"/></a></span>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="hidden" id="totalPageCount" value=""/>
        【当前第${pageBean.pageNum}页 / 共${pageBean.totalPage}页】
        <div class="pageNum">
            <ul>
                <c:if test="${pageBean.pageNum==1}">
                    <li class="disabled"><a href="#">首页</a></li>
                    <li class="threeword disabled"><a href="#">上一页</a></li>
                </c:if>
                <c:if test="${pageBean.pageNum>1}">
                    <li><a href="${pageContext.request.contextPath}/user/findAll?pageNum=1&pageSize=${pageBean.pageSize}">首页</a></li>
                    <li class="threeword"><a href="${pageContext.request.contextPath}/user/findAll?pageNum=${pageBean.pageNum-1}&pageSize=${pageBean.pageSize}">上一页</a></li>
                </c:if>

                <%-- 循环遍历对应每一页链接 --%>
                <c:forEach begin="${pageBean.begin}" end="${pageBean.end}" var="currentPage">
                    <c:if test="${pageBean.pageNum==currentPage}">
                        <li class="btn-primary"><a href="${pageContext.request.contextPath}/user/findAll?pageNum=${currentPage}&pageSize=${pageBean.pageSize}">${currentPage}</a></li>
                    </c:if>
                    <c:if test="${pageBean.pageNum!=currentPage}">
                        <li><a href="${pageContext.request.contextPath}/user/findAll?pageNum=${currentPage}&pageSize=${pageBean.pageSize}">${currentPage}</a></li>
                    </c:if>
                </c:forEach>

                <c:if test="${pageBean.pageNum==pageBean.totalPage}">
                    <li class="threeword disabled"><a href="#">下一页</a></li>
                    <li class="threeword disabled"><a href="#">末页</a></li>
                </c:if>
                <c:if test="${pageBean.pageNum<pageBean.totalPage}">
                    <li class="threeword"><a href="${pageContext.request.contextPath}/user/findAll?&pageNum=${pageBean.pageNum+1}">下一页</a></li>
                    <li class="threeword"><a href="${pageContext.request.contextPath}/user/findAll?pageNum=${pageBean.totalPage}&pageSize=${pageBean.pageSize}">末页</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</section>



<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>



<footer class="footer">
    &copy;2017
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/time.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/calendar/WdatePicker.js"></script>
</body>
</html>
<script>

</script>
