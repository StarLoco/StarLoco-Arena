/*    */ package com.ankamagames.baseImpl.graphicalClient.ui.shortcuts;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.xulor.shortcuts.AbstractShortcutManager;
/*    */ import com.ankamagames.xulor.shortcuts.Shortcut;
/*    */ import com.ankamagames.xulor.shortcuts.ShortcutGroup;
/*    */ import java.awt.event.KeyEvent;
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
/*    */ public final class ShortcutManager
/*    */   extends AbstractShortcutManager
/*    */ {
/* 25 */   private static ShortcutManager m_instance = new ShortcutManager();
/*    */   
/* 27 */   private static final Logger m_logger = Logger.getLogger(ShortcutManager.class);
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
/*    */   public static ShortcutManager getInstance() {
/* 42 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean keyReleased(KeyEvent keyEvent) {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean keyPressed(KeyEvent keyEvent) {
/* 63 */     for (ShortcutGroup group : this.m_shortcutGroups) {
/* 64 */       if (group.isEnabled()) {
/* 65 */         for (Shortcut shortcut : group.getShortcuts()) {
/* 66 */           if (shortcut.isKeyValid(keyEvent.getKeyCode(), keyEvent.getKeyChar()) && 
/* 67 */             shortcut.isCtrlKey() == keyEvent.isControlDown() && shortcut.isAltKey() == keyEvent.isAltDown() && shortcut.isShiftKey() == keyEvent.isShiftDown()) {
/* 68 */             if (shortcut.getConsoleCommand() != null) {
/* 69 */               String input = shortcut.getConsoleCommand()
/* 70 */                 .replace("${keyCode}", Integer.toString(keyEvent.getKeyCode()))
/* 71 */                 .replace("${keyChar}", Character.toString(keyEvent.getKeyChar()));
/* 72 */               ConsoleManager.getInstance().parseInput(input);
/* 73 */               return true;
/* 74 */             }  if (shortcut.getShortcutTrigger() != null) {
/* 75 */               shortcut.getShortcutTrigger().run();
/* 76 */               return true;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean keyTyped(KeyEvent keyEvent) {
/* 93 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClien\\ui\shortcuts\ShortcutManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */