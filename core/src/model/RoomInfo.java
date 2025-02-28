package model;

public class RoomInfo {
   public byte id;
   public byte roomFree;
   public byte roomWait;
   public byte boardID;
   public String[] roomName;
   public byte lv;
   public byte mapID;
   public int stat;
   public String name = "";
   public String playerMax = "";
   public int money;

   public RoomInfo(byte id, byte roomFree, byte roomWait, byte lv) {
      this.id = id;
      this.roomFree = roomFree;
      this.roomWait = roomWait;
      this.lv = lv;
   }

   public void getStat() {
      int total = this.roomFree + this.roomWait;
      if (total == 0) {
         total = 1;
      }

      int a = this.roomFree / total;
      if (a >= 0 && a <= 0) {
         this.stat = 2;
      } else if (a > 0 && a <= 0) {
         this.stat = 1;
      } else if (a > 0 && a <= 1) {
         this.stat = 0;
      }

   }

   public RoomInfo() {
   }
}
