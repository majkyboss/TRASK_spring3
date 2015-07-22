<#import "basicTemplate.ftl" as structure>
<@structure.basic_structure>
	
	<form>
		<button formaction="create_reg" formmethod="GET"/>Add</button>
	</form>
	
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
				<td>${registration.unit.user.name!""}</td>
				<td>${registration.unit.branch.name!""}</td>
				<td>${registration.regStatus.name!""}</td>
				<td><a href="edit_reg_${(registration.regDate.toString('dd.MM.yyyy'))!}_${registration.ico}">edit</a></td>
				<td><a href="del_reg_${(registration.regDate.toString('dd.MM.yyyy'))!}_${registration.ico}">delete</a></td>
			</tr>
		</#list>
	</table>
</@structure.basic_structure>
