/*     */ package com.ankamagames.framework.graphics.sba.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.graphics.sba.records.ColorTransform;
/*     */ import com.ankamagames.framework.graphics.sba.records.Matrix;
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
/*     */ public class PlaceObject
/*     */   extends Tag
/*     */ {
/*     */   public static final int INVALID_ID = 0;
/*     */   private int m_identifier;
/*     */   private int m_depth;
/*     */   private Matrix m_matrix;
/*     */   private ColorTransform m_colorTransform;
/*     */   
/*     */   public PlaceObject(int identifier, int depth, Matrix matrix) {
/*  42 */     this.m_code = 5;
/*  43 */     this.m_identifier = identifier;
/*  44 */     this.m_depth = depth;
/*  45 */     this.m_matrix = matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaceObject() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  55 */     return this.m_depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepth(int depth) {
/*  62 */     this.m_depth = depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdentifier() {
/*  69 */     return this.m_identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdentifier(int identifier) {
/*  76 */     this.m_identifier = identifier;
/*     */   }
/*     */   
/*     */   public boolean hasCharacterId() {
/*  80 */     return (this.m_identifier != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix getMatrix() {
/*  87 */     return this.m_matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatrix(Matrix matrix) {
/*  94 */     this.m_matrix = matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorTransform getColorTransform() {
/* 101 */     return this.m_colorTransform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorTransform(ColorTransform colorTransform) {
/* 108 */     this.m_colorTransform = colorTransform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/* 118 */     InputBitStream inStream = new InputBitStream(data);
/* 119 */     this.m_identifier = inStream.readUI16();
/* 120 */     this.m_depth = inStream.readUI16();
/* 121 */     if (inStream.readBooleanBit()) {
/* 122 */       this.m_matrix = new Matrix(inStream);
/*     */     }
/* 124 */     if (inStream.readBooleanBit()) {
/* 125 */       this.m_colorTransform = new ColorTransform(inStream);
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
/* 136 */     outStream.writeUI16(this.m_identifier);
/* 137 */     outStream.writeUI16(this.m_depth);
/* 138 */     if (this.m_matrix != null) {
/* 139 */       outStream.writeBooleanBit(true);
/* 140 */       this.m_matrix.write(outStream);
/*     */     } else {
/* 142 */       outStream.writeBooleanBit(false);
/*     */     } 
/* 144 */     if (this.m_colorTransform != null) {
/* 145 */       outStream.writeBooleanBit(true);
/* 146 */       this.m_colorTransform.write(outStream);
/*     */     } else {
/* 148 */       outStream.writeBooleanBit(false);
/*     */     } 
/* 150 */     outStream.align();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\PlaceObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */