
import domain.exception.CompressorException;

import presentation.PresentationController;

import java.awt.*;

public class Main {

    public static void main(String[] args) throws CompressorException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PresentationController presentationController = new PresentationController();
                presentationController.init();
            }
        });



    }
}
