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
/*    */ public class SetMobileAnimationSpeed
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileAnimationSpeed(LuaState luaState) {
/* 20 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return "setMobileAnimationSpeed";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 38 */     return new LuaScriptParameterDescriptor[] {
/* 39 */         new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 40 */         new LuaScriptParameterDescriptor("animationSpeed", LuaScriptParameterType.NUMBER, false)
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 49 */     int mobileId = getParamInt(0);
/* 50 */     float animationSpeed = (float)getParamDouble(1);
/*    */     
/* 52 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 53 */     if (mobile != null)
/* 54 */       mobile.setAnimationSpeed(animationSpeed); 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileAnimationSpeed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */