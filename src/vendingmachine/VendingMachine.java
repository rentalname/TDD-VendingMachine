package vendingmachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
			throw new VendingMachineExeption("�݌ɐ؂�ł�");
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

	public Collection<Drink> getDrinkCollection() {
		Collection<Drink> values = drinkType.values();
		return values;
	}

	public Collection<Drink> hasStockDrinkCollection() {
		ArrayList<Drink> list = new ArrayList<>(getDrinkCollection());
		for (Iterator<Drink> i = list.iterator(); i.hasNext();) {
			Drink drink = i.next();
			if (drinkStock.get(drink.getName()) < 1) {
				i.remove();
			}
		}
		return list;
	}

	/**
	 * �w�肳�ꂽ���O�����W���[�X�̍݌ɂ�1�ȏ�ł���, �������z���w�肳�ꂽ�h�����N�̉��i�ȏ�ł����true��Ԃ�
	 * 
	 * @param name
	 * @return �w�肳�ꂽ�W���[�X���w���\�ł����true��Ԃ�
	 */
	public boolean canParchase(String name) {
		if (drinkStock.get(name) > 0) {
			if (drinkType.get(name).getPrice() <= total) {
				return true;
			}
		}
		return false;
	}

	public Collection<Drink> canParchaseList() {
		Collection<Drink> parchaseList = drinkType.values();
		for (Iterator<Drink> i = parchaseList.iterator(); i.hasNext();) {
			Drink drink = i.next();
			if (!canParchase(drink.getName())) {
				i.remove();
			}
		}
		return parchaseList;
	}

	private int total;

	/**
	 * �������v�z��Ԃ�
	 * 
	 * @return ���݂̓������v�z
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
	 * �ݕ��𓊓�����
	 * 
	 * @param {@link Money} money
	 * @return �L���ȉݕ��ł������ꍇ {@link Money.Zero}��Ԃ�.�����ȉݕ��ł������ꍇ,�������ꂽ�ݕ���Ԃ�
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
	 * �ݕ��̕����߂����s��
	 * 
	 * @return �������ꂽ�ݕ��Ɠ��������z
	 */
	public int refund() {
		int payBuckTotal = total;
		total = 0;
		return payBuckTotal;
	}

	int saleProceeds;

	public Drink vending(String name) throws VendingMachineExeption {
		if (canParchase(name)) {
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

}
