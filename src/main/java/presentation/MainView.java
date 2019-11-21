package presentation;

import java.awt.*;
import java.awt.event.*;

public class MainView {

    private PresentationController presentationController;

    private Frame frame = new Frame("Compressor 3000");
    private Button addButton = new Button("+ ADD");
    private Button searchButton = new Button("Search");
    private TextField searchBox = new TextField();

    public MainView(PresentationController controller) {
        presentationController = controller;
        initializeComponents();
    }

    private void initializeComponents() {
        initializeFrame();

    }

    private void initializeFrame() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void setVisible() {
        //frame.pack();
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}
