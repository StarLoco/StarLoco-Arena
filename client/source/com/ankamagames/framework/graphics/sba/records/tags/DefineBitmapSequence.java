/*     */ package com.ankamagames.framework.graphics.sba.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.graphics.sba.records.BitmapFrame;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ public class DefineBitmapSequence
/*     */   extends DefineSequence
/*     */ {
/*     */   private ArrayList<BitmapFrame> m_bitmapFrames;
/*     */   private float m_invertScalingValue;
/*     */   
/*     */   public DefineBitmapSequence(int identifier, float invertScaleFactor) {
/*  34 */     this.m_code = 3;
/*  35 */     this.m_identifier = identifier;
/*  36 */     this.m_invertScalingValue = invertScaleFactor;
/*  37 */     setLoopCount((short)0);
/*  38 */     this.m_bitmapFrames = new ArrayList<BitmapFrame>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefineBitmapSequence(int identifier) {
/*  45 */     this.m_code = 3;
/*  46 */     this.m_identifier = identifier;
/*  47 */     setLoopCount((short)0);
/*  48 */     this.m_bitmapFrames = new ArrayList<BitmapFrame>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   DefineBitmapSequence() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInvertScalingValue() {
/*  58 */     return this.m_invertScalingValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvertScalingValue(float invertScaleFactor) {
/*  65 */     this.m_invertScalingValue = invertScaleFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/*  72 */     return this.m_bitmapFrames.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<BitmapFrame> getBitmapFrames() {
/*  79 */     return this.m_bitmapFrames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBitmapFrame(BitmapFrame bitmapFrame) {
/*  89 */     this.m_bitmapFrames.add(bitmapFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBitmapFrames(ArrayList<BitmapFrame> bitmapFrames) {
/*  99 */     this.m_bitmapFrames.addAll(bitmapFrames);
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
/*     */   
/*     */   public boolean removeBitmapFrame(BitmapFrame bitmapFrame) {
/* 112 */     return this.m_bitmapFrames.remove(bitmapFrame);
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
/*     */   public BitmapFrame removeBitmapFrame(int index) {
/* 124 */     return this.m_bitmapFrames.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/* 134 */     InputBitStream inStream = readDefinitionSequenceTagHeader(data);
/* 135 */     this.m_invertScalingValue = inStream.readFloat16();
/* 136 */     int numFrames = inStream.readUI16();
/* 137 */     this.m_bitmapFrames = new ArrayList<BitmapFrame>();
/* 138 */     for (int i = 0; i < numFrames; i++) {
/* 139 */       this.m_bitmapFrames.add(new BitmapFrame(inStream, version));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 150 */     super.writeData(outStream);
/* 151 */     outStream.writeFloat16(this.m_invertScalingValue);
/* 152 */     int numFrames = this.m_bitmapFrames.size();
/* 153 */     outStream.writeUI16(this.m_bitmapFrames.size());
/* 154 */     for (int i = 0; i < numFrames; i++) {
/* 155 */       BitmapFrame frame = this.m_bitmapFrames.get(i);
/* 156 */       frame.write(outStream);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\DefineBitmapSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */