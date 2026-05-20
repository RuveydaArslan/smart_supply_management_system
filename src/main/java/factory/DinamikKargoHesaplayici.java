package factory;

import model.KargoFirmasiModel;

public class DinamikKargoHesaplayici implements KargoHesaplayici {

	private KargoFirmasiModel firma;
	
	public DinamikKargoHesaplayici(KargoFirmasiModel firma) {
		this.firma = firma;
	}
	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {
		return firma.getSabitUcret()+(mesafe*firma.getKmBasiUcret());
	}

}
