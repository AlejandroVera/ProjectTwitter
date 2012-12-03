package cliente;

class ClientTools {
	
	static UniverseController universeController;

	protected static void showDialog(String text){
		showDialog(text, "Error");
    }
	
	protected static void showDialog(String text, String topText){
		if(universeController != null){
			universeController.showError(text, topText);
		}
    }
	
	protected static UniverseController getUniverseController(){
		return universeController;
	}
	
	protected static void setUniverseController(UniverseController controller){
		universeController = controller;
	}
	
	protected static int countCharacters(String statusText) {
		final String regex = "((^|\\s)[a-zA-Z0-9]+)(\\.[a-zA-Z0-9]+)+";
		//Devolvemos la longitud sustituyendo las URLs con 20 caracteres.
		return statusText.replaceAll(regex, "********************").length();
	}
	
}
