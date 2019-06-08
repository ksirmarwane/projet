<%@page import="org.json.JSONArray"%>
<%@page import="javax.transaction.SystemException"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/jcore/doInitPage.jspf" %>
<%@page import="com.jalios.jcms.taglib.card.CardsDisplayMode"%>
<!-- @page import="com.pluginjira.oauth.OAuthClient"  -->
<%
%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!-- 
<div class="container-fluid">
    <div class="navbar  navbar-fixed-top">
         <div class="row"  style="background-color: #3b73af;height: 100px;">
             <div class="col-offset-4-3 text-center" >
                   <h2 style="color:white;"> Jira Pour JPlatform </h2>
             </div> 
             
        </div>
   </div>
</div> -->
<style>
body, html {
    height: 100%;
}
hr { 
  display: block;
  margin-top: 0.0em;
  margin-bottom: 0.0em;
  margin-left:0;
  margin-right:0;
  border-style: inset;
  border-width: 1px;
}

.list-group-item{
    height:30px;
    padding :2;
}

.input-group-btn .btn-group {
    display: flex !important;
}
.btn-group .btn {
    border-radius: 0;
    margin-left: -1px;
}
.btn-group .btn:last-child {
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
}

</style>

<div class="container-fluid">
    <!-- NavBar -->
    <div class="row">
	    <main class="col-2" style="background-color:#293a48">
	         
	    </main>
	    
	    <main class="col-10 py-3" style="background-color:#205081;">
                <div class="row py-4">
                    <div class="col-3 mb-4">
                         <h3 style="text-align:center;color:white">Jira Pour JPlatform</h3>
                    </div>
                </div>
        </main>
    </div>
    <!-- SideBar -->
    <div class="row">
	    <div class="col-2 px-1 fixed-top" style="background-color:#333333;height:100%;">
			  <div class="app-sidebar-icon">
		        <a href="" title="Return to app home">
		          <img src="plugins/Jiraplugin/images/jira.jpeg" style="height:65px;width:200px;margin-top:5%;margin-left:10%;margin-bottom:16%;" />
		        </a>
		      </div>
				<div class="app-sidebar-section" role="search">
				  
				  <jalios:field name='text' resource="field-light" css="app-sidebar-field">
				    <jalios:control settings='<%= new TextFieldSettings().placeholder("ui.com.placeholder.search").css("no-focus") %>' />
				    <span class="input-group-btn">
				      <button class="btn btn-primary ajax-refresh" name="opSearch"><img src="plugins/Jiraplugin/images/loupe.png" style="height:20px;width:25px;" /></button>
				    </span>
				  </jalios:field>
				</div>
	            <div class="list-group py-4 mt-4" style="text-align:center;">
	                <div class="app-sidebar-section">
	                   <form action="OAuthController"   method="post">     
                            <input type="submit" name="GetIssue" value="My Issues" class="btn  app-sidebar-button" style="color:white;width:100%;background-color:#333333"/> 
                        <hr>
                        </form>
				    </div>
	                <a href="#" class="mt-4" style="color:white;">My open issues</a>
	                <a href="#" style="color:white;">My open issues</a>
	                <a href="#" style="color:white;">Reported to me</a>
	                <a href="#" style="color:white;">All issues</a>
	                <a href="#" style="color:white;">Open issues</a>
	                <a href="#" style="color:white;">Done issues</a>
	                <a href="#" style="color:white;">Viewed Recently</a>
	                <a href="#" style="color:white;">Created Recently</a>
	            </div>
	            <div class="list-group" style="margin-top:20px;text-align:center;">
	                <div class="app-sidebar-section">
                       <form action="OAuthController"   method="post">     
                            <input type="submit" name="GetRequest" value="My Projects" class="btn app-sidebar-button" style="color:white;width:100%;background-color:#333333"/> 
                        <hr>
                        </form>
                    </div>
	                
	                <a href="#" class="disabled  mt-4">Projects Types</a>
	                <a href="#"style="color:white;">Software</a>
	                <a href="#" style="color:white;">Business</a>
	                
	                <a href="#" class="disabled  mt-2">Projects Categories</a>
	                <a href="#"style="color:white;">Digital</a>
	                <a href="#" style="color:white;">R&D</a>
	                <a href="#" style="color:white;">No category</a>
	                <a href="#" style="color:white;">Recent Project</a>
	            </div>
	
	    </div>
	 </div>
	 
	 
	 <!-- Main -->
     <div class="col-10 offset-2 py-4">           
                <div class="row py-2">
                    <div class="col-2">
	                    <jalios:cards mode="<%= CardsDisplayMode.INLINE %>">
						    <jalios:card>
							    <jalios:cardBlock css="card-block-xs">
							      <div class="dropdown show">
                                      <a style="width:100%;" class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Project Type 
                                      </a>
                                    
                                      <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                        <a class="dropdown-item" href="#">
                                        <img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE4LjEuMSwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJMYXllcl8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCINCgkgdmlld0JveD0iMCAwIDMwMCAzMDAiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDMwMCAzMDA7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnIGlkPSJMYXllcl8yIj4NCgk8cGF0aCBzdHlsZT0iZmlsbDojMzU3MkIwOyIgZD0iTTE1MCwwQzY2LjY2NywwLDAsNjYuNjY3LDAsMTUwczY2LjY2NywxNTAsMTUwLDE1MHMxNTAtNjYuNjY3LDE1MC0xNTBTMjMzLjMzMywwLDE1MCwweg0KCQkgTTE2Ni42NjcsMjE2LjY2N0g4My4zMzNWMjAwaDgzLjMzM1YyMTYuNjY3eiBNMjE2LjY2NywxODMuMzMzSDgzLjMzM3YtMTYuNjY3aDEzMy4zMzNWMTgzLjMzM3ogTTIxNi42NjcsMTUwSDgzLjMzM3YtMTYuNjY3DQoJCWgxMzMuMzMzVjE1MHogTTIxNi42NjcsMTE2LjY2N0g4My4zMzNWMTAwaDEzMy4zMzNWMTE2LjY2N3oiLz4NCjwvZz4NCjxyZWN0IHg9IjgzLjMzMyIgeT0iMjAwIiBzdHlsZT0iZmlsbDojRkZGRkZGOyIgd2lkdGg9IjgzLjMzMyIgaGVpZ2h0PSIxNi42NjciLz4NCjxyZWN0IHg9IjgzLjMzMyIgeT0iMTY2LjY2NyIgc3R5bGU9ImZpbGw6I0ZGRkZGRjsiIHdpZHRoPSIxMzMuMzMzIiBoZWlnaHQ9IjE2LjY2NyIvPg0KPHJlY3QgeD0iODMuMzMzIiB5PSIxMzMuMzMzIiBzdHlsZT0iZmlsbDojRkZGRkZGOyIgd2lkdGg9IjEzMy4zMzMiIGhlaWdodD0iMTYuNjY3Ii8+DQo8cmVjdCB4PSI4My4zMzMiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0ZGRkZGRjsiIHdpZHRoPSIxMzMuMzMzIiBoZWlnaHQ9IjE2LjY2NyIvPg0KPC9zdmc+DQo="
                                                        style="height:24px;widht:24px;margin-right:5px;"> Business</a>
                                        <a class="dropdown-item" href="#">
                                        <img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE4LjEuMSwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJMYXllcl8xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB4PSIwcHgiIHk9IjBweCINCgkgdmlld0JveD0iMCAwIDMwMCAzMDAiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDMwMCAzMDA7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxnIGlkPSJMYXllcl8yIj4NCgk8cGF0aCBzdHlsZT0iZmlsbDojRjc5MjMyOyIgZD0iTTE1MCwwQzY2LjY2NywwLDAsNjYuNjY3LDAsMTUwczY2LjY2NywxNTAsMTUwLDE1MHMxNTAtNjYuNjY3LDE1MC0xNTBTMjMzLjMzMywwLDE1MCwweg0KCQkgTTEzNi42NjcsMTc4LjMzM0wxMjUsMTkwbC00MS42NjctNDBMOTUsMTM4LjMzM2wzMC0zMEwxMzYuNjY3LDEyMGwtMzAsMzBMMTM2LjY2NywxNzguMzMzeiBNMjA1LDE2MS42NjdsLTMwLDMwTDE2My4zMzMsMTgwDQoJCWwzMC0zMGwtMzAtMzBMMTc1LDEwOC4zMzNMMjE2LjY2NywxNTBMMjA1LDE2MS42Njd6Ii8+DQo8L2c+DQo8Zz4NCgk8cG9seWdvbiBzdHlsZT0iZmlsbDojRkZGRkZGOyIgcG9pbnRzPSIxNzUsMTkxLjY2NyAyMDUsMTYxLjY2NyAyMTYuNjY3LDE1MCAxNzUsMTA4LjMzMyAxNjMuMzMzLDEyMCAxOTMuMzMzLDE1MCAxNjMuMzMzLDE4MCAJIi8+DQoJPHBvbHlnb24gc3R5bGU9ImZpbGw6I0ZGRkZGRjsiIHBvaW50cz0iMTI1LDEwOC4zMzMgOTUsMTM4LjMzMyA4My4zMzMsMTUwIDEyNSwxOTAgMTM2LjY2NywxNzguMzMzIDEwNi42NjcsMTUwIDEzNi42NjcsMTIwIAkiLz4NCjwvZz4NCjwvc3ZnPg0K"
                                                        style="height:24px;widht:24px;margin-right:5px;"> Software</a>
                                      </div>
                                    </div>
							    </jalios:cardBlock>
							</jalios:card>
						</jalios:cards>
					</div>
                    <div class="col-2">
                        <jalios:cardBlock css="card-block-xs">
                                  <div class="dropdown show">
                                      <a style="width:100%;" class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Project Lead 
                                      </a>
                                    
                                      <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                        <a class="dropdown-item" href="#">
                                        <img src="http://devtools.spectrumgroupe.fr:10009/secure/useravatar?size=small&avatarId=10349"
                                             style="height:24px;widht:24px;margin-right:5px;"> Rym</a>
                                        
                                      </div>
                                    </div>
                                </jalios:cardBlock>
                    </div>
                    <div class="col-2">
                    </div>
                    <div class="col-2">
                    </div>    
                    <div class="col-2" style="text-align:right;">
	                    <form action="OAuthController"   method="post">
				                 <button type="submit" name="GetIssue" class="btn btn-md btn-primary" style="width:100%;height:100M;margin:0%;">My Issues </button> 
	                    </form>
                     </div>
                        
                      <div class="col-2" style="text-align:right;">
	                    <form action="OAuthController"   method="post">
	                    <button type="submit" name="GetRequest" class="btn btn-md btn-primary" style="width:100%;height:100M;margin:0%;">My Project </button>    
	                    </form>
                     </div>
               </div> 
               
               <div class="row py-2">
                    <table class="table table-striped">
                          <thead>
                            <tr>
                              <th scope="col">Project</th>
                              <th scope="col">Name</th>
                              <th scope="col">Key</th>
                              <th scope="col">Project Lead</th>
                              <th scope="col">Project type</th>
                              <th scope="col">URL</th>                             
                            </tr>
                          </thead>
                          <tbody>

                            <%
                                    if((request.getAttribute("data"))!=null)
                                    {      JSONArray recupere = new JSONArray();
                                           recupere = (JSONArray)request.getAttribute("data");
                                           for(int i=0;i<recupere.length();i++){%>
                                               <tr>
                                               
                                               <td>
                                                    
                                                   <img src="<%=recupere.getJSONObject(i).getJSONObject("avatarUrls").getString("24x24")%>"
                                                        style="height:24px;widht:24px;">
                                               </td>
                                               <td><%out.print(recupere.getJSONObject(i).getString("name"));  %></td>
                                               <td><%out.print(recupere.getJSONObject(i).getString("key"));  %></td>
                                               <td>
                                                   <img src="<%=recupere.getJSONObject(i).getJSONObject("lead").getJSONObject("avatarUrls").getString("24x24")%>"
                                                        style="height:24px;widht:24px;margin-right:5px;">
                                                        <%out.print(recupere.getJSONObject(i).getJSONObject("lead").getString("displayName")); %>
                                              </td>
                                               <td><%out.print(recupere.getJSONObject(i).getString("projectTypeKey"));  %></td>
                                               <td>No URL</td>
                                            </tr>
                                                 <%} 
                                    }     
                            %>
                          
                          </tbody>
                        </table>                   
               </div>
        </div>
    </div>




<%@ include file="/jcore/doFooter.jspf" %>
