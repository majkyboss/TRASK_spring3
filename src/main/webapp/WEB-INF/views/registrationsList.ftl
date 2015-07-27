<#import "basicTemplate.ftl" as structure>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@structure.basic_structure>
	<h1>Registrations list</h1>
	<@security.authorize access="!hasRole('manager')">
	<form>
		<button formaction="create_reg" formmethod="GET"/>Add</button>
	</form>
	</@security.authorize>
	
	<table>
		<tr>
			<th>ICO</th>
			<th>Company name</th>
			<th>Registration date</th>
			<th>Registrator</th>
			<th>Branch</th>
			<th>Status</th>
		</tr>
		<#list registrations_list as registration>
			<tr>
				<td>${registration.ico!""}</td>
				<td>${registration.companyName!""}</td>
				<td>${registration.regDate!""}</td>
				<td>${registration.registrator.name!""}</td>
				<td>${registration.registrator.currentBranch.name!""}</td>
				<td>${registration.regStatus.name!""}</td>
				<td><a href="edit_reg_${(registration.regDate.toString('dd.MM.yyyy'))!}_${registration.ico}">edit</a></td>
				<td><a href="del_reg_${(registration.regDate.toString('dd.MM.yyyy'))!}_${registration.ico}">delete</a></td>
			</tr>
		</#list>
	</table>
</@structure.basic_structure>
