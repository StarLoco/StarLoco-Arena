/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.template.IElement;
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
/*    */ public class XulorLoad
/*    */   implements XulorLoadUnload
/*    */ {
/* 20 */   private long m_options = 0L;
/* 21 */   private String m_id = null;
/*    */   private short m_level;
/* 23 */   private ElementMap m_elementMap = null;
/*    */   private URL m_documentUrl;
/*    */   private IElement m_parent;
/* 26 */   private URL m_currentDirectory = null;
/*    */ 
/*    */   
/*    */   private int m_duration;
/*    */ 
/*    */   
/*    */   public URL getDocumentUrl() {
/* 33 */     return this.m_documentUrl;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ElementMap getElementMap() {
/* 40 */     return this.m_elementMap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 47 */     return this.m_id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getLevel() {
/* 54 */     return this.m_level;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getOptions() {
/* 61 */     return this.m_options;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement getParent() {
/* 68 */     return this.m_parent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDuration() {
/* 75 */     return this.m_duration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public URL getCurrentDirectory() {
/* 82 */     return this.m_currentDirectory;
/*    */   }
/*    */   
/*    */   public XulorLoad(URL url, String id, ElementMap elementMap, IElement parent, URL currentDirectory, int duration, long options, short level) {
/* 86 */     this.m_documentUrl = url;
/* 87 */     this.m_elementMap = elementMap;
/* 88 */     this.m_id = id;
/* 89 */     this.m_level = level;
/* 90 */     this.m_options = options;
/* 91 */     this.m_parent = parent;
/* 92 */     this.m_duration = duration;
/* 93 */     this.m_currentDirectory = currentDirectory;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulo\\util\XulorLoad.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */