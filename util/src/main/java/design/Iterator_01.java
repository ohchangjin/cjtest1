package design;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Iterator_01 implements Iterable<String> {

	List<String> list = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return new java.util.Iterator() {
			int seq = 0;
		   public boolean hasNext() {
		    return  seq < list.size();
		   }
		   public String next() {
		    return list.get(seq++);
		   }
		   public void remove() {
		        throw new UnsupportedOperationException();
		   }
		};
	}
	
	
	public void add(String temp) {
		this.list.add(temp);
	}
	
	public static void main(String[] args) {
		Iterator_01 test = new Iterator_01();
		test.add("1");
		test.add("2");
		
		Iterator<String> ite = test.iterator();
		while(ite.hasNext()) {
			System.out.println(ite.next());
		}
	}
}
