package controller;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.Scanner;

import model.Role;
import model.UrunModel;
import model.UrunTipi;
import service.DepoService;
import service.UrunYetkiService;
import view.DepoGorevlisiView;
import view.UrunEkleView;

public class UrunController {

	private UrunEkleView urunEkleView = new UrunEkleView();
	private UrunYetkiService urunYetkiService = new UrunYetkiService();
	private DepoGorevlisiView depoGorevlisiView = new DepoGorevlisiView();
	private DepoService depoService = new DepoService();
	
	Scanner scanner = new Scanner(System.in);
	public void urunEkle() {
		
		UrunModel urun = urunEkleView.urunEkle();
		if(urun==null) {
			System.out.println("Geçersiz işlem");
			return;
		}
		boolean sonuc = urunYetkiService.urunEkle(urun);
		
		if(sonuc) {
			System.out.println("Ürün eklendi");
		}else {
			System.out.println("Bu ürün zaten var");
		}
				
	}
}
