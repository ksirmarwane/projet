<%@page import="javax.transaction.SystemException"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/jcore/doInitPage.jspf" %>
<%@page import="com.jalios.jcms.taglib.card.CardsDisplayMode"%>
<%@page import="com.jalios.jcms.taglib.card.CardBlockMode"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%
%>

  <div class="app-body">
        <div class="container-fluid" style="margin-top:100px;background-color:#f5f5f5;height:500px;">
            <div class="row">
	                <div style="max-width: 450px;">
	                    <form action="http://localhost:8080/jcms/OAuthController"   method="post">
								<jalios:card css="has-footer hide-linear">
								  <jalios:cardBlock>
								    <div class="card-title">Verification</div>
								    <label for="secret">Verification Code <span class="requis">*</span></label>
	                                <input type="text" id="secret" name="secret" value="" size="20" maxlength="60" />
	                               </div>
								  </jalios:cardBlock>
								  <jalios:cardBlock css="card-block-xs" mode="<%= CardBlockMode.FOOTER %>">
								    <input type="submit" name="GetAccessToken" value="handleGetAccessToken" class="btn btn-success"/><br>
								  </jalios:cardBlock>
								</jalios:card>
					    </form>
			</div>
                    
	     </div>
   </div>
<%@ include file="/jcore/doFooter.jspf" %>

       
