package service.strategy;

public class GlobalStrategy implements KargoStrategy {

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {
		return mesafe * 0.05;
	}

}
