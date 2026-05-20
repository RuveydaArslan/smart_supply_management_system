package service;

import java.nio.channels.Pipe.SourceChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.KullaniciModel;
import model.SiparisDurumu;
import model.SiparisModel;
import model.UrunModel;
import repository.SiparisRepository;
import repository.UrunRepository;
import service.singletion.LogManager;

public class SiparisService {

	private UrunRepository urunRepository = new UrunRepository();
	private SiparisRepository siparisRepository = new SiparisRepository();
	Scanner scanner = new Scanner(System.in);

	public void siparisGoruntule(String kullaniciAdi) {
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		boolean bulundu = false;

		System.out.println("***** SİPARİŞLER *****");

		for (SiparisModel s : siparisler) {
			if (s.getKullaniciAdi().equals(kullaniciAdi)) {
				System.out.println("--------------------------------------------");
				System.out.println("Sipariş no: " + s.getId());
				System.out.println("Durum: " + s.getDurum());
				System.out.println("Tarih: " + s.getSiparisTarihi());
				System.out.println("Toplam: " + String.format("%.2f", s.getToplamTutar()));

				System.out.println("Ürünler: ");
				System.out.println(urunDetayFormatla(s.getUrunDetay()));
				bulundu = true;
			}
		}
		if (!bulundu) {
			System.out.println("Sipariş bulunamadı");
		}
	}

	public void siparisHazirlaniyor() {
	    List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

	    if (siparisler.isEmpty()) {
	        System.out.println("Sipariş yok");
	        return;
	    }

	    System.out.println("***** SİPARİŞLER *****");
	    for (SiparisModel s : siparisler) {
	        System.out.println(s.getId() + " - " + s.getKullaniciAdi() + " - " + s.getDurum());
	    }

	    System.out.print("Sipariş id giriniz:");
	    int id = scanner.nextInt();
	    scanner.nextLine();

	    boolean bulundu = false;

	    for (SiparisModel siparis : siparisler) {
	        if (siparis.getId() == id) {

	            SiparisDurumu eskiDurum = siparis.getDurum();
	            siparis.setDurum(SiparisDurumu.HAZIRLANIYOR);

	            LogManager.getInstance().log(
	                "Siparis",
	                "Durum değişti | Id: " + siparis.getId() +
	                " | " + eskiDurum + " -> HAZIRLANIYOR"
	            );

	            bulundu = true;
	            break;
	        }
	    }

	    if (!bulundu) {
	        System.out.println("Sipariş bulunamadı");

	        LogManager.getInstance().log(
	            "Hata",
	            "Sipariş bulunamadı | Id: " + id
	        );

	        return;
	    }

	    siparisRepository.tumunuYenidenYaz(siparisler);

	    System.out.println("Sipariş 'HAZIRLANIYOR' olarak güncellendi");
	}

	public void siparisKargoyaHazirlaniyor() {
		List<SiparisModel> siparislerList = siparisRepository.tumSiparisleriGetir();

		if (siparislerList.isEmpty()) {
			System.out.println("Sipariş yok");
			return;
		}
		System.out.println("***** SİPARİŞLER *****");
		for (SiparisModel s : siparislerList) {
			System.out.println(s.getId() + " - " + s.getKullaniciAdi() + " - " + s.getDurum());
		}
		System.out.print("Sipariş id giriniz: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		boolean bulundu = false;

		for (SiparisModel siparis : siparislerList) {
			if (siparis.getId() == id) {
				SiparisDurumu eskiDurum = siparis.getDurum();
				siparis.setDurum(SiparisDurumu.KARGODA);
				LogManager.getInstance().log(
		                "Siparis",
		                "Durum değişti | Id: " + siparis.getId() +
		                " | " + eskiDurum + " ->	KARGODA"
		            );
				
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Sipariş bulunamadı");
			
			LogManager.getInstance().log(
		            "Hata",
		            "Sipariş bulunamadı | Id: " + id
		        );
			
			return;
		}
		siparisRepository.tumunuYenidenYaz(siparislerList);
		System.out.println("Sipariş 'KARGOYA HAZIR' olarak güncellendi");
	}

	public void siparisDurum(String kullaniciAdi) {
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		boolean bulundu = false;

		System.out.println("***** SİPARİŞLERİM *****");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		for (SiparisModel siparis : siparisler) {
			if (siparis.getKullaniciAdi().equals(kullaniciAdi)) {
				System.out.println("-------------------------------------");
				System.out.println("Sipariş no: " + siparis.getId());
				System.out.println("Durum: " + siparis.getDurum());

				if (siparis.getSiparisTarihi() != null) {
					System.out.println("Tarih: " + siparis.getSiparisTarihi().format(formatter));
					;
				}
				System.out.println("Toplam: " + String.format("%.2f", siparis.getToplamTutar()));

				bulundu = true;
			}
		}
		if (!bulundu) {
			System.out.println("Henüz siparişiniz yok");
		}
	}

	public void iadeTalepOlustur(String kullaniciAdi) {
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		System.out.println("***** TESLİM EDİLMİŞ SİPARİŞLER *****");

		boolean varMi = false;

		for (SiparisModel s : siparisler) {
			if (s.getKullaniciAdi().equals(kullaniciAdi) && s.getDurum() == SiparisDurumu.TESLIM_EDILDI) {

				System.out.println(
						"Sipariş No: " + s.getId() + " - Toplam: " + String.format("%.2f", s.getToplamTutar()));

				varMi = true;
			}
		}
		if (!varMi) {
			System.out.println("İade edilebilecek sipariş bulunamadı");
			return;
		}
		System.out.print("İade talebi oluşturacak sipariş id: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		for (SiparisModel s : siparisler) {
			if (s.getId() == id && s.getKullaniciAdi().equals(kullaniciAdi)
					&& s.getDurum() == SiparisDurumu.TESLIM_EDILDI) {
				SiparisDurumu eskiDurum = s.getDurum();
				s.setDurum(SiparisDurumu.IADE_SURECINDE);
				
				LogManager.getInstance().log(
		                "Siparis",
		                "Durum değişti | Id: " + s.getId() +
		                " | " + eskiDurum + " ->	İADE TALEBİ "
		            );
				
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Geçersiz sipariş veya iade edilemez durumda");
			
			LogManager.getInstance().log(
		            "Hata",
		            "Sipariş bulunamadı | Id: " + id
		        );
			
			return;
		}
		siparisRepository.tumunuYenidenYaz(siparisler);

		System.out.println("İade talebi oluşturuldu");
	}

	public String urunDetayFormatla(String urunDetay) {
		List<UrunModel> urunlerList = urunRepository.urunleriOku();

		StringBuilder sBuilder = new StringBuilder();

		String[] itemStrings = urunDetay.split(",");

		for (String item : itemStrings) {
			String[] parca = item.split(":");

			int urunId = Integer.parseInt(parca[0]);
			int miktar = Integer.parseInt(parca[1]);

			for (UrunModel u : urunlerList) {
				if (u.getId() == urunId) {
					sBuilder.append(u.getAd()).append(" x").append(miktar).append("\n");
					break;
				}
			}
		}
		return sBuilder.toString();
	}

	public SiparisModel siparisOlustur(String kullaniciAdi, String urunDetay, double toplam, double kargoUcreti) {

		SiparisModel siparis = new SiparisModel();
		siparis.setKullaniciAdi(kullaniciAdi);
		siparis.setUrunDetay(urunDetay);
		siparis.setToplamTutar(toplam + kargoUcreti);
		siparis.setSiparisTarihi(LocalDateTime.now());
		siparis.setDurum(SiparisDurumu.BEKLEMEDE);

		siparisRepository.kaydet(siparis);

		LogManager.getInstance().log("Siparis", "kullanıcı: " + kullaniciAdi + " sipariş oluşturuldu");
		return siparis;
	}
}
