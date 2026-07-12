/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
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
/*    */ public class SetMobileAnimation
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public SetMobileAnimation(LuaState luaState) {
/* 25 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 34 */     return "setMobileAnimation";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 43 */     return new LuaScriptParameterDescriptor[] {
/* 44 */         new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 45 */         new LuaScriptParameterDescriptor("animationName", LuaScriptParameterType.STRING, false)
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 55 */     int mobileId = getParamInt(0);
/* 56 */     String animation = getParamString(1);
/*    */     
/* 58 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 59 */     if (mobile != null) {
/*    */ 
/*    */       
/* 62 */       mobile.setAnimation(animation);
/*    */ 
/*    */       
/* 65 */       int animDuration = 0;
/* 66 */       DisplayObject displayObject = mobile.getDisplayObject();
/* 67 */       if (displayObject != null) {
/* 68 */         animDuration = displayObject.getDescriptor().getTotalTime();
/*    */       }
/* 70 */       addReturnValue(animDuration);
/*    */     } else {
/*    */       
/* 73 */       m_logger.trace("le mobile " + mobileId + " n'existe plus");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\SetMobileAnimation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */