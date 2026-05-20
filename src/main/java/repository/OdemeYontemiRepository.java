package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import model.OdemeYontemiModel;

public class OdemeYontemiRepository {

	private String dosyaYolu = "odeme.txt";

	public List<OdemeYontemiModel> odemeYontemiListele() {
		List<OdemeYontemiModel> liste = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				String[] p = satir.split(",");

				OdemeYontemiModel o = new OdemeYontemiModel();
				o.setId(Integer.parseInt(p[0]));
				o.setAd(p[1]);
				liste.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste;
	}

	public void odemeYontemiEkle(OdemeYontemiModel yeni) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu, true))) {
			writer.write(yeni.getId() + "," + yeni.getAd());
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tumunuYenidenYaz(List<OdemeYontemiModel> liste) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {
			for (OdemeYontemiModel o : liste) {
				writer.write(o.getId() + "," + o.getAd());
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int sonIdBul() {
		List<OdemeYontemiModel> liste = odemeYontemiListele();
		if (liste.isEmpty())
			return 1;
		return liste.get(liste.size() - 1).getId() + 1;
	}
}
