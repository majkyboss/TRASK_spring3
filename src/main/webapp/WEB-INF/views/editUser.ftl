<#import "basicTemplate.ftl" as structure/>
<#import "spring.ftl" as spring />

<#macro checkSelected value>
    <#if stringStatusValue?is_number && stringStatusValue == value?number>selected="selected"</#if>
    <#if stringStatusValue?is_string && stringStatusValue == value>selected="selected"</#if>
</#macro>

<@structure.basic_structure>

	<h1>Create New User</h1>

	<#if user??>
		<form action="save_user">
			<@spring.bind "user"/>
			<table>
				<tr>
					<td>First name</td>
					<td><@spring.formInput "user.name"/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Last name</td>
					<td><@spring.formInput "user.lastName"/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Degree</td>
					<td><@spring.formInput "user.degree"/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Birth number</td>
					<td><@spring.formInput "user.birthNumber"/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Work start date</td>
					<td><@spring.formInput "user.dateIn"/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Work end date</td>
					<td><@spring.formInput "user.dateOut"/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Role</td>
					<td><@spring.formSingleSelect "user.role" roles/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>
						<button formaction="save_user" formmethod="POST">Save</button>
					</td>
				</tr>
			</table>
		</form>
	</#if>

</@structure.basic_structure>