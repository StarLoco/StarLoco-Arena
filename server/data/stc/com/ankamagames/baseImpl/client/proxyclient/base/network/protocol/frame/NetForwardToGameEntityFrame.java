/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.frame;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.core.ProxyClientEntity;
/*    */ import com.ankamagames.framework.kernel.FrameHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.events.MessageFrame;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NetForwardToGameEntityFrame
/*    */   implements MessageFrame
/*    */ {
/*    */   private ProxyClientEntity m_proxyClientEntity;
/*    */   
/*    */   public NetForwardToGameEntityFrame(ProxyClientEntity proxyClientEntity)
/*    */   {
/* 28 */     this.m_proxyClientEntity = proxyClientEntity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 38 */     return this.m_proxyClientEntity.onMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getId()
/*    */   {
/* 47 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */   
/*    */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded) {}
/*    */   
/*    */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\frame\NetForwardToGameEntityFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */