import presentation.PresentationController;

import javax.swing.*;

public class MainSwing {
    public static void main(String[] params){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PresentationController presentationController = new PresentationController();
                presentationController.init();
            }
        });
    }
}
