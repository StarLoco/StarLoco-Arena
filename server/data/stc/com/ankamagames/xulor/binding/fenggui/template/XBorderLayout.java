/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.template.IBorderLayout;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import java.io.PrintStream;
/*    */ import org.fenggui.LayoutManager;
/*    */ import org.fenggui.layout.BorderLayout;
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
/*    */   public void buildXML()
/*    */   {
/* 29 */     IElement[] components = getChildren();
/* 30 */     System.out.println("<borderlayout>");
/* 31 */     IElement[] arrayOfIElement1; int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 32 */       c.buildXML();
/*    */     }
/* 34 */     System.out.println("</borderlayout>");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI()
/*    */   {
/* 43 */     IElement[] components = getChildren();
/* 44 */     if (this.m_borderLayout == null) {
/* 45 */       BorderLayout bl = new BorderLayout();
/* 46 */       this.m_borderLayout = bl;
/*    */       
/* 48 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_borderLayout, this);
/*    */       
/* 50 */       if ((this.m_parent instanceof XContainer)) {
/* 51 */         ((XContainer)this.m_parent).setLayoutManager(bl);
/*    */       }
/*    */     }
/*    */     IElement[] arrayOfIElement1;
/* 55 */     int j = (arrayOfIElement1 = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 56 */       c.buildGUI();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LayoutManager getLayoutManager()
/*    */   {
/* 67 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 75 */     return "BorderLayout";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 83 */     XBorderLayout elem = new XBorderLayout();
/* 84 */     copyElementData(elem);
/* 85 */     return elem;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XBorderLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */