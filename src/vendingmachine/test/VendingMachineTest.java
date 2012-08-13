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
	public final void �����𓊓�����O�ł̑��v�z���擾����() {
		assertEquals(machine.getTotal(), 0);
	}

	@Test
	public void �����𓊓�����() {
		machine.insert(Money.Ten);
		assertEquals(machine.getTotal(), 10);
	}

	@Test
	public void �S��ނ̂����𓊓�����() {
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.getTotal(), insertTotal);
	}

	/**
	 * {@link Money}�ŗ񋓂����S��ނ̉ݕ��𓊓�����
	 * 
	 * @return �������ꂽ�ݕ��̍��v���z
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
	public void ��������𕡐�����s�ł���() {
		machine.insert(Money.Fifty);
		machine.insert(Money.Fifty);
		machine.insert(Money.FiveHundred);
		assertEquals(machine.getTotal(), 600);
	}

	@Test
	public void �����߂�����() {
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.refund(), insertTotal);
	}

	@Test
	public void �����߂������͓������z��0�ɂȂ��Ă���() {
		�����߂�����();
		assertEquals(machine.getTotal(), 0);
	}

	@Test
	public void �����Ȃ������������ꂽ�ꍇ�ނ�K�Ƃ��ĕԂ��Ă���() {
		assertSame(machine.insert(Money.One), Money.One);
		assertSame(machine.insert(Money.Zero), Money.Zero);
		assertSame(machine.insert(Money.TenThousand), Money.TenThousand);
	}
	@Test
	public void �i�[����Ă���W���[�X�̏���\��() throws VendingMachineExeption{
		Drink drink = machine.getDrink("coke");
		String drinkName = drink.getName();
		int drinkPrice = drink.getPrice();
		assertEquals(drinkName, "coke");
		assertEquals(drinkPrice, 120);
	}
	@Test
	public void �W���[�X���i�[����() throws VendingMachineExeption{
		machine.store(new Drink("redbull", 200));
		Drink redBull = machine.getDrink("redbull");
		assertEquals(redBull.getName(), "redbull");
		assertEquals(redBull.getPrice(), 200);
	}
	@Test
	public void �w�肵���W���[�X�̍݌ɗʂ�Ԃ�() throws VendingMachineExeption{
		assertEquals(machine.getStock("coke"), 5);
		machine.store(new Drink("water",100));
		assertEquals(machine.getStock("water"),1);
		machine.getDrink("coke");
		assertEquals(machine.getStock("coke"), 4);
	}
	
}
