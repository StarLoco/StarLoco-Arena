/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IActivationListener;
/*     */ import org.fenggui.event.IFocusListener;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.IKeyReleasedListener;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.mouse.IMouseDraggedListener;
/*     */ import org.fenggui.event.mouse.IMouseEnteredListener;
/*     */ import org.fenggui.event.mouse.IMouseExitedListener;
/*     */ import org.fenggui.event.mouse.IMouseMovedListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.IMouseReleasedListener;
/*     */ import org.fenggui.event.mouse.IMouseWheelListener;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MouseMovedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
/*     */ import org.fenggui.event.mouse.MouseWheelEvent;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Dimension;
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
/*     */ public class Container
/*     */   extends Container
/*     */   implements NonBlocking
/*     */ {
/*  48 */   private Dimension wishedSize = null;
/*     */   private boolean m_visible = true;
/*     */   private boolean m_nonBlocking = true;
/*  51 */   private ArrayList<IWidget> m_notUsedInLayout = new ArrayList<IWidget>();
/*     */   
/*     */   private boolean m_enabled = true;
/*  54 */   private ArrayList<IActivationListener> activationHook = new ArrayList<IActivationListener>(0);
/*  55 */   private ArrayList<IMouseEnteredListener> mouseEnteredHook = new ArrayList<IMouseEnteredListener>(0);
/*  56 */   private ArrayList<IMouseMovedListener> mouseMovedHook = new ArrayList<IMouseMovedListener>(0);
/*  57 */   private ArrayList<IMouseExitedListener> mouseExitedHook = new ArrayList<IMouseExitedListener>(0);
/*  58 */   private ArrayList<IMousePressedListener> mousePressedHook = new ArrayList<IMousePressedListener>(0);
/*  59 */   private ArrayList<IMouseReleasedListener> mouseReleasedHook = new ArrayList<IMouseReleasedListener>(0);
/*  60 */   private ArrayList<IFocusListener> focusGainedHook = new ArrayList<IFocusListener>(0);
/*  61 */   private ArrayList<IMouseDraggedListener> mouseDraggedHook = new ArrayList<IMouseDraggedListener>(0);
/*  62 */   private ArrayList<IMouseWheelListener> mouseWheeledHook = new ArrayList<IMouseWheelListener>(0);
/*  63 */   private ArrayList<IKeyPressedListener> keyPressedHook = new ArrayList<IKeyPressedListener>(0);
/*  64 */   private ArrayList<IKeyReleasedListener> keyReleasedHook = new ArrayList<IKeyReleasedListener>(0);
/*     */ 
/*     */   
/*     */   public Container() {
/*  68 */     setAppearance(new ContainerAppearance(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public class ContainerAppearance
/*     */     extends Container.ContainerAppearance
/*     */   {
/*     */     private Container container;
/*     */     
/*     */     public ContainerAppearance(Container w) {
/*  78 */       super(Container.this, w);
/*  79 */       this.container = w;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/*  87 */       if (Container.this.m_visible) {
/*  88 */         IOpenGL opengl = g.getOpenGL();
/*     */         
/*  90 */         List<IWidget> notifyList = this.container.getContent();
/*     */         
/*  92 */         for (int i = 0; i < notifyList.size(); i++) {
/*     */           
/*  94 */           IWidget c = notifyList.get(i);
/*  95 */           if (!Container.this.m_notUsedInLayout.contains(c))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 100 */             if (c.getX() <= this.container.getWidth() && c.getY() <= this.container.getHeight()) {
/*     */               
/* 102 */               if (!(this.container.getParent() instanceof org.fenggui.ScrollContainer)) {
/*     */                 
/* 104 */                 boolean valid = this.container.clipWidgetInContainer(g, c);
/*     */                 
/* 106 */                 if (!valid)
/*     */                   return; 
/*     */               } 
/* 109 */               opengl.pushMatrix();
/* 110 */               g.translate(c.getX(), c.getY());
/*     */               
/* 112 */               c.paint(g);
/*     */               
/* 114 */               g.translate(-c.getX(), -c.getY());
/* 115 */               opengl.popMatrix();
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean clipWidgetInContainer(Graphics g, IWidget c) {
/* 124 */     int startX = (c.getX() < 0) ? 0 : c.getX();
/* 125 */     int startY = (c.getY() < 0) ? 0 : c.getY();
/*     */     
/* 127 */     Binding b = Binding.getInstance();
/*     */     
/* 129 */     if (startX >= b.getCanvasWidth() || startY >= b.getCanvasHeight())
/*     */     {
/* 131 */       return false;
/*     */     }
/*     */     
/* 134 */     int cWidth = c.getSize().getWidth();
/* 135 */     int cHeight = c.getSize().getHeight();
/*     */     
/* 137 */     g.setClipSpace(
/* 138 */         startX, 
/* 139 */         startY, 
/* 140 */         (c.getX() + cWidth > getWidth()) ? (getWidth() - startX) : cWidth, 
/* 141 */         (c.getY() + cHeight > getHeight()) ? (getHeight() - startY) : cHeight);
/*     */     
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   public void setNonBlocking(boolean nonBlocking) {
/* 147 */     this.m_nonBlocking = nonBlocking;
/*     */   }
/*     */   
/*     */   public boolean isNonBlocking() {
/* 151 */     return this.m_nonBlocking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/*     */     IWidget iWidget1;
/* 159 */     if (!this.m_visible) {
/* 160 */       return null;
/*     */     }
/*     */     
/* 163 */     if (!getAppearance().insideMargin(x, y)) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     IWidget ret = null;
/* 168 */     Container container = this.m_nonBlocking ? null : this;
/*     */     
/* 170 */     x -= getAppearance().getLeftMargins();
/* 171 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 173 */     for (IWidget w : this.notifyList) {
/* 174 */       if (this.m_notUsedInLayout.contains(w)) {
/*     */         continue;
/*     */       }
/* 177 */       ret = w.getWidget(x - w.getX(), y - w.getY());
/*     */       
/* 179 */       if (ret != null) {
/* 180 */         iWidget1 = ret;
/*     */       }
/*     */     } 
/*     */     
/* 184 */     return iWidget1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getWishedSize() {
/* 191 */     return this.wishedSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWishedSize(Dimension wishedSize) {
/* 198 */     this.wishedSize = wishedSize;
/* 199 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 206 */     return this.m_visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsedInLayout(IWidget widget, boolean usedInLayout) {
/* 215 */     if (usedInLayout) {
/* 216 */       this.m_notUsedInLayout.remove(widget);
/*     */     }
/* 218 */     else if (this.notifyList.contains(widget) && !this.m_notUsedInLayout.contains(widget)) {
/* 219 */       this.m_notUsedInLayout.add(widget);
/*     */     } 
/*     */     
/* 222 */     layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 229 */     this.m_visible = visible;
/*     */   }
/*     */   
/*     */   public void updateMinSize() {
/* 233 */     Dimension min = getAppearance().getMinSizeHint();
/* 234 */     int height = min.getHeight();
/* 235 */     int width = min.getWidth();
/* 236 */     if (this.wishedSize != null) {
/* 237 */       height = Math.max(height, this.wishedSize.getHeight());
/* 238 */       width = Math.max(width, this.wishedSize.getWidth());
/*     */     } 
/* 240 */     setMinSize(width, height);
/*     */     
/* 242 */     if (getParent() != null) getParent().updateMinSize(); 
/*     */   }
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 246 */     super.removedFromWidgetTree();
/* 247 */     this.notifyList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 253 */     this.m_enabled = enabled;
/*     */     
/* 255 */     ActivationEvent e = new ActivationEvent((IWidget)this, enabled);
/*     */     
/* 257 */     for (IActivationListener l : this.activationHook)
/*     */     {
/* 259 */       l.widgetActivationChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addKeyReleasedListener(IKeyReleasedListener l) {
/* 265 */     this.keyReleasedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeKeyReleasedListener(IKeyReleasedListener l) {
/* 270 */     this.keyReleasedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addKeyPressedListener(IKeyPressedListener l) {
/* 275 */     this.keyPressedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeKeyPressedListener(IKeyPressedListener l) {
/* 280 */     this.keyPressedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseDraggedListener(IMouseDraggedListener l) {
/* 285 */     this.mouseDraggedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseDraggedListener(IMouseDraggedListener l) {
/* 290 */     this.mouseDraggedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseMovedListener(IMouseMovedListener l) {
/* 295 */     this.mouseMovedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseMovedListener(IMouseMovedListener l) {
/* 300 */     this.mouseMovedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseReleasedListener(IMouseReleasedListener l) {
/* 305 */     this.mouseReleasedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseReleasedListener(IMouseReleasedListener l) {
/* 310 */     this.mouseReleasedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMousePressedListener(IMousePressedListener l) {
/* 315 */     this.mousePressedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMousePressedListener(IMousePressedListener l) {
/* 320 */     this.mousePressedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseExitedListener(IMouseExitedListener l) {
/* 325 */     this.mouseExitedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseExitedListener(IMouseExitedListener l) {
/* 330 */     this.mouseExitedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseEnteredListener(IMouseEnteredListener l) {
/* 335 */     this.mouseEnteredHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseEnteredListener(IMouseEnteredListener l) {
/* 340 */     this.mouseEnteredHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFocusListener(IFocusListener l) {
/* 345 */     this.focusGainedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFocusListener(IFocusListener l) {
/* 350 */     this.focusGainedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMouseWheeledListener(IMouseWheelListener l) {
/* 356 */     this.mouseWheeledHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseWheeledListener(IMouseWheelListener l) {
/* 361 */     this.mouseWheeledHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 366 */     if (!this.m_enabled)
/*     */       return; 
/* 368 */     for (IMouseEnteredListener l : this.mouseEnteredHook)
/*     */     {
/* 370 */       l.mouseEntered(mouseEnteredEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addActivationListener(IActivationListener l) {
/* 376 */     this.activationHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeActivationListener(IActivationListener l) {
/* 381 */     this.activationHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 386 */     if (!this.m_enabled)
/*     */       return; 
/* 388 */     for (IMouseExitedListener l : this.mouseExitedHook)
/*     */     {
/* 390 */       l.mouseExited(mouseExitedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 397 */     if (!this.m_enabled)
/*     */       return; 
/* 399 */     for (IMousePressedListener l : this.mousePressedHook)
/*     */     {
/* 401 */       l.mousePressed(mousePressedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 408 */     if (this.mouseMovedHook.isEmpty() || !this.m_enabled)
/*     */       return; 
/* 410 */     MouseMovedEvent e = new MouseMovedEvent(null, displayX, displayY);
/*     */     
/* 412 */     for (IMouseMovedListener l : this.mouseMovedHook)
/*     */     {
/* 414 */       l.mouseMoved(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseDraggedEvent mouseDraggedEvent) {
/* 421 */     if (!this.m_enabled)
/*     */       return; 
/* 423 */     for (IMouseDraggedListener l : this.mouseDraggedHook)
/*     */     {
/* 425 */       l.mouseDragged(mouseDraggedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseReleasedEvent mouseReleasedEvent) {
/* 432 */     if (!this.m_enabled)
/*     */       return; 
/* 434 */     for (IMouseReleasedListener l : this.mouseReleasedHook)
/*     */     {
/* 436 */       l.mouseReleased(mouseReleasedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyPressedEvent keyPressedEvent) {
/* 443 */     if (!this.m_enabled)
/*     */       return; 
/* 445 */     for (IKeyPressedListener l : this.keyPressedHook)
/*     */     {
/* 447 */       l.keyPressed(keyPressedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyReleasedEvent keyReleasedEvent) {
/* 454 */     if (!this.m_enabled)
/*     */       return; 
/* 456 */     for (IKeyReleasedListener l : this.keyReleasedHook)
/*     */     {
/* 458 */       l.keyReleased(keyReleasedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusChanged(FocusEvent focusGainedEvent) {
/* 464 */     if (!this.m_enabled)
/*     */       return; 
/* 466 */     for (IFocusListener l : this.focusGainedHook)
/*     */     {
/* 468 */       l.focusChanged(focusGainedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseWheel(MouseWheelEvent e) {
/* 474 */     if (!this.m_enabled)
/*     */       return; 
/* 476 */     for (IMouseWheelListener l : this.mouseWheeledHook)
/*     */     {
/* 478 */       l.mouseWheel(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Container.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */