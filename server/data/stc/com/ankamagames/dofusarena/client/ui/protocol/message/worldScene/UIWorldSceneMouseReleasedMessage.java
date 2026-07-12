/*     */ package com.ankamagames.dofusarena.client.ui.protocol.message.worldScene;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UIWorldSceneMouseReleasedMessage
/*     */   extends UIMessage
/*     */ {
/*  23 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*     */     public UIWorldSceneMouseReleasedMessage makeObject() {
/*  25 */       return new UIWorldSceneMouseReleasedMessage(null);
/*     */     }
/*  23 */   });
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_mouseButton;
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_mouseX;
/*     */   
/*     */ 
/*     */   private int m_mouseY;
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIWorldSceneMouseReleasedMessage checkOut()
/*     */   {
/*     */     UIWorldSceneMouseReleasedMessage msg;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  45 */       UIWorldSceneMouseReleasedMessage msg = (UIWorldSceneMouseReleasedMessage)m_pool.borrowObject();
/*  46 */       msg.setPool(m_pool);
/*     */     } catch (Exception e) {
/*  48 */       msg = new UIWorldSceneMouseReleasedMessage();
/*  49 */       m_logger.error("Erreur lors d'un checkOut sur un message de type UIWorldSceneMouseReleasedMessage : " + e.getMessage());
/*     */     }
/*  51 */     return msg;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  61 */     return 30000;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMouseButton(int mouseButton)
/*     */   {
/*  68 */     this.m_mouseButton = mouseButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMouseX(int mouseX)
/*     */   {
/*  75 */     this.m_mouseX = mouseX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMouseY(int mouseY)
/*     */   {
/*  82 */     this.m_mouseY = mouseY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseButton()
/*     */   {
/*  89 */     return this.m_mouseButton;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseX()
/*     */   {
/*  96 */     return this.m_mouseX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseY()
/*     */   {
/* 103 */     return this.m_mouseY;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\message\worldScene\UIWorldSceneMouseReleasedMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */