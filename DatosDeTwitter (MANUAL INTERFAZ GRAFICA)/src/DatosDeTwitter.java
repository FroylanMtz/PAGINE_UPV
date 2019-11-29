
import twitter4j.Status;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class DatosDeTwitter {

	public static void main(String[] args) {
		
		//Creacion de la ventana
		MarcoTexto mimarco = new MarcoTexto();
		
		//Se le configura un comportamiento para que cierre la aplicacion al darle clic en la tachita
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//String ejemplo_twitter = "Los tabasqueños, el presidente de México, Andrés Manuel López Obrador  ?? y la presidenta del Senado, Mónica Fernández Balboa, entregaron la Medalla Belisario Domínguez a Rosario Ibarra de Piedra. Recibió su hija Rosario Piedra Ibarra, en su representación."
		
	}
}

//Clase que define la ventana y sus propiedades
class MarcoTexto extends JFrame{
	
	public MarcoTexto() {
		
		setTitle("Recuperacion de datos de Twitter");
		
		setBounds(300, 300, 800, 450);
		
		//Se instancia la lamina en la ventana
		LaminaTexto milamina = new LaminaTexto();
		
		//Se agrega la lamina a la ventana
		add(milamina);
		
		setVisible(true);
		
	}
}

//Clase que define la lamina y todos sus propiedades
class LaminaTexto extends JPanel{
	
	public LaminaTexto() {
		
		//Se establece por defecto estilo BorderLayout en la lamina padre  
		setLayout(new BorderLayout());
		
		//Código para crear un panel hijo que irá en el norte del otro panel
		JPanel zonanorte = new JPanel();
		
		//Se le establece el tipo en el que los elementos se centran
		zonanorte.setLayout(new FlowLayout()); 
		
		//Campo para la busqueda
		buqueda_tf = new JTextField(10);
		
		//Campo para colocar el radio a la redonda
		radio_tf = new JTextField(10);
		
		//Campo para colocar el numero de tweets
		numeroTweets_tf = new JTextField(10);
	
		//Creación del botón y asociar el evento a dicho boton
		miboton = new JButton("Buscar");
		DameTexto mievento = new DameTexto(); //Instancia de la clase interna
		miboton.addActionListener(mievento);
		
		//combo box que define las cuidades donde se puede buscar
		micombo = new JComboBox();
		micombo.setEditable(true);
		micombo.addItem("Ciudad Victoria");
		micombo.addItem("Monterrey");
		micombo.addItem("Guadalajara");
		micombo.addItem("Ciudad de Mexico");

		//Agregado de todos los elementos
		zonanorte.add(new JLabel("Tema: "));
		zonanorte.add(buqueda_tf);
		
		zonanorte.add(new JLabel("Ciudad: "));
		zonanorte.add(micombo);
		
		zonanorte.add(new JLabel("Radio: "));
		zonanorte.add(radio_tf);
		
		zonanorte.add(new JLabel("No. Tweets: "));
		zonanorte.add(numeroTweets_tf);
		
		zonanorte.add(miboton);
		
		//Agregar la lamina hija al norte de la lamina padre
		add(zonanorte, BorderLayout.NORTH);
		
		//Código para colocar un Text area la centro de la lamina padre
		JPanel zonacentro = new JPanel();
		zonacentro.setLayout(new FlowLayout());
		
		miarea = new JTextArea(8, 20);
		
		JScrollPane laminaBarras = new JScrollPane(miarea);
		
		miarea.setLineWrap(true);
		
		add( laminaBarras, BorderLayout.CENTER );
		
		//Código para colocar la opcion de guardado en la parte de abajo
		zonazur = new JPanel();
		zonazur.setLayout(new FlowLayout());
		
		//Boton para identificar la ruta de guardado
		go = new JButton("Seleccionar carpeta de destino");
		go.addActionListener(mievento);
		
		//Texto donde se muestra la ruta a guardar
		ruta_lbl = new JLabel();
		ruta_lbl.setText("Nada seleccionado");
		
		//Boton para guardar el archivo con la ruta especificada
		guardar_btn = new JButton("Guardar");
		guardar_btn.addActionListener(mievento);
		
		
		zonazur.add( new JLabel("Ruta: ") );
		zonazur.add(ruta_lbl);
		zonazur.add(go);
		zonazur.add(guardar_btn);
		
		//DemoJFileChooser panel = new DemoJFileChooser();
		add(zonazur, BorderLayout.SOUTH);
		
		
	}
	
	public Dimension getPreferredSize(){
  		return new Dimension(100, 50);
    }
	
	//Clase interna que se encarga de la implementacion del listener que es disparado al presionar el boton
	private class DameTexto implements ActionListener{
		
		double longitud = 0.0;
		double latitud = 0.0;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object control = e.getSource();

			if( control.equals(miboton) ) {
				
				//System.out.println("Se ha presionado buscar");
				String termino = buqueda_tf.getText().trim();
				int radio_busqueda = Integer.parseInt( radio_tf.getText() );
				String ciudad = (String) micombo.getSelectedItem();
				int numero_tweets = Integer.parseInt( numeroTweets_tf.getText() );
				
				if(ciudad.compareTo("Ciudad Victoria") == 0) {
					latitud  	= 23.736916;
					longitud 	= -99.141113;
				}else if( ciudad.compareTo("Monterrey") == 0 ) {
					latitud 	= 25.6714;
					longitud  	= -100.309;
				}else if( ciudad.compareTo("Guadalajara") == 0) {
					latitud 	= 20.6736;
					longitud  	= -103.344;
				}else if(ciudad.compareTo("Ciudad de Mexico") == 0) {
					latitud 	= 19.4978;
					longitud	= -99.1269;
				}
				
				Twitterer twitter = new Twitterer(consolePrint);
				ts = twitter.saQuery(termino, radio_busqueda, latitud, longitud, numero_tweets);
				
				int t = 1;
				
				String tws = "";
	  		    for(Status tweet: ts)
		  		{
		  			  miarea.setText( tweet.getText() );
		  			  tws = tws + "\n Fecha Creación: " + tweet.getCreatedAt();
		  			  tws = tws + "\n Caldito " + tweet.getSource();
		  			  tws = tws + "\n Truncado" + tweet.isTruncated();
		  			  tws = tws + "\n Id " + tweet.getId();
		  			  tws = tws + "\n Tweet #" + t;
		  			  tws = tws + "\n Autor: @" + tweet.getUser().getName();
		  			  tws = tws + "\n Tweet: " + tweet.getText();
		  			  tws = tws + "\n";
		  			  t++;
		  			  
		  		}
	  		    
	  		    miarea.setText(tws);
	  		    
			}
			
  		    
  		    // Esto es para seleccionar la ruta en donde se guardará el archivo
  		    if(control.equals(go)){
	   			//System.out.println("Se ha pulsado el botón de seleccionar la carpeta de destino");
	   			
	   			int result;
			    chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle(choosertitle);
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(zonazur) == JFileChooser.APPROVE_OPTION) {
			    	ruta_lbl.setText("" + chooser.getSelectedFile()+"\\");
			    	
			    	directorio = ruta_lbl.getText();
			    } else {
			    	ruta_lbl.setText("Nada seleccionado");
			    }
	   		}
  		    
  		    //Esto identifica que se presiono el boton de guardar
  		    if(control.equals(guardar_btn)) 
  		    {
  		    	
  		    	//System.out.println("Se presiono el boton de guardar");
  		    	
  		    	int t = 1;
  		    	for(Status tweet: ts)
		  		{
		  			  JSONObject tweetDetalles = new JSONObject();
		  			  tweetDetalles.put("Numero", t);
		  			  tweetDetalles.put("Autor", tweet.getUser().getName() );
		  			  tweetDetalles.put("Tweet", tweet.getText() );
		  			  tweets_json.add(tweetDetalles);
		  			  t++; 
			  	      
		  		}
  		    	
  		    	
  		    	//Write JSON file
  		    	String ruta;
	  			String nombre_archivo = "";
	  			String f = "" + java.time.LocalDate.now();
	  			String[] fecha = f.split("-");
	  			String h = "" + java.time.LocalTime.now();
	  			String[] hora = h.split(":");
	  			hora[2] = hora[2].substring(0,2);
	  			nombre_archivo = "BusquedaTweets_"+fecha[2]+"_"+fecha[1]+"_"+fecha[0]+"_"+hora[0]+"_"+hora[1]+"_"+hora[2];
	  			
	  			ruta = directorio + nombre_archivo;
		    	
		  	    try (FileWriter file = new FileWriter(ruta)) {
		  	 
		  	        file.write(tweets_json.toJSONString());
		  	        file.flush();
		  	        file.close();
		  	 
		  	    } catch (IOException ev) {
		  	        	
		  	        ev.printStackTrace();
		  	            
		  	    }
  		    	
  		    }
			
		}
	}
	
	JPanel zonazur;
	private JTextField buqueda_tf;
	private JTextField radio_tf;
	private JTextField numeroTweets_tf;
	private JLabel resultado;
	private JTextArea miarea;
	private JComboBox micombo;
	private static PrintStream consolePrint;
	JButton miboton;
	JButton go;
   	JFileChooser chooser;
   	String directorio  = "";
   	String choosertitle;
   	private JLabel ruta_lbl;
   	JButton guardar_btn;	
   	JSONArray tweets_json = new JSONArray();
   	List<Status> ts;
}