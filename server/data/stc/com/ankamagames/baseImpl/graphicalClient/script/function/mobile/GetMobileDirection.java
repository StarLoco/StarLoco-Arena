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
/*    */ public class GetMobileDirection
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public GetMobileDirection(LuaState luaState)
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
/* 29 */     return "getMobileDirection";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 38 */     return new LuaScriptParameterDescriptor[] {
/* 39 */       new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 50 */     int mobileId = getParamInt(0);
/*    */     
/* 52 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 53 */     if (mobile != null) {
/* 54 */       addReturnValue(mobile.getDirection().getIndex());
/*    */     } else {
/* 56 */       throw new LuaException("le mobile n'existe pas");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\GetMobileDirection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */