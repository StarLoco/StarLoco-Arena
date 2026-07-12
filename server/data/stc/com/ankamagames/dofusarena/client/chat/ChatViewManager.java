/*    */ package com.ankamagames.dofusarena.client.chat;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class ChatViewManager
/*    */ {
/* 17 */   private static ChatViewManager m_instance = new ChatViewManager();
/*    */   
/*    */   public static ChatViewManager getInstance() {
/* 20 */     return m_instance;
/*    */   }
/*    */   
/* 23 */   private int m_nextFreeViewId = 0;
/*    */   
/* 25 */   private List<ChatView> m_views = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChatView createView()
/*    */   {
/* 33 */     ChatView view = new ChatView(this.m_nextFreeViewId);
/* 34 */     this.m_nextFreeViewId += 1;
/* 35 */     this.m_views.add(view);
/* 36 */     return view;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChatView getView()
/*    */   {
/* 45 */     return (ChatView)this.m_views.get(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChatView getView(int index)
/*    */   {
/* 53 */     return (ChatView)this.m_views.get(index);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\ChatViewManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */