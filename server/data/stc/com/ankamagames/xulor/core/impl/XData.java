/*    */ package com.ankamagames.xulor.core.impl;
/*    */ 
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
/*    */ public class XData
/*    */   extends XElement
/*    */ {
/*    */   public static final String TAG = "Data";
/* 18 */   private Object m_value = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void applyAllAttributes() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object getEncapsulatedObject()
/*    */   {
/* 32 */     return this.m_value;
/*    */   }
/*    */   
/*    */ 
/*    */   public void buildGUI()
/*    */   {
/*    */     IElement[] arrayOfIElement;
/* 39 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/* 40 */       c.buildGUI();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildXML() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object getElementValue()
/*    */   {
/* 55 */     return this.m_value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 62 */     return this.m_value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setValue(Object value)
/*    */   {
/* 69 */     this.m_value = value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void copyElementData(XData element)
/*    */   {
/* 76 */     super.copyElementData(element);
/* 77 */     element.setValue(this.m_value);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 84 */     XData data = new XData();
/* 85 */     copyElementData(data);
/* 86 */     return data;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 93 */     return "Data";
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\impl\XData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */