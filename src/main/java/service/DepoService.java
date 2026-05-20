package service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.SiparisDurumu;
import model.SiparisModel;
import model.UrunModel;
import repository.SiparisRepository;
import repository.UrunRepository;
import service.observer.DepoObserver;
import service.observer.SatinAlmaObserver;

public class DepoService {

	private UrunYetkiService urunYetkiService = new UrunYetkiService();
	private UrunRepository urunRepository = new UrunRepository();
	private SiparisRepository siparisRepository = new SiparisRepository();
	Scanner scanner = new Scanner(System.in);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	public void stoklariGoster() {
		List<UrunModel> urunler = urunRepository.urunleriOku(); // ürünler repositoryden okunur

		if (urunler.isEmpty()) { // eğer ürün yoksa kullanıcı bilgilendirilir
			System.out.println("Ürün bulunamadı");
			return;
		}
		System.out.println("***** STOK DURUMU *****");

		// her ürün tek tek yazdırılır
		for (UrunModel urun : urunler) {
			System.out.println("ID: " + urun.getId() + " | Ürün: " + urun.getAd() + " | Stok: " + urun.getStok());
		}
	}

	public void urunEkle(UrunModel urun) {
		boolean sonuc = urunYetkiService.urunEkle(urun); // ürün ekleme yetkisi kontrol edilir

		if (sonuc) { // eğer ekleme başarılıysa observerlar bağlanır
			urun.addObserver(new DepoObserver()); // stok değişince depo görevlileri bilgilendirilri
			urun.addObserver(new SatinAlmaObserver()); // satın alma sitemi bilgilendirilir
			System.out.println("Ürün eklendi");
		} else {
			System.out.println("Bu ürün zaten var");
		}
	}

	public void bekleyenSiparisListe() { // beklemede olan siparişleri listeleyen metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir(); // tüm siparişler alınır
		System.out.println("***** BEKLEYEN SİPARİŞLER *****");

		boolean bulundu = false;

		for (SiparisModel s : siparisler) { // sipariş durumu beklemede olan siparişleirn bilgilerini yazdırır
			if (s.getDurum() == SiparisDurumu.BEKLEMEDE) {
				System.out.println("Sipariş No: " + s.getId() + " - Müşteri: " + s.getKullaniciAdi() + " - Tarih: "
						+ s.getSiparisTarihi().format(formatter) + " - Toplam: " + s.getToplamTutar());
				System.out.println("----------------------------------------------");

				bulundu = true;
			}
		}
		if (!bulundu) { // eğer beklemede olan sipariş yoksa mesaj gösterilir
			System.out.println("Bekleyen sipariş yok");
		}
	}

	public void siparisDetayi() { // sipariş detayını yazdıran metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir(); // tüm siparişler alınır

		if (siparisler.isEmpty()) { // eğer siparişler boşsa mesaj gösterilir 
			System.out.println("Sipariş yok");
			return;
		}
		System.out.println("***** SİPARİŞ LİSTESİ *****");
		for (SiparisModel s : siparisler) { // tüm siparişlerin bilgisi gösterilir
			System.out.println("Sipariş No: " + s.getId() + " - Müşteri: " + s.getKullaniciAdi() + " - Toplam: "
					+ String.format("%.2f", s.getToplamTutar()) + " - Durum: " + s.getDurum());
		}
		// daha detaylı görmek istediği sipariş varsa id bilgisi alınır
		System.out.print("\nDetayını görmek istediğiniz siparişin numarasını girin:");
		int id = scanner.nextInt();

		for (SiparisModel siparis : siparisler) { // detayı görünmek istenen siparişin bilgileri gösterilir
			if (siparis.getId() == id) {
				System.out.println("\n***** SİPARİŞ DETAYI *****");
				System.out.println("Sipariş No: " + siparis.getId());
				System.out.println("Müşteri: " + siparis.getKullaniciAdi());
				System.out.println("Tarih: " + siparis.getSiparisTarihi().format(formatter));
				System.out.println("Durum: " + siparis.getDurum());

				System.out.println("Ürünler: ");
				urunDetayYazdir(siparis.getUrunDetay());
				System.out.println("Toplam: " + String.format("%.2f", siparis.getToplamTutar()));
				return;

			}
		}
		System.out.println("Sipariş bulunamadı");

	}

	public void siparisHazirlaniyor() { // onaylanan  siparişleri hazırlanıyor durumuna getirmek için metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir(); // tüm siparişler alınır

		if (siparisler.isEmpty()) { // eğer hiç sipariş yoksa mesaj gösterilir
			System.out.println("Sipariş yok");
			return;
		}
		System.out.println("***** ONAYLANAN SİPARİŞLER *****");

		boolean varMi = false;

		for (SiparisModel s : siparisler) { // siparişler arasında durumu onaylandı olan sipariş listelenir
			if (s.getDurum() == SiparisDurumu.ONAYLANDI) {
				System.out.println("Sipariş No: " + s.getId() + " - Durum: " + s.getDurum());
				varMi = true;
			}

		}
		if (!varMi) { // onaylanan sipariş yoksa mesaj gösterilir
			System.out.println("Onaylanan sipariş yok");
			return;
		}
		// hazırlanacak siparişin idsi alınır 
		System.out.print("Hazırlanacak sipariş no: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		for (SiparisModel s : siparisler) { // onaylanan siparişler hazırlanıyor durumuna getirilir 
			if (s.getId() == id && s.getDurum() == SiparisDurumu.ONAYLANDI) {
				s.setDurum(SiparisDurumu.HAZIRLANIYOR);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Geçersiz sipariş veya zaten hazırlanmış");
			return;
		}
		siparisRepository.tumunuYenidenYaz(siparisler);// hazırlanıyor durumuna getirilen sipariş kaydedilir
		System.out.println("Sipariş 'HAZIRLANIYOR' olarak güncellendi");
	}

	public void siparisKargoda() { // hazırlanıyor durumunda olan siparişleri kargoda durumuna getirmek için kullanılan metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir(); // tüm siparişler alınır

		if (siparisler.isEmpty()) { // hiç sipariş yoksa mesaj gösterilir
			System.out.println("Sipariş yok");
			return;
		}
		System.out.println("***** HAZIRLANAN SİPARİŞLER *****");
		boolean varMi = false;

		for (SiparisModel s : siparisler) { // hazırlanıyor durumunda olan siparişler gösterilir
			if (s.getDurum() == SiparisDurumu.HAZIRLANIYOR) {
				System.out.println("Sipariş No: " + s.getId() + " - Durum: " + s.getDurum());
				varMi = true;
			}
		}
		if (!varMi) { // hazırlanan sipariş yoksa
			System.out.println("Hazırlanan sipariş yok");
			return;
		}
		// kargoya verilecek siparişin id bilgisi alınır
		System.out.print("Kargoya verilecek siparis no: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		// girilen id varsa ve hazırlanıyor durumdaysa kargoda durumuna getirilir
		for (SiparisModel s : siparisler) {
			if (s.getId() == id && s.getDurum() == SiparisDurumu.HAZIRLANIYOR) {
				s.setDurum(SiparisDurumu.KARGODA);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Geçersiz sipariş veya zaten kargoya verilmiş");
			return;
		}
		siparisRepository.tumunuYenidenYaz(siparisler); // kargoda durumuna getirilen sipariş kaydedilir ve mesaj gösterilir
		System.out.println("Sipariş 'KARGODA' olarak güncellendi");
	}

	public void kritikStokGoster() { // kritik stok değeri veya daha altında olan ürünleri gösteren metot
		List<UrunModel> urunler = urunRepository.urunleriOku(); // tüm ürünleri okur

		boolean varMi = false;

		System.out.println("***** KRİTİK STOKTAKİ ÜRÜNLER *****");

		// kritik durumda olan ürünlerin bilgileri gösterilir
		for (UrunModel urun : urunler) {
			if (urun.getStok() <= urun.getKritikStok()) {
				System.out.println("----------------------------------");
				System.out.println("Ürün: " + urun.getAd());
				System.out.println("Stok: " + urun.getStok());
				System.out.println("Kritik Seviye: " + urun.getKritikStok());

				varMi = true;
			}
		}
		if (!varMi) { // eğer kritik stok durumunda ürün yoksa mesaj yazdırılır
			System.out.println("Kritik stokta ürün yok");
		}
	}

	public void siparisOnayla() { // beklemede olan siparişleri onaylandı durumuna getirmek için kullanılan metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();  // tüm siparişler okunur

		if (siparisler.isEmpty()) { // eğer hiç sipariş yoksa mesaj gösterilir
			System.out.println("Sipariş yok");
			return;
		}
		System.out.println("***** BEKLEYEN SİPARİŞLER *****");

		boolean varMi = false;

		// beklemede olan siparişler gösterilir
		for (SiparisModel s : siparisler) {
			if (s.getDurum() == SiparisDurumu.BEKLEMEDE) {
				System.out.println("Sipariş No: " + s.getId() + " - Durum: " + s.getDurum());
				varMi = true;
			}

		}
		if (!varMi) {
			System.out.println("Bekleyen sipariş yok");
			return;
		}
		// onaylancak siparişin id bilgisi alınır
		System.out.print("Onaylanacak sipariş no: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		// alınan id değeri varsa ve beklemede durumundaysa siparişi onaylandı durumuna getirir
		for (SiparisModel s : siparisler) {
			if (s.getId() == id && s.getDurum() == SiparisDurumu.BEKLEMEDE) {
				s.setDurum(SiparisDurumu.ONAYLANDI);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Geçersiz sipariş veya zaten onaylanmış");
			return;
		}
		// onaylandı durumuna getirilen sipariş kaydedilir ve msaj gösterilir
		siparisRepository.tumunuYenidenYaz(siparisler);
		System.out.println("Sipariş 'ONAYLANDI' olarak güncellendi");
	}

	public void iadeTalepListele() { // iade taleplerini listeleyen metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		System.out.println("***** İADE TALEPLERİ *****");

		boolean varMi = false;

		for (SiparisModel s : siparisler) {
			if (s.getDurum() == SiparisDurumu.IADE_SURECINDE) {
				System.out.println("Sipariş No: " + s.getId() + " - Müşteri: " + s.getKullaniciAdi() + " - Toplam: "
						+ String.format("%.2f", s.getToplamTutar()));
				varMi = true;
			}
		}
		if (!varMi) {
			System.out.println("Aktif iade talebi yok");
		}
	}

	public void iadeTalepOnayla() { // iade sürecinde olan siparişleri iade edildi durumuna getirmek için kullanılan metot
		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		System.out.println("***** İADE ONAY LİSTESİ *****");

		boolean varMi = false;

		// iade sürecinde olan siparişleri listeler
		for (SiparisModel s : siparisler) {
			if (s.getDurum() == SiparisDurumu.IADE_SURECINDE) {
				System.out.println("Sipariş No: " + s.getId() + " - Müşteri: " + s.getKullaniciAdi());

				varMi = true;
			}
		}
		if (!varMi) {
			System.out.println("Onaylanacak iade yok");
			return;
		}
		// onaylanacak sipariş id bilgisi alınır
		System.out.print("Onaylanacak iade sipariş no: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		// iade surecinde olan ve alınan id bilgisi olan sipariş iade edildi durumuna getirilir
		for (SiparisModel s : siparisler) {
			if (s.getId() == id && s.getDurum() == SiparisDurumu.IADE_SURECINDE) {
				s.setDurum(SiparisDurumu.IADE_EDILDI);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Geçersiz sipariş");
			return;
		}
		// iade edildi durumuna getirilen sipariş kaydedilir
		siparisRepository.tumunuYenidenYaz(siparisler);
		System.out.println("İade onaylandı");
	}

	private void urunDetayYazdir(String urunDetay) { // alınan ürünlerin daha detaylı görünmesi için kullanılan metot

		String[] urunlerStrings = urunDetay.split(",");
		for (String urunString : urunlerStrings) {
			String[] parcaStrings = urunString.split(":");

			if (parcaStrings.length < 2)
				continue;

			int id = Integer.parseInt(parcaStrings[0]);
			int miktar = Integer.parseInt(parcaStrings[1]);

			UrunModel urunModel = urunRepository.idIleGetir(id);

			if (urunModel != null) {
				System.out.println("- " + urunModel.getAd() + " x" + miktar);
			}
		}
	}

	public void stokGuncelle() { // güncellenecek ürün stok için kullanılan metot

		List<UrunModel> urunler = urunRepository.urunleriOku();

		if (urunler.isEmpty()) {
			System.out.println("Ürün yok");
			return;
		}
		System.out.println("***** ÜRÜNLER *****");

		for (UrunModel u : urunler) {
			System.out.println("Ürün id: " + u.getId() + " - " + "Ürün adı: " + u.getAd() + " - Stok: " + u.getStok());

		}

		System.out.print("Güncellenecek ürün id: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		UrunModel secilen = null;

		for (UrunModel u : urunler) {
			if (u.getId() == id) {
				secilen = u;
				break;

			}
		}
		if (secilen == null) {
			System.out.println("Ürün bulunamadı");
			return;
		}
		System.out.print("Eklenecek miktar: ");
		int miktar = scanner.nextInt();
		scanner.nextLine();

		if (miktar <= 0) {
			System.out.println("Geçersiz miktar");
			return;
		}
		// ürün eklendikten sonra eğer stok kritik stok eşiğinde değilse haber verilir
		if (secilen.getStok() > secilen.getKritikStok()) {
			System.out.println("Ürün artık kritik stokta değil");
		}
		// eklenen stok miktarı var olan stok değeri ile toplanıp güncellenir
		secilen.setStok(secilen.getStok() + miktar);

		// ürün bilgisindeki stok bilgisi kaydedilir
		urunRepository.urunleriYenidenYaz(urunler);

		// güncellendi mesajı ve yeni stok bilgisi gösterilir
		System.out.println("Tedarikçiden ürün temin edildi, stok güncellendi");
		System.out.println("Yeni stok: " + secilen.getStok());
	}

}
