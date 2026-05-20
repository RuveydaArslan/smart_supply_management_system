package factory;

public class ArasKargo implements KargoHesaplayici {

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {

		return mesafe * 0.03;
	}
}
