/*     */ package org.fenggui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
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
/*     */ public class TabContainer
/*     */   extends StandardWidget
/*     */   implements IContainer
/*     */ {
/*  37 */   private ArrayList<TabItem> tabs = new ArrayList<TabItem>();
/*  38 */   private int activeTab = -1;
/*  39 */   private TabContainerAppearance appearance = null;
/*     */ 
/*     */   
/*     */   public TabContainer() {
/*  43 */     this.appearance = new TabContainerAppearance(this);
/*  44 */     setupTheme(TabContainer.class);
/*  45 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabContainerAppearance getAppearance() {
/*  52 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTab(String title, Pixmap pixmap, IWidget widget) {
/*  59 */     TabItemLabel label = new TabItemLabel(this);
/*  60 */     label.setParent(this);
/*  61 */     label.setText(title);
/*  62 */     label.setPixmap(pixmap);
/*  63 */     TabItem tab = new TabItem(widget, label);
/*  64 */     widget.setParent(this);
/*  65 */     if (getDisplay() != null) {
/*  66 */       widget.addedToWidgetTree();
/*     */     }
/*  68 */     this.tabs.add(tab);
/*     */     
/*  70 */     updateMinSize();
/*     */     
/*  72 */     if (this.tabs.size() == 1) selectTab(0);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraversable() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectTab(TabItemLabel label) {
/*  85 */     for (int i = 0; i < this.tabs.size(); i++) {
/*     */       
/*  87 */       TabItem item = this.tabs.get(i);
/*  88 */       if (item.label.equals(label)) {
/*     */         
/*  90 */         selectTab(i);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getChildrenCount() {
/*  97 */     return this.tabs.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void selectTab(int index) {
/* 103 */     if (index < 0 || index >= this.tabs.size()) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     if (this.activeTab >= 0) {
/* 108 */       ((TabItem)this.tabs.get(this.activeTab)).label.getAppearance().setEnabled("active", false);
/*     */     }
/* 110 */     TabItem item = this.tabs.get(index);
/*     */     
/* 112 */     item.label.getAppearance().setEnabled("active", true);
/* 113 */     this.activeTab = index;
/* 114 */     item.widget.setSize(new Dimension(getAppearance().getContentWidth(), getAppearance().getContentHeight() - item.label.getHeight()));
/* 115 */     item.widget.setX(0);
/* 116 */     item.widget.setY(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public TabItemLabel getSelectedTabLabel() {
/* 121 */     return ((TabItem)this.tabs.get(this.activeTab)).label;
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getSelectedTabWidget() {
/* 126 */     return ((TabItem)this.tabs.get(this.activeTab)).widget;
/*     */   }
/*     */   
/*     */   private class TabItem
/*     */   {
/* 131 */     IWidget widget = null;
/* 132 */     TabItemLabel label = null;
/*     */ 
/*     */     
/*     */     public TabItem(IWidget widget, TabItemLabel label) {
/* 136 */       this.widget = widget;
/* 137 */       this.label = label;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 144 */     if (!getAppearance().insideMargin(x, y))
/*     */     {
/* 146 */       return null;
/*     */     }
/*     */     
/* 149 */     if (this.tabs.isEmpty()) return this;
/*     */     
/* 151 */     IWidget ret = null;
/* 152 */     IWidget found = this;
/*     */     
/* 154 */     x -= getAppearance().getLeftMargins();
/* 155 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 157 */     for (TabItem item : this.tabs) {
/*     */       
/* 159 */       IWidget iWidget = item.label;
/* 160 */       ret = iWidget.getWidget(x - iWidget.getX(), y - iWidget.getY());
/*     */       
/* 162 */       if (ret != null) found = ret;
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 167 */     IWidget w = ((TabItem)this.tabs.get(this.activeTab)).widget;
/* 168 */     ret = w.getWidget(x - w.getX(), y - w.getY());
/* 169 */     if (ret != null) {
/* 170 */       found = ret;
/*     */     }
/* 172 */     return found;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 178 */     int xOffset = 0;
/*     */     
/* 180 */     for (TabItem item : this.tabs) {
/*     */       
/* 182 */       TabItemLabel label = item.label;
/* 183 */       label.setSizeToMinSize();
/* 184 */       label.setXY(xOffset, getAppearance().getContentHeight() - label.getHeight());
/* 185 */       xOffset += label.getWidth();
/* 186 */       label.layout();
/*     */       
/* 188 */       IWidget widget = item.widget;
/*     */       
/* 190 */       widget.setX(0);
/* 191 */       widget.setY(0);
/* 192 */       widget.setSize(new Dimension(getAppearance().getContentWidth(), getAppearance().getContentHeight() - label.getHeight()));
/* 193 */       widget.layout();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget start) {
/* 201 */     return getParent().getNextTraversableWidget(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget start) {
/* 206 */     return getParent().getPreviousTraversableWidget(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget start) {
/* 212 */     if (start.equals(getSelectedTabWidget()))
/*     */     {
/*     */       
/* 215 */       return ((TabItem)this.tabs.get(0)).label;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 220 */     int index = 0;
/* 221 */     for (index = 0; index < this.tabs.size(); index++) {
/*     */       
/* 223 */       if (((TabItem)this.tabs.get(index)).label.equals(start))
/*     */         break; 
/*     */     } 
/* 226 */     index++;
/*     */     
/* 228 */     if (index >= this.tabs.size()) return getParent().getNextWidget(this);
/*     */     
/* 230 */     return ((TabItem)this.tabs.get(index)).label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusChanged(FocusEvent focusEvent) {
/* 237 */     super.focusChanged(focusEvent);
/*     */     
/* 239 */     if (focusEvent.isFocusGained())
/*     */     {
/* 241 */       getDisplay().setFocusedWidget(((TabItem)this.tabs.get(this.activeTab)).widget);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget w) {
/* 247 */     addTab("No Title", (Pixmap)null, w);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget w, int position) {
/* 252 */     addTab("No Title", (Pixmap)null, w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget start) {
/* 258 */     return null;
/*     */   }
/*     */   
/*     */   public class TabContainerAppearance
/*     */     extends DecoratorAppearance
/*     */   {
/*     */     public TabContainerAppearance(TabContainer w) {
/* 265 */       super(w);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 271 */       int widthMax = 0;
/* 272 */       int heightMax = 0;
/*     */       
/* 274 */       for (TabContainer.TabItem item : TabContainer.this.tabs) {
/*     */         
/* 276 */         widthMax = Math.max(item.widget.getMinSize().getWidth(), widthMax);
/* 277 */         heightMax = Math.max(item.widget.getMinSize().getHeight(), heightMax);
/*     */       } 
/*     */       
/* 280 */       if (!TabContainer.this.tabs.isEmpty())
/*     */       {
/* 282 */         heightMax += (TabContainer.this.tabs.get(0)).label.getMinHeight();
/*     */       }
/*     */       
/* 285 */       return new Dimension(widthMax, heightMax);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 291 */       if (TabContainer.this.tabs.isEmpty())
/*     */         return; 
/* 293 */       for (TabContainer.TabItem item : TabContainer.this.tabs) {
/*     */         
/* 295 */         g.translate(item.label.getX(), item.label.getY());
/* 296 */         item.label.paint(g);
/* 297 */         g.translate(-item.label.getX(), -item.label.getY());
/*     */       } 
/*     */       
/* 300 */       g.translate((TabContainer.this.tabs.get(TabContainer.this.activeTab)).widget.getX(), (TabContainer.this.tabs.get(TabContainer.this.activeTab)).widget.getY());
/* 301 */       (TabContainer.this.tabs.get(TabContainer.this.activeTab)).widget.paint(g);
/* 302 */       g.translate(-(TabContainer.this.tabs.get(TabContainer.this.activeTab)).widget.getX(), -(TabContainer.this.tabs.get(TabContainer.this.activeTab)).widget.getY());
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\TabContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */