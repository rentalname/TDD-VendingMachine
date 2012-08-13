package vendingmachine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import money.Money;

import org.junit.Before;
import org.junit.Test;

import vendingmachine.VendingMachine;

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
	public void �����Ȃ������������ꂽ�ꍇ�ނ�K�Ƃ��ċA���Ă���() {
		assertSame(machine.insert(Money.One), Money.One);
		assertSame(machine.insert(Money.Zero), Money.Zero);
		assertSame(machine.insert(Money.TenThousand), Money.TenThousand);
	}
}
