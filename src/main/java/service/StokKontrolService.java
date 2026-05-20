package service;

import model.UrunModel;

public class StokKontrolService {

	public void kontrolEt(UrunModel urun) {

		if (urun.getStok() <= urun.getKritikStok()) {
			urun.notifyObservers();
		}
	}

}
