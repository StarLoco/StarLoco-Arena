/*     */ package com.ankamagames.xulor.shortcuts;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentAccessor;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import com.ankamagames.framework.fileFormat.xml.XMLDocumentAccessor;
/*     */ import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractShortcutManager
/*     */   implements KeyboardController
/*     */ {
/*  29 */   private static final Logger m_logger = Logger.getLogger(AbstractShortcutManager.class);
/*     */   
/*     */   protected ArrayList<ShortcutGroup> m_shortcutGroups;
/*     */   
/*     */   protected ShortcutGroup m_currentShortcutGroup;
/*     */   
/*     */ 
/*     */   public AbstractShortcutManager()
/*     */   {
/*  38 */     this.m_shortcutGroups = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<Shortcut> getShortcuts()
/*     */   {
/*  47 */     return this.m_currentShortcutGroup.getShortcuts();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Shortcut getShortcut(int idx)
/*     */   {
/*  58 */     return this.m_currentShortcutGroup.getShortcut(idx);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setShortcuts(ArrayList<Shortcut> shortcuts)
/*     */   {
/*  68 */     this.m_currentShortcutGroup.setShortcuts(shortcuts);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addShortcut(Shortcut shortcut)
/*     */   {
/*  78 */     this.m_currentShortcutGroup.addShortcut(shortcut);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void enableGroup(String name, boolean enable)
/*     */   {
/*  87 */     for (ShortcutGroup group : this.m_shortcutGroups) {
/*  88 */       if (group.getName().equalsIgnoreCase(name)) {
/*  89 */         group.setEnabled(enable);
/*  90 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadFromXMLFile(String XMLFileName)
/*     */     throws Exception
/*     */   {
/* 105 */     DocumentAccessor accessor = XMLDocumentAccessor.getInstance();
/* 106 */     DocumentContainer document = accessor.getNewDocumentContainer();
/*     */     
/*     */ 
/* 109 */     m_logger.info("Loading shortcuts file.");
/*     */     
/*     */ 
/* 112 */     accessor.open(XMLFileName);
/* 113 */     accessor.read(document);
/* 114 */     accessor.close();
/*     */     
/*     */ 
/* 117 */     loadShortcuts(document);
/*     */   }
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
/*     */ 
/*     */   public void loadShortcuts(DocumentContainer modelsDocument)
/*     */   {
/* 132 */     ArrayList<DocumentEntry> groupEntries = modelsDocument.getEntriesByName("group");
/* 133 */     Iterator localIterator3; for (Iterator localIterator1 = groupEntries.iterator(); localIterator1.hasNext(); 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 159 */         localIterator3.hasNext())
/*     */     {
/* 133 */       DocumentEntry groupDoc = (DocumentEntry)localIterator1.next();
/* 134 */       if (groupDoc.getParameterByName("name") == null) {
/* 135 */         m_logger.error("Nom de groupe invalide dans le chargement des raccourcis");
/*     */       }
/* 137 */       String name = groupDoc.getParameterByName("name").getStringValue();
/* 138 */       ShortcutGroup group = null;
/* 139 */       for (ShortcutGroup g : this.m_shortcutGroups) {
/* 140 */         if (g.getName().equalsIgnoreCase(name)) {
/* 141 */           group = g;
/* 142 */           break;
/*     */         }
/*     */       }
/* 145 */       if (group == null) {
/* 146 */         group = new ShortcutGroup(name);
/* 147 */         this.m_shortcutGroups.add(group);
/*     */       }
/*     */       
/*     */ 
/* 151 */       ArrayList<DocumentEntry> shortcutsEntries = groupDoc.getChildrenByName("shortcut");
/* 152 */       String command = null;
/*     */       
/* 154 */       String keyCode = null;
/* 155 */       String id = null;
/* 156 */       Shortcut shortcut = null;
/*     */       
/*     */ 
/* 159 */       localIterator3 = shortcutsEntries.iterator(); continue;DocumentEntry d = (DocumentEntry)localIterator3.next();
/*     */       
/* 161 */       if ((d.getParameterByName("consoleCommand") != null) || (d.getParameterByName("id") != null))
/*     */       {
/*     */ 
/* 164 */         if (d.getParameterByName("consoleCommand") != null) {
/* 165 */           command = d.getParameterByName("consoleCommand").getStringValue();
/*     */         } else {
/* 167 */           command = null;
/*     */         }
/*     */         
/* 170 */         boolean ctrlKey = (d.getParameterByName("ctrlKey") != null) && (d.getParameterByName("ctrlKey").getBooleanValue());
/* 171 */         boolean altKey = (d.getParameterByName("altKey") != null) && (d.getParameterByName("altKey").getBooleanValue());
/* 172 */         boolean shiftKey = (d.getParameterByName("shiftKey") != null) && (d.getParameterByName("shiftKey").getBooleanValue());
/*     */         
/* 174 */         if (d.getParameterByName("id") != null) {
/* 175 */           id = d.getParameterByName("id").getStringValue();
/*     */         } else {
/* 177 */           id = null;
/*     */         }
/*     */         
/* 180 */         if (d.getParameterByName("keyCode") != null) {
/* 181 */           keyCode = d.getParameterByName("keyCode").getStringValue();
/* 182 */           shortcut = new Shortcut(id, keyCode, command, ctrlKey, altKey, shiftKey);
/* 183 */         } else if (d.getParameterByName("keyRegExp") != null) {
/* 184 */           keyCode = d.getParameterByName("keyRegExp").getStringValue();
/* 185 */           shortcut = new Shortcut(id, Pattern.compile(keyCode), command, ctrlKey, altKey, shiftKey);
/*     */         } else {
/* 187 */           m_logger.error("keyCode manquant dans le chargement des raccourcis");
/*     */         }
/*     */         
/* 190 */         group.addShortcut(shortcut);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void saveToFile(File file) {}
/*     */   
/*     */ 
/*     */   public void setShortcutTrigger(String shortcutId, ShortcutTrigger trigger)
/*     */   {
/* 202 */     for (ShortcutGroup group : this.m_shortcutGroups) {
/* 203 */       if (group.isEnabled()) {
/* 204 */         for (Shortcut shortcut : group.getShortcuts()) {
/* 205 */           if ((shortcut.getId() != null) && (shortcut.getId().equalsIgnoreCase(shortcutId))) {
/* 206 */             shortcut.setShortcutTrigger(trigger);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract boolean keyReleased(KeyEvent paramKeyEvent);
/*     */   
/*     */   public abstract boolean keyPressed(KeyEvent paramKeyEvent);
/*     */   
/*     */   public abstract boolean keyTyped(KeyEvent paramKeyEvent);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\shortcuts\AbstractShortcutManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */