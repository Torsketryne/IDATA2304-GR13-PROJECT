package idata2304.group13.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Class for image processing.
 *
 * I used these sources for it however it was mostly their code that I used:
 * https://stackoverflow.com/questions/3211156/how-to-convert-image-to-byte-array-in-java
 * https://www.programcreek.com/2009/02/java-convert-image-to-byte-array-convert-byte-array-to-image/
 */
public class ImageProcessing {

        /**
        * Convert an image to a byte array.
        *
        * @param imageName The image to convert
        * @return The String representation of the byte array
        */
        public static String imageToByteArray(String imageName) {
            try {
                String dirName = "images/";
                BufferedImage image = ImageIO.read(new File(dirName + imageName + ".jpg"));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                baos.flush();

                String base64String=Base64.getEncoder().encodeToString(baos.toByteArray());
                baos.close();
                return base64String;
            } catch (Exception e) {
                System.out.println("Error converting image to byte array: " + e.getMessage());
            }
            return null;
        }

    /**
     * Convert a byte array to an image.
     * @param base64String The byte array to convert
     * @param imageName The name of the image
     */
    public static void byteArrayToImage(String base64String, String imageName) {
        try {
            byte[] imageByte = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageByte);

            BufferedImage image = ImageIO.read(bais);
            bais.close();

            String dirName = "images/";
            ImageIO.write(image, "jpg", new File(dirName + imageName + "1.jpg"));
        } catch (IOException e) {
            System.out.println("Error converting byte array to image: " + e.getMessage());
        }
    }


}
