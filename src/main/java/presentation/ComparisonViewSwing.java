package presentation;

import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ComparisonViewSwing {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewSwing.class);

    private PresentationController presentationController;

    final static String TEXT_PANEL = "Card with textArea";
    final static String IMAGE_PANEL = "Card with canvas";

    private JFrame viewFrame;
    private JPanel viewPanel;

    private JPanel labelsPanel;
    private JLabel originalLabel;
    private JLabel decompressedLabel;

    private JPanel comparisonPanel;
    private JPanel originalContentPanel;
    private JPanel decompressedContentPanel;
    private CardLayout leftCardLayout;
    private CardLayout rightCardLayout;
    BoundedRangeModel originalTextVerticalModel;
    BoundedRangeModel originalImageVerticalModel;
    BoundedRangeModel originalTextHorizontalModel;
    BoundedRangeModel originalImageHorizontalModel;
    private JTextArea originalText;
    private JTextArea decompressedText;
    private CompareCanvas originalImage;
    private CompareCanvas decompressedImage;

    private JPanel buttonPanel;
    private JButton returnButton;


    public ComparisonViewSwing(PresentationController presentationController) {
        LOGGER.info("Constructing Main View");
        this.presentationController = presentationController;
        initInstances();
        initComponents();
        LOGGER.info("Main View constructed");
    }

    private void initInstances() {
        LOGGER.debug("Initiating instances");
        viewFrame = new JFrame("Comparison View");
        viewPanel = new JPanel();
        comparisonPanel = new JPanel();
        buttonPanel = new JPanel();
        leftCardLayout = new CardLayout();
        rightCardLayout = new CardLayout();
        originalContentPanel = new JPanel();
        decompressedContentPanel = new JPanel();
        returnButton = new JButton("return");
        originalText = new JTextArea("original text");
        decompressedText = new JTextArea("decompressed text");
        originalImage = new CompareCanvas();
        decompressedImage = new CompareCanvas();
        labelsPanel = new JPanel();
        originalLabel = new JLabel("Original");
        decompressedLabel = new JLabel("Decompressed");
        LOGGER.debug("Instances initiated");
    }

    private void initComponents() {
        LOGGER.debug("Initiating components");
        initViewFrame();
        initViewPanel();
        intiLabelsPanel();
        initComparisonPanel();
        initOriginalContentPanel();
        initCompressedContentPanel();
        initButtonPanel();
        addListeners();
        LOGGER.debug("Components initiated");
    }

    private void initViewFrame() {
        LOGGER.debug("Initiating View Frame");
        viewFrame.setMinimumSize(new Dimension(1200, 600));
        viewFrame.setPreferredSize(viewFrame.getMinimumSize());
        viewFrame.setResizable(true);
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = (JPanel) viewFrame.getContentPane();
        jPanel.add(viewPanel);
        LOGGER.debug("View Frame initiated");
    }

    private void initViewPanel() {
        LOGGER.debug("Initiating View Panel of comparison view");
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(labelsPanel, BorderLayout.PAGE_START);
        viewPanel.add(comparisonPanel, BorderLayout.CENTER);
        viewPanel.add(buttonPanel, BorderLayout.PAGE_END);
        LOGGER.debug("View Panel initiated");
    }

    private void intiLabelsPanel() {
        labelsPanel.setLayout(new GridLayout(1, 2));
        labelsPanel.add(originalLabel);
        labelsPanel.add(decompressedLabel);
    }

    private void initComparisonPanel() {
        comparisonPanel.setLayout(new GridLayout(1, 2));
        comparisonPanel.add(originalContentPanel);
        comparisonPanel.add(decompressedContentPanel);
    }

    private void initOriginalContentPanel() {
        originalContentPanel.setLayout(leftCardLayout);
        originalText.setEnabled(false);
        originalText.setDisabledTextColor(Color.BLACK);
        JScrollPane originalTextScrollPanel = new JScrollPane(originalText);
        originalTextVerticalModel = originalTextScrollPanel.getVerticalScrollBar().getModel();
        originalTextHorizontalModel = originalTextScrollPanel.getHorizontalScrollBar().getModel();
        originalContentPanel.add(originalTextScrollPanel, TEXT_PANEL);
        JScrollPane originalImageScrollPane = new JScrollPane(originalImage);
        originalImageVerticalModel = originalImageScrollPane.getVerticalScrollBar().getModel();
        originalImageHorizontalModel = originalImageScrollPane.getHorizontalScrollBar().getModel();
        originalContentPanel.add(originalImageScrollPane, IMAGE_PANEL);
    }

    private void initCompressedContentPanel() {
        decompressedContentPanel.setLayout(rightCardLayout);
        decompressedText.setEnabled(false);
        decompressedText.setDisabledTextColor(Color.BLACK);
        JScrollPane decompressedTextScrollPanel = new JScrollPane(decompressedText);
        decompressedTextScrollPanel.getVerticalScrollBar().setModel(originalTextVerticalModel);
        decompressedTextScrollPanel.getHorizontalScrollBar().setModel(originalTextHorizontalModel);
        decompressedContentPanel.add(decompressedTextScrollPanel, TEXT_PANEL);
        JScrollPane decompressedImageScrollPanel = new JScrollPane(decompressedImage);
        decompressedImageScrollPanel.getVerticalScrollBar().setModel(originalImageVerticalModel);
        decompressedImageScrollPanel.getHorizontalScrollBar().setModel(originalImageHorizontalModel);
        decompressedContentPanel.add(decompressedImageScrollPanel, IMAGE_PANEL);
    }

    private void initButtonPanel() {
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(returnButton);
    }


    private void addListeners() {
        LOGGER.debug("Adding listeners");
        addReturnButtonListener();
        LOGGER.debug("Listeners added");
    }

    private void addReturnButtonListener() {
        returnButton.addActionListener(e -> {
            presentationController.changeComparisonViewToMainView();
        });
    }

    public void show() {
        LOGGER.debug("Showing graphical components of comparison view");
        viewFrame.pack();
        viewFrame.setVisible(true);
        LOGGER.debug("Graphical components showed");
    }

    public void hide() {
        viewFrame.setVisible(false);
    }

    public void setDecompressionResult(String pathname) {
        displayResult(pathname, rightCardLayout, decompressedContentPanel, decompressedImage, decompressedText);
    }

    public void setOriginalContent(String pathname) {
        displayResult(pathname, leftCardLayout, originalContentPanel, originalImage, originalText);
    }

    private void displayResult(String pathname, CardLayout cardLayout, JPanel panel, CompareCanvas canvas, JTextArea textArea) {
        String extension = pathname.substring(pathname.length() - 3);
        if ("ppm".equals(extension)) {
            cardLayout.show(panel, IMAGE_PANEL);
            try {
                canvas.setImage(matrixToBufferedImage(presentationController.readPpmImage(pathname)));
            } catch (CompressorException ex) {
                showException(ex);
            }
        } else if ("txt".equals(extension)) {
            cardLayout.show(panel, TEXT_PANEL);
            textArea.setText(presentationController.getContentFromPath(pathname));
        }
    }

    private BufferedImage matrixToBufferedImage(int[][][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int r = matrix[i][j][0];
                int g = matrix[i][j][1];
                int b = matrix[i][j][2];
                Color newColor = new Color(r, g, b);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }

    private void showException(CompressorException ex) {
        String errorMessage = String.format("Error code: %s.\nMessage: %s", ex.getErrorCode().getCode(), ex.getMessage());
        JOptionPane.showMessageDialog(viewFrame, errorMessage, "Message error", JOptionPane.ERROR_MESSAGE);
    }
}
