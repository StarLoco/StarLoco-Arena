/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.worldScene;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class UIWorldSceneMouseMovedMessage
/*    */   extends UIMessage
/*    */ {
/* 23 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*    */     public UIWorldSceneMouseMovedMessage makeObject() {
/* 25 */       return new UIWorldSceneMouseMovedMessage(null);
/*    */     }
/* 23 */   });
/*    */   
/*    */ 
/*    */ 
/*    */   private int m_mouseX;
/*    */   
/*    */ 
/*    */ 
/*    */   private int m_mouseY;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static UIWorldSceneMouseMovedMessage checkOut()
/*    */   {
/*    */     UIWorldSceneMouseMovedMessage msg;
/*    */     
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 44 */       UIWorldSceneMouseMovedMessage msg = (UIWorldSceneMouseMovedMessage)m_pool.borrowObject();
/* 45 */       msg.setPool(m_pool);
/*    */     } catch (Exception e) {
/* 47 */       msg = new UIWorldSceneMouseMovedMessage();
/* 48 */       m_logger.error("Erreur lors d'un checkOut sur un message de type UIWorldSceneMouseMovedMessage : " + e.getMessage());
/*    */     }
/* 50 */     return msg;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 60 */     return 30001;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setMouseX(int mouseX)
/*    */   {
/* 67 */     this.m_mouseX = mouseX;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setMouseY(int mouseY)
/*    */   {
/* 74 */     this.m_mouseY = mouseY;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getMouseX()
/*    */   {
/* 81 */     return this.m_mouseX;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getMouseY()
/*    */   {
/* 88 */     return this.m_mouseY;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\worldScene\UIWorldSceneMouseMovedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */