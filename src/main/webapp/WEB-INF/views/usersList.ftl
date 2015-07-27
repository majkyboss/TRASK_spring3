<#import "basicTemplate.ftl" as structure/>
<#import "spring.ftl" as spring />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@structure.basic_structure>

	<h1>Users list</h1>
	
	<@security.authorize access="hasRole('admin')">
	<form>
		<button formaction="create_user" formmethod="GET"/>Add</button>
	</form>
	</@security.authorize>

	<#if users?? && users?has_content>		
		<table>
			<tr>
				<th>First name</th>
				<th>Last name</th>
				<th>Degree</th>
				<th>Birth number</th>
				<th>Work start date</th>
				<th>Work end date</th>
				<th>Role</th>
			</tr>
			<#list users as user>
				<tr>
					<td>${user.name!""}</td>
					<td>${user.lastName!""}</td>
					<td>${user.degree!""}</td>
					<td>${user.birthNumber!""}</td>
					<td>${user.dateIn!""}</td>
					<td>${user.dateOut!""}</td>
					<td>${(user.role)!""}</td>
					<@security.authorize access="hasRole('admin')">
					<td><a href="edit_user_${user.id}">edit</a></td>
					<td><a href="del_user_${user.id}">delete</a></td>
					</@security.authorize>
				</tr>
			</#list>
		</table>
	</#if>

</@structure.basic_structure>