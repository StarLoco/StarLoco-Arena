/*    */ package net.java.games.joal.util;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ public final class WAVData
/*    */ {
/*    */   public final ByteBuffer data;
/*    */   public final int format;
/*    */   public final int size;
/*    */   public final int freq;
/*    */   public final boolean loop;
/*    */   
/*    */   WAVData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 69 */     this.data = paramByteBuffer;
/* 70 */     this.format = paramInt1;
/* 71 */     this.size = paramInt2;
/* 72 */     this.freq = paramInt3;
/* 73 */     this.loop = paramBoolean;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joa\\util\WAVData.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */