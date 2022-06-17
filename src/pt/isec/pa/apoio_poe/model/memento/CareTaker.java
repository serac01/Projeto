package pt.isec.pa.apoio_poe.model.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker {
    private final IOriginator originator;
    private final Deque<IMemento> history;
    private final Deque<IMemento> redoHist;

    public CareTaker(IOriginator originator) {
        this.originator = originator;
        history = new ArrayDeque<>();
        redoHist= new ArrayDeque<>();
    }
    public void save() {
        redoHist.clear();
        history.push(originator.save());
    }
    public void undo() {
        if (history.isEmpty())
            return;
        redoHist.push(originator.save());
        originator.restore(history.pop());
    }
    public void redo() {
        if (redoHist.isEmpty())
            return;
        history.push(originator.save());
        originator.restore(redoHist.pop());
    }
    public boolean hasUndo() {
        return !history.isEmpty();
    }
    public boolean hasRedo() {
        return !redoHist.isEmpty();
    }
}
