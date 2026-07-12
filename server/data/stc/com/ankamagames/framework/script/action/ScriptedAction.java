/*     */ package com.ankamagames.framework.script.action;
/*     */ 
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.LuaManager;
/*     */ import com.ankamagames.framework.script.LuaManagerEventListener;
/*     */ import com.ankamagames.framework.script.LuaScript;
/*     */ import com.ankamagames.framework.script.LuaScriptErrorType;
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
/*     */ public class ScriptedAction
/*     */   extends Action
/*     */   implements LuaManagerEventListener
/*     */ {
/*     */   public static final int NO_SCRIPT_ID = -1;
/*     */   public static final int NO_SCRIPT_FILE_ID = -1;
/*  28 */   private int m_scriptFileId = -1;
/*     */   
/*     */   private JavaFunctionsLibrary[] m_libraries;
/*     */   
/*  32 */   private int m_waitingEndScript = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScriptedAction(int uniqueId, int type, int id)
/*     */   {
/*  42 */     super(uniqueId, type, id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addJavaFunctionsLibrary(JavaFunctionsLibrary library)
/*     */   {
/*  49 */     if (this.m_libraries == null) {
/*  50 */       this.m_libraries = new JavaFunctionsLibrary[1];
/*     */     } else {
/*  52 */       JavaFunctionsLibrary[] newLibraries = new JavaFunctionsLibrary[this.m_libraries.length + 1];
/*     */       
/*  54 */       System.arraycopy(this.m_libraries, 0, newLibraries, 0, this.m_libraries.length);
/*  55 */       this.m_libraries = newLibraries;
/*     */     }
/*     */     
/*  58 */     this.m_libraries[(this.m_libraries.length - 1)] = library;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getScriptFileId()
/*     */   {
/*  65 */     return this.m_scriptFileId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setScriptFileId(int scriptId)
/*     */   {
/*  72 */     this.m_scriptFileId = scriptId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLibraries(JavaFunctionsLibrary[] libraries)
/*     */   {
/*  79 */     this.m_libraries = libraries;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*  89 */     if (this.m_scriptFileId != -1)
/*     */     {
/*  91 */       LuaManager.getInstance().addEventListener(this);
/*     */       
/*  93 */       if (this.m_libraries != null) {
/*  94 */         this.m_waitingEndScript = LuaManager.getInstance().runScript(this.m_scriptFileId, this.m_libraries);
/*     */       } else
/*  96 */         this.m_waitingEndScript = LuaManager.getInstance().runScript(this.m_scriptFileId);
/*     */     } else {
/*  98 */       this.m_waitingEndScript = -1;
/*     */     }
/*     */     
/* 101 */     if (this.m_waitingEndScript == -1) {
/* 102 */       fireActionFinishedEvent();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onScriptFinished(LuaScript script)
/*     */   {
/* 112 */     if (script.getId() == this.m_waitingEndScript) {
/* 113 */       LuaManager.getInstance().removeEventListener(this);
/*     */       
/* 115 */       this.m_waitingEndScript = -1;
/* 116 */       fireActionFinishedEvent();
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
/*     */   public void onLuaScriptError(LuaScript script, LuaScriptErrorType errorType, String message)
/*     */   {
/* 129 */     if (script.getId() == this.m_waitingEndScript) {
/* 130 */       LuaManager.getInstance().removeEventListener(this);
/*     */       
/* 132 */       this.m_waitingEndScript = -1;
/* 133 */       fireActionFinishedEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWaitingEndScript() {
/* 138 */     return this.m_waitingEndScript;
/*     */   }
/*     */   
/*     */   protected void onActionFinished() {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\action\ScriptedAction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */