/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.event.ButtonPressedEvent;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.IEventListener;
/*     */ import org.fenggui.event.IMenuClosedListener;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
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
/*     */ public class Popup
/*     */   extends Container
/*     */ {
/*     */   private PopupAppearance m_appearance;
/*  33 */   private boolean m_show = false;
/*  34 */   private IEventListener m_currentHideListener = null;
/*  35 */   private ArrayList<IMenuClosedListener> m_mcl = new ArrayList();
/*  36 */   private Alignment m_hotSpotPosition = Alignment.NORTH_WEST;
/*  37 */   private Alignment m_align = Alignment.SOUTH_EAST;
/*     */   private IWidget m_clientWidget;
/*  39 */   private boolean m_horizontal = false;
/*     */   
/*     */   private long m_startTime;
/*     */   
/*     */   public Popup()
/*     */   {
/*  45 */     this.m_appearance = new PopupAppearance(this);
/*     */   }
/*     */   
/*     */   public void addedToWidgetTree() {
/*  49 */     this.m_currentHideListener = new IEventListener() {
/*     */       public void processEvent(Event e) {
/*  51 */         long delta = System.currentTimeMillis() - Popup.this.m_startTime;
/*  52 */         if ((((e instanceof MouseReleasedEvent)) || ((e instanceof ButtonPressedEvent))) && (System.currentTimeMillis() - Popup.this.m_startTime > 100L)) {
/*  53 */           Popup.this.hide();
/*     */         }
/*     */       }
/*  56 */     };
/*  57 */     getDisplay().addGlobalEventListener(this.m_currentHideListener);
/*     */   }
/*     */   
/*     */   public void removedFromWidgetTree() {
/*  61 */     getDisplay().removeGlobalEventListener(this.m_currentHideListener);
/*     */   }
/*     */   
/*     */   public void show() {
/*  65 */     this.m_show = true;
/*  66 */     this.m_startTime = System.currentTimeMillis();
/*     */     
/*  68 */     Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/*     */     
/*  70 */     if (getParent() == null) {
/*  71 */       display.addWidget(this);
/*     */     }
/*  73 */     layout();
/*     */   }
/*     */   
/*     */   public void hide() {
/*  77 */     this.m_show = false;
/*     */   }
/*     */   
/*     */   public void addWidget(IWidget widget) {
/*  81 */     super.addWidget(widget);
/*     */   }
/*     */   
/*     */   public boolean isShow() {
/*  85 */     return this.m_show;
/*     */   }
/*     */   
/*  88 */   public boolean isHorizontal() { return this.m_horizontal; }
/*     */   
/*     */   public void setHorizontal(boolean horizontal)
/*     */   {
/*  92 */     this.m_horizontal = horizontal;
/*     */   }
/*     */   
/*     */   public IWidget getClientWidget() {
/*  96 */     return this.m_clientWidget;
/*     */   }
/*     */   
/*     */   public void setClientWidget(IWidget clientWidget) {
/* 100 */     this.m_clientWidget = clientWidget;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getHotSpotPosition()
/*     */   {
/* 107 */     return this.m_hotSpotPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHotSpotPosition(Alignment align)
/*     */   {
/* 114 */     if (align != null) {
/* 115 */       this.m_hotSpotPosition = align;
/*     */     }
/*     */   }
/*     */   
/*     */   public Alignment getAlign() {
/* 120 */     return this.m_align;
/*     */   }
/*     */   
/*     */   public void setAlign(Alignment align) {
/* 124 */     this.m_align = align;
/*     */   }
/*     */   
/*     */   private void fireMenuClosed() {
/* 128 */     for (IMenuClosedListener l : this.m_mcl) {
/* 129 */       l.menuClosed(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addMenuClosedListener(IMenuClosedListener listener) {
/* 134 */     this.m_mcl.add(listener);
/*     */   }
/*     */   
/*     */   public void removeMenuClosedListener(IMenuClosedListener listener) {
/* 138 */     this.m_mcl.remove(listener);
/*     */   }
/*     */   
/*     */   public org.fenggui.Container.ContainerAppearance getAppearance() {
/* 142 */     if (this.m_appearance == null) {
/* 143 */       return super.getAppearance();
/*     */     }
/* 145 */     return this.m_appearance;
/*     */   }
/*     */   
/*     */   public void setAppearance(PopupAppearance appearance)
/*     */   {
/* 150 */     this.m_appearance = appearance;
/*     */   }
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 154 */     if (this.m_show) {
/* 155 */       return super.getWidget(x, y);
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */   
/*     */   public void layout() {
/* 161 */     setSizeToMinSize();
/*     */     
/* 163 */     setX(this.m_clientWidget.getDisplayX() + this.m_align.getX(this.m_clientWidget.getSize().getWidth()) - this.m_hotSpotPosition.getX(getWidth()));
/* 164 */     setY(this.m_clientWidget.getDisplayY() + this.m_align.getY(this.m_clientWidget.getSize().getHeight()) - this.m_hotSpotPosition.getY(getHeight()));
/*     */     
/* 166 */     if (this.m_horizontal) {
/* 167 */       int x = getAppearance().getContentWidth();
/*     */       
/* 169 */       for (IWidget w : this.notifyList) {
/* 170 */         StandardWidget widget = (StandardWidget)w;
/* 171 */         widget.setHeight(getAppearance().getContentHeight());
/* 172 */         widget.setWidth(widget.getAppearance().getMinSizeHint().getWidth());
/* 173 */         x -= widget.getWidth();
/* 174 */         widget.setX(x);
/* 175 */         widget.setY(0);
/* 176 */         widget.layout();
/*     */       }
/*     */     } else {
/* 179 */       int y = getAppearance().getContentHeight();
/*     */       
/* 181 */       for (IWidget w : this.notifyList) {
/* 182 */         StandardWidget widget = (StandardWidget)w;
/* 183 */         widget.setWidth(getAppearance().getContentWidth());
/* 184 */         widget.setHeight(widget.getAppearance().getMinSizeHint().getHeight());
/* 185 */         y -= widget.getHeight();
/* 186 */         widget.setX(0);
/* 187 */         widget.setY(y);
/* 188 */         widget.layout();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/* 198 */     if (this.m_show) {
/* 199 */       super.paint(g);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public class PopupAppearance
/*     */     extends Container.ContainerAppearance
/*     */     implements IAppearance
/*     */   {
/*     */     public PopupAppearance(Popup w)
/*     */     {
/* 210 */       super(w);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public Dimension getContentMinSizeHint()
/*     */     {
/* 218 */       int minWidth = 0;int minHeight = 0;
/* 219 */       for (IWidget w : Popup.access$0(Popup.this)) {
/* 220 */         StandardWidget widget = (StandardWidget)w;
/* 221 */         Dimension dim = widget.getAppearance().getMinSizeHint();
/* 222 */         minWidth = Math.max(dim.getWidth(), minWidth);
/* 223 */         minHeight += dim.getHeight();
/*     */       }
/* 225 */       return new Dimension(minWidth, minHeight);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void paintContent(Graphics g, IOpenGL gl)
/*     */     {
/* 233 */       if (Popup.this.m_show) {
/* 234 */         for (IWidget w : Popup.access$0(Popup.this)) {
/* 235 */           StandardWidget widget = (StandardWidget)w;
/* 236 */           gl.pushMatrix();
/* 237 */           g.translate(widget.getX(), widget.getY());
/*     */           
/* 239 */           widget.paint(g);
/*     */           
/* 241 */           g.translate(-widget.getX(), -widget.getY());
/* 242 */           gl.popMatrix();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\Popup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */