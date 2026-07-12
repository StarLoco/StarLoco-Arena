/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*    */ import org.keplerproject.luajava.LuaException;
/*    */ import org.keplerproject.luajava.LuaState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetMobileVisible
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileVisible(LuaState luaState)
/*    */   {
/* 20 */     super(luaState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 29 */     return "setMobileVisible";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 38 */     return new LuaScriptParameterDescriptor[] {
/* 39 */       new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 40 */       new LuaScriptParameterDescriptor("visible", LuaScriptParameterType.BOOLEAN, false) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 50 */     int mobileId = getParamInt(0);
/* 51 */     boolean visible = getParamBool(1);
/*    */     
/* 53 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 54 */     if (mobile != null) {
/* 55 */       mobile.setVisible(visible);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileVisible.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */