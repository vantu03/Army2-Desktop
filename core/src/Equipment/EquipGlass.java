package Equipment;

import java.util.Vector;

public class EquipGlass {
   public byte glassID;
   public Vector type;
   public short maxDamage;

   public EquipGlass(byte glassID) {
      this.glassID = glassID;
   }

   public void addType(Vector types) {
      this.type = types;
   }

   public TypeEquip getType(int id) {
      for(int i = 0; i < this.type.size(); ++i) {
         TypeEquip t = (TypeEquip)this.type.elementAt(i);
         if (t != null && t.typeID == id) {
            return t;
         }
      }

      return null;
   }
}
