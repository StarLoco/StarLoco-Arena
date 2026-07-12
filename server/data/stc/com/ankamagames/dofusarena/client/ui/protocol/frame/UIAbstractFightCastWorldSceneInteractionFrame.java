/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
/*     */ import com.ankamagames.baseImpl.graphics.alea.utils.WorldSceneInteractionUtils;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.RangeAndEffectDisplayer;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseMovedMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseReleasedMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ public abstract class UIAbstractFightCastWorldSceneInteractionFrame
/*     */   implements MessageFrame
/*     */ {
/*  36 */   private static Logger m_logger = Logger.getLogger(UIAbstractFightCastWorldSceneInteractionFrame.class);
/*     */   
/*  38 */   protected Fighter m_fighter = null;
/*  39 */   protected static Point3 m_lastTarget = null;
/*     */   protected static int m_mouseX;
/*     */   protected static int m_mouseY;
/*  42 */   protected RangeAndEffectDisplayer m_rangeDisplayer = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFighter(Fighter fighter)
/*     */   {
/*  48 */     this.m_fighter = fighter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract EffectContainer getEffectContainer();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void sendCastMessage(int paramInt1, int paramInt2, short paramShort);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/*  67 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void selectRange()
/*     */   {
/*  82 */     if (this.m_rangeDisplayer != null) {
/*  83 */       this.m_rangeDisplayer.clearRange();
/*     */     }
/*     */     
/*  86 */     String iconName = getCastMouseIcon();
/*  87 */     if (iconName != null) {
/*  88 */       showCastMouseIcon(iconName);
/*     */     } else {
/*  90 */       Xulor.getInstance().hideMouseImage();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String getCastMouseIcon();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/* 104 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */     case 30001: 
/* 108 */       if (this.m_fighter != null) {
/* 109 */         UIWorldSceneMouseMovedMessage msg = (UIWorldSceneMouseMovedMessage)message;
/*     */         
/* 111 */         m_mouseX = msg.getMouseX();
/* 112 */         m_mouseY = msg.getMouseY();
/*     */         
/* 114 */         Point3 target = getNearestPoint3(m_mouseX, m_mouseY);
/* 115 */         if ((target != null) && (!target.equals(m_lastTarget))) {
/* 116 */           m_lastTarget = target;
/* 117 */           refreshRangeDisplay();
/*     */         }
/*     */       }
/*     */       
/* 121 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 30000: 
/* 127 */       if (this.m_fighter != null) {
/* 128 */         UIWorldSceneMouseReleasedMessage msg = (UIWorldSceneMouseReleasedMessage)message;
/*     */         
/* 130 */         if (msg.getMouseButton() == 1)
/*     */         {
/* 132 */           boolean isValid = false;
/* 133 */           int castPositionX = 0;
/* 134 */           int castPositionY = 0;
/* 135 */           short castPositionZ = 0;
/*     */           
/* 137 */           m_mouseX = msg.getMouseX();
/* 138 */           m_mouseY = msg.getMouseY();
/*     */           
/* 140 */           Point3 target = getNearestPoint3(m_mouseX, m_mouseY);
/* 141 */           if ((target != null) && (target.equals(m_lastTarget)))
/*     */           {
/* 143 */             if (this.m_rangeDisplayer.rangeContains(target)) {
/* 144 */               castPositionX = target.getX();
/* 145 */               castPositionY = target.getY();
/* 146 */               castPositionZ = target.getZ();
/*     */               
/* 148 */               isValid = true;
/*     */             }
/*     */           }
/*     */           
/* 152 */           if (isValid) {
/* 153 */             sendCastMessage(castPositionX, castPositionY, castPositionZ);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 159 */         DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */         
/* 161 */         Xulor.getInstance().hideMouseImage();
/*     */       }
/*     */       
/*     */ 
/* 165 */       return false;
/*     */     }
/*     */     
/* 168 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 179 */     m_mouseX = UIFightMoveWorldSceneInteractionFrame.getInstance().getMouseX();
/* 180 */     m_mouseY = UIFightMoveWorldSceneInteractionFrame.getInstance().getMouseY();
/*     */     
/*     */ 
/* 183 */     UIFightMoveWorldSceneInteractionFrame.getInstance().clearPathSelection();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 194 */     if (!isAboutToBeRemoved)
/*     */     {
/* 196 */       this.m_rangeDisplayer.clearRange();
/*     */     }
/*     */     else
/*     */     {
/* 200 */       Xulor.getInstance().hideMouseImage();
/*     */     }
/*     */     
/* 203 */     UIFightMoveWorldSceneInteractionFrame.getInstance().setMouse(m_mouseX, m_mouseY);
/* 204 */     UIFightMoveWorldSceneInteractionFrame.getInstance().refreshPathSelection();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Point3 getNearestPoint3(int mouseX, int mouseY)
/*     */   {
/* 213 */     return WorldSceneInteractionUtils.getNearestPoint3(DofusArenaClientInstance.getInstance().getWorldScene(), mouseX, mouseY, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void showCastMouseIcon(String iconUrl)
/*     */   {
/* 221 */     if (iconUrl != null) {
/*     */       try {
/* 223 */         Xulor.getInstance().showMouseImage(new URL(iconUrl), 10, -30, Alignment.NORTH_WEST);
/*     */       } catch (MalformedURLException e) {
/* 225 */         m_logger.error("Erreur dans showCastMouseIcon :", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void refreshRangeDisplay() {
/* 231 */     if ((m_lastTarget != null) && (this.m_rangeDisplayer.rangeContains(m_lastTarget))) {
/* 232 */       this.m_rangeDisplayer.selectZoneEffect(getEffectContainer(), this.m_fighter, m_lastTarget, DofusArenaClientInstance.getInstance().getWorldScene());
/*     */     } else {
/* 234 */       this.m_rangeDisplayer.clearZoneEffect();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIAbstractFightCastWorldSceneInteractionFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */