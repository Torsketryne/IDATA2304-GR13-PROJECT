package idata2304.group13.gui.greenhouse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The main GUI window for greenhouse simulator.
 *
 * @author Girst
 */
public class MainGreenhouseGuiWindow extends Scene {
  public static final int WIDTH = 300;
  public static final int HEIGHT = 300;

  /**
   * Create a new instance for the main GUI window.
   */
  public MainGreenhouseGuiWindow() {
    super(createMainContent(), WIDTH, HEIGHT);
  }

  /**
   * Create the main content of the window.
   *
   * @return An object containing the main content of the window.
   */
  private static Parent createMainContent() {
    VBox container = new VBox(createInfoLabel(), createMasterImage(), createCopyrightNotice());
    container.setPadding(new Insets(20));
    container.setAlignment(Pos.CENTER);
    container.setSpacing(5);
    return container;
  }

  /**
   * Create a label with information about the app.
   *
   * @return the informational message.
   */
  private static Label createInfoLabel() {
    Label l = new Label("Close this window to stop the whole simulation");
    l.setWrapText(true);
    l.setPadding(new Insets(0, 0, 10, 0));
    return l;
  }

  /**
   *  Create and display an image in the window.
   *
   * @return An error message if there is missing file.
   */
  private static Node createCopyrightNotice() {
    Label l = new Label("Image generated with Picsart");
    l.setFont(Font.font(10));
    return l;
  }

  /**
   * Creates and display an image in the window.
   *
   * @return An error message if there is missing file.
   */
  private static Node createMasterImage() {
    Node node;
    try {
      InputStream fileContent = new FileInputStream("images/picsart_chuck.jpeg");
      ImageView imageView = new ImageView();
      imageView.setImage(new Image(fileContent));
      imageView.setFitWidth(100);
      imageView.setPreserveRatio(true);
      node = imageView;
    } catch (FileNotFoundException e) {
      node = new Label("Could not find image file: " + e.getMessage());
    }
    return node;
  }

}
