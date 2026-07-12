/*     */ package org.fenggui.menu;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.IMenuClosedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.MenuClosedEvent;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
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
/*     */ public class MenuBar
/*     */   extends StandardWidget
/*     */   implements IMenuChainElement
/*     */ {
/*  48 */   private Menu currentlyOpen = null;
/*  49 */   private MenuBarItem mouseOver = null;
/*  50 */   private ArrayList<MenuBarItem> items = new ArrayList<MenuBarItem>();
/*  51 */   private MenuBarAppearance appearance = null;
/*     */ 
/*     */   
/*     */   public MenuBarItem getMouseOver() {
/*  55 */     return this.mouseOver;
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuBarAppearance getAppearance() {
/*  60 */     return this.appearance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuBar() {
/*  66 */     this.appearance = new MenuBarAppearance(this);
/*  67 */     setupTheme(MenuBar.class);
/*  68 */     updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSubMenu(Menu submenu, String name) {
/*  79 */     MenuBarItem item = new MenuBarItem(submenu, name);
/*  80 */     this.items.add(item);
/*     */     
/*  82 */     final MenuBar thizz = this;
/*     */     
/*  84 */     submenu.addMenuClosedListener(new IMenuClosedListener()
/*     */         {
/*     */           public void menuClosed(MenuClosedEvent menuClosedEvent)
/*     */           {
/*  88 */             if (menuClosedEvent.getMenu().equals(MenuBar.this.currentlyOpen)) MenuBar.this.currentlyOpen = null;
/*     */           
/*     */           }
/*     */         });
/*  92 */     submenu.addKeyPressedListener(new IKeyPressedListener()
/*     */         {
/*     */ 
/*     */           
/*     */           public void keyPressed(KeyPressedEvent kpe)
/*     */           {
/*  98 */             if (!kpe.getKeyClass().equals(Key.LEFT))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 131 */               if (kpe.getKeyClass().equals(Key.ESCAPE))
/*     */               {
/* 133 */                 if (MenuBar.this.currentlyOpen != null) {
/*     */                   
/* 135 */                   MenuBar.this.currentlyOpen.closeForward();
/* 136 */                   MenuBar.this.currentlyOpen = null;
/* 137 */                   MenuBar.this.getDisplay().setFocusedWidget((IWidget)thizz);
/*     */                 } 
/*     */               }
/*     */             }
/*     */           }
/*     */         });
/* 143 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private int getItemWidth(MenuBarItem item) {
/* 148 */     return getAppearance().getFont().getWidth(item.getName()) + 
/* 149 */       getAppearance().getGap();
/*     */   }
/*     */ 
/*     */   
/*     */   private MenuBarItem findItem(Menu menu) {
/* 154 */     for (int i = 0; i < this.items.size(); i++) {
/*     */       
/* 156 */       if (((MenuBarItem)this.items.get(i)).getMenu().equals(menu))
/*     */       {
/* 158 */         return this.items.get(i);
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<MenuBarItem> getMenuBarItems() {
/* 167 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   private void openMenu(Menu submenu) {
/* 172 */     if (submenu.equals(this.currentlyOpen)) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     int x = 0;
/*     */     
/* 178 */     for (MenuBarItem item : this.items) {
/*     */       
/* 180 */       if (item.getMenu().equals(submenu))
/*     */         break; 
/* 182 */       x += getAppearance().getFont().getWidth(item.getName()) + getAppearance().getGap();
/*     */     } 
/*     */     
/* 185 */     submenu.setSizeToMinSize();
/* 186 */     submenu.setY(getY() - submenu.getHeight());
/* 187 */     submenu.setX(x);
/* 188 */     submenu.setPreviousMenu(this);
/* 189 */     this.currentlyOpen = submenu;
/*     */     
/* 191 */     getDisplay().displayPopUp((Widget)submenu);
/* 192 */     getDisplay().setFocusedWidget((IWidget)submenu);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeForward() {
/* 198 */     this.currentlyOpen.closeForward();
/* 199 */     this.currentlyOpen = null;
/* 200 */     getDisplay().setFocusedWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeBackward() {
/* 206 */     this.currentlyOpen = null;
/* 207 */     getDisplay().setFocusedWidget((IWidget)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 213 */     if (getParent() == null)
/*     */       return; 
/* 215 */     setMinSize(getAppearance().getMinSizeHint());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseExitedEvent mouseExitedEvent) {
/* 221 */     this.mouseOver = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseDragged(MouseDraggedEvent mp) {
/* 227 */     mouseMoved(mp.getDisplayX(), mp.getDisplayY());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(int displayX, int displayY) {
/* 233 */     int mouseX = displayX - getDisplayX();
/* 234 */     int x = 0;
/*     */     
/* 236 */     for (MenuBarItem item : this.items) {
/*     */       
/* 238 */       int itemWidth = getAppearance().getFont().getWidth(item.getName()) + getAppearance().getGap();
/*     */       
/* 240 */       if (mouseX >= x && mouseX - x < itemWidth) {
/*     */         
/* 242 */         this.mouseOver = item;
/*     */         
/* 244 */         if (!this.mouseOver.getMenu().equals(this.currentlyOpen) && this.currentlyOpen != null) {
/*     */           
/* 246 */           this.currentlyOpen.closeForward();
/* 247 */           openMenu(this.mouseOver.getMenu());
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 253 */       this.mouseOver = null;
/*     */ 
/*     */       
/* 256 */       x += itemWidth;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(KeyPressedEvent kpe) {
/* 265 */     super.keyPressed(kpe);
/*     */     
/* 267 */     if (kpe.getKeyClass().equals(Key.DOWN)) {
/*     */       
/* 269 */       openMenu(this.mouseOver.getMenu());
/*     */     }
/* 271 */     else if (kpe.getKeyClass().equals(Key.LEFT)) {
/*     */       
/* 273 */       if (this.currentlyOpen != null)
/*     */       {
/* 275 */         int x = this.currentlyOpen.getX() - 10;
/* 276 */         if (x < 0) x = getMinWidth() + 5; 
/* 277 */         mouseMoved(x, this.currentlyOpen.getY() + 5);
/*     */       }
/*     */       else
/*     */       {
/* 281 */         int x = -10;
/* 282 */         for (MenuBarItem item : this.items) {
/*     */           
/* 284 */           if (item.equals(this.mouseOver))
/* 285 */             break;  x += getItemWidth(item);
/*     */         } 
/* 287 */         if (x < 0) x = getItemX(this.items.get(this.items.size() - 1)) + 10; 
/* 288 */         mouseMoved(x, 10);
/*     */       }
/*     */     
/* 291 */     } else if (kpe.getKeyClass().equals(Key.RIGHT)) {
/*     */       
/* 293 */       if (this.currentlyOpen != null) {
/*     */         
/* 295 */         int x = this.currentlyOpen.getX() + 
/* 296 */           getItemWidth(findItem(this.currentlyOpen)) + 
/* 297 */           10;
/* 298 */         if (x > getMinWidth() + getItemWidth((MenuBarItem)this.items.get(this.items.size() - 1))) x = 5; 
/* 299 */         mouseMoved(x, this.currentlyOpen.getY() + 5);
/*     */       }
/*     */       else {
/*     */         
/* 303 */         int x = 0;
/* 304 */         if (this.items.indexOf(this.mouseOver) == this.items.size() - 1) {
/*     */           
/* 306 */           x = 10;
/*     */         }
/*     */         else {
/*     */           
/* 310 */           x = getItemX(this.mouseOver);
/* 311 */           x += getItemWidth(this.mouseOver) + 10;
/*     */         } 
/* 313 */         mouseMoved(x, 10);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getItemX(MenuBarItem item) {
/* 322 */     int x = 0;
/* 323 */     for (MenuBarItem it : this.items) {
/*     */       
/* 325 */       if (it.equals(item))
/* 326 */         break;  x += getItemWidth(it);
/*     */     } 
/* 328 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 334 */     super.process(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MousePressedEvent mp) {
/* 342 */     if (this.mouseOver == null)
/*     */       return; 
/* 344 */     openMenu(this.mouseOver.getMenu());
/*     */   }
/*     */ 
/*     */   
/*     */   public IMenuChainElement getNextMenu() {
/* 349 */     return this.currentlyOpen;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMenuChainElement getPreviousMenu() {
/* 354 */     return null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\menu\MenuBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */