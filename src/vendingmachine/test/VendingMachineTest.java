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
	public void �����𓊓�����O�ł̑��v�z���擾����() {
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
	public void �i�[����Ă���W���[�X�̏���\��() throws VendingMachineExeption {
		Drink drink = machine.getDrink("coke");
		String drinkName = drink.getName();
		int drinkPrice = drink.getPrice();
		assertEquals(drinkName, "coke");
		assertEquals(drinkPrice, 120);
	}

	@Test
	public void �W���[�X���i�[����() throws VendingMachineExeption {
		String drinkName = "drinkA";
		machine.store(new Drink(drinkName, 200));
		Drink drinkA = machine.getDrink(drinkName);
		assertEquals(drinkA.getName(), drinkName);
		assertEquals(drinkA.getPrice(), 200);
	}

	@Test
	public void �w�肵���W���[�X�̍݌ɗʂ�Ԃ�() throws VendingMachineExeption {
		assertEquals(machine.getStock("coke"), 5);
		machine.store(new Drink("water", 100));
		assertEquals(machine.getStock("water"), 1);
		machine.getDrink("coke");
		assertEquals(machine.getStock("coke"), 4);
	}

	@Test
	public void �W���[�X���w���\���𔻒f����() {
		assertFalse(machine.canParchase("coke"));
		machine.insert(Money.FiveHundred);
		assertTrue(machine.canParchase("coke"));
	}

	@Test
	public void �W���[�X�̔̔����s��() throws VendingMachineExeption {
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
	public void �W���[�X�̔������s��ꂽ�Ƃ��������v�z�����炷() throws VendingMachineExeption {
		�W���[�X�̔̔����s��();
		assertEquals(machine.getTotal(), 0);
		machine.insert(Money.Thousand);
		machine.vending("Red Bull");
		assertEquals(machine.getTotal(), 800);
	}

	@Test
	public void ������z���擾����() throws VendingMachineExeption {
		�W���[�X�̔̔����s��();
		assertEquals(machine.getSales(), 100);
		machine.insert(Money.FiveHundred);
		machine.vending("coke");
		assertEquals(machine.getSales(), 220);
	}

	@Test
	public void �W���[�X�w����̕����߂�() throws VendingMachineExeption {
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		machine.insert(Money.Hundred);
		machine.vending("milk tea");
		assertEquals(machine.refund(), 150);
	}

	@Test
	public void �����߂�����ɂ���ē������v�z��0�ɂȂ�() throws VendingMachineExeption {
		�W���[�X�w����̕����߂�();
		assertEquals(machine.getTotal(), 0);
	}

	@Test
	public void �h�����N�̃��X�g���擾����() {
		machine.store(new Drink("Diet Coke", 130), 3);
		Collection<Drink> drinkList = machine.getDrinkCollection();
		assertEquals(drinkList.size(), 4);
	}

	@Test
	public void �݌ɂ̑��݂���h�����N�̃��X�g���擾����() throws VendingMachineExeption {
		machine.store(new Drink("Soda Pop", 10));
		machine.getDrink("Soda Pop");
		Collection<Drink> hasStock = machine.hasStockDrinkCollection();
		assertEquals(hasStock.size(), 3);
	}

	@Test
	public void �w���\�ȃh�����N�̃��X�g���擾����() {
		machine.insert(Money.Hundred);
		machine.insert(Money.Fifty);
		Collection<Drink> parchaseList = machine.canParchaseList();
		assertEquals(parchaseList.size(), 2);
	}
}
