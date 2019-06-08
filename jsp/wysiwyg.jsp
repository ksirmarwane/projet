<%@page import="com.pluginjira.oauth.JiraAppHandler"%>
<%@page import="org.json.JSONArray"%><%
%><%@page import="org.json.JSONObject"%><%
%>
 <jsp:useBean id="appHandler" scope="page" class="com.pluginjira.oauth.JiraAppHandler"><%
      %><jsp:setProperty name="appHandler" property="request"       value="<%= request %>"/><%
      %><jsp:setProperty name="appHandler" property="response"      value="<%= response %>"/><%
      %><jsp:setProperty name="appHandler" property="*" /><%
    %></jsp:useBean><%

    appHandler.init();
    
         String appUrl = appHandler.getAppUrl();
         JSONArray dataprojects = JiraAppHandler.getAllMyProjects();
         JSONArray issuestypes = JiraAppHandler.getAllIssuesTypes();
  %>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script type="text/javascript" src="plugins/Jiraplugin/wordcounter/plugin.js" async defer"></script>

<style>
<!--
.form-group.required .control-label:after { 
   content:"*";
   color:red;
}
-->
</style>


<script type="text/javascript">

function closemodal(){
    (this).parent().parent().close();
}

function myfunction(){
    
    
         
           var selected = localStorage.getItem('selected');
           document.getElementById("summary").value = selected  ;
              
           
       }
       
window.onload = myfunction;   
</script>
    <div class="container-fluid py-5">
    
	 <form  action="http://localhost:8080/jcms/OAuthController"   method="post">
	   <div class="form-group  row py-2">
            <label for="projectkey" class="col-sm-2  control-label  text-right">Project </label>
            <div class="col-sm-6">
              <select class="form-control" id="projectkey" name="projectkey">
               <%
                                      for(int i=0;i<dataprojects.length();i++){  
               %>
			      <option value="<%=dataprojects.getJSONObject(i).getString("key") %>" >
			             <%out.print(dataprojects.getJSONObject(i).getString("name"));  %>
                         ( <%out.print(dataprojects.getJSONObject(i).getString("key"));   %> )
                  </option>
                  <%}%>
                  </select>
            </div>
       </div>
       
       <div class="form-group  row" style="margin-bottom:5%;">
            <label for="issuetype" class="col-sm-2 control-label text-right">Issue Type </label>
            <div class="col-sm-6">
              <select class="form-control" id="issuetype" name="issuetype">
               <%
                       for(int i=0;i<issuestypes.length();i++){
               %>
                  <option value="<%=issuestypes.getJSONObject(i).getString("name") %>" >
                        <%out.print(issuestypes.getJSONObject(i).getString("name"));  %>
                  </option>
                  <%}%> 
                </select>
            </div>
       </div>
      
       <hr>
       <div class="form-group  row py-3">
            <label for="summary" class="col-sm-2 control-label text-right">Summary</label>
            <div class="col-sm-8">
              <input type="TEXT" class="form-control" id="summary">
            </div>
       </div>
       <div class="form-group row py-2">
		    <label for="Description" class="col-sm-2 control-label text-right">Description </label>
		    <div class="col-sm-8">
		    <textarea class="form-control" id="Description" rows="5"></textarea>
		    </div>
       </div>
       <div class="form-group row py-3">
		    <div class="col-sm-10 text-right">
		      <button type="reset" class="btn btn-md btn-secondary">Reset</button>
		      <button type="submit" name="CreateIssue"  class="btn btn-md btn-primary">Create</button>
		    </div>
	  </div>
	 
	 </form>
   </div>
		