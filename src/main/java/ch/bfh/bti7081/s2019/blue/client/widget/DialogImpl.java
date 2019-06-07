package ch.bfh.bti7081.s2019.blue.client.widget;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import com.vaadin.flow.component.dialog.Dialog;

public class DialogImpl implements IsDialog {

    private final Dialog dialog;

    public DialogImpl(IsView view) {
        this.dialog = new Dialog(view.asComponent());
    }

    @Override
    public void show() {
        dialog.open();
    }

    @Override
    public void close() {
        dialog.close();
    }
}
