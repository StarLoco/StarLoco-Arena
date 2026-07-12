/*    */ package com.ankamagames.framework.graphics.aps.records.tags;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.graphics.particlesystem.affectors.ColorFader;
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
/*    */ public class DefineColorFader
/*    */   extends DefineBaseAffecor
/*    */ {
/*    */   private float m_red;
/*    */   private float m_green;
/*    */   private float m_blue;
/*    */   private float m_alpha;
/*    */   private float m_speed;
/*    */   
/*    */   protected DefineColorFader() {}
/*    */   
/*    */   public DefineColorFader(ColorFader fader)
/*    */   {
/* 29 */     super(fader);
/* 30 */     this.m_code = 5;
/*    */     
/* 32 */     this.m_red = fader.getRed();
/* 33 */     this.m_green = fader.getGreen();
/* 34 */     this.m_blue = fader.getBlue();
/* 35 */     this.m_alpha = fader.getAlpha();
/* 36 */     this.m_speed = fader.getSpeed();
/*    */   }
/*    */   
/*    */   public void initializeAffector(ColorFader fader)
/*    */   {
/* 41 */     fader.setRed(this.m_red);
/* 42 */     fader.setGreen(this.m_green);
/* 43 */     fader.setBlue(this.m_blue);
/* 44 */     fader.setAlpha(this.m_alpha);
/* 45 */     fader.setSpeed(this.m_speed);
/*    */   }
/*    */   
/*    */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 49 */     outStream.writeFloat(this.m_red);
/* 50 */     outStream.writeFloat(this.m_green);
/* 51 */     outStream.writeFloat(this.m_blue);
/* 52 */     outStream.writeFloat(this.m_alpha);
/* 53 */     outStream.writeFloat(this.m_speed);
/*    */     
/* 55 */     super.writeData(outStream);
/*    */   }
/*    */   
/*    */   public void setData(byte[] data, short version) throws IOException {
/* 59 */     InputBitStream inStream = new InputBitStream(data);
/* 60 */     this.m_red = inStream.readFloat();
/* 61 */     this.m_green = inStream.readFloat();
/* 62 */     this.m_blue = inStream.readFloat();
/* 63 */     this.m_alpha = inStream.readFloat();
/* 64 */     this.m_speed = inStream.readFloat();
/*    */     
/* 66 */     setTagsData(inStream, version);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineColorFader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */