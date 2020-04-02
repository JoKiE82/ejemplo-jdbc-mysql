package com.jsanchez.db;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

/**
 * Clase para generar un objeto de tipo Cliente que puede manejar la tabla CLIENTES de la BBDD.
 * @author 		José Joaquín Sánchez Fernández
 * @since 		01/04/2020
 * @version 	1.0
 */
public class Cliente implements Serializable
{
	private int id;
	private String razonSocial;
	private String nif;
	private String direccion;
	private String codigoPostal;
	private String localidad;
	private String provincia;
	private String telefono;
	private String fax;
	private String email;
	private String alias;
	private String observaciones;
	private boolean activo;
	
	private static final long serialVersionUID = -6174296777399477887L;
	
	/**
	 * Cadena para hacer una SELECT * FROM CLIENTES
	 * 
<pre>
CREATE TABLE CLIENTES(
	Id 		INT PRIMARY KEY AUTO_INCREMENT,
	RazonSocial 	VARCHAR(500) NOT NULL,
	NIF 		VARCHAR(9) NOT NULL,
	Direccion 	VARCHAR(500) NOT NULL,
	CodigoPostal 	VARCHAR(5),
	Localidad 	VARCHAR(500),
	Provincia 	VARCHAR(500),
	Telefono 	VARCHAR(20),
	Fax 		VARCHAR(20),
	Email 		VARCHAR(500),
	Alias 		VARCHAR(500),
	Observaciones 	TEXT,
	Activo 		BIT(1)
);
</pre>
	 */
	private static final String selectAll = "SELECT * FROM CLIENTES";
	
	/**
	 * Cadena para hacer una INSERT INTO CLIENTES
	 * 
<pre>
INSERT INTO CLIENTES
(
	RazonSocial, 
	NIF, 
	Direccion, 
	CodigoPostal, 
	Localidad, 
	Provincia, 
	Telefono, 
	Fax, 
	Email, 
	Alias, 
	Observaciones, 
	Activo
) 
VALUES (...);
</pre>
	 */
	private static final String insertInto = 
			"INSERT INTO CLIENTES("
			+ "RazonSocial, "
			+ "NIF, "
			+ "Direccion, "
			+ "CodigoPostal, "
			+ "Localidad, "
			+ "Provincia, "
			+ "Telefono, "
			+ "Fax, "
			+ "Email, "
			+ "Alias, "
			+ "Observaciones, "
			+ "Activo) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * Cadena para hacer una UPDATE CLIENTES SET ... WHERE ID = id
	 * 
<pre>
UPDATE CLIENTES SET 
	RazonSocial = ?, 
	NIF = ?, 
	Direccion = ?, 
	CodigoPostal = ?, 
	Localidad = ?, 
	Provincia = ?, 
	Telefono = ?, 
	Fax = ?, 
	Email = ?, 
	Alias = ?, 
	Observaciones = ?, 
	Activo = ? 
WHERE id = ?
</pre>
	 */
	private static final String updateItem = 
			"UPDATE CLIENTES SET "
			+ "RazonSocial = ?, " 
			+ "NIF = ?, "
			+ "Direccion = ?, " 
			+ "CodigoPostal = ?, " 
			+ "Localidad = ?, " 
			+ "Provincia = ?, " 
			+ "Telefono = ?, " 
			+ "Fax = ?, " 
			+ "Email = ?, " 
			+ "Alias = ?, " 
			+ "Observaciones = ?, " 
			+ "Activo = ? " 
			+ "WHERE id = ?";
	
	private static final String selectItem = "SELECT * FROM CLIENTES WHERE id = ? LIMIT 1";
	
	private static final String deleteItem = "DELETE FROM CLIENTES WHERE id = ?";
	
	/**
	 * Constructor para hacer inserciones dentro de la clase y/o serializar.
	 */
	private Cliente()
	{
		// El arte del silencio
	}
		
	/**
	 * Constructor generado cuya finalidad es para crear un Objeto Cliente para una posterior inserción en la BBDD, si se desea.
	 * @param razonSocial Razón Social del Cliente. VARCHAR(500) NOT NULL
	 * @param nif NIF del Cliente. VARCHAR(9) NOT NULL
	 * @param direccion Dirección del Cliente. VARCHAR(500) NOT NULL
	 * @param codigo Postal Código Postal del Cliente. VARCHAR(5)
	 * @param localidad Localidad del Cliente. VARCHAR(500)
	 * @param provincia Provincia del Cliente. VARCHAR(500)
	 * @param telefono Teléfono del Cliente. VARCHAR(20)
	 * @param fax Fax del Cliente. VARCHAR(20)
	 * @param email Email del Cliente. VARCHAR(500)
	 * @param alias Alias del Cliente. VARCHAR(500)
	 * @param observaciones Observaciones del Cliente. TEXT
	 * @param activo Activo a mostrar en el mantenimiento en el Cliente. BIT(1)
	 */
	public Cliente(String razonSocial, String nif, String direccion, String codigoPostal, String localidad,
			String provincia, String telefono, String fax, String email, String alias, String observaciones,
			boolean activo) 
	{
		this.razonSocial = razonSocial;
		this.nif = nif;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.provincia = provincia;
		this.telefono = telefono;
		this.fax = fax;
		this.email = email;
		this.alias = alias;
		this.observaciones = observaciones;
		this.activo = activo;
	}
	
	/**
	 * Constructor que dado un Id, realiza la consulta en la BBDD para rellenar el Objeto Cliente.
	 * @param id Identificador AUTO_INCREMENT de la tabla CLIENTES de la BBDD.
	 */
	public Cliente(int id)
	{
		try 
		{
			Connection conexion = ConectorDB.getInstance();
			
			PreparedStatement pstmt = conexion.prepareStatement(selectItem);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				this.setId(rs.getInt("id"));
				this.setRazonSocial(rs.getString("razonSocial"));
				this.setNif(rs.getString("nif"));
				this.setDireccion(rs.getString("direccion"));
				this.setCodigoPostal(rs.getString("codigoPostal"));
				this.setLocalidad(rs.getString("localidad"));
				this.setProvincia(rs.getString("provincia"));
				this.setTelefono(rs.getString("telefono"));
				this.setFax(rs.getString("fax"));
				this.setEmail(rs.getString("email"));
				this.setAlias(rs.getString("alias"));
				this.setObservaciones(rs.getString("observaciones"));
				this.setActivo(rs.getBoolean("activo"));				
			}
			else
				throw new SQLException("Registro no encontrado");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", razonSocial=" + razonSocial + ", nif=" + nif + ", direccion=" + direccion
				+ ", codigoPostal=" + codigoPostal + ", localidad=" + localidad + ", provincia=" + provincia
				+ ", telefono=" + telefono + ", fax=" + fax + ", email=" + email + ", alias=" + alias
				+ ", observaciones=" + observaciones + ", activo=" + activo + "]";
	}
	
	/**
	 * <b>Método de instancia</b> para actualizar un objeto Cliente en la BBDD.
	 * @return Booleano confirmando la actualización de un objeto Cliente en la BBDD de la tabla CLIENTES dado un id.
	 */
	public boolean updateCliente()
	{
		try
		{			
			Connection conexion = ConectorDB.getInstance();
			PreparedStatement pstmt = conexion.prepareStatement(updateItem);
			
			// SET
			pstmt.setString(1, this.getRazonSocial());
			pstmt.setString(2, this.getNif());
			pstmt.setString(3, this.getDireccion());
			pstmt.setString(4, this.getCodigoPostal());
			pstmt.setString(5, this.getLocalidad());
			pstmt.setString(6, this.getProvincia());
			pstmt.setString(7, this.getTelefono());
			pstmt.setString(8, this.getFax());
			pstmt.setString(9, 	this.getEmail());
			pstmt.setString(10, this.getAlias());
			pstmt.setString(11, this.getObservaciones());
			pstmt.setBoolean(12, this.isActivo());
			// WHERE
			pstmt.setInt(13, this.getId());
			
			int affectedRows = pstmt.executeUpdate();
			return affectedRows == 1 ? true : false;		
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}			
	}
	
	/**
	 * <b>Método de instancia</b> para insertar un objeto Cliente en la BBDD.
	 * @return Long (id AUTO_INCREMENT) confirmando la inserción de un objeto Cliente en la BBDD de la tabla CLIENTES.
	 */
	public long insertCliente() 
	{
		long id = 0;		
		try
		{			
			Connection conexion = ConectorDB.getInstance();
			PreparedStatement pstmt = conexion.prepareStatement(insertInto, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, this.getRazonSocial());
			pstmt.setString(2, this.getNif());
			pstmt.setString(3, this.getDireccion());
			pstmt.setString(4, this.getCodigoPostal());
			pstmt.setString(5, this.getLocalidad());
			pstmt.setString(6, this.getProvincia());
			pstmt.setString(7, this.getTelefono());
			pstmt.setString(8, this.getFax());
			pstmt.setString(9, 	this.getEmail());
			pstmt.setString(10, this.getAlias());
			pstmt.setString(11, this.getObservaciones());
			pstmt.setBoolean(12, this.isActivo());
			
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) 
			{
				ResultSet rs = pstmt.getGeneratedKeys();
				id = rs.next() ? rs.getLong(1) : id;
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		return id;
	}
	
	/**
	 * <b>Método estático</b> para obtener un ArrayList con los Clientes de la BBDD.
	 * @return Listado de los Clientes de la BBDD
	 */
	public static List<Cliente> getListAll()
	{
		List<Cliente> clientes = new ArrayList<>();
		try
		{			
			Connection conexion = ConectorDB.getInstance();
			PreparedStatement pstmt = conexion.prepareStatement(selectAll);
			ResultSet rs = pstmt.executeQuery();
			
			 while (rs.next()) 
			 {
				 Cliente cliente = new Cliente();	
				 cliente.setId(rs.getInt("id"));
				 cliente.setRazonSocial(rs.getString("razonSocial"));
				 cliente.setNif(rs.getString("nif"));
				 cliente.setDireccion(rs.getString("direccion"));
				 cliente.setCodigoPostal(rs.getString("codigoPostal"));
				 cliente.setLocalidad(rs.getString("localidad"));
				 cliente.setProvincia(rs.getString("provincia"));
				 cliente.setTelefono(rs.getString("telefono"));
				 cliente.setFax(rs.getString("fax"));
				 cliente.setEmail(rs.getString("email"));
				 cliente.setAlias(rs.getString("alias"));
				 cliente.setObservaciones(rs.getString("observaciones"));
				 cliente.setActivo(rs.getBoolean("activo"));
				 clientes.add(cliente);
			 }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}    
		return clientes;
	}
	
	/**
	 * <b>Método estático</b> para obtener un ArrayList con los Clientes de la BBDD.
	 * @param id Id del Cliente en la BBDD de la tabla CLIENTES.
	 * @return Booleano confirmando la eliminación de un Cliente dado un id
	 */
	public static boolean deleteCliente(int id)
	{
		try
		{			
			Connection conexion = ConectorDB.getInstance();
			PreparedStatement pstmt = conexion.prepareStatement(deleteItem);			
			pstmt.setInt(1, id);
			
			int affectedRows = pstmt.executeUpdate();
			return affectedRows == 1 ? true : false;		
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}	
	}
	
	public static void main(String... args)
	{
		List<Cliente> clientes = Cliente.getListAll();
		for(Cliente cliente : clientes)
			System.out.println(cliente);
		
		/*
		// CLIENTE NUEVO
		Cliente insertarCliente = new Cliente
		(
				"JOSÉ JOAQUÍN SÁNCHEZ FERNÁNDEZ", 
				"00000000T", 
				"MI CASA", 
				"03011", 
				"MURCIA", 
				"MURCIA", 
				"666.666.666", 
				null, 
				"info@sfsolutions.es", 
				"Alias", 
				"Observaciones", 
				true
		);		
		System.out.println("el id es: " + insertarCliente.insertCliente());
		*/
		
		/*
		// SELECT * FROM CLIENTES WHERE ID = id
		Cliente cliente2 = new Cliente(2);
		System.out.println(cliente2);*/
		
		/*
		// UPDATE CLIENTES
		Cliente cliente2 = new Cliente(2);
		cliente2.setNif("00000000T");
		cliente2.updateCliente();
		*/
		
		/*
		// DELETE FROM CLIENTES
		Cliente.deleteCliente(3);
		*/

		/*
		List<Cliente> clientes2 = Cliente.getListAll();
		for(Cliente cliente : clientes2)
			System.out.println(cliente);
		*/
	}
}
