package k23cnt3.vutienduc.project3.fast_food_order.service;

import k23cnt3.vutienduc.project3.fast_food_order.entity.ThanhToan;
import k23cnt3.vutienduc.project3.fast_food_order.repository.ThanhToanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThanhToanService {

    private final ThanhToanRepository thanhToanRepository;

    // ===== DETAIL =====
    @Transactional(readOnly = true)
    public Map<String, Object> getThanhToanForEdit(Long id) {
        return thanhToanRepository.findByIdForEdit(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy thanh toán #" + id)
                );
    }

    // ===== FILTER (🔥 GIỮ NGUYÊN LOGIC ĐÚNG CỦA BẠN) =====
    @Transactional(readOnly = true)
    public List<ThanhToan> filterThanhToan(
            String phuongThuc,
            String trangThai,
            LocalDate fromDate
    ) {

        // Fix empty string
        if (phuongThuc != null && phuongThuc.isBlank()) {
            phuongThuc = null;
        }
        if (trangThai != null && trangThai.isBlank()) {
            trangThai = null;
        }

        // Fix & validate trạng thái
        if (trangThai != null) {
            trangThai = trangThai.trim().toUpperCase();
            if (!List.of(
                    "DA_THANH_TOAN",
                    "CHUA_THANH_TOAN",
                    "HOAN_TIEN"
            ).contains(trangThai)) {
                trangThai = null;
            }
        }

        // Fix phương thức
        if (phuongThuc != null) {
            phuongThuc = phuongThuc.trim().toUpperCase();
        }

        LocalDateTime fromDateTime =
                fromDate != null ? fromDate.atStartOfDay() : null;

        return thanhToanRepository.filterThanhToan(
                phuongThuc,
                trangThai,
                fromDateTime
        );
    }

    // ===== TỔNG DOANH THU (CHỈ ĐÃ THANH TOÁN) =====
    @Transactional(readOnly = true)
    public Double getTotalRevenue() {
        return thanhToanRepository.sumRevenueDaThanhToan();
    }

    // ===== ĐẾM COD / ONLINE (CHỈ ĐÃ THANH TOÁN) =====
    @Transactional(readOnly = true)
    public long countByMethod(String method) {
        return thanhToanRepository.countByMethodDaThanhToan(
                method.toUpperCase()
        );
    }

    // ===== DELETE (🔥 BỔ SUNG DUY NHẤT ĐỂ KHỚP CONTROLLER) =====
    @Transactional
    public void deleteById(Long id) {
        if (!thanhToanRepository.existsById(id)) {
            throw new RuntimeException("Thanh toán #" + id + " không tồn tại");
        }
        thanhToanRepository.deleteById(id);
    }
}
