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
      
      int total_tweets;
      private int numero_de_busquedas = 0;
      
      Query query = new Query();
     
      public Twitterer(PrintStream console)
      {
         // Makes an instance of Twitter - this is re-useable and thread safe.
         // Connects to Twitter and performs authorizations.
         twitter = TwitterFactory.getSingleton(); 
         consolePrint = console;
         statuses = new ArrayList<Status>();
      }
   
   
      //Obtienen los twitts de un usuario en particular pasado como parametro
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
   	
     /** 
      * This helper method fetches the most recent 2,000 tweets of a particular user's handle and 
      * stores them in an arrayList of Status objects.  Populates statuses.
      * @param String  the Twitter handle (username) without the @sign
      */
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
    
      // Este metodo es usado para obtener los datos de tweets en base a una busqueda por 
      // un termino, y pasandole un radio, una longitud y una latidud, asi como el total de twitts que se desean obtener
      public List<Status> saQuery (String searchTerm, double radio, double longitud, double latitud, String fecha, String fecha_fin)
      {
    	  long max_id = 0;
    	  int t = 0;
    	  total_tweets = 0;
    	  statuses.clear();

    	  query = new Query(searchTerm + " +exclude:retweets");
    	  query.setCount(100);
    	  query.setGeoCode( new GeoLocation(longitud , latitud ), radio, Query.KILOMETERS );
    	  query.setSince(fecha);
    	  query.setUntil(fecha_fin);
    	  
    	  while(numero_de_busquedas < 180)
    	  {
    		  numero_de_busquedas++;
    		  t = 0;
    		  
        	  try {
        		  QueryResult result = twitter.search(query);
        		  
        		  //Maximo 100 tweets aqui
        		  for(Status tweet: result.getTweets())
        		  {
        			  statuses.add(tweet);
        			  max_id = tweet.getId(); 
        			  t++;
        		  }
        		  
        		  total_tweets = total_tweets + t;
        		  
        		  if( t < 100 ) {
        			  return statuses;
        		  }
        			  
        		  
        		  query.setMaxId(max_id);
        		  
        		  
        		  
        	  }catch(TwitterException e) {
        		  
        		  e.printStackTrace();
        		  
        	  }
    	  }
    	      	  
    	  return statuses;
    	  
    	  
    	  
      }
      
      public int obtenerNumeroDeTweets()
      {
    	  return total_tweets;
      }
      
   
   }  







