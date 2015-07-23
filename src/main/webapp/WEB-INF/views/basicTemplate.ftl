<#macro basic_structure>
<#-- add parameters: "...macro structure user, afsdvc..."-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.main_content {
	margin-left: auto;
	margin-right: auto;
}

.top_menu {
	height: 30px;
	background: aqua;
	margin-bottom: 20px;
	padding: 10px 5px 5px 5px;
}

#login_button {
	float: right;
}
#footer {
	margin-top: 50px;
	text-align: center;
}
</style>
</head>
<body>
	<div class="top_menu">
		{user.name} | <a href="show_regs_list">Registrations</a> | <a href="show_users_list">Users</a> | <a href="show_branches_list">Branches</a> <span id="login_button">{Login btn}
			| {Logout btn}</span>
	</div>
	<div class="main_content">
	
		<#nested>
	
	</div>
	<div id="footer">
		<hr>
		<i> Registrations test project - TRASK SOLUTIONS SLOVAKIA </i>
	</div>
</body>
</html>
</#macro>

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

<#macro formMultiSelect path options valuePostfix="" attributes="" hiddenValues=false>
    <@spring.bind path/>
    <select multiple="multiple" id="${spring.status.expression?replace('[','')?replace(']','')}" name="${spring.status.expression}" ${attributes}>
        <#list options?keys as value>
			<#assign isSelected = false>
			<#list spring.status.actualValue as unit>
				<#assign id = unit.user.id>
				<#if isSelected!=true>
					<#assign isSelected = id?html==value?html>
				</#if>
			</#list>
        	<#--<#assign isSelected = spring.contains(spring.status.actualValue?default([""]), value)>-->
        	<option value="${value?html}_${valuePostfix}"<#if isSelected> selected="selected"</#if>>${options[value]?html}</option>
        </#list>
		<#list options?keys as value>
			<#assign isSelected = false>
			<#list spring.status.actualValue as unit>
				<#assign id = unit.user.id>
				<#if isSelected!=true>
					<#assign isSelected = id?html==value?html>
				</#if>
			</#list>
			<#if hiddenValues && isSelected>
        		<input type="hidden" value="${value?html}_${valuePostfix}" name="${spring.status.expression}"/>
        	</#if>
		</#list>
    </select>
</#macro>



<@basic_structure>
	ahoj
</@basic_structure>