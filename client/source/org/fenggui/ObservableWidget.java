/*     */ package org.fenggui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IActivationListener;
/*     */ import org.fenggui.event.IFocusListener;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.IKeyReleasedListener;
/*     */ import org.fenggui.event.IKeyTypedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.KeyTypedEvent;
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
/*     */ public abstract class ObservableWidget
/*     */   extends StandardWidget
/*     */ {
/*     */   private boolean enabled = true;
/*  74 */   private IKeyPressedListener keyTraversalListener = null;
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  78 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTraversable(boolean b) {
/*  83 */     if (b == isTraversable())
/*     */       return; 
/*  85 */     if (b) {
/*     */       
/*  87 */       this.keyTraversalListener = new IKeyPressedListener()
/*     */         {
/*     */           public void keyPressed(KeyPressedEvent keyPressedEvent)
/*     */           {
/*  91 */             if (keyPressedEvent.getKeyClass() == Key.TAB) {
/*     */               
/*  93 */               IWidget w = ObservableWidget.this.getNextTraversableWidget();
/*     */               
/*  95 */               Display disp = ObservableWidget.this.getDisplay();
/*     */               
/*  97 */               if (disp != null)
/*  98 */                 disp.setFocusedWidget(w); 
/*     */             } 
/*     */           }
/*     */         };
/* 102 */       this.keyPressedHook.add(this.keyTraversalListener);
/*     */     }
/*     */     else {
/*     */       
/* 106 */       this.keyPressedHook.remove(this.keyTraversalListener);
/*     */       
/* 108 */       this.keyTraversalListener = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget() {
/* 114 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget() {
/* 119 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget() {
/* 124 */     return getParent().getNextWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget() {
/* 129 */     return getParent().getPreviousWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraversable() {
/* 135 */     return (this.keyTraversalListener != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 140 */     if (this.enabled == enabled) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 145 */     this.enabled = enabled;
/*     */     
/* 147 */     ActivationEvent e = new ActivationEvent(this, enabled);
/*     */     
/* 149 */     for (IActivationListener l : this.activationHook)
/*     */     {
/* 151 */       l.widgetActivationChanged(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 156 */   private ArrayList<IActivationListener> activationHook = new ArrayList<IActivationListener>(0);
/* 157 */   private ArrayList<IMouseEnteredListener> mouseEnteredHook = new ArrayList<IMouseEnteredListener>(0);
/* 158 */   private ArrayList<IMouseMovedListener> mouseMovedHook = new ArrayList<IMouseMovedListener>(0);
/* 159 */   private ArrayList<IMouseExitedListener> mouseExitedHook = new ArrayList<IMouseExitedListener>(0);
/* 160 */   private ArrayList<IMousePressedListener> mousePressedHook = new ArrayList<IMousePressedListener>(0);
/* 161 */   private ArrayList<IMouseReleasedListener> mouseReleasedHook = new ArrayList<IMouseReleasedListener>(0);
/* 162 */   private ArrayList<IFocusListener> focusGainedHook = new ArrayList<IFocusListener>(0);
/* 163 */   private ArrayList<IMouseDraggedListener> mouseDraggedHook = new ArrayList<IMouseDraggedListener>(0);
/* 164 */   private ArrayList<IMouseWheelListener> mouseWheeledHook = new ArrayList<IMouseWheelListener>(0);
/* 165 */   private ArrayList<IKeyPressedListener> keyPressedHook = new ArrayList<IKeyPressedListener>(0);
/* 166 */   private ArrayList<IKeyReleasedListener> keyReleasedHook = new ArrayList<IKeyReleasedListener>(0);
/* 167 */   private ArrayList<IKeyTypedListener> keyTypedHook = new ArrayList<IKeyTypedListener>(0);
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKeyReleasedListener(IKeyReleasedListener l) {
/* 172 */     this.keyReleasedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeKeyReleasedListener(IKeyReleasedListener l) {
/* 177 */     this.keyReleasedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addKeyPressedListener(IKeyPressedListener l) {
/* 182 */     this.keyPressedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeKeyPressedListener(IKeyPressedListener l) {
/* 187 */     this.keyPressedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addKeyTypedListener(IKeyTypedListener l) {
/* 192 */     this.keyTypedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeKeyTypedListener(IKeyTypedListener l) {
/* 197 */     this.keyTypedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseDraggedListener(IMouseDraggedListener l) {
/* 202 */     this.mouseDraggedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseDraggedListener(IMouseDraggedListener l) {
/* 207 */     this.mouseDraggedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseMovedListener(IMouseMovedListener l) {
/* 212 */     this.mouseMovedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseMovedListener(IMouseMovedListener l) {
/* 217 */     this.mouseMovedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseReleasedListener(IMouseReleasedListener l) {
/* 222 */     this.mouseReleasedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseReleasedListener(IMouseReleasedListener l) {
/* 227 */     this.mouseReleasedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMousePressedListener(IMousePressedListener l) {
/* 232 */     this.mousePressedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMousePressedListener(IMousePressedListener l) {
/* 237 */     this.mousePressedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseExitedListener(IMouseExitedListener l) {
/* 242 */     this.mouseExitedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseExitedListener(IMouseExitedListener l) {
/* 247 */     this.mouseExitedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseEnteredListener(IMouseEnteredListener l) {
/* 252 */     this.mouseEnteredHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseEnteredListener(IMouseEnteredListener l) {
/* 257 */     this.mouseEnteredHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFocusListener(IFocusListener l) {
/* 262 */     this.focusGainedHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFocusListener(IFocusListener l) {
/* 267 */     this.focusGainedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMouseWheelListener(IMouseWheelListener l) {
/* 273 */     this.mouseWheeledHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMouseWheelListener(IMouseWheelListener l) {
/* 278 */     this.mouseWheeledHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 283 */     if (!this.enabled)
/*     */       return; 
/* 285 */     for (IMouseEnteredListener l : this.mouseEnteredHook)
/*     */     {
/* 287 */       l.mouseEntered(mouseEnteredEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addActivationListener(IActivationListener l) {
/* 293 */     this.activationHook.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeActivationListener(IActivationListener l) {
/* 298 */     this.activationHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 303 */     if (!this.enabled)
/*     */       return; 
/* 305 */     for (IMouseExitedListener l : this.mouseExitedHook)
/*     */     {
/* 307 */       l.mouseExited(mouseExitedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 314 */     if (!this.enabled)
/*     */       return; 
/* 316 */     for (IMousePressedListener l : this.mousePressedHook)
/*     */     {
/* 318 */       l.mousePressed(mousePressedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 325 */     if (this.mouseMovedHook.isEmpty() || !this.enabled)
/*     */       return; 
/* 327 */     MouseMovedEvent e = new MouseMovedEvent(null, displayX, displayY);
/*     */     
/* 329 */     for (IMouseMovedListener l : this.mouseMovedHook)
/*     */     {
/* 331 */       l.mouseMoved(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseDraggedEvent mouseDraggedEvent) {
/* 338 */     if (!this.enabled)
/*     */       return; 
/* 340 */     for (IMouseDraggedListener l : this.mouseDraggedHook)
/*     */     {
/* 342 */       l.mouseDragged(mouseDraggedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseReleasedEvent mouseReleasedEvent) {
/* 349 */     if (!this.enabled)
/*     */       return; 
/* 351 */     for (IMouseReleasedListener l : this.mouseReleasedHook)
/*     */     {
/* 353 */       l.mouseReleased(mouseReleasedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyPressedEvent keyPressedEvent) {
/* 360 */     if (!this.enabled)
/*     */       return; 
/* 362 */     for (IKeyPressedListener l : this.keyPressedHook)
/*     */     {
/* 364 */       l.keyPressed(keyPressedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyReleasedEvent keyReleasedEvent) {
/* 371 */     if (!this.enabled)
/*     */       return; 
/* 373 */     for (IKeyReleasedListener l : this.keyReleasedHook)
/*     */     {
/* 375 */       l.keyReleased(keyReleasedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyTypedEvent keyTypedEvent) {
/* 381 */     if (!this.enabled)
/*     */       return; 
/* 383 */     for (IKeyTypedListener l : this.keyTypedHook)
/*     */     {
/* 385 */       l.keyTyped(keyTypedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusChanged(FocusEvent focusGainedEvent) {
/* 391 */     if (!this.enabled)
/*     */       return; 
/* 393 */     for (IFocusListener l : this.focusGainedHook)
/*     */     {
/* 395 */       l.focusChanged(focusGainedEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseWheel(MouseWheelEvent e) {
/* 401 */     if (!this.enabled)
/*     */       return; 
/* 403 */     for (IMouseWheelListener l : this.mouseWheeledHook)
/*     */     {
/* 405 */       l.mouseWheel(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ObservableWidget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */