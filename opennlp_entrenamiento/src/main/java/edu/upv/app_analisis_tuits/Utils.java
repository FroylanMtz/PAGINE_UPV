package edu.upv.app_analisis_tuits;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import opennlp.tools.util.normalizer.EmojiCharSequenceNormalizer;
import opennlp.tools.util.normalizer.TwitterCharSequenceNormalizer;
import opennlp.tools.util.normalizer.UrlCharSequenceNormalizer;

public class Utils {
	
	public EmojiCharSequenceNormalizer emoji = new EmojiCharSequenceNormalizer();
    public TwitterCharSequenceNormalizer twitter_normalizer = new TwitterCharSequenceNormalizer();
    public UrlCharSequenceNormalizer url_normalizer = new  UrlCharSequenceNormalizer();
    
    
	Date convertirFecha(String fecha_string) throws java.text.ParseException
    {
		
    	DateFormat dateFormat = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    	
	    Date fecha = dateFormat.parse(fecha_string);
	    
	    return fecha;
    }

}
