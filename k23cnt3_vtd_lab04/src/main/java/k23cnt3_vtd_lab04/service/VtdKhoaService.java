package k23cnt3_vtd_lab04.service;


import k23cnt3_vtd_lab04.entity.VtdKhoa;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class VtdKhoaService {
    private List<VtdKhoa> list = new ArrayList<>(Arrays.asList(
            new VtdKhoa("KH01", "Công nghệ thông tin"),
            new VtdKhoa("KH02", "Kinh tế"),
            new VtdKhoa("KH03", "Ngôn ngữ Anh"),
            new VtdKhoa("KH04", "Cơ khí"),
            new VtdKhoa("KH05", "Xây dựng")
    ));

    public List<VtdKhoa> getAll() {
        return list;
    }

    public VtdKhoa getById(String makh) {
        return list.stream().filter(k -> k.getMakh().equalsIgnoreCase(makh)).findFirst().orElse(null);
    }

    public void add(VtdKhoa khoa) {
        list.add(khoa);
    }

    public boolean update(String makh, VtdKhoa khoa) {
        VtdKhoa existing = getById(makh);
        if (existing != null) {
            existing.setTenkh(khoa.getTenkh());
            return true;
        }
        return false;
    }

    public boolean delete(String makh) {
        return list.removeIf(k -> k.getMakh().equalsIgnoreCase(makh));
    }
}