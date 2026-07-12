/*     */ package com.ankamagames.dofusarena.client.alea;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*     */ import com.ankamagames.baseImpl.graphicalClient.alea.GameWorldScene;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.MobileFunctionsLibrary;
/*     */ import com.ankamagames.baseImpl.graphicalClient.script.SoundFunctionsLibrary;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.StartPointManager;
/*     */ import com.ankamagames.dofusarena.client.alea.highlightingCells.StaticEffectAreaDisplayer;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseMovedMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseReleasedMessage;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
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
/*     */ public class DofusArenaWorldScene
/*     */   extends GameWorldScene
/*     */ {
/*     */   private boolean m_dispatchMouseMovedMessage = false;
/*     */   
/*     */   public DofusArenaWorldScene(AbstractGameClientInstance gameClientInstance) {
/*  39 */     super(gameClientInstance);
/*  40 */     setAnimatedObjectActionsFunctionLibraries(new JavaFunctionsLibrary[] { (JavaFunctionsLibrary)SoundFunctionsLibrary.getInstance(), (JavaFunctionsLibrary)MobileFunctionsLibrary.getInstance() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDispatchMouseMovedMessage() {
/*  47 */     return this.m_dispatchMouseMovedMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDispatchMouseMovedMessage(boolean dispatchMouseMovedMessage) {
/*  54 */     this.m_dispatchMouseMovedMessage = dispatchMouseMovedMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clean(boolean forceUpdate) {
/*  64 */     super.clean(forceUpdate);
/*     */ 
/*     */     
/*  67 */     StartPointManager.getInstance().desactivate();
/*  68 */     StaticEffectAreaDisplayer.getInstance().deactivate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(KeyEvent keyEvent) {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyReleased(KeyEvent keyEvent) {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyTyped(KeyEvent keyEvent) {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(MouseEvent mouseEvent) {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(MouseEvent mouseEvent) {
/* 118 */     return mouseMoved(mouseEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseEntered(MouseEvent mouseEvent) {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseMoved(MouseEvent mouseEvent) {
/* 137 */     super.mouseMoved(mouseEvent);
/* 138 */     selectMobilesUnderMousePoint(mouseEvent.getX(), mouseEvent.getY());
/*     */     
/* 140 */     if (this.m_dispatchMouseMovedMessage) {
/* 141 */       UIWorldSceneMouseMovedMessage message = UIWorldSceneMouseMovedMessage.checkOut();
/* 142 */       message.setMouseX(mouseEvent.getX());
/* 143 */       message.setMouseY(mouseEvent.getY());
/* 144 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } 
/*     */     
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseExited(MouseEvent mouseEvent) {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(MouseEvent mouseEvent) {
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(MouseEvent mouseEvent) {
/* 177 */     UIWorldSceneMouseReleasedMessage message = UIWorldSceneMouseReleasedMessage.checkOut();
/* 178 */     message.setMouseButton(mouseEvent.getButton());
/* 179 */     message.setMouseX(mouseEvent.getX());
/* 180 */     message.setMouseY(mouseEvent.getY());
/* 181 */     Worker.getInstance().pushMessage((Message)message);
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseWheelMoved(MouseWheelEvent mouseEvent) {
/* 192 */     setDesiredZoomFactor(getDesiredZoomFactor() - (mouseEvent.getWheelRotation() * 0.1F));
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSpecialEffectToMesh(int gfxId, Mesh2D mesh) {
/*     */     try {
/* 205 */       if (DofusArenaConfiguration.getInstance().getBoolean("activateMapVisualEffect"))
/*     */       {
/* 207 */         switch (gfxId) {
/*     */           case 461:
/*     */           case 462:
/*     */           case 463:
/* 211 */             mesh.setEffect(EffectManager.getInstance().getEffect("sea"), false);
/*     */             return;
/*     */         } 
/* 214 */         mesh.setEffect(null, false);
/*     */       }
/*     */     
/*     */     }
/* 218 */     catch (PropertyException e) {
/* 219 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\DofusArenaWorldScene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */