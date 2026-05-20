package controller;

import model.KullaniciModel;
import service.MusteriService;
import view.MusteriView;

public class MusteriController {

	private MusteriView musteriView = new MusteriView();
	private MusteriService musteriService = new MusteriService();

	public void menuCalistir(KullaniciModel kullanici) {
		while (true) {
			int secim = musteriView.menuGoster();

			switch (secim) {
			case 1:
				musteriService.alisverisYap(kullanici.getadSoyad());
				break;
			case 2:
				musteriService.urunAra();
				break;
			case 3:
				musteriService.siparisGoruntule(kullanici.getadSoyad());
				break;
			case 4:
				musteriService.siparisDurum(kullanici.getadSoyad());
				break;
			case 5:
				musteriService.iadeTalepOlustur(kullanici.getadSoyad());
				break;
			case 6:
				System.out.println("Çıkış yapılıyor...");
				System.exit(0);
				break;
			default:
				System.out.println("Geçersiz seçim");
			}

		}
	}
}
