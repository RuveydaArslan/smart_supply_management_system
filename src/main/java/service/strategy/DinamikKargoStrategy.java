package service.strategy;

import model.KargoFirmasiModel;

public class DinamikKargoStrategy implements KargoStrategy {

	private KargoFirmasiModel firma;

	public DinamikKargoStrategy(KargoFirmasiModel firma) {
		this.firma = firma;

	}

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {
		return firma.getSabitUcret() + (mesafe * firma.getKmBasiUcret());
	}

}
