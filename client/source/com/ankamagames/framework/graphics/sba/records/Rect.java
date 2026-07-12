/*     */ package com.ankamagames.framework.graphics.sba.records;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import java.io.IOException;
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
/*     */ public class Rect
/*     */ {
/*     */   private int m_xMin;
/*     */   private int m_xMax;
/*     */   private int m_yMin;
/*     */   private int m_yMax;
/*     */   
/*     */   public Rect(int xMin, int yMin, int xMax, int yMax) {
/*  41 */     this.m_xMin = xMin;
/*  42 */     this.m_yMin = yMin;
/*  43 */     this.m_xMax = xMax;
/*  44 */     this.m_yMax = yMax;
/*     */   }
/*     */   
/*     */   public Rect(InputBitStream stream) throws IOException {
/*  48 */     this.m_xMin = stream.readSI32();
/*  49 */     this.m_yMin = stream.readSI32();
/*  50 */     this.m_xMax = stream.readSI32();
/*  51 */     this.m_yMax = stream.readSI32();
/*  52 */     stream.align();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMax(int xMax) {
/*  59 */     this.m_xMax = xMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMin(int xMin) {
/*  66 */     this.m_xMin = xMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYMax(int yMax) {
/*  73 */     this.m_yMax = yMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYMin(int yMin) {
/*  80 */     this.m_yMin = yMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXMax() {
/*  89 */     return this.m_xMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXMin() {
/*  98 */     return this.m_xMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYMax() {
/* 107 */     return this.m_yMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYMin() {
/* 116 */     return this.m_yMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 125 */     return this.m_xMax - this.m_xMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 134 */     return this.m_yMax - this.m_yMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(int dx, int dy) {
/* 144 */     this.m_xMin += dx;
/* 145 */     this.m_xMax += dx;
/* 146 */     this.m_yMin += dy;
/* 147 */     this.m_yMax += dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(float ratio) {
/* 156 */     this.m_xMin = (int)(this.m_xMin * ratio);
/* 157 */     this.m_xMax = (int)(this.m_xMax * ratio);
/* 158 */     this.m_yMin = (int)(this.m_yMin * ratio);
/* 159 */     this.m_yMax = (int)(this.m_yMax * ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 166 */     return !(this.m_xMin != this.m_xMax && this.m_yMin != this.m_yMax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 173 */     return "Rect (" + this.m_xMin + ", " + this.m_yMin + ", " + this.m_xMax + ", " + this.m_yMax + ")";
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
/*     */   public void write(OutputBitStream stream) throws IOException {
/* 185 */     stream.writeSI32(this.m_xMin);
/* 186 */     stream.writeSI32(this.m_yMin);
/* 187 */     stream.writeSI32(this.m_xMax);
/* 188 */     stream.writeSI32(this.m_yMax);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\Rect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */