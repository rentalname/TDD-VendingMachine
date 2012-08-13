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
	 * 投入総計額を返す
	 * 
	 * @return 現在の投入総計額
	 */
	public int getTotal() {
		return total;
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
}
