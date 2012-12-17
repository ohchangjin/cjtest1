package design;

public class Factory_01 {
	public Toys make(String type) {
//		if("robot".equals(type)) {
//			return new Robot();
//		} else if("lego".equals(type)) {
//			return new Lego();
//		}
//		return null;
		
		Class<?> cla = null;
		try {
			cla = Class.forName(type);
			return (Toys)cla.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Factory_01 factory = new Factory_01();
		factory.make("design.Robot").make();
		factory.make("design.Lego").make();
	}
}

interface Toys {
	public abstract void make();
}

class Robot implements Toys {
	public void make() {
		System.out.println("robot make");
	}
}

class Lego implements Toys {
	public void make() {
		System.out.println("Lego make");
	}
}




