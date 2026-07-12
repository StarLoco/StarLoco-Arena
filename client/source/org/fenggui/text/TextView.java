/*     */ package org.fenggui.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.ITextWidget;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.ScrollContainer;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.ITextChangedListener;
/*     */ import org.fenggui.event.TextChangedEvent;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Rectangle;
/*     */ import org.fenggui.util.WritableDimension;
/*     */ import org.fenggui.util.WritablePoint;
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
/*     */ public class TextView
/*     */   extends ObservableWidget
/*     */   implements ITextWidget
/*     */ {
/*  51 */   private int minWidth = 10;
/*     */   
/*  53 */   private ArrayList<ITextChangedListener> textChangedHook = new ArrayList<ITextChangedListener>();
/*     */   
/*     */   private ArrayList<TextRun> runs;
/*     */   
/*     */   private TextStyle defaulStyle;
/*  58 */   private WritablePoint nextDrawPoint = new WritablePoint(0, 0);
/*  59 */   private WritableDimension scratchDimension = new WritableDimension(0, 0);
/*     */   
/*     */   private int fullHeight;
/*     */   
/*  63 */   private TextViewAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextView() {
/*  70 */     this.appearance = new TextViewAppearance(this);
/*  71 */     this.runs = new ArrayList<TextRun>();
/*  72 */     this.defaulStyle = new TextStyle(Font.getDefaultFont(), Color.BLACK);
/*     */   }
/*     */ 
/*     */   
/*     */   void buildLogic() {
/*  77 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */ 
/*     */           
/*     */           public void mousePressed(MousePressedEvent mp)
/*     */           {
/*  83 */             int x = mp.getDisplayX() - TextView.this.getDisplayX() - TextView.this.getAppearance().getPadding().getLeft();
/*  84 */             int y = mp.getDisplayY() - TextView.this.getDisplayY() - TextView.this.getAppearance().getPadding().getBottom() - 
/*  85 */               TextView.this.getAppearance().getContentHeight();
/*     */             
/*  87 */             TextRun run = TextView.this.getRun(x, y);
/*  88 */             if (run != null)
/*     */             {
/*  90 */               System.out.println("Click on : " + new String(run.getChars()));
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextStyle getDefaulStyle() {
/* 101 */     return this.defaulStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStyle(TextStyle style) {
/* 111 */     this.defaulStyle = style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 121 */     this.defaulStyle.setFont(font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color color) {
/* 131 */     this.defaulStyle.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinWidth() {
/* 138 */     return this.minWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinWidth(int minWidth) {
/* 145 */     this.minWidth = minWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendText(TextRun run) {
/* 155 */     this.runs.add(run);
/* 156 */     if (getParent() != null)
/*     */     {
/* 158 */       prepare(run);
/*     */     }
/* 160 */     processTextChanged(new String(run.getChars()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendText(String text, TextStyle style) {
/* 171 */     if (text.length() != 0)
/*     */     {
/* 173 */       appendText(new TextRun(text, style));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendText(String text) {
/* 184 */     appendText(text, this.defaulStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextLine(String text, TextStyle style) {
/* 195 */     if (this.runs.isEmpty()) {
/*     */       
/* 197 */       appendText(text, style);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       appendText('\n' + text, style);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextLine(String text) {
/* 212 */     addTextLine(text, this.defaulStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 222 */     StringBuilder sb = new StringBuilder();
/* 223 */     for (TextRun run : this.runs)
/*     */     {
/* 225 */       sb.append(run.getChars());
/*     */     }
/* 227 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 237 */     this.runs.clear();
/* 238 */     appendText(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 249 */     int oldHeight = this.fullHeight;
/* 250 */     prepareAll();
/* 251 */     if (oldHeight != this.fullHeight) {
/* 252 */       getParent().layout();
/*     */     } else {
/* 254 */       super.layout();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 264 */     super.addedToWidgetTree();
/* 265 */     prepareAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 276 */     setMinSize(getAppearance().getMinSizeHint());
/*     */     
/* 278 */     if (getParent() != null && getParent() instanceof ScrollContainer) {
/*     */       
/* 280 */       ((ScrollContainer)getParent()).layout();
/*     */     }
/* 282 */     else if (getParent() != null) {
/*     */       
/* 284 */       getParent().updateMinSize();
/*     */     } 
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
/*     */   public TextRun getRun(int x, int y) {
/* 297 */     for (TextRun run : this.runs) {
/*     */       
/* 299 */       if (run.contains(x, y)) return run; 
/*     */     } 
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepareAll() {
/* 309 */     this.scratchDimension.setSize(0, 0);
/* 310 */     this.nextDrawPoint.setX(0);
/* 311 */     this.nextDrawPoint.setY(0);
/* 312 */     for (TextRun run : this.runs)
/*     */     {
/* 314 */       prepare(run);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepare(TextRun run) {
/* 324 */     int width = Math.max(this.minWidth, getAppearance().getContentWidth());
/* 325 */     run.prepare(width, this.scratchDimension, this.nextDrawPoint);
/* 326 */     this.fullHeight = this.scratchDimension.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processTextChanged(String text) {
/* 334 */     updateMinSize();
/*     */     
/* 336 */     fireTextChangedEvent(text);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextViewAppearance getAppearance() {
/* 342 */     return this.appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   public class TextViewAppearance
/*     */     extends DecoratorAppearance
/*     */   {
/*     */     public TextViewAppearance(TextView w) {
/* 350 */       super((IWidget)w);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 356 */       return new Dimension(TextView.this.minWidth, TextView.this.fullHeight);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 363 */       int y = getContentHeight();
/* 364 */       int x = 0;
/*     */       
/* 366 */       x += g.getTranslation().getX();
/* 367 */       y += g.getTranslation().getY();
/*     */       
/* 369 */       Rectangle clipRect = new Rectangle(g.getClipSpace());
/* 370 */       clipRect.setX(0);
/* 371 */       clipRect.setY(clipRect.getY() - TextView.this.getDisplayY() - getContentHeight());
/*     */ 
/*     */       
/* 374 */       for (TextRun run : TextView.this.runs) {
/*     */         
/* 376 */         if (run.getBoundingRect().intersect(clipRect))
/*     */         {
/* 378 */           run.paint(g, x, y);
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
/*     */   public void addSelectionChangedListener(ITextChangedListener l) {
/* 391 */     if (!this.textChangedHook.contains(l))
/*     */     {
/* 393 */       this.textChangedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelectionChangedListener(ITextChangedListener l) {
/* 403 */     this.textChangedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireTextChangedEvent(String text) {
/* 411 */     TextChangedEvent e = new TextChangedEvent(this, text);
/*     */     
/* 413 */     for (ITextChangedListener l : this.textChangedHook)
/*     */     {
/* 415 */       l.textChanged(e);
/*     */     }
/*     */     
/* 418 */     if (getDisplay() != null)
/* 419 */       getDisplay().fireGlobalEventListener((Event)e); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\text\TextView.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */