/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.event.listener.MenuItemPressedListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IMenuItem;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ import org.fenggui.menu.Menu;
/*     */ import org.fenggui.menu.MenuItem;
/*     */ 
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
/*  29 */   private boolean m_enabled = true;
/*     */   
/*  31 */   private boolean m_enabledInit = false;
/*     */   
/*  33 */   private org.fenggui.event.IMenuItemPressedListener m_menuItemPressedListener = null;
/*  34 */   private Vector<com.ankamagames.xulor.event.IMenuItemPressedListener> m_mipl = new Vector();
/*     */   private XMenuItem THIS;
/*     */   
/*     */   public XMenuItem()
/*     */   {
/*  39 */     this.THIS = this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  48 */     System.out.println("<menuitem text=\"" + this.m_text + "\">");
/*  49 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  50 */       c.buildXML();
/*     */     }
/*  52 */     System.out.println("</menuitem>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  63 */     if (this.m_menuItem == null) {
/*  64 */       this.m_menuItem = new MenuItem(this.m_text);
/*     */       
/*     */ 
/*  67 */       applyAllAttributes();
/*     */       
/*  69 */       this.m_menuItem.addMenuItemPressedListener(new org.fenggui.event.IMenuItemPressedListener() {
/*     */         public void menuItemPressed(org.fenggui.event.MenuItemPressedEvent menuItemPressedEvent) {
/*  71 */           com.ankamagames.xulor.event.MenuItemPressedEvent event = new com.ankamagames.xulor.event.MenuItemPressedEvent(XMenuItem.this.THIS);
/*  72 */           for (com.ankamagames.xulor.event.IMenuItemPressedListener l : XMenuItem.this.m_mipl) {
/*  73 */             ((MenuItemPressedListener)l).run(event);
/*     */           }
/*     */         }
/*     */       });
/*     */       
/*  78 */       if ((this.m_parent != null) && 
/*  79 */         ((this.m_parent.getEncapsulatedObject() instanceof Menu))) {
/*  80 */         ((XMenu)this.m_parent).addItem(this.m_menuItem);
/*     */       }
/*     */       
/*  83 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_menuItem, this); }
/*     */     IElement[] arrayOfIElement;
/*  85 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  86 */       c.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  98 */     if (this.m_menuItem == null)
/*  99 */       return;
/* 100 */     if (this.m_text != null)
/* 101 */       this.m_menuItem.setText(this.m_text);
/* 102 */     if (this.m_enabledInit) {
/* 103 */       this.m_menuItem.setEnabled(this.m_enabled);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeSelfFromParent()
/*     */   {
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
/*     */   public void setOnClick(com.ankamagames.xulor.event.IMenuItemPressedListener l)
/*     */   {
/* 122 */     this.m_mipl.add(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String text)
/*     */   {
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
/*     */   public void setEnabled(boolean enabled)
/*     */   {
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
/*     */   public Object getEncapsulatedObject()
/*     */   {
/* 163 */     return this.m_menuItem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 171 */     return "MenuItem";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 179 */     XMenuItem menuitem = (XMenuItem)element;
/* 180 */     for (com.ankamagames.xulor.event.IMenuItemPressedListener listener : this.m_mipl) {
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
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 193 */     XMenuItem menuitem = new XMenuItem();
/* 194 */     copyElementData(menuitem);
/* 195 */     return menuitem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMenuItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */