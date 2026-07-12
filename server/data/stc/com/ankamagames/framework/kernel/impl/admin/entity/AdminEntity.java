/*    */ package com.ankamagames.framework.kernel.impl.admin.entity;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.FrameworkEntity;
/*    */ import com.ankamagames.framework.kernel.impl.admin.frames.server.LoginFrame;
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
/*    */ public class AdminEntity
/*    */   extends FrameworkEntity
/*    */ {
/*    */   private boolean m_authenticationRequested;
/*    */   
/*    */   public void onConnect()
/*    */   {
/* 22 */     pushFrame(new LoginFrame());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onDisconnect()
/*    */   {
/* 31 */     super.onDisconnect();
/* 32 */     removeAllFrames();
/*    */   }
/*    */   
/*    */   public void onCheckOut()
/*    */   {
/* 37 */     super.onCheckOut();
/*    */   }
/*    */   
/*    */   public void onCheckIn() {
/* 41 */     super.onCheckIn();
/* 42 */     this.m_authenticationRequested = false;
/*    */   }
/*    */   
/*    */   public boolean isAuthenticationRequested() {
/* 46 */     return this.m_authenticationRequested;
/*    */   }
/*    */   
/*    */   public void setAuthenticationRequested(boolean authenticationRequested) {
/* 50 */     this.m_authenticationRequested = authenticationRequested;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\entity\AdminEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */