package CLib;

import model.CRes;

public class mSound {
   public static float volumeSound = 0.0F;
   public static float volumeMusic = 0.0F;
   private static final int MAX_VOLUME = 10;
   public static int[] soundID;
   public static int CurMusic = -1;
   public static boolean isMusic = true;
   public static boolean isSound = true;
   public static SoundSystem[] music;
   public static SoundSystem[] sound;
   public static boolean isEnableSound = true;
   public static int sizeMusic = 1;
   public static int sizeSound = 3;

   public static void init() {
      music = new SoundSystem[sizeMusic];

      int i;
      for(i = 0; i < music.length; ++i) {
         music[i] = new SoundSystem(String.valueOf(i), true);
      }

      sound = new SoundSystem[sizeSound];

      for(i = 0; i < sound.length; ++i) {
         boolean isLOOP = false;
         sound[i] = new SoundSystem(String.valueOf(i), isLOOP);
      }

      System.gc();
   }

   public static int getSoundPoolSource(int index, String fileName) {
      return index;
   }

   public static void playSound(int id, float volume, int index) {
      if (isSound) {
         if (sound != null && sound[id] != null) {
            try {
               sound[id].play(volume);
            } catch (IllegalStateException var4) {
               var4.printStackTrace();
            }
         }

      }
   }

   public static void SetLoopSound(int id, float volume, int loop) {
   }

   public static void UnSetLoopAll() {
   }

   public static void playMus(int id, float volume, boolean isLoop) {
      if (isMusic) {
         if (music != null) {
            for(int i = 0; i < music.length; ++i) {
               if (music[i] != null && music[i].isPlaying() && i != id) {
                  music[i].pause();
               }
            }
         }

         if (id >= 0 && id <= music.length - 1) {
            try {
               music[id].setVolume(volume, volume);
               music[id].setLooping(isLoop);
               music[id].play(volume);
            } catch (IllegalStateException var4) {
               var4.printStackTrace();
            }

         }
      }
   }

   public static void pauseMusic(int id) {
   }

   public static void pauseCurMusic() {
      for(int i = 0; i < music.length; ++i) {
         if (music[i].isPlaying()) {
            music[i].pause();
            CurMusic = i;
         }
      }

   }

   public static void resumeMusic(int id) {
   }

   public static void resumeAll() {
   }

   public static void releaseAll() {
   }

   public static void stopAll() {
   }

   public static void stopSoundAll() {
      if (sound != null) {
         for(int i = 0; i < sound.length; ++i) {
            if (sound[i] != null) {
               sound[i].stop();
            }
         }
      }

   }

   public static void setVolume(int id, int index, int soundVolume) {
   }

   public static void setVolume(int volumeSound) {
      CRes.saveRMSInt("sound", volumeSound);
      mSound.volumeSound = (float)volumeSound / 100.0F;
   }
}
