package design;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "1";
		String b = "1";
		System.out.println(a.hashCode() + ":" + b.hashCode() + ":" + a.equals(b) + ":" + (a == b));
		
		a = new String("1");
		b = new String("1");
		System.out.println(a.hashCode() + ":" + b.hashCode() + ":" + a.equals(b) + ":" + (a == b));
		
		StringBuffer sb = new StringBuffer();
		
		String[] c = new String[10];
		String[] d = {"1","2"};
	}

}
