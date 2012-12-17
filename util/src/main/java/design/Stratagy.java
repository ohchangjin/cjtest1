package design;

public class Stratagy {
	public static void main(String[] args) {
		Market market1 = new Market(new Stratagy_01());
		market1.order();
		
		Market market2 = new Market(new Stratagy_02());
		market2.order();
	}
}

interface Seller {
	abstract void sell();
}

class Stratagy_01 implements Seller {
	public void sell() {
		System.out.println("sell a");
	}
}

class Stratagy_02 implements Seller {
	public void sell() {
		System.out.println("sell b");
	}
}

class Market {
	Seller seller;
	
	public Market(Seller seller) {
		this.seller = seller;
	}
	
	public void order() {
		this.seller.sell();
	}
}
