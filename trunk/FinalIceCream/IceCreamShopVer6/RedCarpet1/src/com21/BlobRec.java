package com21;

import java.awt.Image;
import java.awt.Toolkit;

public class BlobRec {
	private String id; 
	  private String file; 
	  private String desc; 
	  private String type; 
	  private byte[] byteStream; 
	  
	  public void setID (String id) { 
	    this.id = id; 
	  } 
	  
	  public String getID () { 
	    return this.id; 
	  } 
	  
	  public void setFile (String file) { 
	    this.file = file; 
	  } 
	  
	  public String getFile () { 
	    return this.file; 
	  } 
	  
	  public void setDesc (String desc) { 
	    this.desc = desc; 
	  } 
	  
	  public String getDesc () { 
	    return this.desc; 
	  } 
	  
	  public void setType (String type) { 
	    this.type = type; 
	  } 
	  
	  public String getType () { 
	    return this.type; 
	  } 
	  
	  public void setByteStream (byte[] byteStream) { 
	    this.byteStream = byteStream; 
	  } 
	  
	  public byte[] getByteStream () { 
	    return this.byteStream; 
	  } 
	  
	  public Image getImage () { 
	    Image image = Toolkit.getDefaultToolkit().createImage(getByteStream()); 
	    return image; 
	  } 
}
