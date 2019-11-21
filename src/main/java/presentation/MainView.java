package presentation;

import java.awt.*;
import java.awt.event.*;

public class MainView {

    private PresentationController presentationController;

    private Frame frame = new Frame("Compressor 3000");
    private Panel contentPanel = new Panel();

    private Panel leftPanel = new Panel();
    private Button statsButton = new Button("Stats");
    // TODO Add drag&drop component

    private Panel buttonsPanel = new Panel();
    private Button addButton = new Button("+ ADD");
    private Button searchButton = new Button("Search");
    private TextField searchBox = new TextField();

    // Perhaps it would be better to create a table class
    private Panel historyPanel = new Panel();

    private Panel informationPanel = new Panel();

    public MainView(PresentationController controller) {
        presentationController = controller;
        initializeComponents();
    }

    private void initializeComponents() {
        initializeFrame();
        initializeContentPanel();
        initializeLeftPanel();
        initializeButtonsPanel();
        initializeHistoryPanel();
        initializeInformationPanel();
    }

    private void initializeFrame() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.add(contentPanel);
    }

    private void initializeContentPanel() {
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(buttonsPanel);
    }

    private void initializeLeftPanel() {
    }

    private void initializeButtonsPanel() {
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(addButton);
        buttonsPanel.add(searchBox);
        buttonsPanel.add(searchButton);
    }

    private void initializeHistoryPanel() {}

    private void initializeInformationPanel() {}

    public void setVisible() {
        frame.pack();
        //frame.setSize(640, 480);
        frame.setVisible(true);
    }
}
