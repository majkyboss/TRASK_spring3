<#import "basicTemplate.ftl" as basic>
<#import "spring.ftl" as spring />

<@basic.basic_structure>
	
	<#if branch??>
		<form>
			<@spring.bind "branch"/>
			<table>
				<tr>
					<td>Branch name</td>
					<td><@spring.formInput "branch.name"/></td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>Manager name</td>
					<td><@basic.formSingleSelect "branch.manager" users/></td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>
						<@basic.formMultiSelect "branch.agentUnits" users branch.id "disabled" true/>
					</td>
					<td></td>
					<td></td>
				</tr>
				<#--
				<tr>
					<td>Agents</td>
					<td>
						<@basic.formMultiSelect "branch.agentUnits" users "${branch.id}"/>
						<a href="add_user_to_branch">add agent</a>
						<button formaction="add_user_to_branch" formmethod="POST">Add agent</button>
					</td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				-->
				<tr>
					<td>
						<@spring.formHiddenInput "branch.id"/>
						<button formaction="save_branch" formmethod="POST">Save</button>
					</td>
				</tr>
			</table>
		</form>
	</#if>
	
</@basic.basic_structure>
