/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.ButtonPressedEvent;
/*     */ import org.fenggui.event.IActivationListener;
/*     */ import org.fenggui.event.IButtonPressedListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Timer;
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
/*     */ public class ScrollBar
/*     */   extends ObservableWidget
/*     */   implements IBasicContainer
/*     */ {
/*     */   public static final String LABEL_DEFAULT = "default";
/*     */   public static final String LABEL_DISABLED = "disabled";
/*     */   private boolean horizontal = true;
/*     */   private Button increaseBtn;
/*     */   private Button decreaseBtn;
/*  61 */   private ScrollBarAppearance appearance = null;
/*     */ 
/*     */ 
/*     */   
/*  65 */   private Slider slider = null;
/*     */   
/*  67 */   private double buttonJump = 0.05D;
/*     */   
/*  69 */   private Timer autoScrollDelay = new Timer(2, 500L);
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ScrollBarBehaviour
/*     */   {
/*  80 */     WHEN_NEEDED, FORCE_DISPLAY, FORCE_HIDE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ScrollBar() {
/*  85 */     this(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScrollBar(InputOnlyStream stream) throws IOException, IOStreamException {
/*  90 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBar(boolean horizontal) {
/* 101 */     this.horizontal = horizontal;
/*     */     
/* 103 */     this.slider = new Slider(horizontal);
/* 104 */     this.slider.setParent(this);
/*     */     
/* 106 */     this.increaseBtn = new Button();
/* 107 */     this.increaseBtn.setParent(this);
/* 108 */     this.increaseBtn.addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mousePressedEvent)
/*     */           {
/* 112 */             ScrollBar.this.autoScrollDelay.reset();
/*     */           }
/*     */         });
/* 115 */     this.decreaseBtn = new Button();
/* 116 */     this.decreaseBtn.setParent(this);
/* 117 */     this.decreaseBtn.addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mousePressedEvent)
/*     */           {
/* 121 */             ScrollBar.this.autoScrollDelay.reset();
/*     */           }
/*     */         });
/* 124 */     this.increaseBtn.addButtonPressedListener(new IButtonPressedListener()
/*     */         {
/*     */           public void buttonPressed(ButtonPressedEvent e)
/*     */           {
/* 128 */             ScrollBar.this.slider.setValue(ScrollBar.this.slider.getValue() + ScrollBar.this.buttonJump);
/*     */           }
/*     */         });
/*     */     
/* 132 */     this.decreaseBtn.addButtonPressedListener(new IButtonPressedListener()
/*     */         {
/*     */           
/*     */           public void buttonPressed(ButtonPressedEvent e)
/*     */           {
/* 137 */             ScrollBar.this.slider.setValue(ScrollBar.this.slider.getValue() - ScrollBar.this.buttonJump);
/*     */           }
/*     */         });
/*     */     
/* 141 */     this.appearance = new ScrollBarAppearance(this);
/*     */     
/* 143 */     setTraversable(false);
/*     */     
/* 145 */     setupTheme(ScrollBar.class);
/* 146 */     getAppearance().setEnabled("disabled", false);
/* 147 */     buildListeners();
/* 148 */     updateMinSize();
/*     */   }
/*     */   
/*     */   private void buildListeners() {
/* 152 */     addActivationListener(new IActivationListener()
/*     */         {
/*     */           public void widgetActivationChanged(ActivationEvent activationEvent)
/*     */           {
/* 156 */             boolean enabled = activationEvent.isEnabled();
/* 157 */             ScrollBar.this.getAppearance().setEnabled("disabled", !enabled);
/* 158 */             ScrollBar.this.getAppearance().setEnabled("default", enabled);
/*     */             
/* 160 */             ScrollBar.this.increaseBtn.setEnabled(enabled);
/* 161 */             ScrollBar.this.decreaseBtn.setEnabled(enabled);
/* 162 */             ScrollBar.this.slider.setEnabled(enabled);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 170 */     this.increaseBtn.addedToWidgetTree();
/* 171 */     this.slider.addedToWidgetTree();
/* 172 */     this.decreaseBtn.addedToWidgetTree();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 180 */     this.increaseBtn.removedFromWidgetTree();
/* 181 */     this.slider.removedFromWidgetTree();
/* 182 */     this.decreaseBtn.removedFromWidgetTree();
/*     */   }
/*     */   
/*     */   public Slider getSlider() {
/* 186 */     return this.slider;
/*     */   }
/*     */   
/*     */   public boolean isHorizontal() {
/* 190 */     return this.horizontal;
/*     */   }
/*     */   
/*     */   public Button getDecreaseButton() {
/* 194 */     return this.decreaseBtn;
/*     */   }
/*     */   
/*     */   public Button getIncreaseButton() {
/* 198 */     return this.increaseBtn;
/*     */   }
/*     */   
/*     */   public double getButtonJump() {
/* 202 */     return this.buttonJump;
/*     */   }
/*     */   
/*     */   public void setButtonJump(double buttonJump) {
/* 206 */     this.buttonJump = buttonJump;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 213 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 220 */     if (this.enabled == enabled) {
/*     */       return;
/*     */     }
/*     */     
/* 224 */     this.enabled = enabled;
/* 225 */     this.increaseBtn.setEnabled(enabled);
/* 226 */     this.decreaseBtn.setEnabled(enabled);
/* 227 */     this.slider.setEnabled(enabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 234 */     this.decreaseBtn.setSizeToMinSize();
/* 235 */     this.increaseBtn.setSizeToMinSize();
/*     */     
/* 237 */     int contentHeight = getAppearance().getContentHeight();
/* 238 */     int contentWidth = getAppearance().getContentWidth();
/*     */     
/* 240 */     if (this.horizontal) {
/*     */       
/* 242 */       this.decreaseBtn.setHeight(contentHeight);
/* 243 */       this.increaseBtn.setHeight(contentHeight);
/* 244 */       this.decreaseBtn.setXY(0, 0);
/* 245 */       this.increaseBtn.setXY(contentWidth - this.increaseBtn.getWidth(), 0);
/* 246 */       this.slider.setXY(this.decreaseBtn.getWidth(), 0);
/* 247 */       this.slider.setSize(contentWidth - this.increaseBtn.getWidth() + this.decreaseBtn.getWidth(), contentHeight);
/* 248 */       this.slider.layout();
/*     */     }
/*     */     else {
/*     */       
/* 252 */       this.increaseBtn.setWidth(contentWidth);
/* 253 */       this.decreaseBtn.setWidth(contentWidth);
/* 254 */       this.decreaseBtn.setXY(0, 0);
/* 255 */       this.increaseBtn.setXY(0, contentHeight - this.decreaseBtn.getHeight());
/* 256 */       this.slider.setXY(0, this.decreaseBtn.getHeight());
/* 257 */       this.slider.setSize(contentWidth, contentHeight - this.decreaseBtn.getHeight() + this.increaseBtn.getHeight());
/* 258 */       this.slider.layout();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBarAppearance getAppearance() {
/* 267 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 275 */     if (!getAppearance().insideMargin(x, y)) return null;
/*     */     
/* 277 */     x -= getAppearance().getLeftMargins();
/* 278 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 280 */     if (this.decreaseBtn.getSize().contains(x - this.decreaseBtn.getX(), y - this.decreaseBtn.getY())) return this.decreaseBtn; 
/* 281 */     if (this.increaseBtn.getSize().contains(x - this.increaseBtn.getX(), y - this.increaseBtn.getY())) return this.increaseBtn; 
/* 282 */     if (this.slider.getSize().contains(x - this.slider.getX(), y - this.slider.getY())) return this.slider.getWidget(x - this.slider.getX(), y - this.slider.getY());
/*     */     
/* 284 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class ScrollBarAppearance
/*     */     extends DecoratorAppearance
/*     */   {
/*     */     public ScrollBarAppearance(ScrollBar w) {
/* 292 */       super(w);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 298 */       if (ScrollBar.this.isHorizontal()) {
/*     */         
/* 300 */         int height = Math.max(ScrollBar.this.getIncreaseButton().getMinHeight(), 
/* 301 */             Math.max(ScrollBar.this.getDecreaseButton().getMinHeight(), 
/* 302 */               ScrollBar.this.getSlider().getMinHeight()));
/* 303 */         return new Dimension(
/* 304 */             ScrollBar.this.getIncreaseButton().getMinWidth() + 
/* 305 */             ScrollBar.this.getDecreaseButton().getMinWidth() + 
/* 306 */             ScrollBar.this.getSlider().getMinWidth(), height);
/*     */       } 
/*     */ 
/*     */       
/* 310 */       return new Dimension(
/* 311 */           Math.max(ScrollBar.this.getIncreaseButton().getMinWidth(), 
/* 312 */             Math.max(ScrollBar.this.getDecreaseButton().getMinWidth(), 
/* 313 */               ScrollBar.this.getSlider().getMinWidth())), 
/* 314 */           ScrollBar.this.getIncreaseButton().getMinHeight() + 
/* 315 */           ScrollBar.this.getDecreaseButton().getMinHeight() + 
/* 316 */           ScrollBar.this.getSlider().getMinHeight());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 323 */       g.translate(ScrollBar.this.decreaseBtn.getX(), ScrollBar.this.decreaseBtn.getY());
/* 324 */       ScrollBar.this.decreaseBtn.paint(g);
/* 325 */       g.translate(-ScrollBar.this.decreaseBtn.getX(), -ScrollBar.this.decreaseBtn.getY());
/*     */       
/* 327 */       g.translate(ScrollBar.this.slider.getX(), ScrollBar.this.slider.getY());
/* 328 */       ScrollBar.this.slider.paint(g);
/* 329 */       g.translate(-ScrollBar.this.slider.getX(), -ScrollBar.this.slider.getY());
/*     */       
/* 331 */       g.translate(ScrollBar.this.increaseBtn.getX(), ScrollBar.this.increaseBtn.getY());
/* 332 */       ScrollBar.this.increaseBtn.paint(g);
/* 333 */       g.translate(-ScrollBar.this.increaseBtn.getX(), -ScrollBar.this.increaseBtn.getY());
/*     */ 
/*     */       
/* 336 */       if (ScrollBar.this.increaseBtn.isPressed() && ScrollBar.this.autoScrollDelay.getState() == 1) {
/*     */         
/* 338 */         ScrollBar.this.slider.setValue(ScrollBar.this.slider.getValue() + ScrollBar.this.buttonJump / 10.0D);
/* 339 */         ScrollBar.this.autoScrollDelay.setState(1);
/*     */       }
/* 341 */       else if (ScrollBar.this.decreaseBtn.isPressed() && ScrollBar.this.autoScrollDelay.getState() == 1) {
/*     */         
/* 343 */         ScrollBar.this.slider.setValue(ScrollBar.this.slider.getValue() - ScrollBar.this.buttonJump / 10.0D);
/* 344 */         ScrollBar.this.autoScrollDelay.setState(1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 352 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 357 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 362 */     return getParent().getNextWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 367 */     return getParent().getPreviousWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 373 */     super.process(stream);
/*     */     
/* 375 */     stream.processInherentChild("Slider", this.slider);
/*     */     
/* 377 */     if (this.horizontal) {
/*     */       
/* 379 */       stream.processInherentChild("ScrollRightButton", this.increaseBtn);
/* 380 */       stream.processInherentChild("ScrollLeftButton", this.decreaseBtn);
/*     */     }
/*     */     else {
/*     */       
/* 384 */       stream.processInherentChild("ScrollUpButton", this.increaseBtn);
/* 385 */       stream.processInherentChild("ScrollDownButton", this.decreaseBtn);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\ScrollBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */