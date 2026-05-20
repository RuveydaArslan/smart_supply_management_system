package view; // view paketi

import java.util.Scanner; // kullanıcıdan veri almak için Scanner sınıfı

public class AnaEkranView { // Ana ekranın views sınıfı

	public int anaEkran() { // kullanıcıya menü gösteren ve seçimini döndürenen metot
		Scanner scanner = new Scanner(System.in); // kullanıcıdan veri almak için Scanner nesnesi
		//kullanıcıya sunulan seçenekler
		System.out.println("*** AKILLI TEDARİK SİSTEMİ ***");
		System.out.println("1- Giriş yap");
		System.out.println("2- Kayıt ol");
		System.out.println("3- Çıkış");
		System.out.print("Seçim: "); // kullanıcıdan seçim yapılması istenir
		
		return scanner.nextInt(); // kullanıcının girdiği değer alınır ve geri döndürülür
		
		
		
	}

}
