/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.component.Container;
/*    */ import com.ankamagames.xulor.template.IElement;
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
/*    */ public class XInstanciatedContainer
/*    */   extends XContainer
/*    */ {
/*    */   public static final String TAG = "InstanciatedContainer";
/*    */   public static final String SHORT_TAG = "IC";
/*    */   
/*    */   public XInstanciatedContainer(Container c) {
/* 25 */     this.m_container = c;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildGUI() {
/*    */     byte b;
/*    */     int i;
/*    */     IElement[] arrayOfIElement;
/* 37 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 38 */       c.buildGUI();
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildXML() {
/* 48 */     IElement[] components = getChildren();
/* 49 */     System.out.println("<IC>"); byte b; int i; IElement[] arrayOfIElement1;
/* 50 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 51 */       c.buildXML(); b++; }
/*    */     
/* 53 */     System.out.println("</IC>");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 61 */     return "InstanciatedContainer";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 68 */     XInstanciatedContainer container = new XInstanciatedContainer(this.m_container);
/* 69 */     copyElementData(container);
/* 70 */     return (IElement)container;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XInstanciatedContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */