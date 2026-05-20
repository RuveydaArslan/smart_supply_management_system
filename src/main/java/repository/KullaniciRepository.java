package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import model.KullaniciModel;
import model.Role;

public class KullaniciRepository {

	private String dosyaYolu = "kullanici.txt";

	public void kullaniciKaydet(KullaniciModel kullaniciModel) {
		int yeniId = siradakiId();
		kullaniciModel.setId((yeniId));

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu, true))) {
			writer.write(kullaniciModel.getId() + "," + kullaniciModel.getadSoyad() + "," + kullaniciModel.getSifre()
					+ "," + kullaniciModel.getRole());

			writer.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<KullaniciModel> tumKullanicilariOku() {
		List<KullaniciModel> kullanicis = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
			String satir;
			while ((satir = reader.readLine()) != null) {
				String[] parcalar = satir.split(",");
				KullaniciModel kullaniciModel = new KullaniciModel();
				kullaniciModel.setId(Integer.parseInt(parcalar[0]));
				kullaniciModel.setadSoyad(parcalar[1]);
				kullaniciModel.setSifre(parcalar[2]);
				kullaniciModel.setRole(Role.valueOf(parcalar[3]));

				kullanicis.add(kullaniciModel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return kullanicis;
	}

	public KullaniciModel girisIcinKullaniciBul(String adSoyad) {
		List<KullaniciModel> kullanicis = tumKullanicilariOku();

		for (KullaniciModel kullanici : kullanicis) {
			if (kullanici.getadSoyad().equals(adSoyad)) {
				return kullanici;
			}
		}
		return null;
	}

	public int siradakiId() {
		List<KullaniciModel> kullanicis = tumKullanicilariOku();

		int max = 0;

		for (KullaniciModel kullanici : kullanicis) {
			int id = (kullanici.getId());

			if (id > max) {
				max = id;
			}
		}
		return max + 1;
	}

	public void tumunuYenidenYaz(List<KullaniciModel> kullanicilar) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaYolu))) {

			for (KullaniciModel k : kullanicilar) {
				StringBuilder sBuilder = new StringBuilder();

				sBuilder.append(k.getId()).append(",");
				sBuilder.append(k.getadSoyad()).append(",");
				sBuilder.append(k.getSifre()).append(",");
				sBuilder.append(k.getRole());

				writer.write(sBuilder.toString());
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
