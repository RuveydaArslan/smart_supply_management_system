package factory;

public class YurtIciKargo implements KargoHesaplayici {

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {

		return mesafe * 0.04;
	}
}
