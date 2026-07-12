/*     */ package org.fenggui.composites;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.FengGUI;
/*     */ import org.fenggui.IBasicContainer;
/*     */ import org.fenggui.IContainer;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.Label;
/*     */ import org.fenggui.LayoutManager;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.ButtonPressedEvent;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.IButtonPressedListener;
/*     */ import org.fenggui.event.IDragAndDropListener;
/*     */ import org.fenggui.event.IEventListener;
/*     */ import org.fenggui.event.IWindowClosedListener;
/*     */ import org.fenggui.event.WindowClosedEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ public class Window
/*     */   extends Container
/*     */   implements IWindow
/*     */ {
/*  64 */   private ArrayList<IWindowClosedListener> windowClosedHook = new ArrayList<IWindowClosedListener>();
/*     */   
/*  66 */   protected IContainer content = null;
/*  67 */   protected Container titleBar = null;
/*  68 */   protected Label title = null;
/*  69 */   protected Button closeButton = null;
/*  70 */   protected Button maximizeButton = null;
/*  71 */   protected Button minimizeButton = null;
/*     */   private boolean stickWithinDisplayBounds = false;
/*  73 */   private final Window THIS = this;
/*     */   
/*     */   private boolean isShowingResizeCursors = false;
/*  76 */   protected IDragAndDropListener moveDnDListener = new WindowMoveDnDListenerImpl();
/*  77 */   protected IDragAndDropListener resizeDnDListener = new WindowResizeDnDListenerImpl();
/*     */   
/*  79 */   protected IEventListener windowPressedListener = new IEventListener() {
/*     */       public void processEvent(Event event) {
/*  81 */         if (event instanceof MousePressedEvent) {
/*  82 */           MousePressedEvent mpe = (MousePressedEvent)event;
/*  83 */           IWidget widget = mpe.getSource();
/*  84 */           if (widget != Window.this.titleBar);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public Window() {
/*  92 */     this(true, false, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Window(boolean closeBtn, boolean maximizeBtn, boolean minimizeBtn) {
/*  99 */     this(closeBtn, maximizeBtn, minimizeBtn, true);
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
/*     */   
/*     */   public Window(boolean closeBtn, boolean maximizeBtn, boolean minimizeBtn, boolean autoClose) {
/* 117 */     build(closeBtn, maximizeBtn, minimizeBtn);
/*     */     
/* 119 */     setupTheme(Window.class);
/*     */     
/* 121 */     if (autoClose)
/*     */     {
/* 123 */       addWindowClosedListener(new IWindowClosedListener()
/*     */           {
/*     */             public void windowClosed(WindowClosedEvent windowClosedEvent)
/*     */             {
/* 127 */               windowClosedEvent.getWindow().close();
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getContentContainer() {
/* 140 */     return (Container)this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public IContainer getIContent() {
/* 145 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentContainer(IContainer c) {
/* 155 */     removeWidget((IWidget)this.content);
/* 156 */     if (!getContent().contains(c)) addWidget((IWidget)c); 
/* 157 */     ((Widget)c).setLayoutData((ILayoutData)BorderLayoutData.CENTER);
/* 158 */     if (c instanceof Container) ((Container)c).setKeyTraversalRoot(false); 
/* 159 */     updateMinSize();
/* 160 */     this.content = c;
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
/*     */   protected void build(boolean closeBtn, boolean maximizeBtn, boolean minimizeBtn) {
/* 174 */     this.titleBar = new Container();
/* 175 */     addWidget((IWidget)this.titleBar);
/*     */     
/* 177 */     setLayoutManager((LayoutManager)new BorderLayout());
/*     */     
/* 179 */     this.content = (IContainer)new Container();
/* 180 */     ((Container)this.content).setLayoutData((ILayoutData)BorderLayoutData.CENTER);
/* 181 */     ((Container)this.content).setKeyTraversalRoot(true);
/* 182 */     addWidget((IWidget)this.content);
/*     */     
/* 184 */     this.titleBar.setLayoutData((ILayoutData)BorderLayoutData.NORTH);
/*     */     
/* 186 */     buildTitleBar(closeBtn, maximizeBtn, minimizeBtn);
/*     */     
/* 188 */     setSize(100, 120);
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
/* 202 */     this.titleBar.setLayoutManager((LayoutManager)new RowLayout(true));
/*     */     
/* 204 */     this.title = new Label();
/* 205 */     this.titleBar.addWidget((IWidget)this.title);
/* 206 */     this.title.setText("Frame");
/*     */     
/* 208 */     if (minimizeBtn)
/*     */     {
/* 210 */       buildMinimizeButton();
/*     */     }
/*     */     
/* 213 */     if (maximizeBtn)
/*     */     {
/* 215 */       buildMaximizeButton();
/*     */     }
/*     */     
/* 218 */     if (closeBtn)
/*     */     {
/* 220 */       buildCloseButton();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void buildMinimizeButton() {
/* 228 */     this.minimizeButton = FengGUI.createButton((IContainer)this.titleBar, "_");
/* 229 */     this.minimizeButton.addButtonPressedListener(new IButtonPressedListener()
/*     */         {
/*     */           public void buttonPressed(ButtonPressedEvent e) {
/* 232 */             System.err.println("Minimize Window: Not implemented yet");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void buildMaximizeButton() {
/* 241 */     this.maximizeButton = FengGUI.createButton((IContainer)this.titleBar);
/* 242 */     this.maximizeButton.addButtonPressedListener(new IButtonPressedListener()
/*     */         {
/*     */           public void buttonPressed(ButtonPressedEvent e) {
/* 245 */             System.err.println("Maximize Window: Not implemented yet");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void buildCloseButton() {
/* 253 */     this.closeButton = FengGUI.createButton((IContainer)this.titleBar);
/* 254 */     this.closeButton.setText("X");
/* 255 */     this.closeButton.addButtonPressedListener(new IButtonPressedListener()
/*     */         {
/*     */           public void buttonPressed(ButtonPressedEvent e) {
/* 258 */             Window.this.fireWindowClosedEvent();
/*     */           }
/*     */         });
/* 261 */     this.closeButton.setTraversable(false);
/*     */   }
/*     */   class WindowMoveDnDListenerImpl implements IDragAndDropListener { int oldX; int oldY;
/*     */     
/*     */     WindowMoveDnDListenerImpl() {
/* 266 */       this.oldX = 0;
/* 267 */       this.oldY = 0;
/*     */     }
/*     */     
/*     */     public void select(int x, int y) {
/* 271 */       this.oldX = x;
/* 272 */       this.oldY = y;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drag(int x, int y) {
/* 277 */       if (Window.this.stickWithinDisplayBounds) {
/* 278 */         if (Window.this.getX() + x - this.oldX + Window.this.getWidth() - 50 < 0) {
/* 279 */           x = this.oldX - Window.this.getX() - Window.this.getWidth() + 50;
/* 280 */         } else if (Window.this.getX() + x - this.oldX + 50 > Window.this.getDisplay().getWidth()) {
/* 281 */           x = Window.this.getDisplay().getWidth() - 50 - Window.this.getX() + this.oldX;
/*     */         } 
/* 283 */         int titleBarHeight = Window.this.notifyList.contains(Window.this.titleBar) ? Window.this.titleBar.getHeight() : 50;
/* 284 */         if (Window.this.getY() + y - this.oldY + Window.this.getHeight() - titleBarHeight < 0) {
/*     */           
/* 286 */           y = 0;
/* 287 */         } else if (Window.this.getY() + y - this.oldY + Window.this.getHeight() > Window.this.getDisplay().getHeight()) {
/* 288 */           y = Window.this.getDisplay().getHeight() - Window.this.getHeight() - Window.this.getY() + this.oldY;
/*     */         } 
/*     */       } 
/* 291 */       int newX = x - this.oldX;
/* 292 */       int newY = y - this.oldY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 298 */       Window.this.move(newX, newY);
/* 299 */       this.oldX = x;
/* 300 */       this.oldY = y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drop(int x, int y, IWidget dropOn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int x, int y) {
/* 312 */       return !(!w.equals(Window.this.title) && !w.equals(Window.this.titleBar));
/*     */     } }
/*     */   class WindowResizeDnDListenerImpl implements IDragAndDropListener { int oldX; int oldY; int type; final int NORTH = 1;
/*     */     final int WEST = 2;
/*     */     
/*     */     WindowResizeDnDListenerImpl() {
/* 318 */       this.oldX = 0;
/* 319 */       this.oldY = 0;
/*     */       
/* 321 */       this.type = -1;
/*     */       
/* 323 */       this.NORTH = 1;
/* 324 */       this.WEST = 2;
/* 325 */       this.SOUTH = 3;
/* 326 */       this.EAST = 4;
/* 327 */       this.SOUTH_EAST = 5;
/* 328 */       this.SOUTH_WEST = 6;
/* 329 */       this.NORTH_EAST = 7;
/* 330 */       this.NORTH_WEST = 8;
/*     */     }
/*     */     final int SOUTH = 3; final int EAST = 4; final int SOUTH_EAST = 5; final int SOUTH_WEST = 6; final int NORTH_EAST = 7; final int NORTH_WEST = 8;
/*     */     public void select(int x, int y) {
/* 334 */       this.oldX = x;
/* 335 */       this.oldY = y;
/*     */       
/* 337 */       int localX = x - Window.this.getDisplayX();
/* 338 */       int localY = y - Window.this.getDisplayY();
/*     */       
/* 340 */       if (Window.this.onLeftBorder(localX, localY))
/*     */       
/* 342 */       { if (Window.this.onBottomBorder(localX, localY))
/* 343 */         { this.type = 6; }
/* 344 */         else if (Window.this.onTopBorder(localX, localY))
/* 345 */         { this.type = 8; }
/* 346 */         else { this.type = 2; }
/*     */          }
/* 348 */       else if (Window.this.onRightBorder(localX, localY))
/*     */       
/* 350 */       { if (Window.this.onBottomBorder(localX, localY))
/* 351 */         { this.type = 5; }
/* 352 */         else if (Window.this.onTopBorder(localX, localY))
/* 353 */         { this.type = 7; }
/* 354 */         else { this.type = 4; }
/*     */          }
/* 356 */       else if (Window.this.onBottomBorder(localX, localY)) { this.type = 3; }
/* 357 */       else if (Window.this.onTopBorder(localX, localY)) { this.type = 1; }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void drag(int x, int y) {
/* 363 */       int flagX = 0;
/* 364 */       int flagY = 0;
/*     */       
/* 366 */       switch (this.type) {
/*     */         
/*     */         case 4:
/* 369 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() + x - this.oldX);
/* 370 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight());
/*     */           break;
/*     */         case 1:
/* 373 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() + y - this.oldY);
/*     */           break;
/*     */         case 3:
/* 376 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() - y + this.oldY);
/* 377 */           if (flagY == 0) {
/* 378 */             Window.this.move(0, y - this.oldY);
/* 379 */             this.oldY = y; break;
/* 380 */           }  if (flagY > 0) {
/* 381 */             Window.this.move(0, flagY);
/* 382 */             this.oldY = y - flagY;
/*     */           } 
/*     */           break;
/*     */         case 7:
/* 386 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() + x - this.oldX);
/* 387 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() + y - this.oldY);
/*     */           break;
/*     */         case 8:
/* 390 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() - x + this.oldX);
/* 391 */           if (flagX == 0) {
/* 392 */             Window.this.move(x - this.oldX, 0);
/* 393 */             this.oldX = x;
/* 394 */           } else if (flagX > 0) {
/* 395 */             Window.this.move(flagX, 0);
/* 396 */             this.oldX = x - flagX;
/*     */           } 
/* 398 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() + y - this.oldY);
/*     */           break;
/*     */         case 6:
/* 401 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() - x + this.oldX);
/* 402 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() - y + this.oldY);
/*     */           
/* 404 */           if (flagX == 0) {
/* 405 */             Window.this.move(x - this.oldX, 0);
/* 406 */             this.oldX = x;
/* 407 */           } else if (flagX > 0) {
/* 408 */             Window.this.move(flagX, 0);
/* 409 */             this.oldX = x - flagX;
/*     */           } 
/*     */           
/* 412 */           if (flagY == 0) {
/* 413 */             Window.this.move(0, y - this.oldY);
/* 414 */             this.oldY = y; break;
/* 415 */           }  if (flagY > 0) {
/* 416 */             Window.this.move(0, flagY);
/* 417 */             this.oldY = y - flagY;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 5:
/* 422 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() + x - this.oldX);
/* 423 */           flagY = Window.this.setCheckedHeight(Window.this.getHeight() - y + this.oldY);
/* 424 */           if (flagY == 0) {
/* 425 */             Window.this.move(0, y - this.oldY);
/* 426 */             this.oldY = y; break;
/* 427 */           }  if (flagY > 0) {
/* 428 */             Window.this.move(0, flagY);
/* 429 */             this.oldY = y - flagY;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 433 */           flagX = Window.this.setCheckedWidth(Window.this.getWidth() - x + this.oldX);
/* 434 */           if (flagX == 0) {
/* 435 */             Window.this.move(x - this.oldX, 0);
/* 436 */             this.oldX = x; break;
/* 437 */           }  if (flagX > 0) {
/* 438 */             Window.this.move(flagX, 0);
/* 439 */             this.oldX = x - flagX;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 446 */       Window.this.layout();
/*     */       
/* 448 */       if (flagX == 0) this.oldX = x; 
/* 449 */       if (flagY == 0) this.oldY = y;
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void drop(int x, int y, IWidget dropOn) {
/* 455 */       this.type = -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int displayX, int displayY) {
/* 460 */       if (!w.equals(Window.this.THIS)) return false;
/*     */       
/* 462 */       return Window.this.isShowingResizeCursors;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int setCheckedWidth(int width) {
/* 468 */     int deltaWidth = 0;
/* 469 */     if (width >= getMinWidth()) {
/*     */       
/* 471 */       setWidth(width);
/*     */     }
/*     */     else {
/*     */       
/* 475 */       deltaWidth = getWidth() - getMinWidth();
/* 476 */       setWidth(getMinWidth());
/* 477 */       if (deltaWidth == 0) {
/* 478 */         return -1;
/*     */       }
/*     */     } 
/* 481 */     return deltaWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int setCheckedHeight(int height) {
/* 486 */     int deltaHeight = 0;
/* 487 */     if (height >= getMinHeight()) {
/*     */       
/* 489 */       setHeight(height);
/*     */     }
/*     */     else {
/*     */       
/* 493 */       deltaHeight = getHeight() - getMinHeight();
/* 494 */       setHeight(getMinHeight());
/* 495 */       if (deltaHeight == 0) {
/* 496 */         return -1;
/*     */       }
/*     */     } 
/* 499 */     return deltaHeight;
/*     */   }
/*     */   
/*     */   public boolean isStickWithinDisplayBounds() {
/* 503 */     return this.stickWithinDisplayBounds;
/*     */   }
/*     */   
/*     */   public void setStickWithinDisplayBounds(boolean stickWithinDisplayBounds) {
/* 507 */     this.stickWithinDisplayBounds = stickWithinDisplayBounds;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 513 */     int localX = displayX - getDisplayX();
/* 514 */     int localY = displayY - getDisplayY();
/*     */     
/* 516 */     if (onLeftBorder(localX, localY)) {
/*     */       
/* 518 */       if (onBottomBorder(localX, localY)) {
/*     */         
/* 520 */         Binding.getInstance().getCursorFactory().getSWResizeCursor().show();
/*     */       }
/* 522 */       else if (onTopBorder(localX, localY)) {
/*     */         
/* 524 */         Binding.getInstance().getCursorFactory().getNWResizeCursor().show();
/*     */       }
/*     */       else {
/*     */         
/* 528 */         Binding.getInstance().getCursorFactory().getHorizontalResizeCursor().show();
/*     */       } 
/*     */       
/* 531 */       this.isShowingResizeCursors = true;
/*     */     }
/* 533 */     else if (onRightBorder(localX, localY)) {
/*     */       
/* 535 */       if (onBottomBorder(localX, localY)) {
/*     */         
/* 537 */         Binding.getInstance().getCursorFactory().getNWResizeCursor().show();
/*     */       }
/* 539 */       else if (onTopBorder(localX, localY)) {
/*     */         
/* 541 */         Binding.getInstance().getCursorFactory().getSWResizeCursor().show();
/*     */       }
/*     */       else {
/*     */         
/* 545 */         Binding.getInstance().getCursorFactory().getHorizontalResizeCursor().show();
/*     */       } 
/*     */       
/* 548 */       this.isShowingResizeCursors = true;
/*     */     }
/* 550 */     else if (onBottomBorder(localX, localY)) {
/*     */       
/* 552 */       Binding.getInstance().getCursorFactory().getVerticalResizeCursor().show();
/* 553 */       this.isShowingResizeCursors = true;
/*     */     }
/* 555 */     else if (onTopBorder(localX, localY)) {
/*     */       
/* 557 */       Binding.getInstance().getCursorFactory().getVerticalResizeCursor().show();
/* 558 */       this.isShowingResizeCursors = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 567 */     if (this.isShowingResizeCursors)
/* 568 */       Binding.getInstance().getCursorFactory().getDefaultCursor().show(); 
/*     */   }
/*     */   
/*     */   public void putSelfOnTop() {
/* 572 */     IBasicContainer container = getParent();
/*     */     
/* 574 */     if (container == null) {
/*     */       return;
/*     */     }
/*     */     
/* 578 */     if (container instanceof Container) {
/* 579 */       ((Container)container).addWidget((IWidget)this, ((Container)container).getContent().size());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onLeftBorder(int localX, int localY) {
/* 585 */     localX += getAppearance().getLeftMargins();
/*     */     
/* 587 */     if (localX >= 0 && localX < getAppearance().getLeftMargins()) return true;
/*     */     
/* 589 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onBottomBorder(int localX, int localY) {
/* 594 */     localY += getAppearance().getBottomMargins();
/*     */     
/* 596 */     if (localY >= 0 && localY < getAppearance().getBottomMargins()) return true;
/*     */     
/* 598 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onRightBorder(int localX, int localY) {
/* 603 */     if (localX >= getAppearance().getContentWidth()) return true;
/*     */     
/* 605 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onTopBorder(int localX, int localY) {
/* 610 */     if (localY >= getAppearance().getContentHeight()) return true;
/*     */     
/* 612 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Window setTitle(String t) {
/* 622 */     this.title.setText(t);
/* 623 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 632 */     return this.title.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label getTitleLabel() {
/* 641 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public Button getCloseButton() {
/* 646 */     return this.closeButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 655 */     ((Container)getParent()).removeWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Button getMinimizeButton() {
/* 661 */     return this.minimizeButton;
/*     */   }
/*     */ 
/*     */   
/*     */   public Button getMaximizeButton() {
/* 666 */     return this.maximizeButton;
/*     */   }
/*     */ 
/*     */   
/*     */   public Container getTitleBar() {
/* 671 */     return this.titleBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 678 */     super.addedToWidgetTree();
/* 679 */     getDisplay().addDndListener(this.moveDnDListener);
/* 680 */     getDisplay().addDndListener(this.resizeDnDListener);
/* 681 */     getDisplay().addGlobalEventListener(this.windowPressedListener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 687 */     super.removedFromWidgetTree();
/* 688 */     getDisplay().removeDndListener(this.moveDnDListener);
/* 689 */     getDisplay().removeDndListener(this.resizeDnDListener);
/* 690 */     getDisplay().removeGlobalEventListener(this.windowPressedListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWindowClosedListener(IWindowClosedListener l) {
/* 699 */     if (!this.windowClosedHook.contains(l))
/*     */     {
/* 701 */       this.windowClosedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeWindowClosedListener(IWindowClosedListener l) {
/* 711 */     this.windowClosedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireWindowClosedEvent() {
/* 719 */     WindowClosedEvent e = new WindowClosedEvent(this);
/*     */     
/* 721 */     for (IWindowClosedListener l : this.windowClosedHook)
/*     */     {
/* 723 */       l.windowClosed(e);
/*     */     }
/*     */   }
/*     */   
/*     */   public void layout() {
/* 728 */     if (this.stickWithinDisplayBounds && 
/* 729 */       getDisplayY() + getHeight() > getDisplay().getHeight()) {
/* 730 */       setY(getDisplay().getHeight() - getHeight());
/*     */     }
/*     */     
/* 733 */     super.layout();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 739 */     setExpandable(stream.processAttribute("expandable", isExpandable(), true));
/* 740 */     setShrinkable(stream.processAttribute("shrinkable", isShrinkable(), true));
/* 741 */     setWidth(stream.processAttribute("width", getWidth(), 10));
/* 742 */     setHeight(stream.processAttribute("height", getHeight(), 10));
/* 743 */     setMinSize(stream.processAttribute("minWidth", getMinWidth(), 50), stream.processAttribute("minHeight", getMinHeight(), 50));
/* 744 */     setX(stream.processAttribute("x", getX(), 10));
/* 745 */     setY(stream.processAttribute("y", getY(), 10));
/*     */     
/* 747 */     setTitle(stream.processAttribute("title", getTitle(), "No Title"));
/*     */     
/* 749 */     stream.processInherentChild("TitleLabel", (IOStreamSaveable)this.title);
/*     */     
/* 751 */     stream.processInherentChild("TitleBar", (IOStreamSaveable)this.titleBar);
/*     */ 
/*     */     
/* 754 */     if (this.closeButton != null) {
/* 755 */       stream.processInherentChild("CloseButton", (IOStreamSaveable)this.closeButton);
/*     */     }
/* 757 */     if (this.minimizeButton != null) {
/* 758 */       stream.processInherentChild("MinimizeButton", (IOStreamSaveable)this.minimizeButton);
/*     */     }
/* 760 */     if (this.maximizeButton != null) {
/* 761 */       stream.processInherentChild("MaximizeButton", (IOStreamSaveable)this.maximizeButton);
/*     */     }
/* 763 */     if (stream.startSubcontext("content")) {
/*     */ 
/*     */       
/* 766 */       this.content = (IContainer)stream.processChild((IOStreamSaveable)this.content, FengGUI.TYPE_REGISTRY);
/* 767 */       this.content.setParent((IBasicContainer)this);
/* 768 */       stream.endSubcontext();
/*     */     } 
/*     */     
/* 771 */     if (stream.startSubcontext("Appearance")) {
/*     */       
/* 773 */       getAppearance().process(stream);
/* 774 */       stream.endSubcontext();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\composites\Window.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */