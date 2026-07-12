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
/*    */   
/*    */   public void applyAllAttributes() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getEncapsulatedObject() {
/* 32 */     return this.m_value;
/*    */   }
/*    */   
/*    */   public void buildGUI() {
/*    */     byte b;
/*    */     int i;
/*    */     IElement[] arrayOfIElement;
/* 39 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/* 40 */       c.buildGUI();
/*    */       b++; }
/*    */   
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
/*    */   public Object getElementValue() {
/* 55 */     return this.m_value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 62 */     return this.m_value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 69 */     this.m_value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void copyElementData(XData element) {
/* 76 */     copyElementData(element);
/* 77 */     element.setValue(this.m_value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 84 */     XData data = new XData();
/* 85 */     copyElementData(data);
/* 86 */     return data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 93 */     return "Data";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\impl\XData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */