package entity;

public class AddableParameters extends Parameters {

	public void addUp(int ncarbohydrate, int ncalorie, int nprotein, int nfat)
	{
		this.carbohydrate=+ncarbohydrate;
		this.calorie=+ncalorie;
		this.protein=+nprotein;
		this.fat=+nfat;
	}
	
	public void addUp(Parameters params)
	{
		this.carbohydrate=+params.getCarbohydrate();
		this.calorie=+params.getCalorie();
		this.protein=+params.getProtein();
		this.fat=+params.getFat();
	}
}
