/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.core.form.Form;
/*    */ import com.ankamagames.xulor.core.form.FormValidateCallBack;
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
/*    */ 
/*    */ public class FormConverter
/*    */   implements Converter
/*    */ {
/*    */   public Object convert(Class type, String func) {
/* 27 */     if (func == null) {
/* 28 */       return null;
/*    */     }
/*    */     
/* 31 */     if (type.equals(FormValidateCallBack.class)) {
/* 32 */       FormValidateCallBack validate = new FormValidateCallBack();
/* 33 */       ElementMap currentElementMap = Xulor.getInstance().getEnvironment().getCurrentElementMap();
/* 34 */       Form currentForm = Xulor.getInstance().getEnvironment().getCurrentForm();
/* 35 */       validate.setFunc(func, currentElementMap, currentForm);
/* 36 */       return validate;
/*    */     } 
/*    */     
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 48 */     return FormValidateCallBack.class;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\FormConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */