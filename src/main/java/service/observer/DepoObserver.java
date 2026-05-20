package service.observer;

import model.UrunModel;

public class DepoObserver implements StokObserver {

	@Override
	public void guncelle(UrunModel urun) {
		System.out.println("[DEPO] Stok kritik seviyede: " + urun.getAd());
	}

}
