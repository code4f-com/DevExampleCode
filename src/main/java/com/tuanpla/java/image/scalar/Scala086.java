/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.java.image.scalar;

import com.mortennobel.imagescaling.ProgressListener;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author TUANPLA
 */
public class Scala086 {

    public static void main(String[] args) {
//        if (args.length != 4) {
//            System.out.println("Usage java RescaleImage [sourcefile] [destfile] [newwidth] [newheight]");
//            System.exit(1);
//        }
        try {
            String dirPath = "D:/Crop";
            File f = new File(dirPath);
            if (!f.isDirectory()) {
                f.mkdir();
            }
            
            String sourcefile = "D:/screen568x568.jpg";
            String destfile = dirPath + "/" + "new_getScaledImage.jpg";
            int newwidth = 90;
            int newheight = 159;

            BufferedImage sourceImage = ImageIO.read(new File(sourcefile));
            ResampleOp resizeOp = new ResampleOp(newwidth, newheight);
            resizeOp.addProgressListener(new ProgressListener() {
                @Override
                public void notifyProgress(float fraction) {
                    System.out.printf("Resizing %f%n", fraction);
                }
            });
            BufferedImage resizedImage = resizeOp.filter(sourceImage, null);
            ImageIO.write(resizedImage, "jpg", new File(destfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
