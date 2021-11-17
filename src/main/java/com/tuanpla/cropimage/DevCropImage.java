/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.cropimage;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Canvas;
import net.coobird.thumbnailator.geometry.Positions;

/**
 *
 * @author TUANPLA
 */
public class DevCropImage {

    public static void main(String[] args) {
        File f = new File("D:/screen568x568.jpg");
        try {
//            CropImageCenter(90, 90, f, "new.jpg", 0, "jpg");
//            CropImage(90, 90, 0, 0, f, "new1.jpg", 0, "jpg");
            Thumbnails.of(f).width(90)
                    .outputQuality(1)
                    .toFile(new File("D:/Crop/Thumbnails0.8.jpg"));
            Thumbnails.of(f).width(320)
                    .outputQuality(1)
                    .toFile(new File("D:/Crop/Thumbnails_goc.jpg"));
            Thumbnails.of(f)
                    .size(90, 90)
                    .crop(Positions.CENTER)
                    .outputQuality(1)
                    .toFile(new File("D:/Crop/Crop.jpg"));
            //asdas
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            // Them vao
            gr2d.setComposite(AlphaComposite.Src);
            gr2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gr2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //---
//        g.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, this);
//        gr2d.drawImage(originalImage, 0, 0, biggerWidth, biggerHeight, (ImageObserver) this);
//        gr2d.dispose();
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

    public static void cropThumbnailCenter(int cropWidth, int cropHeight, File srcFile, File destFilename) {
        try {
            Thumbnails.of(srcFile)
                    .size(cropWidth, cropHeight)
                    .crop(Positions.CENTER)
                    .outputQuality(1)
                    .toFile(destFilename);
        } catch (Exception e) {
        }
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
//                bimage = resizeImageWidth(bimage, bimage.getType(), cropWidth);
                bimage = Thumbnails.of(bimage)
                        .width(cropWidth)
                        .outputQuality(1)
                        .asBufferedImage();
                System.out.println("Affter Rezie Width:" + bimage.getWidth() + "|" + bimage.getHeight());
            } else {
//                bimage = resizeImageHeight(bimage, bimage.getType(), cropHeight);
                bimage = Thumbnails.of(bimage)
                        .height(cropHeight)
                        .outputQuality(1)
                        .asBufferedImage();
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
            gr2d.setComposite(AlphaComposite.Src);
            gr2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gr2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //
            isOkResolution = gr2d.drawImage(bimage, 0, 0, cropWidth, cropHeight, rootX, rootY, rootX + cropWidth, rootY + cropHeight, null);
            gr2d.dispose();
            Thumbnails.of(oneimg)
                    .size(oneimg.getWidth(), oneimg.getHeight())
                    .toFile(outputFile);
//            ImageIO.write(oneimg, fileFormat, outputFile);
        } catch (FileNotFoundException fe) {
            System.out.println("No File Found ==" + fe);
        } catch (Exception ex) {
            System.out.println("Error in Croping File=" + ex);
            ex.printStackTrace();
        }
        return isOkResolution;
    }

    private static BufferedImage resizeImageWidth(BufferedImage originalImage, int type, int image_with) {
        float IMG_HEIGHT = originalImage.getHeight() / ((float) originalImage.getWidth() / image_with);
        BufferedImage resizedImage = new BufferedImage(image_with, (int) IMG_HEIGHT, type);
        Graphics2D gr2d = resizedImage.createGraphics();
        // Them vao 
        gr2d.setComposite(AlphaComposite.Src);
        gr2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //
//        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr2d.drawImage(originalImage, 0, 0, image_with, (int) IMG_HEIGHT, null);
        gr2d.dispose();
        return resizedImage;
    }

    private static BufferedImage resizeImageHeight(BufferedImage originalImage, int type, int image_heigth) {
        float image_with = originalImage.getWidth() / ((float) originalImage.getHeight() / image_heigth);
        BufferedImage resizedImage = new BufferedImage((int) image_with, image_heigth, type);
        Graphics2D gr2d = resizedImage.createGraphics();
        // Them vao 
        gr2d.setComposite(AlphaComposite.Src);
        gr2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //
        gr2d.drawImage(originalImage, 0, 0, (int) image_with, (int) image_heigth, null);
        gr2d.dispose();
        return resizedImage;
    }
}
