package map;

public class MapFile {
   public byte[] data;
   public byte mapID;
   public short backGroundID;
   public short yBackGround;
   public short water_class;
   public short yWater;
   public short yCloud;
   public boolean isWaterClass;
   public short[] values = new short[5];

   public MapFile(byte[] data, byte mapID, short[] values) {
      this.data = data;
      this.mapID = mapID;
      this.values = values;
      this.init();
   }

   public void init() {
      this.backGroundID = this.values[0];
      this.yCloud = this.values[2];
      this.yBackGround = this.values[1];
      this.water_class = this.values[4];
      this.yWater = this.values[3];
      if (this.water_class != -1) {
         this.isWaterClass = true;
      } else {
         this.isWaterClass = false;
      }

   }
}
