/*     */ package com.ankamagames.dofusarena.client.core.script;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.action.AbstractFightCastAction;
/*     */ import com.ankamagames.framework.script.JavaFunctionEx;
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*     */ import org.keplerproject.luajava.LuaException;
/*     */ import org.keplerproject.luajava.LuaState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FightCastActionFunctionsLibrary
/*     */   extends JavaFunctionsLibrary
/*     */ {
/*     */   private AbstractFightCastAction m_fightCastAction;
/*     */   
/*     */   private class GetCaster
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public GetCaster(LuaState luaState)
/*     */     {
/*  26 */       super();
/*     */     }
/*     */     
/*     */     public String getName() {
/*  30 */       return "getCaster";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  34 */       return null;
/*     */     }
/*     */     
/*     */     public void run(int paramCount)
/*     */       throws LuaException
/*     */     {
/*  40 */       addReturnValue(FightCastActionFunctionsLibrary.this.m_fightCastAction.getInstigatorId());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class GetCastPosition
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public GetCastPosition(LuaState luaState)
/*     */     {
/*  50 */       super();
/*     */     }
/*     */     
/*     */     public String getName() {
/*  54 */       return "getPosition";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  58 */       return null;
/*     */     }
/*     */     
/*     */     public void run(int paramCount)
/*     */       throws LuaException
/*     */     {
/*  64 */       addReturnValue(FightCastActionFunctionsLibrary.this.m_fightCastAction.getX());
/*  65 */       addReturnValue(FightCastActionFunctionsLibrary.this.m_fightCastAction.getY());
/*  66 */       addReturnValue(FightCastActionFunctionsLibrary.this.m_fightCastAction.getZ());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class IsCritical
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public IsCritical(LuaState luaState)
/*     */     {
/*  76 */       super();
/*     */     }
/*     */     
/*     */     public String getName() {
/*  80 */       return "isCritical";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  84 */       return null;
/*     */     }
/*     */     
/*     */     public void run(int paramCount)
/*     */       throws LuaException
/*     */     {
/*  90 */       addReturnValue(FightCastActionFunctionsLibrary.this.m_fightCastAction.isCriticalHit());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FightCastActionFunctionsLibrary(AbstractFightCastAction action)
/*     */   {
/* 100 */     super("Cast");
/* 101 */     this.m_fightCastAction = action;
/*     */     
/* 103 */     registerFunctionClass(GetCaster.class);
/* 104 */     registerFunctionClass(GetCastPosition.class);
/* 105 */     registerFunctionClass(IsCritical.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\script\FightCastActionFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */