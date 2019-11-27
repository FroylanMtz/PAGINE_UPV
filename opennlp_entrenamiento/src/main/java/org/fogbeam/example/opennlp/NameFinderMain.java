package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class NameFinderMain
{
	/**
	 * @param args
	 */
	public static void main( String[] args ) throws Exception
	{
		InputStream modelIn = new FileInputStream( "models/es-ner-person.bin" );
		//InputStream modelIn = new FileInputStream( "models/es-pos-maxent.bin" );
		
		try
		{
			TokenNameFinderModel model = new TokenNameFinderModel( modelIn );
		
			NameFinderME nameFinder = new NameFinderME(model);
			
			//<START:person> José Luis Perales <END>, es un gran cantante.
			String[] tokens = {"José", "Luis", "Perales", "es", "un", "gran", "cantante"};
			
			
			Span[] names = nameFinder.find( tokens );
		
			for( Span ns : names )
			{
				System.out.println( "ns: " + ns.toString() );
			}
		
			nameFinder.clearAdaptiveData();
			
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			if( modelIn != null )
			{
				try
				{
					modelIn.close();
				}
				catch( IOException e )
				{
				}
			}
		}
		
		
		System.out.println( "acabado" );
	}
}

/*			
				StringBuilder sb = new StringBuilder();
				for( int i = ns.getStart(); i < ns.getEnd(); i++ )
				{
					sb.append( tokens[i] + " " );
				}
				
				System.out.println( "The name is: " + sb.toString() );
*/