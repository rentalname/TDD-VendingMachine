/**
 * 
 */
package vendingmachine.test;

import static org.junit.Assert.*;

import money.Money;

import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.regexp.internal.recompile;

import vendingmachine.VendingMachine;

/**
 * TODOs:払い戻し操作 
 * TODO:払い戻し操作実行時に現在の投入総計額に等しい金額が払い戻される
 * TODO:払い戻し操作後に投入総計額は0になる
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
	 * @return 投入された貨幣の合計金額
	 */
	private int insertAllTypeMoney() {
		int insertTotalAmount = 0;
		for(Money m:Money.values()){
			machine.insert(m);
			insertTotalAmount += m.getAmount();
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
	public void 払い戻し操作(){
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.refund(), insertTotal);
	}
	@Test
	public void 払い戻し操作後は投入金額が0になっている(){
		払い戻し操作();
		assertEquals(machine.getTotal(),0);
	}
	
}
