/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class EquipedCoachCardProvider
/*    */   implements InventoryContentProvider<CoachCard>
/*    */ {
/* 18 */   private static final EquipedCoachCardProvider m_instance = new EquipedCoachCardProvider();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static EquipedCoachCardProvider getInstance()
/*    */   {
/* 30 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EquipedCoachCard unserializeContent(ByteBuffer buf)
/*    */   {
/* 39 */     EquipedCoachCard card = new EquipedCoachCard();
/* 40 */     card.unserialize(buf);
/* 41 */     return card;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\EquipedCoachCardProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */