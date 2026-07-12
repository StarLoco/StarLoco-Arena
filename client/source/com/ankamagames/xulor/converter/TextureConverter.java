/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.TextureLoader;
/*    */ import com.ankamagames.xulor.util.ThemeTexture;
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
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
/*    */ public class TextureConverter
/*    */   implements Converter
/*    */ {
/* 23 */   private static Logger m_logger = Logger.getLogger(TextureConverter.class);
/* 24 */   private Class TEMPLATE = ThemeTexture.class;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object convert(Class type, String value) {
/* 34 */     if (value != null && 
/* 35 */       type.equals(ThemeTexture.class)) {
/* 36 */       ThemeTexture tex = null;
/*    */       
/*    */       try {
/* 39 */         URL url = new URL(value);
/* 40 */         if (url != null) {
/* 41 */           tex = TextureLoader.getInstance().loadTexture(url);
/* 42 */           if (tex.isLoaded()) {
/* 43 */             return tex;
/*    */           }
/*    */         } 
/* 46 */       } catch (MalformedURLException e) {
/* 47 */         e.printStackTrace();
/*    */       } 
/*    */       
/* 50 */       URL resourceUrl = getClass().getClassLoader().getResource(value);
/* 51 */       if (resourceUrl != null) {
/* 52 */         tex = TextureLoader.getInstance().loadTexture(resourceUrl);
/* 53 */         return tex;
/*    */       } 
/*    */       
/* 56 */       File f = new File(value);
/* 57 */       if (f.exists()) {
/*    */         try {
/* 59 */           tex = TextureLoader.getInstance().loadTexture(f.toURI().toURL());
/* 60 */           return tex;
/* 61 */         } catch (MalformedURLException e) {
/* 62 */           e.printStackTrace();
/*    */         } 
/*    */       }
/*    */       
/* 66 */       tex = Xulor.getInstance().getThemeParser().getTexture(value);
/* 67 */       return tex;
/*    */     } 
/*    */ 
/*    */     
/* 71 */     m_logger.error("pas de texture " + value);
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 81 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\TextureConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */