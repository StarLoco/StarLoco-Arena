/*     */ package com.ankamagames.xulor.binding;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
/*     */ import com.ankamagames.framework.kernel.core.controllers.MouseController;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.MouseImageManager;
/*     */ import com.ankamagames.xulor.core.GLImpl.Tooltip;
/*     */ import com.ankamagames.xulor.core.WindowManager;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.media.opengl.GLAutoDrawable;
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
/*     */ public abstract class XulorScene
/*     */   extends Scene
/*     */   implements MouseController, KeyboardController
/*     */ {
/*  32 */   private final Tooltip m_tooltip = new Tooltip(Font.decode("---"));
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_mouseX;
/*     */   
/*     */ 
/*     */ 
/*     */   private int m_mouseY;
/*     */   
/*     */ 
/*  43 */   private List<XulorSceneEventListener> m_listeners = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public Tooltip getTooltip()
/*     */   {
/*  49 */     return this.m_tooltip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseX()
/*     */   {
/*  56 */     return this.m_mouseX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMouseY()
/*     */   {
/*  63 */     return this.m_mouseY;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract IElement getElementAt(int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */   public abstract short getModalLevelAt(int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */   public abstract boolean isElementAt(IElement paramIElement, int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */   public abstract int getDisplayYFromY(int paramInt);
/*     */   
/*     */ 
/*     */   public void addEventListener(XulorSceneEventListener listener)
/*     */   {
/*  81 */     if (!this.m_listeners.contains(listener)) {
/*  82 */       this.m_listeners.add(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeEventListener(XulorSceneEventListener listener)
/*     */   {
/*  92 */     if (this.m_listeners.contains(listener)) {
/*  93 */       this.m_listeners.remove(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init(GLAutoDrawable glAutoDrawable)
/*     */   {
/* 103 */     super.init(glAutoDrawable);
/* 104 */     addChild(this.m_tooltip);
/* 105 */     fireInitializationComplete();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFrustumSize(float frustumWidth, float frustumHeight)
/*     */   {
/* 116 */     super.setFrustumSize(frustumWidth, frustumHeight);
/* 117 */     this.m_tooltip.setDrawZoneBounds(-frustumWidth / 2.0F, -frustumHeight / 2.0F, frustumWidth, frustumHeight);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(long realTime, int frameCount)
/*     */   {
/* 127 */     super.process(realTime, frameCount);
/* 128 */     fireProcess(realTime, frameCount);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseMoved(MouseEvent mouseEvent)
/*     */   {
/* 137 */     this.m_mouseX = mouseEvent.getX();
/* 138 */     this.m_mouseY = mouseEvent.getY();
/* 139 */     int x = this.m_mouseX;
/* 140 */     int y = getDisplayYFromY(mouseEvent.getY());
/* 141 */     MouseImageManager.getInstance().setXY(x, y);
/* 142 */     return ModalManager.getInstance().sendEventToDisplay(x, y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseClicked(MouseEvent mouseEvent)
/*     */   {
/* 151 */     Xulor.getInstance().hidePopupMenu();
/* 152 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mousePressed(MouseEvent mouseEvent)
/*     */   {
/* 161 */     boolean sendToDisplay = ModalManager.getInstance().sendEventToDisplay(mouseEvent.getX(), getDisplayYFromY(mouseEvent.getY()));
/* 162 */     if (sendToDisplay) {
/* 163 */       WindowManager.getInstance().mousePressed(mouseEvent.getX(), getDisplayYFromY(mouseEvent.getY()));
/*     */     }
/* 165 */     return sendToDisplay;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseReleased(MouseEvent mouseEvent)
/*     */   {
/* 174 */     return ModalManager.getInstance().sendEventToDisplay(mouseEvent.getX(), getDisplayYFromY(mouseEvent.getY()));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void fireInitializationComplete()
/*     */   {
/*     */     Object[] arrayOfObject;
/* 181 */     int j = (arrayOfObject = this.m_listeners.toArray()).length; for (int i = 0; i < j; i++) { Object listener = arrayOfObject[i];
/* 182 */       ((XulorSceneEventListener)listener).onXulorSceneInitializationComplete(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void fireProcess(long realTime, int frameCount)
/*     */   {
/*     */     Object[] arrayOfObject;
/* 190 */     int j = (arrayOfObject = this.m_listeners.toArray()).length; for (int i = 0; i < j; i++) { Object listener = arrayOfObject[i];
/* 191 */       ((XulorSceneEventListener)listener).onProcess(this, realTime, frameCount);
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract IElement getBackRootContainer();
/*     */   
/*     */   public abstract IElement getTopRootContainer();
/*     */   
/*     */   public abstract IElement getMsgBoxRootContainer();
/*     */   
/*     */   public abstract void layout();
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\XulorScene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */