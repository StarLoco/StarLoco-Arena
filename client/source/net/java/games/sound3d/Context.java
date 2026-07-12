/*    */ package net.java.games.sound3d;
/*    */ 
/*    */ import net.java.games.joal.ALC;
/*    */ import net.java.games.joal.ALCcontext;
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
/*    */ public class Context
/*    */ {
/*    */   private final ALC alc;
/*    */   final ALCcontext realContext;
/*    */   final Device device;
/*    */   
/*    */   Context(ALC paramALC, ALCcontext paramALCcontext, Device paramDevice) {
/* 50 */     this.alc = paramALC;
/* 51 */     this.realContext = paramALCcontext;
/* 52 */     this.device = paramDevice;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void suspend() {
/* 59 */     this.alc.alcSuspendContext(this.realContext);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void destroy() {
/* 66 */     this.alc.alcDestroyContext(this.realContext);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Device getDevice() {
/* 75 */     return this.device;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\sound3d\Context.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */