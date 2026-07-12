/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ public class SetMobileDirection
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileDirection(LuaState luaState)
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
/* 34 */     return "setMobileDirection";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 43 */     return new LuaScriptParameterDescriptor[] {
/* 44 */       new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 45 */       new LuaScriptParameterDescriptor("directionIndex", LuaScriptParameterType.INTEGER, false) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 55 */     int mobileId = getParamInt(0);
/* 56 */     int directionIndex = getParamInt(1);
/*    */     
/* 58 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 59 */     if (mobile != null) {
/* 60 */       mobile.setDirection(Direction8.getDirectionFromIndex(directionIndex));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileDirection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */