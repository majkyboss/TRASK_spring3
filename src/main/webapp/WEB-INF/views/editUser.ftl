<#import "basicTemplate.ftl" as basic/>
<#import "spring.ftl" as spring />



<@basic.basic_structure>

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
					<td><@basic.formSingleSelect "user.role" roles /><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Login name</td>
					<td><@spring.formInput "user.username" /><td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><@spring.formPasswordInput "user.password" /><td>
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

</@basic.basic_structure>