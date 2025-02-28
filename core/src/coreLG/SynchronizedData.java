package coreLG;

import model.IAction;

public class SynchronizedData {
    public byte cmd;
    public IAction action2;

    public void invoke() {
        if (this.action2 != null) {
            this.action2.perform();
        }

    }
}
