/*    */ package com.ankamagames.dofusarena.client.chat;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatMessage;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.pipe.ChatSimplePipe;
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.Adviser;
/*    */ import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.dofusarena.client.alea.adviser.DofusArenaBubble;
/*    */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatVicinityPipe
/*    */   extends ChatSimplePipe
/*    */ {
/* 23 */   public static HashMap<String, DofusArenaBubble> m_bubbles = new HashMap<String, DofusArenaBubble>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChatVicinityPipe(String internalName) {
/* 31 */     super(internalName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushMessage(ChatMessage message) {
/* 41 */     super.pushMessage(message);
/* 42 */     if (message != null) {
/* 43 */       String userName = message.getSourceName();
/* 44 */       if (userName != null) {
/*    */         
/* 46 */         if (m_bubbles.get(userName) != null) {
/* 47 */           DofusArenaBubble dofusArenaBubble = m_bubbles.get(userName);
/*    */           
/* 49 */           if (dofusArenaBubble != null) {
/* 50 */             dofusArenaBubble.setDuration(0);
/*    */           }
/*    */         } 
/*    */         
/* 54 */         DofusArenaBubble bubble = new DofusArenaBubble(message.getMessage());
/* 55 */         m_bubbles.put(userName, bubble);
/* 56 */         Mobile mobile = MobileManager.getInstance().getMobile(message.getSourceId());
/* 57 */         if (mobile != null) {
/* 58 */           bubble.setTarget((IsoWorldTarget)mobile);
/* 59 */           AdviserManager.getInstance().addAdviser((Adviser)bubble);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\ChatVicinityPipe.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */