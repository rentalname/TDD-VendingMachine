package money;

public enum Money {
	Ten(10),Fifty(50),Hundred(100),FiveHundred(500),Thousand(1000);
	int value;
	Money(int num){
		value = num;
	}
	public int getAmount(){
		return value;
	}
}
