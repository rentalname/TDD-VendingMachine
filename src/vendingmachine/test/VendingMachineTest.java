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

	private static final String SODA_POP = "Soda Pop";
	private static final String DIET_COKE = "Diet Coke";
	private static final String WATER = "water";
	private static final String MILK_TEA = "milk tea";
	private static final String RED_BULL = "Red Bull";
	private static final String COKE = "coke";
	private VendingMachine machine;

	@Before
	public void setUp() {
		machine = new VendingMachine();
		Drink coke = new Drink(COKE, 120);
		machine.store(coke, 5);
		Drink redbull = new Drink(RED_BULL, 200);
		machine.store(redbull, 4);
		Drink milktea = new Drink(MILK_TEA, 150);
		machine.store(milktea, 10);
	}

	@Test
	public void お金を投入する前での総計額を取得する() {
		assertEquals(machine.getAmount(), 0);
	}

	@Test
	public void お金を投入する() {
		machine.insert(Money.Ten);
		assertEquals(machine.getAmount(), 10);
	}

	@Test
	public void 全種類のお金を投入する() {
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.getAmount(), insertTotal);
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
		assertEquals(machine.getAmount(), 600);
	}

	@Test
	public void 払い戻し操作() {
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.refund(), insertTotal);
	}

	@Test
	public void 払い戻し操作後は投入金額が0になっている() {
		払い戻し操作();
		assertEquals(machine.getAmount(), 0);
	}

	@Test
	public void 無効なお金が投入された場合釣り銭として返ってくる() {
		assertSame(machine.insert(Money.One), Money.One);
		assertSame(machine.insert(Money.Zero), Money.Zero);
		assertSame(machine.insert(Money.TenThousand), Money.TenThousand);
	}

	@Test
	public void 格納されているジュースの情報を表示() throws VendingMachineExeption {
		Drink drink = machine.getDrink(COKE);
		String drinkName = drink.getName();
		int drinkPrice = drink.getPrice();
		assertEquals(drinkName, COKE);
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
		assertEquals(machine.getStock(COKE), 5);
		machine.store(new Drink(WATER, 100));
		assertEquals(machine.getStock(WATER), 1);
		machine.getDrink(COKE);
		assertEquals(machine.getStock(COKE), 4);
	}

	@Test
	public void ジュースが購入可能かを判断する() {
		assertFalse(machine.canParchase(COKE));
		machine.insert(Money.FiveHundred);
		assertTrue(machine.canParchase(COKE));
	}

	@Test
	public void ジュースの販売を行う() throws VendingMachineExeption {
		Drink water = new Drink(WATER, 100);
		machine.store(water);

		Drink drink = machine.vending(WATER);
		assertNull(drink);
		machine.insert(Money.Hundred);
		Drink vending = machine.vending(WATER);
		assertNotNull(vending);
		assertEquals(vending.getName(), WATER);
		assertEquals(vending.getPrice(), 100);
	}

	@Test
	public void ジュースの売買が行われたとき投入総計額を減らす() throws VendingMachineExeption {
		ジュースの販売を行う();
		assertEquals(machine.getAmount(), 0);
		machine.insert(Money.Thousand);
		Drink vending = machine.vending(RED_BULL);
		assertEquals(vending.getChange(), 800);
	}

	@Test
	public void 売上金額を取得する() throws VendingMachineExeption {
		ジュースの販売を行う();
		assertEquals(machine.getSales(), 100);
		machine.insert(Money.FiveHundred);
		machine.vending(COKE);
		assertEquals(machine.getSales(), 220);
	}

	@Test
	public void ジュース購入後の払い戻し() throws VendingMachineExeption {
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		Drink vending = machine.vending(MILK_TEA);
		assertEquals(vending.getChange(), 150);
	}

	@Test
	public void 払い戻し操作によって投入総計額は0になる() throws VendingMachineExeption {
		ジュース購入後の払い戻し();
		assertEquals(machine.getAmount(), 0);
	}

	@Test
	public void ドリンクのリストを取得する() {
		machine.store(new Drink(DIET_COKE, 130), 3);
		Collection<Drink> drinkList = machine.getDrinkCollection();
		assertEquals(drinkList.size(), 4);
	}

	@Test
	public void 在庫の存在するドリンクのリストを取得する() throws VendingMachineExeption {
		machine.store(new Drink(SODA_POP, 10));
		machine.getDrink(SODA_POP);
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

	/**
	 * 釣り銭は,ドリンクのフィールドとして設定される. 売買において,品物と釣り銭が同時に渡されるのはごく自然なことであるから.
	 * 
	 * @throws VendingMachineExeption
	 */
	@Test
	public void 釣り銭を販売と同時に払い戻す() throws VendingMachineExeption {
		machine.insert(Money.Thousand);
		Drink drink = machine.vending(RED_BULL);
		assertEquals(drink.getChange(), 800);
	}
	@Test
	public void 投入額と価格が等しいときの釣り銭() throws VendingMachineExeption{
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		Drink vending = machine.vending(RED_BULL);
		assertEquals(vending.getChange(), 0);
	}

}
