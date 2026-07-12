/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.event.DropValidateCallBack;
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
/*    */ public class DropValidateConverter
/*    */   implements Converter
/*    */ {
/*    */   public Object convert(Class type, String func)
/*    */   {
/* 26 */     if (func == null) {
/* 27 */       return null;
/*    */     }
/*    */     
/* 30 */     if (type.equals(DropValidateCallBack.class)) {
/* 31 */       DropValidateCallBack validate = new DropValidateCallBack();
/* 32 */       ElementMap currentElementMap = Xulor.getInstance().getEnvironment().getCurrentElementMap();
/* 33 */       validate.setFunc(func, currentElementMap);
/* 34 */       return validate;
/*    */     }
/*    */     
/* 37 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 46 */     return DropValidateCallBack.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\DropValidateConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */