/*    */ package com.ankamagames.framework.graphics.sba.records;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*    */ import java.io.IOException;
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
/*    */ public class BitmapFrame
/*    */   extends Bitmap
/*    */ {
/*    */   public static final int INFINIT_DURATION = -1;
/*    */   private int m_duration;
/*    */   
/*    */   public BitmapFrame(Point hotPoint, AlphaBitmapData bitmapData, float quality, int duration)
/*    */   {
/* 39 */     super(hotPoint, bitmapData, quality);
/* 40 */     this.m_duration = duration;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BitmapFrame(InputBitStream inStream, short sbaversion)
/*    */     throws IOException
/*    */   {
/* 50 */     this.m_hotPoint = new Point(inStream);
/* 51 */     this.m_duration = inStream.readUI16();
/* 52 */     readBitmapData(inStream, sbaversion);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public BitmapFrame() {}
/*    */   
/*    */ 
/*    */   public int getDuration()
/*    */   {
/* 62 */     return this.m_duration;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setDuration(int duration)
/*    */   {
/* 69 */     this.m_duration = duration;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 73 */     return "BitmapFrame";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void write(OutputBitStream outStream)
/*    */     throws IOException
/*    */   {
/* 84 */     this.m_hotPoint.write(outStream);
/* 85 */     outStream.writeUI16(this.m_duration);
/* 86 */     writeBitmapData(outStream);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\BitmapFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */