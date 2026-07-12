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
/*    */ public abstract class ALCcontext
/*    */ {
/*    */   StructAccessor accessor;
/*    */   
/*    */   public static int size() {
/* 20 */     if (CPU.is32Bit()) {
/* 21 */       return ALCcontext32.size();
/*    */     }
/* 23 */     return ALCcontext64.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ALCcontext create() {
/* 28 */     return create(BufferFactory.newDirectByteBuffer(size()));
/*    */   }
/*    */   
/*    */   public static ALCcontext create(ByteBuffer paramByteBuffer) {
/* 32 */     if (CPU.is32Bit()) {
/* 33 */       return new ALCcontext32(paramByteBuffer);
/*    */     }
/* 35 */     return new ALCcontext64(paramByteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   ALCcontext(ByteBuffer paramByteBuffer) {
/* 40 */     this.accessor = new StructAccessor(paramByteBuffer);
/*    */   }
/*    */   
/*    */   public ByteBuffer getBuffer() {
/* 44 */     return this.accessor.getBuffer();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\ALCcontext.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */