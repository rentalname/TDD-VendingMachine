package vendingmachine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import money.Money;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.VendingMachine;
import vendingmachine.VendingMachineExeption;
import vendingmachine.store.Drink;

public class VendingMachineTest {

	private VendingMachine machine;

	@Before
	public void setUp(){
		machine = new VendingMachine();
		Drink coke = new Drink("coke", 120);
		machine.store(coke, 5);
		Drink redbull = new Drink("Red Bull", 200);
		machine.store(redbull, 4);
		Drink milktea = new Drink("milk tea", 150);
		machine.store(milktea, 10);
	}

	@Test
	public void お金を投入する前での総計額を取得する() {
		assertEquals(machine.getTotal(), 0);
	}

	@Test
	public void お金を投入する() {
		machine.insert(Money.Ten);
		assertEquals(machine.getTotal(), 10);
	}

	@Test
	public void 全種類のお金を投入する() {
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.getTotal(), insertTotal);
	}

	/**
	 * {@link Money}で列挙される全種類の貨幣を投入する
	 * 
	 * @return 投入された貨幣の合計金額
	 */
	private int insertAllTypeMoney() {
		int insertTotalAmount = 0;
		for (Money m : Money.values()) {
			Money refund = machine.insert(m);
			if (refund == Money.Zero) {
				insertTotalAmount += m.getAmount();
			}
		}
		return insertTotalAmount;
	}

	@Test
	public void 投入操作を複数回実行できる() {
		machine.insert(Money.Fifty);
		machine.insert(Money.Fifty);
		machine.insert(Money.FiveHundred);
		assertEquals(machine.getTotal(), 600);
	}

	@Test
	public void 払い戻し操作() {
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.refund(), insertTotal);
	}

	@Test
	public void 払い戻し操作後は投入金額が0になっている() {
		払い戻し操作();
		assertEquals(machine.getTotal(), 0);
	}

	@Test
	public void 無効なお金が投入された場合釣り銭として返ってくる() {
		assertSame(machine.insert(Money.One), Money.One);
		assertSame(machine.insert(Money.Zero), Money.Zero);
		assertSame(machine.insert(Money.TenThousand), Money.TenThousand);
	}

	@Test
	public void 格納されているジュースの情報を表示() throws VendingMachineExeption {
		Drink drink = machine.getDrink("coke");
		String drinkName = drink.getName();
		int drinkPrice = drink.getPrice();
		assertEquals(drinkName, "coke");
		assertEquals(drinkPrice, 120);
	}

	@Test
	public void ジュースを格納する() throws VendingMachineExeption {
		String drinkName = "drinkA";
		machine.store(new Drink(drinkName, 200));
		Drink drinkA = machine.getDrink(drinkName);
		assertEquals(drinkA.getName(), drinkName);
		assertEquals(drinkA.getPrice(), 200);
	}

	@Test
	public void 指定したジュースの在庫量を返す() throws VendingMachineExeption {
		assertEquals(machine.getStock("coke"), 5);
		machine.store(new Drink("water", 100));
		assertEquals(machine.getStock("water"), 1);
		machine.getDrink("coke");
		assertEquals(machine.getStock("coke"), 4);
	}

	@Test
	public void ジュースが購入可能かを判断する() {
		assertFalse(machine.canParchase("coke"));
		machine.insert(Money.FiveHundred);
		assertTrue(machine.canParchase("coke"));
	}

	@Test
	public void ジュースの販売を行う() throws VendingMachineExeption {
		Drink water = new Drink("water", 100);
		machine.store(water);

		Drink drink = machine.vending("water");
		assertNull(drink);
		machine.insert(Money.Hundred);
		Drink vending = machine.vending("water");
		assertNotNull(vending);
		assertEquals(vending.getName(), "water");
		assertEquals(vending.getPrice(), 100);
	}

	@Test
	public void ジュースの売買が行われたとき投入総計額を減らす() throws VendingMachineExeption {
		ジュースの販売を行う();
		assertEquals(machine.getTotal(), 0);
		machine.insert(Money.Thousand);
		machine.vending("Red Bull");
		assertEquals(machine.getTotal(), 800);
	}

	@Test
	public void 売上金額を取得する() throws VendingMachineExeption {
		ジュースの販売を行う();
		assertEquals(machine.getSales(), 100);
		machine.insert(Money.FiveHundred);
		machine.vending("coke");
		assertEquals(machine.getSales(), 220);
	}

	@Test
	public void ジュース購入後の払い戻し() throws VendingMachineExeption {
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		machine.vending("milk tea");
		assertEquals(machine.refund(), 150);
	}

	@Test
	public void 払い戻し操作によって投入総計額は0になる() throws VendingMachineExeption {
		ジュース購入後の払い戻し();
		assertEquals(machine.getTotal(), 0);
	}

	@Test
	public void ドリンクのリストを取得する() {
		machine.store(new Drink("Diet Coke", 130), 3);
		Collection<Drink> drinkList = machine.getDrinkCollection();
		assertEquals(drinkList.size(), 4);
	}

	@Test
	public void 在庫の存在するドリンクのリストを取得する() throws VendingMachineExeption {
		machine.store(new Drink("Soda Pop", 10));
		machine.getDrink("Soda Pop");
		Collection<Drink> hasStock = machine.hasStockDrinkCollection();
		assertEquals(hasStock.size(), 3);
	}

	@Test
	public void 購入可能なドリンクのリストを取得する() {
		machine.insert(Money.Hundred);
		machine.insert(Money.Fifty);
		Collection<Drink> parchaseList = machine.canParchaseList();
		assertEquals(parchaseList.size(), 2);
	}
}
