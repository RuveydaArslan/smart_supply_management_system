package model;

import java.time.LocalDateTime;
import java.util.List;

public class SiparisModel {

	private int id;
	private String kullaniciAdi;
	private List<UrunModel> urunler;
	private double urunToplamTutar;
	private double kargoUcreti;
	private double toplamTutar;
	private LocalDateTime siparisTarihi;
	
	private SiparisDurumu durum;
	private KargoFirmasiModel kargoFirmasi;
	private double mesafe;
	private String urunDetay;
	
	public SiparisModel() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public double getUrunToplam() {
		return urunToplamTutar;
	}
	public void setUrunToplam(double urunToplamTutar) {
		this.urunToplamTutar = urunToplamTutar;
	}
	public double getKargoUcreti() {
		return kargoUcreti;
	}
	public void setKargoUcreti(double kargoUcreti) {
		this.kargoUcreti = kargoUcreti;
	}
	public String getKullaniciAdi() {
		return kullaniciAdi;
	}
	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}
	public List<UrunModel> getUrunler(){
		return urunler;
	}
	public void setUrunler(List<UrunModel> urunler) {
		this.urunler=urunler;
	}
	public double getToplamTutar() {
		return toplamTutar;
	}
	public void setToplamTutar(double toplamTutar) {
		this.toplamTutar = toplamTutar;
	}
	public KargoFirmasiModel getKargoFirmasi() {
		return kargoFirmasi;
	}
	public void setKargoFirmasi(KargoFirmasiModel kargoFirmasi) {
		this.kargoFirmasi = kargoFirmasi;
	}
	public double getMesafe() {
		return mesafe;
	}
	public void setMesafe(double mesafe) {
		this.mesafe = mesafe;
	}
	public SiparisDurumu getDurum() {
		return durum;
	}
	public void setDurum(SiparisDurumu durum) {
		this.durum = durum;
	}
	public LocalDateTime getSiparisTarihi() {
		return siparisTarihi;
	}
	public void setSiparisTarihi(LocalDateTime siparisTarihi) {
		this.siparisTarihi = siparisTarihi;
	}
	public String getUrunDetay() {
		return urunDetay;
	}
	public void setUrunDetay(String urunDetay) {
		this.urunDetay = urunDetay;
	}
}
