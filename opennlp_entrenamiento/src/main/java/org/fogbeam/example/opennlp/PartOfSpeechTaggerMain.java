
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;


public class PartOfSpeechTaggerMain
{
	public static void main( String[] args )
	{
		InputStream modelIn = null;
		try
		{
			//Modelo encontrado en linea
			//modelIn = new FileInputStream( "models/es-pos-maxent-universal.bin" );
			
			//Modelo encontrado en linea entrenado con 8000 ejemplos
			modelIn = new FileInputStream( "models/pos-espaniol-15000.model" );
			
			//Modelo encontrado en linea entrenado con 15000 ejemplos
			//modelIn = new FileInputStream( "models/pos-espaniol-8000.model" );
			
			POSModel model = new POSModel( modelIn );
			
			POSTaggerME tagger = new POSTaggerME(model);
			
			String sent[] = new String[]{"Mi", "papá", "cumple", "hoy", "66", "años", "igualito",
                    "que", "AMLO", ".", "hahaha", "random", "."};
			
			String tags[] = tagger.tag(sent);
			
			double probs[] = tagger.probs();
			
			for( int i = 0; i < sent.length; i++ )
			{
				System.out.println( "Token [" + sent[i] + "] has POS [" + tags[i] + "] with probability = " + probs[i] );
			}
		}
		catch( IOException e )
		{
			// Model loading failed, handle the error
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
		
		System.out.println( "done" );
	
	}
}
