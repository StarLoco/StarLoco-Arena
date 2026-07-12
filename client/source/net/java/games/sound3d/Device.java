/*    */ package net.java.games.sound3d;
/*    */ 
/*    */ import net.java.games.joal.ALC;
/*    */ import net.java.games.joal.ALCdevice;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Device
/*    */ {
/*    */   private final ALC alc;
/*    */   final ALCdevice realDevice;
/*    */   
/*    */   Device(ALC paramALC, ALCdevice paramALCdevice) {
/* 49 */     this.alc = paramALC;
/* 50 */     this.realDevice = paramALCdevice;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 57 */     this.alc.alcCloseDevice(this.realDevice);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\sound3d\Device.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */