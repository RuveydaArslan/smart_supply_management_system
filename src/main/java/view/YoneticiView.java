package view; // view paketi

import java.util.Scanner;

public class YoneticiView { // yönetici ekran sınıfı

	Scanner scanner = new Scanner(System.in);
	// yönetici menüsünü gösteren ve seçimi döndüren metot
	public int menuGoster() {
		System.out.println("***** YÖNETİCİ EKRANI *****");
		System.out.println("1- Müşteri işlemleri");
		System.out.println("2- Depo görevlisi işlemleri");
		System.out.println("3- Kurye işlemleri");
		System.out.println("4- Siparişleri görüntüle");
		System.out.println("5- Stokları görüntüle");
		System.out.println("6- Kargo firması işlemleri");
		System.out.println("7- Ödeme yöntemleri işlemleri");
		System.out.println("8- Çıkış");
		System.out.print("Seçim: ");
		int secim = scanner.nextInt();  // kullacıdan seçimi alınır
		scanner.nextLine(); // buffer temizliği
		return secim; // seçim döndürülür
	}
}
