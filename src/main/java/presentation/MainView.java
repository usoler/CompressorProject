package presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        initToolBarPanel();
        rightPanel.add(toolBarPanel, gridBagConstraints);

        initHistoryPanel();
        gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new Insets(50, 20, 0, 0);
        rightPanel.add(historyPanel, gridBagConstraints);

        //rightPanel.add(dataFilePanel);
    }

    private void initLeftPanel() {
        LOGGER.debug("Initiating Left Panel");
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.ipady = 70;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.insets = new Insets(10,10,0,10);
        dragAndDropButtonComponent.setMaximumSize(new Dimension(125, 125));
        stadisticalsButtonComponent.setMaximumSize(new Dimension(125, 30));
        leftPanel.add(dragAndDropButtonComponent, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(110,5,0,5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        leftPanel.add(stadisticalsButtonComponent, gridBagConstraints);
    }

    private void initToolBarPanel() {
        LOGGER.debug("Initiating Tool Bar Panel");
        toolBarPanel.add(addFileButtonComponent);
        toolBarPanel.add(searchTextFieldComponent);
        toolBarPanel.add(searchButtonComponent);
    }

    private void initHistoryPanel() {
        LOGGER.debug("Initiating History Panel");
        historyPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // ALL FILES
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        historyPanel.add(historyLabelComponent, gridBagConstraints);

        // Table
        gridBagConstraints.insets = new Insets(10,0,0,0);
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridy = 1;
        Table table = new Table(new String[]{"NAME", "TYPE", "DATE", "SIZE"});
        table.setLayout(new GridLayout());
        historyPanel.add(table, gridBagConstraints);

        // ScrollPane
        gridBagConstraints.insets = new Insets(5,0,0,0);
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        String[] params = new String[]{"Filename", "PPM", "12/12/12", "2MB"};

        Table table1 = new Table(params);
        Table table2 = new Table(params);
        Table table3 = new Table(params);
        Table table4 = new Table(params);
        Table table5 = new Table(params);

        table1.setLayout(new GridLayout());
        table2.setLayout(new GridLayout());
        table3.setLayout(new GridLayout());
        table4.setLayout(new GridLayout());
        table5.setLayout(new GridLayout());

        Panel gridPanel = new Panel(new GridLayout(0, 1, 1, 1));
        gridPanel.add(table1);
        gridPanel.add(table2);
        gridPanel.add(table3);
        gridPanel.add(table4);
        gridPanel.add(table5);

        scrollPane.add(gridPanel);
        scrollPane.setSize(75,75);

        historyPanel.add(scrollPane, gridBagConstraints);
    }
}
