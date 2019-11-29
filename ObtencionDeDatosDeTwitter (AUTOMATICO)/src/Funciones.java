//Librerias de POI requeridas
import java.io.BufferedReader;
import org.apache.poi.hwpf.extractor.WordExtractor; //Para leer un .doc
import org.apache.poi.xwpf.extractor.XWPFWordExtractor; //Para leer un XWPF Document
import org.apache.poi.xwpf.usermodel.XWPFDocument; //Para instanciar un .docx

//Librerias de JAVA requeridas
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Alejandro
 */
public class Funciones {
	
	boolean convertirMinuscula=true;//Concierte todas las letras mayusculas en minusculas
	boolean espaciosDeMas=true;//elimina todos los espacios que contiene de mas el texro dejando unicamente separaciones de 1 espacio
	boolean eleiminarCaracteresEspeciales=true;//elimina del texto todos los caracteres que no sean letras o numeros    
	boolean imprimirPalabrasRepetidas=true;
	boolean EliminarEspaciosPorCompleto=false;//Es util al separar por palabras ya que elimina completamente los espacios
	boolean mensajes=true;//Activa lo mensajes que se muestran al llamar las funciones
	String separador=" |\\.|\n";//Separar por palabaras

	//String separador="\n";//Separar por sentencias
    public Funciones() 
    {
        
    }

    public String extraerWord(String dir) throws IOException 
    {
        File archivoDocx = new File(dir);//
         boolean ver=false;
         String extension="";
        InputStream entradaArch2 = null;
        try 
        {
            entradaArch2 = new FileInputStream(archivoDocx);
        } 
        catch (Exception ex) 
        {
            //Manejar Excepcion IO y FileNotFound
        }
        
        
       
        for (int i = 0; i < dir.length(); i++) {
             if(ver)
                extension+=dir.charAt(i);
            if(dir.charAt(i)=='.')
                ver=true;
           
        }
        System.out.println(extension);
            if(extension.equals("doc"))
            {
                System.out.println("por aqui");
                return leerDoc(entradaArch2);
            }
            else
                return leerDocx(entradaArch2);
    }

    private static String leerDoc(InputStream doc) throws IOException 
    {
        //Creamos el extractor pasandole el stream
        WordExtractor we = new WordExtractor(doc);
        //Regresamos lo leÃƒÂ­do		
        return we.getText();
    }
     
       
    private static String leerDocx(InputStream docx) throws IOException 
    {
        //Se crea un documento que la POI entiende pasandole el stream
        //instanciamos el obj para extraer contenido pasando el documento
        XWPFWordExtractor xwpf_we = new XWPFWordExtractor(new XWPFDocument(docx));
        return xwpf_we.getText();
    }
    
    public String extraerTxt(String archivo) throws FileNotFoundException, IOException 
    {
        
        String cadena;
        String output="";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) 
        {
            output+=cadena+"\n";
        }
        b.close();
        return output;
    } 
    
    public String extraerPDf(String dir) throws IOException
    {
         File file = new File(dir); 
       PDDocument document = PDDocument.load(file);

      //Instantiate PDFTextStripper class
      PDFTextStripper pdfStripper = new PDFTextStripper();

      //Retrieving text from PDF document
      String text = pdfStripper.getText(document);
      

      //Closing the document
      document.close();
      return text;
    }
    
    public List extraerCsv(String archivo) throws FileNotFoundException, IOException 
    {
        if(mensajes)
            System.out.println("Obteniendo informachin del archivo...");
        String cadena;
        List<String> lista = new ArrayList<>();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) 
        {
            lista.add(cadena); 
        }
        b.close();
        return lista;
    } 
     
    public void crearArchivo(List datos , String nombre) throws IOException 
    {
        String ruta = nombre;
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(mensajes)    
            System.out.println("Creando archivo...");
        bw = new BufferedWriter(new FileWriter(archivo));
        
             for (int j = 0; j < datos.size(); j++)
            {
                String linea=datos.get(j).toString();
                linea = linea.replaceAll(" +", " ").trim();//asegura que no pase ningun espacio en blanco
                if (linea.length()>0)
                    if (!isNumeric(linea))//Se asegura de que no entren numeros
                        bw.write(linea+"\n");
            }
        bw.close();
    }
    
    public void unirScv(String dir,String dir2,String dir3) throws IOException
    {
        List csv1=extraerCsv(dir);
        List csv2=extraerCsv(dir2);
        if(mensajes)
            System.out.println("Uniendo archivos...");
        System.out.println("Archivo CSV1 con :"+csv1.size()+" palabras");
        System.out.println("Archivo CSV2 con :"+csv2.size()+" palabras");
        csv1.addAll(csv2);
        //csv1=EliminaElementosRepetidos(csv1);
        crearArchivo(csv1,dir3);
        System.out.println("Archivo CSV3 con :"+csv1.size()+" palabras");
    }
     
    public  String limpiar(String input) 
    {
        if(mensajes)
            System.out.println("Limpiando caracteres especiales de la cadena...");
        // Cadena de caracteres original a sustituir.
        String original = "Ã¡Ã Ã¤Ã©Ã¨Ã«Ã­Ã¬Ã¯Ã³Ã²Ã¶ÃºÃ¹uÃ�Ã€Ã„Ã‰ÃˆÃ‹Ã�ÃŒÃ�Ã“Ã’Ã–ÃšÃ™ÃœÃ‘Ã§Ã‡-~,%()[]{}Â¿?Â¡!:;'â€˜â€™.*â€“â€�â€¢â€œÂ·&\\\"+â‚¬â€”Â»ÂªÂº_/=<>$#ï£§â€¦Ã¸Â«Ã¸|Â´Â°"; 
        // Cadena de caracteres ASCII que reemplazarÃƒÂ¡n los originales. 
        String ascii =    "aaaeeeiiiooouuuAAAEEEIIIOOOUUUNcC                                                  ";
        //System.out.println("originial "+original.length());
        //System.out.println("ascii "+ascii.length());
        String output = input;   
        if(eleiminarCaracteresEspeciales)
            for (int i = 0; i < original.length(); i++) 
                output = output.replace(original.charAt(i), ascii.charAt(i));
        if (espaciosDeMas)
        output = output.replaceAll(" +", " ").trim();
        if(convertirMinuscula)
                output=output.toLowerCase();
        return output;
    }
    

    public List Separar(String texto) 
    {   
       if(mensajes)
            System.out.println("Separando cadena...");
        List<String> lista = new ArrayList<>();
        List<String> lista2 = new ArrayList<>();
        String []arSep=texto.split(separador);
        
        for (int i = 0; i < arSep.length; i++) 
            lista.add(arSep[i]);     
        for (int i = 0; i < lista.size(); i++)
        {
            String linea=lista.get(i).toString();
            linea = linea.replaceAll(" +", " ").trim();    
            if (linea.length()>0)
                if (!isNumeric(linea))
                {
                   if(EliminarEspaciosPorCompleto)
                   {
                        while (!linea.equals(linea.replace(" ", "")))
                        linea = linea.replace(" ", "");
                   }
                    lista2.add(linea);
                }
        }
          return lista2;
    }
    
    public static boolean isNumeric(String cadena) 
    {
        boolean resultado;
        try 
        {
            Integer.parseInt(cadena);
            resultado = true;
        } 
        catch (NumberFormatException excepcion) 
        {
            resultado = false;
        }
        return resultado;
    }
    
  /*  public List EliminaElementosRepetidos(List arraycar) throws IOException//Es util cuando se separa por palabras
    {
        if(mensajes)
            System.out.println("Eliminando elementos repetidos de la lista...");
        List<String> lista = new ArrayList<>();
	for(int i=0;i<arraycar.size();i++)
            for(int j=0;j<arraycar.size()-1;j++)
		if(i!=j)
                    if(!"".equals(" "))
                        if(arraycar.get(i).equals(arraycar.get(j)))  
                        {
                            if(imprimirPalabrasRepetidas)
                                System.out.println("Palabra repetida: "+arraycar.get(i));
                            arraycar.set(i," ");
                        }
	int n=arraycar.size();
        if(mensajes) 
            System.out.println("Generando nueva lista sin repetidos...");
	for (int k=0;k<=n-1;k++)
            if(!" ".equals(arraycar.get(k)))
                lista.add(arraycar.get(k).toString());
        return lista;
    }*/
    
     public List EliminarElementosRepetidos(List lista)
    {
      if(mensajes)
            System.out.println("Eliminando elementos repetidos de la lista...");
        
        List<String> palabrasrep = new ArrayList<>();
            Set<String> cuenta = new HashSet<String>(lista);
         
        for (String key : cuenta) {
           palabrasrep.add(key);
        }
        palabrasrep = ordenar(palabrasrep);
        return palabrasrep;
    }
    
    public List ordenar(List lista)//Es util cuando se separa por palabras
    {
        if(mensajes)
            System.out.println("Ordenando lista...");
        Collections.sort(lista);
        return lista;
    }
    
    public String List2String(List lista,char delimitador)
    {
        if(mensajes)
            System.out.println("Generando string...");
        String res="";
        for (int i = 0; i < lista.size(); i++) {
            res+=lista.get(i).toString()+delimitador+"\n";
        }
        return res;
    }

    public List encontrarDiferentes(List lista1, List lista2)//toma 2 listas y regresa una con las palabras diferentes encontradas entre las 2
    {
        List<String> diferentes = new ArrayList<>();
        boolean verificar;
         if(mensajes)
            System.out.println("Encontradno palabras diferentes...");
        for (int i = 0; i < lista2.size(); i++) 
        {
            verificar=false;
            for (int j = 0; j < lista1.size(); j++) 
            {
                if(lista2.get(i).toString().equals(lista1.get(j).toString()))
                    verificar=true;
            }
            if(!verificar)
            {
                diferentes.add(lista2.get(i).toString());
                System.out.println("Palabra diferente: "+lista2.get(i).toString());
            }
        }
        return diferentes;
    }
    
    public boolean isAlpha(String name) //veridica si el string ingresado tiene solo valores alfabeticos
    {
    return name.matches("[a-zA-Z]+");
}
    
    public List dejarSoloLetras(List lista)//elimina todos los valores no alfabeticos de las palabras
    {
        List<String> limpia = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) 
        {
            if(!isAlpha(lista.get(i).toString()))
            {
                String cadena=lista.get(i).toString();
                for (int j = 0; j < cadena.length(); j++) 
                {
                    if (!isAlpha(cadena.charAt(j)+"")) {
                        StringBuilder ncadena= new StringBuilder(cadena);
                        ncadena.setCharAt(j,' ');
                        cadena=ncadena.toString();
                        
                    }
                }
                while (!cadena.equals(cadena.replace(" ", "")))
                        cadena = cadena.replace(" ", "");
                System.out.println(cadena);
                if(!cadena.equals(" "))
                    limpia.add(cadena);
            }
            else
            {
                limpia.add(lista.get(i).toString());
            }
        }
        return limpia;
    }
    
 
   public void contarPalabrasRepetidas(List lista) throws IOException
   {
        System.out.println("Contando palabras repetidas...");
        List<String> palabrasrep = new ArrayList<>();
         Set<String> cuenta = new HashSet<String>(lista);
        for (String key : cuenta) 
           palabrasrep.add(key + "," + Collections.frequency(lista, key));
        palabrasrep=ordenar(palabrasrep);
        crearArchivo(palabrasrep,"C:\\Users\\Alejandro\\Desktop\\archivo2.csv");
   }
   
   
   public void limpiarTweet(String tweet)
   {
	   System.out.println(tweet);
   }
   
}
 