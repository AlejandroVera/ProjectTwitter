package excepcionesComunes;



public class TwitterException extends RuntimeException implements InterfazTwitterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5768238791907208649L;
	String additionalInfo;

	public TwitterException(String string){
		super(string);
		this.additionalInfo = "";
	}
	
	public TwitterException(String string, String additionalInfo) {
		super(string);
		this.additionalInfo = additionalInfo;
	}
	
	public String getAdditionalInfo() {
		return this.additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}
