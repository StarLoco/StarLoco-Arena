/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.ui.protocol.message.AbstractUIMessage;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
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
/*    */   public UIMessage() {
/* 25 */     super((MessageHandler)DofusArenaGameEntity.getInstance());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckOut() {
/* 35 */     setHandler((MessageHandler)DofusArenaGameEntity.getInstance());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\UIMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */