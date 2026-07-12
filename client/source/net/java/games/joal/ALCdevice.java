/*    */ package net.java.games.joal;
/*    */ 
/*    */ import com.sun.gluegen.runtime.BufferFactory;
/*    */ import com.sun.gluegen.runtime.CPU;
/*    */ import com.sun.gluegen.runtime.StructAccessor;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ALCdevice
/*    */ {
/*    */   StructAccessor accessor;
/*    */   
/*    */   public static int size() {
/* 20 */     if (CPU.is32Bit()) {
/* 21 */       return ALCdevice32.size();
/*    */     }
/* 23 */     return ALCdevice64.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ALCdevice create() {
/* 28 */     return create(BufferFactory.newDirectByteBuffer(size()));
/*    */   }
/*    */   
/*    */   public static ALCdevice create(ByteBuffer paramByteBuffer) {
/* 32 */     if (CPU.is32Bit()) {
/* 33 */       return new ALCdevice32(paramByteBuffer);
/*    */     }
/* 35 */     return new ALCdevice64(paramByteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   ALCdevice(ByteBuffer paramByteBuffer) {
/* 40 */     this.accessor = new StructAccessor(paramByteBuffer);
/*    */   }
/*    */   
/*    */   public ByteBuffer getBuffer() {
/* 44 */     return this.accessor.getBuffer();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\ALCdevice.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */