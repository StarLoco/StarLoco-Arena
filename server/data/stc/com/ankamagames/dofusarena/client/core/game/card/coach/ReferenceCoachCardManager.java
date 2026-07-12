/*    */ package com.ankamagames.dofusarena.client.core.game.card.coach;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.card.AbstractReferenceCoachCardManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceCoachCardManager
/*    */   extends AbstractReferenceCoachCardManager<ReferenceCoachCard>
/*    */ {
/* 16 */   private static final ReferenceCoachCardManager m_instance = new ReferenceCoachCardManager();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ReferenceCoachCardManager getInstance()
/*    */   {
/* 28 */     return m_instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\card\coach\ReferenceCoachCardManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */