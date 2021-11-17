/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.org.imgscalr;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author TUANPLA
 */
public class ImageUtil {

    public static boolean zoomAndCrop() {
        boolean result = false;

        return result;
    }

    public static void main(String[] args) {
        try {
            String dirPath = "D:/Crop";
            File f = new File(dirPath);
            if (!f.isDirectory()) {
                f.mkdir();
            }
            String destpath = dirPath + "/" + "new_getScaledImage.jpg";
            File outputFile = new File(destpath);
            FileInputStream fis = new FileInputStream("D:/screen568x568.jpg");
            // Doc file goc vao Buffer Image
            BufferedImage bimage = ImageIO.read(fis);
            BufferedImage oneimg = scale(bimage, BufferedImage.TYPE_INT_ARGB_PRE, 90, 90, 320, 586);
            ImageIO.write(oneimg, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * we want the x and o to be resized when the JFrame is resized
     *
     * @param originalImage an x or an o. Use cross or oh fields.
     *
     * @param biggerWidth
     * @param biggerHeight
     */
    private Image resizeToBig(Image originalImage, int biggerWidth, int biggerHeight) {
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage resizedImage = new BufferedImage(biggerWidth, biggerHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, this);
        g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, (ImageObserver) this);
        g.dispose();

        return resizedImage;
    }

    /**
     * scale image
     *
     * @param sbi image to scale
     * @param imageType type of image
     * @param dWidth width of destination image
     * @param dHeight height of destination image
     * @param fWidth x-factor for transformation / scaling
     * @param fHeight y-factor for transformation / scaling
     * @return scaled image
     */
    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if (sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

    /**
     *
     * @param cropHeight image affter Crop Height
     * @param cropWidth image affter Crop Width
     * @param windowLeft
     * @param windowTop
     * @param srcFile
     * @param destFilename
     * @param commonPadding // Pading image
     * @param fileFormat
     * @return
     * @throws IOException
     */
    public static boolean CropImage(int cropWidth, int cropHeight, int windowLeft, int windowTop, File srcFile,
            String destFilename, int commonPadding, String fileFormat) throws IOException {
        boolean isOkResolution = false;
        try {
            String dirPath = "D:/Crop";
            File f = new File(dirPath);
            if (!f.isDirectory()) {
                f.mkdir();
            }
            String destpath = dirPath + "/" + destFilename;
            File outputFile = new File(destpath);
            FileInputStream fis = new FileInputStream(srcFile);
            // Doc file goc vao Buffer Image
            BufferedImage bimage = ImageIO.read(fis);
            System.out.println("Image Origilnal Height==" + bimage.getHeight());
            BufferedImage oneimg = new BufferedImage(cropWidth, cropHeight, bimage.getType());
            Graphics2D gr2d = oneimg.createGraphics();
            isOkResolution = gr2d.drawImage(bimage, 0, 0, cropWidth, cropHeight, windowLeft - commonPadding, windowTop - commonPadding, (windowLeft + cropWidth) - commonPadding, (windowTop + cropHeight) - commonPadding, null);
            gr2d.dispose();
            ImageIO.write(oneimg, fileFormat, outputFile);
        } catch (FileNotFoundException fe) {
            System.out.println("No File Found ==" + fe);
        } catch (Exception ex) {
            System.out.println("Error in Croping File=" + ex);
            ex.printStackTrace();
        }
        return isOkResolution;
    }

    public static boolean CropImageCenter(int cropWidth, int cropHeight, File srcFile,
            String destFilename, int commonPadding, String fileFormat) throws IOException {
        boolean isOkResolution = false;
        try {
            String dirPath = "D:/Crop";
            File f = new File(dirPath);
            if (!f.isDirectory()) {
                f.mkdir();
            }
            String destpath = dirPath + "/" + destFilename;
            File outputFile = new File(destpath);
            FileInputStream fis = new FileInputStream(srcFile);
            // Doc file goc vao Buffer Image
            BufferedImage bimage = ImageIO.read(fis);
            System.out.println("Image Origilnal Height==" + bimage.getHeight());
            int rootX = bimage.getWidth();
            int rootY = bimage.getHeight();
            //
            System.out.println("rootX: " + rootX + "| rootY:" + rootY);
            double rootRate = (double) rootX / rootY;
            double newRate = (double) cropWidth / cropHeight;
            System.out.println("rootRate: " + rootRate);
            System.out.println("newRate: " + newRate);
            if (rootRate < newRate) {
                bimage = resizeImageWidth(bimage, bimage.getType(), cropWidth);
                System.out.println("Affter Rezie Width:" + bimage.getWidth() + "|" + bimage.getHeight());
            } else {
                bimage = resizeImageHeight(bimage, bimage.getType(), cropHeight);
                System.out.println("Affter Rezie Height: " + bimage.getWidth() + "|" + bimage.getHeight());
            }
            //
            rootX = bimage.getWidth();
            rootY = bimage.getHeight();
            rootX = (rootX - cropWidth) / 2;
            rootY = (rootY - cropHeight) / 2;
            System.out.println("X:" + rootX + "| Y:" + rootY);
            BufferedImage oneimg = new BufferedImage(cropWidth, cropHeight, bimage.getType());
            Graphics2D gr2d = oneimg.createGraphics();
            // Them vao 
            gr2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            isOkResolution = gr2d.drawImage(bimage, 0, 0, cropWidth, cropHeight, rootX, rootY, rootX + cropWidth, rootY + cropHeight, null);
            gr2d.dispose();
            ImageIO.write(oneimg, fileFormat, outputFile);
        } catch (FileNotFoundException fe) {
            System.out.println("No File Found ==" + fe);
        } catch (Exception ex) {
            System.out.println("Error in Croping File=" + ex);
            ex.printStackTrace();
        }
        return isOkResolution;
    }

    public static BufferedImage resizeImageWidth(BufferedImage originalImage, int type, int image_with) {
        float IMG_HEIGHT = originalImage.getHeight() / ((float) originalImage.getWidth() / image_with);
        BufferedImage resizedImage = new BufferedImage(image_with, (int) IMG_HEIGHT, type);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, image_with, (int) IMG_HEIGHT, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeImageHeight(BufferedImage originalImage, int type, int image_heigth) {
        float image_with = originalImage.getWidth() / ((float) originalImage.getHeight() / image_heigth);
        BufferedImage resizedImage = new BufferedImage((int) image_with, image_heigth, type);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, (int) image_with, (int) image_heigth, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
