<#import "basicTemplate.ftl" as structure>
<@structure.basic_structure>
	
	<#if branches??>
	<form>
		<button formaction="create_branch" formmethod="GET"/>Add</button>
	</form>
	
	<table>
		<tr>
			<th>Branch name</th>
			<th>Manager</th>
			<th>Agents</th>
		</tr>
		<#list branches as branch>
			<tr>
				<td>${branch.name!""}</td>
				<td>${(branch.manager.name)!""} ${(branch.manager.lastName)!""}</td>
				<td>
					<#list branch.agents as agent>
						${agent.name} ${agent.lastName}<br>
					</#list>
					<#--<a href="add_user_to_branch_${branch.id}">add agent</a>-->
				</td>
				
				<td><a href="edit_branch_${branch.id}">edit</a></td>
				<td><a href="del_branch_${branch.id}">delete</a></td>
			</tr>
		</#list>
	</table>
	</#if>
</@structure.basic_structure>
