/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.Reader;
/*    */ import java.util.HashMap;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.input.SAXBuilder;
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
/*    */ public class ThemeData
/*    */ {
/*    */   private Document m_document;
/*    */   private HashMap<String, ThemeElement> m_themeElements;
/*    */   
/*    */   public ThemeData(InputStream f) throws Exception {
/* 35 */     Reader r = new InputStreamReader(f);
/* 36 */     this.m_document = (new SAXBuilder()).build(r);
/* 37 */     this.m_themeElements = new HashMap<String, ThemeElement>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setThemeElement(String themeElementId, ThemeElement themeElement) {
/* 49 */     this.m_themeElements.put(themeElementId, themeElement);
/*    */   }
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
/*    */   public ThemeElement getThemeElement(String themeAppearanceId) {
/* 75 */     return this.m_themeElements.get(themeAppearanceId);
/*    */   }
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
/*    */   public Document getDocument() {
/* 88 */     return this.m_document;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */