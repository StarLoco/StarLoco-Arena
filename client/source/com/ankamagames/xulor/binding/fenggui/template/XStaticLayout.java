/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.binding.fenggui.component.StaticLayoutPlus;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IStaticLayout;
/*    */ import org.fenggui.LayoutManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XStaticLayout
/*    */   extends XLayoutManager
/*    */   implements IStaticLayout
/*    */ {
/*    */   public static final String TAG = "StaticLayout";
/*    */   public static final String SHORT_TAG = "SL";
/* 28 */   private StaticLayoutPlus m_staticLayout = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildXML() {
/* 36 */     System.out.println("<staticlayout>"); byte b; int i; IElement[] arrayOfIElement;
/* 37 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 38 */       c.buildXML(); b++; }
/*    */     
/* 40 */     System.out.println("</staticlayout>");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildGUI() {
/* 49 */     if (this.m_staticLayout == null) {
/* 50 */       this.m_staticLayout = new StaticLayoutPlus();
/*    */       
/* 52 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_staticLayout, (IElement)this);
/*    */       
/* 54 */       if (this.m_parent instanceof XContainer)
/* 55 */         ((XContainer)this.m_parent).setLayoutManager((LayoutManager)this.m_staticLayout); 
/*    */     }  byte b;
/*    */     int i;
/*    */     IElement[] arrayOfIElement;
/* 59 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 60 */       c.buildGUI();
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LayoutManager getLayoutManager() {
/* 70 */     return (LayoutManager)this.m_staticLayout;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 78 */     return "StaticLayout";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 85 */     XStaticLayout elem = new XStaticLayout();
/* 86 */     copyElementData((IElement)elem);
/* 87 */     return (IElement)elem;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XStaticLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */