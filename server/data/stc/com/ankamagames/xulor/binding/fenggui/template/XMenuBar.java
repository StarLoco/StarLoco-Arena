/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IMenuBar;
/*     */ import java.io.PrintStream;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.menu.Menu;
/*     */ import org.fenggui.menu.MenuBar;
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
/*     */   public void buildXML()
/*     */   {
/*  29 */     System.out.println("<menubar>");
/*  30 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  31 */       c.buildXML();
/*     */     }
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
/*     */   public void buildGUI()
/*     */   {
/*  45 */     if (this.m_menuBar == null) {
/*  46 */       this.m_menuBar = new MenuBar();
/*     */       
/*     */ 
/*  49 */       applyAllAttributes();
/*     */       
/*  51 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/*  52 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_menuBar, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  55 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  56 */       c.buildGUI();
/*     */     }
/*     */     
/*  59 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  68 */     if (this.m_menuBar == null)
/*  69 */       return;
/*  70 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  74 */     if (this.m_themeNeedToBeApplied) {
/*  75 */       this.m_themeNeedToBeApplied = false;
/*     */     }
/*     */   }
/*     */   
/*     */   void addSubMenu(Menu menu, String name)
/*     */   {
/*  81 */     this.m_menuBar.registerSubMenu(menu, name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/*  89 */     return this.m_menuBar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  97 */     return "Menubar";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 104 */     XMenuBar elem = new XMenuBar();
/* 105 */     copyElementData(elem);
/* 106 */     return elem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMenuBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */