package com.my.sampleGw.msg;



public class DataField {
	
	String name;
	String propertyName;
	int pos;
	public int length;
	int dec;
	FieldType type;
	String initValue;
	byte[] value = null;
	
	DataField (String name, String propertyName, int pos, int length, int dec,FieldType type,String initValue) {
		this.name = name;
		this.propertyName = propertyName;
		this.pos = pos;
		this.length = length;
		this.type = type;
		this.initValue = initValue;
		this.dec = dec;
		setString(this.initValue);
	}
	
	DataField (String name, int pos, int length, int dec,FieldType type,String initValue) {
		this ( name,  name,  pos,  length,  dec,type, initValue);
	}
		
	public DataField copy() {
		DataField fld = new	DataField (this.name, this.pos, this.length, this.dec,this.type,this.initValue);
	    fld.value = new byte[length];
	    System.arraycopy(this.value, 0, fld.value, 0, length);
	    return fld;
	}
	
	public void setString(Object o) {

		if (o instanceof byte[]) { 
			if (this.type == FieldType.B) {
					byte[] val = (byte[]) o;
					byte[] bytebuff = new byte[this.length];
					System.arraycopy(val, 0, bytebuff, 0, length);
					this.value = bytebuff;
			} else {
				String val = new String ((byte[]) o);
				this.value = Fields.getFormattedString(this.type, this.length,0, val).getBytes();
			}
		} else {		
		 		this.value = Fields.getFormattedString(this.type, this.length,this.dec, o).getBytes();
		}	
	}
		
	public String getString() {
		if (this.type == FieldType.B)  {
			return  Fields.getFormattedString(this.type, this.length,this.dec, "");
		}
		
		return  new String(this.value);
	}
	
	public String getPropertyName() {
		return  this.propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public boolean isNull() {
		if (this.value == null) {
			return true;		
		}
		
		if (this.type != FieldType.B) { 
			 String value =  new String (this.value);		
			if (value.equals("")) {
				return true;
			}
			
		}
		return false;
		
	}
	 
	public byte[] getBytes() {
		 return  this.value ;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String fmt = "[%-15s][%03d:%03d:%03d:%s]=[%s]";
		sb.append(String.format(fmt, this.name , this.pos,this.length,this.dec,this.type, getString()));
		return sb.toString();
	}

	
	
	public static void main(String[] args) {

        byte[] a = "".getBytes();

		System.out.println( "getFormattedString(A,0,0,) =  " +  "[" + Fields.getFormattedString(FieldType.A,0,0,"  ")  + "]");
		System.out.println( "getFormattedString(N,10,0,) = " +  "[" + Fields.getFormattedString(FieldType.N,10,0,"")  + "]");
		System.out.println( "getFormattedString(D,10,3,) = " +  "[" + Fields.getFormattedString(FieldType.D,10,3,"")  + "]");
		
		System.out.println( "getFormattedString(A,10,0,) = " +  "[" + Fields.getFormattedString(FieldType.A,10,0,null)  + "]");
		System.out.println( "getFormattedString(N,10,0,) = " +  "[" + Fields.getFormattedString(FieldType.N,10,0,null)  + "]");
		System.out.println( "getFormattedString(D,10,3,) = " +  "[" + Fields.getFormattedString(FieldType.D,10,3,null)  + "]");
		System.out.println( "getFormattedString(B,10,0,) = " +  "[" + Fields.getFormattedString(FieldType.B,10,0,null)  + "]");
		System.out.println( "getFormattedString(B,10,0,) = " +  "[" + Fields.getFormattedString(FieldType.B,10,0,a)  + "]");

	}
	
}
