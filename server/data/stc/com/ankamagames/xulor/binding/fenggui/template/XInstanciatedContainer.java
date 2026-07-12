/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.component.Container;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import java.io.PrintStream;
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
/*    */ public class XInstanciatedContainer
/*    */   extends XContainer
/*    */ {
/*    */   public static final String TAG = "InstanciatedContainer";
/*    */   public static final String SHORT_TAG = "IC";
/*    */   
/*    */   public XInstanciatedContainer(Container c)
/*    */   {
/* 25 */     this.m_container = c;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI()
/*    */   {
/*    */     IElement[] arrayOfIElement;
/*    */     
/*    */ 
/*    */ 
/* 37 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 38 */       c.buildGUI();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildXML()
/*    */   {
/* 48 */     IElement[] components = getChildren();
/* 49 */     System.out.println("<IC>");
/* 50 */     IElement[] arrayOfIElement1; int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 51 */       c.buildXML();
/*    */     }
/* 53 */     System.out.println("</IC>");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 61 */     return "InstanciatedContainer";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 68 */     XInstanciatedContainer container = new XInstanciatedContainer(this.m_container);
/* 69 */     copyElementData(container);
/* 70 */     return container;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XInstanciatedContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */