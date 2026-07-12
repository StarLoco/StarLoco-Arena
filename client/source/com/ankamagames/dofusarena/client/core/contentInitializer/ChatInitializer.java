/*    */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatPipe;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.pipe.ChatPrivatePipe;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.pipe.ChatSimplePipe;
/*    */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*    */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*    */ import com.ankamagames.dofusarena.client.chat.ChatVicinityPipe;
/*    */ import com.ankamagames.dofusarena.client.chat.ChatView;
/*    */ import com.ankamagames.dofusarena.client.chat.ChatViewManager;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.xulor.Xulor;
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
/*    */ public class ChatInitializer
/*    */   implements ContentInitializer
/*    */ {
/* 28 */   private static ChatInitializer m_instance = new ChatInitializer();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChatInitializer getInstance() {
/* 34 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return DofusArenaTranslator.getInstance().getString("contentLoader.chat", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(AbstractGameClientInstance clientInstance) throws Exception {
/* 53 */     ChatVicinityPipe chatVicinityPipe = new ChatVicinityPipe("vicinityPipe");
/* 54 */     ChatPrivatePipe chatPrivatePipe = new ChatPrivatePipe("privatePipe");
/* 55 */     ChatSimplePipe chatSimplePipe1 = new ChatSimplePipe("gameErrorPipe");
/* 56 */     ChatSimplePipe chatSimplePipe2 = new ChatSimplePipe("fightInformationPipe");
/* 57 */     ChatSimplePipe chatSimplePipe3 = new ChatSimplePipe("gameInformationPipe");
/*    */ 
/*    */     
/* 60 */     ChatManager chatManager = ChatManager.getInstance();
/* 61 */     chatManager.addChatPipe(1, (ChatPipe)chatVicinityPipe);
/* 62 */     chatManager.addChatPipe(2, (ChatPipe)chatPrivatePipe);
/* 63 */     chatManager.addChatPipe(4, (ChatPipe)chatSimplePipe1);
/* 64 */     chatManager.addChatPipe(6, (ChatPipe)chatSimplePipe2);
/* 65 */     chatManager.addChatPipe(5, (ChatPipe)chatSimplePipe3);
/*    */ 
/*    */     
/* 68 */     ChatView defaultView = ChatViewManager.getInstance().createView();
/* 69 */     defaultView.registerPipe((ChatPipe)chatVicinityPipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.vicinity", new Object[0]));
/* 70 */     defaultView.registerPipe((ChatPipe)chatPrivatePipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.private", new Object[0]));
/* 71 */     defaultView.registerPipe((ChatPipe)chatSimplePipe1, DofusArenaTranslator.getInstance().getString("chat.pipeName.gameError", new Object[0]));
/* 72 */     defaultView.registerPipe((ChatPipe)chatSimplePipe2, DofusArenaTranslator.getInstance().getString("chat.pipeName.fightInformation", new Object[0]));
/* 73 */     defaultView.registerPipe((ChatPipe)chatSimplePipe3, DofusArenaTranslator.getInstance().getString("chat.pipeName.gameInformation", new Object[0]));
/*    */     
/* 75 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.pipes.list", defaultView.getWrappedPipes().toArray());
/*    */     
/* 77 */     clientInstance.fireContentInitializerDone(this);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\ChatInitializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */