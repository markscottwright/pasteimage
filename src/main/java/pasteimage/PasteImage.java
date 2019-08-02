package pasteimage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PasteImage {

    /*
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * 
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public static void main(String[] args) throws UnsupportedFlavorException,
            IOException {
        File output = args.length > 0 ? new File(args[0])
                : new File("clipboard.png");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            Image image = (Image) clipboard.getData(DataFlavor.imageFlavor);
            ImageIO.write(toBufferedImage(image), "PNG", output);
            System.out.println("Image written to " + output.getAbsolutePath());
        } else {
            System.out.println("No image in clipboard");
        }
    }
}
