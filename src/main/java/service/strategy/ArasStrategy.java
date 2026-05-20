package service.strategy;

public class ArasStrategy implements KargoStrategy {

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {
		return mesafe * 0.03;
	}
}
