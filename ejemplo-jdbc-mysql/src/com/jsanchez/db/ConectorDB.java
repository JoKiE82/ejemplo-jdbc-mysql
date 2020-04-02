package com.jsanchez.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectorDB 
{
	private static final String _DB 		= "facturacion";
	private static final String _URL 		= "jdbc:mysql://localhost/" + _DB;   
    private static final String _USER 		= "root";
    private static final String _PASS 		= "";    
    private static Connection myConectorDB 	= null;
    
    private ConectorDB() { } // El arte del silencio (Singlenton) para la conexión.
    
    /**
     * Contexto de conexión a la BBDD.
     * @return Retorna un objeto de tipo java.sql.Connection
     */
    public static Connection getInstance() throws java.sql.SQLException
    {
    	return (myConectorDB == null) ? DriverManager.getConnection(_URL, _USER, _PASS) : myConectorDB;
    }
}
