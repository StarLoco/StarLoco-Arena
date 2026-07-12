/*    */ package com.ankamagames.baseImpl.graphics.alea.display;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*    */ import java.io.File;
/*    */ import java.text.DecimalFormat;
/*    */ import java.util.Properties;
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
/*    */ class MaterialFactory
/*    */ {
/*    */   public static final String DEFAULT_FILE_EXTENSION = ".png";
/* 21 */   private static final MaterialFactory m_instance = new MaterialFactory();
/*    */   
/* 23 */   private DecimalFormat frameParser = new DecimalFormat("0000");
/* 24 */   private String m_fileExtension = ".png";
/* 25 */   private String m_gfxPath = "";
/*    */   
/*    */   public static MaterialFactory getInstance() {
/* 28 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setFileExtension(String fileExtention)
/*    */   {
/* 37 */     this.m_fileExtension = fileExtention;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setGfxPath(String gfxPath)
/*    */   {
/* 47 */     this.m_gfxPath = gfxPath;
/*    */   }
/*    */   
/*    */ 
/*    */   public Material getCharacterMaterial(String characterPath, String animationDirectory, int directionIndex, int frameCount)
/*    */   {
/* 53 */     String filePath = this.m_gfxPath + System.getProperties().getProperty("file.separator") + characterPath + System.getProperties().getProperty("file.separator") + directionIndex + "-" + 
/* 54 */       animationDirectory + System.getProperties().getProperty("file.separator") + directionIndex + "_" + animationDirectory + this.frameParser.format(frameCount) + this.m_fileExtension;
/*    */     
/* 56 */     if (!new File(filePath).exists()) {
/* 57 */       return null;
/*    */     }
/* 59 */     Material material = new Material();
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 64 */     return material;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\MaterialFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */