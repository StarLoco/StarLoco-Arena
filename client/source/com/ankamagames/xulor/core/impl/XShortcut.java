/*     */ package com.ankamagames.xulor.core.impl;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.event.listener.ActionListener;
/*     */ import com.ankamagames.xulor.shortcuts.ShortcutTrigger;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IObservable;
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
/*     */ public class XShortcut
/*     */   extends XElement
/*     */   implements IElement
/*     */ {
/*     */   public static final String TAG = "Shortcut";
/*  25 */   private static Logger m_logger = Logger.getLogger(XShortcut.class);
/*     */   
/*  27 */   private String m_shortcut = null;
/*  28 */   private String m_action = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_alreadyBuilt = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  52 */     if (!this.m_alreadyBuilt && this.m_parent != null && this.m_parent instanceof com.ankamagames.xulor.template.IListenerManager) {
/*  53 */       if (Xulor.getInstance().getShortcutManager() != null) {
/*  54 */         Xulor.getInstance().getShortcutManager().setShortcutTrigger(this.m_shortcut, (ShortcutTrigger)new ActionListener((IObservable)this.m_parent, this.m_action));
/*     */       } else {
/*  56 */         m_logger.error("Tentative de créer un Shortcut alors qu'aucun AbstractShortcutMAnager n'est défini dans Xulor");
/*     */       } 
/*  58 */       this.m_alreadyBuilt = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAction() {
/*  69 */     return this.m_action;
/*     */   }
/*     */   
/*     */   public void setAction(String action) {
/*  73 */     this.m_action = action;
/*     */   }
/*     */   
/*     */   public String getShortcut() {
/*  77 */     return this.m_shortcut;
/*     */   }
/*     */   
/*     */   public void setShortcut(String ref) {
/*  81 */     this.m_shortcut = ref;
/*     */   }
/*     */   
/*     */   protected void copyElementData(XShortcut shortcut) {
/*  85 */     shortcut.setAction(this.m_action);
/*  86 */     shortcut.setShortcut(this.m_shortcut);
/*  87 */     copyElementData(shortcut);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/*  94 */     XShortcut shortcut = new XShortcut();
/*  95 */     copyElementData(shortcut);
/*  96 */     return shortcut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 103 */     return "Shortcut";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\impl\XShortcut.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */