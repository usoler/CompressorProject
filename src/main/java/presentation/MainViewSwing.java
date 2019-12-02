package presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainViewSwing {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewSwing.class);
    private static final String[] COLUMN_NAMES = {"Name", "Date", "Size", "Extension"};

    private PresentationController presentationController;

    private JFrame viewFrame;
    private JPanel viewPanel;

    private JPanel historyPanel;
    private JButton addFileButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel allFilesLabel;
    private JTable historyTable;
    private JScrollPane scrollPane;

    public MainViewSwing(PresentationController presentationController) {
        LOGGER.info("Constructing Main View");
        this.presentationController = presentationController;

        initInstances();
        initComponents();

        LOGGER.info("Main View constructed");
    }

    public void show() {
        LOGGER.debug("Showing graphical components");
        viewFrame.pack();
        viewFrame.setVisible(true);
        LOGGER.debug("Graphical components showed");
    }

    private void initInstances() {
        LOGGER.debug("Initiating instances");
        viewFrame = new JFrame("Main View");
        viewPanel = new JPanel();

        // History Panel ------------------------------------
        historyPanel = new JPanel();
        addFileButton = new JButton("+ Add File");
        searchTextField = new JTextField(20);
        searchButton = new JButton("Search");
        allFilesLabel = new JLabel("All Files");
        historyTable = new JTable(new DefaultTableModel(new Object[][]{}, COLUMN_NAMES));
        scrollPane = new JScrollPane(historyTable);
        // --------------------------------------------------

        LOGGER.debug("Instances initiated");
    }

    private void initComponents() {
        LOGGER.debug("Initiating components");
        initViewFrame();
        initViewPanel();
        initHistoryPanel();

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
        LOGGER.debug("Initiating View Panel");
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(historyPanel);
        LOGGER.debug("View Panel initiated");
    }

    private void initHistoryPanel() {
        LOGGER.debug("Initiating History Panel");
        historyPanel.add(addFileButton);
        historyPanel.add(searchTextField);
        historyPanel.add(searchButton);
        historyPanel.add(scrollPane);
        historyTable.setDefaultEditor(Object.class, null);
        LOGGER.debug("History Panel initiated");
    }

    private void addListeners() {
        LOGGER.debug("Adding listeners");
        addHistoryTableListeners();
        addAddFileButtonListeners();
        LOGGER.debug("Listeners added");
    }

    private void addHistoryTableListeners() {
        historyTable.getSelectionModel().addListSelectionListener(event -> {
            System.out.println(historyTable.getValueAt(historyTable.getSelectedRow(), 0).toString());
        });
    }

    private void addAddFileButtonListeners() {
        addFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int selection = fileChooser.showOpenDialog(historyPanel);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Selected file with pathname: " + file.getAbsolutePath());
                presentationController.addFile(file.getAbsolutePath());
                addRowToTableFromFile(file);
            } else if (selection == JFileChooser.ERROR_OPTION) {
                // TODO: throw exception
            }
        });
    }

    private void addRowToTableFromFile(File file) {
        String[] fileParts = file.getName().split("\\.");
        DefaultTableModel tableModel = (DefaultTableModel) historyTable.getModel();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        tableModel.addRow(new Object[]{fileParts[0], formatter.format(new Date()), getSizeFromFile(file), fileParts[1]});
    }

    private String getSizeFromFile(File file) {
        double bytesSize = (double) (file.length() / (1024 * 1024));
        double roundedSize = (Math.round(bytesSize * 100.0) / 100.0);
        if (roundedSize <= 0.1) {
            return Double.toString(file.length()) + " B";
        } else if (roundedSize < 0.5 && roundedSize > 0.1) {
            return Double.toString((Math.round(bytesSize * 100.0) / 100.0)) + " KB";
        } else {
            bytesSize = (double) file.length() / 1024;
            return Double.toString((Math.round(bytesSize * 100.0) / 100.0)) + " MB";
        }
    }
}



























