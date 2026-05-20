package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import model.KargoFirmasiModel;

public class KargoFirmasiRepository {

	private String dosyaYolu = "data/kargo.txt";

	public List<KargoFirmasiModel> kargoFirmasiListele() {
		List<KargoFirmasiModel> liste = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				String[] p = satir.split(",");

				KargoFirmasiModel k = new KargoFirmasiModel();
				k.setId(Integer.parseInt(p[0]));
				k.setAd(p[1]);
				k.setSabitUcret(Double.parseDouble(p[2]));
				k.setKmBasiUcret(Double.parseDouble(p[3]));

				liste.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste;
	}

	public void kargoEkle(KargoFirmasiModel kargo) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu,true))) {

			String satir = kargo.getId() + "," + kargo.getAd() + "," + kargo.getSabitUcret() + ","
					+ kargo.getKmBasiUcret();

			writer.write(satir);
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int sonIdBul() {
		int max = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				String[] p = satir.split(",");
				int id = Integer.parseInt(p[0]);

				if (id > max) {
					max = id;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return max + 1;
	}

	public void kargoSil(int id) {
		List<KargoFirmasiModel> liste = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				String[] p = satir.split(",");

				KargoFirmasiModel k = new KargoFirmasiModel();
				k.setId(Integer.parseInt(p[0]));
				k.setAd(p[1]);
				k.setSabitUcret(Double.parseDouble(p[2]));
				k.setKmBasiUcret(Double.parseDouble(p[3]));

				if (k.getId() != id) {
					liste.add(k);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {

			for (KargoFirmasiModel k : liste) {
				writer.write(k.getId() + "," + k.getAd() + "," + k.getSabitUcret() + "," + k.getKmBasiUcret());
				writer.newLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tumunuYenidenYaz(List<KargoFirmasiModel> liste) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {
			for (KargoFirmasiModel k : liste) {
				String satir = k.getId() + "," + k.getAd() + "," + k.getSabitUcret() + "," + k.getKmBasiUcret();

				writer.write(satir);
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
