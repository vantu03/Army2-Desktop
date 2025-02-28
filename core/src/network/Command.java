package network;

import CLib.Graphics;
import model.IAction;

public class Command {
    public String caption;
    public IAction action;

    public Command(String caption, IAction action) {
        this.caption = caption;
        this.action = action;
    }

    public void paint(Graphics g) {
    }
}
