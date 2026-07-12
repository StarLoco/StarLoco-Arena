/*     */ package com.ankamagames.framework.fileFormat.xml;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainerEventsHandler;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import java.util.ArrayList;
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
/*     */ public class XMLDocumentContainer
/*     */   implements DocumentContainer
/*     */ {
/*     */   private XMLDocumentNode m_rootNode;
/*     */   private ArrayList<DocumentContainerEventsHandler> m_handlers;
/*     */   
/*     */   public XMLDocumentContainer()
/*     */   {
/*  25 */     this.m_handlers = new ArrayList();
/*     */   }
/*     */   
/*     */   public XMLDocumentNode getRootNode() {
/*  29 */     return this.m_rootNode;
/*     */   }
/*     */   
/*     */   public void setRootNode(XMLDocumentNode rootNode) {
/*  33 */     this.m_rootNode = rootNode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DocumentEntry getEntryByName(String name)
/*     */   {
/*  43 */     if (this.m_rootNode != null) {
/*  44 */       return this.m_rootNode.getChildByName(name);
/*     */     }
/*  46 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<DocumentEntry> getEntriesByName(String name)
/*     */   {
/*  56 */     if (this.m_rootNode != null) {
/*  57 */       return this.m_rootNode.getChildrenByName(name);
/*     */     }
/*  59 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addEventsHandler(DocumentContainerEventsHandler handler)
/*     */   {
/*  69 */     if (!this.m_handlers.contains(handler)) {
/*  70 */       this.m_handlers.add(handler);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void notifyOnLoadBegin()
/*     */   {
/*  77 */     for (DocumentContainerEventsHandler handler : this.m_handlers) {
/*  78 */       handler.onLoadBegin(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void notifyOnLoadComplete()
/*     */   {
/*  85 */     for (DocumentContainerEventsHandler handler : this.m_handlers) {
/*  86 */       handler.onLoadComplete(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void notifyOnLoadError(String errorMessage)
/*     */   {
/*  95 */     for (DocumentContainerEventsHandler handler : this.m_handlers) {
/*  96 */       handler.onLoadError(this, errorMessage);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void notifyOnSaveBegin()
/*     */   {
/* 103 */     for (DocumentContainerEventsHandler handler : this.m_handlers) {
/* 104 */       handler.onSaveBegin(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void notifyOnSaveComplete()
/*     */   {
/* 111 */     for (DocumentContainerEventsHandler handler : this.m_handlers) {
/* 112 */       handler.onSaveComplete(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void notifyOnSaveError(String errorMessage)
/*     */   {
/* 121 */     for (DocumentContainerEventsHandler handler : this.m_handlers) {
/* 122 */       handler.onSaveError(this, errorMessage);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\xml\XMLDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */