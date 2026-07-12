/*    */ package com.ankamagames.baseImpl.graphicalClient.script;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.GetMobileDirection;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.GetMobilePosition;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.MoveMobile;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileAnimation;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileAnimationSpeed;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileDirection;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileLookAt;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileMovementStyle;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobilePosition;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileToStaticAnimation;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.mobile.SetMobileVisible;
/*    */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*    */ 
/*    */ public class MobileFunctionsLibrary extends JavaFunctionsLibrary {
/* 17 */   private static MobileFunctionsLibrary m_instance = new MobileFunctionsLibrary();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MobileFunctionsLibrary() {
/* 23 */     super("Mobile");
/* 24 */     registerFunctionClass(MoveMobile.class);
/* 25 */     registerFunctionClass(SetMobileAnimation.class);
/* 26 */     registerFunctionClass(SetMobileAnimationSpeed.class);
/* 27 */     registerFunctionClass(SetMobileToStaticAnimation.class);
/* 28 */     registerFunctionClass(GetMobileDirection.class);
/* 29 */     registerFunctionClass(SetMobileDirection.class);
/* 30 */     registerFunctionClass(SetMobileLookAt.class);
/* 31 */     registerFunctionClass(GetMobilePosition.class);
/* 32 */     registerFunctionClass(SetMobilePosition.class);
/* 33 */     registerFunctionClass(SetMobileVisible.class);
/* 34 */     registerFunctionClass(SetMobileMovementStyle.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MobileFunctionsLibrary getInstance() {
/* 41 */     return m_instance;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\MobileFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */