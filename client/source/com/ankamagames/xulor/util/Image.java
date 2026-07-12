/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.imageio.ImageIO;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class Image
/*    */ {
/* 26 */   private static Logger m_logger = Logger.getLogger(Image.class);
/*    */   
/* 28 */   private BufferedImage m_image = null;
/*    */   private boolean m_loaded = false;
/* 30 */   private int m_width = -1;
/* 31 */   private int m_height = -1;
/*    */   
/*    */   public Image(File file) {
/*    */     try {
/* 35 */       loadImage(file.toURI().toURL());
/* 36 */     } catch (MalformedURLException mue) {
/* 37 */       m_logger.error("Erreur au chargement de l'image (url incorrecte) : " + file + " (" + mue + ")");
/*    */     } 
/*    */   }
/*    */   
/*    */   public Image(URL url) {
/* 42 */     loadImage(url);
/*    */   }
/*    */   
/*    */   public void loadImage(URL url) {
/*    */     try {
/* 47 */       this.m_image = ImageIO.read(url);
/* 48 */       this.m_width = this.m_image.getWidth();
/* 49 */       this.m_height = this.m_image.getHeight();
/* 50 */       this.m_loaded = true;
/* 51 */     } catch (Exception e) {
/* 52 */       m_logger.error("Erreur au chargement de l'image : " + url + " (" + e + ")");
/*    */     } 
/*    */   }
/*    */   
/*    */   public BufferedImage getImage() {
/* 57 */     return this.m_image;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 61 */     return this.m_width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 65 */     return this.m_height;
/*    */   }
/*    */   
/*    */   public void releaseImage() {
/* 69 */     this.m_image = null;
/*    */   }
/*    */   
/*    */   public boolean isLoaded() {
/* 73 */     return this.m_loaded;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\Image.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */