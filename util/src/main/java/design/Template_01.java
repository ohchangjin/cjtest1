package design;

public class Template_01 {
	public static void main(String[] args) {
		Make a = new A();
		a.step1();
		Make b = new A();
		b.step1();
	}
}

abstract class Make {
	protected abstract void step2();
	
	final void step1() {
		System.out.println("start");
		step2();
		System.out.println("end");
	}
}

class A extends Make {

	@Override
	protected void step2() {
		// TODO Auto-generated method stub
		System.out.println("A");
	}
}

class B extends Make {

	@Override
	protected void step2() {
		// TODO Auto-generated method stub
		System.out.println("B");
	}
}

