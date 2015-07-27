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
	
	<form action="/Spring3/logout" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<div class="top_menu">
		${(pageContext.request.userPrincipal.name)!}
		<#if !(currentUser??)>
			<a href="/Spring3/user/show_regs_list">Registrations</a>
			 | <a href="/Spring3/manager/show_users_list">Users</a>
			 | <a href="/Spring3/admin/show_branches_list">Branches</a>
			<span id="login_button">
				<a href="login">Log in</a>			
		<#elseif currentUser??>
			<#if currentUser.role??>
			<a href="/Spring3/${(currentUser.role.name)}/show_regs_list">Registrations</a>
			<#if currentUser.role=="admin" || currentUser.role=="manager" >
			 | <a href="/Spring3/${(currentUser.role.name)}/show_users_list">Users</a>
			<#if currentUser.role=="admin">
			 | <a href="/Spring3/${(currentUser.role.name)}/show_branches_list">Branches</a>
			</#if>
			</#if>
			</#if>
			<span id="login_button">
				${(currentUser.username)!}
				 | 
				<a href="javascript:formSubmit()">Log out</a>
		</#if>
		 
		</span>
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
				<#assign selected = checkSelected(options[value])>
            	<option value="${value?html}" ${selected} >${options[value]?html}</option>
            </#list>
        <#else> 
            <#list options as value>
				<#assign selected = checkSelected(options[value])>
            	<option value="${value?html}" ${selected} >${value?html}</option>
            </#list>
        </#if>
    </select>
</#macro>

<#function checkSelected value>
    <#if spring.stringStatusValue?is_number && spring.stringStatusValue == value?number>
		<#return 'selected="selected"'>
    <#elseif spring.stringStatusValue?is_string && spring.stringStatusValue == value >
		<#return 'selected="selected"'>
	<#else>
		<#return "">
	</#if>
</#function>

<#macro formMultiSelect path options attributes="">
    <@spring.bind path/>
    <select multiple="multiple" id="${spring.status.expression?replace('[','')?replace(']','')}" name="${spring.status.expression}" ${attributes}>
        <#list options?keys as value>
        	<#assign isSelected = spring.contains(spring.status.actualValue?default([""]), options[value])>
			<#if isSelected>
				<#assign selected = "selected=selected">
			<#else>
				<#assign selected = "">
			</#if>
        	<option value="${value?html}" ${selected}>${options[value]?html}</option>
        </#list>
    </select>
</#macro>

<#macro csrfToken>
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
</#macro>

<@basic_structure>
	ahoj
</@basic_structure>