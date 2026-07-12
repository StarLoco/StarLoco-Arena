/*     */ package com.ankamagames.xulor.shortcuts;
/*     */ 
/*     */ import java.util.regex.Pattern;
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
/*     */ public class Shortcut
/*     */ {
/*     */   private String m_id;
/*     */   private int m_keyCodeStart;
/*     */   private int m_keyCodeEnd;
/*  23 */   private Pattern m_keyPattern = null;
/*     */   
/*  25 */   private String m_consoleCommand = null;
/*     */   
/*     */   private boolean m_ctrlKey = false;
/*     */   
/*     */   private boolean m_altKey = false;
/*     */   
/*     */   private boolean m_shiftKey = false;
/*     */   
/*  33 */   private ShortcutTrigger m_trigger = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shortcut(String id, int keyCode, String consoleCommand) {
/*  39 */     this((String)null, keyCode, consoleCommand, false, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shortcut(String id, int keyCode, String consoleCommand, boolean ctrlKey, boolean altKey, boolean shifKey) {
/*  46 */     this.m_id = id;
/*  47 */     this.m_keyCodeStart = keyCode;
/*  48 */     this.m_consoleCommand = consoleCommand;
/*  49 */     this.m_ctrlKey = ctrlKey;
/*  50 */     this.m_altKey = altKey;
/*  51 */     this.m_shiftKey = shifKey;
/*     */   }
/*     */   
/*     */   public Shortcut(String id, String keyCodePattern, String consoleCommand, boolean ctrlKey, boolean altKey, boolean shifKey) {
/*  55 */     String[] keyCodes = keyCodePattern.split("-");
/*  56 */     if (keyCodes.length == 1) {
/*  57 */       this.m_keyCodeStart = this.m_keyCodeEnd = Integer.parseInt(keyCodes[0]);
/*  58 */     } else if (keyCodes.length == 2) {
/*  59 */       this.m_keyCodeStart = Integer.parseInt(keyCodes[0]);
/*  60 */       this.m_keyCodeEnd = Integer.parseInt(keyCodes[1]);
/*     */     } 
/*     */     
/*  63 */     this.m_id = id;
/*  64 */     this.m_consoleCommand = consoleCommand;
/*  65 */     this.m_ctrlKey = ctrlKey;
/*  66 */     this.m_altKey = altKey;
/*  67 */     this.m_shiftKey = shifKey;
/*     */   }
/*     */   
/*     */   public Shortcut(String id, Pattern keyCodePattern, String consoleCommand, boolean ctrlKey, boolean altKey, boolean shifKey) {
/*  71 */     this.m_id = null;
/*  72 */     this.m_keyPattern = keyCodePattern;
/*  73 */     this.m_consoleCommand = consoleCommand;
/*  74 */     this.m_ctrlKey = ctrlKey;
/*  75 */     this.m_altKey = altKey;
/*  76 */     this.m_shiftKey = shifKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getConsoleCommand() {
/*  83 */     return this.m_consoleCommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKeyCodeStart() {
/*  90 */     return this.m_keyCodeStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCtrlKey(boolean ctrlKey) {
/*  97 */     this.m_ctrlKey = ctrlKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAltKey(boolean altKey) {
/* 104 */     this.m_altKey = altKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShiftKey(boolean shiftKey) {
/* 111 */     this.m_shiftKey = shiftKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCtrlKey() {
/* 118 */     return this.m_ctrlKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAltKey() {
/* 125 */     return this.m_altKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShiftKey() {
/* 132 */     return this.m_shiftKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 140 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 148 */     this.m_id = id;
/*     */   }
/*     */   
/*     */   public boolean isKeyValid(int keyCode, char keyChar) {
/* 152 */     if (this.m_keyPattern != null && this.m_keyPattern.matcher(Character.toString(keyChar)).matches())
/* 153 */       return true; 
/* 154 */     if (keyCode >= this.m_keyCodeStart && keyCode <= this.m_keyCodeEnd)
/* 155 */       return true; 
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShortcutTrigger(ShortcutTrigger trigger) {
/* 164 */     this.m_trigger = trigger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortcutTrigger getShortcutTrigger() {
/* 172 */     return this.m_trigger;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\shortcuts\Shortcut.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */