/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.io.MissingElementException;
/*     */ import org.fenggui.layout.RowLayout;
/*     */ import org.fenggui.render.Binding;
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
/*     */ public class Container
/*     */   extends StandardWidget
/*     */   implements IContainer
/*     */ {
/*  53 */   private LayoutManager layoutManager = null;
/*  54 */   protected ArrayList<IWidget> notifyList = new ArrayList<IWidget>();
/*     */   private boolean keyTraversalRoot = false;
/*  56 */   private ContainerAppearance appearance = null;
/*     */ 
/*     */   
/*     */   public boolean isKeyTraversalRoot() {
/*  60 */     return this.keyTraversalRoot;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyTraversalRoot(boolean traversalRoot) {
/*  65 */     this.keyTraversalRoot = traversalRoot;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAppearance(ContainerAppearance appearance) {
/*  70 */     this.appearance = appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerAppearance getAppearance() {
/*  75 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container() {
/*  83 */     this((LayoutManager)new RowLayout());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Container(LayoutManager layoutManager) {
/*  89 */     this.layoutManager = layoutManager;
/*  90 */     this.appearance = new ContainerAppearance(this);
/*  91 */     setupTheme(Container.class);
/*  92 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusChanged(FocusEvent focusEvent) {
/*  98 */     super.focusChanged(focusEvent);
/*     */     
/* 100 */     if (focusEvent.isFocusGained()) {
/*     */       
/* 102 */       int i = 0;
/*     */       
/* 104 */       for (; i < size() && !((IWidget)this.notifyList.get(i)).isTraversable(); i++);
/*     */       
/* 106 */       if (i >= size())
/*     */         return; 
/* 108 */       getDisplay().setFocusedWidget(this.notifyList.get(i));
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
/*     */   final boolean clipWidget(Graphics g, IWidget c) {
/* 146 */     int startX = (c.getX() < 0) ? 0 : c.getX();
/* 147 */     int startY = (c.getY() < 0) ? 0 : c.getY();
/*     */     
/* 149 */     Binding b = Binding.getInstance();
/*     */     
/* 151 */     if (startX >= b.getCanvasWidth() || startY >= b.getCanvasHeight())
/*     */     {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     int cWidth = c.getSize().getWidth();
/* 157 */     int cHeight = c.getSize().getHeight();
/*     */     
/* 159 */     g.setClipSpace(
/* 160 */         startX, 
/* 161 */         startY, 
/* 162 */         (c.getX() + cWidth > getWidth()) ? (getWidth() - startX) : cWidth, 
/* 163 */         (c.getY() + cHeight > getHeight()) ? (getHeight() - startY) : cHeight);
/*     */     
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraversable() {
/* 174 */     return true;
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
/*     */   public void updateMinSize() {
/* 197 */     setMinSize(getAppearance().getMinSizeHint());
/*     */     
/* 199 */     if (getParent() != null) getParent().updateMinSize();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IWidget> getContent() {
/* 208 */     return this.notifyList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget c, int position) {
/* 218 */     if (position < 0) position = 0; 
/* 219 */     if (position > this.notifyList.size()) position = this.notifyList.size();
/*     */     
/* 221 */     if (c == null)
/*     */       return; 
/* 223 */     if (c.equals(this))
/*     */     {
/* 225 */       throw new IllegalArgumentException("Can't add myself! c.equals(this)");
/*     */     }
/*     */     
/* 228 */     if (c.equals(getParent()))
/*     */     {
/* 230 */       throw new IllegalArgumentException("Can't add my parent!");
/*     */     }
/*     */     
/* 233 */     if (this.notifyList.contains(c)) {
/*     */       
/* 235 */       if (this.notifyList.indexOf(c) == position)
/*     */       {
/* 237 */         System.err.println("Container.addWidget: Widget " + c + " is already in the container (" + this + ")");
/*     */       }
/*     */       else
/*     */       {
/* 241 */         int newPosition = (position < this.notifyList.indexOf(c)) ? position : (position - 1);
/* 242 */         this.notifyList.remove(c);
/* 243 */         this.notifyList.add(newPosition, c);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 249 */       this.notifyList.add(position, c);
/* 250 */       c.setParent(this);
/*     */       
/* 252 */       if (getDisplay() != null) {
/* 253 */         c.addedToWidgetTree();
/*     */       }
/*     */     } 
/* 256 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addWidget(IWidget widget) {
/* 261 */     addWidget(widget, this.notifyList.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 268 */     super.removedFromWidgetTree();
/* 269 */     for (int i = 0; i < this.notifyList.size(); i++) {
/* 270 */       ((IWidget)this.notifyList.get(i)).removedFromWidgetTree();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 276 */     for (int i = 0; i < this.notifyList.size(); i++) {
/* 277 */       ((IWidget)this.notifyList.get(i)).addedToWidgetTree();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayoutManager(LayoutManager lm) {
/* 288 */     if (lm == null)
/* 289 */       return;  this.layoutManager = lm;
/*     */     
/* 291 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutManager getLayoutManager() {
/* 300 */     return this.layoutManager;
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
/*     */   public void layout() {
/* 312 */     this.layoutManager.doLayout(this, this.notifyList);
/*     */ 
/*     */     
/* 315 */     for (IWidget c : this.notifyList) c.layout();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSizeAndLayout() {
/* 324 */     updateMinSize();
/* 325 */     layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeWidget(IWidget c) {
/* 334 */     if (c == null)
/* 335 */       return;  if (c.equals(this)) throw new IllegalArgumentException("Cannot remove myself! " + this);
/*     */     
/* 337 */     for (int i = 0; i < this.notifyList.size(); i++) {
/*     */       
/* 339 */       if (c.equals(this.notifyList.get(i))) {
/*     */         
/* 341 */         this.notifyList.remove(i);
/* 342 */         c.removedFromWidgetTree();
/* 343 */         c.setParent(null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 348 */     updateMinSize();
/*     */     
/* 350 */     if (getDisplay() != null) getDisplay().focusedWidgetValityCheck();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeWidgets(List<IWidget> list) {
/* 361 */     if (list == null)
/*     */       return; 
/* 363 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/* 365 */       removeWidget(list.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllWidgets() {
/* 374 */     for (; size() > 0; removeWidget(getWidget(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int x, int y) {
/* 384 */     if (!getAppearance().insideMargin(x, y))
/*     */     {
/* 386 */       return null;
/*     */     }
/*     */     
/* 389 */     IWidget ret = null;
/* 390 */     IWidget found = this;
/*     */     
/* 392 */     x -= getAppearance().getLeftMargins();
/* 393 */     y -= getAppearance().getBottomMargins();
/*     */     
/* 395 */     for (IWidget w : this.notifyList) {
/*     */       
/* 397 */       ret = w.getWidget(x - w.getX(), y - w.getY());
/*     */       
/* 399 */       if (ret != null) found = ret;
/*     */     
/*     */     } 
/*     */     
/* 403 */     return found;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 411 */     if (this.notifyList == null) {
/* 412 */       return String.valueOf(super.toString()) + " {}";
/*     */     }
/*     */     
/* 415 */     String s = String.valueOf(super.toString()) + " {";
/*     */     
/* 417 */     for (int i = 0; i < this.notifyList.size(); i++) {
/* 418 */       s = String.valueOf(s) + ((IWidget)this.notifyList.get(i)).getClass().getSimpleName();
/* 419 */       if (i < this.notifyList.size() - 1) s = String.valueOf(s) + ", "; 
/*     */     } 
/* 421 */     s = String.valueOf(s) + "}";
/* 422 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 431 */     return this.notifyList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getWidget(int index) {
/* 440 */     return this.notifyList.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<IWidget> getWidgets() {
/* 450 */     return this.notifyList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayX() {
/* 456 */     return super.getDisplayX() + getAppearance().getLeftMargins();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayY() {
/* 463 */     return super.getDisplayY() + getAppearance().getBottomMargins();
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
/*     */   public void pack() {
/* 479 */     setSizeToMinSize();
/* 480 */     layout();
/*     */   }
/*     */   
/*     */   public class ContainerAppearance
/*     */     extends DecoratorAppearance {
/* 485 */     Container container = null;
/*     */ 
/*     */     
/*     */     public ContainerAppearance(Container w) {
/* 489 */       super(w);
/* 490 */       this.container = w;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getContentMinSizeHint() {
/* 496 */       return this.container.getLayoutManager().computeMinSize(this.container, this.container.getContent());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintContent(Graphics g, IOpenGL gl) {
/* 502 */       IOpenGL opengl = g.getOpenGL();
/*     */       
/* 504 */       List<IWidget> notifyList = this.container.getContent();
/*     */       
/* 506 */       for (int i = 0; i < notifyList.size(); i++) {
/*     */         
/* 508 */         IWidget c = notifyList.get(i);
/*     */ 
/*     */         
/* 511 */         if (c.getX() <= this.container.getWidth() && c.getY() <= this.container.getHeight()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 521 */           if (!(this.container.getParent() instanceof ScrollContainer)) {
/*     */             
/* 523 */             boolean valid = this.container.clipWidget(g, c);
/*     */             
/* 525 */             if (!valid)
/*     */               return; 
/*     */           } 
/* 528 */           opengl.pushMatrix();
/* 529 */           g.translate(c.getX(), c.getY());
/*     */           
/* 531 */           c.paint(g);
/*     */           
/* 533 */           g.translate(-c.getX(), -c.getY());
/* 534 */           opengl.popMatrix();
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
/*     */   
/*     */   public IWidget getPreviousWidget(IWidget currentWidget) {
/*     */     int i;
/* 549 */     if (currentWidget == null) { i = size() - 1; }
/* 550 */     else { i = this.notifyList.indexOf(currentWidget) - 1; }
/*     */     
/* 552 */     if (i < 0) {
/* 553 */       if (isKeyTraversalRoot()) { i = size() - 1; }
/* 554 */       else { return getParent().getPreviousWidget(this); }
/*     */     
/*     */     }
/* 557 */     return this.notifyList.get(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextWidget(IWidget currentWidget) {
/*     */     int i;
/* 568 */     if (currentWidget == null) { i = 0; }
/* 569 */     else { i = this.notifyList.indexOf(currentWidget) + 1; }
/*     */     
/* 571 */     if (i > size() - 1) {
/* 572 */       if (isKeyTraversalRoot()) { i = 0; }
/*     */       else
/*     */       
/* 575 */       { if (getParent() == null)
/*     */         {
/*     */           
/* 578 */           return this;
/*     */         }
/*     */ 
/*     */         
/* 582 */         IWidget nextWidget = getParent().getNextWidget(this);
/* 583 */         return nextWidget; }
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 590 */     return this.notifyList.get(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getNextTraversableWidget(IWidget currentWidget) {
/* 600 */     if (!this.notifyList.contains(currentWidget)) {
/* 601 */       throw new IllegalArgumentException("currentWidget is not child of this container!");
/*     */     }
/* 603 */     IWidget w = getNextWidget(currentWidget);
/*     */     
/* 605 */     for (; w != null && !w.isTraversable(); w = getNextWidget(w));
/*     */     
/* 607 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWidget getPreviousTraversableWidget(IWidget currentWidget) {
/* 617 */     if (!this.notifyList.contains(currentWidget)) {
/* 618 */       throw new IllegalArgumentException("currentWidget is not child of this container!");
/*     */     }
/* 620 */     IWidget w = getPreviousWidget(currentWidget);
/*     */     
/* 622 */     for (; w != null && !w.isTraversable(); w = getPreviousWidget(w));
/*     */     
/* 624 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 630 */     super.process(stream);
/*     */     
/*     */     try {
/* 633 */       this.layoutManager = (LayoutManager)stream.processChild(this.layoutManager, FengGUI.TYPE_REGISTRY);
/* 634 */     } catch (MissingElementException missingElementException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     if (stream.startSubcontext("children")) {
/*     */       
/* 643 */       stream.processChildren(this.notifyList, FengGUI.TYPE_REGISTRY);
/* 644 */       stream.endSubcontext();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Container.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */