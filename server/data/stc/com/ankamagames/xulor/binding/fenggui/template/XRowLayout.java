/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IRowLayout;
/*    */ import java.io.PrintStream;
/*    */ import org.fenggui.LayoutManager;
/*    */ import org.fenggui.layout.RowLayout;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XRowLayout
/*    */   extends XLayoutManager
/*    */   implements IRowLayout
/*    */ {
/*    */   public static final String TAG = "RowLayout";
/*    */   public static final String SHORT_TAG = "RL";
/* 22 */   private RowLayout m_rowLayout = null;
/*    */   
/* 24 */   boolean m_horizontal = true;
/*    */   
/*    */ 
/*    */ 
/*    */   public void setHorizontal(boolean h)
/*    */   {
/* 30 */     this.m_horizontal = h;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildXML()
/*    */   {
/* 38 */     System.out.println("<rowlayout horizontal=\"" + this.m_horizontal + "\">");
/* 39 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 40 */       c.buildXML();
/*    */     }
/* 42 */     System.out.println("</rowlayout>");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI()
/*    */   {
/* 50 */     if (this.m_rowLayout == null)
/*    */     {
/* 52 */       this.m_rowLayout = new RowLayout(this.m_horizontal);
/*    */       
/*    */ 
/*    */ 
/* 56 */       if ((this.m_parent instanceof XContainer)) {
/* 57 */         ((XContainer)this.m_parent).setLayoutManager(this.m_rowLayout);
/*    */       }
/*    */     }
/*    */     IElement[] arrayOfIElement;
/* 61 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 62 */       c.buildGUI();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LayoutManager getLayoutManager()
/*    */   {
/* 72 */     return this.m_rowLayout;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 80 */     return "RowLayout";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 87 */     XRowLayout elem = new XRowLayout();
/* 88 */     elem.setHorizontal(this.m_horizontal);
/* 89 */     copyElementData(elem);
/* 90 */     return elem;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XRowLayout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */