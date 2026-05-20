package view; // view paketi

import java.util.Scanner; //kullanıcıdan veri almak için Scanner sınıfı

public class KuryeView {// kurye ekranı için sınıfı

	private Scanner scanner = new Scanner(System.in); // kullanıcıdan giriş almak için Scanner nesnesi
	public int menuGoster() { // kurye menüsünü gösteren ve seçimi döndüren metot
		System.out.println("***** KURYE MENÜ *****");
		System.out.println("1- Kargodaki siparişleri listele");
		System.out.println("2- Siparişi 'TESLİM EDİLDİ' yap");
		System.out.println("3- Teslim edilemeyen siparişi iade sürecine al");
		System.out.println("4- Çıkış");
		System.out.print("Seçim: ");
		int secim = scanner.nextInt(); // kullanıcıdan giriş alınır
		scanner.nextLine(); // buffer temizleme
		return secim; // kullanıcının alınan değer döndürülür
	}
}
