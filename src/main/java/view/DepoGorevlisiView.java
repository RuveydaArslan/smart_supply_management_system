package view; // view paketi

import java.util.Scanner; // Kullanıcıdan veri almak için Scanner sınıfı ekledim


public class DepoGorevlisiView { // depo görevlisinin kullanacağı ekranı temsil eden sınıf

	private Scanner scanner = new Scanner(System.in); // depo görevlisinden sürekli veri almak için tek bir Scanner nesnesi oluşturdum

	public int menuGoster() { // menü ekranını gösteren ve kullanıcının seçimini döndüren metot
		// depo görevlisine sunulan işlemler
		System.out.println("***** DEPO GÖREVLİSİ EKRANI *****");
		System.out.println("1- Bekleyen siparişleri listele");
		System.out.println("2- Sipariş detayını görüntüle");
		System.out.println("3- Siparişi ONAYLA");
		System.out.println("4- Siparişi 'HAZIRLANIYOR' yap");
		System.out.println("5- Siparişi 'KARGODA' yap");
		System.out.println("6- İade taleplerini listele");
		System.out.println("7- İade talebini onayla");
		System.out.println("8- Stokları görüntüle");
		System.out.println("9- Stok güncelle");
		System.out.println("10- Ürün ekle");
		System.out.println("11- Kritik stok uyarıları");
		System.out.println("12- Çıkış");
		System.out.print("Seçim: ");
		int secim = scanner.nextInt(); // kullanıcının sayısal girişi alınır
		scanner.nextLine(); // nextInt sonrasında bufferda kalan karakteri temizlemek için ekledim
		return secim; // kullanıcının seçimi geri döndürülür
		
	}

	public String getAd() { // kullanıcıdan ürün adı alan metot
		
		System.out.print("Ürün adı: "); // kullanıcıdan ürün adı istenir
		return scanner.nextLine(); // girilen string ifade alınır ve döndürülür
	}
	public double getFiyat() { // kullanıcıdan ürün fiyatı alan metot
		System.out.print("Fiyat: "); // fiyat bilgisi istenir
		double fiyat = scanner.nextDouble(); // double olarak fiyat alınır
		scanner.nextLine(); // buffer temizlenir
		return fiyat; // fiyat değeri döndürülür
	}
	public int getStok() { // kullanıcıdan stok miktarı alan metot
		System.out.print("Stok: "); // stok bilgisi istenir
		int stok = scanner.nextInt(); // int olarak stok alınır
		scanner.nextLine(); // buffer temizlenir
		return stok; // stok değeri döndürülür
	}
}
