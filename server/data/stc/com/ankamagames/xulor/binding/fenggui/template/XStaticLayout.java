/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.binding.fenggui.component.StaticLayoutPlus;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IStaticLayout;
/*    */ import java.io.PrintStream;
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
/*    */   public void buildXML()
/*    */   {
/* 36 */     System.out.println("<staticlayout>");
/* 37 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 38 */       c.buildXML();
/*    */     }
/* 40 */     System.out.println("</staticlayout>");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI()
/*    */   {
/* 49 */     if (this.m_staticLayout == null) {
/* 50 */       this.m_staticLayout = new StaticLayoutPlus();
/*    */       
/* 52 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_staticLayout, this);
/*    */       
/* 54 */       if ((this.m_parent instanceof XContainer)) {
/* 55 */         ((XContainer)this.m_parent).setLayoutManager(this.m_staticLayout);
/*    */       }
/*    */     }
/*    */     IElement[] arrayOfIElement;
/* 59 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 60 */       c.buildGUI();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LayoutManager getLayoutManager()
/*    */   {
/* 70 */     return this.m_staticLayout;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 78 */     return "StaticLayout";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 85 */     XStaticLayout elem = new XStaticLayout();
/* 86 */     copyElementData(elem);
/* 87 */     return elem;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XStaticLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */