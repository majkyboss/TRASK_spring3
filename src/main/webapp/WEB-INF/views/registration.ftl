<#import "basicTemplate.ftl" as structure/>
<#import "spring.ftl" as spring />



<@structure.basic_structure>

	<#if registration??>
	<form>
		<@spring.bind "registration"/>
		<table>
			<tr>
				<td>Company name</td>
				<td><@spring.formInput "registration.companyName"/><br></td>
				<td><@spring.showErrors "<br>"/></td>
			</tr>
			<tr>
				<td>ICO</td>
				<td><@spring.formInput "registration.ico"/><br></td>
				<td><@spring.showErrors "<br>"/></td>
			</tr>
			<tr>
				<td>Status</td>
				<#if statuses??>
					<td><@spring.formSingleSelect "registration.regStatus.name", statuses, ""/></td>
					<td><@spring.showErrors "<br>", "error"/></td>
				<#else>
					<@spring.formHiddenInput "registration.regStatus"/>
					<td>${(registration.regStatus.name)!}</td>
					<td><@spring.showErrors "<br>", "error"/></td>
				</#if>
			</tr>
			<tr>
				<td>Registrator</td>
				<#if users??>
					<#--<td>${user.name}</td>-->
					<td><@spring.formSingleSelect "registration.unit.user.name", users, ""/></td>
					<td><@spring.showErrors "<br>", "error"/></td>
				<#else>
					<td>${registration.unit.user.name}<br></td>
					<td><@spring.showErrors "<br>"/></td>
				</#if>
			</tr>
			<tr>
				<td>Registration date</td>
				<td><@spring.formInput "registration.regDate"/><br></td>
				<td><@spring.showErrors "<br>"/></td>
			</tr>
			<tr>
				<td><button formaction="save_reg" formmethod="POST">Save</button></td>
			</tr>
		</table>
	</form>
	
	</#if>

</@structure.basic_structure>