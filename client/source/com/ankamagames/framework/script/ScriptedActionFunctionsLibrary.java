/*     */ package com.ankamagames.framework.script;
/*     */ 
/*     */ import com.ankamagames.framework.script.action.Action;
/*     */ import com.ankamagames.framework.script.action.ActionGroup;
/*     */ import java.util.Iterator;
/*     */ import org.keplerproject.luajava.LuaException;
/*     */ import org.keplerproject.luajava.LuaState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptedActionFunctionsLibrary
/*     */   extends JavaFunctionsLibrary
/*     */ {
/*     */   private ActionGroup m_actionGroup;
/*     */   
/*     */   private class ExecuteFirstAction
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public ExecuteFirstAction(LuaState luaState) {
/*  24 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  28 */       return "executeFirstAction";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  32 */       return new LuaScriptParameterDescriptor[] {
/*  33 */           new LuaScriptParameterDescriptor("actionType", LuaScriptParameterType.INTEGER, false), 
/*  34 */           new LuaScriptParameterDescriptor("actionId", LuaScriptParameterType.INTEGER, false) };
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/*  38 */       int type = getParamInt(0);
/*  39 */       int id = getParamInt(1);
/*     */       
/*  41 */       Action action = ScriptedActionFunctionsLibrary.this.m_actionGroup.getActionByTypeAndId(type, id);
/*     */       
/*  43 */       if (action != null) {
/*     */         
/*  45 */         ScriptedActionFunctionsLibrary.this.m_actionGroup.runAction(action, false);
/*  46 */         addReturnValue(true);
/*     */       } else {
/*     */         
/*  49 */         addReturnValue(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ExecuteAllAction
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public ExecuteAllAction(LuaState luaState) {
/*  59 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  63 */       return "executeAllAction";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  67 */       return new LuaScriptParameterDescriptor[] {
/*  68 */           new LuaScriptParameterDescriptor("actionType", LuaScriptParameterType.INTEGER, false), 
/*  69 */           new LuaScriptParameterDescriptor("actionId", LuaScriptParameterType.INTEGER, false) };
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/*  73 */       int type = getParamInt(0);
/*  74 */       int id = getParamInt(1);
/*     */       
/*  76 */       Action action = ScriptedActionFunctionsLibrary.this.m_actionGroup.getActionByTypeAndId(type, id);
/*     */       
/*  78 */       while (action != null) {
/*     */         
/*  80 */         ScriptedActionFunctionsLibrary.this.m_actionGroup.runAction(action, false);
/*  81 */         action = ScriptedActionFunctionsLibrary.this.m_actionGroup.getActionByTypeAndId(type, id);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class GetTargets
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public GetTargets(LuaState luaState) {
/*  93 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  97 */       return "getTargets";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/* 106 */       Iterable<Long> targets = ScriptedActionFunctionsLibrary.this.m_actionGroup.getTargets();
/*     */       
/* 108 */       for (Iterator<Long> iterator = targets.iterator(); iterator.hasNext(); ) { long value = ((Long)iterator.next()).longValue();
/*     */         
/* 110 */         addReturnValue(value); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptedActionFunctionsLibrary(ActionGroup actionGroup) {
/* 121 */     super("ScriptedAction");
/* 122 */     this.m_actionGroup = actionGroup;
/*     */     
/* 124 */     registerFunctionClass((Class)ExecuteFirstAction.class);
/* 125 */     registerFunctionClass((Class)ExecuteAllAction.class);
/* 126 */     registerFunctionClass((Class)GetTargets.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionGroup getScriptedActionGroup() {
/* 133 */     return this.m_actionGroup;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\ScriptedActionFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */