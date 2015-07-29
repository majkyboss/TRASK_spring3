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
					<script>
// 						var noteIndex = ${registration.notes?size+1};
						
						
// 						function createNote(){
// 							var hidden = document.createElement('input');
// 							hidden.type = 'hidden';
// 							hidden.name = 'registration.notes['+ noteIndex +'].id';
// 							hidden.id = 'registration.notes['+ noteIndex +'].id';
// 							hidden.value = noteIndex;
							
// 							var noteDate = document.createElement('input');
// 							noteDate.type = 'text';
// 							noteDate.name = 'registration.notes['+ noteIndex +'].createdDate';
// 							noteDate.id = 'registration.notes['+ noteIndex +'].createdDate';
							
// 							var noteText = document.createElement('textarea');
// 							noteText.name = 'registration.notes['+ noteIndex +'].text';
// 							noteText.id = 'registration.notes['+ noteIndex +'].text';
							
// 							var tempDiv = document.createElement('div');
// 							tempDiv.appendChild(hidden);
// 							tempDiv.appendChild(document.createElement('br'));
// 							tempDiv.appendChild(noteDate);
// 							tempDiv.appendChild(document.createElement('br'));
// 							tempDiv.appendChild(noteText);
							
// 							var notes = document.getElementById('notes');
// 							notes.appendChild(tempDiv);
// 							noteIndex++;
							
							// --------------------------------------------
// 							var tempDiv = document.createElement('div');
							
// 							var inputText = document.createElement('input');
// 							inputText.type = 'text';
// 							inputText.name = 'registeredDate';
// 							tempDiv.appendChild(inputText);
							
// 							tempDiv.appendChild(document.createElement('br'));
							
// 							var ta = document.createElement('textarea');
// 							ta.id = i;
// 							ta.name = 'note';
// 							tempDiv.appendChild(ta);
							
// 							tempDiv.appendChild(document.createElement('hr'));
							
// 							var notes = document.getElementById('notes');
// 							notes.appendChild(tempDiv);
// 							i++;
// 						}
						
// 						function deleteLastNote(){
// 							var notes = document.getElementById('notes');
// 							notes.removeChild(notes.childNodes[notes.childNodes.length-1]);
// 							noteIndex--;
// 						}
					</script>
					Notes<br>
					<#--<a href="javascript:createNote()">{+}</a>
					<a href="javascript:deleteLastNote()">{-}</a>-->
				</td>
				<td>    
					<#list registration.notes as note>
						<#--<#assign index = note_index>
						<@spring.bind "registration.notes[note_index].id"/>	
						<input type="hidden" name="${spring.status.expression}"
    						id="${spring.status.expression}"
    						value="${spring.status.value}" /><br>
    						
    					<@spring.bind "registration.notes[note_index].createdDate"/>
						<input type="text" name="${spring.status.expression}"
    						id="${spring.status.expression}"
    						value="${spring.status.value}" /><br>
    						
    					<@spring.bind "registration.notes[note_index].text"/>
    					<textarea name="${spring.status.expression}"
    						id="${spring.status.expression}" >
							${spring.status.value}
							</textarea><br>-->
							
						Date created: ${(note.createdDate)!}<br>
						Text: ${(note.text)!}<br>
						<a href="edit_note_${note.id}">edit</a>
						<a href="del_note_${note.id}">delete</a>
						<hr>
					</#list>
					
					<#if registration.ico?? && registration.regDate??>
						<a href="add_note_${(registration.regDate.toString('dd.MM.yyyy'))!}_${registration.ico}">add note</a>
					</#if>
					</div>
				</td>
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