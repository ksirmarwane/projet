package com.pluginjira.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.jalios.jcms.Channel;
import com.jalios.util.LangProperties;
import com.jalios.jcms.servlet.DisplayServlet;



@WebServlet(name="OAuthController" ,urlPatterns="/OAuthController")
public class OAuthController extends DisplayServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Channel channel = Channel.getChannel();
	private LangProperties properties=channel.getProperties("jcmsplugin.Jiraplugin");
	public static final String CHAMP_CODE = "secret";
	public static final String projectname = "projectkey";
	public static final String typename = "issuetype";
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
			response.setContentType("application/json");
			String path = request.getContextPath();
			String servletpath = request.getServletPath();
		 
			System.out.println("Contextpath : " + path);
			System.out.println("Servlettpath : " + servletpath);
			System.out.println("in do Post");
			System.out.println("*****************");
		 
		 
	        if (request.getParameter("GetRequestToken") != null) {
	        	
	        	System.out.println("OAuthClient start \n ");
	        	
					try {
						JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
						new OAuthClient(jiraOAuthClient).handleGetRequestTokenAction();
						response.sendRedirect(properties.getProperty("jcmsplugin.Jiraplugin.authorizationURL"));
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        } else if (request.getParameter("GetAccessToken") != null) {
	        	System.out.println("Getting Access Token start \n ");
	        	
				try {
					JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
					String secret = request.getParameter( CHAMP_CODE );
		        	properties.setProperty("jcmsplugin.Jiraplugin.secret",secret);
		        	channel.updateAndSaveProperties(properties);
					new OAuthClient(jiraOAuthClient).handleGetAccessToken(secret);
					request.getRequestDispatcher("/plugins/Jiraplugin/jsp/layouts/getdata.jsp").forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        } else if(request.getParameter("GetRequest") != null) {

	        	System.out.println("Getting data \n ");
	        	
				try {
					
					JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
					JSONArray data = new OAuthClient(jiraOAuthClient).GetAllProjects();
					System.out.println("data ......... : "+data);
					request.setAttribute("data", data);
					request.getRequestDispatcher("/plugins/Jiraplugin/jsp/layouts/getdata.jsp").forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        } else if (request.getParameter("CreateIssue") != null) {
	        	
	        	System.out.println("Create Issue start \n ");
	        	
				try {

					String selectedprojectkey =request.getParameter("projectkey");
					System.out.println("Project name ... "+selectedprojectkey);
					String selectedissuetype =request.getParameter("issuetype");
					System.out.println("issue type ... "+selectedissuetype);
					JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
					new OAuthClient(jiraOAuthClient).handleCreateIssue(selectedprojectkey,selectedissuetype);
					System.out.println("Create Issue End \n ");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        	
	        } else if(request.getParameter("GetIssue") != null) {

	        	System.out.println("Getting Issue data \n ");
	        	
				try {
					JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
					System.out.println(jiraOAuthClient.toString());
					new OAuthClient(jiraOAuthClient).GetIssue();
					JSONArray data = new OAuthClient(jiraOAuthClient).GetIssue();
					System.out.println("data Issue ......... : "+data);
					request.setAttribute("data", data);
					request.getRequestDispatcher("/plugins/Jiraplugin/jsp/layouts/getdata2.jsp").forward(request, response);
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        }
		  
		 
	   }

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{  
		
		 response.setContentType("text/html");
		 String path = request.getContextPath();
		 String servletpath = request.getServletPath();
		 System.out.println(path);
		 System.out.println(servletpath);
		 System.out.println("in do Get");
		 JiraOAuthClient jiraOAuthClient;
		try {
			jiraOAuthClient = new JiraOAuthClient();
			new OAuthClient(jiraOAuthClient).GetIssue();
			JSONArray data = new OAuthClient(jiraOAuthClient).GetIssue();
			System.out.println("data ......... : "+data);
			request.setAttribute("data", data);
		    request.getRequestDispatcher("/plugins/Jiraplugin/jsp/layouts/getdata.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	   }
	
    
}
