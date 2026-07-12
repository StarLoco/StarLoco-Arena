/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Container;
/*     */ import com.ankamagames.xulor.event.ActivationEvent;
/*     */ import com.ankamagames.xulor.event.FocusEvent;
/*     */ import com.ankamagames.xulor.event.IActivationListener;
/*     */ import com.ankamagames.xulor.event.IFocusListener;
/*     */ import com.ankamagames.xulor.event.IKeyPressedListener;
/*     */ import com.ankamagames.xulor.event.IKeyReleasedListener;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.event.IMouseDoubleClickListener;
/*     */ import com.ankamagames.xulor.event.IMouseDraggedListener;
/*     */ import com.ankamagames.xulor.event.IMouseEnteredListener;
/*     */ import com.ankamagames.xulor.event.IMouseExitedListener;
/*     */ import com.ankamagames.xulor.event.IMouseMovedListener;
/*     */ import com.ankamagames.xulor.event.IMousePressedListener;
/*     */ import com.ankamagames.xulor.event.IMouseReleasedListener;
/*     */ import com.ankamagames.xulor.event.IMouseWheelListener;
/*     */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*     */ import com.ankamagames.xulor.event.KeyReleasedEvent;
/*     */ import com.ankamagames.xulor.event.MouseDraggedEvent;
/*     */ import com.ankamagames.xulor.event.MouseEnteredEvent;
/*     */ import com.ankamagames.xulor.event.MouseExitedEvent;
/*     */ import com.ankamagames.xulor.event.MouseManager;
/*     */ import com.ankamagames.xulor.event.MouseMovedEvent;
/*     */ import com.ankamagames.xulor.event.MousePressedEvent;
/*     */ import com.ankamagames.xulor.event.MouseReleasedEvent;
/*     */ import com.ankamagames.xulor.event.MouseWheelEvent;
/*     */ import com.ankamagames.xulor.event.listener.FocusListener;
/*     */ import com.ankamagames.xulor.event.listener.KeyPressedListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseExitedListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseMovedListener;
/*     */ import com.ankamagames.xulor.event.listener.MouseWheelListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IListenerManager;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.StandardWidget;
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
/*     */ 
/*     */ public abstract class XObservableComponent extends XComponent implements IObservable {
/*  65 */   private IActivationListener m_activationListener = null;
/*  66 */   private IFocusListener m_focusListener = null;
/*  67 */   private IKeyPressedListener m_keyPressedListener = null;
/*  68 */   private IKeyReleasedListener m_keyReleasedListener = null;
/*  69 */   private IMouseDraggedListener m_mouseDraggedListener = null;
/*  70 */   private IMouseEnteredListener m_mouseEnteredListener = null;
/*  71 */   private IMouseExitedListener m_mouseExitedListener = null;
/*  72 */   private IMouseMovedListener m_mouseMovedListener = null;
/*  73 */   private IMousePressedListener m_mousePressedListener = null;
/*  74 */   private IMouseReleasedListener m_mouseReleasedListener = null;
/*  75 */   private IMouseWheelListener m_mouseWheelListener = null;
/*     */   
/*     */   private Vector<IActionListener> m_acl;
/*     */   
/*     */   private Vector<IActivationListener> m_al;
/*     */   private Vector<IFocusListener> m_fl;
/*     */   private Vector<IKeyPressedListener> m_kpl;
/*     */   private Vector<IKeyReleasedListener> m_krl;
/*     */   private Vector<IMouseDraggedListener> m_mdl;
/*     */   private Vector<IMouseEnteredListener> m_mel;
/*     */   private Vector<IMouseExitedListener> m_mexl;
/*     */   private Vector<IMouseMovedListener> m_mml;
/*     */   private Vector<IMouseDoubleClickListener> m_mdcl;
/*     */   private Vector<IMouseClickListener> m_mcl;
/*     */   private Vector<IMousePressedListener> m_mpl;
/*     */   private Vector<IMouseReleasedListener> m_mrl;
/*     */   private Vector<IMouseWheelListener> m_mwl;
/*  92 */   private ToolTipAttributes m_tooltipAttributes = null;
/*  93 */   private IMouseEnteredListener m_toolTipEnter = null;
/*  94 */   private IMouseExitedListener m_toolTipExit = null;
/*     */   
/*     */   private XObservableComponent THIS;
/*     */   
/*     */   private boolean m_enabled = true;
/*     */   
/*     */   private boolean m_traversable = false;
/*     */   private boolean m_enabledInit = false, m_traversableInit = false;
/*     */   
/*     */   public XObservableComponent() {
/* 104 */     this.THIS = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDoubleClickListener() {
/* 112 */     return (this.m_mdcl != null && this.m_mdcl.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyObservableComponentAttributes() {
/* 117 */     if (getWidget() != null) {
/*     */       
/* 119 */       if (this.m_tooltipAttributes != null) {
/* 120 */         this.m_toolTipEnter = new IMouseEnteredListener() {
/*     */             public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 122 */               StandardWidget w = (StandardWidget)mouseEnteredEvent.getEntered();
/*     */               
/* 124 */               if (XObservableComponent.this.m_tooltipAttributes.FONT != null)
/* 125 */                 Xulor.getInstance().setTooltipFont(XObservableComponent.this.m_tooltipAttributes.FONT); 
/* 126 */               int xOffset = (XObservableComponent.this.m_tooltipAttributes.X_OFFSET != null) ? XObservableComponent.this.m_tooltipAttributes.X_OFFSET.intValue() : 0;
/* 127 */               int yOffset = (XObservableComponent.this.m_tooltipAttributes.Y_OFFSET != null) ? XObservableComponent.this.m_tooltipAttributes.Y_OFFSET.intValue() : 0;
/* 128 */               if (XObservableComponent.this.m_tooltipAttributes.HOT_POINT_POSITION != null)
/* 129 */                 Xulor.getInstance().setTooltipHotPointPosition(XObservableComponent.this.m_tooltipAttributes.HOT_POINT_POSITION); 
/* 130 */               if (XObservableComponent.this.m_tooltipAttributes.MAX_WIDTH != null)
/* 131 */                 Xulor.getInstance().setTooltipMaxWidth(XObservableComponent.this.m_tooltipAttributes.MAX_WIDTH.intValue()); 
/* 132 */               if (XObservableComponent.this.m_tooltipAttributes.BACKGROUND_COLOR != null)
/* 133 */                 Xulor.getInstance().setTooltipBackgroundColor(XObservableComponent.this.m_tooltipAttributes.BACKGROUND_COLOR); 
/* 134 */               if (XObservableComponent.this.m_tooltipAttributes.TEXT_COLOR != null)
/* 135 */                 Xulor.getInstance().setTooltipTextColor(XObservableComponent.this.m_tooltipAttributes.TEXT_COLOR); 
/* 136 */               if (XObservableComponent.this.m_tooltipAttributes.BORDER_COLOR != null)
/* 137 */                 Xulor.getInstance().setTooltipBorderColor(XObservableComponent.this.m_tooltipAttributes.BORDER_COLOR); 
/* 138 */               int duration = (XObservableComponent.this.m_tooltipAttributes.DURATION != null) ? XObservableComponent.this.m_tooltipAttributes.DURATION.intValue() : 3000;
/* 139 */               Alignment align = (XObservableComponent.this.m_tooltipAttributes.POSITION != null) ? XObservableComponent.this.m_tooltipAttributes.POSITION : Alignment.NORTH_WEST;
/* 140 */               Xulor.getInstance().showTooltip(XObservableComponent.this.m_tooltipAttributes.TEXT, 
/* 141 */                   w.getDisplayX() + align.getX(w.getWidth()), 
/* 142 */                   w.getDisplayY() + align.getY(w.getHeight()), 
/* 143 */                   duration, xOffset, yOffset);
/*     */             }
/*     */           };
/*     */         
/* 147 */         this.m_toolTipExit = new IMouseExitedListener()
/*     */           {
/*     */             public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 150 */               Xulor.getInstance().hideTooltip();
/*     */             }
/*     */           };
/*     */       } 
/*     */ 
/*     */       
/* 156 */       if (getWidget() instanceof ObservableWidget) {
/* 157 */         ObservableWidget w = (ObservableWidget)getWidget();
/* 158 */         if (this.m_enabledInit)
/* 159 */           w.setEnabled(this.m_enabled); 
/* 160 */         if (this.m_traversableInit)
/* 161 */           w.setTraversable(this.m_traversable); 
/* 162 */         if (this.m_toolTipEnter != null) w.addMouseEnteredListener(this.m_toolTipEnter); 
/* 163 */         if (this.m_toolTipExit != null) w.addMouseExitedListener(this.m_toolTipExit); 
/* 164 */       } else if (getWidget() instanceof Container) {
/* 165 */         Container w = (Container)getWidget();
/* 166 */         if (this.m_enabledInit)
/* 167 */           w.setEnabled(this.m_enabled); 
/* 168 */         if (this.m_toolTipEnter != null) w.addMouseEnteredListener(this.m_toolTipEnter); 
/* 169 */         if (this.m_toolTipExit != null) w.addMouseExitedListener(this.m_toolTipExit); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeSelfFromParent() {
/* 175 */     if (getWidget() instanceof ObservableWidget) {
/* 176 */       ObservableWidget w = (ObservableWidget)getWidget();
/* 177 */       if (w != null) {
/* 178 */         w.removeActivationListener(this.m_activationListener);
/* 179 */         w.removeFocusListener(this.m_focusListener);
/* 180 */         w.removeKeyPressedListener(this.m_keyPressedListener);
/* 181 */         w.removeKeyReleasedListener(this.m_keyReleasedListener);
/* 182 */         w.removeMouseDraggedListener(this.m_mouseDraggedListener);
/* 183 */         w.removeMouseEnteredListener(this.m_mouseEnteredListener);
/* 184 */         w.removeMouseExitedListener(this.m_mouseExitedListener);
/* 185 */         w.removeMouseMovedListener(this.m_mouseMovedListener);
/* 186 */         w.removeMousePressedListener(this.m_mousePressedListener);
/* 187 */         w.removeMouseReleasedListener(this.m_mouseReleasedListener);
/* 188 */         w.removeMouseWheelListener(this.m_mouseWheelListener);
/* 189 */         w.removeMouseEnteredListener(this.m_toolTipEnter);
/* 190 */         w.removeMouseExitedListener(this.m_toolTipExit);
/*     */       } 
/* 192 */     } else if (getWidget() instanceof Container) {
/* 193 */       Container w = (Container)getWidget();
/* 194 */       if (w != null) {
/* 195 */         w.removeActivationListener(this.m_activationListener);
/* 196 */         w.removeFocusListener(this.m_focusListener);
/* 197 */         w.removeKeyPressedListener(this.m_keyPressedListener);
/* 198 */         w.removeKeyReleasedListener(this.m_keyReleasedListener);
/* 199 */         w.removeMouseDraggedListener(this.m_mouseDraggedListener);
/* 200 */         w.removeMouseEnteredListener(this.m_mouseEnteredListener);
/* 201 */         w.removeMouseExitedListener(this.m_mouseExitedListener);
/* 202 */         w.removeMouseMovedListener(this.m_mouseMovedListener);
/* 203 */         w.removeMousePressedListener(this.m_mousePressedListener);
/* 204 */         w.removeMouseReleasedListener(this.m_mouseReleasedListener);
/* 205 */         w.removeMouseWheeledListener(this.m_mouseWheelListener);
/* 206 */         w.removeMouseEnteredListener(this.m_toolTipEnter);
/* 207 */         w.removeMouseExitedListener(this.m_toolTipExit);
/*     */       } 
/*     */     } 
/* 210 */     super.removeSelfFromParent();
/*     */   }
/*     */   
/*     */   public void removeAllListeners() {
/* 214 */     if (this.m_acl != null)
/* 215 */       this.m_acl.clear(); 
/* 216 */     if (this.m_al != null)
/* 217 */       this.m_al.clear(); 
/* 218 */     if (this.m_fl != null)
/* 219 */       this.m_fl.clear(); 
/* 220 */     if (this.m_kpl != null)
/* 221 */       this.m_kpl.clear(); 
/* 222 */     if (this.m_krl != null)
/* 223 */       this.m_krl.clear(); 
/* 224 */     if (this.m_mdl != null)
/* 225 */       this.m_mdl.clear(); 
/* 226 */     if (this.m_mel != null)
/* 227 */       this.m_mel.clear(); 
/* 228 */     if (this.m_mexl != null)
/* 229 */       this.m_mexl.clear(); 
/* 230 */     if (this.m_mml != null)
/* 231 */       this.m_mml.clear(); 
/* 232 */     if (this.m_mpl != null)
/* 233 */       this.m_mpl.clear(); 
/* 234 */     if (this.m_mrl != null)
/* 235 */       this.m_mrl.clear(); 
/* 236 */     if (this.m_mwl != null) {
/* 237 */       this.m_mwl.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IElement childElement) {
/* 245 */     if (childElement instanceof XToolTip) {
/* 246 */       this.m_tooltipAttributes = ((XToolTip)childElement).getToolTipAttributes();
/*     */     }
/*     */     
/* 249 */     super.add(childElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnActivation(IActivationListener l) {
/* 258 */     if (this.m_al == null) this.m_al = new Vector<IActivationListener>(); 
/* 259 */     this.m_al.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnFocus(IFocusListener l) {
/* 268 */     if (this.m_fl == null) this.m_fl = new Vector<IFocusListener>(); 
/* 269 */     this.m_fl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnKeyPress(IKeyPressedListener l) {
/* 278 */     if (this.m_kpl == null) this.m_kpl = new Vector<IKeyPressedListener>(); 
/* 279 */     this.m_kpl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnKeyRelease(IKeyReleasedListener l) {
/* 288 */     if (this.m_krl == null) this.m_krl = new Vector<IKeyReleasedListener>(); 
/* 289 */     this.m_krl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMouseDrag(IMouseDraggedListener l) {
/* 298 */     if (this.m_mdl == null) this.m_mdl = new Vector<IMouseDraggedListener>(); 
/* 299 */     this.m_mdl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMouseEnter(IMouseEnteredListener l) {
/* 308 */     if (this.m_mel == null) this.m_mel = new Vector<IMouseEnteredListener>(); 
/* 309 */     this.m_mel.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMouseExit(IMouseExitedListener l) {
/* 318 */     if (this.m_mexl == null) this.m_mexl = new Vector<IMouseExitedListener>(); 
/* 319 */     this.m_mexl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMouseMove(IMouseMovedListener l) {
/* 328 */     if (this.m_mml == null) this.m_mml = new Vector<IMouseMovedListener>(); 
/* 329 */     this.m_mml.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMousePress(IMousePressedListener l) {
/* 338 */     if (this.m_mpl == null) this.m_mpl = new Vector<IMousePressedListener>(); 
/* 339 */     this.m_mpl.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnDoubleClick(IMouseDoubleClickListener l) {
/* 344 */     if (this.m_mdcl == null) this.m_mdcl = new Vector<IMouseDoubleClickListener>(); 
/* 345 */     this.m_mdcl.add(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnClick(IMouseClickListener l) {
/* 350 */     if (this.m_mcl == null) this.m_mcl = new Vector<IMouseClickListener>(); 
/* 351 */     this.m_mcl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMouseRelease(IMouseReleasedListener l) {
/* 360 */     if (this.m_mrl == null) this.m_mrl = new Vector<IMouseReleasedListener>(); 
/* 361 */     this.m_mrl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnMouseWheel(IMouseWheelListener l) {
/* 370 */     if (this.m_mwl == null) this.m_mwl = new Vector<IMouseWheelListener>(); 
/* 371 */     this.m_mwl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IActivationListener> getOnActivation() {
/* 379 */     return this.m_al;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseClickListener> getOnClick() {
/* 387 */     return this.m_mcl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseDoubleClickListener> getOnDoubleClick() {
/* 395 */     return this.m_mdcl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IFocusListener> getOnFocus() {
/* 403 */     return this.m_fl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IKeyPressedListener> getOnKeyPress() {
/* 411 */     return this.m_kpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IKeyReleasedListener> getOnKeyRelease() {
/* 419 */     return this.m_krl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseDraggedListener> getOnMouseDrag() {
/* 427 */     return this.m_mdl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseEnteredListener> getOnMouseEnter() {
/* 435 */     return this.m_mel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseExitedListener> getOnMouseExit() {
/* 443 */     return this.m_mexl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseMovedListener> getOnMouseMove() {
/* 451 */     return this.m_mml;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMousePressedListener> getOnMousePress() {
/* 459 */     return this.m_mpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseReleasedListener> getOnMouseRelease() {
/* 467 */     return this.m_mrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector<IMouseWheelListener> getOnMouseWheel() {
/* 475 */     return this.m_mwl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 484 */     this.m_enabled = enabled;
/* 485 */     this.m_enabledInit = true;
/* 486 */     if (getWidget() != null) {
/* 487 */       ((ObservableWidget)getWidget()).setEnabled(enabled);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTraversable(boolean traversable) {
/* 497 */     this.m_traversable = traversable;
/* 498 */     this.m_traversableInit = true;
/* 499 */     if (getWidget() != null) {
/* 500 */       ((ObservableWidget)getWidget()).setTraversable(traversable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 510 */     if (getWidget() != null) {
/* 511 */       this.m_enabled = ((ObservableWidget)getWidget()).isEnabled();
/*     */     }
/* 513 */     return this.m_enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getTraversable() {
/* 522 */     if (getWidget() != null) {
/* 523 */       this.m_traversable = ((ObservableWidget)getWidget()).isTraversable();
/*     */     }
/* 525 */     return this.m_traversable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 534 */     XObservableComponent elem = (XObservableComponent)element;
/* 535 */     if (this.m_al != null)
/* 536 */       for (IActivationListener listener : this.m_al) elem.setOnActivation(listener);  
/* 537 */     if (this.m_fl != null)
/* 538 */       for (IFocusListener listener : this.m_fl) elem.setOnFocus(listener);  
/* 539 */     if (this.m_kpl != null)
/* 540 */       for (IKeyPressedListener listener : this.m_kpl) elem.setOnKeyPress(listener);  
/* 541 */     if (this.m_krl != null)
/* 542 */       for (IKeyReleasedListener listener : this.m_krl) elem.setOnKeyRelease(listener);  
/* 543 */     if (this.m_mdl != null)
/* 544 */       for (IMouseDraggedListener listener : this.m_mdl) elem.setOnMouseDrag(listener);  
/* 545 */     if (this.m_mel != null)
/* 546 */       for (IMouseEnteredListener listener : this.m_mel) elem.setOnMouseEnter(listener);  
/* 547 */     if (this.m_mexl != null)
/* 548 */       for (IMouseExitedListener listener : this.m_mexl) elem.setOnMouseExit(listener);  
/* 549 */     if (this.m_mml != null)
/* 550 */       for (IMouseMovedListener listener : this.m_mml) elem.setOnMouseMove(listener);  
/* 551 */     if (this.m_mpl != null)
/* 552 */       for (IMousePressedListener listener : this.m_mpl) elem.setOnMousePress(listener);  
/* 553 */     if (this.m_mcl != null)
/* 554 */       for (IMouseClickListener listener : this.m_mcl) elem.setOnClick(listener);  
/* 555 */     if (this.m_mdcl != null)
/* 556 */       for (IMouseDoubleClickListener listener : this.m_mdcl) elem.setOnDoubleClick(listener);  
/* 557 */     if (this.m_mrl != null)
/* 558 */       for (IMouseReleasedListener listener : this.m_mrl) elem.setOnMouseRelease(listener);  
/* 559 */     if (this.m_mwl != null)
/* 560 */       for (IMouseWheelListener listener : this.m_mwl) elem.setOnMouseWheel(listener);
/*     */        
/* 562 */     elem.m_enabled = this.m_enabled;
/* 563 */     elem.m_enabledInit = this.m_enabledInit;
/* 564 */     elem.m_traversable = this.m_traversable;
/* 565 */     elem.m_traversableInit = this.m_traversableInit;
/* 566 */     elem.m_tooltipAttributes = this.m_tooltipAttributes;
/* 567 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */   protected void addObservableComponentListeners() {
/* 571 */     if (getWidget() instanceof Container) {
/* 572 */       addContainerListeners();
/* 573 */     } else if (getWidget() instanceof ObservableWidget) {
/* 574 */       addObservableListeners();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addContainerListeners() {
/* 579 */     if (!(getWidget() instanceof Container)) {
/*     */       return;
/*     */     }
/* 582 */     Container w = (Container)getWidget();
/*     */     
/* 584 */     this.m_activationListener = new IActivationListener() {
/*     */         public void widgetActivationChanged(ActivationEvent activationEvent) {
/* 586 */           if (XObservableComponent.this.m_al != null) {
/* 587 */             ActivationEvent event = new ActivationEvent(XObservableComponent.this.THIS, activationEvent.isEnabled());
/* 588 */             for (IActivationListener l : XObservableComponent.this.m_al) {
/* 589 */               l.run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 594 */     w.addActivationListener(this.m_activationListener);
/*     */     
/* 596 */     this.m_focusListener = new IFocusListener() {
/*     */         public void focusChanged(FocusEvent focusEvent) {
/* 598 */           if (XObservableComponent.this.m_fl != null) {
/* 599 */             FocusEvent event = FengguiConstant.toXulorFocusEvent(focusEvent);
/* 600 */             for (IFocusListener l : XObservableComponent.this.m_fl) {
/* 601 */               ((FocusListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 606 */     w.addFocusListener(this.m_focusListener);
/*     */     
/* 608 */     this.m_keyPressedListener = new IKeyPressedListener() {
/*     */         public void keyPressed(KeyPressedEvent keyPressedEvent) {
/* 610 */           if (XObservableComponent.this.m_kpl != null) {
/* 611 */             KeyPressedEvent event = FengguiConstant.toXulorKeyPressedEvent(keyPressedEvent);
/* 612 */             for (IKeyPressedListener l : XObservableComponent.this.m_kpl) {
/* 613 */               ((KeyPressedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 618 */     w.addKeyPressedListener(this.m_keyPressedListener);
/*     */     
/* 620 */     this.m_keyReleasedListener = new IKeyReleasedListener() {
/*     */         public void keyReleased(KeyReleasedEvent keyReleasedEvent) {
/* 622 */           if (XObservableComponent.this.m_krl != null) {
/* 623 */             KeyReleasedEvent event = FengguiConstant.toXulorKeyReleasedEvent(keyReleasedEvent);
/* 624 */             for (IKeyReleasedListener l : XObservableComponent.this.m_krl) {
/* 625 */               ((KeyReleasedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 630 */     w.addKeyReleasedListener(this.m_keyReleasedListener);
/*     */     
/* 632 */     this.m_mouseDraggedListener = new IMouseDraggedListener() {
/*     */         public void mouseDragged(MouseDraggedEvent mouseDraggedEvent) {
/* 634 */           if (XObservableComponent.this.m_mdl != null) {
/* 635 */             MouseDraggedEvent event = FengguiConstant.toXulorMouseDraggedEvent(mouseDraggedEvent);
/* 636 */             for (IMouseDraggedListener l : XObservableComponent.this.m_mdl) {
/* 637 */               ((MouseDraggedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 642 */     w.addMouseDraggedListener(this.m_mouseDraggedListener);
/*     */     
/* 644 */     this.m_mouseEnteredListener = new IMouseEnteredListener() {
/*     */         public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 646 */           if (XObservableComponent.this.m_mel != null) {
/* 647 */             MouseEnteredEvent event = FengguiConstant.toXulorMouseEnteredEvent(mouseEnteredEvent);
/* 648 */             for (IMouseEnteredListener l : XObservableComponent.this.m_mel) {
/* 649 */               ((MouseEnteredListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 654 */     w.addMouseEnteredListener(this.m_mouseEnteredListener);
/*     */     
/* 656 */     this.m_mouseExitedListener = new IMouseExitedListener() {
/*     */         public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 658 */           if (XObservableComponent.this.m_mexl != null) {
/* 659 */             MouseExitedEvent event = FengguiConstant.toXulorMouseExitedEvent(mouseExitedEvent);
/* 660 */             for (IMouseExitedListener l : XObservableComponent.this.m_mexl) {
/* 661 */               ((MouseExitedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 666 */     w.addMouseExitedListener(this.m_mouseExitedListener);
/*     */     
/* 668 */     this.m_mouseMovedListener = new IMouseMovedListener() {
/*     */         public void mouseMoved(MouseMovedEvent mouseMovedEvent) {
/* 670 */           if (XObservableComponent.this.m_mml != null) {
/* 671 */             MouseMovedEvent event = FengguiConstant.toXulorMouseMovedEvent(mouseMovedEvent);
/* 672 */             for (IMouseMovedListener l : XObservableComponent.this.m_mml) {
/* 673 */               ((MouseMovedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 678 */     w.addMouseMovedListener(this.m_mouseMovedListener);
/*     */     
/* 680 */     this.m_mousePressedListener = new IMousePressedListener() {
/*     */         public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 682 */           MousePressedEvent event = FengguiConstant.toXulorMousePressedEvent(mousePressedEvent);
/* 683 */           MouseManager.getInstance().notifyPressed((IListenerManager)XObservableComponent.this.THIS, event);
/* 684 */           if (XObservableComponent.this.m_mpl != null) {
/* 685 */             for (IMousePressedListener l : XObservableComponent.this.m_mpl) {
/* 686 */               ((MousePressedListener)l).run(event);
/*     */             }
/*     */           }
/*     */         }
/*     */       };
/* 691 */     w.addMousePressedListener(this.m_mousePressedListener);
/*     */     
/* 693 */     this.m_mouseReleasedListener = new IMouseReleasedListener() {
/*     */         public void mouseReleased(MouseReleasedEvent mouseReleasedEvent) {
/* 695 */           MouseReleasedEvent mre = FengguiConstant.toXulorMouseReleasedEvent(mouseReleasedEvent);
/* 696 */           MouseManager.getInstance().notifyReleased((IListenerManager)XObservableComponent.this.THIS, mre);
/* 697 */           if (XObservableComponent.this.m_mrl != null) {
/* 698 */             for (IMouseReleasedListener l : XObservableComponent.this.m_mrl) {
/* 699 */               ((MouseReleasedListener)l).run(mre);
/*     */             }
/*     */           }
/*     */         }
/*     */       };
/* 704 */     w.addMouseReleasedListener(this.m_mouseReleasedListener);
/*     */     
/* 706 */     this.m_mouseWheelListener = new IMouseWheelListener() {
/*     */         public void mouseWheel(MouseWheelEvent mouseWheelEvent) {
/* 708 */           if (XObservableComponent.this.m_mwl != null) {
/* 709 */             MouseWheelEvent event = FengguiConstant.toXulorMouseWheelEvent(mouseWheelEvent);
/* 710 */             for (IMouseWheelListener l : XObservableComponent.this.m_mwl) {
/* 711 */               ((MouseWheelListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 716 */     w.addMouseWheeledListener(this.m_mouseWheelListener);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addObservableListeners() {
/* 721 */     if (!(getWidget() instanceof ObservableWidget)) {
/*     */       return;
/*     */     }
/* 724 */     ObservableWidget w = (ObservableWidget)getWidget();
/*     */     
/* 726 */     this.m_activationListener = new IActivationListener() {
/*     */         public void widgetActivationChanged(ActivationEvent activationEvent) {
/* 728 */           if (XObservableComponent.this.m_al != null) {
/* 729 */             ActivationEvent event = new ActivationEvent(XObservableComponent.this.THIS, activationEvent.isEnabled());
/* 730 */             for (IActivationListener l : XObservableComponent.this.m_al) {
/* 731 */               l.run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 736 */     w.addActivationListener(this.m_activationListener);
/*     */     
/* 738 */     this.m_focusListener = new IFocusListener() {
/*     */         public void focusChanged(FocusEvent focusEvent) {
/* 740 */           if (XObservableComponent.this.m_fl != null) {
/* 741 */             FocusEvent event = FengguiConstant.toXulorFocusEvent(focusEvent);
/* 742 */             for (IFocusListener l : XObservableComponent.this.m_fl) {
/* 743 */               ((FocusListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 748 */     w.addFocusListener(this.m_focusListener);
/*     */     
/* 750 */     this.m_keyPressedListener = new IKeyPressedListener() {
/*     */         public void keyPressed(KeyPressedEvent keyPressedEvent) {
/* 752 */           if (XObservableComponent.this.m_kpl != null) {
/* 753 */             KeyPressedEvent event = FengguiConstant.toXulorKeyPressedEvent(keyPressedEvent);
/* 754 */             for (IKeyPressedListener l : XObservableComponent.this.m_kpl) {
/* 755 */               ((KeyPressedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 760 */     w.addKeyPressedListener(this.m_keyPressedListener);
/*     */     
/* 762 */     this.m_keyReleasedListener = new IKeyReleasedListener() {
/*     */         public void keyReleased(KeyReleasedEvent keyReleasedEvent) {
/* 764 */           if (XObservableComponent.this.m_krl != null) {
/* 765 */             KeyReleasedEvent event = FengguiConstant.toXulorKeyReleasedEvent(keyReleasedEvent);
/* 766 */             for (IKeyReleasedListener l : XObservableComponent.this.m_krl) {
/* 767 */               ((KeyReleasedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 772 */     w.addKeyReleasedListener(this.m_keyReleasedListener);
/*     */     
/* 774 */     this.m_mouseDraggedListener = new IMouseDraggedListener() {
/*     */         public void mouseDragged(MouseDraggedEvent mouseDraggedEvent) {
/* 776 */           if (XObservableComponent.this.m_mdl != null) {
/* 777 */             MouseDraggedEvent event = FengguiConstant.toXulorMouseDraggedEvent(mouseDraggedEvent);
/* 778 */             for (IMouseDraggedListener l : XObservableComponent.this.m_mdl) {
/* 779 */               ((MouseDraggedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 784 */     w.addMouseDraggedListener(this.m_mouseDraggedListener);
/*     */     
/* 786 */     this.m_mouseEnteredListener = new IMouseEnteredListener() {
/*     */         public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 788 */           if (XObservableComponent.this.m_mel != null) {
/* 789 */             MouseEnteredEvent event = FengguiConstant.toXulorMouseEnteredEvent(mouseEnteredEvent);
/* 790 */             for (IMouseEnteredListener l : XObservableComponent.this.m_mel) {
/* 791 */               ((MouseEnteredListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 796 */     w.addMouseEnteredListener(this.m_mouseEnteredListener);
/*     */     
/* 798 */     this.m_mouseExitedListener = new IMouseExitedListener() {
/*     */         public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 800 */           if (XObservableComponent.this.m_mexl != null) {
/* 801 */             MouseExitedEvent event = FengguiConstant.toXulorMouseExitedEvent(mouseExitedEvent);
/* 802 */             for (IMouseExitedListener l : XObservableComponent.this.m_mexl) {
/* 803 */               ((MouseExitedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 808 */     w.addMouseExitedListener(this.m_mouseExitedListener);
/*     */     
/* 810 */     this.m_mouseMovedListener = new IMouseMovedListener() {
/*     */         public void mouseMoved(MouseMovedEvent mouseMovedEvent) {
/* 812 */           if (XObservableComponent.this.m_mml != null) {
/* 813 */             MouseMovedEvent event = FengguiConstant.toXulorMouseMovedEvent(mouseMovedEvent);
/* 814 */             for (IMouseMovedListener l : XObservableComponent.this.m_mml) {
/* 815 */               ((MouseMovedListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 820 */     w.addMouseMovedListener(this.m_mouseMovedListener);
/*     */     
/* 822 */     this.m_mousePressedListener = new IMousePressedListener() {
/*     */         public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 824 */           MousePressedEvent event = FengguiConstant.toXulorMousePressedEvent(mousePressedEvent);
/* 825 */           MouseManager.getInstance().notifyPressed((IListenerManager)XObservableComponent.this.THIS, event);
/* 826 */           if (XObservableComponent.this.m_mpl != null) {
/* 827 */             for (IMousePressedListener l : XObservableComponent.this.m_mpl) {
/* 828 */               ((MousePressedListener)l).run(event);
/*     */             }
/*     */           }
/*     */         }
/*     */       };
/* 833 */     w.addMousePressedListener(this.m_mousePressedListener);
/*     */     
/* 835 */     this.m_mouseReleasedListener = new IMouseReleasedListener() {
/*     */         public void mouseReleased(MouseReleasedEvent mouseReleasedEvent) {
/* 837 */           MouseReleasedEvent mre = FengguiConstant.toXulorMouseReleasedEvent(mouseReleasedEvent);
/* 838 */           MouseManager.getInstance().notifyReleased((IListenerManager)XObservableComponent.this.THIS, mre);
/* 839 */           if (XObservableComponent.this.m_mrl != null) {
/* 840 */             for (IMouseReleasedListener l : XObservableComponent.this.m_mrl) {
/* 841 */               ((MouseReleasedListener)l).run(mre);
/*     */             }
/*     */           }
/*     */         }
/*     */       };
/* 846 */     w.addMouseReleasedListener(this.m_mouseReleasedListener);
/*     */     
/* 848 */     this.m_mouseWheelListener = new IMouseWheelListener() {
/*     */         public void mouseWheel(MouseWheelEvent mouseWheelEvent) {
/* 850 */           if (XObservableComponent.this.m_mwl != null) {
/* 851 */             MouseWheelEvent event = FengguiConstant.toXulorMouseWheelEvent(mouseWheelEvent);
/* 852 */             for (IMouseWheelListener l : XObservableComponent.this.m_mwl) {
/* 853 */               ((MouseWheelListener)l).run(event);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/* 858 */     w.addMouseWheelListener(this.m_mouseWheelListener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAppearance(ObservableWidget widget) {
/* 864 */     if (widget == null || !(widget.getAppearance() instanceof DecoratorAppearance)) {
/*     */       return;
/*     */     }
/*     */     
/* 868 */     DecoratorAppearance app = (DecoratorAppearance)widget.getAppearance();
/* 869 */     app.setEnabled("disabled", !widget.isEnabled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void simpleClick(MouseReleasedEvent event) {
/* 878 */     if (this.m_mcl != null) {
/* 879 */       MouseClickEvent mce = new MouseClickEvent(this, event.getDisplayX(), event.getDisplayY(), 1, event.getButton());
/* 880 */       for (IMouseClickListener l : this.m_mcl) {
/* 881 */         l.run(mce);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doubleClick(MouseReleasedEvent event) {
/* 892 */     if (this.m_mdcl != null) {
/* 893 */       MouseDoubleClickEvent mdce = new MouseDoubleClickEvent(this, event.getDisplayX(), event.getDisplayY(), 2, event.getButton());
/* 894 */       for (IMouseDoubleClickListener l : this.m_mdcl)
/* 895 */         ((MouseDoubleClickListener)l).run(mdce); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XObservableComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */