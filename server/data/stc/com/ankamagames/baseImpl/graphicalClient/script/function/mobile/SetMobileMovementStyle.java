/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
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
/*    */ 
/*    */ 
/*    */ public class SetMobileMovementStyle
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileMovementStyle(LuaState luaState)
/*    */   {
/* 23 */     super(luaState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 32 */     return "SetMobileMovementStyle";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 41 */     return new LuaScriptParameterDescriptor[] {
/* 42 */       new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 43 */       new LuaScriptParameterDescriptor("style", LuaScriptParameterType.STRING, false) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 53 */     int mobileId = getParamInt(0);
/* 54 */     String mobileStyle = getParamString(1);
/*    */     
/* 56 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 57 */     if ((mobile != null) && ((mobile instanceof PathMobile))) {
/* 58 */       ((PathMobile)mobile).setMovementStyle(mobileStyle);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileMovementStyle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */