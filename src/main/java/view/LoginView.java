package view; // view sınıfı

import java.util.Scanner; // Scanner sınıfı

public class LoginView { // kullanıcıdan giriş işlemlerini yöneten sınıf

	private Scanner scanner = new Scanner(System.in); // Scanner nesnesi

	public String getKullaniciAdi() { // kullanıcıdan kullanıcı adı alan metot
			System.out.print("Kullanıcı adı: "); // kullanıcıdan isim girmesi istenir
			return scanner.nextLine(); // alınan string ifade geri döndürülür
		}
	public String getSifre() { // kullanıcıdan şifre alan metot
		System.out.print("Şifre: "); // kullanıcıdan şifre girilmesi istenir
		return scanner.nextLine(); // girilen şifre alınır ve geri döndürülür
	} 

}
