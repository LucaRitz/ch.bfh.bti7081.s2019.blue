package ch.bfh.bti7081.s2019.blue.client.widget;

import ch.bfh.bti7081.s2019.blue.client.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class DialogFactoryImpl implements DialogFactory {

    @Override
    public IsDialog show(IsView view) {
        IsDialog dialog = new DialogImpl(view);
        dialog.show();
        return dialog;
    }
}
