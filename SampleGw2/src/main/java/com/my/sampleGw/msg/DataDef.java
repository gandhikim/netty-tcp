package com.my.sampleGw.msg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.json.JSONObject;


public class DataDef {
	
	public String key;
	public int length;
	ArrayList<DataField> dataFields 
		= new ArrayList<DataField>();

	
	DataDef() {
		length = 0; 
		this.key = "";
	}

	
	public DataDef(String key) {
		this.key = key;
		length = 0;
	}
	
	
	public DataField add(String name, int pos, 
			int length,int dec, FieldType type, String initValue) {

		DataField field 
			= new DataField(name, this.length, length, dec, type, initValue);
		
		dataFields.add(field);
		this.length = this.length + length;
		return field;
	}
	
	
	public DataField add(DataField field) {

		field.pos = this.length;
		dataFields.add(field);
		this.length = this.length + field.length;
		return field;
	}
	
	
	public DataDef copy() {
		this.resetPos();
		DataDef data = new DataDef();
		data.key = this.key;
		data.length = this.length;
		for (DataField a : this.dataFields) {
			data.dataFields.add(a.copy());
		}
		return data;
	}

	
	public DataDef append(DataDef dataDef) {
		
		for (DataField a : dataDef.dataFields) {
			this.add(a.copy());
		}
		return this;
	}
	
	
	public byte[] toBytes() {

		byte[] b = new byte[this.length];
		int len = 0;
		for (DataField a : dataFields) {
		if((a.length + len ) > this.length) break;
			if (a.length > 0 ) {
				System.arraycopy(a.getBytes(), 0, b, len, a.length);
				len += a.length;
			}
		}
		return b;
	}	
	
	
   public void toDataDef(byte[] mesg) {
	   int mesgLength = mesg.length;
	   int targetLength = 0;

		for (DataField a : this.dataFields) {
			a.pos = targetLength;
			
			if (mesgLength < (targetLength + a.length)) {
				if (a.length > 0) {
					a.value = Fields.getFormattedString(a.type, a.length,a.dec, "").getBytes();
					targetLength += a.length;
				}	
				
				this.length = targetLength;
				continue;
			}
					
			if (a.length > 0) {
				System.arraycopy(mesg, targetLength, a.value, 0, a.length);
				targetLength += a.length;
			}
		}
   }
   
   public void copy(Message mesg) {
		for (DataField a : this.dataFields) {			
			DataField b = mesg.getDataField(a.name);				
			if(b != null && b.length > 0) {				
				if(isEmpty( new String(a.value).trim().replace("0", "")))					
					a.setString(new String (b.value));
			}	
		}
   }
  
	
	public void set(String name, Object value) {
		for (DataField a : this.dataFields) {
			if (a.name.equals(name)) {
				a.setString(value);
			}
		}
	}
	
	public DataField getDataField(int loc) {
		return dataFields.get(loc);
	}
	
	public void resetPos() {
		this.length = 0;
		for (DataField a : this.dataFields) {
			 a.pos = this.length;
			 this.length += a.length;
		}
	}
	
	
	DataField getDataField(String name) {
		for (DataField a : this.dataFields) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		return null;
	}
	
 
	public JSONObject toJSON() {
		
		JSONObject jsonO =new JSONObject();
		
		for (DataField a : this.dataFields) {
			
			if (a.propertyName== null || a.propertyName.equals("") ) {
				continue;
			}
			jsonO.put(a.propertyName, a.getString());
			
			/*
			if  (a.type == FieldType.N  ) {
				js.put(a.propertyName,Integer.parseInt((new String(a.value).trim()) ));
			} else if (a.type == FieldType.D ) {
				js.put(a.propertyName,Double.valueOf(new String(a.value).trim()));
			} else if (a.type == FieldType.A ) {
				js.put(a.propertyName,new String(a.value).trim());
			} else {
				js.put(a.propertyName,a.value);
			}
			*/
		}
		return jsonO;
	}

	public DataDef  toObject(JSONObject js)  {
		for (DataField a : this.dataFields) {
			Object v=js.get(a.propertyName);
			if (v != null) {
				a.setString(v);
			}
		}
		resetPos();
		return this;
	}
	 
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//String fmt = "[%-15s][%03d:%03d:%03d:%s] = %s %-50s\n";
		String fmt = "[%-15s][%-15s][%03d:%03d:%03d:%s] = %s\n";
		sb.append("key = " + "[" + this.key + "]\n");
		sb.append("length = " + "[" + this.length + "]\n");
		for (DataField d : this.dataFields) {
			sb.append(String.format(fmt, d.name ,d.propertyName, d.pos,d.length,d.dec,d.type, "[" +  d.getString()  + "]"));
		}
		return sb.toString();
	}
	
	
	public static byte[] get(File f) throws Exception {
		byte[] array = null;

		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;
		try {
			baos = new ByteArrayOutputStream();
			fis = new FileInputStream(f);
			int readCnt = 0;

			byte[] buf = new byte[1024];
			while ((readCnt = fis.read(buf)) != -1) {
				baos.write(buf, 0, readCnt);
			}
			baos.flush();
			array = baos.toByteArray();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return array;
	}
	
	private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	
}

