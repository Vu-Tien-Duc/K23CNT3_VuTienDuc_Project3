package k23cnt3.vutienduc.project3.fast_food_order.config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class PaymentQRUtil {

    /**
     * Sinh QR chuyển khoản thủ công (Base64 PNG)
     */
    public static String generateQR(String account, String name, double amount, String memo) throws Exception {
        // Nội dung QR, mỗi dòng 1 thông tin
        String payload = "STK:" + account + "\n" +
                "Tên:" + name + "\n" +
                "So Tien:" + String.format("%.0f", amount) + "\n" +
                "Noi dung:" + memo;

        // Encode QR
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitMatrix matrix = new MultiFormatWriter()
                .encode(payload, BarcodeFormat.QR_CODE, 300, 300);
        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);

        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }
}
