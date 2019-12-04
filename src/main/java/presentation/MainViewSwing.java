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

    // History Panel ----------------------
    private JPanel historyPanel;
    private JButton addFileButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel allFilesLabel;
    private JTable historyTable;
    private JScrollPane scrollPane;
    // -----------------------------------


    // File Data Panel ------------------------------
    private JPanel dataFilePanel;

    private JPanel fileInfoPanel;
    private JLabel filenameLabel;
    private JLabel dateLabel;
    private JLabel formatLabel;

    private JPanel compressedFileInfoPanel;
    private JLabel compressedLabel;
    private JLabel originalSizeLabel;

    private JPanel uncompressedFileInfoPanel;
    private JLabel uncompressedLabel;
    private JLabel newSizeLabel;

    private JPanel controlComponentsPanel;
    private JButton compressButton;
    private JButton uncompressButton;
    private JComboBox<String> algorithmComboBox;
    // ---------------------------------------------

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

        // Data File Panel ----------------------------------
        dataFilePanel = new JPanel();
        fileInfoPanel = new JPanel();
        filenameLabel = new JLabel("Filename: -");
        dateLabel = new JLabel("Date: -");
        formatLabel = new JLabel("Format: -");
        compressedFileInfoPanel = new JPanel();
        compressedLabel = new JLabel("Compressed");
        originalSizeLabel = new JLabel("Original Size: (*)");
        uncompressedFileInfoPanel = new JPanel();
        uncompressedLabel = new JLabel("Uncompressed");
        newSizeLabel = new JLabel("New Size: (*)");
        controlComponentsPanel = new JPanel();
        compressButton = new JButton("Compress");
        uncompressButton = new JButton("Uncompress");
        algorithmComboBox = new JComboBox<>();
        algorithmComboBox.addItem("LZ78");
        algorithmComboBox.addItem("LZW");
        algorithmComboBox.addItem("JPEG");
        // --------------------------------------------------

        LOGGER.debug("Instances initiated");
    }

    private void initComponents() {
        LOGGER.debug("Initiating components");
        initViewFrame();
        initViewPanel();
        initHistoryPanel();
        initDataFilePanel();

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
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.add(historyPanel);
        viewPanel.add(dataFilePanel);
        LOGGER.debug("View Panel initiated");
    }

    private void initHistoryPanel() {
        LOGGER.debug("Initiating History Panel");
        historyPanel.add(addFileButton);
        historyPanel.add(searchTextField);
        historyPanel.add(searchButton);
        historyPanel.add(allFilesLabel);
        historyPanel.add(scrollPane);
        historyTable.setDefaultEditor(Object.class, null);
        LOGGER.debug("History Panel initiated");
    }

    private void initDataFilePanel() {
        LOGGER.debug("Initiating Data File Panel");
        dataFilePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(50, 50, 0, 0);
        dataFilePanel.add(fileInfoPanel, constraints);
        initFileInfoPanel();
        constraints.gridx = 1;
        dataFilePanel.add(compressedFileInfoPanel, constraints);
        initCompressedFileInfoPanel();
        constraints.gridx = 2;
        dataFilePanel.add(uncompressedFileInfoPanel, constraints);
        initUncompressedFileInfoPanel();
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 2;
        dataFilePanel.add(controlComponentsPanel, constraints);
        initControlComponentsPanel();
        LOGGER.debug("Data File Panel initiated");
    }

    private void initFileInfoPanel() {
        fileInfoPanel.setLayout(new BoxLayout(fileInfoPanel, BoxLayout.Y_AXIS));
        fileInfoPanel.add(filenameLabel);
        fileInfoPanel.add(dateLabel);
        fileInfoPanel.add(formatLabel);
    }

    private void initCompressedFileInfoPanel() {
        compressedFileInfoPanel.setLayout(new BoxLayout(compressedFileInfoPanel, BoxLayout.Y_AXIS));
        compressedFileInfoPanel.add(originalSizeLabel);
        compressedFileInfoPanel.add(compressedLabel);
    }

    private void initUncompressedFileInfoPanel() {
        uncompressedFileInfoPanel.setLayout(new BoxLayout(uncompressedFileInfoPanel, BoxLayout.Y_AXIS));
        uncompressedFileInfoPanel.add(newSizeLabel);
        uncompressedFileInfoPanel.add(uncompressedLabel);
    }

    private void initControlComponentsPanel() {
        controlComponentsPanel.add(algorithmComboBox);
        controlComponentsPanel.add(compressButton);
        controlComponentsPanel.add(uncompressButton);
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



























