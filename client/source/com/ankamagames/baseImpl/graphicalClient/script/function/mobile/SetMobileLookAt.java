/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*    */ import com.ankamagames.framework.kernel.core.maths.Vector3i;
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
/*    */ public class SetMobileLookAt
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileLookAt(LuaState luaState) {
/* 26 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 35 */     return "setMobileLookAt";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 44 */     return new LuaScriptParameterDescriptor[] {
/* 45 */         new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 46 */         new LuaScriptParameterDescriptor("worldX", LuaScriptParameterType.INTEGER, false), 
/* 47 */         new LuaScriptParameterDescriptor("worldY", LuaScriptParameterType.INTEGER, false), 
/* 48 */         new LuaScriptParameterDescriptor("isHeightDirections", LuaScriptParameterType.BOOLEAN, true)
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 58 */     int mobileId = getParamInt(0);
/* 59 */     int worldX = getParamInt(1);
/* 60 */     int worldY = getParamInt(2);
/* 61 */     boolean isHeightDirections = (paramCount >= 4) ? getParamBool(3) : true;
/*    */     
/* 63 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 64 */     if (mobile != null) {
/* 65 */       int dx = worldX - mobile.getWorldCellX();
/* 66 */       int dy = worldY - mobile.getWorldCellY();
/*    */       
/* 68 */       if (dx != 0 || dy != 0) {
/* 69 */         Direction8 direction8 = isHeightDirections ? Vector3i.getDirection8FromVector(dx, dy) : Vector3i.getDirection4FromVector(dx, dy);
/* 70 */         mobile.setDirection(direction8);
/* 71 */         addReturnValue(direction8.getIndex());
/*    */       } else {
/*    */         
/* 74 */         addReturnValue(mobile.getDirection().getIndex());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileLookAt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */