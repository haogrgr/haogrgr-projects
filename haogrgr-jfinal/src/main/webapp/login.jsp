<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("path", request.getContextPath());

	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
	if(username != null && username.trim().length() != 0 && password != null && password.trim().length() != 0){
	    //验证登录,这里可以写你的jdbc代码
	    if(username.equals("haogrgr") && password.equals("haogrgr")){
	        //登录成功,跳转到你指定的页面
	        response.sendRedirect("http://www.baidu.com");
	    }else{
	        //登录失败,跳转到登陆界面
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	    }
	}
	
	//请求参数用户名密码为空,则不操作.
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${path}">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<title>index</title>
</head>
<body>
	<div align="center" style="width: 100%; height: 100%; padding: 0px; margin: 0px;">
		<form action="${path}/login.jsp" method="post">
			<table>
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>密&nbsp;&nbsp;码:</td>
					<td><input type="text" name="password"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" name="submit">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>