package cliente;

class ClientTools {
	
	static UniverseController errorController;

	protected static void showDialog(String text){
		showDialog(text, "Error");
    }
	
	protected static void showDialog(String text, String topText){
		if(errorController != null){
			errorController.showError(text, topText);
		}
    }
	
	protected static UniverseController getErrorController(){
		return errorController;
	}
	
	protected static void setErrorController(UniverseController controller){
		errorController = controller;
	}
	
	protected static int countCharacters(String statusText) {
		final String regex = "((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+";
		//Devolvemos la longitud sustituyendo las URLs con 20 caracteres.
		return statusText.replaceAll(regex, "********************").length();
	}
	
}
