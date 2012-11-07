package servidor;

public class ServerCommon {

	public static final boolean DEBUG = true;

	public static void TwitterError(Exception e, String error, int numError) {
		if(error != null)
			System.err.println("[ERROR] "+ error);
		if (DEBUG && e != null)
			e.printStackTrace();
		System.exit(numError);
	}
	
	public static void TwitterError(Exception e, String error) {
		TwitterError(e, error, 1);
	}
	
	public static void TwitterError(String error) {
		TwitterError(null, error, 1);
	}
	
	public static void TwitterError(String error, int numError) {
		TwitterError(null, error, numError);
	}
	
	public static void TwitterWarning(Exception e, String warning) {
		if(warning != null)
			System.err.println("[WARNING] "+ warning);
		if (DEBUG && e != null)
			e.printStackTrace();
	}
	
	public static void TwitterWarning(String warning) {
		TwitterWarning(null, warning);
	}

}
