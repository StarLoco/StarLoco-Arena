/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.event.ISliderMovedListener;
/*     */ import org.fenggui.event.SliderMovedEvent;
/*     */ import org.fenggui.event.mouse.IMouseWheelListener;
/*     */ import org.fenggui.event.mouse.MouseWheelEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ public class ScrollContainer
/*     */   extends StandardWidget
/*     */   implements IContainer
/*     */ {
/*  63 */   protected IWidget innerWidget = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   protected ScrollContainerAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   protected ScrollBar verticalScrollBar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected ScrollBar horizontalScrollBar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayVerticalScrollBar = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected ScrollBar.ScrollBarBehaviour horizontalScrollBarBehaviour = ScrollBar.ScrollBarBehaviour.WHEN_NEEDED;
/*  89 */   protected ScrollBar.ScrollBarBehaviour verticalScrollBarBehaviour = ScrollBar.ScrollBarBehaviour.WHEN_NEEDED;
/*     */ 
/*     */   
/*     */   protected boolean displayHorizontalScrollBar = false;
/*     */ 
/*     */ 
/*     */   
/*  96 */   protected IMouseWheelListener mouseWheelListener = new IMouseWheelListener()
/*     */     {
/*     */       public void mouseWheel(MouseWheelEvent mouseWheelEvent)
/*     */       {
/* 100 */         if (!ScrollContainer.this.displayVerticalScrollBar)
/*     */           return; 
/* 102 */         for (int i = 0; i < mouseWheelEvent.getRotations(); i++) {
/* 103 */           ScrollContainer.this.stepScrollVertical(mouseWheelEvent.wheeledUp());
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createHorizontalScrollBar() {
/* 113 */     if (this.horizontalScrollBar != null) {
/*     */       return;
/*     */     }
/* 116 */     this.horizontalScrollBar = new ScrollBar(true);
/* 117 */     this.horizontalScrollBar.setParent(this);
/* 118 */     if (isInWidgetTree())
/* 119 */       this.horizontalScrollBar.addedToWidgetTree(); 
/* 120 */     this.horizontalScrollBar.getSlider().setValue(0.0D);
/* 121 */     this.horizontalScrollBar.getSlider().addSliderMovedListener(new ISliderMovedListener()
/*     */         {
/*     */           public void sliderMoved(SliderMovedEvent sliderMovedEvent)
/*     */           {
/* 125 */             ScrollContainer.this.placeInnerWidgetHorizontally(sliderMovedEvent.getPosition());
/*     */           }
/*     */         });
/* 128 */     this.horizontalScrollBar.getIncreaseButton().setTraversable(false);
/* 129 */     this.horizontalScrollBar.getDecreaseButton().setTraversable(false);
/* 130 */     this.horizontalScrollBar.getSlider().getSliderButton().setTraversable(false);
/* 131 */     this.horizontalScrollBar.setXY(0, 0);
/* 132 */     this.horizontalScrollBar.setHeight(this.horizontalScrollBar.getMinHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 139 */     super.process(stream);
/*     */     
/* 141 */     if (getInnerWidget() instanceof StandardWidget || getInnerWidget() == null)
/*     */     {
/* 143 */       if (stream.startSubcontext("innerWidget")) {
/*     */         
/* 145 */         setInnerWidget((StandardWidget)stream.processChild((StandardWidget)this.innerWidget, FengGUI.TYPE_REGISTRY));
/* 146 */         stream.endSubcontext();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void placeInnerWidgetHorizontally(double sliderValue) {
/* 156 */     this.innerWidget.setX(-((int)(sliderValue * getHorizontalScrollSpace())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void placeInnerWidgetVertically(double sliderValue) {
/* 164 */     this.innerWidget.setY(-((int)(sliderValue * getVerticalScrollSpace())) + (
/* 165 */         this.displayHorizontalScrollBar ? this.horizontalScrollBar.getHeight() : 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createVerticalScrollBar() {
/* 174 */     if (this.verticalScrollBar != null) {
/*     */       return;
/*     */     }
/* 177 */     this.verticalScrollBar = new ScrollBar(false);
/* 178 */     this.verticalScrollBar.setParent(this);
/* 179 */     if (isInWidgetTree())
/* 180 */       this.verticalScrollBar.addedToWidgetTree(); 
/* 181 */     this.verticalScrollBar.getSlider().setValue(0.0D);
/* 182 */     this.verticalScrollBar.getSlider().addSliderMovedListener(new ISliderMovedListener()
/*     */         {
/*     */           public void sliderMoved(SliderMovedEvent sliderMovedEvent)
/*     */           {
/* 186 */             ScrollContainer.this.placeInnerWidgetVertically(sliderMovedEvent.getPosition());
/*     */           }
/*     */         });
/* 189 */     this.verticalScrollBar.updateMinSize();
/* 190 */     this.verticalScrollBar.getIncreaseButton().setTraversable(false);
/* 191 */     this.verticalScrollBar.getDecreaseButton().setTraversable(false);
/* 192 */     this.verticalScrollBar.getSlider().getSliderButton().setTraversable(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollContainer() {
/* 200 */     createVerticalScrollBar();
/* 201 */     createHorizontalScrollBar();
/*     */     
/* 203 */     this.appearance = new ScrollContainerAppearance(this);
/* 204 */     setupTheme(ScrollContainer.class);
/* 205 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 211 */     if (this.innerWidget != null)
/* 212 */       this.innerWidget.addedToWidgetTree(); 
/* 213 */     if (this.horizontalScrollBar != null)
/* 214 */       this.horizontalScrollBar.addedToWidgetTree(); 
/* 215 */     if (this.verticalScrollBar != null) {
/* 216 */       this.verticalScrollBar.addedToWidgetTree();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 222 */     if (this.innerWidget != null)
/* 223 */       this.innerWidget.removedFromWidgetTree(); 
/* 224 */     if (this.horizontalScrollBar != null)
/* 225 */       this.horizontalScrollBar.removedFromWidgetTree(); 
/* 226 */     if (this.verticalScrollBar != null) {
/* 227 */       this.verticalScrollBar.removedFromWidgetTree();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 237 */     if (!getAppearance().insideMargin(x, y)) {
/* 238 */       return null;
/*     */     }
/* 240 */     x -= getAppearance().getLeftMargins();
/* 241 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 243 */     if (this.displayHorizontalScrollBar && this.horizontalScrollBar.getSize()
/* 244 */       .contains(x - this.horizontalScrollBar.getX(), y - this.horizontalScrollBar.getY()))
/* 245 */       return this.horizontalScrollBar.getWidget(x - this.horizontalScrollBar.getX(), y - 
/* 246 */           this.horizontalScrollBar.getY()); 
/* 247 */     if (this.displayVerticalScrollBar && this.verticalScrollBar.getSize()
/* 248 */       .contains(x - this.verticalScrollBar.getX(), y - this.verticalScrollBar.getY())) {
/* 249 */       return this.verticalScrollBar.getWidget(x - this.verticalScrollBar.getX(), y - 
/* 250 */           this.verticalScrollBar.getY());
/*     */     }
/* 252 */     if (this.innerWidget != null) {
/* 253 */       int modX = x;
/* 254 */       int modY = y;
/*     */       
/* 256 */       if (this.displayHorizontalScrollBar) {
/* 257 */         modX = (int)((this.innerWidget.getSize().getWidth() - getAppearance().getContentWidth()) * getHorizontalScrollQuotient() + x);
/*     */       }
/* 259 */       if (this.displayVerticalScrollBar) {
/* 260 */         modY = (int)((this.innerWidget.getSize().getHeight() - getAppearance().getContentHeight()) * getVerticalScrollQuotient() + y);
/*     */       }
/* 262 */       return this.innerWidget.getWidget(modX, modY);
/*     */     } 
/*     */     
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHorizontalScrollBarBehaviour(ScrollBar.ScrollBarBehaviour sbb) {
/* 274 */     this.horizontalScrollBarBehaviour = sbb;
/* 275 */     layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBar.ScrollBarBehaviour getHorizontalScrollBarBehaviour() {
/* 284 */     return this.horizontalScrollBarBehaviour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBar.ScrollBarBehaviour getVerticalScrollBarBehaviour() {
/* 292 */     return this.verticalScrollBarBehaviour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerticalScrollBarBehaviour(ScrollBar.ScrollBarBehaviour verticalScrollBarBehaviour) {
/* 301 */     this.verticalScrollBarBehaviour = verticalScrollBarBehaviour;
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
/*     */   public double getHorizontalScrollQuotient() {
/* 316 */     return this.innerWidget.getX() / getHorizontalScrollSpace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVerticalScrollQuotient() {
/* 325 */     if (this.displayHorizontalScrollBar) {
/* 326 */       return (this.horizontalScrollBar.getHeight() - this.innerWidget.getY()) / 
/* 327 */         getVerticalScrollSpace();
/*     */     }
/* 329 */     return -this.innerWidget.getY() / 
/* 330 */       getVerticalScrollSpace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollHorizontal(double percent) {
/* 337 */     if (!this.displayHorizontalScrollBar) {
/*     */       return;
/*     */     }
/* 340 */     if (percent > 1.0D)
/* 341 */       percent = 1.0D; 
/* 342 */     if (percent < 0.0D) {
/* 343 */       percent = 0.0D;
/*     */     }
/* 345 */     double d = percent * getHorizontalScrollSpace();
/* 346 */     this.innerWidget.setX(-((int)d));
/*     */     
/* 348 */     this.horizontalScrollBar.getSlider().setValue(d);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollVertical(double percent) {
/* 354 */     if (!this.displayVerticalScrollBar) {
/*     */       return;
/*     */     }
/* 357 */     if (percent > 1.0D)
/* 358 */       percent = 1.0D; 
/* 359 */     if (percent < 0.0D) {
/* 360 */       percent = 0.0D;
/*     */     }
/* 362 */     double d = percent * getVerticalScrollSpace();
/* 363 */     this.innerWidget.setY(-((int)d) + this.horizontalScrollBar.getHeight());
/*     */     
/* 365 */     this.verticalScrollBar.getSlider().setValue(d);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stepScrollHorizontal(boolean right) {
/* 370 */     if (!this.displayHorizontalScrollBar) {
/*     */       return;
/*     */     }
/* 373 */     double curValue = this.horizontalScrollBar.getSlider().getValue();
/* 374 */     double horScrollSpace = getHorizontalScrollSpace();
/*     */     
/* 376 */     double step = 1.0D / horScrollSpace / getWidth();
/*     */     
/* 378 */     if (right) {
/* 379 */       curValue += step;
/*     */     } else {
/* 381 */       curValue -= step;
/*     */     } 
/* 383 */     this.horizontalScrollBar.getSlider().setValue(curValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stepScrollVertical(boolean up) {
/* 388 */     if (!this.displayVerticalScrollBar) {
/*     */       return;
/*     */     }
/* 391 */     double curValue = this.verticalScrollBar.getSlider().getValue();
/* 392 */     double verScrollSpace = getVerticalScrollSpace();
/*     */     
/* 394 */     double step = 1.0D / verScrollSpace / getHeight();
/*     */     
/* 396 */     if (up) {
/* 397 */       curValue += step;
/*     */     } else {
/* 399 */       curValue -= step;
/*     */     } 
/* 401 */     this.verticalScrollBar.getSlider().setValue(curValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getVerticalScrollSpace() {
/* 409 */     int contentHeight = getAppearance().getContentHeight();
/*     */     
/* 411 */     if (this.displayHorizontalScrollBar) {
/* 412 */       contentHeight -= this.horizontalScrollBar.getHeight();
/*     */     }
/* 414 */     return (this.innerWidget.getSize().getHeight() - contentHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getHorizontalScrollSpace() {
/* 422 */     int contentWidth = getAppearance().getContentWidth();
/*     */     
/* 424 */     if (this.displayVerticalScrollBar) {
/* 425 */       contentWidth -= this.verticalScrollBar.getWidth();
/*     */     }
/* 427 */     return (this.innerWidget.getSize().getWidth() - contentWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBar getHorizontalScrollBar() {
/* 436 */     return this.horizontalScrollBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getInnerWidget() {
/* 445 */     return this.innerWidget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBar getVerticalScrollBar() {
/* 454 */     return this.verticalScrollBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInnerWidget(IWidget innerWidget) {
/* 463 */     if (this.innerWidget != null) {
/*     */       
/* 465 */       this.innerWidget.removedFromWidgetTree();
/* 466 */       this.innerWidget.setParent(null);
/* 467 */       if (getDisplay() != null) getDisplay().focusedWidgetValityCheck(); 
/* 468 */       if (this.innerWidget instanceof ObservableWidget) {
/* 469 */         ((ObservableWidget)this.innerWidget).removeMouseWheelListener(this.mouseWheelListener);
/*     */       }
/*     */     } 
/* 472 */     this.innerWidget = innerWidget;
/* 473 */     innerWidget.setParent(this);
/* 474 */     if (isInWidgetTree())
/* 475 */       this.innerWidget.addedToWidgetTree(); 
/* 476 */     if (innerWidget instanceof ObservableWidget) {
/* 477 */       ((ObservableWidget)innerWidget).addMouseWheelListener(this.mouseWheelListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 484 */     if (this.innerWidget == null) {
/*     */       return;
/*     */     }
/* 487 */     setMinSize(50, 50);
/*     */     
/* 489 */     if (getParent() != null) getParent().updateMinSize();
/*     */   
/*     */   }
/*     */   
/*     */   public ScrollContainerAppearance getAppearance() {
/* 494 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 503 */     if (this.innerWidget == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     int contentHeight = getAppearance().getContentHeight();
/* 514 */     int contentWidth = getAppearance().getContentWidth();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 520 */     int viewPortWidth = contentWidth;
/* 521 */     int viewPortHeight = contentHeight;
/*     */ 
/*     */     
/* 524 */     int innerWidgetMinWidth = getInnerWidget().getMinSize().getWidth();
/* 525 */     int innerWidgetMinHeight = getInnerWidget().getMinSize().getHeight();
/* 526 */     int innerWidgetWidth = getInnerWidget().getSize().getWidth();
/* 527 */     int innerWidgetHeight = getInnerWidget().getSize().getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     if (innerWidgetMinWidth < contentWidth && !this.horizontalScrollBarBehaviour.equals(ScrollBar.ScrollBarBehaviour.FORCE_DISPLAY)) {
/* 536 */       this.displayHorizontalScrollBar = false;
/* 537 */     } else if (!this.horizontalScrollBarBehaviour.equals(ScrollBar.ScrollBarBehaviour.FORCE_HIDE)) {
/*     */       
/* 539 */       this.displayHorizontalScrollBar = true;
/* 540 */       viewPortHeight -= this.horizontalScrollBar.getMinHeight();
/*     */     } 
/*     */     
/* 543 */     if (innerWidgetMinHeight < contentHeight && !this.verticalScrollBarBehaviour.equals(ScrollBar.ScrollBarBehaviour.FORCE_DISPLAY)) {
/* 544 */       this.displayVerticalScrollBar = false;
/* 545 */     } else if (!this.verticalScrollBarBehaviour.equals(ScrollBar.ScrollBarBehaviour.FORCE_HIDE)) {
/*     */       
/* 547 */       this.displayVerticalScrollBar = true;
/* 548 */       viewPortWidth -= this.verticalScrollBar.getMinWidth();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 554 */     if (this.displayVerticalScrollBar && !this.displayHorizontalScrollBar && viewPortWidth < innerWidgetMinWidth && !this.horizontalScrollBarBehaviour.equals(ScrollBar.ScrollBarBehaviour.FORCE_HIDE)) {
/*     */       
/* 556 */       this.displayHorizontalScrollBar = true;
/* 557 */       viewPortHeight -= this.horizontalScrollBar.getMinHeight();
/*     */     } 
/*     */     
/* 560 */     if (!this.displayVerticalScrollBar && this.displayHorizontalScrollBar && viewPortHeight < innerWidgetMinHeight && !this.verticalScrollBarBehaviour.equals(ScrollBar.ScrollBarBehaviour.FORCE_HIDE)) {
/*     */       
/* 562 */       this.displayVerticalScrollBar = true;
/* 563 */       viewPortWidth -= this.verticalScrollBar.getMinWidth();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     if (innerWidgetMinWidth > viewPortWidth) {
/* 573 */       innerWidgetWidth = innerWidgetMinWidth;
/*     */     } else {
/* 575 */       innerWidgetWidth = viewPortWidth;
/*     */     } 
/* 577 */     if (innerWidgetMinHeight > viewPortHeight) {
/* 578 */       innerWidgetHeight = innerWidgetMinHeight;
/*     */     } else {
/* 580 */       innerWidgetHeight = viewPortHeight;
/*     */     } 
/* 582 */     this.innerWidget.setSize(new Dimension(innerWidgetWidth, innerWidgetHeight));
/* 583 */     this.innerWidget.layout();
/* 584 */     placeInnerWidgetHorizontally(this.horizontalScrollBar.getSlider().getValue());
/* 585 */     placeInnerWidgetVertically(this.verticalScrollBar.getSlider().getValue());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 590 */     if (this.displayHorizontalScrollBar) {
/*     */       
/* 592 */       this.horizontalScrollBar.setSize(viewPortWidth, this.horizontalScrollBar.getMinHeight());
/* 593 */       this.horizontalScrollBar.setXY(0, 0);
/* 594 */       this.horizontalScrollBar.layout();
/*     */ 
/*     */       
/* 597 */       double d = viewPortWidth / innerWidgetWidth;
/* 598 */       this.horizontalScrollBar.getSlider().setSize(d);
/* 599 */       d = 10.0D / (innerWidgetMinWidth - viewPortWidth);
/* 600 */       this.horizontalScrollBar.setButtonJump(d);
/*     */     } 
/*     */ 
/*     */     
/* 604 */     if (this.displayVerticalScrollBar) {
/*     */       
/* 606 */       this.verticalScrollBar.setSize(this.verticalScrollBar.getMinWidth(), viewPortHeight);
/* 607 */       this.verticalScrollBar.setXY(viewPortWidth, contentHeight - viewPortHeight);
/* 608 */       this.verticalScrollBar.layout();
/*     */ 
/*     */       
/* 611 */       double d = viewPortHeight / innerWidgetHeight;
/* 612 */       this.verticalScrollBar.getSlider().setSize(d);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget w) {
/* 623 */     setInnerWidget(w);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget w, int position) {
/* 628 */     setInnerWidget(w);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 633 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 638 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 644 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 649 */     return getParent().getPreviousWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAppearance(ScrollContainerAppearance appearance) {
/* 657 */     this.appearance = appearance;
/*     */   }
/*     */   
/*     */   public class ScrollContainerAppearance
/*     */     extends DecoratorAppearance
/*     */   {
/*     */     protected ScrollContainer scrollContainer;
/*     */     
/*     */     public ScrollContainerAppearance(ScrollContainer scrollContainer) {
/* 666 */       super(scrollContainer);
/* 667 */       this.scrollContainer = scrollContainer;
/*     */     }
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 672 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 677 */       IWidget innerWidget = this.scrollContainer.getInnerWidget();
/* 678 */       if (innerWidget == null)
/*     */         return; 
/* 680 */       ScrollBar horizontalScrollBar = this.scrollContainer.getHorizontalScrollBar();
/* 681 */       if (ScrollContainer.this.displayHorizontalScrollBar) {
/*     */         
/* 683 */         g.translate(horizontalScrollBar.getX(), horizontalScrollBar.getY());
/* 684 */         horizontalScrollBar.paint(g);
/* 685 */         g.translate(-horizontalScrollBar.getX(), -horizontalScrollBar.getY());
/*     */       } 
/*     */       
/* 688 */       ScrollBar verticalScrollBar = this.scrollContainer.getVerticalScrollBar();
/* 689 */       if (ScrollContainer.this.displayVerticalScrollBar) {
/*     */         
/* 691 */         g.translate(verticalScrollBar.getX(), verticalScrollBar.getY());
/* 692 */         verticalScrollBar.paint(g);
/* 693 */         g.translate(-verticalScrollBar.getX(), -verticalScrollBar.getY());
/*     */       } 
/*     */       
/* 696 */       int verticalSBMinWidth = !ScrollContainer.this.displayVerticalScrollBar ? 0 : verticalScrollBar.getMinWidth();
/* 697 */       int horizontalSBMinHeight = !ScrollContainer.this.displayHorizontalScrollBar ? 0 : horizontalScrollBar.getMinHeight();
/*     */       
/* 699 */       g.setClipSpace(0, horizontalSBMinHeight, getContentWidth() - verticalSBMinWidth, getContentHeight() - 
/* 700 */           horizontalSBMinHeight);
/*     */       
/* 702 */       g.translate(innerWidget.getX(), innerWidget.getY());
/* 703 */       innerWidget.paint(g);
/* 704 */       g.translate(-innerWidget.getX(), -innerWidget.getY());
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ScrollContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */