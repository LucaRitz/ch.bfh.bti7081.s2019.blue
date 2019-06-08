package ch.bfh.bti7081.s2019.blue.client.app.frontoffice.report;

import java.util.List;

public class Wizard<T> {

    private final List<T> steps;
    private int index = 0;

    public Wizard(List<T> steps) {
        this.steps = steps;
    }

    public T getCurrent() {
        return steps.get(index);
    }

    public List<T> getSteps() {
        return steps;
    }

    public void next() {
        if (!isLast()) {
            index++;
        }
    }

    public void previous() {
        if (!isFirst()) {
            index--;
        }
    }

    public boolean isFirst() {
        return index == 0;
    }

    public boolean isLast() {
        return index == steps.size() - 1;
    }
}
