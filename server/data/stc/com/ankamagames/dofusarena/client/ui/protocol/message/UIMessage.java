/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.ui.protocol.message.AbstractUIMessage;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
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
/*    */ 
/*    */ public class UIMessage
/*    */   extends AbstractUIMessage
/*    */ {
/*    */   public UIMessage()
/*    */   {
/* 25 */     super(DofusArenaGameEntity.getInstance());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onCheckOut()
/*    */   {
/* 35 */     setHandler(DofusArenaGameEntity.getInstance());
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\UIMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */