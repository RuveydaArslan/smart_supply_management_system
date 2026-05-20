package service.facade;

import factory.DinamikKargoHesaplayici;
import factory.KargoHesaplayici;
import model.KargoFirmasiModel;
import model.OdemeYontemiModel;
import model.SiparisModel;
import service.SiparisService;
import service.adapter.KargoAdapter;
import service.strategy.DinamikKargoStrategy;
import service.strategy.KargoStrategy;

public class AlisverisFacade {

	private SiparisService siparisService;

	public AlisverisFacade() {

		this.siparisService = new SiparisService();

	}

	public SiparisModel satinAl(String kullaniciAdi, String urunDetay, double toplam, double mesafe,
			KargoFirmasiModel firma, OdemeYontemiModel odeme) {

		KargoStrategy strategy = new DinamikKargoStrategy(firma);
		KargoHesaplayici kargo = new KargoAdapter(strategy);
		double kargoUcreti = kargo.kargoUcretiHesapla(mesafe, toplam);

		SiparisModel siparis = siparisService.siparisOlustur(kullaniciAdi, urunDetay, toplam, kargoUcreti);

		return siparis;
	}
}
