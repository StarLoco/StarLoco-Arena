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
/*    */ public class SetMobilePosition
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobilePosition(LuaState luaState)
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
/* 33 */     return "setMobilePosition";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 42 */     return new LuaScriptParameterDescriptor[] {
/* 43 */       new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 44 */       new LuaScriptParameterDescriptor("worldX", LuaScriptParameterType.INTEGER, false), 
/* 45 */       new LuaScriptParameterDescriptor("worldY", LuaScriptParameterType.INTEGER, false), 
/* 46 */       new LuaScriptParameterDescriptor("altitude", LuaScriptParameterType.INTEGER, false) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 56 */     int mobileId = getParamInt(0);
/* 57 */     int worldX = getParamInt(1);
/* 58 */     int worldY = getParamInt(2);
/* 59 */     int altitude = getParamInt(3);
/*    */     
/* 61 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 62 */     if (mobile != null) {
/* 63 */       mobile.setWorldPosition(worldX, worldY, altitude);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobilePosition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */