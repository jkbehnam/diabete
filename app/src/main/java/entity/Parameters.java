package entity;

public class Parameters {

	int carbohydrate;
	int calorie;
	int protein;
	int fat;
	public Parameters()
	{
		this(0,0,0,0);
	}
	
	public Parameters(int carbohydrate, int calorie, int protein, int fat) {
		super();
		this.carbohydrate = carbohydrate;
		this.calorie = calorie;
		this.protein = protein;
		this.fat = fat;
	}
	public int getCarbohydrate() {
		return carbohydrate;
	}
	public int getCalorie() {
		return calorie;
	}
	public int getProtein() {
		return protein;
	}
	public int getFat() {
		return fat;
	}
	
	public void addParams(int carbo,int cal,int prot,int fat)
	{
		this.carbohydrate+=carbo;
		this.calorie+=cal;
		this.protein+=prot;
		this.fat+=fat;
	}

	public void addParams(Parameters p)
	{
		this.carbohydrate+=p.getCarbohydrate();
		this.calorie+=p.getCalorie();
		this.protein+=p.getProtein();
		this.fat+=p.getFat();
	}

	public void subtractParams(int carbo,int cal,int prot,int fat)
	{
		this.carbohydrate-=carbo;
		this.calorie-=cal;
		this.protein-=prot;
		this.fat-=fat;
	}

}
