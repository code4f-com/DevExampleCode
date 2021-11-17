package com.tuanpla.java.image.scalar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author James
 */
public class Example {
    public static void main(String[] args) {
//        String imgLocation = (args.length == 1 ? args[0] : null);
        String imgLocation = "D:/screen568x568.jpg";
        if (imgLocation == null)
            throw new IllegalArgumentException("One argument required: path-to-image");

        try {
            Thumbnails.of(new File("D:/screen568x568.jpg"))
                    .width(90)
                    .outputFormat("jpg")
                    .toFile("Thumbnails.jpg");
            Thumbnails.of(new File("D:/screen568x568.jpg"))
                    .width(90)
                    .outputFormat("jpg")
                    .outputQuality(1)
                    .toFile("Thumbnails1.jpg");
            
            Image img = null;
            if (imgLocation.startsWith("http")) {
                //read the image from a URL
                img = ImageLoader.fromUrl(new URL(imgLocation));
            }
            else {
                File f = new File(imgLocation);
                if (!f.exists() || !f.isFile())
                    throw new IllegalArgumentException("Invalid path to image");
                else {
                    //read the image from a file
                    img = ImageLoader.fromFile(f);
                }
            }

            //output source type
            System.out.println("Image source type: "+ img.getSourceType());
            //output dimensions
            System.out.println("Image dimensions: "+ img.getWidth() +"x"+ img.getHeight());

            //crop it
            Image cropped = img.crop(80, 50, 170, 140);
            cropped.writeToJPG(new File("cropped.jpg"), 0.95f);
            cropped.dispose();

            //resize
            Image resized = img.getResizedToWidth(90);
            //save it with varying softness and quality
//            softenAndSave(resized, 0.95f, 0f);
//            softenAndSave(resized, 0.95f, 0.1f);
//            softenAndSave(resized, 0.95f, 0.2f);
//            softenAndSave(resized, 0.95f, 0.3f);
//            softenAndSave(resized, 0.8f, 0.08f);
//            softenAndSave(resized, 0.6f, 0.08f);
//            softenAndSave(resized, 0.4f, 0.08f);
//            resized.dispose();
            
            //write a 0.95 quality JPG without using Sun's JPG codec
            resized.writeToFile(new File("resized--q0.95--s0.0--nocodec.jpg"));

            //resize it to a square with different settings for edge cropping
//            squareIt(img, 200, 0.0, 0.95f, 0.08f);
//            squareIt(img, 200, 0.1, 0.95f, 0.08f);
//            squareIt(img, 200, 0.2, 0.95f, 0.08f);

            //small thumbs
            squareIt(img, 90, 0.0, 1f, 0.0f);
//            squareIt(img, 90, 0.1, 1f, 0.08f);
//            squareIt(img, 90, 0.2, 1f, 0.08f);            
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private static void softenAndSave(Image img, float quality, float soften) throws IOException {
        img.soften(soften).writeToJPG(new File("resized--q"+ quality +"--s"+ soften +".jpg"), quality);
    }

    private static void squareIt(Image img, int width, double cropEdges, float quality, float soften) throws IOException {
        Image square = img.getResizedToSquare(width, cropEdges).soften(soften);
        square.writeToJPG(new File("square--w"+ width +"--e"+ cropEdges +"--q"+ quality +".jpg"), quality);
        square.dispose();
    }

}
