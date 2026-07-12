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
/*    */ public class GetMobilePosition
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public GetMobilePosition(LuaState luaState)
/*    */   {
/* 24 */     super(luaState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 33 */     return "getMobilePosition";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 42 */     return new LuaScriptParameterDescriptor[] { new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 52 */     int mobileId = getParamInt(0);
/*    */     
/* 54 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 55 */     if (mobile != null) {
/* 56 */       addReturnValue(mobile.getWorldCellX());
/* 57 */       addReturnValue(mobile.getWorldCellY());
/* 58 */       addReturnValue(mobile.getAltitude());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\GetMobilePosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */