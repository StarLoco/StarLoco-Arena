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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetMobileToStaticAnimation
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileToStaticAnimation(LuaState luaState)
/*    */   {
/* 25 */     super(luaState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 34 */     return "setMobileToStaticAnimation";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 43 */     return new LuaScriptParameterDescriptor[] { new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false) };
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
/*    */     
/* 55 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 56 */     if (mobile != null)
/*    */     {
/*    */ 
/* 59 */       mobile.setAnimation(mobile.getStaticAnimationKey());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileToStaticAnimation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */