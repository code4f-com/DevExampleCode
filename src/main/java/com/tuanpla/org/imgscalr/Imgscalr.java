package com.tuanpla.org.imgscalr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import static org.imgscalr.Scalr.OP_ANTIALIAS;
import static org.imgscalr.Scalr.OP_BRIGHTER;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TUANPLA
 */
public class Imgscalr {

    public static void main(String[] args) {
        try {
            String dirPath = "D:/Crop";
            File f = new File(dirPath);
            if (!f.isDirectory()) {
                f.mkdir();
            }
            String destpath = dirPath + "/" + "ULTRA_QUALITY.jpg";
            File outputFile = new File(destpath);
            FileInputStream fis = new FileInputStream("D:/screen568x568.jpg");
            // Doc file goc vao Buffer Image
            BufferedImage bimage = ImageIO.read(fis);
            BufferedImage thumbnail
                    //                    = createThumbnail(bimage);
//                    = Scalr.crop(bimage, 90, 90, 90, 90, Scalr.OP_ANTIALIAS);
//                    = Scalr.crop(bimage, 90, 90, Scalr.OP_ANTIALIAS);
                    = Scalr.resize(bimage, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 90, 100, Scalr.OP_ANTIALIAS);
            ImageIO.write(thumbnail, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static BufferedImage createThumbnail(BufferedImage img) {
        // Create quickly, then smooth and brighten it.
        img = Scalr.resize(img, Method.SPEED, 125, OP_ANTIALIAS, OP_BRIGHTER);
        // Let's add a little border before we return result.
        return Scalr.pad(img, 1);
    }

    public static BufferedImage resizeWithScalr(BufferedImage originalImage, int width, int height) {
        return Scalr.resize(
                originalImage,
                Scalr.Method.QUALITY,
                Scalr.Mode.FIT_EXACT,
                width,
                height,
                Scalr.OP_ANTIALIAS);
    }
}
