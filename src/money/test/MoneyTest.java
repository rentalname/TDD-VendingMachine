package money.test;

import static org.junit.Assert.*;

import money.Money;

import org.junit.Test;

/*
 * 列挙されている貨幣について金額が正しく取得できることをテストする
 */
public class MoneyTest {

	@Test
	public final void 正しい金額が取得できることをテストする() {
		assertEquals(Money.Zero.getAmount(), 0);
		assertEquals(Money.One.getAmount(), 1);
		assertEquals(Money.Five.getAmount(), 5);
		assertEquals(Money.TwoThousand.getAmount(), 2000);
		assertEquals(Money.FiveThousand.getAmount(), 5000);
		assertEquals(Money.TenThousand.getAmount(), 10000);
		assertEquals(Money.Ten.getAmount(), 10);
		assertEquals(Money.Fifty.getAmount(), 50);
		assertEquals(Money.Hundred.getAmount(), 100);
		assertEquals(Money.FiveHundred.getAmount(), 500);
		assertEquals(Money.Thousand.getAmount(), 1000);
	}

}
