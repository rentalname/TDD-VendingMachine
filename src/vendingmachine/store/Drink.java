package vendingmachine.store;

public class Drink {
	private String name;
	private int price;

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

}
