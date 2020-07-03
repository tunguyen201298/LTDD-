package com.example.cuahangsach;

import java.io.Serializable;

public class SachOnThi implements Serializable {
    private int id;
    private String tenSach;
    private String gioiThieuSach;
    private int giaSach;
    private String nhaXuatBan;
    private int soLuong;
    private int hinhAnhSach;
    private String diaChiMua;
    private String emailDangNhap;

    public SachOnThi() {
    }

    public SachOnThi(int id, String tenSach, String gioiThieuSach, int giaSach, String nhaXuatBan, int soLuong, int hinhAnhSach, String diaChiMua, String emailDangNhap) {
        this.id = id;
        this.tenSach = tenSach;
        this.gioiThieuSach = gioiThieuSach;
        this.giaSach = giaSach;
        this.nhaXuatBan = nhaXuatBan;
        this.soLuong = soLuong;
        this.hinhAnhSach = hinhAnhSach;
        this.diaChiMua = diaChiMua;
        this.emailDangNhap = emailDangNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getGioiThieuSach() {
        return gioiThieuSach;
    }

    public void setGioiThieuSach(String gioiThieuSach) {
        this.gioiThieuSach = gioiThieuSach;
    }

    public int getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(int giaSach) {
        this.giaSach = giaSach;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getHinhAnhSach() {
        return hinhAnhSach;
    }

    public void setHinhAnhSach(int hinhAnhSach) {
        this.hinhAnhSach = hinhAnhSach;
    }

    public String getDiaChiMua() {
        return diaChiMua;
    }

    public void setDiaChiMua(String diaChiMua) {
        this.diaChiMua = diaChiMua;
    }

    public String getEmailDangNhap() {
        return emailDangNhap;
    }

    public void setEmailDangNhap(String emailDangNhap) {
        this.emailDangNhap = emailDangNhap;
    }
}
