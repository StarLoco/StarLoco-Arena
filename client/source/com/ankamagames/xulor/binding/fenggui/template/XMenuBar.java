/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IMenuBar;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.menu.Menu;
/*     */ import org.fenggui.menu.MenuBar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMenuBar
/*     */   extends XComponent
/*     */   implements IMenuBar
/*     */ {
/*     */   public static final String TAG = "Menubar";
/*  21 */   private MenuBar m_menuBar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  29 */     System.out.println("<menubar>"); byte b; int i; IElement[] arrayOfIElement;
/*  30 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  31 */       c.buildXML(); b++; }
/*     */     
/*  33 */     System.out.println("</menubar>");
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
/*  45 */     if (this.m_menuBar == null) {
/*  46 */       this.m_menuBar = new MenuBar();
/*     */ 
/*     */       
/*  49 */       applyAllAttributes();
/*     */       
/*  51 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  52 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_menuBar, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  55 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  56 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  59 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  68 */     if (this.m_menuBar == null)
/*     */       return; 
/*  70 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  74 */     if (this.m_themeNeedToBeApplied) {
/*  75 */       this.m_themeNeedToBeApplied = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void addSubMenu(Menu menu, String name) {
/*  81 */     this.m_menuBar.registerSubMenu(menu, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/*  89 */     return (Widget)this.m_menuBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  97 */     return "Menubar";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 104 */     XMenuBar elem = new XMenuBar();
/* 105 */     copyElementData((IElement)elem);
/* 106 */     return (IElement)elem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMenuBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */