package presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class ComparisonViewSwing { // TODO: Add javadoc

    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewSwing.class);

    private PresentationController presentationController;

    private JFrame viewFrame;
    private JPanel viewPanel;
    private JPanel comparisonPanel;
    private JPanel originalContentPanel;
    private JPanel decompressedContentPanel;
    private JPanel buttonPanel;
    private CardLayout leftCardLayout;
    private CardLayout rightCardLayou;
    private JButton returnButton;
    private JTextArea originalText;
    private JTextArea decompressedText;
    private Canvas originalImage;
    private Canvas decompressedImage;

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
        rightCardLayou = new CardLayout();
        originalContentPanel = new JPanel();
        decompressedContentPanel = new JPanel();
        returnButton = new JButton("return");
        originalText = new JTextArea("original text");
        decompressedText = new JTextArea("decompressed text");
        originalImage = new Canvas();
        decompressedImage = new Canvas();
        LOGGER.debug("Instances initiated");
    }

    private void initComponents() {
        LOGGER.debug("Initiating components");
        initViewFrame();
        initViewPanel();
        initComparisonPanel();
        initOriginalContentPanel();
        initCompressedContentPanel();
        initButtonPanel();
        addListeners();
        LOGGER.debug("Components initiated");
    }

    private void initViewFrame() {
        LOGGER.debug("Initiating View Frame");
        viewFrame.setMinimumSize(new Dimension(700, 500));
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
//        viewPanel.setLayout(new GridLayout());
//        viewPanel.setLayout(new GridLayout(2, 1));    
//        viewPanel.setLayout(viewFrame);
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.add(comparisonPanel);
//        viewPanel.add(buttonPanel);
        LOGGER.debug("View Panel initiated");
    }

    private void initComparisonPanel() {
        comparisonPanel.setLayout(new GridLayout(1, 2));
        comparisonPanel.add(originalContentPanel);
        comparisonPanel.add(decompressedContentPanel);
    }

    private void initOriginalContentPanel() {
        originalContentPanel.setLayout(leftCardLayout);
        originalContentPanel.add(new JScrollPane(originalText), "text");
        originalContentPanel.add(originalImage, "image");
    }

    private void initCompressedContentPanel() {
        decompressedContentPanel.setLayout(rightCardLayou);
        decompressedContentPanel.add(new JScrollPane(decompressedText), "text");
        decompressedContentPanel.add(decompressedImage, "image");
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

    public void setDecompressionResult(String result) {
        decompressedText.setText(result);
    }

    public void setOriginalContent(String content) {
        originalText.setText(content);
    }
}
