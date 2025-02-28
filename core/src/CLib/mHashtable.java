package CLib;

import java.util.Enumeration;
import java.util.Hashtable;

public class mHashtable {
   public Hashtable htb = new Hashtable();

   public void clear() {
      this.htb.clear();
   }

   public boolean contains(Object obj) {
      return this.htb.contains(obj);
   }

   public boolean containsKey(Object obj) {
      return this.htb.containsKey(obj);
   }

   public Object get(String k) {
      return this.htb.get(k);
   }

   public int size() {
      return this.htb.size();
   }

   public void put(String k, Object v) {
      if (this.htb.containsKey(k)) {
         this.htb.remove(k);
      }

      this.htb.put(k, v);
   }

   public void remove(Object k) {
      this.htb.remove(k);
   }

   public Enumeration keys() {
      return this.htb.keys();
   }
}
