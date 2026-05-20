package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.JoinRowSet;

import model.UrunModel;
import model.UrunTipi;
import service.KritikStokService;
import service.UrunYetkiService;
import service.observer.DepoObserver;
import service.observer.SatinAlmaObserver;
import service.singletion.LogManager;

public class UrunRepository {

	private String dosyaYolu = "urunler.txt";
	private KritikStokService kritikStokService = new KritikStokService();

	public List<UrunModel> urunleriOku() {
		List<UrunModel> urunler = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("urunler.txt"))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				String[] parca = satir.split(",");
				UrunModel urun = new UrunModel();
				urun.setId(Integer.parseInt(parca[0]));
				urun.setAd(parca[1]);
				urun.setFiyat(Double.parseDouble(parca[2]));
				urun.setStok(Integer.parseInt(parca[3]));
				urun.setTip(UrunTipi.valueOf(parca[4]));

				urun.addObserver(new DepoObserver());
				urun.addObserver(new SatinAlmaObserver());

				urunler.add(urun);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return urunler;
	}

	public void urunKaydet(UrunModel urun) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu, true))) {
			writer.write(urun.getId() + "," + urun.getAd() + "," + urun.getFiyat() + "," + urun.getStok() + ","
					+ urun.getTip());

			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stokDusur(int urunId, int miktar) {
		List<UrunModel> urunler = urunleriOku();

		for (UrunModel u : urunler) {
			if (u.getId() == urunId) {
				u.setStok(u.getStok() - miktar);

				LogManager.getInstance().log("Stok", u.getAd() + " stok düşürüldü. Yeni stok: " + u.getStok());
				
				if (u.getStok() <= u.getKritikStok()) {
					
					LogManager.getInstance().log("Kritik stok",u.getAd()+" kritik seviyeye düştü");
					
					u.notifyObservers();
				}
				break;
			}
		}
		urunleriYenidenYaz(urunler);
	}

	public void urunleriYenidenYaz(List<UrunModel> urunler) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {

			for (UrunModel urun : urunler) {

				writer.write(urun.getId() + "," + urun.getAd() + "," + urun.getFiyat() + "," + urun.getStok() + ","
						+ urun.getTip());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public UrunModel idIleGetir(int id) {
		List<UrunModel> urunlerList = urunleriOku();

		for (UrunModel urun : urunlerList) {
			if (urun.getId() == id) {
				return urun;
			}
		}
		return null;
	}
}
