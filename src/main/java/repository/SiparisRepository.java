package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.SiparisDurumu;
import model.SiparisModel;
import model.UrunModel;

public class SiparisRepository {

	private String dosyaYolu = "siparisler.txt";

	public void kaydet(SiparisModel siparis) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu, true))) {

			int yeniId = sonIdBul();
			siparis.setId(yeniId);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			StringBuilder builder = new StringBuilder();

			builder.append(siparis.getId()).append(" | ");
			builder.append(siparis.getKullaniciAdi()).append(" | ");
			builder.append(siparis.getToplamTutar()).append(" | ");
			builder.append(siparis.getDurum()).append(" | ");
			builder.append(siparis.getSiparisTarihi().format(formatter)).append(" | ");
			builder.append(siparis.getUrunDetay());

			writer.write(builder.toString());
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> oku() {
		List<String> liste = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				liste.add(satir);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste;
	}

	private int sonIdBul() {
		int max = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;
			while ((satir = reader.readLine()) != null) {
				String[] parca = satir.split("\\|");

				int id = Integer.parseInt(parca[0].trim());

				if (id > max) {
					max = id;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return max + 1;
	}

	public List<SiparisModel> tumSiparisleriGetir() {
		List<SiparisModel> liste = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;

			while ((satir = reader.readLine()) != null) {
				String[] parca = satir.split("\\|");
				if (parca.length < 6)
					continue;

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
				SiparisModel siparis = new SiparisModel();
				siparis.setId(Integer.parseInt(parca[0].trim()));
				siparis.setKullaniciAdi(parca[1].trim());
				siparis.setToplamTutar(Double.parseDouble(parca[2].trim()));
				siparis.setDurum(SiparisDurumu.valueOf(parca[3].trim()));
				siparis.setSiparisTarihi(LocalDateTime.parse(parca[4].trim(), formatter));

				siparis.setUrunDetay(parca[5].trim());
				liste.add(siparis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liste;
	}

	public void tumunuYenidenYaz(List<SiparisModel> siparisler) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {
			for (SiparisModel siparis : siparisler) {
				StringBuilder builder = new StringBuilder();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

				builder.append(siparis.getId()).append(" | ");
				builder.append(siparis.getKullaniciAdi()).append(" | ");
				builder.append(siparis.getToplamTutar()).append(" | ");
				builder.append(siparis.getDurum()).append(" | ");
				builder.append(siparis.getSiparisTarihi().format(formatter)).append(" | ");
				builder.append(siparis.getUrunDetay());

				
				writer.write(builder.toString());
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
