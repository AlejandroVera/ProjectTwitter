package interfazIPO;


import java.lang.reflect.Method;

import javafx.scene.Node;

public class SelectorEntry{
	
	Node node;
	Method handler;
	Object instance;
	public SelectorEntry(Node node, Method handler, Object instance) {
		super();
		this.node = node;
		this.handler = handler;
		this.instance = instance;
	}
	
}