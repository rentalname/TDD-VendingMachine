package vendingmachine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import vendingmachine.store.Drink;

import money.Money;

public class VendingMachine {
	private Map<String, Drink> drinkType = new HashMap<>();
	private Map<String, Integer> drinkStock = new HashMap<>();

	public Drink getDrink(String name) throws VendingMachineExeption {
		Integer remainStock = drinkStock.get(name);
		if (remainStock < 1) {
			throw new VendingMachineExeption("在庫切れです");
		}
		drinkStock.put(name, remainStock - 1);
		return drinkType.get(name);
	}

	public void store(Drink drink) {
		String name = drink.getName();
		drinkType.put(name, drink);
		if (drinkStock.containsKey(name)) {
			drinkStock.put(name, drinkStock.get(name) + 1);
		} else {
			drinkStock.put(name, 1);
		}
	}

	public void store(Drink drink, int num) {
		for (int i = 0; i < num; i++) {
			store(drink);
		}
	}

	public int getStock(String name) {
		return drinkStock.get(name);
	}

	/**
	 * 指定された名前を持つジュースの在庫が1以上であり, 投入金額が指定されたドリンクの価格以上であればtrueを返す
	 * 
	 * @param name
	 * @return 指定されたジュースが購入可能であればtrueを返す
	 */
	public boolean canParchase(String name) {
		if (drinkStock.get(name) > 0) {
			if (drinkType.get(name).getPrice() <= total) {
				return true;
			}
		}
		return false;
	}

	private int total;

	/**
	 * 投入総計額を返す
	 * 
	 * @return 現在の投入総計額
	 */
	public int getTotal() {
		return total;
	}

	static Set<Money> acceptableMoney;
	static {
		acceptableMoney = getAcceptableMoneySet();
	}

	static Set<Money> notAcceptableMoney;
	static {
		notAcceptableMoney = getNotAcceptableMoneySet();
	}

	/**
	 * 貨幣を投入する
	 * 
	 * @param {@link Money} money
	 * @return 有効な貨幣であった場合 {@link Money.Zero}を返す.無効な貨幣であった場合,投入された貨幣を返す
	 */
	public Money insert(Money money) {
		if (acceptableMoney.contains(money)) {
			total += money.getAmount();
			return Money.Zero;
		} else if (notAcceptableMoney.contains(money)) {
			return money;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 貨幣の払い戻しを行う
	 * 
	 * @return 投入された貨幣と等しい金額
	 */
	public int refund() {
		int payBuckTotal = total;
		total = 0;
		return payBuckTotal;
	}

	private static Set<Money> getAcceptableMoneySet() {
		Set<Money> set = new HashSet<Money>();
		set.add(Money.Ten);
		set.add(Money.Fifty);
		set.add(Money.Hundred);
		set.add(Money.FiveHundred);
		set.add(Money.Thousand);
		return set;
	}

	private static Set<Money> getNotAcceptableMoneySet() {
		Set<Money> set = new HashSet<>();
		set.add(Money.Zero);
		set.add(Money.One);
		set.add(Money.Five);
		set.add(Money.TwoThousand);
		set.add(Money.FiveThousand);
		set.add(Money.TenThousand);
		return set;
	}

	int saleProceeds;
	public Drink vending(String name) throws VendingMachineExeption {
		if(canParchase(name)){
			Drink drink = getDrink(name);
			total -= drink.getPrice();
			saleProceeds += drink.getPrice();
			return drink;
		}
		return null;
	}
	
	public int getSales() {
		return saleProceeds;
	}
}
