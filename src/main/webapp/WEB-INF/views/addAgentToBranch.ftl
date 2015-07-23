<#import "basicTemplate.ftl" as basic>
<#import "spring.ftl" as spring />

<@basic.basic_structure>
	
	<#if branch??>
		<form>
			<@spring.bind "branch"/>
			<table>
				<tr>
					<td colspan=2>Branch name</td>
					<td colspan=2>${branch.name}</td>					
				</tr>
				<tr>
					<td colspan=2>Manager name</td>
					<td colspan=2>${branch.manager}</td>
				</tr>
				<tr>
					<td>Agents</td>
					<td>
						<@basic.formMultiSelect "branch.agentUnits" users branch.id/>
					</td>
					<td><@spring.showErrors "<br>" /></td>
				</tr>
				<tr>
					<td>
						<@spring.formHiddenInput "branch.id"/>
						<@spring.formHiddenInput "branch.manager.id"/>
						<button formaction="update_branch_users" formmethod="POST">Save</button>
					</td>
				</tr>
			</table>
		</form>
	</#if>
	
</@basic.basic_structure>
