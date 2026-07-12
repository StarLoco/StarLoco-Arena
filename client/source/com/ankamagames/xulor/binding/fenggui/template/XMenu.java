/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.event.IMenuClosedListener;
/*     */ import com.ankamagames.xulor.event.MenuClosedEvent;
/*     */ import com.ankamagames.xulor.event.listener.MenuClosedListener;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IMenu;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IMenuClosedListener;
/*     */ import org.fenggui.event.MenuClosedEvent;
/*     */ import org.fenggui.menu.Menu;
/*     */ import org.fenggui.menu.MenuItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMenu
/*     */   extends XComponent
/*     */   implements IMenu
/*     */ {
/*     */   public static final String TAG = "Menu";
/*  28 */   private Menu m_menu = null;
/*     */   
/*  30 */   private String m_text = null;
/*     */   
/*     */   private IMenuClosedListener m_menuClosedListener;
/*  33 */   private Vector<MenuClosedListener> m_mcl = new Vector<MenuClosedListener>();
/*     */   
/*     */   private XMenu THIS;
/*     */   
/*     */   public XMenu() {
/*  38 */     this.THIS = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  47 */     System.out.println("<menu text=\"" + this.m_text + "\">"); byte b; int i; IElement[] arrayOfIElement;
/*  48 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  49 */       c.buildXML(); b++; }
/*     */     
/*  51 */     System.out.println("</menu>");
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
/*     */   public void buildGUI() {
/*  63 */     if (this.m_menu == null) {
/*  64 */       this.m_menu = new Menu();
/*     */       
/*  66 */       applyAllAttributes();
/*     */       
/*  68 */       this.m_menuClosedListener = new IMenuClosedListener() {
/*     */           public void menuClosed(MenuClosedEvent menuClosedEvent) {
/*  70 */             MenuClosedEvent event = new MenuClosedEvent(XMenu.this.THIS);
/*  71 */             for (MenuClosedListener l : XMenu.this.m_mcl) {
/*  72 */               l.run(event);
/*     */             }
/*     */           }
/*     */         };
/*  76 */       this.m_menu.addMenuClosedListener(this.m_menuClosedListener);
/*     */       
/*  78 */       if (this.m_parent != null) {
/*  79 */         if (this.m_parent.getEncapsulatedObject() instanceof org.fenggui.menu.MenuBar) {
/*  80 */           ((XMenuBar)this.m_parent).addSubMenu(this.m_menu, this.m_text);
/*  81 */         } else if (this.m_parent.getEncapsulatedObject() instanceof Menu) {
/*  82 */           ((XMenu)this.m_parent).addSubMenu(this.m_menu, this.m_text);
/*     */         } 
/*     */       }
/*  85 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_menu, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  88 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  89 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  92 */     applyTheme();
/*     */   }
/*     */   
/*     */   public void removeSelfFromParent() {
/*  96 */     if (this.m_menu != null) {
/*  97 */       this.m_menu.removeMenuClosedListener(this.m_menuClosedListener);
/*     */     }
/*  99 */     super.removeSelfFromParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnClose(IMenuClosedListener l) {
/* 108 */     this.m_mcl.add((MenuClosedListener)l);
/*     */   }
/*     */   
/*     */   void addSubMenu(Menu menu, String name) {
/* 112 */     this.m_menu.registerSubMenu(menu, name);
/*     */   }
/*     */   
/*     */   void addItem(MenuItem item) {
/* 116 */     this.m_menu.addItem(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IComponent getItem(int i) {
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 136 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 145 */     this.m_text = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/* 155 */     if (this.m_menu == null)
/*     */       return; 
/* 157 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/* 161 */     if (this.m_themeNeedToBeApplied) {
/* 162 */       this.m_themeNeedToBeApplied = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 172 */     return (Widget)this.m_menu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 180 */     return "Menu";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 188 */     XMenu elem = (XMenu)element;
/* 189 */     elem.m_text = this.m_text;
/* 190 */     for (IMenuClosedListener mcl : this.m_mcl) {
/* 191 */       elem.setOnClose(mcl);
/*     */     }
/* 193 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 200 */     XMenu elem = new XMenu();
/* 201 */     copyElementData((IElement)elem);
/* 202 */     return (IElement)elem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */