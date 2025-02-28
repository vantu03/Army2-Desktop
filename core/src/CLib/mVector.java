package CLib;

import java.util.Vector;

public class mVector {
   private Vector a;
   private String name;

   public mVector() {
      this.a = new Vector();
      this.name = "No name";
   }

   public mVector(String aa) {
      this.a = new Vector();
      this.name = aa;
   }

   public mVector(Vector a) {
      this.a = a;
      this.name = "No Name";
   }

   public void addElement(Object o) {
      this.a.addElement(o);
   }

   public boolean contains(Object o) {
      return this.a.contains(o);
   }

   public int size() {
      return this.a == null ? 0 : this.a.size();
   }

   public Object elementAt(int index) {
      return index > -1 && index < this.a.size() ? this.a.elementAt(index) : null;
   }

   public void setElementAt(Object obj, int index) {
      if (index > -1 && index < this.a.size()) {
         this.a.setElementAt(obj, index);
      }

   }

   public int indexOf(Object o) {
      return this.a.indexOf(o);
   }

   public void removeElementAt(int index) {
      if (index > -1 && index < this.a.size()) {
         this.a.removeElementAt(index);
      }

   }

   public void removeElement(Object o) {
      this.a.removeElement(o);
   }

   public void removeAllElements() {
      this.a.removeAllElements();
   }

   public void insertElementAt(Object o, int i) {
      this.a.insertElementAt(o, i);
   }

   public Object firstElement() {
      return this.a.firstElement();
   }

   public Object lastElement() {
      return this.a.lastElement();
   }

   public void add(Object c) {
      this.a.add(c);
   }
}
