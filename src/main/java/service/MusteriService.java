package service;

import repository.SiparisRepository;

public class MusteriService {

	private AlisverisService alisverisService = new AlisverisService();
	private UrunYetkiService urunYetkiService = new UrunYetkiService();
	private SiparisRepository siparisRepository = new SiparisRepository();
	private SiparisService siparisService = new SiparisService();
	
	public void alisverisYap(String kullaniciAdi) {
		alisverisService.alisverisYap(kullaniciAdi);
	}
	public void urunAra() {
		urunYetkiService.UrunAraMenu();;
	}
	public void siparisGoruntule(String kullaniciAdi) {
		siparisService.siparisGoruntule(kullaniciAdi);;
	}
	public void siparisDurum(String kullaniciAdi) {
		siparisService.siparisDurum(kullaniciAdi);;
	}
	public void iadeTalepOlustur(String kullaniciAdi) {
		siparisService.iadeTalepOlustur(kullaniciAdi);
	}
}
