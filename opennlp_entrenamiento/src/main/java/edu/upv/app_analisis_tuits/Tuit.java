package edu.upv.app_analisis_tuits;

import java.util.Date;

public class Tuit {
	
	private int numero = 0;
	private Date fecha = new Date();
	private String tuit = "";
	private String autor = "";
	
	
	public Tuit()
	{
		
	}
	
	public Tuit(int numero, Date fecha, String tuit, String autor)
	{
		this.numero = numero;
		this.fecha = fecha;
		this.tuit = tuit;
		this.autor = autor;
	}
	
	public int getNumero()
	{
		return numero;
	}
	
	public void setNumero(int numero) 
	{
		this.numero = numero;
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
	
	public String getTuit()
	{
		return tuit;
	}
	
	public void setTuit(String tuit)
	{
		this.tuit = tuit;
	}
	
	public String getAutor()
	{
		return autor;
	}
	
	public void setAutor(String autor)
	{
		this.autor = autor;
	}

}
