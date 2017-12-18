package com.my.sampleGw.msg;

import net.sf.json.JSONObject;


public class Message {
	DataDef dataDef = new DataDef();
	
	public Message() {
		this.dataDef = new DataDef();
		//zclass = this.getClass().getName();
	}
	
	public Message(byte[] data) {
		this.dataDef = new DataDef();
		this.dataDef.toDataDef(data);
	}

	public DataDef getDataDef() 
	{
		return this.dataDef;
	}
	
	
	public DataField getDataField (String name) {
		return  this.dataDef.getDataField(name);
	}
	
	public DataField getDataField (int loc) {
		return  this.dataDef.getDataField(loc);
	}
	
	public void resetPos() {
		this.dataDef.resetPos();
	}
	
	public byte[] toBytes() {
		return this.dataDef.toBytes();
	}
	
	public Message toMessage(byte[] msg) {
		this.dataDef.toDataDef(msg);
			
		return this;
	}
	

	
	public int getLength() {
		return this.dataDef.length;
	}
	
	public Message newInstance()  {
		//DataObject o = this.dataDef.copy();
		Class nclass=null;
		Message data = null;

		try {
			data = (Message) Class.forName(this.getClass().getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
		data.toMessage(this.toBytes());
		return data;
	}
	
	public DataField add (DataField fld) {
		return dataDef.add(fld);
}
	
	public Message copy()  {
		Message o = new Message();
		o.toMessage(this.toBytes());
		return o;
	}
	
	public Message copy(Message msg) {
		this.dataDef.copy(msg);		
		return this;
	}
	public JSONObject  toJSON() {
		return dataDef.toJSON();
	}
	
	public String toString() {
		return this.dataDef.toString();
	}
	

}
