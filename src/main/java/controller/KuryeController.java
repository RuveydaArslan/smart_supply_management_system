package controller;

import service.KuryeService;
import view.KuryeView;

public class KuryeController {

	private KuryeView kuryeView = new KuryeView();
	private KuryeService kuryeService = new KuryeService();
	public void menuGoster() {
		while(true) {
			int secim = kuryeView.menuGoster();
			
			switch (secim) {
			case 1:
				kuryeService.kargodakiSiparisler();
				break;
			case 2:
				kuryeService.siparisTeslimEdildi();
				break;
			case 3:
				kuryeService.iadeSureciAl();
				break;
			case 4:
				System.out.println("Çıkış yapılıyor...");
				System.exit(0);
				break;
			default:
				System.out.println("Geçersiz işlem");
			}
		}
	}
	
}
