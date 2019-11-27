package com.upv.uima.UIMA_APP_UPV;

import java.io.IOException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import opennlp.uima.chunker.Chunker;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException, InvalidXMLException, ResourceInitializationException, AnalysisEngineProcessException
    {
    	/*XMLInputSource in = new XMLInputSource("RoomNumberAnnotator.xml");
    	ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);
    	// Creacion del motor de analisis
    	AnalysisEngine ae = 
    	    UIMAFramework.produceAnalysisEngine(specifier);
    	// Create a JCas, given an Analysis Engine (ae)
    	JCas jcas = ae.newJCas();
    	// Analyze a document
    	String documento = "August 26, 2003\r\n" + 
    			"UIMA 101 GN-K35 - The New UIMA Introduction \r\n" + 
    			"(Hands-on  GN-K35 Tutorial)\r\n" + 
    			"9:00AM-5:00PM in HAW GN-K35\n";
    	jcas.setDocumentText(documento);
    	ae.process(jcas);
    	for (RoomNumber rn : jcas.getAnnotationIndex().select(RoomNumber.class)) {
    		//do something
    		System.out.println("======= Anotacion ========");
    		System.out.println(rn);
		}*/
    }
}
