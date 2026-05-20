package factory;

public class GlobalExpres implements KargoHesaplayici {

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {

		return mesafe * 0.05;
	}
}
