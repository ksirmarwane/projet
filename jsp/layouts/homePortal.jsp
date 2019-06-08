<%@page import="javax.transaction.SystemException"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/jcore/doInitPage.jspf" %>
<%@page import="com.jalios.jcms.taglib.card.CardsDisplayMode"%>
<%@ include file='/jcore/portal/doPortletParams.jspf'%><% 
%><%@ page contentType="text/html; charset=UTF-8" %>

<%

PortalJspCollection box = (PortalJspCollection) portlet;
ServletUtil.backupAttribute(pageContext, "ShowChildPortalElement");
%><%@ include file='/types/AbstractCollection/doIncludePortletCollection.jspf'%><%
ServletUtil.restoreAttribute(pageContext, "ShowChildPortalElement");


// Retrieve Portlet's buffer

String infos    = getPortlet(bufferMap,"infos");

        
        
%>
 <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
 
 
<!--  CORS ERROR -->
 
<script>
function CreateIssueBasicJira(){
	
	
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', 'http://localhost:2990/jira/rest/api/2/issue/PIT-1', true);
    xhttp.setRequestHeader('Content-type', "application/json");
    xhttp.setRequestHeader("Accept", "application/json");
    xhttp.setRequestHeader('Authorization',  "Basic YWRtaW46YWRtaW4=");
    
    
    xhttp.onreadystatechange = function(event) {
    	
    	console.log(xhttp.status);
        console.log(xhttp.response);
    	
    if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) {
                console.log("Réponse reçue: %s", this.responseText);
                var json=  this.responseText;
                json_string=JSON.parse(json);
                console.log(json_string);
            }
            else {
                console.log("Status de la réponse: %d (%s)", this.status, this.statusText);
            }
        }
    };
    //xhttp.open('GET', 'http://localhost:2990/jira/rest/api/2/issue/PIT-1/transitions', true);

    var data = {   
       fields: {
	        project: {
	           key: "PIT"
	         },
	        summary: "Test localhost create ",
	        description: "Test marwen PFE",
	        issuetype: {
	           name: "Task"
	         }
	      } 
       };
    console.log(JSON.stringify(data));

     xhttp.send(JSON.stringify(data));
}

// function CreateIssueBasicJira() {
	
// 	// var jira_create_issue_url= "http://devtools.spectrumgroupe.fr:10009/jira/rest/api/2/issue";
// 	 var jira_create_issue_url= "http://localhost:2990/jira/rest/api/2/issue";
// 	 var xhttp = new XMLHttpRequest();
	 
// 	 xhttp.open("POST", jira_create_issue_url, true);//async POST to API resource
	 
// 	 xhttp.setRequestHeader("Content-type", "application/json");
//      xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
//      xhttp.setRequestHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//      xhttp.setRequestHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
// //      xhttp.setRequestHeader("Authorization", "Basic YWRtaW46U3BnMjAxOCQ=");
//      xhttp.setRequestHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
//      xhttp.onload = () => {
//          console.log("JIRA REST API responded: ");
//          console.log(xhttp.status);
//          console.log(xhttp.response);
//        };
       
//      var data = {	fields: {
// 		    		       project: {
// 		    		          key: "PIT"
// 		    		        },
// 	    		           summary: "Test localhost create ",
// 		    		       description: "Test marwen PFE",
// 		    		       issuetype: {
// 		    		          name: "Task"
// 		    		        }
// 		    		    } };
    		   
//      xhttp.send(JSON.stringify(data));
   
// }
 </script>


<style>
#cover {
  background-image: url("plugins/Jiraplugin/images/cover1.jpg");
  background-color: #cccccc;
  height: 200px;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}

</style>
  <div class="app-body">
        <div class="container-fluid">
            <div class="row" id="cover">
                <div class="col-md-3"></div>
                <div class="col-md-8">
                <!-- 
                    <h1 style="margin-left:100px;margin-top:75px;">Accédez simplement à vos services<h1></h1></h1>
                    <h1 style="margin-left:250px;margin-bottom:25px;">JIRA en ligne<h1></h1></h1>
                 -->
                </div>
                <div class="col-md-1"></div>
            </div>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8" style="padding-left:100px;">
                    <h2 style="margin-left:50px;">Milliers de personnes personnes utilisent Jira ... et vous ?</h2>
                    <h3 style="margin-left:300px;margin-bottom:25px;">Comment ça marche ?</h3>
                    <jalios:cards mode="<%= CardsDisplayMode.INLINE %>" >
                    <jalios:card >
                        <jalios:cardImage src="plugins/Jiraplugin/images/cliquer.jpg"/>
                        <jalios:cardBlock >
                            <div class="card-title">1.Cliquer</div>
                            <div class="card-meta" style="color:black;"><p>sur le bouton Essayer Maintenant</p>
                            <p> sur le bouton Essayer Maintenant<br>sur le bouton Essayer Maintenant sur le bouton Essayer Maintenant</p>
                             </div>
                        </jalios:cardBlock>
                   </jalios:card>
               
               <jalios:card >
                    <jalios:cardImage src="plugins/Jiraplugin/images/oauth.jpg"/>
                    <jalios:cardBlock >
                        <div class="card-title">2.Authentifié</div>
                        <div class="card-meta" style="color:black;">Un compte existant pour vous identifier : <span style="color:blue">Jira</span>
                        <p> sur le bouton Essayer Maintenant<br>sur le bouton Essayer Maintenant sur le bouton Essayer Maintenant</p>
                        </div>
                    </jalios:cardBlock>
               </jalios:card>
               
               <jalios:card>
                    <jalios:cardImage src="plugins/Jiraplugin/images/jira1.jpg"/>
                    <jalios:cardBlock >
                        <div class="card-title">3.Vous êtes identifié !</div>
                        <div class="card-meta">Vous êtes désormais reconnu par le service en ligne</div>
                    </jalios:cardBlock>
<!--                     <form action="OAuthController"   method="post"> -->
<!--                         <button type="submit" name="GetRequestToken" class="btn btn-lg btn-card-block btn-primary">Essayer Maintenant ! </button> -->
<!--                     </form> -->

                    <button onclick=" CreateIssueBasicJira()">Click</button>
               </jalios:card>
               
               </jalios:cards>
                
                
                </div>
                <div class="col-md-1"></div>
            </div>
        
        </div>
        
</div>
<%@ include file="/jcore/doFooter.jspf" %>
