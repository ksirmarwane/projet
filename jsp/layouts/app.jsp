<%@page import="com.jalios.jcms.uicomponent.app.AppConstants"%>
<%jcmsContext.setPageTitle("Search input"); // The app page title (Displayed in head)%>
<%@ include file="/front/app/doAppCommon.jspf" %>
<%%>
<%@ include file="/jcore/doHeader.jspf" %>
<form action="myUrl.jsp" method="POST">
  <div class="app app-myapp">
    <%-- SIDEBAR --%>
    <div class="app-sidebar">
      <div class="app-sidebar-icon">
        <a href="debug/app/debugAppFullDisplay.jsp" title="Return to app home">
          <jalios:icon src="glyph: icomoon icomoon-pacman" />
        </a>
      </div>
      <div class="app-sidebar-section" role="search">
        <div class="app-sidebar-section-title"><%= glp("ui.app.sidebar-section.filters") %></div>
        <jalios:field name='text' resource="field-light" css="app-sidebar-field">
          <jalios:control settings='<%= new TextFieldSettings().placeholder("ui.com.placeholder.search").css("no-focus") %>' />
          <span class="input-group-btn">
            <button class="btn btn-primary ajax-refresh" name="opSearch" type="submit"><jalios:icon src="app-search"/></button>
          </span>
        </jalios:field>
      </div>

    </div>
    <%-- MAIN --%>
    <div class="app-main">
      <%-- BODY --%>
      <div class="app-body">
        <div class="app-no-results">
          <jalios:icon src="app.no-result"/>
          <p>No results</p>
        </div>
      </div>
    </div>
  </div>
</form>
<%@ include file='/jcore/doFooter.jspf' %>