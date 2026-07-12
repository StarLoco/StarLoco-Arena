/*    */ package com.ankamagames.dofusarena.client.network.protocol.message.game.serverToClient.actor;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.dofusarena.client.core.game.actor.ActorHolder;
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
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
/*    */ public class ActorSpawnMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 24 */   private final ArrayList<ActorHolder> m_actorHolders = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 34 */     if (!checkMessageSize(rawDatas.length, 1, false)) {
/* 35 */       return false;
/*    */     }
/* 37 */     this.m_actorHolders.clear();
/* 38 */     ByteBuffer buffer = ByteBuffer.wrap(rawDatas);
/*    */     
/* 40 */     int charactersCount = buffer.getInt();
/* 41 */     for (int i = 0; i < charactersCount; i++)
/*    */     {
/* 43 */       byte type = buffer.get();
/* 44 */       switch (type)
/*    */       {
/*    */ 
/*    */       case 1: 
/* 48 */         Coach coach = new Coach();
/* 49 */         if (coach.unserialize(buffer, 11)) {
/* 50 */           this.m_actorHolders.add(coach);
/*    */         }
/*    */         
/* 53 */         break;
/*    */       
/*    */ 
/*    */ 
/*    */       case 2: 
/* 58 */         Fighter fighter = new Fighter();
/* 59 */         if (fighter.unserialize(buffer)) {
/* 60 */           this.m_actorHolders.add(fighter);
/*    */         }
/*    */         
/*    */         break;
/*    */       }
/*    */       
/*    */     }
/*    */     
/* 68 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 78 */     return 4096;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Iterable<ActorHolder> getActorHolders()
/*    */   {
/* 85 */     return this.m_actorHolders;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\network\protocol\message\game\serverToClient\actor\ActorSpawnMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */