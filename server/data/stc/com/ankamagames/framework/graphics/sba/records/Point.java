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
/*     */ public class Point
/*     */ {
/*     */   private int m_x;
/*     */   private int m_y;
/*     */   
/*     */   public Point(int x, int y)
/*     */   {
/*  31 */     this.m_x = x;
/*  32 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public Point(InputBitStream stream) throws IOException {
/*  36 */     this.m_x = stream.readSI32();
/*  37 */     this.m_y = stream.readSI32();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void move(int dx, int dy)
/*     */   {
/*  47 */     this.m_x += dx;
/*  48 */     this.m_y += dy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void scale(float factor)
/*     */   {
/*  57 */     this.m_x = ((int)(this.m_x * factor));
/*  58 */     this.m_y = ((int)(this.m_y * factor));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/*  65 */     return this.m_x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setX(int x)
/*     */   {
/*  72 */     this.m_x = x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getY()
/*     */   {
/*  79 */     return this.m_y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setY(int y)
/*     */   {
/*  86 */     this.m_y = y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     return "Point (" + this.m_x + ", " + this.m_y + ")";
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
/* 105 */     stream.writeSI32(this.m_x);
/* 106 */     stream.writeSI32(this.m_y);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\Point.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */