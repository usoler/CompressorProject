package presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainView {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainView.class);

    private PresentationController presentationController;

    private Frame viewFrame;
    private Panel viewPanel;

    private Panel rightPanel;
    private Panel leftPanel;

    private Panel toolBarPanel;
    private Panel historyPanel;
    private Panel dataFilePanel;

    private Button dragAndDropButtonComponent; // TODO: provisional
    private Button stadisticalsButtonComponent;
    private Button addFileButtonComponent;
    private Button searchButtonComponent;
    private TextField searchTextFieldComponent;
    private Label historyLabelComponent;

    public MainView(PresentationController controller) {
        LOGGER.debug("Constructing Main View");
        presentationController = controller;

        viewFrame = new Frame("Compressor Application");
        viewPanel = new Panel(new BorderLayout(4, 4));

//        rightPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel = new Panel();
        leftPanel = new Panel();
        toolBarPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        historyPanel = new Panel();

        dragAndDropButtonComponent = new Button("DRAG & DROP");
        stadisticalsButtonComponent = new Button("STATS");
        addFileButtonComponent = new Button("+ ADD FILE");
        searchButtonComponent = new Button("SEARCH");
        searchTextFieldComponent = new TextField(20);
        historyLabelComponent = new Label("ALL FILES");

        initComponents();

        LOGGER.debug("Main View constructed");
    }

    public void show() {
        LOGGER.debug("Showing graphical components");
        viewFrame.pack();
        viewFrame.setVisible(true);
        // Enable components
        // ...
        dragAndDropButtonComponent.setEnabled(true);
        stadisticalsButtonComponent.setEnabled(true);
        addFileButtonComponent.setEnabled(true);
        searchButtonComponent.setEnabled(true);
        searchTextFieldComponent.setEnabled(true);
        historyLabelComponent.setEnabled(true);
    }

    private void initComponents() {
        LOGGER.debug("Initiating graphical components");
        initViewFrame();
        initViewPanel();
        initRightPanel();
        initLeftPanel();
        LOGGER.debug("Graphical components iniciated");
    }

    private void initViewFrame() {
        LOGGER.debug("Initiating View Frame");
        viewFrame.setMinimumSize(new Dimension(700, 400)); // TODO: default
        viewFrame.setPreferredSize(viewFrame.getMinimumSize());
        viewFrame.setLocationRelativeTo(null);

        viewFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        viewFrame.add(viewPanel);
    }

    private void initViewPanel() {
        LOGGER.debug("Initiating View Panel");
        viewPanel.add(rightPanel);
        viewPanel.add(leftPanel, BorderLayout.LINE_START);
    }

    private void initRightPanel() {
        LOGGER.debug("Initiating Right Panel");

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        initToolBarPanel();
        initHistoryPanel();

        rightPanel.add(toolBarPanel);
        rightPanel.add(historyPanel);
        //rightPanel.add(dataFilePanel);
    }

    private void initLeftPanel() {
        LOGGER.debug("Initiating Left Panel");
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        dragAndDropButtonComponent.setMaximumSize(new Dimension(125, 125));
        stadisticalsButtonComponent.setMaximumSize(new Dimension(125, 30));
        leftPanel.add(dragAndDropButtonComponent);
        leftPanel.add(stadisticalsButtonComponent);
    }

    private void initToolBarPanel() {
        LOGGER.debug("Initiating Tool Bar Panel");
        toolBarPanel.add(addFileButtonComponent);
        toolBarPanel.add(searchTextFieldComponent);
        toolBarPanel.add(searchButtonComponent);
    }

    private void initHistoryPanel() {
        LOGGER.debug("Initiating History Panel");


        ScrollPane scrollPane = new ScrollPane();
        String[] params = new String[]{"Filename", "PPM", "12/12/12", "2MB"};
        Table table = new Table(params);
        Table table2 = new Table(params);
        Table table3 = new Table(params);
        table.setLayout(new GridLayout());
        table2.setLayout(new GridLayout());
        table3.setLayout(new GridLayout());
        Panel gridPanel = new Panel(new GridLayout(0, 1, 2, 2));
        gridPanel.add(table);
        gridPanel.add(table2);
        gridPanel.add(table3);

//        table.setPreferredSize(new Dimension(500,400));
        scrollPane.add(gridPanel);
//        scrollPane.setPreferredSize(new Dimension(500,200));

        historyPanel.add(historyLabelComponent);
        historyPanel.add(new Table(new String[]{"NAME", "TYPE", "DATE", "SIZE"}));
        historyPanel.add(scrollPane);
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
//        historyPanel.setPreferredSize(new Dimension(500, 200));
    }
}
