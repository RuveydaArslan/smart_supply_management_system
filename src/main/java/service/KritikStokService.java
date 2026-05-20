package service;

import model.UrunModel;

public class KritikStokService {

	public static void bildirimGonder(UrunModel urun) {

		mailGonder(urun);
		sistemBildirimiOlustur(urun);
	}

	private static void mailGonder(UrunModel urun) {

	}

	private static void sistemBildirimiOlustur(UrunModel urun) {

	}
}
