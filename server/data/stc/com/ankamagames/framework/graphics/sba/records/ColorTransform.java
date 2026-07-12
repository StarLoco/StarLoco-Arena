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
/*     */ public class ColorTransform
/*     */ {
/*  21 */   private int m_redMultTerm = 256;
/*  22 */   private int m_greenMultTerm = 256;
/*  23 */   private int m_blueMultTerm = 256;
/*  24 */   private int m_alphaMultTerm = 256;
/*     */   
/*  26 */   private int m_redAddTerm = 0;
/*  27 */   private int m_greenAddTerm = 0;
/*  28 */   private int m_blueAddTerm = 0;
/*  29 */   private int m_alphaAddTerm = 0;
/*     */   
/*     */   private boolean m_hasMultTerms;
/*     */   
/*     */   private boolean m_hasAddTerms;
/*     */   
/*     */ 
/*     */   public ColorTransform(boolean hasMultTerm, boolean hasAddTerm)
/*     */   {
/*  38 */     this.m_hasMultTerms = hasMultTerm;
/*  39 */     this.m_hasAddTerms = hasAddTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ColorTransform(InputBitStream stream)
/*     */     throws IOException
/*     */   {
/*  51 */     this.m_hasAddTerms = stream.readBooleanBit();
/*  52 */     this.m_hasMultTerms = stream.readBooleanBit();
/*  53 */     int nBits = (int)stream.readUnsignedBits(4);
/*  54 */     if (this.m_hasMultTerms) {
/*  55 */       this.m_redMultTerm = ((int)stream.readSignedBits(nBits));
/*  56 */       this.m_greenMultTerm = ((int)stream.readSignedBits(nBits));
/*  57 */       this.m_blueMultTerm = ((int)stream.readSignedBits(nBits));
/*  58 */       this.m_alphaMultTerm = ((int)stream.readSignedBits(nBits));
/*     */     }
/*  60 */     if (this.m_hasAddTerms) {
/*  61 */       this.m_redAddTerm = ((int)stream.readSignedBits(nBits));
/*  62 */       this.m_greenAddTerm = ((int)stream.readSignedBits(nBits));
/*  63 */       this.m_blueAddTerm = ((int)stream.readSignedBits(nBits));
/*  64 */       this.m_alphaAddTerm = ((int)stream.readSignedBits(nBits));
/*     */     }
/*  66 */     stream.align();
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
/*     */ 
/*     */ 
/*     */   public void setAddTerms(int redAddTerm, int greenAddTerm, int blueAddTerm, int alphaAddTerm)
/*     */   {
/*  82 */     if ((redAddTerm == 0) && (greenAddTerm == 0) && (blueAddTerm == 0) && (alphaAddTerm == 0)) {
/*  83 */       this.m_hasAddTerms = false;
/*     */     } else {
/*  85 */       this.m_redAddTerm = redAddTerm;
/*  86 */       this.m_greenAddTerm = greenAddTerm;
/*  87 */       this.m_blueAddTerm = blueAddTerm;
/*  88 */       this.m_alphaAddTerm = alphaAddTerm;
/*  89 */       this.m_hasAddTerms = true;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMultTerms(int redMultTerm, int greenMultTerm, int blueMultTerm, int alphaMultTerm)
/*     */   {
/* 108 */     if ((redMultTerm == 256) && (greenMultTerm == 256) && (blueMultTerm == 256) && (alphaMultTerm == 256)) {
/* 109 */       this.m_hasMultTerms = false;
/*     */     } else {
/* 111 */       this.m_redMultTerm = redMultTerm;
/* 112 */       this.m_greenMultTerm = greenMultTerm;
/* 113 */       this.m_blueMultTerm = blueMultTerm;
/* 114 */       this.m_alphaMultTerm = alphaMultTerm;
/* 115 */       this.m_hasMultTerms = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAlphaAddTerm()
/*     */   {
/* 126 */     return this.m_alphaAddTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAlphaMultTerm()
/*     */   {
/* 136 */     return this.m_alphaMultTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlueAddTerm()
/*     */   {
/* 146 */     return this.m_blueAddTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlueMultTerm()
/*     */   {
/* 156 */     return this.m_blueMultTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getGreenAddTerm()
/*     */   {
/* 166 */     return this.m_greenAddTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getGreenMultTerm()
/*     */   {
/* 176 */     return this.m_greenMultTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRedAddTerm()
/*     */   {
/* 186 */     return this.m_redAddTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRedMultTerm()
/*     */   {
/* 196 */     return this.m_redMultTerm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasAddTerms()
/*     */   {
/* 205 */     return this.m_hasAddTerms;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasMultTerms()
/*     */   {
/* 215 */     return this.m_hasMultTerms;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 219 */     String mult = "no multiplication transformation";
/* 220 */     String add = "no addition transformation";
/* 221 */     if (hasMultTerms()) {
/* 222 */       mult = String.format("redMultTerm=%d greenMultTerm=%d blueMultTerm=%d alphaMultTerm=%d ", new Object[] {
/* 223 */         Integer.valueOf(this.m_redMultTerm), Integer.valueOf(this.m_greenMultTerm), Integer.valueOf(this.m_blueMultTerm), Integer.valueOf(this.m_alphaMultTerm) });
/*     */     }
/*     */     
/* 226 */     if (hasAddTerms()) {
/* 227 */       add = String.format("redAddTerm=%d greenAddTerm=%d blueAddTerm=%d alphaAddTerm=%d", new Object[] {
/* 228 */         Integer.valueOf(this.m_redAddTerm), Integer.valueOf(this.m_greenAddTerm), Integer.valueOf(this.m_blueAddTerm), Integer.valueOf(this.m_alphaAddTerm) });
/*     */     }
/* 230 */     return String.format("ColorTransform( %s  ;  %s)", new Object[] { mult, add });
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
/* 242 */     stream.writeBooleanBit(this.m_hasAddTerms);
/* 243 */     stream.writeBooleanBit(this.m_hasMultTerms);
/* 244 */     int nBits = 0;
/* 245 */     if (this.m_hasAddTerms) {
/* 246 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_redAddTerm));
/* 247 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_greenAddTerm));
/* 248 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_blueAddTerm));
/* 249 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_alphaAddTerm));
/*     */     }
/* 251 */     if (this.m_hasMultTerms) {
/* 252 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_redMultTerm));
/* 253 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_greenMultTerm));
/* 254 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_blueMultTerm));
/* 255 */       nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_alphaMultTerm));
/*     */     }
/* 257 */     stream.writeUnsignedBits(nBits, 4);
/* 258 */     if (this.m_hasMultTerms) {
/* 259 */       stream.writeSignedBits(this.m_redMultTerm, nBits);
/* 260 */       stream.writeSignedBits(this.m_greenMultTerm, nBits);
/* 261 */       stream.writeSignedBits(this.m_blueMultTerm, nBits);
/* 262 */       stream.writeSignedBits(this.m_alphaMultTerm, nBits);
/*     */     }
/* 264 */     if (this.m_hasAddTerms) {
/* 265 */       stream.writeSignedBits(this.m_redAddTerm, nBits);
/* 266 */       stream.writeSignedBits(this.m_greenAddTerm, nBits);
/* 267 */       stream.writeSignedBits(this.m_blueAddTerm, nBits);
/* 268 */       stream.writeSignedBits(this.m_alphaAddTerm, nBits);
/*     */     }
/* 270 */     stream.align();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(ColorTransform color)
/*     */   {
/* 281 */     return (color.getRedAddTerm() == this.m_redAddTerm) && (color.getGreenAddTerm() == this.m_greenAddTerm) && (color.getBlueAddTerm() == this.m_blueAddTerm) && (color.getAlphaAddTerm() == this.m_alphaAddTerm) && (color.getRedMultTerm() == this.m_redMultTerm) && (color.getGreenMultTerm() == this.m_greenMultTerm) && (color.getBlueMultTerm() == this.m_blueMultTerm) && (color.getAlphaMultTerm() == this.m_alphaMultTerm);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\ColorTransform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */