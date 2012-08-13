package money;

public enum Money {
	Ten(10),Fifty(50),Hundred(100),FiveHundred(500),Thousand(1000),
	One(1),Five(5),TwoThousand(2000),FiveThousand(5000),TenThousand(10000),
	Zero(0);
	int value;
	Money(int num){
		value = num;
	}
	public int getAmount(){
		return value;
	}
	
}
