<#import "basicTemplate.ftl" as structure>
<@structure.basic_structure>
	
	<form>
		<button formaction="create_reg" formmethod="GET"/>Add</button>
		<button formaction="/Spring3/basic" formmethod="POST">Test Btn</button>
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
			</tr>
		</#list>
	</table>
</@structure.basic_structure>
