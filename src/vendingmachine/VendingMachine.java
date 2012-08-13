package vendingmachine;

import money.Money;

public class VendingMachine {

	private int total;

	/**
	 * �������v�z��Ԃ�
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	public void insert(Money money) {
		total += money.getAmount();
	}

	public int refund() {
		int payBuckTotal = total;
		total = 0;
		return payBuckTotal;
	}

}
