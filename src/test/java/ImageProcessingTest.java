package idata2304.group13.tools;

import org.junit.jupiter.api.Test;

import java.io.File;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the ImageProcessing class.
 *
 * @author MoldyDaniel
 */
public class ImageProcessingTest {

  private final String originalImage = "picsart_chuck"; // Name of the original image without extension
  private final String generatedImage = "test1"; // Name of the generated image
  private final String imageDir = "images/";
  @Test
  void testImageToByteArray() {
    String base64String = ImageProcessing.imageToByteArray(originalImage);
    assertNotNull(base64String);
  }

  /**
   * Ai was used to help create this class
   */
  @Test
  void testByteArrayToImage() {
    String base64String = ImageProcessing.imageToByteArray(originalImage);
    ImageProcessing.byteArrayToImage(base64String, generatedImage);
    File file = new File(imageDir + generatedImage + ".jpg");
    assertTrue(file.exists());
  }
}