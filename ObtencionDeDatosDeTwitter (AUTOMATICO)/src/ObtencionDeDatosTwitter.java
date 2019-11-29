import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import twitter4j.Status;

public class ObtencionDeDatosTwitter {
	
	public static void main(String[] args) throws IOException
	{
		
		Funciones fc = new Funciones();
		List<Status> ts;
		String archivo = "C:\\Users\\Estancia\\Documents\\Codigos\\ObtencionDeDatosDeTwitter\\src\\terminos_de_busqueda.txt";
		List<String> palabras = fc.extraerCsv(archivo);
		PrintStream consolePrint = null;
		Twitterer twitter = new Twitterer(consolePrint);
		Calendar fecha_busqueda = Calendar.getInstance();
		String dir = "";

		String fecha = "";
		String fecha_fin = "";
		
		fecha_busqueda.add(Calendar.DATE, -1);
		fecha = fecha_busqueda.get(Calendar.YEAR) +"-0" + (fecha_busqueda.get(Calendar.MONTH) + 1) + "-" + fecha_busqueda.get(Calendar.DATE);
		fecha_busqueda.add(Calendar.DATE, +1);
		fecha_fin = fecha_busqueda.get(Calendar.YEAR) +"-0" + (fecha_busqueda.get(Calendar.MONTH) + 1) + "-" + fecha_busqueda.get(Calendar.DATE);

		System.out.println("Fehca inicio " + fecha);
		System.out.println("Fecha fin " + fecha_fin);
		for(String p: palabras) {
			
			String [] partes = p.split(",");
			int numero_tweets = 0;
			
			
			if( partes[0].compareTo("directorio") == 0 )  {
				
				dir = partes[1];
				
			} else {
				
				int radio_busqueda = Integer.parseInt(partes[3]);
				double latitud = Double.parseDouble(partes[1]);
				double longitud = Double.parseDouble(partes[2]);
				String termino = partes[0];
								
				File directorio = new File(dir + termino);
				boolean existeDirectorio = directorio.exists();
				
				if (!existeDirectorio) {
					//Codigo para traer tuits de una semana
					directorio.mkdir(); //Se crea el directorio
				}
				
				//Codigo para traer tuits del ultimo dia
				ts = twitter.saQuery(termino, radio_busqueda, latitud, longitud, fecha, fecha_fin);
				numero_tweets = twitter.obtenerNumeroDeTweets();
				guardarArchivo(termino, dir + "\\" + termino, ts);
				guardarResultados(termino, numero_tweets, dir, fecha_fin);
				System.out.println("Total de Tweets para el tema " + termino + ": " + numero_tweets);
			
				
			}
			
		}
		
	}
	
	public static void guardarArchivo(String termino,String dir, List<Status> ts)
	{
		JSONArray tweets_json = new JSONArray();
		int t = 1;
	    
		for(Status tweet: ts)
  		{
  			  JSONObject tweetDetalles = new JSONObject();
  			  tweetDetalles.put("Numero", t);
  			  tweetDetalles.put("Fecha", tweet.getCreatedAt());
  			  tweetDetalles.put("Autor", tweet.getUser().getName() );
  			  tweetDetalles.put("Tweet", tweet.getText() );
  			  tweets_json.add(tweetDetalles);
  			  t++; 
  		}
	    	
		String ruta;
		String nombre_archivo = "";
		String f = "" + java.time.LocalDate.now();
		String[] fecha = f.split("-");
		String h = "" + java.time.LocalTime.now();
		String[] hora = h.split(":");
		hora[2] = hora[2].substring(0,2);
		nombre_archivo = termino + "_" + fecha[0] + "_" + fecha[1] + "_" + fecha[2] + "_" + hora[0] + "_" + hora[1] + "_" + hora[2];
		
		ruta = dir + "\\" + nombre_archivo;
	
		try (FileWriter file = new FileWriter(ruta)) {
 
  	        file.write(tweets_json.toJSONString());
  	        file.flush();
  	        file.close();
 
    	} catch (IOException ev) {
        	
        	ev.printStackTrace();
            
    	}
		
	}
	
	
	public static void guardarResultados(String termino, int numero_tweets, String dir, String fecha)
	{
		File archivo = new File(dir + "\\conteo.csv");
		
		try {
			
			FileWriter file = new FileWriter(archivo.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(file);
	        bw.write(termino + "," + fecha + "," + numero_tweets + "\n");
	        bw.close();
			
		} catch (IOException ev) {
        	ev.printStackTrace();
    	}
		
	}
	
	

	

}
