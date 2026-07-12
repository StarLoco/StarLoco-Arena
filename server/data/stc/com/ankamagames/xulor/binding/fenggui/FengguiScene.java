/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.ModalManager;
/*     */ import com.ankamagames.xulor.binding.XulorScene;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Container;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.RootContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.RootContainer.RootContainerLevel;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.StaticLayoutPlus;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XInstanciatedContainer;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeParser;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.GLAutoDrawable;
/*     */ import javax.media.opengl.GLCanvas;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IBasicContainer;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.render.jogl.EventHelper;
/*     */ import org.fenggui.render.jogl.JOGLBinding;
/*     */ import org.fenggui.util.Alphabet;
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
/*     */ public class FengguiScene
/*     */   extends XulorScene
/*     */ {
/*     */   public class JOGLNoResizeBinding
/*     */     extends JOGLBinding
/*     */   {
/*     */     public JOGLNoResizeBinding(GLCanvas canvas)
/*     */     {
/*  48 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected void fireDisplayResizedEvent(int width, int height) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  62 */   private Display m_display = null;
/*     */   
/*  64 */   private IElement m_rootContainer = null;
/*     */   
/*  66 */   private IElement m_onTopRootContainer = null;
/*     */   
/*  68 */   private IElement m_msgBoxRootContainer = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public FengguiScene()
/*     */   {
/*  74 */     Alphabet.setDefaultAlphabet(Alphabet.FRENCH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Display getDisplay()
/*     */   {
/*  81 */     return this.m_display;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement getBackRootContainer()
/*     */   {
/*  91 */     if ((this.m_rootContainer == null) && (this.m_display != null)) {
/*  92 */       createContainers();
/*     */     }
/*  94 */     return this.m_rootContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement getTopRootContainer()
/*     */   {
/* 104 */     if ((this.m_onTopRootContainer == null) && (this.m_display != null)) {
/* 105 */       createContainers();
/*     */     }
/* 107 */     return this.m_onTopRootContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement getMsgBoxRootContainer()
/*     */   {
/* 116 */     if ((this.m_msgBoxRootContainer == null) && (this.m_display != null)) {
/* 117 */       createContainers();
/*     */     }
/* 119 */     return this.m_msgBoxRootContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement getElementAt(int x, int y)
/*     */   {
/* 129 */     if (this.m_display != null) {
/* 130 */       IWidget widget = this.m_display.getWidget(x, y);
/* 131 */       if (widget == this.m_display) {
/* 132 */         return null;
/*     */       }
/* 134 */       IBasicContainer parent = widget.getParent();
/* 135 */       IElement ret = Xulor.getInstance().getEnvironment().getElementByWidget(widget);
/* 136 */       while ((ret == null) && (parent != null)) {
/* 137 */         ret = Xulor.getInstance().getEnvironment().getElementByWidget(parent);
/* 138 */         parent = parent.getParent();
/*     */       }
/*     */       
/* 141 */       return ret;
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getModalLevelAt(int x, int y)
/*     */   {
/* 154 */     IWidget widget = null;
/* 155 */     if (this.m_display != null) {
/* 156 */       widget = this.m_display.getWidget(x, y);
/*     */     }
/*     */     
/* 159 */     while ((widget != null) && (widget != this.m_display)) {
/* 160 */       IElement element = Xulor.getInstance().getEnvironment().getElementByWidget(widget);
/* 161 */       if ((element != null) && (element.getModalLevel() != -1)) {
/* 162 */         return element.getModalLevel();
/*     */       }
/* 164 */       widget = widget.getParent();
/*     */     }
/*     */     
/*     */ 
/* 168 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isElementAt(IElement element, int x, int y)
/*     */   {
/* 179 */     if ((element == null) || (!(element.getEncapsulatedObject() instanceof Widget))) {
/* 180 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 185 */     IWidget w = this.m_display.getWidget(x, y);
/* 186 */     if (w == null) {
/* 187 */       return false;
/*     */     }
/*     */     
/* 190 */     IBasicContainer c = w.getParent();
/* 191 */     while ((c != null) && (!(c instanceof RootContainer))) {
/* 192 */       c = c.getParent();
/*     */     }
/*     */     
/* 195 */     if (c == null) {
/* 196 */       return false;
/*     */     }
/*     */     
/* 199 */     RootContainer.RootContainerLevel level = ((RootContainer)c).getLevel();
/*     */     
/* 201 */     w = (IWidget)element.getEncapsulatedObject();
/* 202 */     if (w == null) {
/* 203 */       return false;
/*     */     }
/*     */     
/* 206 */     c = w.getParent();
/* 207 */     while ((c != null) && (!(c instanceof RootContainer))) {
/* 208 */       c = c.getParent();
/*     */     }
/*     */     
/* 211 */     if (c == null) {
/* 212 */       return false;
/*     */     }
/*     */     
/* 215 */     return level.isGreaterOrEqualThan(((RootContainer)c).getLevel());
/*     */   }
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
/*     */ 
/*     */   public int getDisplayYFromY(int y)
/*     */   {
/* 233 */     if (this.m_display != null) {
/* 234 */       return this.m_display.getHeight() - y;
/*     */     }
/* 236 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void layout()
/*     */   {
/* 247 */     if (this.m_display != null) {
/* 248 */       this.m_display.layout();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void createContainers()
/*     */   {
/* 256 */     if (this.m_display != null)
/*     */     {
/* 258 */       this.m_display.removeAllWidgets();
/*     */       
/* 260 */       Container container = new RootContainer(RootContainer.RootContainerLevel.BOTTOM);
/* 261 */       container.setLayoutManager(new StaticLayoutPlus());
/* 262 */       container.setSize(this.m_display.getWidth(), this.m_display.getHeight());
/* 263 */       container.setXY(0, 0);
/* 264 */       this.m_display.addWidget(container);
/* 265 */       this.m_rootContainer = new XInstanciatedContainer(container);
/*     */       
/* 267 */       container = new RootContainer(RootContainer.RootContainerLevel.TOP);
/* 268 */       container.setLayoutManager(new StaticLayoutPlus());
/* 269 */       container.setSize(this.m_display.getWidth(), this.m_display.getHeight());
/* 270 */       container.setXY(0, 0);
/* 271 */       this.m_display.addWidget(container);
/* 272 */       this.m_onTopRootContainer = new XInstanciatedContainer(container);
/*     */       
/* 274 */       container = new RootContainer(RootContainer.RootContainerLevel.MSGBOX);
/* 275 */       container.setLayoutManager(new StaticLayoutPlus());
/* 276 */       container.setSize(this.m_display.getWidth(), this.m_display.getHeight());
/* 277 */       container.setXY(0, 0);
/* 278 */       this.m_display.addWidget(container);
/* 279 */       this.m_msgBoxRootContainer = new XInstanciatedContainer(container);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init(GLAutoDrawable glAutoDrawable)
/*     */   {
/* 291 */     GLCanvas canvas = (GLCanvas)glAutoDrawable;
/*     */     
/* 293 */     canvas.setFocusTraversalKeysEnabled(false);
/*     */     
/* 295 */     this.m_display = new Display(new JOGLNoResizeBinding(canvas));
/*     */     
/* 297 */     FengguiBinding.getInstance().loadCursors(Xulor.getInstance().getThemeParser().getCursors());
/*     */     
/* 299 */     super.init(glAutoDrawable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void cleanUp()
/*     */   {
/* 306 */     if (this.m_display != null) {
/* 307 */       this.m_display.removeAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void display(GL gl)
/*     */   {
/* 317 */     this.m_display.display();
/* 318 */     super.display(gl);
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
/* 329 */     super.setFrustumSize(frustumWidth, frustumHeight);
/* 330 */     if (this.m_display != null) {
/* 331 */       this.m_display.setSize((int)frustumWidth, (int)frustumHeight);
/* 332 */       this.m_display.layout();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseDragged(MouseEvent mouseEvent)
/*     */   {
/* 342 */     if (this.m_display != null) {
/* 343 */       return this.m_display.fireMouseDraggedEvent(mouseEvent.getX(), this.m_display.getHeight() - mouseEvent.getY(), EventHelper.getMouseButton(mouseEvent));
/*     */     }
/* 345 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseEntered(MouseEvent mouseEvent)
/*     */   {
/* 354 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseExited(MouseEvent mouseEvent)
/*     */   {
/* 363 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseMoved(MouseEvent mouseEvent)
/*     */   {
/* 372 */     boolean displayRet = false;
/* 373 */     if ((super.mouseMoved(mouseEvent)) && 
/* 374 */       (this.m_display != null)) {
/* 375 */       displayRet = this.m_display.fireMouseMovedEvent(mouseEvent.getX(), this.m_display.getHeight() - mouseEvent.getY());
/*     */     }
/*     */     
/* 378 */     return (!ModalManager.getInstance().isEmpty()) || (displayRet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mousePressed(MouseEvent mouseEvent)
/*     */   {
/* 387 */     boolean displayRet = false;
/* 388 */     if ((super.mousePressed(mouseEvent)) && 
/* 389 */       (this.m_display != null)) {
/* 390 */       displayRet = this.m_display.fireMousePressedEvent(mouseEvent.getX(), this.m_display.getHeight() - mouseEvent.getY(), EventHelper.getMouseButton(mouseEvent), mouseEvent.getClickCount());
/*     */     }
/*     */     
/* 393 */     return (!ModalManager.getInstance().isEmpty()) || (displayRet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mouseReleased(MouseEvent mouseEvent)
/*     */   {
/* 402 */     boolean displayRet = false;
/* 403 */     if (this.m_display != null) {
/* 404 */       displayRet = this.m_display.fireMouseReleasedEvent(mouseEvent.getX(), this.m_display.getHeight() - mouseEvent.getY(), EventHelper.getMouseButton(mouseEvent), mouseEvent.getClickCount());
/*     */     }
/* 406 */     return (!ModalManager.getInstance().isEmpty()) || (displayRet);
/*     */   }
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
/*     */   public boolean mouseWheelMoved(MouseWheelEvent mouseEvent)
/*     */   {
/* 422 */     if (this.m_display != null) {
/* 423 */       return this.m_display.fireMouseWheel(mouseEvent.getX(), this.m_display.getHeight() - mouseEvent.getY(), mouseEvent.getWheelRotation() < 0, Math.abs(mouseEvent.getWheelRotation()));
/*     */     }
/* 425 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean keyPressed(KeyEvent keyEvent)
/*     */   {
/* 434 */     if (this.m_display != null) {
/* 435 */       return this.m_display.fireKeyPressedEvent(keyEvent.getKeyChar(), EventHelper.getKeyPressed(keyEvent), keyEvent.getModifiersEx());
/*     */     }
/* 437 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean keyReleased(KeyEvent keyEvent)
/*     */   {
/* 446 */     if (this.m_display != null) {
/* 447 */       return this.m_display.fireKeyReleasedEvent(keyEvent.getKeyChar(), EventHelper.getKeyPressed(keyEvent), keyEvent.getModifiersEx());
/*     */     }
/* 449 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean keyTyped(KeyEvent keyEvent)
/*     */   {
/* 458 */     if (this.m_display != null) {
/* 459 */       return this.m_display.fireKeyTypedEvent(keyEvent.getKeyChar(), keyEvent.getModifiersEx());
/*     */     }
/* 461 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 471 */     return "FengguiScene";
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiScene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */