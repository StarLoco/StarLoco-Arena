/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.Percentage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PercentageConverter
/*    */   implements Converter
/*    */ {
/* 17 */   public static final Class TEMPLATE = Percentage.class;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object convert(Class type, String value) {
/* 26 */     if (!type.equals(TEMPLATE)) {
/* 27 */       return null;
/*    */     }
/* 29 */     return Percentage.valueOf(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 38 */     return TEMPLATE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\PercentageConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */