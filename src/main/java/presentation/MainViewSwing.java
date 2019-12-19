package presentation;

import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class MainViewSwing {
    // Green color: 075E54
    // Green light color: 25D366

    private static final Logger LOGGER = LoggerFactory.getLogger(MainViewSwing.class);
    private static final String[] COLUMN_NAMES = {"Name", "Date", "Size", "Extension", "Pathname"};
    private static final String[] STATS_FILES_COLUMN_NAMES = {"Filename", "Algorithm", "Type", "Ratio", "Time", "Speed", "Space"};
    private static final String[] STATS_ALGORITHMS_COLUMN_NAMES = {"Algorithm", "Type", "Ratio", "Time", "Speed", "Space", "# Files"};

    private PresentationController presentationController;

    private JFrame viewFrame;
    private JPanel viewPanel;
    private JTabbedPane tabbedPane;
    private JPanel filesCardPanel;
    private JPanel statsCardPanel;

    // History Panel ----------------------
    private JPanel historyPanel;
    private JPanel historyControlComponentsPanel;
    private JButton addFileButton;
    private JButton removeFileButton;
    private JTextField searchTextField;
    private JLabel searchLabel;
    private TableRowSorter<TableModel> rowSorter;
    private JTable historyTable;
    private JScrollPane scrollPane;
    // -----------------------------------

    // File Data Panel ------------------------------
    private JPanel dataFilePanel;

    private JPanel fileInfoPanel;
    private JLabel filenameLabel;
    private JLabel dateLabel;
    private JLabel extensionLabel;
    private JLabel pathnameLabel;

    private JPanel controlComponentsPanel;
    private JButton compressButton;
    private JButton uncompressButton;
    private JButton compressAndUncompressButton;
    private JComboBox<String> algorithmComboBox;
    // ---------------------------------------------

    // Stats Files Panel ------------------------------
    private JPanel statsFilesPanel;
    private JTable statsFilesTable;
    private JScrollPane statsFilesScrollPane;
    // ------------------------------------------------

    // Stats Algorithms Panel -------------------------
    private JPanel statsAlgorithmsPanel;
    private JTable statsAlgorithmsTable;
    private JScrollPane statsAlgorithmsScrollPane;
    // ------------------------------------------------

    /**
     * Constructs a new {@link MainViewSwing} with a given {@link PresentationController} and initializes it
     *
     * @param presentationController the {@link PresentationController} to link
     */
    public MainViewSwing(PresentationController presentationController) {
        LOGGER.info("Constructing Main View");
        this.presentationController = presentationController;

        initInstances();
        initComponents();
        LOGGER.info("Main View constructed");
    }

    /**
     * Shows the view
     */
    public void show() {
        LOGGER.debug("Showing graphical components");
        viewFrame.pack();
        viewFrame.setVisible(true);
        LOGGER.debug("Graphical components showed");
    }

    /**
     * Enables the view
     */
    public void enable() {
        viewFrame.setEnabled(true);
    }

    /**
     * Disables the view
     */
    public void disable() {
        viewFrame.setEnabled(false);
    }

    /**
     * Loads the history table with a given {@link ArrayList<String>} of file data
     *
     * @param arrayOfFileData the {@link ArrayList<String>} of file data
     * @param exception       a {@link CompressorException} if any occurs. Otherwise is null
     */
    public void loadHistoryTable(ArrayList<String> arrayOfFileData, CompressorException exception) {
        if (Objects.isNull(exception)) {
            addFilesToTable(arrayOfFileData);
        } else {
            showException(exception);
        }
    }

    /**
     * Loads the stats table with a given {@link ArrayList<String>} of stats data
     *
     * @param arrayOfStats the {@link ArrayList<String>} of stats data
     * @param exception    a {@link CompressorException} if any error occurs. Otherwise is null
     */
    public void loadStatsTable(ArrayList<String> arrayOfStats, CompressorException exception) {
        if (Objects.isNull(exception)) {
            addStatsToTable(arrayOfStats);
        } else {
            showException(exception);
        }
    }

    private void initInstances() {
        LOGGER.debug("Initiating instances");
        viewFrame = new JFrame("Main View");
        viewPanel = new JPanel(new CardLayout());
        tabbedPane = new JTabbedPane();
        filesCardPanel = new JPanel();
        statsCardPanel = new JPanel();

        initHistoryInstances();
        initDataFileInstances();
        initStatsFilesInstances();
        initStatsAlgorithmsInstances();
        LOGGER.debug("Instances initiated");
    }

    private void initHistoryInstances() {
        historyPanel = new JPanel();
        historyControlComponentsPanel = new JPanel();
        addFileButton = new JButton("+ Add File");
        removeFileButton = new JButton("- Remove File");
        removeFileButton.setEnabled(false);
        searchTextField = new JTextField(20);
        searchLabel = new JLabel("Filter: ");
        historyTable = new JTable(new DefaultTableModel(new Object[][]{}, COLUMN_NAMES));
        rowSorter = new TableRowSorter<>(historyTable.getModel());
        scrollPane = new JScrollPane(historyTable);
    }

    private void initDataFileInstances() {
        dataFilePanel = new JPanel();
        fileInfoPanel = new JPanel();
        filenameLabel = new JLabel("Filename: -");
        dateLabel = new JLabel("Date: -");
        extensionLabel = new JLabel("Format: -");
        pathnameLabel = new JLabel("Pathname: -");
        controlComponentsPanel = new JPanel();
        compressButton = new JButton("Compress");
        compressButton.setEnabled(false);
        uncompressButton = new JButton("Uncompress");
        uncompressButton.setEnabled(false);
        compressAndUncompressButton = new JButton("Compress and uncompress");
        compressAndUncompressButton.setEnabled(false);
        algorithmComboBox = new JComboBox<>();
        algorithmComboBox.setEnabled(false);
        algorithmComboBox.addItem("LZ78");
        algorithmComboBox.addItem("LZW");
        algorithmComboBox.addItem("JPEG");
    }

    private void initStatsFilesInstances() {
        statsFilesPanel = new JPanel();
        statsFilesTable = new JTable(new DefaultTableModel(new Object[][]{}, STATS_FILES_COLUMN_NAMES));
        statsFilesScrollPane = new JScrollPane(statsFilesTable);
    }

    private void initStatsAlgorithmsInstances() {
        statsAlgorithmsPanel = new JPanel();
        statsAlgorithmsTable = new JTable(new DefaultTableModel(new Object[][]{}, STATS_ALGORITHMS_COLUMN_NAMES));
        statsAlgorithmsScrollPane = new JScrollPane(statsAlgorithmsTable);
    }

    private void initComponents() {
        LOGGER.debug("Initiating components");
        initViewFrame();
        initViewPanel();
        initFilesCardPanel();
        initStatsCardPanel();
        initHistoryControlComponentsPanel();
        initHistoryPanel();
        initDataFilePanel();
        initControlComponentsPanel();
        initStatsFilesPanel();
        initStatsAlgorithmsPanel();

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
        tabbedPane.addTab("Files", filesCardPanel);
        tabbedPane.addTab("Stats", statsCardPanel);
        viewPanel.add(tabbedPane);
        LOGGER.debug("View Panel initiated");
    }

    private void initFilesCardPanel() {
        LOGGER.debug("Initiating File Card Panel");
        filesCardPanel.setLayout(new BoxLayout(filesCardPanel, BoxLayout.Y_AXIS));
        filesCardPanel.add(historyControlComponentsPanel);
        filesCardPanel.add(historyPanel);
        filesCardPanel.add(dataFilePanel);
        filesCardPanel.add(controlComponentsPanel);
        LOGGER.debug("File Card Panel initiated");
    }

    private void initStatsCardPanel() {
        LOGGER.debug("Initiating Stats Card Panel");
        statsCardPanel.setLayout(new BoxLayout(statsCardPanel, BoxLayout.Y_AXIS));
        statsCardPanel.add(statsFilesPanel);
        statsCardPanel.add(statsAlgorithmsPanel);
        LOGGER.debug("Stats Card Panel initiated");
    }

    private void initHistoryControlComponentsPanel() {
        historyControlComponentsPanel.add(addFileButton);
        historyControlComponentsPanel.add(removeFileButton);
        historyControlComponentsPanel.add(searchLabel);
        historyControlComponentsPanel.add(searchTextField);
    }

    private void initHistoryPanel() {
        LOGGER.debug("Initiating History Panel");
        historyPanel.add(scrollPane);
        historyTable.setDefaultEditor(Object.class, null);
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.setRowSorter(rowSorter);
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
        LOGGER.debug("Data File Panel initiated");
    }

    private void initStatsFilesPanel() {
        LOGGER.debug("Initiating Stats Files Panel");
        statsFilesPanel.add(statsFilesScrollPane);
        statsFilesTable.setDefaultEditor(Object.class, null);
        statsFilesTable.getTableHeader().setReorderingAllowed(false);
        LOGGER.debug("Stats Files initiated");
    }

    private void initStatsAlgorithmsPanel() {
        LOGGER.debug("Initiating Stats Algorithms Panel");
        statsAlgorithmsPanel.add(statsAlgorithmsScrollPane);
        statsAlgorithmsTable.setDefaultEditor(Object.class, null);
        statsAlgorithmsTable.getTableHeader().setReorderingAllowed(false);
        initStatsAlgorithmsTable();
        LOGGER.debug("Stats Algorithms initiated");
    }

    private void initStatsAlgorithmsTable() {
        DefaultTableModel tableModel = (DefaultTableModel) statsAlgorithmsTable.getModel();
        tableModel.addRow(new Object[]{"JPEG", "Encode", 0, 0, 0, 0, 0});
        tableModel.addRow(new Object[]{"JPEG", "Decode", 0, 0, 0, 0, 0});
        tableModel.addRow(new Object[]{"LZ78", "Encode", 0, 0, 0, 0, 0});
        tableModel.addRow(new Object[]{"LZ78", "Decode", 0, 0, 0, 0, 0});
        tableModel.addRow(new Object[]{"LZW", "Encode", 0, 0, 0, 0, 0});
        tableModel.addRow(new Object[]{"LZW", "Decode", 0, 0, 0, 0, 0});
    }

    private void initFileInfoPanel() {
        fileInfoPanel.setLayout(new BoxLayout(fileInfoPanel, BoxLayout.Y_AXIS));
        fileInfoPanel.add(filenameLabel);
        fileInfoPanel.add(dateLabel);
        fileInfoPanel.add(extensionLabel);
        fileInfoPanel.add(pathnameLabel);
    }

    private void initControlComponentsPanel() {
        controlComponentsPanel.add(algorithmComboBox);
        controlComponentsPanel.add(compressButton);
        controlComponentsPanel.add(uncompressButton);
        controlComponentsPanel.add(compressAndUncompressButton);
    }

    private void addListeners() {
        LOGGER.debug("Adding listeners");
        addHistoryTableListeners();
        addAddFileButtonListeners();
        addRemoveFileButtonListeners();
        addSearchTextFieldListeners();
        addCompressButtonListeners();
        addUncompressButtonListeners();
        addCompressAndUncompressButtonListeners();
        LOGGER.debug("Listeners added");
    }

    private void addHistoryTableListeners() {
        historyTable.getSelectionModel().addListSelectionListener(event -> {
            compressButton.setEnabled(true);
            uncompressButton.setEnabled(true);
            compressAndUncompressButton.setEnabled(true);
            algorithmComboBox.setEnabled(true);
            removeFileButton.setEnabled(true);
            updateFileInfo();
        });
    }

    private void updateFileInfo() {
        try {
            filenameLabel.setText(String.format("Filename: %s", historyTable.getValueAt(historyTable.getSelectedRow(), 0)));
            dateLabel.setText(String.format("Date: %s", historyTable.getValueAt(historyTable.getSelectedRow(), 1)));
            extensionLabel.setText(String.format("Extension: %s", historyTable.getValueAt(historyTable.getSelectedRow(), 3)));
            pathnameLabel.setText(String.format("Pathname: %s", historyTable.getValueAt(historyTable.getSelectedRow(), 4)));
        } catch (IndexOutOfBoundsException e) {
            filenameLabel.setText("Filename: -");
            dateLabel.setText("Date: -");
            extensionLabel.setText("Extension: -");
            pathnameLabel.setText("Pathname: -");
        }
    }

    private void addAddFileButtonListeners() {
        addFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int selection = fileChooser.showOpenDialog(historyPanel);

            if (selection == JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();
                System.out.println("Selected file with pathname: " + file.getPath());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                addNewFile(file.getPath(), formatter.format(new Date()));
            } else if (selection == JFileChooser.ERROR_OPTION) {
                String message = "Failure to choose a file";
                LOGGER.error(message);
                String errorMessage = String.format("Error code: %s.\nMessage: %s", "4009", CompressorErrorCode.CHOOSE_FILE_FAILURE.getCode(), message);
                JOptionPane.showMessageDialog(viewFrame, errorMessage);
            }
        });
    }

    private void addNewFile(String pathname, String date) {
        try {
            presentationController.addFile(pathname, date);
            String filename = presentationController.getFilenameFromPath(pathname);
            String fileSize = presentationController.getFileSizeFromPath(pathname);
            addRowToTable(filename, pathname, fileSize, date);
        } catch (CompressorException ex) {
            showException(ex);
        }
    }

    private void addRemoveFileButtonListeners() {
        removeFileButton.addActionListener(e -> {
            int modelRow = historyTable.convertRowIndexToModel(historyTable.getSelectedRow());
            String pathname = historyTable.getValueAt(historyTable.getSelectedRow(), 4).toString();
            ((DefaultTableModel) historyTable.getModel()).removeRow(modelRow);
            ArrayList<Integer> lineToRemove = new ArrayList<>();
            lineToRemove.add(modelRow);
            try {
                presentationController.rewriteHistoryFile(lineToRemove, pathname);
            } catch (CompressorException ex) {
                showException(ex);
            }
            removeFileButton.setEnabled(false);
            compressButton.setEnabled(false);
            uncompressButton.setEnabled(false);
            compressAndUncompressButton.setEnabled(false);
            algorithmComboBox.setEnabled(false);
        });
    }

    private void addSearchTextFieldListeners() {
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterRow();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterRow();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Empty on purpose
            }
        });
    }

    private void filterRow() {
        String text = searchTextField.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private void showException(CompressorException ex) {
        String errorMessage = String.format("Error code: %s.\nMessage: %s", ex.getErrorCode().getCode(), ex.getMessage());
        JOptionPane.showMessageDialog(viewFrame, errorMessage, "Message error", JOptionPane.ERROR_MESSAGE);
    }

    private void addCompressButtonListeners() {
        compressButton.addActionListener(e -> {
            String algorithm = algorithmComboBox.getSelectedItem().toString();
            String pathname = historyTable.getValueAt(historyTable.getSelectedRow(), 4).toString();
            String filename = historyTable.getValueAt(historyTable.getSelectedRow(), 0).toString();
            String extension = historyTable.getValueAt(historyTable.getSelectedRow(), 3).toString();
            String size = historyTable.getValueAt(historyTable.getSelectedRow(), 2).toString();
            try {
                String[] response = presentationController.compressFile(algorithm, pathname, filename, extension);
                String compressedFilename = presentationController.getFilenameFromPath(response[0]);
                String fileSize = presentationController.getFileSizeFromPath(response[0]);
                addRowToTable(compressedFilename, response[0], fileSize, null);
                addRowToStatsTable(filename, algorithm, response, true);
            } catch (CompressorException ex) {
                showException(ex);
            }
        });
    }

    private void addUncompressButtonListeners() {
        uncompressButton.addActionListener(e -> {
            String algorithm = algorithmComboBox.getSelectedItem().toString();
            String pathname = historyTable.getValueAt(historyTable.getSelectedRow(), 4).toString();
            String filename = historyTable.getValueAt(historyTable.getSelectedRow(), 0).toString();
            String extension = historyTable.getValueAt(historyTable.getSelectedRow(), 3).toString();
            String size = historyTable.getValueAt(historyTable.getSelectedRow(), 2).toString();
            try {
                String[] response = presentationController.uncompressFile(algorithm, pathname, filename, extension);
                String uncompressedFilename = presentationController.getFilenameFromPath(response[0]);
                String fileSize = presentationController.getFileSizeFromPath(response[0]);
                addRowToTable(uncompressedFilename, response[0], fileSize, null);
                addRowToStatsTable(filename, algorithm, response, false);
            } catch (CompressorException ex) {
                showException(ex);
            }
        });
    }

    private void addCompressAndUncompressButtonListeners() {
        compressAndUncompressButton.addActionListener(e -> {
            String algorithm = algorithmComboBox.getSelectedItem().toString();
            String pathname = historyTable.getValueAt(historyTable.getSelectedRow(), 4).toString();
            String filename = historyTable.getValueAt(historyTable.getSelectedRow(), 0).toString();
            String extension = historyTable.getValueAt(historyTable.getSelectedRow(), 3).toString();
            String size = historyTable.getValueAt(historyTable.getSelectedRow(), 2).toString();
            try {
                String[] response = presentationController.compressFile(algorithm, pathname, filename, extension);
                String compressedFilename = presentationController.getFilenameFromPath(response[0]);
                String fileSize = presentationController.getFileSizeFromPath(response[0]);
                addRowToTable(compressedFilename, response[0], fileSize, null);
                addRowToStatsTable(filename, algorithm, response, true);
            } catch (CompressorException ex) {
                showException(ex);
            }

            pathname = historyTable.getValueAt(historyTable.getSelectedRow() + 1, 4).toString();
            filename = historyTable.getValueAt(historyTable.getSelectedRow() + 1, 0).toString();
            extension = historyTable.getValueAt(historyTable.getSelectedRow() + 1, 3).toString();
            size = historyTable.getValueAt(historyTable.getSelectedRow() + 1, 2).toString();
            try {
                String[] response = presentationController.uncompressFile(algorithm, pathname, filename, extension);
                String uncompressedFilename = presentationController.getFilenameFromPath(response[0]);
                String fileSize = presentationController.getFileSizeFromPath(response[0]);
                addRowToTable(uncompressedFilename, response[0], fileSize, null);
                addRowToStatsTable(filename, algorithm, response, false);
            } catch (CompressorException ex) {
                showException(ex);
            }

            String originalPath = historyTable.getValueAt(historyTable.getSelectedRow(), 4).toString();
            LOGGER.debug(String.format("Getting the content form: %s", originalPath));
            String originalContent = presentationController.getContentFromPath(originalPath);
            String decompressionPath = historyTable.getValueAt(historyTable.getSelectedRow() + 2, 4).toString();
            LOGGER.debug(String.format("Getting the content form: %s", decompressionPath));
            String decompressionResult = presentationController.getContentFromPath(decompressionPath);
            presentationController.changeMainViewToComparisonView(originalContent, decompressionResult);
        });
    }

    private void addRowToStatsTable(String filename, String algorithm, String[] stats, boolean encode) {
        String type;
        if (encode) {
            type = "Encode";
        } else {
            type = "Decode";
        }
        DefaultTableModel tableModel = (DefaultTableModel) statsFilesTable.getModel();
        tableModel.addRow(new Object[]{filename, algorithm, type, stats[1], stats[2], stats[3], stats[4]});

        int row = selectStatsAlgorithmRow(algorithm, type);
        updateStatsAlgorithm(row, stats[1], stats[2], stats[3], stats[4]);
    }

    private void updateStatsAlgorithm(int row, String ratio, String time, String speed, String space) {
        DefaultTableModel tableModel = (DefaultTableModel) statsAlgorithmsTable.getModel();
        int numFiles = (Integer) tableModel.getValueAt(row, 6);
        tableModel.setValueAt(numFiles + 1, row, 6);

        if (numFiles != 0) {
            String newRatio = String.valueOf((Float.parseFloat(ratio) + (Float.parseFloat((String) tableModel.getValueAt(row, 2)) * (float) numFiles)) / (float) (numFiles + 1));
            tableModel.setValueAt(newRatio, row, 2);

            String newTime = String.valueOf((Float.parseFloat(time) + (Float.parseFloat((String) tableModel.getValueAt(row, 3)) * (float) numFiles)) / (float) (numFiles + 1));
            tableModel.setValueAt(newTime, row, 3);

            String newSpeed = String.valueOf((Float.parseFloat(speed) + (Float.parseFloat((String) tableModel.getValueAt(row, 4)) * (float) numFiles)) / (float) (numFiles + 1));
            tableModel.setValueAt(newSpeed, row, 4);

            String newSpace = String.valueOf((Float.parseFloat(space) + (Float.parseFloat((String) tableModel.getValueAt(row, 5)) * (float) numFiles)) / (float) (numFiles + 1));
            tableModel.setValueAt(newSpace, row, 5);
        } else {
            tableModel.setValueAt(ratio, row, 2);
            tableModel.setValueAt(time, row, 3);
            tableModel.setValueAt(speed, row, 4);
            tableModel.setValueAt(space, row, 5);
        }
    }

    private int selectStatsAlgorithmRow(String algorithm, String type) {
        if (algorithm.equals("JPEG")) {
            if (type.equals("Encode")) {
                return 0;
            }
            return 1;
        } else if (algorithm.equals("LZ78")) {
            if (type.equals("Encode")) {
                return 2;
            }
            return 3;
        } else {
            if (type.equals("Encode")) {
                return 4;
            }
            return 5;
        }
    }

    private void addRowToTable(String filename, String pathname, String fileSize, String fileDate) {
        String[] fileParts = filename.split("\\.");
        DefaultTableModel tableModel = (DefaultTableModel) historyTable.getModel();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = parseDate(fileDate, formatter);
        tableModel.addRow(new Object[]{fileParts[0], formatter.format(date), fileSize, fileParts[fileParts.length - 1], pathname});
    }

    private Date parseDate(String fileDate, SimpleDateFormat formatter) {
        Date date = new Date();
        try {
            if (Objects.nonNull(fileDate)) {
                return formatter.parse(fileDate);
            }
        } catch (ParseException e) {
            String message = "Failure to parse the file date";
            LOGGER.error(message, e);
            showException(new CompressorException(message, e, CompressorErrorCode.PARSE_DATA_FAILURE));
        }
        return date;
    }

    private void addFilesToTable(ArrayList<String> arrayOfFileData) {
        for (int i = 0; i < arrayOfFileData.size(); ++i) {
            String[] data = arrayOfFileData.get(i).split(" ");
            String date = String.format("%s %s", data[0], data[1]);
            String pathname = data[2];
            String filename = presentationController.getFilenameFromPath(pathname);
            String fileSize = presentationController.getFileSizeFromPath(pathname);
            addRowToTable(filename, pathname, fileSize, date);
        }
    }

    private void addStatsToTable(ArrayList<String> arrayOfStats) {
        for (int i = 0; i < arrayOfStats.size(); ++i) {
            String[] data = arrayOfStats.get(i).split(" ");
            String[] stats = new String[]{"", data[3], data[4], data[5], data[6]};
            addRowToStatsTable(data[0], data[1], stats, data[2].equals("Encode"));
        }
    }
}