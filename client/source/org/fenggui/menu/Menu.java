/*     */ package org.fenggui.menu;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.IMenuClosedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.MenuClosedEvent;
/*     */ import org.fenggui.event.mouse.IMouseDraggedListener;
/*     */ import org.fenggui.event.mouse.IMouseExitedListener;
/*     */ import org.fenggui.event.mouse.IMouseMovedListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.IMouseReleasedListener;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MouseMovedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
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
/*     */ public class Menu
/*     */   extends ObservableWidget
/*     */   implements IMenuChainElement
/*     */ {
/*  54 */   private ArrayList<IMenuClosedListener> menuClosedHook = new ArrayList<IMenuClosedListener>();
/*     */   
/*  56 */   private MenuAppearance appearance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private IMenuChainElement nextMenu = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private IMenuChainElement previousMenu = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private int mouseOverRow = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDragging = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu() {
/*  86 */     this.appearance = new MenuAppearance(this);
/*  87 */     setupTheme(Menu.class);
/*  88 */     updateMinSize();
/*  89 */     buildBehavior();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu(InputOnlyStream stream) throws IOException, IOStreamException {
/*  97 */     this.appearance = new MenuAppearance(this);
/*  98 */     process((InputOutputStream)stream);
/*  99 */     updateMinSize();
/* 100 */     buildBehavior();
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildBehavior() {
/* 105 */     final Menu thizz = this;
/*     */ 
/*     */ 
/*     */     
/* 109 */     addMouseDraggedListener(new IMouseDraggedListener()
/*     */         {
/*     */           public void mouseDragged(MouseDraggedEvent mp)
/*     */           {
/* 113 */             Menu.this.mouseMoved(mp.getDisplayX(), mp.getDisplayY());
/* 114 */             Menu.this.isDragging = true;
/*     */           }
/*     */         });
/*     */     
/* 118 */     addMouseMovedListener(new IMouseMovedListener()
/*     */         {
/*     */           public void mouseMoved(MouseMovedEvent mouseMovedEvent)
/*     */           {
/* 122 */             Menu.this.isDragging = false;
/*     */             
/* 124 */             int mouseY = mouseMovedEvent.getDisplayY() - Menu.this.getDisplayY();
/*     */             
/* 126 */             Menu.this.setMouseOverRow(Menu.this.computeRow(mouseY));
/*     */           }
/*     */         });
/*     */     
/* 130 */     addMouseExitedListener(new IMouseExitedListener()
/*     */         {
/*     */           public void mouseExited(MouseExitedEvent mouseExited)
/*     */           {
/* 134 */             Menu.this.mouseOverRow = -1;
/*     */           }
/*     */         });
/*     */     
/* 138 */     addKeyPressedListener(new IKeyPressedListener()
/*     */         {
/*     */           public void keyPressed(KeyPressedEvent kpe)
/*     */           {
/* 142 */             if (kpe.getKeyClass().equals(Key.DOWN)) {
/*     */               
/* 144 */               Menu.this.setMouseOverRow((Menu.this.mouseOverRow + 1) % Menu.this.items.size());
/*     */             }
/* 146 */             else if (kpe.getKeyClass().equals(Key.UP)) {
/*     */               
/* 148 */               Menu.this.mouseOverRow = Menu.this.mouseOverRow - 1;
/* 149 */               if (Menu.this.mouseOverRow <= -1) Menu.this.mouseOverRow = Menu.this.items.size() - 1; 
/* 150 */               Menu.this.setMouseOverRow(Menu.this.mouseOverRow);
/*     */             }
/* 152 */             else if (kpe.getKeyClass().equals(Key.ENTER)) {
/*     */               
/* 154 */               if (Menu.this.mouseOverRow >= 0 && Menu.this.mouseOverRow < Menu.this.items.size()) {
/* 155 */                 Menu.this.selectItem(Menu.this.mouseOverRow);
/*     */               }
/* 157 */             } else if (kpe.getKeyClass().equals(Key.LEFT)) {
/*     */               
/* 159 */               Menu.this.getDisplay().setFocusedWidget((IWidget)Menu.this.getPreviousMenu());
/*     */               
/* 161 */               if (Menu.this.getPreviousMenu() instanceof MenuBar)
/* 162 */               { Menu.this.getDisplay().getFocusedWidget().keyPressed(kpe); }
/* 163 */               else { Menu.this.closeForward(); }
/*     */             
/* 165 */             } else if (kpe.getKeyClass().equals(Key.RIGHT)) {
/*     */               
/* 167 */               if (Menu.this.mouseOverRow > 0 && ((MenuItem)Menu.this.items.get(Menu.this.mouseOverRow)).getMenu() != null) {
/*     */                 
/* 169 */                 Menu m = ((MenuItem)Menu.this.items.get(Menu.this.mouseOverRow)).getMenu();
/* 170 */                 Menu.this.getDisplay().setFocusedWidget((IWidget)m);
/* 171 */                 m.setMouseOverRow(0);
/*     */               }
/*     */               else {
/*     */                 
/* 175 */                 IMenuChainElement m = Menu.this.getPreviousMenu();
/*     */                 
/* 177 */                 while (m.getPreviousMenu() != null) {
/* 178 */                   m = m.getPreviousMenu();
/*     */                 }
/* 180 */                 if (m instanceof MenuBar) {
/*     */                   
/* 182 */                   Menu.this.getDisplay().setFocusedWidget((IWidget)m);
/* 183 */                   Menu.this.getDisplay().getFocusedWidget().keyPressed(kpe);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 191 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mp)
/*     */           {
/* 195 */             int row = Menu.this.computeRow(mp.getLocalY(thizz));
/*     */             
/* 197 */             Menu.this.selectItem(row);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 202 */     addMouseReleasedListener(new IMouseReleasedListener()
/*     */         {
/*     */           public void mouseReleased(MouseReleasedEvent mr)
/*     */           {
/* 206 */             if (Menu.this.isDragging) {
/*     */               
/* 208 */               Menu.this
/* 209 */                 .mousePressed(new MousePressedEvent(thizz, mr.getDisplayX(), mr.getDisplayY(), mr.getButton(), mr.getClickCount()));
/* 210 */               Menu.this.isDragging = false;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuItem getItem(int index) {
/* 218 */     return this.items.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 223 */     return this.items.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuItem getMenuItem(int index) {
/* 228 */     return this.items.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<MenuItem> getItems() {
/* 233 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayAsPopup(Menu prev) {
/* 238 */     this.previousMenu = prev;
/* 239 */     setSizeToMinSize();
/*     */     
/* 241 */     Display display = prev.getDisplay();
/*     */     
/* 243 */     display.displayPopUp((Widget)this);
/* 244 */     display.layout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeForward() {
/* 254 */     if (this.nextMenu != null) {
/*     */       
/* 256 */       if (this.nextMenu.equals(this))
/*     */       {
/* 258 */         System.out.println(this + " " + ((MenuItem)this.items.get(0)).getText());
/*     */       }
/*     */       
/* 261 */       this.nextMenu.closeForward();
/* 262 */       this.nextMenu = null;
/*     */     } 
/* 264 */     this.previousMenu = null;
/*     */     
/* 266 */     if (getDisplay() != null) getDisplay().removeWidget((IWidget)this); 
/* 267 */     this.mouseOverRow = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSubMenu(Menu submenu, String name) {
/* 277 */     if (submenu.equals(this)) throw new IllegalArgumentException("submenu.equals(this): circular reference!");
/*     */     
/* 279 */     MenuItem item = new MenuItem(name);
/* 280 */     item.menu = submenu;
/* 281 */     addItem(item);
/* 282 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPreviousMenu(IMenuChainElement previousMenu) {
/* 291 */     this.previousMenu = previousMenu;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeBackward() {
/* 296 */     if (getDisplay() != null) getDisplay().removeWidget((IWidget)this);
/*     */     
/* 298 */     if (this.previousMenu != null) {
/*     */       
/* 300 */       this.previousMenu.closeBackward();
/* 301 */       this.previousMenu = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int computeRow(int localY) {
/* 307 */     return (this.appearance.getContentHeight() - localY) / this.appearance.getCellHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void selectItem(int row) {
/* 317 */     MenuItem item = this.items.get(row);
/*     */     
/* 319 */     if (!item.isEnabled())
/*     */       return; 
/* 321 */     item.fireMenuItemPressedEvent();
/*     */     
/* 323 */     closeBackward();
/* 324 */     closeForward();
/*     */     
/* 326 */     if (getDisplay() != null) getDisplay().removeWidget((IWidget)this);
/*     */   
/*     */   }
/*     */   
/*     */   public void addItem(MenuItem item2) {
/* 331 */     this.items.add(item2);
/* 332 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setMouseOverRow(int row) {
/* 341 */     this.mouseOverRow = row;
/* 342 */     if (this.mouseOverRow >= this.items.size())
/*     */       return; 
/* 344 */     MenuItem item = this.items.get(this.mouseOverRow);
/* 345 */     Menu menu = item.menu;
/*     */     
/* 347 */     if (menu != null) {
/*     */       
/* 349 */       if (this.nextMenu != null) this.nextMenu.closeForward(); 
/* 350 */       menu.setSizeToMinSize();
/* 351 */       menu.setX(getWidth() + getX());
/* 352 */       menu.setY(getY() + (this.items.size() - this.mouseOverRow) * this.appearance.getCellHeight() - menu.getHeight());
/* 353 */       menu.displayAsPopup(this);
/* 354 */       this.nextMenu = menu;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMouseOverRow() {
/* 363 */     return this.mouseOverRow;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 369 */     super.removedFromWidgetTree();
/* 370 */     fireMenuClosedEvent();
/* 371 */     closeBackward();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<MenuItem> getMenuItems() {
/* 376 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 384 */     setMinSize(this.appearance.getMinSizeHint());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMenuChainElement getNextMenu() {
/* 392 */     return this.nextMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMenuChainElement getPreviousMenu() {
/* 400 */     return this.previousMenu;
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuAppearance getAppearance() {
/* 405 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMenuClosedListener(IMenuClosedListener l) {
/* 414 */     if (!this.menuClosedHook.contains(l))
/*     */     {
/* 416 */       this.menuClosedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMenuClosedListener(IMenuClosedListener l) {
/* 426 */     this.menuClosedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireMenuClosedEvent() {
/* 434 */     MenuClosedEvent e = new MenuClosedEvent(this);
/*     */     
/* 436 */     for (IMenuClosedListener l : this.menuClosedHook)
/*     */     {
/* 438 */       l.menuClosed(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 445 */     super.process(stream);
/*     */     
/* 447 */     stream.processChildren("Item", this.items, MenuItem.class);
/*     */ 
/*     */ 
/*     */     
/* 451 */     if (stream.isInputStream()) {
/* 452 */       updateMinSize();
/* 453 */       this.mouseOverRow = -1;
/* 454 */       this.isDragging = false;
/* 455 */       this.nextMenu = null;
/* 456 */       this.previousMenu = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\Menu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */