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
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainViewSwing {
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
        compressedFileInfoPanel = new JPanel();
        compressedLabel = new JLabel("Compressed");
        originalSizeLabel = new JLabel("Original Size: (*)");
        uncompressedFileInfoPanel = new JPanel();
        uncompressedLabel = new JLabel("Uncompressed");
        newSizeLabel = new JLabel("New Size: (*)");
        controlComponentsPanel = new JPanel();
        compressButton = new JButton("Compress");
        compressButton.setEnabled(false);
        uncompressButton = new JButton("Uncompress");
        uncompressButton.setEnabled(false);
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
        initHistoryPanel();
        initDataFilePanel();
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
        filesCardPanel.add(historyPanel);
        filesCardPanel.add(dataFilePanel);
        LOGGER.debug("File Card Panel initiated");
    }

    private void initStatsCardPanel() {
        LOGGER.debug("Initiating Stats Card Panel");
        statsCardPanel.setLayout(new BoxLayout(statsCardPanel, BoxLayout.Y_AXIS));
        statsCardPanel.add(statsFilesPanel);
        statsCardPanel.add(statsAlgorithmsPanel);
        LOGGER.debug("Stats Card Panel initiated");
    }

    private void initHistoryPanel() {
        LOGGER.debug("Initiating History Panel");
        historyPanel.add(addFileButton);
        historyPanel.add(removeFileButton);
        historyPanel.add(searchLabel);
        historyPanel.add(searchTextField);
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
        addRemoveFileButtonListeners();
        addSearchTextFieldListeners();
        addCompressButtonListeners();
        addUncompressButtonListeners();
        LOGGER.debug("Listeners added");
    }

    private void addHistoryTableListeners() {
        historyTable.getSelectionModel().addListSelectionListener(event -> {
            compressButton.setEnabled(true);
            uncompressButton.setEnabled(true);
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
            originalSizeLabel.setText(String.format("Size: %s", historyTable.getValueAt(historyTable.getSelectedRow(), 2)));
        } catch (IndexOutOfBoundsException e) {
            filenameLabel.setText("Filename: -");
            dateLabel.setText("Date: -");
            extensionLabel.setText("Extension: -");
            pathnameLabel.setText("Pathname: -");
            originalSizeLabel.setText("Size: (*)");
        }
    }

    private void addAddFileButtonListeners() {
        addFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int selection = fileChooser.showOpenDialog(historyPanel);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Selected file with pathname: " + file.getAbsolutePath());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                addNewFile(file, formatter.format(new Date()));
            } else if (selection == JFileChooser.ERROR_OPTION) {
                String message = "Failure to choose a file";
                LOGGER.error(message);
                String errorMessage = String.format("Error code: %s.\nMessage: %s", "4009", CompressorErrorCode.CHOOSE_FILE_FAILURE.getCode(), message);
                JOptionPane.showMessageDialog(viewFrame, errorMessage);
            }
        });
    }

    private void addNewFile(File file, String date) {
        try {
            presentationController.addFile(file.getAbsolutePath(), date);
            addRowToTableFromFile(file, date);
        } catch (CompressorException ex) {
            showException(ex);
        }
    }

    private void addRemoveFileButtonListeners() {
        removeFileButton.addActionListener(e -> {
            int modelRow = historyTable.convertRowIndexToModel(historyTable.getSelectedRow());
            ((DefaultTableModel) historyTable.getModel()).removeRow(modelRow);
            ArrayList<Integer> lineToRemove = new ArrayList<>();
            lineToRemove.add(modelRow);
            try {
                presentationController.rewriteHistoryFile(lineToRemove);
            } catch (CompressorException ex) {
                showException(ex);
            }
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
                originalSizeLabel.setText(size);
                File compressedFile = new File(response[0]);
                newSizeLabel.setText(getSizeFromFile(compressedFile));
                addRowToTableFromFile(compressedFile, null);
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
                newSizeLabel.setText(size);
                File uncompressedPath = new File(response[0]);
                originalSizeLabel.setText(getSizeFromFile(uncompressedPath));
                addRowToTableFromFile(uncompressedPath, null);
                addRowToStatsTable(filename, algorithm, response, false);
            } catch (CompressorException ex) {
                showException(ex);
            }
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

    private void addRowToTableFromFile(File file, String fileDate) {
        String[] fileParts = file.getName().split("\\.");
        DefaultTableModel tableModel = (DefaultTableModel) historyTable.getModel();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = parseDate(fileDate, formatter);
        tableModel.addRow(new Object[]{fileParts[0], formatter.format(date), getSizeFromFile(file), fileParts[fileParts.length - 1], file.getAbsolutePath()});
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

    private String getSizeFromFile(File file) {
        double bytesSize = (double) (file.length() / (1024 * 1024));
        double roundedSize = (Math.round(bytesSize * 100.0) / 100.0);
        if (roundedSize <= 0.1) {
            return Double.toString((double) file.length() / 1000) + " B";
        } else if (roundedSize < 0.5 && roundedSize > 0.1) {
            return Double.toString((Math.round(bytesSize * 100.0) / 100.0)) + " KB";
        } else {
            bytesSize = (double) file.length() / (1024000);
            return Double.toString((Math.round(bytesSize * 100.0) / 100.0)) + " MB";
        }
    }

    public void loadHistoryTable(ArrayList<String> arrayOfFileData, CompressorException exception) {
        if (Objects.isNull(exception)) {
            addFilesToTable(arrayOfFileData);
        } else {
            showException(exception);
        }
    }

    private void addFilesToTable(ArrayList<String> arrayOfFileData) {
        for (int i = 0; i < arrayOfFileData.size(); ++i) {
            String[] data = arrayOfFileData.get(i).split(" ");
            String date = String.format("%s %s", data[0], data[1]);
            String pathname = data[2];
            File file = new File(pathname);
            addRowToTableFromFile(file, date);
        }
    }
}