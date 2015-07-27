<#import "basicTemplate.ftl" as basic/>
<#import "spring.ftl" as spring />



<@basic.basic_structure>

	<#if registration??>
	<form>
		<@basic.csrfToken/>
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
					<td><@basic.formSingleSelect "registration.regStatus", statuses, ""/></td>
					<td><@spring.showErrors "<br>", "error"/></td>
				<#else>
					<td>
						<#--<@spring.formHiddenInput "registration.regStatus"/>-->
						<input type="hidden" id="regStatus" name="regStatus" value="${registration.regStatus.id}"/>
						${(registration.regStatus.name)!}
					</td>
					<td><@spring.showErrors "<br>", "error"/></td>
				</#if>
			</tr>
			<tr>
				<td>Registrator</td>
				<#if users??>
					<td><@basic.formSingleSelect "registration.registrator", users, ""/></td>
					<td><@spring.showErrors "<br>", "error"/></td>
				<#else>
					<td>
						<#--<@spring.formHiddenInput "registration.registrator"/>-->
						<input type="hidden" id="registrator" name="registrator" value="${registration.registrator.id}"/>
						${(registration.registrator)!}
					</td>
					<td><@spring.showErrors "<br>"/></td>
				</#if>
			</tr>
			<tr>
				<td>Registration date</td>
				<td><@spring.formInput "registration.regDate"/><br></td>
				<td><@spring.showErrors "<br>"/></td>
			</tr>
			<tr>
				<td>
					<button formaction="save_reg" formmethod="POST">Save</button>
				</td>
			</tr>
		</table>
	</form>
	
	</#if>

</@basic.basic_structure>