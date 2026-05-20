package controller;

import service.YoneticiService;
import view.YoneticiView;

public class YoneticiController {

	private YoneticiService yoneticiService = new YoneticiService();
	private YoneticiView yoneticiView = new YoneticiView();
	public void menuCalistir() {
		
		while(true) {
			int secim = yoneticiView.menuGoster();
			
			switch (secim) {
			case 1:
				yoneticiService.musteriIslemleri();
				break;
			case 2:
				yoneticiService.depoGorevlisiIslemleri();
				break;
			case 3: 
				yoneticiService.kuryeIslemleri();
				break;
			case 4:
				yoneticiService.siparisleriGoruntule();
				break;
			case 5:
				yoneticiService.stoklarıGoruntule();
				break;
			case 6:
				yoneticiService.kargoFirmasiIslemleri();
				break;
			case 7:
				yoneticiService.odemeYontemleriIslemleri();
				break;
			case 8:
				System.out.println("Çıkış yapılıyor...");
				System.exit(0);
				break;
			default:
				System.out.println("Geçersiz seçim");;
			}
		}
	}
	
}
