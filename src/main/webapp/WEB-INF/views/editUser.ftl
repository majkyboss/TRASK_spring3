<#import "basicTemplate.ftl" as structure/>
<#import "spring.ftl" as spring />

<#macro formSingleSelect path options attributes="">
	<@spring.bind path/>
    <select id="${spring.status.expression?replace('[','')?replace(']','')}" name="${spring.status.expression}" ${attributes}>
        <#if options?is_hash>
            <#list options?keys as value>
            <option value="${value?html}"<@spring.checkSelected options[value]/>>${options[value]?html}</option>
            </#list>
        <#else> 
            <#list options as value>
            <option value="${value?html}"<@spring.checkSelected value/>>${value?html}</option>
            </#list>
        </#if>
    </select>
</#macro>

<@structure.basic_structure>

	<h1>User Edit</h1>

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
					<td><@formSingleSelect "user.role" roles/><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>
						<@spring.formHiddenInput "user.id"/>
						<button formaction="save_user" formmethod="POST">Save</button>
					</td>
				</tr>
			</table>
		</form>
	</#if>

</@structure.basic_structure>