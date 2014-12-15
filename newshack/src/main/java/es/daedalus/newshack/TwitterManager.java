package es.daedalus.newshack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

//import org.apache.http.client.ClientProtocolException;


//import es.daedalus.textalytics.sempub.Result;
//import es.daedalus.textalytics.sempub.SempubClient;
//import es.daedalus.textalytics.sempub.TextalyticsClientException;
//import es.daedalus.textalytics.sempub.domain.Document.Language;
//import es.daedalus.textalytics.sempub.domain.Entity;
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
//	SempubClient textalytics;

	
	public TwitterManager() {
    	this.twitter = TwitterFactory.getSingleton();
//    	this.textalytics = new SempubClient();
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
//	
//	public List<Entity> analyzeUserStatus(List<Status> statuses) {
//		Status s = statuses.get(0);
//		Result result = null;
//		
//		try {
//			String language = s.getLang().substring(0, 2);
//			if (language.equals("es") || language.equals("en"))
//
//			result = this.textalytics.analyze(s.getText(),Language.valueOf(language));
//			
//			
//		} catch (TextalyticsClientException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//
//		if (result != null)
//			return result.getEntities();
//		else 
//			return new ArrayList<Entity>();
//	}
	
	
	

    public static void main( String[] args ) throws TwitterException
    {

    	TwitterManager twitterManager = new TwitterManager();
    	
 
//    	String bbcbreaking = "BBCBreaking";
//    	List<Status> statuses = twitterManager.getUserTimeline(bbcbreaking);
//    	for (Status status : statuses) {
//			System.out.println(status.getId() + " " + status.getText() );	
//		}

//    	Status first = statuses.get(0);

    	int numRTUsers = 1;
    	int numStatuses = 10;
    	
    	
    	String id = "544302821450207233";
    	long tweetid = Long.valueOf(id);
    	LanguageStats stats = new LanguageStats();
    			
    	List<User> users = twitterManager.getRetweetersIds(tweetid,numRTUsers);  
    	for (User user : users) {
			String language = user.getLang().substring(0,2);
			System.out.println(user.getScreenName() + " " + language);
			stats.addLanguage(language);
			
			List<Status> statuses = twitterManager.getUserTimeline(user.getId(), numStatuses);
//			for (Status status : statuses) {
//				System.out.println(status.getText());
//			}
			System.out.println("--------------------------------------------");
		}

    	
    	stats.printLanguageStats();
    }       
}
