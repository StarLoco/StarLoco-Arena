/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.Alignment;
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
/*    */ public class AlignmentConverter
/*    */   implements Converter
/*    */ {
/* 19 */   private Class TEMPLATE = Alignment.class;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object convert(Class type, String value)
/*    */   {
/* 29 */     if ((value != null) && 
/* 30 */       (type.equals(Alignment.class))) {
/* 31 */       return Alignment.valueOf(value.toUpperCase());
/*    */     }
/*    */     
/*    */ 
/* 35 */     return Alignment.NORTH_WEST;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 44 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\AlignmentConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */