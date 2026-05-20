package model;

import java.util.ArrayList;
import java.util.List;

import service.observer.StokObserver;
import service.observer.StokSubject;

public class UrunModel implements StokSubject {

	private int id;
	private String ad;
	private double fiyat;
	private int stok;
	private UrunTipi tip;
	private int kritikStok = 20;
	private String urunDetay;

	private List<StokObserver> observers = new ArrayList<>();

	@Override
	public void addObserver(StokObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserve(StokObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (StokObserver o : observers) {
			o.guncelle(this);
		}
	}

	public void stokAzalt(int miktar) {
		for(StokObserver o:observers) {
			o.guncelle(this);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public double getFiyat() {
		return fiyat;
	}

	public void setFiyat(double fiyat) {
		this.fiyat = fiyat;
	}

	public int getStok() {
		return stok;
	}

	public void setStok(int stok) {
		this.stok = stok;
	}

	public UrunTipi getTip() {
		return tip;
	}

	public void setTip(UrunTipi tip) {
		this.tip = tip;
	}

	public int getKritikStok() {
		return kritikStok;
	}

	public void setKritikStok(int kritikStok) {
		this.kritikStok = kritikStok;
	}

	public String getUrunDetay() {
		return urunDetay;
	}

	public void setUrunDetay(String urunDetay) {
		this.urunDetay = urunDetay;
	}

}
