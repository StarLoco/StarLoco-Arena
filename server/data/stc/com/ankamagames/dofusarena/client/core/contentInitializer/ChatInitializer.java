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
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.property.PropertiesProvider;
/*    */ import java.util.List;
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
/*    */   public static ChatInitializer getInstance()
/*    */   {
/* 34 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 43 */     return DofusArenaTranslator.getInstance().getString("contentLoader.chat", new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void init(AbstractGameClientInstance clientInstance)
/*    */     throws Exception
/*    */   {
/* 53 */     ChatPipe vicinityPipe = new ChatVicinityPipe("vicinityPipe");
/* 54 */     ChatPipe privatePipe = new ChatPrivatePipe("privatePipe");
/* 55 */     ChatPipe gameErrorPipe = new ChatSimplePipe("gameErrorPipe");
/* 56 */     ChatPipe chatInformationPipe = new ChatSimplePipe("fightInformationPipe");
/* 57 */     ChatPipe gameInformationPipe = new ChatSimplePipe("gameInformationPipe");
/*    */     
/*    */ 
/* 60 */     ChatManager chatManager = ChatManager.getInstance();
/* 61 */     chatManager.addChatPipe(1, vicinityPipe);
/* 62 */     chatManager.addChatPipe(2, privatePipe);
/* 63 */     chatManager.addChatPipe(4, gameErrorPipe);
/* 64 */     chatManager.addChatPipe(6, chatInformationPipe);
/* 65 */     chatManager.addChatPipe(5, gameInformationPipe);
/*    */     
/*    */ 
/* 68 */     ChatView defaultView = ChatViewManager.getInstance().createView();
/* 69 */     defaultView.registerPipe(vicinityPipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.vicinity", new Object[0]));
/* 70 */     defaultView.registerPipe(privatePipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.private", new Object[0]));
/* 71 */     defaultView.registerPipe(gameErrorPipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.gameError", new Object[0]));
/* 72 */     defaultView.registerPipe(chatInformationPipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.fightInformation", new Object[0]));
/* 73 */     defaultView.registerPipe(gameInformationPipe, DofusArenaTranslator.getInstance().getString("chat.pipeName.gameInformation", new Object[0]));
/*    */     
/* 75 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("chat.pipes.list", defaultView.getWrappedPipes().toArray());
/*    */     
/* 77 */     clientInstance.fireContentInitializerDone(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\ChatInitializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */