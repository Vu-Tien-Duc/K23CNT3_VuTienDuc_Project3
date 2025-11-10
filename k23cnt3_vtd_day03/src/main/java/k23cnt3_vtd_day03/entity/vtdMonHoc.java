package k23cnt3_vtd_day03.entity;

public class vtdMonHoc {
    private String id;
    private String ten;
    private int soTiet;

    public vtdMonHoc() {
    }

    public vtdMonHoc(String id, String ten, int soTiet) {
        this.id = id;
        this.ten = ten;
        this.soTiet = soTiet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoTiet() {
        return soTiet;
    }

    public void setSoTiet(int soTiet) {
        this.soTiet = soTiet;
    }
}
