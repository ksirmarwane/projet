package com.pluginjira.oauth;

import org.json.JSONArray;

import com.jalios.jcms.handler.QueryHandler;

public class JiraAppHandler extends QueryHandler{
	
	protected View view = View.AllIssues;
	protected boolean isCollaborativeSpace;
	
	
	@Override
	  public void init() {
	    super.init();
	    isCollaborativeSpace = getWorkspace().isCollaborativeSpace();
	  }

	  public boolean showAppTitle() {
	    return !isCollaborativeSpace;
	  }

	  
	  //View
	  
	  public enum View {
		  OpenIssues,AllIssues,AssignedToMe,RecentlyCreatedIssues,RecentlyViewedIssues,Projects,ProjectsByType,DoneIssues,SearchedIssue,FiltredIssuesByProject;
		}
	  
	 
	  //affichage de title qui va varier selon View
	  
	  public String getAppTitle() {
		  switch(view) {
		  
		  case SearchedIssue:
			  return " Search Result ";
			  
		  case FiltredIssuesByProject :
			  return " Filtred Issues By Project";
		  
		  case DoneIssues :
			  return " Resolved Issues";
		  
		  case OpenIssues:
			  return "Open Issues";
		  
		  case RecentlyCreatedIssues:
			    return "Recently Created Issues";
   
		  case ProjectsByType:
				    return "ProjectsByType"; 
			    
		  case Projects:
		    return "Projects";
	
		  case AssignedToMe:
			    return "Assigned To Me";
 
		  case RecentlyViewedIssues:
		    return "Recently Viewed Issues";
		   
		  default:
		   case AllIssues:
		    return "All Issues";  
		    
		  }
		  
	  }
	  

	  public String getAppUrl() {
		    return "plugins/Jiraplugin/jsp/app/jhome.jsp";
		  }	  
	  
	  
	  //  méthodes retournant l'URL d'accès à la vue
	  
	  
	  private String getViewUrl(View view) {
		  return getAppUrl() + "?appView=" + view;
		}
	  
	  
	  public String getFiltredIssuesByProjectUrl() {
		  return getViewUrl(View.FiltredIssuesByProject);
		}


	  
	  public String getSearchedIssueUrl() {
		  return getViewUrl(View.SearchedIssue);
		}

	  
	  public String getDoneIssuesUrl() {
		  return getViewUrl(View.DoneIssues);
		}

	  public String getAllIssuesUrl() {
		  return getViewUrl(View.AllIssues);
		}

	  public String getOpenIssuesUrl() {
		  return getViewUrl(View.OpenIssues);
		}
	  
	  public String getRecentlyCreatedIssuesUrl() {
		  return getViewUrl(View.RecentlyCreatedIssues);
		}
	  
	  public String getRecentlyViewedIssuesUrl() {
		  return getViewUrl(View.RecentlyViewedIssues);
		}

	  public String getAssignedToMeIssuesUrl() {
		  return getViewUrl(View.AssignedToMe);
		}
	  
	  public String getProjectsUrl() {
		  return getViewUrl(View.Projects);
		}
	  
	  public String getProjectsByTypeUrl(String type) {
		  return getViewUrl(View.ProjectsByType);
		}
	  
	  
	  // méthodes qui permettent de savoir quelle vue doit être affichée
	  
	  public boolean showAllIssues() {
		  return view == View.AllIssues;
		}
	  
	  
	  public boolean showFiltredIssuesByProject() {
		  return view == View.FiltredIssuesByProject;
		}

	  
	  public boolean showSearchedIssue() {
		  return view == View.SearchedIssue;
		}
	  
	  public boolean showDoneIssues() {
		  return view == View.DoneIssues;
		}
	  
	  public boolean showOpenIssues() {
		  return view == View.OpenIssues;
		}
	  
	  
	  public boolean showRecentlyViewedIssues() {
		  return view == View.RecentlyViewedIssues;
		}
	  
	  public boolean showRecentlyCreatedIssues() {
		  return view == View.RecentlyCreatedIssues;
		}

	  public boolean showAssignedToMeIssues() {
		  return view == View.AssignedToMe;
		}
	  
	  public boolean showProjects() {
		  return view == View.Projects;
		}

	  public boolean showProjectsByType(String type) {
		  return view == View.ProjectsByType;
		}
	  
	  
	  public void setAppView(String v) {
		  this.view = View.valueOf(v);
		}
	  
	  public String getAvailableAppView() {
		    return view == null ? View.AllIssues.toString() : view.toString();
		  }
	  
	  public boolean showViews() {
		    return !isCollaborativeSpace;
		  }
	  
	  
	  
	  //methode java qui retour json
	  
	  public static JSONArray getSearchByText(String text) {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).SearchByText(text);
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  

	  public static JSONArray getFiltredIssuesByProject(String filter) {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).FiltredIssuesByProject(filter);
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  
	  public static JSONArray getAllIssues(int startAt,int maxResults) {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).AllIssues(startAt,maxResults);
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  
	  
	  
	  public static JSONArray getOpenIssues() {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				System.out.println(jiraOAuthClient.toString());
				JSONArray data = new OAuthClient(jiraOAuthClient).OpenIssues();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  
	  
	  public static JSONArray getDoneIssues() {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				System.out.println(jiraOAuthClient.toString());
				JSONArray data = new OAuthClient(jiraOAuthClient).DoneIssues();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  

	  
	  public static JSONArray getRecentlyCreatedIssues() {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				System.out.println(jiraOAuthClient.toString());
				JSONArray data = new OAuthClient(jiraOAuthClient).CreatedRecentlyIssues();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  
	  
	  public static JSONArray getAssignedToMeIssues() {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				System.out.println(jiraOAuthClient.toString());
				JSONArray data = new OAuthClient(jiraOAuthClient).AssignedToMeIssues();
				System.out.println("My issues lenght ...."+data.length());
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  
	  
	  public static JSONArray getRecentlyViewedIssues() {
			
		  try {
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				System.out.println(jiraOAuthClient.toString());
				JSONArray data = new OAuthClient(jiraOAuthClient).GetIssue();
				System.out.println(data.length());
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	  }
	  
	  public static JSONArray getAllMyProjects() {
			
		  try {
				
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).GetAllProjects();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }
	  	  
	  
	  public static JSONArray getAllIssuesTypes() {
			
		  try {
				
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).GetAllIssuesTypes();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }
	  
	  public static JSONArray getAllProjectCategory() {
			
		  try {
				
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).GetAllProjectCategory();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }
	  
	  
	  public static JSONArray getAllProjectTypes() {
			
		  try {
				
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).GetAllProjectTypes();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }
	  
	  public static JSONArray getAllStatus() {
			
		  try {
				
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).GetAllStatus();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }
	  
	  public static JSONArray getAllUsers() {
			
		  try {
				
				JiraOAuthClient jiraOAuthClient = new JiraOAuthClient();
				JSONArray data = new OAuthClient(jiraOAuthClient).GetAllUsers();
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	  }
	  
	  
			
}
