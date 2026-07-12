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
/*    */ public class CoachCardProvider
/*    */   implements InventoryContentProvider<CoachCard>
/*    */ {
/* 18 */   private static final CoachCardProvider m_instance = new CoachCardProvider();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static CoachCardProvider getInstance()
/*    */   {
/* 30 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CoachCard unserializeContent(ByteBuffer buf)
/*    */   {
/* 39 */     CoachCard card = new CoachCard();
/* 40 */     if (card.unserialize(buf)) {
/* 41 */       return card;
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\CoachCardProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */