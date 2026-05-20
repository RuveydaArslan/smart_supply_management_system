package view; // view paketi

import java.util.List; // liste yapısını kullanmak için eklendi
import java.util.Scanner;

import model.KargoFirmasiModel; // kargo firmasi model sınıfı
import model.OdemeYontemiModel; // ödeme yöntemi model sınıfı
import model.UrunModel; // ürün model sınıfı eklendi

public class MusteriView { // müşteri ekranını temsil eden sınıf

	Scanner scanner = new Scanner(System.in); // scanner nesnesi oluşturuldu

	public int menuGoster() { // kullanıcıya gösterilen seçenekler
		System.out.println("***** MÜŞTERİ EKRANI *****");
		System.out.println("1- Alışveriş yap");
		System.out.println("2- Ürün listele / ara");
		System.out.println("3- Siparişlerimi görüntüle");
		System.out.println("4- Sipariş durumunu takip et");
		System.out.println("5- İade talebi oluştur");
		System.out.println("6- Çıkış");
		System.out.print("Seçim: ");
		int secim = scanner.nextInt(); // kullanıcıdan seçim alınır
		scanner.nextLine(); // buffer temizliği 
		return secim; // seçim geri döndürülür
	}

	public double mesafeAl() { // kullanıcıdan km bilgisi alan metot
		System.out.print("Mesafe(km) giriniz:");
		return scanner.nextDouble(); // double olarak değer alınır ve geri döndürülür
	}

	public void urunleriGoster(List<UrunModel> urunler) { // ürün listesini ekrana yazdıran metot
		System.out.println("***** ÜRÜNLER *****");

		for (UrunModel u : urunler) { // ürünlerin tüm bilgilerini ekrana yazdırır
			System.out.println("Id: " + u.getId() + " | İsim: " + u.getAd() + " | Fiyat: " + u.getFiyat() + " | Tip: "
					+ u.getTip() + " | Stok: " + u.getStok());
		}
	}

	public void kargoListesiGoster(List<KargoFirmasiModel> kargolar) { // kargo firmalarını listeleyen metot
		System.out.println("***** KARGO FİRMALARI *****");

		for (KargoFirmasiModel k : kargolar) { // her kargo firmasını ekrana yazdırır
			System.out.println("Id: " + k.getId() + " | Firma: " + k.getAd());
		}
	}

	public void odemeListesiGoster(List<OdemeYontemiModel> odemeler) { // ödeme yöntemlerini listeleyen metot
		System.out.println("***** ÖDEME YÖNTEMLERİ *****");

		for (OdemeYontemiModel o : odemeler) { // her ödeme yöntemini ekrana yazdırır
			System.out.println("Id: " + o.getId() + " | Yöntem: " + o.getAd());
		}
	}
}
