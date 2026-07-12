/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.template.IBorderLayout;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import org.fenggui.LayoutManager;
/*    */ import org.fenggui.layout.BorderLayout;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XBorderLayout
/*    */   extends XLayoutManager
/*    */   implements IBorderLayout
/*    */ {
/*    */   public static final String TAG = "BorderLayout";
/*    */   public static final String SHORT_TAG = "BL";
/* 21 */   private BorderLayout m_borderLayout = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildXML() {
/* 29 */     IElement[] components = getChildren();
/* 30 */     System.out.println("<borderlayout>"); byte b; int i; IElement[] arrayOfIElement1;
/* 31 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 32 */       c.buildXML(); b++; }
/*    */     
/* 34 */     System.out.println("</borderlayout>");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildGUI() {
/* 43 */     IElement[] components = getChildren();
/* 44 */     if (this.m_borderLayout == null) {
/* 45 */       BorderLayout bl = new BorderLayout();
/* 46 */       this.m_borderLayout = bl;
/*    */       
/* 48 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_borderLayout, (IElement)this);
/*    */       
/* 50 */       if (this.m_parent instanceof XContainer)
/* 51 */         ((XContainer)this.m_parent).setLayoutManager((LayoutManager)bl); 
/*    */     }  byte b;
/*    */     int i;
/*    */     IElement[] arrayOfIElement1;
/* 55 */     for (i = (arrayOfIElement1 = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 56 */       c.buildGUI();
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LayoutManager getLayoutManager() {
/* 67 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 75 */     return "BorderLayout";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 83 */     XBorderLayout elem = new XBorderLayout();
/* 84 */     copyElementData((IElement)elem);
/* 85 */     return (IElement)elem;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XBorderLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */