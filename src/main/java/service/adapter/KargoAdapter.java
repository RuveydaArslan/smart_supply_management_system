package service.adapter;

import factory.KargoHesaplayici;
import service.strategy.KargoStrategy;

public class KargoAdapter implements KargoHesaplayici {

	private KargoStrategy strategy;

	public KargoAdapter(KargoStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public double kargoUcretiHesapla(double mesafe, double tutar) {
		return strategy.kargoUcretiHesapla(mesafe, tutar);
	}

}
