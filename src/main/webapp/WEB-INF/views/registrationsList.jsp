<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>ICO</th>
			<th>Company name</th>
			<th>Registration date</th>
			<th>Registrator</th>
			<th>Branch</th>
			<th>Status</th>
		</tr>
		<c:forEach items="${registrations_list}" var="registration">
			<tr>
				<td>${registration.ico}</td>
				<td>${registration.companyName}</td>
				<td>${registration.regDate}</td>
				<td>${registration.unit.user.name}</td>
				<td>${registration.unit.branch.name}</td>
				<td>${registration.regStatus.name}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>