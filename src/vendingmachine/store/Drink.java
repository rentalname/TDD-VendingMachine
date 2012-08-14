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

	// 販売されたときの,釣り銭を取得する
	public int getChange() {
		return change;
	}

	public void setChange(int change) {
		this.change = change;
	}
}
