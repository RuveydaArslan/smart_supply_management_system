package controller;

import model.KullaniciModel;
import view.AnaEkranView;
import view.LoginView;

public class UygulamaController {

	private AnaEkranView anaEkranView = new AnaEkranView();
	private YetkiController yetkiController = new YetkiController();

	public void basla() {
		while (true) {
			int secim = anaEkranView.anaEkran();
			switch (secim) {
			case 1:
				KullaniciModel kullaniciModel = yetkiController.login();
				if (kullaniciModel != null) {
					System.out.println("Giriş başarılı: " + kullaniciModel.getadSoyad());
					System.out.println("Rol: " + kullaniciModel.getRole());

					yetkiController.rolEkraniniAc(kullaniciModel);
					return;
				} else {
					System.out.println("Hatalı giriş");
				}
				break;
			case 2:
				yetkiController.kayitOl();
				break;
			case 3:
				System.out.println("Çıkış yapılıyor...");
				System.exit(0);
				break;
			default:
				System.out.println("Geçersiz seçim");
			}
		}
	}
}
