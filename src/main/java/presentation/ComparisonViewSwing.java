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
    private JButton returnButton;
    private JTextArea originalText;
    private JTextArea decompressedText;

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
        returnButton = new JButton("return");
        originalText = new JTextArea(5, 25);
        decompressedText = new JTextArea(5, 25);
        LOGGER.debug("Instances initiated");
    }

    private void initComponents() {
        LOGGER.debug("Initiating components");
        initViewFrame();
        initViewPanel();

        addListeners();
        LOGGER.debug("Components initiated");
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
        viewPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.ipady = 100;
//        constraints.weightx = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 5;
        viewPanel.add(originalText, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.ipady = 100;
//        constraints.weightx = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 5;
        viewPanel.add(decompressedText, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.ipady = 10;
//        constraints.weightx = 0.0;
//        constraints.gridwidth = 3;
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        viewPanel.add(returnButton, constraints);


        LOGGER.debug("View Panel initiated");
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
}
