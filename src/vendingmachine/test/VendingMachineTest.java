package vendingmachine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import money.Money;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.VendingMachine;
import vendingmachine.VendingMachineExeption;
import vendingmachine.store.Drink;

/**
 * 
 * @author Hi
 * 
 */
public class VendingMachineTest {

	private VendingMachine machine;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		machine = new VendingMachine();
	}

	@Test
	public final void お金を投入する前での総計額を取得する() {
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
	public void 格納されているジュースの情報を表示() throws VendingMachineExeption{
		Drink drink = machine.getDrink("coke");
		String drinkName = drink.getName();
		int drinkPrice = drink.getPrice();
		assertEquals(drinkName, "coke");
		assertEquals(drinkPrice, 120);
	}
	@Test
	public void ジュースを格納する() throws VendingMachineExeption{
		machine.store(new Drink("redbull", 200));
		Drink redBull = machine.getDrink("redbull");
		assertEquals(redBull.getName(), "redbull");
		assertEquals(redBull.getPrice(), 200);
	}
	@Test
	public void 指定したジュースの在庫量を返す() throws VendingMachineExeption{
		assertEquals(machine.getStock("coke"), 5);
		machine.store(new Drink("water",100));
		assertEquals(machine.getStock("water"),1);
		machine.getDrink("coke");
		assertEquals(machine.getStock("coke"), 4);
	}
	
}
