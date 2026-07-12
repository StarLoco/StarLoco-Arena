/*    */ package com.ankamagames.xulor.shortcuts;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class ShortcutGroup
/*    */ {
/*    */   private String m_name;
/* 17 */   private boolean m_enabled = false;
/*    */   private ArrayList<Shortcut> m_shortcuts;
/*    */   
/*    */   public ShortcutGroup(String name) {
/* 21 */     this.m_name = name;
/* 22 */     this.m_shortcuts = new ArrayList();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setShortcuts(ArrayList<Shortcut> shortcuts)
/*    */   {
/* 31 */     this.m_shortcuts = shortcuts;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Shortcut getShortcut(int index)
/*    */   {
/* 41 */     if ((this.m_shortcuts != null) && (index < this.m_shortcuts.size())) {
/* 42 */       return (Shortcut)this.m_shortcuts.get(index);
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addShortcut(Shortcut shortcut)
/*    */   {
/* 53 */     if (this.m_shortcuts == null) {
/* 54 */       this.m_shortcuts = new ArrayList();
/*    */     }
/* 56 */     this.m_shortcuts.add(shortcut);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ArrayList<Shortcut> getShortcuts()
/*    */   {
/* 65 */     return this.m_shortcuts;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 74 */     return this.m_name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 83 */     this.m_name = name;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 87 */     return this.m_enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 91 */     this.m_enabled = enabled;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\shortcuts\ShortcutGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */