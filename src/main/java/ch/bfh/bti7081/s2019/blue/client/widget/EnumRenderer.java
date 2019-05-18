package ch.bfh.bti7081.s2019.blue.client.widget;

import com.vaadin.flow.data.renderer.TextRenderer;

import java.util.function.Function;

public class EnumRenderer<E extends Enum<E>> {

    private final TextRenderer<E> renderer;
    private final Class<E> enumClass;
    private final Function<String, String> getTranslation;

    public EnumRenderer(Class<E> enumClass, Function<String, String> getTranslation) {
        renderer = new TextRenderer<>(this::onRender);
        this.enumClass = enumClass;
        this.getTranslation = getTranslation;
    }

    private String onRender(E item) {
        String key = enumClass.getSimpleName() + "_" + item.name();
        return getTranslation.apply(key);
    }

    public TextRenderer<E> asRenderer() {
        return renderer;
    }
}
