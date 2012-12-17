package design;

import java.util.Observable;
import java.util.Observer;

public class Observer_01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Target a = new Target("a");
		Target b = new Target("b");
		
		Watch w = new Watch();
		w.addObserver(a);
		w.addObserver(b);
		w.action();
	}

}

class Watch extends Observable  {
	public void action() {
		setChanged();
    	notifyObservers();
	}
}

class Target implements Observer {
	
	String name;
	
	public Target(String name) {
		this.name = name;
	}
	
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof Watch) {
			System.out.println(this.name + " call");
		}
	}
}