package design;

import java.util.Hashtable;

public class FlyWeight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persion p1 = FlyWeightFactory.getPersion("a");
		Persion p2 = FlyWeightFactory.getPersion("a");
		Persion p3 = FlyWeightFactory.getPersion("b");
		
		System.out.println(p1.hashCode() + ":" + p2.hashCode() + ":" + p3.hashCode());
	}

}


class FlyWeightFactory {
	private static Hashtable<String, Persion> t = new Hashtable<String, Persion>();
	
	public static Persion getPersion(String key) {
		if(t.containsKey(key)) {
			return t.get(key);
		}
		Persion temp = new Persion(key);
		t.put(key, temp);
		return temp;
	}
}

class Persion {
	String name;
	
	public Persion(String name) {
		this.name = name;
	}
}