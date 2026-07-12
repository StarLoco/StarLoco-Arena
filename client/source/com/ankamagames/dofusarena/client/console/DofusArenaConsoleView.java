/*     */ package com.ankamagames.dofusarena.client.console;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleView;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*     */ public class DofusArenaConsoleView
/*     */   implements ConsoleView, FieldProvider
/*     */ {
/*     */   public static final String FIELDEDPROPERTY_NAME = "debug.console";
/*     */   public static final String PROMPT_FIELD = "prompt";
/*     */   public static final String INPUT_FIELD = "input";
/*     */   public static final String LOGS_FIELD = "logs";
/*  25 */   public static final String[] FIELDS = new String[] { "prompt", "input", "logs" };
/*     */   
/*  27 */   private static DofusArenaConsoleView m_instance = new DofusArenaConsoleView();
/*     */   
/*  29 */   private String m_prompt = "";
/*  30 */   private String m_imput = "";
/*  31 */   private String m_logs = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DofusArenaConsoleView() {
/*  37 */     PropertiesProvider propertiesProvider = Xulor.getInstance().getEnvironment().getPropertiesProvider();
/*  38 */     propertiesProvider.setPropertyValue("debug.console", this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DofusArenaConsoleView getInstance() {
/*  45 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrompt(String prompt) {
/*  54 */     this.m_prompt = prompt;
/*  55 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("debug.console", "prompt", prompt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void err(String text) {
/*  64 */     String formatedText = "<color=FF0000|" + text + ">\n";
/*  65 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().appendPropertyValue("debug.console", "logs", formatedText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(String text) {
/*  74 */     String formatedText = "<color=00FF00|" + text + ">\n";
/*  75 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().appendPropertyValue("debug.console", "logs", formatedText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trace(String text) {
/*  84 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().appendPropertyValue("debug.console", "logs", String.valueOf(text) + "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/*  93 */     if (fieldName.equals("prompt"))
/*  94 */       return this.m_prompt; 
/*  95 */     if (fieldName.equals("input"))
/*  96 */       return this.m_imput; 
/*  97 */     if (fieldName.equals("logs")) {
/*  98 */       return this.m_logs;
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 109 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {
/* 119 */     if (fieldName.equals("prompt")) {
/* 120 */       this.m_prompt = (String)value;
/* 121 */     } else if (fieldName.equals("input")) {
/* 122 */       this.m_imput = (String)value;
/* 123 */     } else if (fieldName.equals("logs")) {
/* 124 */       this.m_logs = (String)value;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFieldValue(String fieldName, Object value) {
/* 135 */     if (fieldName.equals("logs")) {
/* 136 */       this.m_logs = String.valueOf(this.m_logs) + (String)value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 155 */     if (fieldName.equals("logs")) {
/* 156 */       return false;
/*     */     }
/* 158 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\DofusArenaConsoleView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */