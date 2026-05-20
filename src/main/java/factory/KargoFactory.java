package factory;

import java.util.HashMap;
import java.util.Map;

public class KargoFactory {

	private static Map<String, KargoHesaplayici> kargolar = new HashMap<>();

	static {
		kargolar.put("aras", new ArasKargo());
		kargolar.put("yurtiçi", new YurtIciKargo());
		kargolar.put("global", new GlobalExpres());

	}

	public static KargoHesaplayici getKargo(String firma) {
		if(firma == null) {
			throw new RuntimeException("Kargo firması boş olamaz");
		}
		
		KargoHesaplayici kargo = kargolar.get(firma.trim().toLowerCase());

		if (kargo == null) {
			throw new RuntimeException("Geçersiz kargo firması: " + firma);
		}
		return kargo;
	}

	public static void kargoEkle(String isim, KargoHesaplayici kargo) {
		kargolar.put(isim.toLowerCase(), kargo);
	}

	public static void kargoSil(String isim) {
		kargolar.remove(isim.toLowerCase());
	}

	public static void kargolariListele() {
		System.out.println("***** KARGO FİRMALARI *****");

		for (String key : kargolar.keySet()) {
			System.out.println("- " + key);
		}
	}
}
