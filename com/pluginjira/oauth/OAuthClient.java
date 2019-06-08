package  com.pluginjira.oauth;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jalios.jcms.Channel;
import com.jalios.util.LangProperties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;
import javax.json.*;


public class OAuthClient {

  
	//public PropertiesClient propertiesClient;
	private Channel channel = Channel.getChannel();
	private LangProperties properties=channel.getProperties("jcmsplugin.Jiraplugin");
	public JiraOAuthClient jiraOAuthClient;

    public OAuthClient(JiraOAuthClient jiraOAuthClient) {
        //this.propertiesClient= propertiesClient;
        this.jiraOAuthClient = jiraOAuthClient;
    }


    /**
     * Gets request token and saves it to properties file
     *
     * @param arguments list of arguments: no arguments are needed here
     * @return
     */
    public Optional<Exception> handleGetRequestTokenAction() {
    	
    	
        try {
        	
        	String consumerkey = properties.getProperty("jcmsplugin.Jiraplugin.consumer_key");        	
        	String privatekey = properties.getProperty("jcmsplugin.Jiraplugin.private_key");
        	
        	String requestToken = jiraOAuthClient.getAndAuthorizeTemporaryToken(consumerkey, privatekey);
        	System.out.println("*************");
        	System.out.println("getting temporary token");
        	System.out.println(this.getClass() + ".... "+requestToken);
        	
        	properties.setProperty("jcmsplugin.Jiraplugin.request_token", requestToken);

            channel.updateAndSaveProperties(properties);
            System.out.println("-------------------------");
            System.out.println(" Liste of properties after adding requesttoken");
            System.out.println(properties);
            System.out.println("-------------------------\n");
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of(e);
        }
    }

    /**
     * Gets access token and saves it to properties file
     *
     * @param arguments list of arguments: first argument should be secert (verification code provided by JIRA after request token authorization)
     * @return
     */
    public Optional<Exception> handleGetAccessToken(String code) {

    	LangProperties properties=channel.getProperties("jcmsplugin.Jiraplugin");
        System.out.println(this.getClass() +"   ...."+ properties.get("jcmsplugin.Jiraplugin.request_token"));
        
        String tmpToken = properties.getProperty("jcmsplugin.Jiraplugin.request_token");
        String secret = properties.getProperty("jcmsplugin.Jiraplugin.secret");
        String consumerkey = properties.getProperty("jcmsplugin.Jiraplugin.consumer_key");
        String privatekey = properties.getProperty("jcmsplugin.Jiraplugin.private_key");
        
        try {
            String accessToken = jiraOAuthClient.getAccessToken(tmpToken, secret, consumerkey, privatekey);
            properties.setProperty("jcmsplugin.Jiraplugin.access_token", accessToken);
            channel.updateAndSaveProperties(properties);
            System.out.println("-------------------------");
            System.out.println(" Liste of properties after adding Accesstoken");
            System.out.println(properties);
            System.out.println("-------------------------\n");
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of(e);
        }
    }
  
    
    /**
     * Create Issue 
     *
     * @param arguments list of arguments: access token
     * @return
     */
    
    public Optional<Exception> handleCreateIssue(String selectedprojectkey,String selectedissuetype) {

    	 try {
//             URL url = new URL("http://devtools.spectrumgroupe.fr:10009/rest/api/latest/issue");
    		 
    		 URL url = new URL("http://localhost:2990/jira/rest/api/2/issue");
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setDoOutput(true);
             conn.setDoInput(true);
      
             conn.setRequestMethod("POST");
//           conn.setRequestProperty("Authorization", "Basic YWRtaW46U3BnMjAxOCQ= ");
             conn.setRequestProperty("Authorization", "Basic YWRtaW46YWRtaW4= ");
             conn.setRequestProperty("Content-Type", "application/json");
             
             
             String data;
             JSONObject json = new JSONObject();
             JSONObject Jsonfield = new JSONObject();
             JSONObject JsonProj  = new JSONObject();
             JSONObject JsonIssue = new JSONObject();
             

             JsonIssue.put("name", selectedissuetype);
             JsonProj.put("key", selectedprojectkey);
             Jsonfield.put("project",JsonProj);
             Jsonfield.put("summary","Create issue PFE");
             Jsonfield.put("issuetype",JsonIssue);
             Jsonfield.put("duedate", "2018-08-28T15:13:36.000+0000");
             json.put("fields", Jsonfield);
             data=json.toString();
             
//             
//             String data = "{"
//                     + "\"fields\": {"
//                     + "\"project\":"
//                         + "{"
//                         +    "\"key\": \""+selectedprojectkey+"\""
//                         + "},"
//                     + "\"summary\": \"Create issue PFE\","
//                     + "\"issuetype\": {"
//                             + "\"name\": \""+selectedissuetype+"\""
//                         + "},"
//                     + "\"duedate\": \"2018-08-28T15:13:36.000+0000\""
//                     + "}"
//                 + "}";
             System.out.println("data ..... "+data);

             OutputStream os = conn.getOutputStream();
     		 os.write(data.getBytes());
     		 os.flush();
     		
     		
             if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
     			throw new RuntimeException("Failed : HTTP error code : "
     				+ conn.getResponseCode());
     		}
            
             BufferedReader br = new BufferedReader(new InputStreamReader(
     				(conn.getInputStream())));

     		String output;
     		System.out.println("Output from Server .... \n");
     		while ((output = br.readLine()) != null) {
     			System.out.println(output);
     		}
     		conn.disconnect();
             
             
            
         } catch (Exception e) {
             e.printStackTrace();
         }
		return null;
}
 
    
    
    
    /**
     * Makes request to JIRA to provided url and prints response content
     *
     * @param arguments list of arguments: first argument should be request url
     * @return
     */

  public JSONArray GetAllProjects() {
    	
    	
    	String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
        String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
        String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
        String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
        String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
        
        
        String rest="/rest/api/2/project?expand=description,lead,url,projectKeys,projectCategory";
        String url = jirahome.concat(rest);
        
        try {
        	
            OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
            HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
            @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            	//convert response to JsonObject
            	JSONArray jArray = new JSONArray(result);
                
            return jArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
  
  public JSONArray SearchByText(String text) {
  	
  	
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      String rest="/rest/api/2/search?jql=text%20~%20"+""+text+"";
      String url = jirahome.concat(rest);
      System.out.println(url);
      
      try {
      	
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
		  Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
          String result = s.hasNext() ? s.next() : "";
          	//convert response to JsonObject
           JSONObject jsonObj = new JSONObject(result);
          // getting sections array from json object
           JSONArray section_data = jsonObj.getJSONArray("issues");
              
          return section_data;
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
  }
  
  public JSONArray GetAllIssuesTypes() {
  	
  	
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      String rest="/rest/api/2/issuetype";
      String url = jirahome.concat(rest);
      
      try {
      	
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
          	//convert response to JsonObject
          	JSONArray jArray = new JSONArray(result);
              
          return jArray;
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
  }
  
  
  public JSONArray GetAllProjectCategory() {
	  	
	  	
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      
      String rest="/rest/api/2/projectCategory";
      String url = jirahome.concat(rest);
      
      try {
      	
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
          	//convert response to JsonObject
          	JSONArray jArray = new JSONArray(result);
              
          return jArray;
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
  }
  
 
  
  public JSONArray GetAllProjectTypes() {
	  	
	  	
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      
      String rest="/rest/api/2/project/type";
      String url = jirahome.concat(rest);
      
      try {
      	
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
          	//convert response to JsonObject
          	JSONArray jArray = new JSONArray(result);
              
          return jArray;
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
  }
  
  
  
  public JSONArray GetAllStatus() {
	  	
	  	
	  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
	      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
	      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
	      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
	      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
	      
	      
	      String rest="/rest/api/2/status";
	      String url = jirahome.concat(rest);
	      
	      try {
	      	
	          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
	          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
	          @SuppressWarnings("resource")
				Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
	            String result = s.hasNext() ? s.next() : "";
	          	//convert response to JsonObject
	          	JSONArray jArray = new JSONArray(result);
	              
	          return jArray;
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
			return null;
	  }
  
  
  public JSONArray GetAllUsers() {
	  	
	  	
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      
      String rest="/rest/api/latest/user/search?username=.&maxResults=100";
      String url = jirahome.concat(rest);
      
      try {
      	
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
          	//convert response to JsonObject
          	JSONArray jArray = new JSONArray(result);
          	
          return jArray;
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
  }
  
  
  public JSONArray AssignedToMeIssues() {
	  
	  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
	      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
	      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
	      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
	      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
	      
	      // return current user 's list of issues 
	      // /rest/api/2/search?jql=assignee%20in%20(currentUser())
	      // Viewd Recently 
	      String rest="/rest/api/2/search?jql=assignee%20in%20(currentUser())&maxResults=1000";
	      String url = jirahome.concat(rest);
	      System.out.println("url :"+url);
	      
	      try {
	    	  
	          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
	          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
	          System.out.println("****responeQuit****");
	          @SuppressWarnings("resource")
				Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
	            String result = s.hasNext() ? s.next() : "";	            
	            //getting whole json string
	              JSONObject jsonObj = new JSONObject(result);
	            // getting sections array from json object
	              JSONArray section_data = jsonObj.getJSONArray("issues");
	              
	          return section_data;
	          
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
			return null;
  }

  


  public JSONArray OpenIssues() {
	  
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      String rest="/rest/api/2/search?jql=status%20!%3D%20Done&maxResults=1000";
      String url = jirahome.concat(rest);
      System.out.println("url :"+url);
      
      try {
    	  
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          System.out.println("****responeQuit****");
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";	            
            //getting whole json string
              JSONObject jsonObj = new JSONObject(result);
              String total =jsonObj.getString("total");
              System.out.println(total);
              JSONArray section_data = jsonObj.getJSONArray("issues");
              
          return section_data;
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
}
 
  
  

  public JSONArray DoneIssues() {
	  
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      String rest="/rest/api/2/search?jql=status%20%3D%20Done%20ORDER%20BY%20duedate%20DESC";
      String url = jirahome.concat(rest);
      System.out.println("url :"+url);
      
      try {
    	  
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          System.out.println("****responeQuit****");
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";	            
            //getting whole json string
              JSONObject jsonObj = new JSONObject(result);
              String total =jsonObj.getString("total");
              System.out.println(total);
              JSONArray section_data = jsonObj.getJSONArray("issues");
              
          return section_data;
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
}
   
    
  
  
  

  public JSONArray CreatedRecentlyIssues() {
	  
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      String rest="/rest/api/2/search?jql=created%20>%3D%20startOfYear()%20";
      String url = jirahome.concat(rest);
      System.out.println("url :"+url);
      
      try {
    	  
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          System.out.println("****responeQuit****");
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";	            
            //getting whole json string
              JSONObject jsonObj = new JSONObject(result);
              String total =jsonObj.getString("total");
              System.out.println(total);
              JSONArray section_data = jsonObj.getJSONArray("issues");
              
          return section_data;
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
}
 

  

  public JSONArray FiltredIssuesByProject(String filter) {
	  
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");      
      String rest="/rest/api/2/search?jql="+filter;
      String url = jirahome.concat(rest);
      System.out.println("rest ... "+url);
      try {
    	  
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";	            
            //getting whole json string
              JSONObject jsonObj = new JSONObject(result);
              JSONArray section_data = jsonObj.getJSONArray("issues");
              
          return section_data;
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
}



  public JSONArray AllIssues(int startAt,int maxResults) {
	  
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      
      String rest="/rest/api/2/search?jql=&startAt="+startAt+"&maxResults="+maxResults;
      String url = jirahome.concat(rest);
      
      try {
    	  
          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";	            
            //getting whole json string
              JSONObject jsonObj = new JSONObject(result);
              JSONArray section_data = jsonObj.getJSONArray("issues");
              
          return section_data;
          
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
}

  
  
  
  //recently viewed
  public JSONArray GetIssue() {
  	
  	
  	  String tmpToken = channel.getProperty("jcmsplugin.Jiraplugin.access_token");
      String secret = channel.getProperty("jcmsplugin.Jiraplugin.secret");
      String jirahome = channel.getProperty("jcmsplugin.Jiraplugin.jira_home");
      String consumerkey = channel.getProperty("jcmsplugin.Jiraplugin.consumer_key");
      String privatekey =  channel.getProperty("jcmsplugin.Jiraplugin.private_key");
      System.out.println(" ------------------------------------------------");
      
      String rest="/rest/api/2/issue/picker";
      String url = jirahome.concat(rest);
      System.out.println("url :"+url);
      
      try {

          OAuthParameters parameters = jiraOAuthClient.getParameters(tmpToken, secret,consumerkey ,privatekey);
          System.out.println(parameters.realm);
          HttpResponse response = getResponseFromUrl(parameters, new GenericUrl(url));
          System.out.println("****responeQuit****");
          @SuppressWarnings("resource")
			Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
          	//convert response to JsonArray
            
            //getting whole json string
              JSONObject jsonObj = new JSONObject(result);
              System.out.println("jsonObj...... "+jsonObj);
            // getting sections array from json object
              JSONArray section_data = jsonObj.getJSONArray("sections");
              System.out.println("section_data ....... "+section_data);
              System.out.println("length......... "+section_data.length());
              
          return section_data;
      } catch (Exception e) {
          e.printStackTrace();
      }
		return null;
  }
  
  
    /**
     * Prints response content
     * if response content is valid JSON it prints it in 'pretty' format
     *
     * @param response
     * @throws IOException
     */
    public void parseResponse(HttpResponse response) throws IOException {
        @SuppressWarnings("resource")
		Scanner s = new Scanner(response.getContent()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        try {
            JSONObject jsonObj = new JSONObject(result);
            System.out.println(jsonObj.toString(2));/*
            String name = jsonObj.getString("name");  
            System.out.println(name.toString());
            String self = jsonObj.getString("self");  
            System.out.println(self.toString());
            String projectTypeKey = jsonObj.getString("projectTypeKey");  
            System.out.println(projectTypeKey.toString());
            String key = jsonObj.getString("key");  
            System.out.println(key.toString());*/
        } catch (Exception e) {
            System.out.println(result);
        }
    }

    /**
     * Authanticates to JIRA with given OAuthParameters and makes request to url
     *
     * @param parameters
     * @param jiraUrl
     * @return
     * @throws IOException
     */
    public static HttpResponse getResponseFromUrl(OAuthParameters parameters, GenericUrl jiraUrl) throws IOException {
    	
    	HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(parameters);
        HttpRequest request = requestFactory.buildGetRequest(jiraUrl);
        return request.execute();
    }
}
