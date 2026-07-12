/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IDragAndDropListener;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Pixmap;
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
/*     */ public class SplitContainer
/*     */   extends StandardWidget
/*     */   implements IContainer
/*     */ {
/*  44 */   private Pixmap pixmap = null;
/*     */   
/*     */   private boolean horizontal = true;
/*  47 */   private IWidget firstWidget = null;
/*  48 */   private IWidget secondWidget = null;
/*  49 */   private int barSize = 10;
/*     */   
/*  51 */   private SplitContainerDndListener dndListener = null;
/*  52 */   private SplitContainerAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private int value = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SplitContainer(boolean horizontal) {
/*  65 */     this.horizontal = horizontal;
/*  66 */     this.dndListener = new SplitContainerDndListener(this);
/*     */     
/*  68 */     this.appearance = new SplitContainerAppearance(this);
/*  69 */     setupTheme(SplitContainer.class);
/*  70 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public SplitContainer() {
/*  75 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SplitContainerAppearance getAppearance() {
/*  82 */     return this.appearance;
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  86 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(int value) {
/*  90 */     this.value = value;
/*     */   }
/*     */   
/*     */   public int getBarSize() {
/*  94 */     return this.barSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBarSize(int barSize) {
/*  99 */     this.barSize = barSize;
/*     */   }
/*     */   
/*     */   public IWidget getFirstWidget() {
/* 103 */     return this.firstWidget;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFirstWidget(Widget firstWidget) {
/* 108 */     this.firstWidget = firstWidget;
/*     */     
/* 110 */     if (this.firstWidget != null) {
/*     */       
/* 112 */       this.firstWidget.removedFromWidgetTree();
/* 113 */       this.firstWidget.setParent(null);
/* 114 */       if (getDisplay() != null) getDisplay().focusedWidgetValityCheck();
/*     */     
/*     */     } 
/* 117 */     if (firstWidget != null) {
/*     */       
/* 119 */       firstWidget.setParent(this);
/* 120 */       firstWidget.addedToWidgetTree();
/*     */     } 
/*     */     
/* 123 */     updateMinSize();
/*     */   }
/*     */   
/*     */   public IWidget getSecondWidget() {
/* 127 */     return this.secondWidget;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecondWidget(Widget secondWidget) {
/* 132 */     this.secondWidget = secondWidget;
/*     */     
/* 134 */     if (this.secondWidget != null) {
/*     */       
/* 136 */       this.secondWidget.removedFromWidgetTree();
/* 137 */       this.secondWidget.setParent(null);
/* 138 */       if (getDisplay() != null) getDisplay().focusedWidgetValityCheck();
/*     */     
/*     */     } 
/* 141 */     if (secondWidget != null) {
/*     */       
/* 143 */       secondWidget.setParent(this);
/* 144 */       secondWidget.addedToWidgetTree();
/*     */     } 
/*     */     
/* 147 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 154 */     getDisplay().addDndListener(this.dndListener);
/*     */     
/* 156 */     if (this.firstWidget != null) this.firstWidget.addedToWidgetTree(); 
/* 157 */     if (this.secondWidget != null) this.secondWidget.addedToWidgetTree();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 163 */     getDisplay().removeDndListener(this.dndListener);
/*     */     
/* 165 */     if (this.firstWidget != null) this.firstWidget.addedToWidgetTree(); 
/* 166 */     if (this.secondWidget != null) this.secondWidget.addedToWidgetTree(); 
/*     */   }
/*     */   
/*     */   public boolean isHorizontal() {
/* 170 */     return this.horizontal;
/*     */   }
/*     */ 
/*     */   
/*     */   private int keepSliderInRange(int newValue) {
/* 175 */     int contentHeight = getAppearance().getContentHeight();
/* 176 */     int contentWidth = getAppearance().getContentWidth();
/*     */     
/* 178 */     if (this.horizontal) {
/*     */       
/* 180 */       int firstMinHeight = (this.firstWidget != null) ? this.firstWidget.getMinSize().getHeight() : 0;
/* 181 */       int secondMinHeight = (this.secondWidget != null) ? this.secondWidget.getMinSize().getHeight() : 0;
/*     */       
/* 183 */       if (newValue < firstMinHeight) {
/* 184 */         return firstMinHeight;
/*     */       }
/* 186 */       if (newValue > contentHeight - secondMinHeight - this.barSize) {
/* 187 */         return contentHeight - secondMinHeight - this.barSize;
/*     */       }
/*     */     } else {
/*     */       
/* 191 */       int firstMinWidth = (this.firstWidget != null) ? this.firstWidget.getMinSize().getWidth() : 0;
/* 192 */       int secondMinWidth = (this.secondWidget != null) ? this.secondWidget.getMinSize().getWidth() : 0;
/*     */       
/* 194 */       if (newValue < firstMinWidth) {
/* 195 */         return firstMinWidth;
/*     */       }
/* 197 */       if (newValue > contentWidth - secondMinWidth - this.barSize) {
/* 198 */         return contentWidth - secondMinWidth - this.barSize;
/*     */       }
/*     */     } 
/*     */     
/* 202 */     return newValue;
/*     */   }
/*     */   
/*     */   class SplitContainerDndListener
/*     */     implements IDragAndDropListener
/*     */   {
/* 208 */     int oldValue = -1;
/*     */     
/* 210 */     private SplitContainer thizz = null;
/*     */ 
/*     */     
/*     */     public SplitContainerDndListener(SplitContainer mom) {
/* 214 */       this.thizz = mom;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int x, int y) {
/* 219 */       return w.equals(this.thizz);
/*     */     }
/*     */ 
/*     */     
/*     */     public void select(int displayX, int displayY) {
/* 224 */       if (SplitContainer.this.horizontal) {
/* 225 */         this.oldValue = displayY;
/*     */       } else {
/* 227 */         this.oldValue = displayX;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void drag(int displayX, int displayY) {
/* 232 */       if (SplitContainer.this.horizontal) {
/*     */         
/* 234 */         SplitContainer.this.value = SplitContainer.this.value + displayY - this.oldValue;
/* 235 */         this.oldValue = displayY;
/*     */       }
/*     */       else {
/*     */         
/* 239 */         SplitContainer.this.value = SplitContainer.this.value + displayX - this.oldValue;
/* 240 */         this.oldValue = displayX;
/*     */       } 
/*     */       
/* 243 */       SplitContainer.this.value = SplitContainer.this.keepSliderInRange(SplitContainer.this.value);
/*     */       
/* 245 */       SplitContainer.this.layout();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drop(int x, int y, IWidget droppedOn) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 259 */     if (!getAppearance().insideMargin(x, y))
/*     */     {
/* 261 */       return null;
/*     */     }
/*     */     
/* 264 */     x -= getAppearance().getLeftMargins();
/* 265 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 267 */     if (this.firstWidget != null && this.firstWidget.getSize().contains(x - this.firstWidget.getX(), y - this.firstWidget.getY())) {
/* 268 */       return this.firstWidget.getWidget(x - this.firstWidget.getX(), y - this.firstWidget.getY());
/*     */     }
/* 270 */     if (this.secondWidget != null && this.secondWidget.getSize().contains(x - this.secondWidget.getX(), y - this.secondWidget.getY())) {
/* 271 */       return this.secondWidget.getWidget(x - this.secondWidget.getX(), y - this.secondWidget.getY());
/*     */     }
/* 273 */     return this;
/*     */   }
/*     */   
/*     */   public Pixmap getPixmap() {
/* 277 */     return this.pixmap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPixmap(Pixmap pixmap) {
/* 282 */     this.pixmap = pixmap;
/*     */     
/* 284 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 290 */     int contentHeight = getAppearance().getContentHeight();
/* 291 */     int contentWidth = getAppearance().getContentWidth();
/*     */     
/* 293 */     if (this.horizontal) {
/*     */       
/* 295 */       if (this.value < 0) this.value = (contentHeight - this.barSize) / 2;
/*     */       
/* 297 */       if (this.firstWidget != null) {
/*     */         
/* 299 */         this.firstWidget.setX(0);
/* 300 */         this.firstWidget.setY(0);
/* 301 */         this.firstWidget.setSize(new Dimension(contentWidth, this.value));
/*     */       } 
/*     */       
/* 304 */       if (this.secondWidget != null)
/*     */       {
/* 306 */         this.secondWidget.setX(0);
/* 307 */         this.secondWidget.setY(this.value + this.barSize);
/* 308 */         this.secondWidget.setSize(new Dimension(contentWidth, contentHeight - this.value - this.barSize));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 313 */       if (this.value < 0) this.value = (contentWidth - this.barSize) / 2;
/*     */       
/* 315 */       if (this.firstWidget != null) {
/*     */         
/* 317 */         this.firstWidget.setX(0);
/* 318 */         this.firstWidget.setY(0);
/* 319 */         this.firstWidget.setSize(new Dimension(this.value, contentHeight));
/*     */       } 
/*     */       
/* 322 */       if (this.secondWidget != null) {
/*     */         
/* 324 */         this.secondWidget.setX(this.value + this.barSize);
/* 325 */         this.secondWidget.setY(0);
/* 326 */         this.secondWidget.setSize(new Dimension(contentWidth - this.value - this.barSize, contentHeight));
/*     */       } 
/*     */     } 
/*     */     
/* 330 */     if (this.firstWidget != null) this.firstWidget.layout(); 
/* 331 */     if (this.secondWidget != null) this.secondWidget.layout();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {
/* 337 */     if (this.horizontal) {
/* 338 */       Binding.getInstance().getCursorFactory().getVerticalResizeCursor().show();
/*     */     } else {
/* 340 */       Binding.getInstance().getCursorFactory().getHorizontalResizeCursor().show();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 346 */     Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget w) {
/* 353 */     if (this.firstWidget == null) {
/*     */       
/* 355 */       this.firstWidget = w;
/*     */       
/*     */       return;
/*     */     } 
/* 359 */     this.secondWidget = w;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget w, int position) {
/* 364 */     if (position <= 0) {
/* 365 */       this.firstWidget = w;
/*     */       return;
/*     */     } 
/* 368 */     if (position >= 1) {
/* 369 */       this.secondWidget = w;
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 376 */     if (start.equals(this.firstWidget) && this.secondWidget.isTraversable())
/* 377 */       return this.secondWidget; 
/* 378 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 383 */     if (start.equals(this.secondWidget) && this.firstWidget.isTraversable())
/* 384 */       return this.firstWidget; 
/* 385 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 390 */     if (start.equals(this.firstWidget))
/* 391 */       return this.secondWidget; 
/* 392 */     return getParent().getNextWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 397 */     if (start.equals(this.secondWidget))
/* 398 */       return this.firstWidget; 
/* 399 */     return getParent().getNextWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 408 */     super.process(stream);
/*     */     
/* 410 */     if (getFirstWidget() instanceof StandardWidget || getFirstWidget() == null)
/*     */     {
/* 412 */       if (stream.startSubcontext("firstWidget")) {
/*     */         
/* 414 */         this.firstWidget = (StandardWidget)stream.processChild((StandardWidget)this.firstWidget, FengGUI.TYPE_REGISTRY);
/* 415 */         stream.endSubcontext();
/*     */       } 
/*     */     }
/*     */     
/* 419 */     if (getSecondWidget() instanceof StandardWidget || getSecondWidget() == null)
/*     */     {
/* 421 */       if (stream.startSubcontext("secondWidget")) {
/*     */         
/* 423 */         this.secondWidget = (StandardWidget)stream.processChild((StandardWidget)this.secondWidget, FengGUI.TYPE_REGISTRY);
/* 424 */         stream.endSubcontext();
/*     */       } 
/*     */     }
/*     */     
/* 428 */     if (isHorizontal()) {
/* 429 */       setPixmap((Pixmap)stream.processChild("HorizontalPixmap", (IOStreamSaveable)getPixmap(), null, Pixmap.class));
/*     */     } else {
/* 431 */       setPixmap((Pixmap)stream.processChild("VerticalPixmap", (IOStreamSaveable)getPixmap(), null, Pixmap.class));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraversable() {
/* 438 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusChanged(FocusEvent focusEvent) {
/* 444 */     super.focusChanged(focusEvent);
/*     */     
/* 446 */     if (focusEvent.isFocusGained())
/*     */     {
/* 448 */       getDisplay().setFocusedWidget(this.firstWidget);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\SplitContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */