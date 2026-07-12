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
/*     */ public class Matrix
/*     */ {
/*  26 */   private float m_scaleX = 1.0F;
/*     */   
/*  28 */   private float m_scaleY = 1.0F;
/*     */   
/*  30 */   private float m_rotateSkew0 = 0.0F;
/*     */   
/*  32 */   private float m_rotateSkew1 = 0.0F;
/*     */   
/*  34 */   private float m_translateX = 0.0F;
/*     */   
/*  36 */   private float m_translateY = 0.0F;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean m_hasTranslate;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean m_hasScale;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean m_hasRotateSkew;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Matrix(float translateX, float translateY)
/*     */   {
/*  55 */     this.m_translateX = translateX;
/*  56 */     this.m_translateY = translateY;
/*  57 */     this.m_hasTranslate = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Matrix() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Matrix(InputBitStream stream)
/*     */     throws IOException
/*     */   {
/*  73 */     this.m_hasScale = stream.readBooleanBit();
/*  74 */     if (this.m_hasScale) {
/*  75 */       int nScaleBits = (int)stream.readUnsignedBits(5);
/*  76 */       this.m_scaleX = ((float)stream.readFPBits(nScaleBits));
/*  77 */       this.m_scaleY = ((float)stream.readFPBits(nScaleBits));
/*     */     }
/*  79 */     this.m_hasRotateSkew = stream.readBooleanBit();
/*  80 */     if (this.m_hasRotateSkew) {
/*  81 */       int nRotateBits = (int)stream.readUnsignedBits(5);
/*  82 */       this.m_rotateSkew0 = ((float)stream.readFPBits(nRotateBits));
/*  83 */       this.m_rotateSkew1 = ((float)stream.readFPBits(nRotateBits));
/*     */     }
/*  85 */     this.m_hasTranslate = stream.readBooleanBit();
/*  86 */     if (this.m_hasTranslate) {
/*  87 */       int nTranslateBits = (int)stream.readUnsignedBits(5);
/*  88 */       this.m_translateX = ((float)stream.readFPBits(nTranslateBits));
/*  89 */       this.m_translateY = ((float)stream.readFPBits(nTranslateBits));
/*     */     }
/*  91 */     stream.align();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRotateSkew(float rotateSkew0, float rotateSkew1)
/*     */   {
/* 101 */     this.m_rotateSkew0 = rotateSkew0;
/* 102 */     this.m_rotateSkew1 = rotateSkew1;
/* 103 */     this.m_hasRotateSkew = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getRotateSkew0()
/*     */   {
/* 112 */     return this.m_rotateSkew0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getRotateSkew1()
/*     */   {
/* 121 */     return this.m_rotateSkew1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setScale(float scaleX, float scaleY)
/*     */   {
/* 133 */     this.m_scaleX = scaleX;
/* 134 */     this.m_scaleY = scaleY;
/* 135 */     this.m_hasScale = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getScaleX()
/*     */   {
/* 144 */     return this.m_scaleX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getScaleY()
/*     */   {
/* 153 */     return this.m_scaleY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getTranslateX()
/*     */   {
/* 162 */     return this.m_translateX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getTranslateY()
/*     */   {
/* 171 */     return this.m_translateY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTranslate(float translateX, float translateY)
/*     */   {
/* 183 */     this.m_translateX = translateX;
/* 184 */     this.m_translateY = translateY;
/* 185 */     this.m_hasTranslate = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasTranslate()
/*     */   {
/* 195 */     return this.m_hasTranslate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasRotateSkew()
/*     */   {
/* 205 */     return this.m_hasRotateSkew;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasScale()
/*     */   {
/* 215 */     return this.m_hasScale;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 219 */     return 
/* 220 */       "Matrix (scaleX=" + this.m_scaleX + " scaleY=" + this.m_scaleY + " rotateSkew0=" + this.m_rotateSkew0 + " rotateSkew1=" + this.m_rotateSkew1 + " translateX=" + this.m_translateX + " translateY=" + this.m_translateY + ")";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(OutputBitStream stream)
/*     */     throws IOException
/*     */   {
/* 232 */     stream.writeBooleanBit(this.m_hasScale);
/* 233 */     if (this.m_hasScale) {
/* 234 */       int nScaleBits = OutputBitStream.getFPBitsLength(this.m_scaleX);
/* 235 */       nScaleBits = Math.max(nScaleBits, OutputBitStream.getFPBitsLength(this.m_scaleY));
/* 236 */       stream.writeUnsignedBits(nScaleBits, 5);
/* 237 */       stream.writeFPBits(this.m_scaleX, nScaleBits);
/* 238 */       stream.writeFPBits(this.m_scaleY, nScaleBits);
/*     */     }
/* 240 */     stream.writeBooleanBit(this.m_hasRotateSkew);
/* 241 */     if (this.m_hasRotateSkew) {
/* 242 */       int nRotateBits = OutputBitStream.getFPBitsLength(this.m_rotateSkew0);
/* 243 */       nRotateBits = Math.max(nRotateBits, OutputBitStream.getFPBitsLength(this.m_rotateSkew1));
/* 244 */       stream.writeUnsignedBits(nRotateBits, 5);
/* 245 */       stream.writeFPBits(this.m_rotateSkew0, nRotateBits);
/* 246 */       stream.writeFPBits(this.m_rotateSkew1, nRotateBits);
/*     */     }
/* 248 */     stream.writeBooleanBit(this.m_hasTranslate);
/* 249 */     if (this.m_hasTranslate) {
/* 250 */       int nTranslateBits = OutputBitStream.getFPBitsLength(this.m_translateX);
/* 251 */       nTranslateBits = Math.max(nTranslateBits, OutputBitStream.getFPBitsLength(this.m_translateY));
/* 252 */       stream.writeUnsignedBits(nTranslateBits, 5);
/* 253 */       stream.writeFPBits(this.m_translateX, nTranslateBits);
/* 254 */       stream.writeFPBits(this.m_translateY, nTranslateBits);
/*     */     }
/* 256 */     stream.align();
/*     */   }
/*     */   
/*     */   public boolean scaleEquals(float sx, float sy) {
/* 260 */     return (this.m_scaleX == sx) && (this.m_scaleY == sy);
/*     */   }
/*     */   
/*     */   public boolean translateEquals(float tx, float ty) {
/* 264 */     return (this.m_translateX == tx) && (this.m_translateY == ty);
/*     */   }
/*     */   
/*     */   public boolean rotateSkewEquals(float rsx, float rsy) {
/* 268 */     return (this.m_rotateSkew0 == rsx) && (this.m_rotateSkew1 == rsy);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\Matrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */