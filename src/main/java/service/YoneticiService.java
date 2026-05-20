package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.channels.Pipe.SourceChannel;
import java.security.PublicKey;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.print.attribute.PrintServiceAttribute;

import factory.DinamikKargoHesaplayici;
import factory.KargoFactory;
import model.KargoFirmasiModel;
import model.KullaniciModel;
import model.OdemeYontemiModel;
import model.Role;
import model.SiparisModel;
import model.UrunModel;
import repository.KargoFirmasiRepository;
import repository.KullaniciRepository;
import repository.OdemeYontemiRepository;
import repository.SiparisRepository;
import repository.UrunRepository;

public class YoneticiService {

	private OdemeYontemiRepository odemeYontemiRepository = new OdemeYontemiRepository();
	private KargoFirmasiRepository kargoFirmasiRepository = new KargoFirmasiRepository();
	private SiparisRepository siparisRepository = new SiparisRepository();
	private KullaniciRepository kullaniciRepository = new KullaniciRepository();
	private UrunRepository urunRepository = new UrunRepository();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	Scanner scanner = new Scanner(System.in);

	public void musteriIslemleri() {
		System.out.println("1- Müşteri listele");
		System.out.println("2- Müşteri sil");
		System.out.print("Seçim: ");
		int secim = scanner.nextInt();

		switch (secim) {
		case 1:
			musteriListele();
			break;
		case 2:
			musteriSil();
			break;
		default:
			System.out.println("Hatalı seçim");
		}
	}

	public void musteriListele() {
		List<KullaniciModel> liste = kullaniciRepository.tumKullanicilariOku();

		for (KullaniciModel k : liste) {
			if (k.getRole() == Role.MUSTERI) {
				System.out.println("Id: " + k.getId() + " - İsim: " + k.getadSoyad());
			}
		}
	}

	public void musteriSil() {
		List<KullaniciModel> liste = kullaniciRepository.tumKullanicilariOku();

		System.out.println("***** MÜŞTERİLER *****");
		for (KullaniciModel k : liste) {
			if (k.getRole() == Role.MUSTERI) {
				System.out.println("Id: " + k.getId() + " - İsim: " + k.getadSoyad());
			}
		}
		System.out.print("Silmek istediğiniz müşteri id: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		for (KullaniciModel k : liste) {
			if (k.getId() == id && k.getRole() == Role.MUSTERI) {
				liste.remove(k);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Müşteri bulunamadı");
			return;
		}
		kullaniciRepository.tumunuYenidenYaz(liste);
		System.out.println("Müşteri silindi");
	}

	public void depoGorevlisiIslemleri() {

		System.out.println("1- Depo görevlisi listele");
		System.out.println("2- Depo görevlisi ekle");
		System.out.println("3- Depo görevlisi sil");

		System.out.print("Seçim: ");
		int secim = scanner.nextInt();
	

		switch (secim) {
		case 1:
			depoGorevlisiListele();
			break;
		case 2:
			depoGorevlsiEkle();
			break;
		case 3:
			depoGorevlisiSil();
			break;
		default:
			System.out.println("Hatalı seçim");
		}
	}

	public void depoGorevlisiListele() {
		List<KullaniciModel> liste = kullaniciRepository.tumKullanicilariOku();

		for (KullaniciModel k : liste) {
			if (k.getRole()== Role.DEPO_GOREVLISI) {
				System.out.println("Id: " + k.getId() + " - İsim: " + k.getadSoyad());
			}
		}
	}

	public void depoGorevlsiEkle() {
		scanner.nextLine();
		System.out.print("Ad Soyad: ");
		String isim = scanner.nextLine();
		System.out.print("Şifre: ");
		String sifre = scanner.nextLine();

		List<KullaniciModel> kullanicilar = kullaniciRepository.tumKullanicilariOku();

		int yeniId = 1;
		for (KullaniciModel k : kullanicilar) {
			if (k.getId() >= yeniId) {
				yeniId = k.getId() + 1;
			}
		}
		KullaniciModel depoGorevlisi = new KullaniciModel();
		depoGorevlisi.setId(yeniId);
		depoGorevlisi.setadSoyad(isim);
		depoGorevlisi.setSifre(sifre);
		depoGorevlisi.setRole(Role.DEPO_GOREVLISI);

		kullaniciRepository.kullaniciKaydet(depoGorevlisi);
		System.out.println("Depo görevlisi eklendi: " + isim);
	}

	public void depoGorevlisiSil() {
		List<KullaniciModel> liste = kullaniciRepository.tumKullanicilariOku();

		System.out.println("***** DEPO GÖREVLİLERİ *****");
		for (KullaniciModel k : liste) {
			if (k.getRole() == Role.DEPO_GOREVLISI) {
				System.out.println("Id: " + k.getId() + " - İsim: " + k.getadSoyad());
			}
		}
		System.out.print("Silmek istediğiniz depo görevlisi id: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		boolean bulundu = false;

		for (KullaniciModel k : liste) {
			if (k.getId() == id && k.getRole() == Role.DEPO_GOREVLISI) {
				liste.remove(k);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Depo görevlisi bulunamadı");
			return;
		}
		kullaniciRepository.tumunuYenidenYaz(liste);
		System.out.println("Depo görevlisi silindi");
	}

	public void kuryeIslemleri() {

		System.out.println("1- Kurye listele");
		System.out.println("2- Kurye ekle");
		System.out.println("3- Kurye sil");

		System.out.print("Seçim: ");
		int secim = scanner.nextInt();

		switch (secim) {
		case 1:
			kuryeListele();
			break;
		case 2:
			kuryeEkle();
			break;
		case 3:
			kuryeSil();
			break;
		default:
			System.out.println("Hatalı seçim");
		}
	}

	public void kuryeListele() {
		List<KullaniciModel> liste = kullaniciRepository.tumKullanicilariOku();

		for (KullaniciModel k : liste) {
			if (k.getRole() == Role.KURYE) {
				System.out.println("Id: " + k.getId() + " - İsim: " + k.getadSoyad());
			}
		}
	}

	public void kuryeEkle() {
		scanner.nextLine();
		System.out.print("Ad Soyad: ");
		String isim = scanner.nextLine();
		System.out.println("Şifre: ");
		String sifre = scanner.nextLine();

		List<KullaniciModel> kullanicilar = kullaniciRepository.tumKullanicilariOku();

		int yeniId = 1;
		for (KullaniciModel k : kullanicilar) {
			if (k.getId() >= yeniId) {
				yeniId = k.getId() + 1;
			}
		}
		KullaniciModel kurye = new KullaniciModel();
		kurye.setId(yeniId);
		kurye.setadSoyad(isim);
		kurye.setSifre(sifre);
		kurye.setRole(Role.KURYE);

		kullaniciRepository.kullaniciKaydet(kurye);
		System.out.println("Kurye eklendi: " + isim);
	}

	public void kuryeSil() {
		List<KullaniciModel> liste = kullaniciRepository.tumKullanicilariOku();

		System.out.println("***** KURYE *****");
		for (KullaniciModel k : liste) {
			if (k.getRole() == Role.KURYE) {
				System.out.println("Id: " + k.getId() + " - İsim: " + k.getadSoyad());
			}
		}
		System.out.print("Silmek istediğiniz kurye id: ");
		int id = scanner.nextInt();

		boolean bulundu = false;

		for (KullaniciModel k : liste) {
			if (k.getId() == id && k.getRole() == Role.KURYE) {
				liste.remove(k);
				bulundu = true;
				break;
			}
		}
		if (!bulundu) {
			System.out.println("Kurye bulunamadı");
			return;
		}
		kullaniciRepository.tumunuYenidenYaz(liste);
		System.out.println("Kurye silindi");
	}

	public void siparisleriGoruntule() {

		List<SiparisModel> siparisler = siparisRepository.tumSiparisleriGetir();

		if (siparisler.isEmpty()) {
			System.out.println("Sipariş bulunamadı");
			return;
		}
		System.out.println("***** SİPARİŞLER *****");

		for (SiparisModel s : siparisler) {
			System.out.println("------------------------------------");
			System.out.println("Sipariş No: " + s.getId());
			System.out.println("Müşteri adı: " + s.getKullaniciAdi());
			System.out.println("Toplam fiyat: " + String.format("%.2f", s.getToplamTutar()));
			System.out.println("Durum: " + s.getDurum());
			System.out.println("Tarih: " + s.getSiparisTarihi().format(formatter));
		}
		System.out.println("------------------------------------");
	}

	public void stoklarıGoruntule() {

		List<UrunModel> urunler = urunRepository.urunleriOku();

		if (urunler.isEmpty()) {
			System.out.println("Ürün bulunamadı");
			return;
		}
		System.out.println("***** STOK DURUMU *****");

		for (UrunModel urun : urunler) {
			System.out.println("------------------------------------");
			System.out.println(
					"Ürün no: " + urun.getId() + " | Ürün adı: " + urun.getAd() + " | Stok: " + urun.getStok());
		}
		System.out.println("------------------------------------");
	}

	public void kargoFirmasiIslemleri() {

		System.out.println("1- Kargo firması listele");
		System.out.println("2- Kargo firması ekle");
		System.out.println("3- Kargo firması sil");

		System.out.print("Seçim: ");
		int secim = scanner.nextInt();

		switch (secim) {
		case 1:
			kargoFirmasiListele();
			break;
		case 2:
			kargoFirmasiEkle();
			break;
		case 3:
			kargoFirmasiSil();
			break;
		default:
			System.out.println("Hatalı seçim");
		}
	}

	public void kargoFirmasiListele() {
		List<KargoFirmasiModel> liste = kargoFirmasiRepository.kargoFirmasiListele();

		System.out.println("***** KARGO FİRMALARI *****");

		for (KargoFirmasiModel k : liste) {
			System.out.println("--------------------------------------");
			System.out.println("Id: " + k.getId() + " | Firma: " + k.getAd() + " | Sabit Ücret: " + k.getSabitUcret()
					+ " | Km Ücreti: " + k.getKmBasiUcret());
		}
	}

	public void kargoFirmasiEkle() {
		scanner.nextLine();
		System.out.print("Firma adı: ");
		String ad = scanner.nextLine().toLowerCase().trim();

		System.out.print("Sabit ücret: ");
		double sabit = Double.parseDouble(scanner.nextLine());

		System.out.print("Km başı ücret: ");
		double km = Double.parseDouble(scanner.nextLine());

		int id = kargoFirmasiRepository.sonIdBul();

		KargoFirmasiModel yeni = new KargoFirmasiModel();
		yeni.setId(id);
		yeni.setAd(ad.toLowerCase());
		yeni.setDisplayAd(ad);
		yeni.setSabitUcret(sabit);
		yeni.setKmBasiUcret(km);

		kargoFirmasiRepository.kargoEkle(yeni);
		KargoFactory.kargoEkle(yeni.getAd(), new DinamikKargoHesaplayici(yeni));

		System.out.println("Kargo firması eklendi: " + ad);
	}

	public void kargoFirmasiSil() {
		kargoFirmasiListele();

		System.out.print("Silinecek kargo id: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		List<KargoFirmasiModel> liste = kargoFirmasiRepository.kargoFirmasiListele();

		boolean silindi = liste.removeIf(k -> k.getId() == id);

		if (!silindi) {
			System.out.println("Kargo bulunamadı");
			return;
		}

		kargoFirmasiRepository.tumunuYenidenYaz(liste);
		System.out.println("Kargo firması silindi");
	}

	public void odemeYontemleriIslemleri() {

		System.out.println("1- Ödeme yöntemlerini listele");
		System.out.println("2- Ödeme yöntemi ekle");
		System.out.println("3- Ödeme yöntemi sil");
		System.out.print("Seçim: ");
		int secim = scanner.nextInt();
		scanner.nextLine();

		switch (secim) {
		case 1:
			odemeYontemleriListele();
			break;
		case 2:
			odemeYontemiEkle();
			break;
		case 3:
			odemeYontemiSil();
			break;
		default:
			System.out.println("Geçerli olmayan seçim");
			;
		}
	}

	public void odemeYontemleriListele() {
		List<OdemeYontemiModel> liste = odemeYontemiRepository.odemeYontemiListele();

		System.out.println("***** ÖDEM YÖNTEMLERİ *****");

		for (OdemeYontemiModel o : liste) {
			System.out.println("Id: " + o.getId() + " - Ödeme Yöntemi:" + o.getAd());
		}
	}

	public void odemeYontemiEkle() {
		System.out.print("Ödeme yöntemi adı: ");
		String ad = scanner.nextLine();

		int id = odemeYontemiRepository.sonIdBul();

		OdemeYontemiModel yeni = new OdemeYontemiModel();
		yeni.setId(id);
		yeni.setAd(ad);

		odemeYontemiRepository.odemeYontemiEkle(yeni);
		System.out.println("Ödeme yöntemi eklendi: " + ad);
	}

	public void odemeYontemiSil() {
		odemeYontemleriListele();

		System.out.print("Silinecek ödeme yöntemi id: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		List<OdemeYontemiModel> liste = odemeYontemiRepository.odemeYontemiListele();

		boolean silindi = liste.removeIf(o -> o.getId() == id);

		if (!silindi) {
			System.out.println("Bulunamadı");
			return;
		}
		odemeYontemiRepository.tumunuYenidenYaz(liste);
		System.out.println("Ödeme yöntemi silindi");
	}
}
