package design;

public class ProtoType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrototypeClass p1 = new PrototypeClass("p1");
		PrototypeClass p3 = new PrototypeClass("p1");
		try {
			PrototypeClass p2 = (PrototypeClass)p1.clone();
			
			System.out.println(p1.getName().hashCode()+":"+p2.getName().hashCode() + ":" + p1.hashCode() + ":" + p2.hashCode());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class PrototypeClass implements Cloneable {
	String name;
	
	public PrototypeClass(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return (PrototypeClass)super.clone();
	}
	
}
