<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%-- 引入头文件 --%>
<%--<jsp:include page="/INC/common/head.jsp"></jsp:include>--%>
<script type="text/javascript" src="/assets/js/jquery.min.js"></script>
<%@include file="/INC/common/head.jsp"%>

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
				var pid=$("#hid_proid").val();
				for (var provider of data){
					if(provider.id==pid){
						str+='<option selected value="'+provider.id+'">'+provider.proName+'</option>';
					}
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
           <span>订单管理页面</span>
       </div>
       <div class="search">
       <form method="get" action="${pageContext.request.contextPath }/bill/findAll">
			<input name="method" value="query" class="input-text" type="hidden">
			<input id="hid_proid" value="${provider_id}" type="hidden">
			<span>商品名称：</span>
			<input name="productName" type="text" value="${productName}">

			<span>供应商：</span>
			<select name="provider_id" id="provideList">
				<option value="0">--请选择--</option>
       		</select>

			<span>是否付款：</span>
			<select name="isPayment">
				<option value="0">--请选择--</option>
				<option value="1" <c:if test="${isPayment==1}">selected</c:if>>未付款</option>
				<option value="2" <c:if test="${isPayment==2}">selected</c:if> >已付款</option>
       		</select>

			 <input	value="查 询" type="submit" id="searchbutton">
			 <a href="${pageContext.request.contextPath }/bill/add">添加订单</a>
		</form>
       </div>
       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="10%">订单编码</th>
              <th width="20%">商品名称</th>
              <th width="10%">供应商</th>
              <th width="10%">订单金额</th>
              <th width="10%">是否付款</th>
              <th width="10%">创建时间</th>
              <th width="30%">操作</th>
          </tr>
		  <c:forEach items="${pageBean.list}" var="bill">
			  <tr>
				  <td>
					  <span>${bill.billCode}</span>
				  </td>
				  <td>
					  <span>${bill.productName}</span>
				  </td>
				  <td>
					  <span>${bill.provider.proName}</span>
				  </td>
				  <td>
					  <span>${bill.totalPrice}</span>
				  </td>
				  <td>
					<span>
						<c:if test="${bill.isPayment==1}">未支付</c:if>
						<c:if test="${bill.isPayment==2}">已支付</c:if>
					</span>
				  </td>
				  <td>
					<span>
						<fmt:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd" />
					</span>
				  </td>
				  <td>
					  <span><a class="viewBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					  <span><a class="modifyBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					  <span><a class="deleteBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
				  </td>
			  </tr>
		  </c:forEach>
      </table>
	<div>
		<input type="hidden" id="totalPageCount" value=""/>
		【当前第${pageBean.pageNum}页 / 共${pageBean.pages}页】
		<div class="pageNum">
			<ul>
				<c:if test="${pageBean.pageNum==1}">
					<li class="disabled"><a href="#">首页</a></li>
					<li class="threeword disabled"><a href="#">上一页</a></li>
				</c:if>
				<c:if test="${pageBean.pageNum>1}">
					<li><a href="${pageContext.request.contextPath}/bill/findAll?pageNum=1&pageSize=${pageBean.pageSize}">首页</a></li>
					<li class="threeword"><a href="${pageContext.request.contextPath}/bill/findAll?pageNum=${pageBean.pageNum-1}&pageSize=${pageBean.pageSize}">上一页</a></li>
				</c:if>

				<%-- 循环遍历对应每一页链接 --%>
				<c:forEach begin="${pageBean.navigateFirstPage}" end="${pageBean.navigateLastPage}" var="currentPage">
					<c:if test="${pageBean.pageNum==currentPage}">
						<li class="btn-primary"><a href="${pageContext.request.contextPath}/bill/findAll?pageNum=${currentPage}&pageSize=${pageBean.pageSize}">${currentPage}</a></li>
					</c:if>
					<c:if test="${pageBean.pageNum!=currentPage}">
						<li><a href="${pageContext.request.contextPath}/bill/findAll?pageNum=${currentPage}&pageSize=${pageBean.pageSize}">${currentPage}</a></li>
					</c:if>
				</c:forEach>

				<c:if test="${pageBean.pageNum==pageBean.pages}">
					<li class="threeword disabled"><a href="#">下一页</a></li>
					<li class="threeword disabled"><a href="#">末页</a></li>
				</c:if>
				<c:if test="${pageBean.pageNum<pageBean.pages}">
					<li class="threeword"><a href="${pageContext.request.contextPath}/bill/findAll?&pageNum=${pageBean.pageNum+1}">下一页</a></li>
					<li class="threeword"><a href="${pageContext.request.contextPath}/bill/findAll?pageNum=${pageBean.pages}&pageSize=${pageBean.pageSize}">末页</a></li>
				</c:if>
			</ul>
		</div>
	</div>
  </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>


<%-- 引入尾文件 --%>
<%--<jsp:include page="/INC/common/foot.jsp"></jsp:include>--%>
<%@include file="/INC/common/foot.jsp"%>
