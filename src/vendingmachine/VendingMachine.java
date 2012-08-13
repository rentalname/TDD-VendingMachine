package vendingmachine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import vendingmachine.store.Drink;

import money.Money;

public class VendingMachine {
	static Set<Money> acceptableMoney;
	static {
		acceptableMoney = getAcceptableMoneySet();
	}

	static Set<Money> notAcceptableMoney;
	static {
		notAcceptableMoney = getNotAcceptableMoneySet();
	}

	private int total;

	/**
	 * “Š“ü‘ŒvŠz‚ğ•Ô‚·
	 * 
	 * @return Œ»İ‚Ì“Š“ü‘ŒvŠz
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * ‰İ•¼‚ğ“Š“ü‚·‚é
	 * 
	 * @param {@link Money} money
	 * @return —LŒø‚È‰İ•¼‚Å‚ ‚Á‚½ê‡ {@link Money.Zero}‚ğ•Ô‚·.–³Œø‚È‰İ•¼‚Å‚ ‚Á‚½ê‡,“Š“ü‚³‚ê‚½‰İ•¼‚ğ•Ô‚·
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
	 * ‰İ•¼‚Ì•¥‚¢–ß‚µ‚ğs‚¤
	 * 
	 * @return “Š“ü‚³‚ê‚½‰İ•¼‚Æ“™‚µ‚¢‹àŠz
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

	private Map<String, Drink> drinkType = new HashMap<>();
	private Map<String, Integer> drinkStock = new HashMap<>();
	{
		Drink coke = new Drink("coke", 120);
		drinkType.put(coke.getName(), coke);
		drinkStock.put(coke.getName(), 5);
	}

	public Drink getDrink(String name) throws VendingMachineExeption {
		Integer remainStock = drinkStock.get(name);
		if (remainStock < 1) {
			throw new VendingMachineExeption("İŒÉØ‚ê‚Å‚·");
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
	public void store(Drink drink,int num){
		for(int i = 0;i < num;i++){
			store(drink);
		}
	}

	public int getStock(String name) {
		return drinkStock.get(name);
	}
	
}
