<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%-- 引入头文件 --%>
<%--<jsp:include page="/INC/common/head.jsp"></jsp:include>--%>
<%@include file="/INC/common/head.jsp"%>
<script type="text/javascript" src="/assets/js/jquery.min.js"></script>

<script>
    // 异步加载所有供应商名称
    $(function () {
        $.ajax({
            type:"get",
            url:"${pageContext.request.contextPath}/provider/findAllProvider",
            dataType:"json",
            success:function (data) {
                // alert(data);
                var str="";
                for (var provider of data){
                    str+='<option value="'+provider.id+'">'+provider.proName+'</option>';
                }
                $("#provideList").append(str);
            }
        })
    })
</script>
<div class="right">
     <div class="location">
         <strong>你现在所在的位置是:</strong>
         <span>订单管理页面 >> 订单添加页面</span>
     </div>
     <div class="providerAdd">
         <form id="billForm" name="billForm" method="post" action="${pageContext.request.contextPath }/bill/add">
             <!--div的class 为error是验证错误，ok是验证成功-->
             <input type="hidden" name="method" value="add">
             <div class="">
                 <label for="billCode">订单编码：</label>
                 <input type="text" name="billCode" class="text" id="billCode" value="">
				 <!-- 放置提示信息 -->
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productName">商品名称：</label>
                 <input type="text" name="productName" id="productName" value="">
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productName">商品描述：</label>
                 <input type="text" name="productDesc" id="productDesc" value="">
                 <font color="red"></font>
             </div>
             <div>
                 <label for="productUnit">商品单位：</label>
                 <input type="text" name="productUnit" id="productUnit" value="">
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productCount">商品数量：</label>
                 <input type="text" name="productCount" id="productCount" value="">
				 <font color="red"></font>
             </div>
             <div>
                 <label for="totalPrice">总金额：</label>
                 <input type="text" name="totalPrice" id="totalPrice" value="">
				 <font color="red"></font>
             </div>
             <div>
                 <label >供应商：</label>
                 <select name="provider.id" id="provideList">
		         </select>
				 <font color="red"></font>
             </div>
             <div>
                 <label >是否付款：</label>
                 <input type="radio" name="isPayment" value="1" checked="checked">未付款
				 <input type="radio" name="isPayment" value="2" >已付款
             </div>
             <div class="providerAddBtn">
                  <input type="submit" name="add" id="add" value="保存">
				  <input type="button" id="back" name="back" value="返回" >
             </div>
         </form>
     </div>
 </div>
</section>

<%-- 引入尾文件 --%>
<%--<jsp:include page="/INC/common/foot.jsp"></jsp:include>--%>
<%@include file="/INC/common/foot.jsp"%>
