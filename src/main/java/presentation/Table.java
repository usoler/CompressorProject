package presentation;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Table extends Panel {
    private List<Label> labels;

    public Table(String[] params) {
        removeAll();

        labels = new LinkedList<>();
        for (int i = 0; i < params.length; ++i) {
            labels.add(new Label(params[i]));
            labels.get(i).setEnabled(true);
            labels.get(i).setSize(new Dimension(labels.get(i).getWidth() + 2, labels.get(i).getHeight() + 2));
            add(labels.get(i));
        }
    }
}
