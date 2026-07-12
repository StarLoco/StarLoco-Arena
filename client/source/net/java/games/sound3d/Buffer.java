/*     */ package net.java.games.sound3d;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import net.java.games.joal.AL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Buffer
/*     */ {
/*     */   public static final int FORMAT_MONO8 = 4352;
/*     */   public static final int FORMAT_MONO16 = 4353;
/*     */   public static final int FORMAT_STEREO8 = 4354;
/*     */   public static final int FORMAT_STEREO16 = 4355;
/*     */   final int bufferID;
/*     */   private ByteBuffer data;
/*     */   private boolean isConfigured = false;
/*     */   private final AL al;
/*     */   
/*     */   Buffer(AL paramAL, int paramInt) {
/*  61 */     this.bufferID = paramInt;
/*  62 */     this.al = paramAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/*  74 */     if (!this.isConfigured) {
/*  75 */       this.al.alBufferData(this.bufferID, paramInt1, paramByteBuffer, paramByteBuffer.capacity(), paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {
/*  83 */     this.data = null;
/*  84 */     this.al.alDeleteBuffers(1, new int[] { this.bufferID }, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitDepth() {
/*  93 */     int[] arrayOfInt = new int[1];
/*  94 */     this.al.alGetBufferi(this.bufferID, 8194, arrayOfInt, 0);
/*     */     
/*  96 */     return arrayOfInt[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumChannels() {
/* 105 */     int[] arrayOfInt = new int[1];
/* 106 */     this.al.alGetBufferi(this.bufferID, 8195, arrayOfInt, 0);
/*     */     
/* 108 */     return arrayOfInt[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getData() {
/* 117 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrequency() {
/* 126 */     int[] arrayOfInt = new int[1];
/* 127 */     this.al.alGetBufferi(this.bufferID, 8193, arrayOfInt, 0);
/*     */     
/* 129 */     return arrayOfInt[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 138 */     int[] arrayOfInt = new int[1];
/* 139 */     this.al.alGetBufferi(this.bufferID, 8196, arrayOfInt, 0);
/*     */     
/* 141 */     return arrayOfInt[0];
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\sound3d\Buffer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */