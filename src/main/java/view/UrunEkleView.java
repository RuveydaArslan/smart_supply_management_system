package view; // view paketi

import java.util.Scanner;

import model.UrunModel; // ürün model sınıfı
import model.UrunTipi; // ürün tip enum 

public class UrunEkleView { // ürün ekleme işlemlerini yöneten arayüz sınıfı

	Scanner scanner = new Scanner(System.in);

	public UrunModel urunEkle() { // kullanıcıdan tüm ürün bilgilerini alıp urunModel nesnesi oluşturan metot
		System.out.println("1- Basit Ürün");
		System.out.println("2- Karmaşık Ürün");
		System.out.print("Seçim: ");
		
		int tipSecimi = scanner.nextInt(); // kullanıcıdan seçim değeri alınır
		scanner.nextLine(); // buffer temizliği
		
		System.out.print("Ürün adı: "); // ürün adı alınır
		String isim = scanner.nextLine(); 
		
		System.out.print("Fiyat: "); // ürün fiyatı alınır
		double fiyat = scanner.nextDouble();
		
		System.out.println("Stok: "); // stok değeri alınır 
		int stok = scanner.nextInt();
		scanner.nextLine(); // buffer temizliği
		
		UrunModel urun = new UrunModel(); // yeni bir ürün nesnesi oluşturulur
		
		// kullanıcıdan alınan bilgiler nesneye atanır
		urun.setAd(isim);
		urun.setFiyat(fiyat);
		urun.setStok(stok);
		
		// ürün tipi seçimine göre enum değeri atanır
		if(tipSecimi==1) {
			urun.setTip(UrunTipi.BASIT);
		}else if(tipSecimi==2){
			urun.setTip(UrunTipi.KARMASIK);
		}else {
			System.out.println("Geçersiz işlem"); // olmayan seçenek seçerse boş değer döndürülür ve mesaj gösterilir
			return null;
		}
		return urun; // oluşturulan ürün nesnesi geri döndürülür
	}

	public UrunModel urunBilgileriniAl() { // ürünün bazı bilgilerini alan metot
		UrunModel urun = new UrunModel(); // yeni ürün nesnesi oluşturulur

		System.out.println("Ürün adı: "); // ürün adı alınır
		urun.setAd(scanner.nextLine());

		System.out.println("Fiyat: "); // ürün fiyatı alınır
		urun.setFiyat(scanner.nextDouble());

		return urun; // oluşturulan ürün nesnesi döndürülür
	}
}
