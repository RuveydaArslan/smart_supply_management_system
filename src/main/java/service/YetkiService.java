package service;

import java.util.List;
import service.singletion.LogManager;

import model.KullaniciModel;
import repository.KullaniciRepository;

public class YetkiService {

	private KullaniciRepository kullaniciRepository = new KullaniciRepository();

	public KullaniciModel login(String kullaniciAdi, String sifre) {
		List<KullaniciModel> kullanicis = kullaniciRepository.tumKullanicilariOku();

		for (KullaniciModel kullaniciModel : kullanicis) {
			if (kullaniciModel.getadSoyad().equals(kullaniciAdi) && kullaniciModel.getSifre().equals(sifre)) {
				return kullaniciModel;
			}
		}
		return null;
	}

	public void kayitOl(KullaniciModel yeniKullanici) {
		List<KullaniciModel> kullanicis = kullaniciRepository.tumKullanicilariOku();

		for (KullaniciModel kullanici : kullanicis) {
			if ((kullanici.getadSoyad().equals(yeniKullanici.getadSoyad()))
					&& (kullanici.getSifre().equals(yeniKullanici.getSifre()))) {

				System.out.println("Bu kullanıcı zaten var");

				LogManager.getInstance().log("Kullanıcı",
						"Var olan kullanıcı kayıt denemesi: " + yeniKullanici.getadSoyad());
				return;
			}
		}
		if (yeniKullanici.getRole() == model.Role.YONETICI) {
			System.out.println("Yönetici kayıt olamaz");
			return;
		} else if (yeniKullanici.getRole() == model.Role.DEPO_GOREVLISI) {
			System.out.println("Depo görevlisini sadece yönetici kaydedebilir");
			return;
		} else if (yeniKullanici.getRole() == model.Role.KURYE) {
			System.out.println("Kuryeyi sadece yönetici kaydedebilir");
			return;
		}
		kullaniciRepository.kullaniciKaydet(yeniKullanici);
		System.out.println("Kayıt başarılı");

		LogManager.getInstance().log("Kullanıcı", "Yeni kullanıcı kaydedildi: " + yeniKullanici.getadSoyad());
	}
}
