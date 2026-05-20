package service.observer;

import model.UrunModel;

public class SatinAlmaObserver implements StokObserver {

	@Override
	public void guncelle(UrunModel urun) {
		System.out.println("[SATIN ALMA] Mail gönderildi: " + urun.getAd());
	}

}
