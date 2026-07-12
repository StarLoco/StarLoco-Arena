/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.event.IMenuItemPressedListener;
/*     */ import com.ankamagames.xulor.event.MenuItemPressedEvent;
/*     */ import com.ankamagames.xulor.event.listener.MenuItemPressedListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IMenuItem;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.event.IMenuItemPressedListener;
/*     */ import org.fenggui.event.MenuItemPressedEvent;
/*     */ import org.fenggui.menu.MenuItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMenuItem
/*     */   extends XElement
/*     */   implements IMenuItem
/*     */ {
/*     */   public static final String TAG = "MenuItem";
/*  26 */   private MenuItem m_menuItem = null;
/*     */   
/*  28 */   private String m_text = null;
/*     */   
/*     */   private boolean m_enabled = true;
/*     */   
/*     */   private boolean m_enabledInit = false;
/*  33 */   private IMenuItemPressedListener m_menuItemPressedListener = null;
/*  34 */   private Vector<IMenuItemPressedListener> m_mipl = new Vector<IMenuItemPressedListener>();
/*     */   
/*     */   private XMenuItem THIS;
/*     */   
/*     */   public XMenuItem() {
/*  39 */     this.THIS = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  48 */     System.out.println("<menuitem text=\"" + this.m_text + "\">"); byte b; int i; IElement[] arrayOfIElement;
/*  49 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  50 */       c.buildXML(); b++; }
/*     */     
/*  52 */     System.out.println("</menuitem>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  63 */     if (this.m_menuItem == null) {
/*  64 */       this.m_menuItem = new MenuItem(this.m_text);
/*     */ 
/*     */       
/*  67 */       applyAllAttributes();
/*     */       
/*  69 */       this.m_menuItem.addMenuItemPressedListener(new IMenuItemPressedListener() {
/*     */             public void menuItemPressed(MenuItemPressedEvent menuItemPressedEvent) {
/*  71 */               MenuItemPressedEvent event = new MenuItemPressedEvent(XMenuItem.this.THIS);
/*  72 */               for (IMenuItemPressedListener l : XMenuItem.this.m_mipl) {
/*  73 */                 ((MenuItemPressedListener)l).run(event);
/*     */               }
/*     */             }
/*     */           });
/*     */       
/*  78 */       if (this.m_parent != null && 
/*  79 */         this.m_parent.getEncapsulatedObject() instanceof org.fenggui.menu.Menu) {
/*  80 */         ((XMenu)this.m_parent).addItem(this.m_menuItem);
/*     */       }
/*     */       
/*  83 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_menuItem, (IElement)this);
/*     */     }  byte b; int i; IElement[] arrayOfIElement;
/*  85 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  86 */       c.buildGUI();
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  98 */     if (this.m_menuItem == null)
/*     */       return; 
/* 100 */     if (this.m_text != null)
/* 101 */       this.m_menuItem.setText(this.m_text); 
/* 102 */     if (this.m_enabledInit) {
/* 103 */       this.m_menuItem.setEnabled(this.m_enabled);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelfFromParent() {
/* 110 */     if (this.m_menuItem != null) {
/* 111 */       this.m_menuItem.removeMenuItemPressedListener(this.m_menuItemPressedListener);
/*     */     }
/* 113 */     super.removeSelfFromParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnClick(IMenuItemPressedListener l) {
/* 122 */     this.m_mipl.add(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 131 */     this.m_text = text;
/* 132 */     if (this.m_menuItem != null) {
/* 133 */       this.m_menuItem.setText(this.m_text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 143 */     this.m_enabled = enabled;
/* 144 */     this.m_enabledInit = true;
/* 145 */     if (this.m_menuItem != null) {
/* 146 */       this.m_menuItem.setEnabled(this.m_enabled);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getText() {
/* 151 */     return this.m_text;
/*     */   }
/*     */   
/*     */   public boolean getEnabled() {
/* 155 */     return this.m_enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/* 163 */     return this.m_menuItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 171 */     return "MenuItem";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 179 */     XMenuItem menuitem = (XMenuItem)element;
/* 180 */     for (IMenuItemPressedListener listener : this.m_mipl) {
/* 181 */       menuitem.setOnClick(listener);
/*     */     }
/* 183 */     menuitem.m_enabled = this.m_enabled;
/* 184 */     menuitem.m_text = this.m_text;
/* 185 */     menuitem.m_enabledInit = this.m_enabledInit;
/* 186 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 193 */     XMenuItem menuitem = new XMenuItem();
/* 194 */     copyElementData((IElement)menuitem);
/* 195 */     return (IElement)menuitem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMenuItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */