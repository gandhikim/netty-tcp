package com.my.sampleGw.msg;

public class MyMessage extends Message {
	
	public DataField fldLength 		=  Fields.N("Length",4,"");
	public DataField fldText	   	=  Fields.A("Text",7,"");
	public DataField fldTID			=  Fields.A("TID",20,"");
	public DataField fldType		=  Fields.A("Type",4,"");
	public DataField fldMsgTag	   	=  Fields.A("MsgTag",7,"");
	public DataField fldSendDt     	=  Fields.A("SendDt",8,"");
	public DataField fldSendTime    =  Fields.A("SendTm",6,"");
	public DataField fldFill        =  Fields.A("FILL",16,"");
	
	MyMessage () {
		super();
		
		add( fldLength 	  );
		add( fldText	  );
		add( fldTID		  );
		add( fldType	  );
		add( fldMsgTag	  );
		add( fldSendDt    );
		add( fldSendTime  );
		add( fldFill      );
		
		updateLength();
	}
	
	 void updateLength() {
		 fldLength.setString(this.getLength() - 4);
	 }
	 
	 public static void main(String[ ] args) {
		 
		 MyMessage vReq = new MyMessage();
		 String msg = "111122222223333333333333333333344445555555666666667777778888888888888888";
		 byte[] accept = msg.getBytes();
		 vReq.toMessage(accept);
		 vReq.updateLength();
		 System.out.println(vReq.toString());
		 
	 }
	
}

