<#import "basicTemplate.ftl" as basic/>
<#import "spring.ftl" as spring />

<@basic.basic_structure>

	<#if note??>
	<form>
		<@basic.csrfToken/>
		<table>
			<tr>
				<td>
					Created date:<br>
				</td>
				<td>${note.createdDate}</td>
				<td></td>
			</tr>
			<tr>
				<td>Note text</td>
				<td><@spring.formTextarea "note.text"/></td>
				<td><@spring.showErrors "<br>"/></td>
			</tr>
			<tr>
				<td>
					<@spring.formHiddenInput "note.id"/>
					<@spring.formHiddenInput "note.createdDate"/>
					<button formaction="save_note" formmethod="POST">Save</button>
				</td>
			</tr>
		</table>
	</form>
	
	</#if>

</@basic.basic_structure>
