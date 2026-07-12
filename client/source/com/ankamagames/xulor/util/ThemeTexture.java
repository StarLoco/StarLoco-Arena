/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.URL;
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
/*    */ public class ThemeTexture
/*    */   extends Image
/*    */ {
/* 22 */   private Object m_instanciatedTexture = null;
/*    */   public URL m_url;
/*    */   
/*    */   public ThemeTexture(File f) {
/* 26 */     super(f);
/*    */   }
/*    */   
/*    */   public ThemeTexture(URL url) {
/* 30 */     super(url);
/* 31 */     this.m_url = url;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getInstanciatedTexture() {
/* 39 */     return this.m_instanciatedTexture;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInstanciatedTexture(Object instanciatedTexture) {
/* 47 */     this.m_instanciatedTexture = instanciatedTexture;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\ThemeTexture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */