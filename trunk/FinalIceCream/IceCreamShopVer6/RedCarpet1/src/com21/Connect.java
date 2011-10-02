package com21;

import java.sql.*;

import oracle.jdbc.driver.*;

public class Connect {

	/**
	 * @param args
	 */
	Connection Conn; 
	Statement stmt; 
	ResultSet rset; 
	
	public Connection getConn(){
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Conn = DriverManager.getConnection
			("jdbc:oracle:thin:@AdminpingPC:1521:orcl","redcarpet","oracle");
			//Conn = DriverManager.getConnection
			//("jdbc:oracle:thin:@Kaew-PC:1521:orcl","redcarpet","oracle");
			//Conn = DriverManager.getConnection
			//("jdbc:oracle:thin:@hiterujung-PC:1521:orcl","redcarpet","oracle");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Conn;
	}
	
	public void DisConn()throws Exception{
		stmt.close();
		Conn.close();
	}
}
