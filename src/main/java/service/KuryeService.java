package service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.SiparisDurumu;
import model.SiparisModel;
import repository.SiparisRepository;

public class KuryeService {

	private SiparisRepository siparisRepository = new SiparisRepository();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	Scanner scanner = new Scanner(System.in);

	public void kargodakiSiparisler() {
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();
		System.out.println("***** KARGODAKİ SİPARİŞLER *****");

		boolean bulundu = false;

		for (SiparisModel s : siparisler) {
			if (s.getDurum() == SiparisDurumu.KARGODA) {
				System.out.println("Sipariş No: " + s.getId() + " - Müşteri: " + s.getKullaniciAdi() + " - Tarih: "
						+ s.getSiparisTarihi().format(formatter) + " - Toplam: " + s.getToplamTutar());
				System.out.println("----------------------------------------------");

				bulundu = true;
			}
		}
		if (!bulundu) {
			System.out.println("Kargoda olan sipariş yok");
		}
	}

	public void siparisTeslimEdildi() {
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		if (siparisler.isEmpty()) {
			System.out.println("Sipariş yok");
			return;
		}
		System.out.println("***** KARGODA OLAN SİPARİŞLER *****");

		boolean varMi = false;

		for (SiparisModel s : siparisler) {
			if (s.getDurum() == SiparisDurumu.KARGODA) {
				System.out.println("Sipariş No: " + s.getId() + " - Durum: " + s.getDurum());
				varMi = true;
			}

		}
		if (!varMi) {
			System.out.println("Kargoda olan sipariş yok");
			return;
		}
		System.out.print("Teslim edilen sipariş no: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		for (SiparisModel s : siparisler) {
			if (s.getId() == id && s.getDurum() == SiparisDurumu.KARGODA) {
				s.setDurum(SiparisDurumu.TESLIM_EDILDI);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Geçersiz sipariş veya zaten teslim edilmiş");
			return;
		}
		siparisRepository.tumunuYenidenYaz(siparisler);
		System.out.println("Sipariş 'TESLİM EDİLDİ' olarak güncellendi");

	}

	public void iadeSureciAl() {
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		System.out.println("***** KARGODAKİ SİPARİŞLER *****");

		boolean varMi = false;

		for (SiparisModel s : siparisler) {
			if (s.getDurum() == SiparisDurumu.KARGODA) {
				System.out.println("Sipariş No: " + s.getId() + " - Müşteri: " + s.getKullaniciAdi());
			varMi =true;
			}
		}
		if(!varMi) {
			System.out.println("Kargoda sipariş yok");
			return;
		}
		System.out.print("İade edilecek sipariş no: ");
		int id = scanner.nextInt();
		
		boolean bulundu =false;
		
		for(SiparisModel s:siparisler) {
			if(s.getId() == id && s.getDurum()==SiparisDurumu.KARGODA) {
				s.setDurum(SiparisDurumu.IADE_SURECINDE);
				bulundu = true;
				break;
			}
		}
		if(!bulundu) {
			System.out.println("Geçersiz sipariş veya iadeye uygun değil");
			return;
		}
		siparisRepository.tumunuYenidenYaz(siparisler);
		System.out.println("Sipariş 'İADE SÜRECİNE' alındı");
	}
}
