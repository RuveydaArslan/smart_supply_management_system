package service.strategy;

public class YurticiStrategy implements KargoStrategy {

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {
		return mesafe * 0.04;
	}

}
