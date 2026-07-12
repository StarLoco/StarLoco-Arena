/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.Propagation;
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
/*    */ public class PropagationConverter
/*    */   implements Converter
/*    */ {
/* 19 */   private Class TEMPLATE = Propagation.class;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object convert(Class type, String value) {
/* 29 */     if (value != null && 
/* 30 */       type.equals(Propagation.class)) {
/* 31 */       return Propagation.valueOf(value.toUpperCase());
/*    */     }
/*    */ 
/*    */     
/* 35 */     return Propagation.PASS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 44 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\PropagationConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */