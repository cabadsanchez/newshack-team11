package es.daedalus.newshack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.apache.http.client.ClientProtocolException;

import es.daedalus.textalytics.sempub.Result;
import es.daedalus.textalytics.sempub.SempubClient;
import es.daedalus.textalytics.sempub.TextalyticsClientException;
import es.daedalus.textalytics.sempub.domain.Category;
import es.daedalus.textalytics.sempub.domain.Document.Language;
import es.daedalus.textalytics.sempub.domain.Entity;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Hello world!
 *
 */
public class TwitterManager 
{
	
	Twitter twitter;
	SempubClient textalytics;
	static ObjectMapper mapper;

	
	public TwitterManager() {
    	this.twitter = TwitterFactory.getSingleton();
    	this.textalytics = new SempubClient();
    	mapper = new ObjectMapper();
	}

	public List<Status> getUserTimeline(String screenname) 
	{
	   List<Status> statuses = null;
		
		try {
			User user = twitter.showUser(screenname);
			statuses = this.getUserTimeline(user.getId(), 100);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		if (statuses == null)
			return new LinkedList<Status>();
		else 
			return statuses;
	}
	
	public List<Status> getUserTimeline(long id, int max) 
	{
		List<Status> statuses = new LinkedList<Status>();
		
		try {
			Paging paging = new Paging(1, max);
			statuses.addAll(twitter.getUserTimeline(id, paging));
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return statuses;
	}
	
	public List<User> getRetweetersIds(long statusId, int max)  
	{	
    	List<User> users = new LinkedList<User>();
		int step = (max < 100) ? max : 100;
		
		for (int i = 0; i < (max / step)  ; i++) {
			try {
				System.out.println("step " + step + "  page " + (i -1));
				IDs ids = twitter.getRetweeterIds(statusId, step, i -1 );
		    	users.addAll(twitter.lookupUsers(ids.getIDs()));
			} catch (TwitterException e) {
				e.printStackTrace();
			}    				
		}

		return users;
	}
	
	public Result analyzeUserStatus(Status status) {
		Result result = null;
		
		try {
			String language = status.getLang().substring(0, 2);
			if (language.equals("es") || language.equals("en"))

			result = this.textalytics.analyze(status.getText(),Language.valueOf(language));
			
		} catch (TextalyticsClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return result;
	}
	
	public void countUserStatusList(LanguageStats stats, List<Status> statuses) {
		for (Status status : statuses) {
			Result annotations = this.analyzeUserStatus(status);
			if (annotations != null) {
				List<Category> categories = annotations.getCategories();
				for (Category category : categories) {
					stats.addTheme(category.getLabels().get(0));
				}
				
				List<Entity> entities = annotations.getEntities();
				for (Entity entity : entities) {
					stats.addEntity(entity.getForm());;
				}
				
			}
		}
	}
	
	/**
	 * Method to store retweeters and retweets in JSON files
	 * 
	 * @param users
	 * @param statuses
	 */
	public void serializeUsers(List<User> users, List<Status> statuses) {
		int t = 0;
		int u = 0;
		for (User user : users) {
			for (Status status : statuses) {				
				try {
					mapper.writeValue(new File("/home/bolloyo/software/newshack-team11/tweets/tweet" + t +"-user" + u + ".json"), status);
					t++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			t = 0;
    		try {
				mapper.writeValue(new File("/home/bolloyo/software/newshack-team11/users/user" + u +".json"), user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			u++;
		}
	}
	

    public static void main( String[] args ) throws TwitterException
    {

    	TwitterManager twitterManager = new TwitterManager();
    	
 
//    	String bbcbreaking = "BBCBreaking";
//    	List<Status> statuses = twitterManager.getUserTimeline(bbcbreaking);
//    	for (Status status : statuses) {
//			System.out.println(status.getId() + " " + status.getText() );	
//		}

//    	Status first = statuses.get(0);

    	int numRTUsers = 10;
    	int numStatuses = 20;
    	
    	
    	String id = "544302821450207233";
    	long tweetid = Long.valueOf(id);
    	LanguageStats stats = new LanguageStats();
    			
    	List<User> users = twitterManager.getRetweetersIds(tweetid,numRTUsers);
    	for (User user : users) {
			String language = user.getLang().substring(0,2);
			System.out.println(user.getScreenName() + " " + language);
			stats.addLanguage(language);
			
			List<Status> statuses = twitterManager.getUserTimeline(user.getId(), numStatuses);
			twitterManager.countUserStatusList(stats, statuses);

			for (Status status : statuses) {
				System.out.println(status.getText());
//				System.out.println(result);
			}
		}

    	
    	stats.printLanguageStats();
    	System.out.println("-----------");
    	stats.printThemeStats();
    	System.out.println("-----------");    	
    	stats.printEntityStats();
    	System.out.println("-----------");
    	
    			
    }       
}
