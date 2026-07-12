/*    */ package com.ankamagames.baseImpl.graphicalClient.network;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.frame.NetForwardToGameEntityFrame;
/*    */ import com.ankamagames.baseImpl.graphicalClient.core.GameEntity;
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
/*    */ public class GameNetworkEntity
/*    */   extends NetworkEntity
/*    */ {
/*    */   private GameEntity m_gameEntity;
/*    */   
/*    */   public GameNetworkEntity(GameEntity gameEntity)
/*    */   {
/* 27 */     this.m_gameEntity = gameEntity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onConnect()
/*    */   {
/* 38 */     this.m_gameEntity.setNetworkEntity(this);
/*    */     
/*    */ 
/* 41 */     pushFrame(new NetForwardToGameEntityFrame(this.m_gameEntity));
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\network\GameNetworkEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */