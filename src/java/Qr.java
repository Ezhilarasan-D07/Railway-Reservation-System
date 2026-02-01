import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

public class Qr {

    public static byte[] generateQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int grayValue = (bitMatrix.get(x, y) ? 0 : 255);
                    int rgb = (grayValue << 16) | (grayValue << 8) | grayValue;
                    bufferedImage.setRGB(x, y, rgb);
                }
            }

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", pngOutputStream);
            return pngOutputStream.toByteArray();

        } catch (WriterException | java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
