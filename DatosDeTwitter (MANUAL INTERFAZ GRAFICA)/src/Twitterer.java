import twitter4j.GeoLocation;       // jar found at http://twitter4j.org/en/index.html
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterException;
import twitter4j.User;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class Twitterer
   {
      private Twitter twitter;
      private PrintStream consolePrint;
      private List<Status> statuses;
      private User usuario;
     
      public Twitterer(PrintStream console)
      {
         // Makes an instance of Twitter - this is re-useable and thread safe.
         // Connects to Twitter and performs authorizations.
         twitter = TwitterFactory.getSingleton(); 
         consolePrint = console;
         statuses = new ArrayList<Status>();
      }
   
      public void queryHandle(String handle) throws TwitterException, IOException
      {
    	  statuses.clear();
    	  fetchTweets(handle);
    	  int counter = statuses.size();
    	  
    	  System.out.println("Usuario que tweetió: " + usuario.getName() );
    	  System.out.println("Localizacion del usuario: " + usuario.getLocation() );
    	  System.out.println("Descripcion: " + usuario.getDescription() );
    	  System.out.println("Geolocalizacion " + usuario.isGeoEnabled() );
    	  
    	  while(counter > 0)
    	  {
    		  counter --;
    		  System.out.println("Tweet #"+counter + ": "+ statuses.get(counter).getText());
    		  
    	  }
    	  
    	  
    	  
      }
   	
      private void fetchTweets(String handle) throws TwitterException, IOException
      {
    	  Paging page = new Paging(1, 1);
    	  int p = 1;
    	  while(p <= 1)
    	  {
    		  page.setPage(p);
    		  statuses.addAll( twitter.getUserTimeline(handle, page) );  
    		  p++;
    	  }
    	  
    	  usuario = twitter.showUser(handle);
    	  
      }   
    
      public List<Status> saQuery (String searchTerm, double radio, double longitud, double latitud, int totalTweets)
      {
    	  
    	  Query query = new Query(searchTerm);
    	  query.setCount(totalTweets);
    	  query.setGeoCode( new GeoLocation(longitud , latitud ), radio, Query.KILOMETERS );
    	  query.setSince("2018-09-03");
    	  	 
    	  try {
    		 
    		  QueryResult result = twitter.search(query);
    		  int counter = 0;
    		  
    		  for(Status tweet: result.getTweets())
    		  {
    			  counter ++;
    			  statuses.add(tweet);
    		  }
    		  
    	  }catch(TwitterException e) {
    		  e.printStackTrace();
    	  }
    	  
    	  System.out.println();
    	  
    	  return statuses;
    	  
    	  
    	  
      }
   
   }  




