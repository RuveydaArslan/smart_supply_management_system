package service;

import java.util.List;
import java.util.Scanner;

import model.UrunModel;
import repository.UrunRepository;

public class UrunYetkiService {

	private UrunRepository urunRepository = new UrunRepository();
	Scanner scanner = new Scanner(System.in);

	public boolean urunEkle(UrunModel yeniUrun) {
		
		List<UrunModel> urunler = urunRepository.urunleriOku();

		for (UrunModel urun : urunler) {
			if (urun.getAd().equalsIgnoreCase(yeniUrun.getAd())) {
				
				return false;
			}
		}
		yeniUrun.setId(siradakiId());
		urunRepository.urunKaydet(yeniUrun);
		return true;
		
	}

	public int siradakiId() {
		List<UrunModel> uruns = urunRepository.urunleriOku();

		int max = 0;

		for (UrunModel urun : uruns) {
			int id = (urun.getId());

			if (id > max) {
				max = id;
			}
		}
		return max + 1;
	}
	public void UrunAraMenu() {
		List<UrunModel> urunler = urunRepository.urunleriOku();
		
		System.out.println("1- Tüm ürünleri listele");
		System.out.println("2- Ürün ara");
		System.out.print("Seçim: ");
		
		int secim = scanner.nextInt();
		scanner.nextLine();
		
		switch (secim) {
		case 1:
			urunListele(urunler);;;
			break;
		case 2:
			urunAra(urunler);
			break;
		default:
			System.out.println("Geçersiz seçim");;
		}
	}
	public void urunAra(List<UrunModel> urunler) {
		System.out.print("Ürün adı:");
		String aranan = scanner.nextLine();
		boolean bulundu = true;
		
		for(UrunModel urun : urunler) {
			if(urun.getAd().equalsIgnoreCase(aranan)) {
				System.out.println("Bulundu: ");
				System.out.println("ID: " +urun.getId() + " - " +"Adı: "+ urun.getAd() + " - " + "Fiyatı: "+urun.getFiyat() + " - " +"Stok: "+ urun.getStok() + " - "
					+"Tip: " + urun.getTip());
				bulundu=true;
				break;
			}
		}
		if(!bulundu) {
			System.out.println("Ürün bulunamadı");
		}
	}
	public void urunListele(List<UrunModel> urunler) {
		for(UrunModel urun: urunler) {
			System.out.println("ID: " +urun.getId() + " - " +"Adı: "+ urun.getAd() + " - " + "Fiyatı: "+urun.getFiyat() + " - " +"Stok: "+ urun.getStok() + " - "
					+"Tip: " + urun.getTip());
		}
	}
	
	
	

}
