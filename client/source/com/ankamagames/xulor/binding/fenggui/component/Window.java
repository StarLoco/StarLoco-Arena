/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IContainer;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.composites.Window;
/*     */ import org.fenggui.event.IDragAndDropListener;
/*     */ import org.fenggui.layout.BorderLayout;
/*     */ import org.fenggui.layout.BorderLayoutData;
/*     */ import org.fenggui.layout.ILayoutData;
/*     */ import org.fenggui.layout.RowLayout;
/*     */ import org.fenggui.render.Binding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Window
/*     */   extends Window
/*     */   implements NonBlocking
/*     */ {
/*  31 */   private static Logger m_logger = Logger.getLogger(Window.class);
/*     */   
/*     */   private boolean m_positionReadFromWindowAttributes = false;
/*     */   
/*     */   private boolean m_displayTitleBar = true;
/*     */   private boolean m_resizable = true;
/*     */   private boolean m_movable = true;
/*     */   private boolean m_pack = false;
/*  39 */   private String m_id = null;
/*     */   
/*     */   private boolean m_showResizeCursor = false;
/*     */   private boolean m_nonBlocking = false;
/*  43 */   private ArrayList<WindowResizePoint> m_resizePoints = null;
/*  44 */   private ArrayList<WindowMovePoint> m_movePoints = null;
/*     */   
/*  46 */   private WindowMovePointDnDListenerImpl m_moveListener = new WindowMovePointDnDListenerImpl();
/*  47 */   private WindowResizePointDnDListenerImpl m_resizeListener = new WindowResizePointDnDListenerImpl();
/*     */   
/*     */   public Window(boolean closeButton, boolean maxButton, boolean minButton, boolean resizable, boolean movable, boolean useTitleBar) {
/*  50 */     super(closeButton, maxButton, minButton, false);
/*  51 */     setStickWithinDisplayBounds(true);
/*  52 */     this.m_resizable = resizable;
/*  53 */     this.m_movable = movable;
/*  54 */     this.m_displayTitleBar = useTitleBar;
/*  55 */     if (!this.m_displayTitleBar) {
/*  56 */       removeWidget((IWidget)this.titleBar);
/*     */     }
/*  58 */     setTitle(" ");
/*     */   }
/*     */ 
/*     */   
/*     */   public Window(boolean resizable, boolean movable) {
/*  63 */     this(true, false, false, resizable, movable, true);
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
/*     */   protected void build(boolean closeBtn, boolean maximizeBtn, boolean minimizeBtn) {
/*  79 */     setLayoutManager((LayoutManager)new BorderLayout());
/*     */     
/*  81 */     Container c = new Container();
/*  82 */     c.setNonBlocking(false);
/*  83 */     this.content = (IContainer)c;
/*     */     
/*  85 */     ((Container)this.content).setLayoutData((ILayoutData)BorderLayoutData.CENTER);
/*  86 */     ((Container)this.content).setKeyTraversalRoot(true);
/*  87 */     addWidget((IWidget)this.content);
/*     */     
/*  89 */     c = new Container();
/*  90 */     c.setNonBlocking(false);
/*  91 */     this.titleBar = c;
/*  92 */     this.titleBar.setLayoutData((ILayoutData)BorderLayoutData.NORTH);
/*  93 */     buildTitleBar(closeBtn, maximizeBtn, minimizeBtn);
/*     */ 
/*     */     
/*  96 */     addWidget((IWidget)this.titleBar);
/*     */ 
/*     */     
/*  99 */     setSize(100, 120);
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
/*     */   protected void buildTitleBar(boolean closeBtn, boolean maximizeBtn, boolean minimizeBtn) {
/* 113 */     this.titleBar.setLayoutManager((LayoutManager)new RowLayout(true));
/*     */     
/* 115 */     Label l = new Label();
/* 116 */     l.setNonBlocking(true);
/* 117 */     this.title = l;
/* 118 */     this.titleBar.addWidget((IWidget)this.title);
/* 119 */     this.title.setText("Frame");
/*     */     
/* 121 */     if (minimizeBtn)
/*     */     {
/* 123 */       buildMinimizeButton();
/*     */     }
/*     */     
/* 126 */     if (maximizeBtn)
/*     */     {
/* 128 */       buildMaximizeButton();
/*     */     }
/*     */     
/* 131 */     if (closeBtn)
/*     */     {
/* 133 */       buildCloseButton();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 144 */     super.addedToWidgetTree();
/*     */     
/* 146 */     Display display = getDisplay();
/* 147 */     if (display == null) {
/* 148 */       m_logger.error("getDisplay() renvoye null dans addedToWidgetTree");
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     if (this.m_resizable) {
/* 153 */       display.addDndListener(this.m_resizeListener);
/*     */     }
/*     */     
/* 156 */     if (this.m_movable) {
/* 157 */       display.addDndListener(this.m_moveListener);
/*     */     }
/*     */     
/* 160 */     if (!this.m_resizable || (this.m_resizePoints != null && this.m_resizePoints.size() > 0)) {
/* 161 */       display.removeDndListener(this.resizeDnDListener);
/*     */     }
/* 163 */     if (!this.m_movable || (this.m_movePoints != null && this.m_movePoints.size() > 0)) {
/* 164 */       display.removeDndListener(this.moveDnDListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayoutData(ILayoutData data) {
/* 170 */     if (this.m_positionReadFromWindowAttributes && data instanceof StaticLayoutPlusData) {
/* 171 */       ((StaticLayoutPlusData)data).resized();
/*     */     }
/* 173 */     super.setLayoutData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPacked() {
/* 180 */     return this.m_pack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPack(boolean pack) {
/* 187 */     this.m_pack = pack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 194 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 201 */     this.m_id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNonBlocking() {
/* 208 */     return this.m_nonBlocking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonBlocking(boolean nonBlocking) {
/* 215 */     this.m_nonBlocking = nonBlocking;
/* 216 */     ((Container)this.content).setNonBlocking(nonBlocking);
/* 217 */     ((Container)this.titleBar).setNonBlocking(nonBlocking);
/*     */   }
/*     */   public IWidget getWidget(int x, int y) {
/*     */     IWidget iWidget1;
/* 221 */     if (!getAppearance().insideMargin(x, y)) {
/* 222 */       return null;
/*     */     }
/*     */     
/* 225 */     x -= getAppearance().getLeftMargins();
/* 226 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 228 */     Window window = this.m_nonBlocking ? null : this;
/*     */ 
/*     */     
/* 231 */     IWidget found = this.titleBar.getWidget(x - this.titleBar.getX(), y - this.titleBar.getY());
/* 232 */     if (found != null) {
/* 233 */       iWidget1 = found;
/*     */     }
/*     */     
/* 236 */     found = this.content.getWidget(x - this.content.getX(), y - this.content.getY());
/* 237 */     if (found != null) {
/* 238 */       iWidget1 = found;
/*     */     }
/*     */     
/* 241 */     return iWidget1;
/*     */   }
/*     */   
/*     */   public void setWishedSize(Dimension size) {
/* 245 */     ((Container)this.content).setWishedSize(size);
/*     */   }
/*     */   
/*     */   public Dimension getWishedSize() {
/* 249 */     return ((Container)this.content).getWishedSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayTitleBar() {
/* 256 */     return this.m_displayTitleBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayTitleBar(boolean displayTitleBar) {
/* 263 */     this.m_displayTitleBar = displayTitleBar;
/* 264 */     if (displayTitleBar && !this.notifyList.contains(this.titleBar)) {
/* 265 */       addWidget((IWidget)this.titleBar);
/* 266 */     } else if (!displayTitleBar && this.notifyList.contains(this.titleBar)) {
/* 267 */       removeWidget((IWidget)this.titleBar);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addWindowResizePoint(WindowResizePoint wrp) {
/* 272 */     if (this.m_resizePoints == null) {
/* 273 */       this.m_resizePoints = new ArrayList<WindowResizePoint>();
/*     */     }
/* 275 */     wrp.setWindow(this);
/* 276 */     this.m_resizePoints.add(wrp);
/* 277 */     if (this.m_resizable && isInWidgetTree()) {
/* 278 */       getDisplay().addDndListener(this.m_resizeListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addWindowMovePoint(WindowMovePoint wmp) {
/* 283 */     if (this.m_movePoints == null) {
/* 284 */       this.m_movePoints = new ArrayList<WindowMovePoint>();
/*     */     }
/* 286 */     this.m_movePoints.add(wmp);
/* 287 */     if (this.m_movable && isInWidgetTree()) {
/* 288 */       getDisplay().addDndListener(this.m_moveListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 299 */     super.removedFromWidgetTree();
/* 300 */     if (this.m_resizable) {
/* 301 */       getDisplay().removeDndListener(this.resizeDnDListener);
/* 302 */       getDisplay().removeDndListener(this.m_resizeListener);
/*     */     } 
/* 304 */     if (this.m_movable) {
/* 305 */       getDisplay().removeDndListener(this.moveDnDListener);
/* 306 */       getDisplay().removeDndListener(this.m_moveListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 316 */     if (this.m_resizable) {
/* 317 */       super.mouseMoved(displayX, displayY);
/*     */     }
/*     */   }
/*     */   
/*     */   public void layout() {
/* 322 */     if (this.m_pack) {
/* 323 */       setSizeToMinSize();
/*     */     }
/* 325 */     super.layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWidgetToContent(IWidget widget) {
/* 332 */     if (widget instanceof WindowMovePoint) {
/* 333 */       addWindowMovePoint((WindowMovePoint)widget);
/* 334 */     } else if (widget instanceof WindowResizePoint) {
/* 335 */       addWindowResizePoint((WindowResizePoint)widget);
/*     */     } 
/* 337 */     this.content.addWidget(widget);
/*     */   }
/*     */   
/*     */   public void setShowResizeCursor(boolean show) {
/* 341 */     this.m_showResizeCursor = show;
/*     */   }
/*     */   
/*     */   class WindowMovePointDnDListenerImpl
/*     */     implements IDragAndDropListener {
/* 346 */     int oldX = 0;
/* 347 */     int oldY = 0;
/*     */ 
/*     */     
/*     */     public void select(int x, int y) {
/* 351 */       this.oldX = x;
/* 352 */       this.oldY = y;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drag(int x, int y) {
/* 357 */       if (Window.this.isStickWithinDisplayBounds()) {
/* 358 */         if (Window.this.getX() + x - this.oldX + Window.this.getWidth() - 50 < 0) {
/* 359 */           x = this.oldX - Window.this.getX() - Window.this.getWidth() + 50;
/* 360 */         } else if (Window.this.getX() + x - this.oldX + 50 > Window.this.getDisplay().getWidth()) {
/* 361 */           x = Window.this.getDisplay().getWidth() - 50 - Window.this.getX() + this.oldX;
/*     */         } 
/* 363 */         int titleBarHeight = ((Container)Window.this).notifyList.contains(Window.this.titleBar) ? Window.this.titleBar.getHeight() : 50;
/*     */         
/* 365 */         if (Window.this.getY() + y - this.oldY < 0) {
/*     */           
/* 367 */           y = this.oldY - Window.this.getY();
/* 368 */         } else if (Window.this.getY() + y - this.oldY + Window.this.getHeight() > Window.this.getDisplay().getHeight()) {
/* 369 */           y = Window.this.getDisplay().getHeight() - Window.this.getHeight() - Window.this.getY() + this.oldY;
/*     */         } 
/*     */       } 
/* 372 */       int newX = x - this.oldX;
/* 373 */       int newY = y - this.oldY;
/*     */       
/* 375 */       Window.this.move(newX, newY);
/* 376 */       this.oldX = x;
/* 377 */       this.oldY = y;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drop(int x, int y, IWidget dropOn) {}
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int x, int y) {
/* 385 */       if (Window.this.m_movePoints != null) {
/* 386 */         for (int i = 0; i < Window.this.m_movePoints.size(); i++) {
/* 387 */           if (((WindowMovePoint)Window.this.m_movePoints.get(i)).equals(w)) {
/* 388 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/* 392 */       return false;
/*     */     } }
/*     */   
/*     */   class WindowResizePointDnDListenerImpl implements IDragAndDropListener { int oldX;
/*     */     
/*     */     WindowResizePointDnDListenerImpl() {
/* 398 */       this.oldX = 0;
/* 399 */       this.oldY = 0;
/*     */       
/* 401 */       this.type = null;
/*     */     }
/*     */     int oldY; Alignment type;
/*     */     public void select(int x, int y) {}
/*     */     
/*     */     public void drag(int x, int y) {
/* 407 */       int flagX = 0;
/* 408 */       int flagY = 0;
/*     */       
/* 410 */       switch (this.type) {
/*     */         
/*     */         case EAST:
/* 413 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() + x - this.oldX);
/* 414 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight());
/*     */           break;
/*     */         case NORTH:
/* 417 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() + y - this.oldY);
/*     */           break;
/*     */         case SOUTH:
/* 420 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() - y + this.oldY);
/* 421 */           if (flagY == 0) {
/* 422 */             Window.this.move(0, y - this.oldY);
/* 423 */             this.oldY = y; break;
/* 424 */           }  if (flagY > 0) {
/* 425 */             Window.this.move(0, flagY);
/* 426 */             this.oldY = y - flagY;
/*     */           } 
/*     */           break;
/*     */         case NORTH_EAST:
/* 430 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() + x - this.oldX);
/* 431 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() + y - this.oldY);
/*     */           break;
/*     */         case NORTH_WEST:
/* 434 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() - x + this.oldX);
/* 435 */           if (flagX == 0) {
/* 436 */             Window.this.move(x - this.oldX, 0);
/* 437 */             this.oldX = x;
/* 438 */           } else if (flagX > 0) {
/* 439 */             Window.this.move(flagX, 0);
/* 440 */             this.oldX = x - flagX;
/*     */           } 
/* 442 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() + y - this.oldY);
/*     */           break;
/*     */         case SOUTH_WEST:
/* 445 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() - x + this.oldX);
/* 446 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() - y + this.oldY);
/*     */           
/* 448 */           if (flagX == 0) {
/* 449 */             Window.this.move(x - this.oldX, 0);
/* 450 */             this.oldX = x;
/* 451 */           } else if (flagX > 0) {
/* 452 */             Window.this.move(flagX, 0);
/* 453 */             this.oldX = x - flagX;
/*     */           } 
/*     */           
/* 456 */           if (flagY == 0) {
/* 457 */             Window.this.move(0, y - this.oldY);
/* 458 */             this.oldY = y; break;
/* 459 */           }  if (flagY > 0) {
/* 460 */             Window.this.move(0, flagY);
/* 461 */             this.oldY = y - flagY;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case SOUTH_EAST:
/* 466 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() + x - this.oldX);
/* 467 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() - y + this.oldY);
/* 468 */           if (flagY == 0) {
/* 469 */             Window.this.move(0, y - this.oldY);
/* 470 */             this.oldY = y; break;
/* 471 */           }  if (flagY > 0) {
/* 472 */             Window.this.move(0, flagY);
/* 473 */             this.oldY = y - flagY;
/*     */           } 
/*     */           break;
/*     */         case WEST:
/* 477 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() - x + this.oldX);
/* 478 */           if (flagX == 0) {
/* 479 */             Window.this.move(x - this.oldX, 0);
/* 480 */             this.oldX = x; break;
/* 481 */           }  if (flagX > 0) {
/* 482 */             Window.this.move(flagX, 0);
/* 483 */             this.oldX = x - flagX;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 490 */       Window.this.layout();
/*     */       
/* 492 */       if (flagX == 0) this.oldX = x; 
/* 493 */       if (flagY == 0) this.oldY = y;
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void drop(int x, int y, IWidget dropOn) {
/* 499 */       this.type = null;
/* 500 */       if (Window.this.m_showResizeCursor && !Window.this.m_resizePoints.contains(dropOn)) {
/* 501 */         Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/* 502 */         Window.this.m_showResizeCursor = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int displayX, int displayY) {
/* 508 */       if (!Window.this.m_showResizeCursor) return false; 
/* 509 */       this.oldX = displayX;
/* 510 */       this.oldY = displayY;
/* 511 */       if (Window.this.m_resizePoints != null) {
/* 512 */         for (int i = 0; i < Window.this.m_resizePoints.size(); i++) {
/* 513 */           if (((WindowResizePoint)Window.this.m_resizePoints.get(i)).equals(w)) {
/* 514 */             this.type = ((WindowResizePoint)Window.this.m_resizePoints.get(i)).getPointAlignment();
/* 515 */             return true;
/*     */           } 
/*     */         } 
/*     */       }
/* 519 */       return false;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Window.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */