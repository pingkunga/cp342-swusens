package com21;

import java.awt.Image;
import java.io.BufferedInputStream; 
import java.io.ByteArrayOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 

import oracle.jdbc.OraclePreparedStatement; 
import oracle.jdbc.OracleResultSet; 
import oracle.sql.BLOB; 

public class UploadBlob {
	  private Connection connection = null; 
	  private ResultSet rs; 
	  private Statement stmt; 
	  private PreparedStatement pstmt; 
	  
	  public void loadBlob(int bid, String files, String types, InputStream stream
			  ,Connection connection) { 
	  
	    OraclePreparedStatement ops;
	    String sql = null;
	   
	    
	    try { 
	      BLOB blob = getBLOB(connection, stream); 
	      if(bid==0){
		    	//insert
		    	//sql = "INSERT INTO PICTURES VALUES(PICTURES_SEQ.NEXTVAL, ?, ?, ?)"; 
		    	sql = "INSERT INTO PICTURES VALUES(PICTURES_SEQ.NEXTVAL, ?, ?, ?)"; 
		    }else{
		    	//edit
		    	sql = "UPDATE PICTURES " +
				  "SET BLOB_FILE = ? ," +
						"BLOB_TYPE = ?," +
						"BLOB_CONTENT = ? " +
				  "WHERE BLOB_ID = "+bid+"";
		    }
	      ops = (OraclePreparedStatement)connection.prepareStatement(sql); 
	      //ops.setInt(1, 1); 
	      ops.setString(1, files); 
	      ops.setString(2, types); 
	      ops.setBLOB(3, blob); 
	      ops.execute(); 
	    } catch (SQLException e) { 
	      e.printStackTrace(); 
	    } catch (IOException e) { 
	      e.printStackTrace(); 
	    } 
	      
	  } 
	  
	  public BlobRec getBlobRec(int blob_id, Connection connection) { 
	    BlobRec br = new BlobRec(); 
	    try { 
	      stmt = connection.createStatement(); 
	      String sql = "SELECT * FROM PICTURES WHERE BLOB_ID = '" + blob_id + "'";
	      System.out.println(sql);
	      rs = stmt.executeQuery(sql); 
	      if (rs.next()) { 
	        br.setID(rs.getString(1)); 
	        br.setFile(rs.getString(2)); 
	        br.setType(rs.getString(3)); 
	        BLOB blob = ((OracleResultSet)rs).getBLOB (4); 
	        if (blob != null) { 
	          br.setByteStream(getByteArray(blob)); 
	        } 
	      } 
	    } catch (SQLException e) { 
	      e.printStackTrace(); 
	    } catch (IOException e) { 
	      e.printStackTrace(); 
	    } 
	    return br; 
	  } 
	  
	  public byte[] getByteArray (BLOB blob) throws SQLException, IOException { 
	  
	    BufferedInputStream bis; 
	    ByteArrayOutputStream bo; 
	    bis = new BufferedInputStream(blob.getBinaryStream()); 
	    bo = new ByteArrayOutputStream(); 
	    int MAXBUFSIZE = 1024; 
	    byte[] buf = new byte[MAXBUFSIZE]; 
	    int n = 0; 
	    
	    while ((n = bis.read(buf, 0, MAXBUFSIZE)) != -1) { 
	      bo.write(buf, 0, n); 
	    } 
	    
	    bo.flush(); 
	    bo.close(); 
	    bis.close(); 
	    buf = null; 
	    
	    return bo.toByteArray(); 
	           
	  } 
	  
	  public BLOB getBLOB (Connection connection, InputStream inputStream) 
	  throws SQLException, IOException { 
	  
	    BLOB blob; 
	    blob = BLOB.createTemporary(connection, false, BLOB.DURATION_SESSION); 
	    byte[] binaryBuffer; 
	    long position = 1; 
	    int bytesRead = 0; 
	    binaryBuffer = new byte[inputStream.available()]; 
	    
	    while ((bytesRead = inputStream.read(binaryBuffer)) != -1) { 
	      blob.putBytes(position, binaryBuffer); 
	      position += bytesRead; 
	    } 
	    
	    return blob; 
	    
	  }
	  public static Image getImage(int id, Connection connection) {
		  
		 UploadBlob upload = new UploadBlob();
		 BlobRec br = upload.getBlobRec(id, connection);
		 Image image = br.getImage();
		 return image;
		    
	  }
	  
}
