package view; // view paketi

import java.util.Scanner; // kullanıcıdan veri almak için Scanner sınıfı

public class KayitOlView { // kullanıcı kayıt işlemlerini yöneten sınıf

	private Scanner scanner = new Scanner(System.in); // Scanner nesnesi
	
	public String getKullaniciAdi() { // kullanıcıdan isim ve soyisim alınan metot

		System.out.print("Kullanıcı adı: "); // kullanıcıdan isim bilgisi alınır
		return scanner.nextLine(); // girilen ifade döndürülür
	}
	
	public String getSifre() { // kullanıcıdan şifre alan metot
		System.out.print("Şifre: "); // şifre girişi
		return scanner.nextLine(); // şifre geri döndürülür
	}
	public int rolSecim() { // kullanıcıdan rol seçimi yapımı yapan metot
	
		// rol seçenekleri kullanıcıya gösterilir
		System.out.println("*** ROLLER ***");
		System.out.println("1- Müşteri");
		System.out.println("2- Depo Görevlisi (sadece yönetici)"); // sadece yöneticinin yapabileceği belirtildi
		System.out.println("3- Kurye (sadece yönetici)");
		System.out.println("4- Yönetici (yasak)"); // yönetici eklenmesinin olamayacağı belirtildi
		System.out.print("Rol seçiniz: "); // kullanıcıdan seçim yapması istenir
		
		return scanner.nextInt(); // kullanıcının seçtiği seçenek geri döndürülür
	}
}
