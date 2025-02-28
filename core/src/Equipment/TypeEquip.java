package Equipment;

import java.util.Vector;

public class TypeEquip {
    public static final byte SUNG = 0;
    public static final byte NON = 1;
    public static final byte GIAP = 2;
    public static final byte KINH = 3;
    public static final byte CANH = 4;
    public byte typeID;
    public Vector equip;

    public TypeEquip(byte id) {
        this.typeID = id;
    }

    public void addEquip(Vector equips) {
        this.equip = equips;
    }

    public Equip getEquip(short id) {
        for (int i = 0; i < this.equip.size(); ++i) {
            Equip eq = (Equip) this.equip.elementAt(i);
            if (eq != null && eq.id == id) {
                return eq;
            }
        }

        return null;
    }
}
