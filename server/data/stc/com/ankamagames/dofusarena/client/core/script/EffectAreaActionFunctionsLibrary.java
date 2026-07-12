/*    */ package com.ankamagames.dofusarena.client.core.script;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.action.EffectAreaAction;
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import org.keplerproject.luajava.LuaException;
/*    */ import org.keplerproject.luajava.LuaState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EffectAreaActionFunctionsLibrary
/*    */   extends JavaFunctionsLibrary
/*    */ {
/*    */   private EffectAreaAction m_effectAreaAction;
/*    */   
/*    */   private class GetTarget
/*    */     extends JavaFunctionEx
/*    */   {
/*    */     public GetTarget(LuaState luaState)
/*    */     {
/* 28 */       super();
/*    */     }
/*    */     
/*    */     public String getName() {
/* 32 */       return "getTarget";
/*    */     }
/*    */     
/*    */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 36 */       return null;
/*    */     }
/*    */     
/*    */     public void run(int paramCount) throws LuaException {
/* 40 */       addReturnValue(EffectAreaActionFunctionsLibrary.this.m_effectAreaAction.getTargetId());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public EffectAreaActionFunctionsLibrary(EffectAreaAction action)
/*    */   {
/* 48 */     super("EffectArea");
/* 49 */     this.m_effectAreaAction = action;
/*    */     
/* 51 */     registerFunctionClass(GetTarget.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\script\EffectAreaActionFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */