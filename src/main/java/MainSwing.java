import presentation.PresentationController;

import javax.swing.*;

public class MainSwing {
    public static void main(String[] params) {
        SwingUtilities.invokeLater(() -> {
            PresentationController presentationController = new PresentationController();
            presentationController.init();
        });
    }
}
