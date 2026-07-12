/*    */ package com.ankamagames.xulor.core.form;
/*    */ 
/*    */ import com.ankamagames.xulor.core.CallBack;
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import java.util.List;
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
/*    */ public class FormValidateCallBack
/*    */   extends CallBack
/*    */ {
/* 19 */   private Form m_form = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFunc(String func, ElementMap elementMap, Form form) {
/* 27 */     this.m_form = form;
/* 28 */     setCallBackFunc(func, elementMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void fillParameters(String[] parameters, List<Class<?>> parameterTypes, List<Object> args) {
/* 39 */     parameterTypes.add(Form.class);
/* 40 */     args.add(this.m_form);
/* 41 */     super.fillParameters(parameters, parameterTypes, args);
/*    */   }
/*    */   
/*    */   public void copyCallback(FormValidateCallBack listener) {
/* 45 */     listener.setFunc(this.m_func, this.m_elementMap, this.m_form);
/*    */   }
/*    */   
/*    */   public FormValidateCallBack cloneListener() {
/* 49 */     FormValidateCallBack cb = new FormValidateCallBack();
/* 50 */     copyCallback(cb);
/* 51 */     return cb;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\form\FormValidateCallBack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */