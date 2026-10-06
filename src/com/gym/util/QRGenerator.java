package com.gym.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QRGenerator {

    private QRGenerator() {
    }

    public static BufferedImage generateQRCode(
            String text,
            int width,
            int height
    ) throws Exception {

        BitMatrix matrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height
        );

        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                image.setRGB(
                        x,
                        y,
                        matrix.get(x, y)
                                ? Color.BLACK.getRGB()
                                : Color.WHITE.getRGB()
                );
            }
        }

        return image;
    }

    public static ImageIcon generateQRCodeIcon(
            String text,
            int width,
            int height
    ) throws Exception {

        BufferedImage image =
                generateQRCode(text, width, height);

        return new ImageIcon(image);
    }
}