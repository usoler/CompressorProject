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
            add(labels.get(i));
        }
    }
}
