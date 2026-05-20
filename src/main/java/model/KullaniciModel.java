package model;

import model.Role;

public class KullaniciModel {
	
	int id;
	String adSoyad;
	String sifre;
	Role role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getadSoyad() {
		return adSoyad;
	}
	public void setadSoyad(String adSoyad) {
		this.adSoyad = adSoyad;
	}
	public String getSifre() {
		return sifre;
	}
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
