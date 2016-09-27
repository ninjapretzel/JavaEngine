
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;

class ExtractImage {
    /* get a file name, produce a file with same prefix (before .) 
     and suffix .raw
     in raw format giving # of rows, # of columns, and
     r g b a for each pixel
     */

    public static void main(String[] args) throws Exception {
        String fileName = args[0];

        // transform the image file to an Image
        Image image = null;

        Frame observer = new Frame();

        try {
            MediaTracker tracker = new MediaTracker(observer);
            image = Toolkit.getDefaultToolkit().getImage(fileName);
            tracker.addImage(image, 1);
            try {
                tracker.waitForID(1);
            } catch (InterruptedException e) {
            }
        } catch (Exception e) {
            System.out.println("some problem getting the image");
            System.exit(0);
        }

        // get the actual size of the image:
        int actualWidth, actualHeight;
        actualWidth = image.getWidth(observer);
        actualHeight = image.getHeight(observer);

        System.out.println("found actual size of "
                + actualHeight + " by " + actualWidth);

        // obtain the image data 
        int[] pixels = new int[actualWidth * actualHeight];
        try {
            PixelGrabber pg = new PixelGrabber(image, 0, 0, actualWidth,
                    actualHeight, pixels, 0, actualWidth);
            pg.grabPixels();
            if ((pg.status() & ImageObserver.ABORT) != 0) {
                System.out.println("error while grabbing pixels");
                System.exit(0);
            }
        } catch (InterruptedException e) {
            System.out.println("pixel grabbing was interrupted");
            System.exit(0);
        }

     // pull each int apart to obtain the individual pieces
        // and write them to the output file
        // prepare the output file
        String outFileName
                = fileName.substring(0, fileName.indexOf('.')) + ".raw";;

        System.out.println(
                "about to work on output file named: [" + outFileName + "]");

        PrintWriter outFile = new PrintWriter(new File(outFileName));

        int alpha, red, green, blue, pixel;
        int k;

        outFile.println(actualWidth + " " + actualHeight);

        for (k = 0; k < actualWidth * actualHeight; k++) {
            pixel = pixels[k];
            alpha = (pixel >> 24) & 0xff;
            red = (pixel >> 16) & 0xff;
            green = (pixel >> 8) & 0xff;
            blue = (pixel) & 0xff;

            outFile.println(red + " " + green + " " + blue + " " + alpha);

        }

        outFile.close();

    }// main

}
