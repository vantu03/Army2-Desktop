package model;

import CLib.mImage;
import java.util.Vector;

public class MaterialIconMn {
   public static Vector<ImageIcon> icons = new Vector();

   public static void addIcon(ImageIcon img) {
      icons.addElement(img);
   }

   public static boolean isExistIcon(int id) {
      for(int i = 0; i < icons.size(); ++i) {
         if (((ImageIcon)icons.elementAt(i)).id == id) {
            return true;
         }
      }

      return false;
   }

   public static mImage getImageFromID(int id) {
      for(int i = 0; i < icons.size(); ++i) {
         if (((ImageIcon)icons.elementAt(i)).id == id) {
            return ((ImageIcon)icons.elementAt(i)).img;
         }
      }

      return null;
   }
}
