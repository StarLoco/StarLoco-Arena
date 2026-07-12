/*     */ package org.fenggui;
/*     */ 
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.KeyTypedEvent;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
/*     */ import org.fenggui.event.mouse.MouseWheelEvent;
/*     */ import org.fenggui.layout.ILayoutData;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Point;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Widget
/*     */   implements IWidget
/*     */ {
/*  61 */   private WritableDimension size = new WritableDimension(10, 10);
/*  62 */   private WritableDimension minSize = new WritableDimension(10, 10);
/*     */   
/*     */   private boolean shrinkable = true;
/*     */   
/*     */   private boolean expandable = true;
/*     */   
/*     */   public Dimension getSize() {
/*  69 */     return (Dimension)this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinSize() {
/*  74 */     return (Dimension)this.minSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private IBasicContainer parent = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private WritablePoint position = new WritablePoint(0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private ILayoutData layoutData = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayoutData(ILayoutData layoutData) {
/* 111 */     this.layoutData = layoutData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ILayoutData getLayoutData() {
/* 118 */     return this.layoutData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBasicContainer getParent() {
/* 125 */     return this.parent;
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
/*     */   public void mouseEntered(MouseEnteredEvent mouseEnteredEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseDraggedEvent mp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseReleasedEvent mr) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheel(MouseWheelEvent mouseWheelEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyPressedEvent keyPressedEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyReleasedEvent keyReleasedEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyTypedEvent keyTypedEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInWidgetTree() {
/* 230 */     if (getDisplay() == null) return false; 
/* 231 */     return true;
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
/*     */   public void removedFromWidgetTree() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayX() {
/* 323 */     IBasicContainer parent = getParent();
/* 324 */     if (parent != null) {
/* 325 */       return parent.getDisplayX() + getX();
/*     */     }
/* 327 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayY() {
/* 336 */     IBasicContainer parent = getParent();
/* 337 */     if (parent != null) {
/* 338 */       return parent.getDisplayY() + getY();
/*     */     }
/* 340 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Display getDisplay() {
/* 348 */     if (this.parent == null) return null; 
/* 349 */     return getParent().getDisplay();
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
/*     */   public IWidget getWidget(int x, int y) {
/* 362 */     if (x > 0 && y > 0 && x < getWidth() && y < getHeight()) return this;
/*     */     
/* 364 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSizeToMinSize() {
/* 369 */     setSize(getMinSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(Dimension s) {
/* 374 */     this.size.setSize(s);
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
/*     */   public void move(int x, int y) {
/* 386 */     setX(getX() + x);
/* 387 */     setY(getY() + y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXY(int x, int y) {
/* 398 */     this.position.setXY(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setParent(IBasicContainer parent) {
/* 414 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 420 */     StringBuffer sb = new StringBuffer();
/* 421 */     sb.append("--- "); sb.append(getClass().getSimpleName()); sb.append(" ---\n");
/* 422 */     sb.append("size    : "); sb.append(this.size); sb.append('\n');
/* 423 */     sb.append("position: "); sb.append(this.position); sb.append('\n');
/* 424 */     sb.append("minSize : "); sb.append(this.minSize);
/*     */     
/* 426 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusChanged(FocusEvent focusEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
/* 444 */     this.position.setY(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/* 455 */     return this.position.getY();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFocus() {
/* 460 */     Display d = getDisplay();
/*     */     
/* 462 */     if (d == null) return false;
/*     */     
/* 464 */     IWidget w = d.getFocusedWidget();
/*     */     
/* 466 */     if (w == null) return false;
/*     */     
/* 468 */     return w.equals(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTraversable() {
/* 476 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 481 */     return this.size.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 486 */     return this.size.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExpandable() {
/* 491 */     return this.expandable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExpandable(boolean expandable) {
/* 496 */     this.expandable = expandable;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShrinkable() {
/* 501 */     return this.shrinkable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShrinkable(boolean shrinkable) {
/* 506 */     this.shrinkable = shrinkable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMinSize(Dimension dim) {
/* 511 */     this.minSize.setSize(dim);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public WritablePoint getPosition() {
/* 521 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinWidth() {
/* 528 */     return getMinSize().getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinHeight() {
/* 533 */     return getMinSize().getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMinSize(int minWidth, int minHeight) {
/* 538 */     setMinSize(new Dimension(minWidth, minHeight));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(int width, int height) {
/* 544 */     setSize(new Dimension(width, height));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeight(int height) {
/* 549 */     this.size.setHeight(height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 554 */     this.size.setWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/* 564 */     this.position.setX(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 575 */     return this.position.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(Point p) {
/* 580 */     setXY(p.getX(), p.getY());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Widget.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */