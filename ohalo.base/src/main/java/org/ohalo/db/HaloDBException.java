package org.ohalo.db;

public class HaloDBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6402096353945338205L;
	
	public HaloDBException(){
		super();
	}
	
	public HaloDBException(String msg){
		super(msg);
	}
	
	public HaloDBException(Throwable e){
		super(e);
	}
	
	public HaloDBException(String msg , Throwable e){
		super(msg, e);
	}

}
