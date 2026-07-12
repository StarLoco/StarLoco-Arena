/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
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
/*    */ public class UIFightMessage
/*    */   extends UIMessage
/*    */ {
/*    */   private long m_fightId;
/*    */   
/*    */   public long getFightId()
/*    */   {
/* 22 */     return this.m_fightId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setFightId(long fightId)
/*    */   {
/* 29 */     this.m_fightId = fightId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\teamManagement\UIFightMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */