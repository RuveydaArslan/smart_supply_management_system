package controller;

import model.UrunModel;
import service.DepoService;
import service.UrunYetkiService;
import view.DepoGorevlisiView;
import view.UrunEkleView;

public class DepoController {

	private DepoGorevlisiView depoGorevlisiView = new DepoGorevlisiView();
	private DepoService depoService = new DepoService();
	private UrunYetkiService urunYetkiService = new UrunYetkiService();
	private UrunModel urunModel =new UrunModel();
	private UrunEkleView urunEkleView = new UrunEkleView();

	public void menuCalistir() {
	
		while (true) {
			
			int secim = depoGorevlisiView.menuGoster();

			switch (secim) {
			case 1:
				depoService.bekleyenSiparisListe();
				break;
			case 2:
				depoService.siparisDetayi();
				break;
			case 3:
				depoService.siparisOnayla();
				break;
			case 4:
				depoService.siparisHazirlaniyor();
				break;
			case 5:
				depoService.siparisKargoda();
				break;
			case 6:
				depoService.iadeTalepListele();
				break;
			case 7:
				depoService.iadeTalepOnayla();
				break;
			case 8:
				depoService.stoklariGoster();
				break;
			case 9:
				depoService.stokGuncelle();
				break;
			case 10:
				UrunModel urunModel = urunEkleView.urunEkle();
				depoService.urunEkle(urunModel);
			case 11:
				depoService.kritikStokGoster();
				break;
			case 12:
				System.out.println("Çıkış yapılıyor...");
				System.exit(0);
				break;
			default:
				System.out.println("Geçersiz seçim");
			}
		}
	}
}