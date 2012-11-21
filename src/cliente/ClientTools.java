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
	
}
