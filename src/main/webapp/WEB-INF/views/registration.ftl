<#import "basicTemplate.ftl" as structure>
<#import "spring.ftl" as spring />



<@structure.basic_structure>

	
	<@spring.bind "registration.*"/>
	<@spring.showErrors "<br>"/>
	<br>
	${spring.status}<br>
	<#list spring.status.errorMessages as error>
		<b>${error}</b><br>
	</#list><br>
	
	<form>
		<table>
			<tr>
				<td>Company name</td>
				<td><@spring.formInput "registration.companyName"/><br></td>
				<td><@spring.showErrors "<br>"/>
				</td>
			</tr>
			<tr>
				<td>ICO</td>
				<td><@spring.formInput "registration.ico"/><br></td>
				<td><@spring.showErrors "<br>"/>
				</td>
			</tr>
			<tr>
				<td>Status</td>
				<td><@spring.formInput "registration.regStatus"/><br></td>
				<td><@spring.showErrors "<br>", "error"/>
				</td>
			</tr>
			<tr>
				<td>Registrator</td>
				<td><@spring.formInput "registration.unit.user.name"/><br></td>
				<td><@spring.showErrors "<br>"/>
				</td>
			</tr>
			<tr>
				<td><button formaction="save_reg" formmethod="POST">Save</button></td>
			</tr>
		</table>
	</form>
	
	<br>
	<br>
	Company name: ${(registration.companyName)!""}<br>
	ICO: ${(registration.ico)!}

</@structure.basic_structure>