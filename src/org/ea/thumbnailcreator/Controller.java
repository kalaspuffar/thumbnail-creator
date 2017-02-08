package org.ea.thumbnailcreator;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Controller {
    private final int IMAGE_WIDTH = 1280;
    private final int IMAGE_HEIGHT = 760;
    private final String FONT_FAMILY = "Segoe UI";
    private BufferedImage originalImage;
    private BufferedImage currentImage;

    @FXML
    private TextField partText; // value will be injected by the FXMLLoader
    @FXML
    private TextField gameName; // value will be injected by the FXMLLoader
    @FXML
    private ImageView imageArea; // value will be injected by the FXMLLoader
    @FXML
    private Button fileChooseButton; // value will be injected by the FXMLLoader
    @FXML
    private Button saveFileButton; // value will be injected by the FXMLLoader
    @FXML
    private Button renderFileButton; // value will be injected by the FXMLLoader

    @FXML
    private void openImageFile(ActionEvent ae){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        try {
            originalImage = ImageIO.read(file);
            renderImage(ae);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveImageFile(ActionEvent ae){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(currentImage, "png", file);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void findSize(Graphics2D g2d, String gameName) {
        int stringSize = 2000;

        int fontSize = 136;
        while (stringSize > IMAGE_WIDTH - 30) {
            g2d.setFont(new Font(FONT_FAMILY, Font.BOLD, fontSize));
            FontMetrics fm = g2d.getFontMetrics();
            stringSize = fm.stringWidth(gameName);
            fontSize--;
        }
    }

    @FXML
    private void renderImage(ActionEvent ae){
        java.awt.Image transistionImg = originalImage.getScaledInstance(1280, 720, java.awt.Image.SCALE_SMOOTH);
        currentImage = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = currentImage.createGraphics();
        g2d.drawImage(transistionImg, 0, 0, null);

        g2d.setFont(new Font(FONT_FAMILY, Font.BOLD, 136));
        String gameNameText = gameName.getText();
        String partNameText = partText.getText();
        FontMetrics fm = g2d.getFontMetrics();

        g2d.setPaint(Color.BLACK);
        g2d.drawString(partNameText, 25, IMAGE_HEIGHT - 65);

        g2d.setPaint(Color.WHITE);
        g2d.drawString(partNameText, 20, IMAGE_HEIGHT - 70);

        findSize(g2d, gameNameText);
        fm = g2d.getFontMetrics();

        g2d.setPaint(Color.BLACK);
        g2d.drawString(gameNameText, 25, 33 + (fm.getHeight() / 2));

        g2d.setPaint(Color.WHITE);
        g2d.drawString(gameNameText, 20, 28 + (fm.getHeight() / 2));
        g2d.dispose();

        Image image = SwingFXUtils.toFXImage(currentImage, null);
        imageArea.setImage(image);
    }
}
