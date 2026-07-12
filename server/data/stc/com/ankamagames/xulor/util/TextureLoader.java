/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
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
/*    */ public class TextureLoader
/*    */ {
/* 17 */   private static TextureLoader m_textureLoader = new TextureLoader();
/* 18 */   private HashMap<URL, ThemeTexture> m_textures = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */   public ThemeTexture loadTexture(URL url)
/*    */   {
/* 24 */     ThemeTexture texture = (ThemeTexture)this.m_textures.get(url);
/* 25 */     if ((texture == null) && (url != null)) {
/* 26 */       texture = new ThemeTexture(url);
/* 27 */       if (texture != null) {
/* 28 */         this.m_textures.put(url, texture);
/*    */       }
/*    */     }
/*    */     
/* 32 */     return texture;
/*    */   }
/*    */   
/*    */   public static TextureLoader getInstance() {
/* 36 */     return m_textureLoader;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\TextureLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */