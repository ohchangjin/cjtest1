package design;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class Adapter_01 {
	
	public void method01(Enumeration<String> em) {
		while(em.hasMoreElements()) {
			System.out.println(em.nextElement());
		}
	}
	
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("test");
		
		// ite to em
		Enumeration em = new IteToEm(list.iterator());
		
		Adapter_01 ad = new Adapter_01();
		ad.method01(em);
	}
}

class IteToEm implements Enumeration {
	Iterator<String> ite;
	
	public IteToEm(Iterator<String> ite) {
		this.ite = ite;
	}
	
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return ite.hasNext();
	}

	public Object nextElement() {
		// TODO Auto-generated method stub
		return ite.next();
	}
	
}
