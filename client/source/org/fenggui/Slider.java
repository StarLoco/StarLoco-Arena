/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.IActivationListener;
/*     */ import org.fenggui.event.IDragAndDropListener;
/*     */ import org.fenggui.event.ISliderMovedListener;
/*     */ import org.fenggui.event.SliderMovedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ public class Slider
/*     */   extends ObservableWidget
/*     */   implements IBasicContainer
/*     */ {
/*     */   public static final String LABEL_DISABLED = "disabled";
/*     */   public static final String LABEL_DEFAULT = "default";
/*  51 */   private ArrayList<ISliderMovedListener> sliderMovedHook = new ArrayList<ISliderMovedListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private SliderMovedEvent sliderMoved = new SliderMovedEvent(this);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private double value = 0.0D;
/*  64 */   private double buttonSize = 0.0D;
/*  65 */   private SliderAppearance appearance = null;
/*  66 */   private Button sliderButton = null;
/*     */ 
/*     */   
/*     */   private boolean horizontal = true;
/*     */ 
/*     */   
/*  72 */   private double clickJump = 0.03D;
/*     */   
/*  74 */   private IDragAndDropListener dndListener = new SliderDnDListener(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/*  85 */     return this.value;
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
/*     */   public void setValue(double position) {
/*  97 */     if (position > 1.0D) position = 1.0D; 
/*  98 */     if (position < 0.0D) position = 0.0D; 
/*  99 */     if (this.value != position) {
/* 100 */       this.value = position;
/*     */       
/* 102 */       if (this.horizontal) {
/*     */         
/* 104 */         this.sliderButton.setX((int)((getAppearance().getContentWidth() - this.sliderButton.getWidth()) * this.value));
/*     */       }
/*     */       else {
/*     */         
/* 108 */         this.sliderButton.setY((int)((getAppearance().getContentHeight() - this.sliderButton.getHeight()) * this.value));
/*     */       } 
/*     */       
/* 111 */       fireSliderMovedEvent();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Button getSliderButton() {
/* 118 */     return this.sliderButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Slider(boolean horizontal) {
/* 129 */     this.horizontal = horizontal;
/*     */     
/* 131 */     this.sliderButton = new Button();
/* 132 */     this.sliderButton.setParent(this);
/* 133 */     this.sliderButton.setSize(10, 10);
/* 134 */     this.sliderButton.setXY(0, 0);
/*     */     
/* 136 */     this.appearance = new SliderAppearance(this);
/*     */     
/* 138 */     setTraversable(false);
/*     */     
/* 140 */     setupTheme(Slider.class);
/* 141 */     getAppearance().setEnabled("disabled", false);
/* 142 */     buildListeners();
/* 143 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Slider(InputOnlyStream stream) throws IOException, IOStreamException {
/* 148 */     process((InputOutputStream)stream);
/*     */   }
/*     */   
/*     */   private void buildListeners() {
/* 152 */     addActivationListener(new IActivationListener()
/*     */         {
/*     */           public void widgetActivationChanged(ActivationEvent activationEvent)
/*     */           {
/* 156 */             boolean enabled = activationEvent.isEnabled();
/* 157 */             Slider.this.getAppearance().setEnabled("disabled", !enabled);
/* 158 */             Slider.this.getAppearance().setEnabled("default", enabled);
/*     */             
/* 160 */             Slider.this.sliderButton.setEnabled(enabled);
/*     */             
/* 162 */             if (enabled) {
/* 163 */               if (Slider.this.getDisplay() != null) {
/* 164 */                 Slider.this.getDisplay().addDndListener(Slider.this.dndListener);
/*     */               }
/*     */             }
/* 167 */             else if (Slider.this.getDisplay() != null) {
/* 168 */               Slider.this.getDisplay().removeDndListener(Slider.this.dndListener);
/*     */             } 
/*     */           }
/*     */         });
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
/*     */   public IWidget getWidget(int x, int y) {
/* 205 */     if (!getAppearance().insideMargin(x, y))
/*     */     {
/* 207 */       return null;
/*     */     }
/*     */     
/* 210 */     if (this.sliderButton.getSize().contains(x - this.sliderButton.getX(), y - this.sliderButton.getY())) {
/* 211 */       return this.sliderButton;
/*     */     }
/* 213 */     return this;
/*     */   }
/*     */   private class SliderDnDListener implements IDragAndDropListener { private int deltaX; private int deltaY;
/*     */     
/*     */     private SliderDnDListener() {
/* 218 */       this.deltaX = 0;
/* 219 */       this.deltaY = 0;
/* 220 */       this.cacheDisplayX = -1;
/* 221 */       this.cacheDisplayY = -1;
/*     */     }
/*     */     private int cacheDisplayX; private int cacheDisplayY;
/*     */     public void select(int x, int y) {
/* 225 */       this.cacheDisplayX = Slider.this.getDisplayX();
/* 226 */       this.cacheDisplayY = Slider.this.getDisplayY();
/* 227 */       x -= this.cacheDisplayX;
/* 228 */       y -= this.cacheDisplayY;
/*     */       
/* 230 */       this.deltaX = Slider.this.getSliderStart() - x;
/* 231 */       this.deltaY = Slider.this.getSliderStart() - y;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drag(int x, int y) {
/* 236 */       if (Slider.this.horizontal) {
/*     */         
/* 238 */         x -= this.cacheDisplayX;
/* 239 */         y -= this.cacheDisplayY;
/*     */         
/* 241 */         x += this.deltaX;
/*     */         
/* 243 */         Slider.this.setValue(x / (Slider.this.getWidth() - Slider.this.sliderButton.getWidth()));
/*     */       }
/*     */       else {
/*     */         
/* 247 */         x -= this.cacheDisplayX;
/* 248 */         y -= this.cacheDisplayY;
/*     */         
/* 250 */         y += this.deltaY;
/*     */         
/* 252 */         Slider.this.setValue(y / (Slider.this.getHeight() - Slider.this.sliderButton.getHeight()));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void drop(int x, int y, IWidget dropOn) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int x, int y) {
/* 263 */       return w.equals(Slider.this.sliderButton);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 270 */     if (getDisplay() != null && isEnabled()) {
/* 271 */       getDisplay().addDndListener(this.dndListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 277 */     if (getDisplay() != null) {
/* 278 */       getDisplay().removeDndListener(this.dndListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSliderStart() {
/* 289 */     if (this.horizontal) {
/* 290 */       return (int)(this.value * (getWidth() - this.sliderButton.getWidth()));
/*     */     }
/* 292 */     return (int)(this.value * (getHeight() - this.sliderButton.getHeight()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/* 301 */     return this.horizontal;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getClickJump() {
/* 306 */     return this.clickJump;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClickJump(double clickJump) {
/* 311 */     this.clickJump = clickJump;
/*     */   }
/*     */ 
/*     */   
/*     */   public void layout() {
/* 316 */     int contentHeight = getAppearance().getContentHeight();
/* 317 */     int contentWidth = getAppearance().getContentWidth();
/*     */     
/* 319 */     if (this.horizontal) {
/*     */       
/* 321 */       int width = Math.max((int)(getAppearance().getContentWidth() * this.buttonSize), this.sliderButton.getMinWidth());
/* 322 */       if (width < 15) width = 15; 
/* 323 */       this.sliderButton.setWidth(width);
/* 324 */       this.sliderButton.setHeight(Math.max(contentHeight, 15));
/* 325 */       this.sliderButton.setY(contentHeight / 2 - this.sliderButton.getHeight() / 2);
/* 326 */       this.sliderButton.setX((int)((getAppearance().getContentWidth() - this.sliderButton.getWidth()) * this.value));
/*     */     }
/*     */     else {
/*     */       
/* 330 */       int height = Math.max((int)(getAppearance().getContentHeight() * this.buttonSize), this.sliderButton.getMinHeight());
/* 331 */       if (height < 15) height = 15; 
/* 332 */       this.sliderButton.setWidth(Math.max(contentWidth, 15));
/* 333 */       this.sliderButton.setHeight(height);
/* 334 */       this.sliderButton.setX(contentWidth / 2 - this.sliderButton.getWidth() / 2);
/* 335 */       this.sliderButton.setY((int)((getAppearance().getContentHeight() - this.sliderButton.getHeight()) * this.value));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mousePressedEvent) {
/* 342 */     super.mousePressed(mousePressedEvent);
/* 343 */     if (isEnabled()) {
/* 344 */       if (isHorizontal()) {
/*     */         
/* 346 */         int x = mousePressedEvent.getDisplayX() - getDisplayX();
/* 347 */         double size = this.sliderButton.getWidth() / getAppearance().getContentMinWidth();
/* 348 */         if (x < getSliderStart())
/*     */         {
/* 350 */           setValue(getValue() - size);
/*     */         }
/*     */         else
/*     */         {
/* 354 */           setValue(getValue() + size);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 359 */         int y = mousePressedEvent.getDisplayY() - getDisplayY();
/* 360 */         double size = this.sliderButton.getHeight() / getAppearance().getContentHeight();
/*     */         
/* 362 */         if (y < getSliderStart()) {
/*     */           
/* 364 */           setValue(getValue() - size);
/*     */         }
/*     */         else {
/*     */           
/* 368 */           setValue(getValue() + size);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(double d) {
/* 381 */     if (d < 0.0D) { d = 0.0D; }
/* 382 */     else if (d > 1.0D) { d = 1.0D; }
/*     */     
/* 384 */     this.buttonSize = d;
/*     */     
/* 386 */     if (this.horizontal) {
/*     */       
/* 388 */       int size = (int)(getAppearance().getContentWidth() * this.buttonSize);
/* 389 */       if (size < 15) size = 15; 
/* 390 */       this.sliderButton.setWidth(size);
/*     */     }
/*     */     else {
/*     */       
/* 394 */       int size = (int)(getAppearance().getContentHeight() * this.buttonSize);
/* 395 */       if (size < 15) size = 15; 
/* 396 */       this.sliderButton.setHeight(size);
/*     */     } 
/*     */ 
/*     */     
/* 400 */     setValue(getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 405 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 410 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 415 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 420 */     return getParent().getPreviousWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public SliderAppearance getAppearance() {
/* 425 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 431 */     if (this.horizontal) {
/*     */       
/* 433 */       setMinSize(30, Math.max(this.sliderButton.getMinHeight(), 15));
/*     */     }
/*     */     else {
/*     */       
/* 437 */       setMinSize(Math.max(this.sliderButton.getMinWidth(), 15), 30);
/*     */     } 
/*     */     
/* 440 */     if (getParent() != null) getParent().updateMinSize();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSliderMovedListener(ISliderMovedListener l) {
/* 449 */     if (!this.sliderMovedHook.contains(l))
/*     */     {
/* 451 */       this.sliderMovedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSliderMovedListener(ISliderMovedListener l) {
/* 461 */     this.sliderMovedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireSliderMovedEvent() {
/* 469 */     for (ISliderMovedListener l : this.sliderMovedHook)
/*     */     {
/* 471 */       l.sliderMoved(this.sliderMoved);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 478 */     super.process(stream);
/*     */ 
/*     */ 
/*     */     
/* 482 */     if (this.horizontal) {
/*     */       
/* 484 */       stream.processInherentChild("HorizontalSliderButton", this.sliderButton);
/* 485 */       stream.processInherentChild("HorizontalAppearance", this.appearance);
/*     */     }
/*     */     else {
/*     */       
/* 489 */       stream.processInherentChild("VerticalSliderButton", this.sliderButton);
/* 490 */       stream.processInherentChild("VerticalAppearance", this.appearance);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Slider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */