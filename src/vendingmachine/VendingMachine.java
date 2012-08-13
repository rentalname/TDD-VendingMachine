package vendingmachine;

import java.util.HashSet;
import java.util.Set;

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
}
