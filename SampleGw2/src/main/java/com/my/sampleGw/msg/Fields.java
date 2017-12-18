package com.my.sampleGw.msg;

public class Fields {
	
	public static DataField A(String name, int length, String initValue) {
		return  new DataField (name,0,length,0,FieldType.A,initValue);
	}
	
	public static DataField N(String name,  int length, String initValue) {
		return  new DataField (name,0,length,0,FieldType.N,initValue);
	}
	
	public static DataField D(String name,  int length, int dec,String initValue) {
		return  new DataField (name,0,length,dec,FieldType.D,initValue);
	}

	public static DataField B(String name, int length) {
		return  new DataField (name,0,length,0,FieldType.B,"");
	}
	
	public static DataField B(String name) {
		return  new DataField (name,0,256,0,FieldType.B,"");
	}
	
	public static DataField copy(DataField fld) {
		   return  fld.copy();
	}
	
	public static byte[] getByteArray(byte[] value,int length) {
		
		if (length < 1) return null;
		
		byte[] byteBuff = new byte[length];
		int startpos = 0;

		if (value != null) {
		   System.arraycopy(value, 0, byteBuff, 0, (length  < value.length) ? length : value.length );
		   startpos = (length  < value.length) ? length : value.length;
		 }
		
		for(int i = startpos ; i < length ;i++ ) {
			byteBuff[i] =  0x20;
		}
		
	 	return byteBuff;
	}
		
	public static String getFormattedString(FieldType type, int length, int dec, Object o) {
		String format = "";
		String value = ""; 

		if (o== null  || o.equals("null") ) {
				o = "";
		}
		
		if ( type  == FieldType.N || type  == FieldType.D ) {
			if (o instanceof String)  { 
				o = ((String) o).trim();
				if (o.equals("")) {
					o = "0";
				}
			}
		}
		
		if (o.toString() == "") {
			if ( type  == FieldType.N || type  == FieldType.D ) {
				o = "0";
			}
		}
			
		if (length  == 0) {
			return "";
		}
		
		if (o instanceof byte[]) { 
			String val = "";
			if (type == FieldType.B) {
		 
			} else {
				val = new String ((byte[]) o);
			}	
			format = "%-" + length + "s";
			value =   String.format(format, val ) ;
		} 
		
		if (o instanceof String ) {

			if (type  == FieldType.A || type  == FieldType.B) {
				format = "%-" + length + "s";
				value =   String.format(format, o.toString() ) ;
				 
			} else 	if (type  == FieldType.N  ) {
				String s = o.toString();
				int p = s.indexOf(".");
				if (p > 0) {
					s = s.substring(0, s.indexOf("."));
				}
				format = "%0" + length + "d";
				value = String.format(format, Long.valueOf(s));

			} else if (type  == FieldType.D) {
				value = String.format("%f", Double.valueOf(o.toString()));
			}

		} else if (o instanceof Integer || o instanceof Long) {
			if (type  == FieldType.N || 
				type  == FieldType.A ) {
			
				format = "%0" + length + "d";
				value = String.format(format, Long.valueOf(o.toString()));
			} else if (type  == FieldType.D ) {
				format = "%0" + length + "." + dec + "f";
				value = String.format(format, Double.valueOf(o.toString()));
    		}

		} else if (o instanceof Float || o instanceof Double) {
			 if (type  == FieldType.D ) {
				 format = "%0" + length + "." + dec + "f";
                 value = String.format(format,  Double.valueOf(o.toString()));
			} else if ( type  == FieldType.A ) {
				format =  "%0" + length + "f";
				value = String.format(format,  Double.valueOf(o.toString()));
			} else if (type  == FieldType.N )  {
				double a = Double.valueOf(o.toString());
					format = "%0" + length + "d";
				value = String.format(format, (long) a);
			}
		} else {
			// Not Define
		}

		if (value.getBytes().length > length)
				  {
			byte[] bytebuff = new byte[length];
			System.arraycopy(value.getBytes(), 0, bytebuff, 0, length);
			value = new String(bytebuff);
			bytebuff = null;
		} 
		
		return value;
	}
	
 
}
