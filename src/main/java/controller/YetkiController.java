package controller;

import service.YetkiService;
import view.DepoGorevlisiView;
import view.KayitOlView;
import view.LoginView;
import view.MusteriView;
import view.YoneticiView;
import ch.qos.logback.core.joran.action.NewRuleAction;
import model.KullaniciModel;
import model.Role;

public class YetkiController {

	private YetkiService yetkiService = new YetkiService();
	private LoginView view = new LoginView();

	public KullaniciModel login() {
		String kullaniciAdi = view.getKullaniciAdi();
		String sifre = view.getSifre();

		return yetkiService.login(kullaniciAdi, sifre);
	}

	public void kayitOl() {
		KayitOlView kayitOlView = new KayitOlView();
		KullaniciModel yeniKullanici = new KullaniciModel();

		yeniKullanici.setadSoyad(kayitOlView.getKullaniciAdi());
		yeniKullanici.setSifre(kayitOlView.getSifre());

		int roleSecim = kayitOlView.rolSecim();

		if (roleSecim == 1) {
			yeniKullanici.setRole(Role.MUSTERI);
		} else if (roleSecim == 2) {
			yeniKullanici.setRole(Role.DEPO_GOREVLISI);
		}else if(roleSecim ==3) {
			yeniKullanici.setRole(Role.KURYE);
		}else {
			yeniKullanici.setRole(Role.YONETICI);
		}

		yetkiService.kayitOl(yeniKullanici);
	}

	public void rolEkraniniAc(KullaniciModel kullanici) {
		switch (kullanici.getRole()) {
		case MUSTERI:
			new MusteriController().menuCalistir(kullanici);
			break;
		case YONETICI:
			new YoneticiController().menuCalistir();
			break;
		case DEPO_GOREVLISI:
			new DepoController().menuCalistir();
			break;
		case KURYE:
			new KuryeController().menuGoster();
		}
	}

}
