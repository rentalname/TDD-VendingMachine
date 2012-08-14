package vendingmachine.store;

public class Drink {
	private String name;
	private int price;
	private int change;

	public Drink(String drinkName, int drinkPrice) {
		name = drinkName;
		price = drinkPrice;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	// ”Ì”„‚³‚ê‚½‚Æ‚«‚Ì,’Þ‚è‘K‚ðŽæ“¾‚·‚é
	public int getChange() {
		return change;
	}

	public void setChange(int change) {
		this.change = change;
	}
}
