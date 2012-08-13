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
 * TODOs:�����߂����� 
 * TODO:�����߂�������s���Ɍ��݂̓������v�z�ɓ��������z�������߂����
 * TODO:�����߂������ɓ������v�z��0�ɂȂ�
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
	 * @return �������ꂽ�ݕ��̍��v���z
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
	public void ��������𕡐�����s�ł���() {
		machine.insert(Money.Fifty);
		machine.insert(Money.Fifty);
		machine.insert(Money.FiveHundred);
		assertEquals(machine.getTotal(), 600);
	}
	@Test
	public void �����߂�����(){
		int insertTotal = insertAllTypeMoney();
		assertEquals(machine.refund(), insertTotal);
	}
	@Test
	public void �����߂������͓������z��0�ɂȂ��Ă���(){
		�����߂�����();
		assertEquals(machine.getTotal(),0);
	}
	
}
