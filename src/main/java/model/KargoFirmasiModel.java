package model;

public class KargoFirmasiModel {

	private int id;
	private String ad;
	private double sabitUcret;
	private double kmBasiUcret;
	private String displayAd;
	
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
	public double getSabitUcret() {
		return sabitUcret;
	}
	public void setSabitUcret(double sabitUcret) {
		this.sabitUcret= sabitUcret;
	}
	public double getKmBasiUcret() {
		return kmBasiUcret;
	}
	public void setKmBasiUcret(double kmBasiUcret) {
		this.kmBasiUcret = kmBasiUcret;
	}
	public String getDisplayAd() {
		return displayAd;
	}
	public void setDisplayAd(String displayAd) {
		this.displayAd = displayAd;
	}
	
}
