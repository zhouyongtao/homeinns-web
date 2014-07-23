<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<title>列表显示</title>
		<#include "/common/meta.ftl"/>

		<script type="text/javascript">
		</script>
	</head>

	<body>
	<div id="container">
		<div id="header">
			<div class="headrow">
				<form action="${ctx}/book/list" method="GET">
					<div>型  号:</div>
					<div>
						<input type="text" name="bookName" value="${(entity.bookName)!''}"/>
					</div>
					<div>
						<input type="submit" value="搜索" />
					</div>
					<div><a href="${ctx}/book/books_add"><input type="submit" value="添加" /></a></div>
				</form>
			</div>
		</div>
		<table>
					<tr>
						<td>书名</td>
						<td>出版商</td>
						<td>类型</td>
						<td>价格</td>
						<td>作者</td>
						<td> </td>
						<td> </td>
					</tr>

				    <#if list??>
						<#list list as entity>
						    <tr class="row">

						     <td>${entity.bookName}</td>
						     <td>${entity.mfrName}</td>
						     <td>${entity.bookSort}</td>
						     <td>${entity.costPrice}</td>
						     <td>${entity.bookAuthor}</td>
						     <td><a href="${ctx}/book/edit/${entity.bookId}"><img src="${ctx}/content/images/edit.gif" alt="修改" width="40" height="15" border="0"></a></td>
						     <td><a href="${ctx}/book/delete/${entity.bookId}">删除</a></td>
						    </tr>
				    	</#list>
					</#if>

			</table>
		 <div id="footer"></div>
	</div>
	</body>
</html>