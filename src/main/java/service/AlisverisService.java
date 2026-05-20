package service; // service paketi

import java.util.HashMap; // sepet için map yapısı
import java.util.List; // liste işlemleri
import java.util.Map; // key value veri yapısı
import java.util.Scanner;

import model.KargoFirmasiModel; // kargo firması modeli
import model.OdemeYontemiModel; // ödeme yöntemi modeli
import model.SiparisModel; // sipariş modeli
import model.UrunModel; // ürün modeli

// repositor paketi erişim veri erişimi için eklendi
import repository.KargoFirmasiRepository;
import repository.OdemeYontemiRepository;
import repository.SiparisRepository;
import repository.UrunRepository;
import service.facade.AlisverisFacade; // alışveriş işlemini tek noktadan yönetmek için
import view.MusteriView; // view paketi ile eklendi
import service.singletion.LogManager; // singletion log sistemi

public class AlisverisService { // alışveriş işlemlerini yöneten ana service sınıfı

	//Repository ve bağımlılıkları oluşturdum
	private OdemeYontemiRepository odemeYontemiRepository = new OdemeYontemiRepository();
	private UrunRepository urunRepository = new UrunRepository();
	private MusteriView musteriView = new MusteriView();
	private SiparisRepository siparisRepository = new SiparisRepository();
	private KargoFirmasiRepository kargoFirmasiRepository = new KargoFirmasiRepository();
	private SiparisService siparisService = new SiparisService();

	Scanner scanner = new Scanner(System.in);

	public double alisverisYap(String kullaniciAdi) { // ana alışveriş akışı

		List<UrunModel> urunler = urunRepository.urunleriOku(); // ürünler repositoryden okunur
		musteriView.urunleriGoster(urunler); // view ile ürünler kullanıcıya gösterilir

		Map<Integer, Integer> sepet = sepetOlustur(urunler); // kullanıcıdan sepet oluşturulur
		if (sepet.isEmpty()) // eğer sepet boşsa sipariş iptal edilir 
			return 0;

		double toplam = stokDusVeToplamHesapla(sepet, urunler); // stok düşürülür ve toplam fiyat hesaplanır
		String urunDetay = sepetDetayOlustur(sepet, urunler); // sepet detay stringi oluşturulur 

		double mesafe = musteriView.mesafeAl(); // mesafe bilgisi alınır

		KargoFirmasiModel kargoFirma = kargoSec(); // kargo firması seçilir
		OdemeYontemiModel odemeYontemiModel = odemeSec(); // ödeme yöntemi seçilir

		AlisverisFacade facade = new AlisverisFacade(); // facade pattern ile sipariş oluşturulur
		SiparisModel siparis = facade.satinAl(kullaniciAdi, urunDetay, toplam, mesafe, kargoFirma, odemeYontemiModel);

		double genelToplam = siparis.getToplamTutar(); // toplam tutar bilgisi

		double kargoUcreti = genelToplam - toplam; // kargo ücreti bilgisi

		ozetiYaz(toplam, kargoUcreti, genelToplam); // özet yazdır metodu çağrılarak sipariş özeti yazdırılır

		return genelToplam; // toplam bilgisi döndürülür

	}

	private Map<Integer, Integer> sepetOlustur(List<UrunModel> urunler) { // sepet oluşturma metodu
		System.out.print("Almak istediğiniz ürünler (ürünId:miktar): ");
		String input = scanner.nextLine();

		Map<Integer, Integer> sepet = new HashMap<>();

		// ürün id : alınacak miktar şeklinde değer alınır ve parçalara ayrılır
		for (String parca : input.split(" ")) {
			String[] split = parca.split(":");
			int id = Integer.parseInt(split[0]);
			int miktar = Integer.parseInt(split[1]);

			UrunModel urun = urunBul(urunler, id); // ürün bulunur

			if (urun == null) // ürün yoksa geç
				continue;

			if (urun.getStok() < miktar) // stok yeterli değilse geç
				continue;

			sepet.put(id, sepet.getOrDefault(id, 0) + miktar); // sepete eklenir
		}
		return sepet;
	}

	private UrunModel urunBul(List<UrunModel> urunler, int id) { // ürün bulma metodu
		for (UrunModel u : urunler) { //girilen ürün id ürünlerde varsa ürün döndürülür
			if (u.getId() == id)
				return u;
		}
		return null; // girilen id bilgisi yoksa null döner
	}

	private double stokDusVeToplamHesapla(Map<Integer, Integer> sepet, List<UrunModel> urunler) { // stok düşme ve toplam hesaplama metodu

		double toplam = 0;

		for (Map.Entry<Integer, Integer> e : sepet.entrySet()) {
			urunRepository.stokDusur(e.getKey(), e.getValue()); // stok düşürülür

			UrunModel u = urunBul(urunler, e.getKey()); // ürün bulunur

			toplam += u.getFiyat() * e.getValue(); // toplam fiyat hesaplanır
		}
		return toplam; // toplam bilgisi döndürülür
	}

	private String sepetDetayOlustur(Map<Integer, Integer> sepet, List<UrunModel> urunler) { // sepet detay String metodu
		StringBuilder sBuilder = new StringBuilder();

		for (Map.Entry<Integer, Integer> e : sepet.entrySet()) {
			sBuilder.append(e.getKey()).append(":").append(e.getValue()).append(",");
		}
		return sBuilder.substring(0, sBuilder.length() - 1); // son virgülün eklenmemesi için silinir
	}

	private KargoFirmasiModel kargoSec() { // kargo seçimi için metot
		List<KargoFirmasiModel> liste = kargoFirmasiRepository.kargoFirmasiListele();
		musteriView.kargoListesiGoster(liste);

		System.out.print("Kargo id: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		for (KargoFirmasiModel k : liste) {
			if (k.getId() == id)
				return k;
		}
		throw new RuntimeException("Kargo bulunamadı");
	}

	private OdemeYontemiModel odemeSec() { // ödeme yöntemi seçimi için metot
		List<OdemeYontemiModel> liste = odemeYontemiRepository.odemeYontemiListele();
		musteriView.odemeListesiGoster(liste);

		System.out.print("Seçim: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		for (OdemeYontemiModel o : liste) {
			if (o.getId() == id) {
				LogManager.getInstance().log("Odeme", o.getAd() + " seçildi"); // singletion log sistemi kullanılarak loglandı
				return o;
			}

		}
		throw new RuntimeException("Ödeme bulunamadı"); 
	}

	private void ozetiYaz(double toplam, double kargo, double genel) { // sipariş özetinin yazdırılması için metot
		System.out.println("***** ÖZET *****");
		System.out.println("Sipariş toplam: " + toplam);
		System.out.println("Kargo ücreti: " + kargo);
		System.out.println("Genel toplam: " + genel);
	}

}