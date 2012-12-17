package design;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Command_01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Commands c = new Commands();
		c.add(new Command_1());
		c.add(new Command_2());
		
		c.execute();
	}

}

class Commands {
	
	List<Command_i> list = new ArrayList<Command_i>();
	
	public synchronized void add(Command_i i) {
		this.list.add(i);
	}
	
	public void execute() {
		for(Iterator<Command_i> xx = list.iterator(); xx.hasNext();) {
			xx.next().execute();
		}
	}
}

interface Command_i {
	public void execute();
}

class Command_1 implements Command_i {
	public void execute() {
		System.out.println("Command_1");
	}
}
class Command_2 implements Command_i {
	public void execute() {
		System.out.println("Command_2");
	}
}
