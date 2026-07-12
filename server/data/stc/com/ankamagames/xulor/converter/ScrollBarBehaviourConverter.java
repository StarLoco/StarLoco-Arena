/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.ScrollBarBehaviour;
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
/*    */ public class ScrollBarBehaviourConverter
/*    */   implements Converter
/*    */ {
/* 20 */   private Class TEMPLATE = ScrollBarBehaviour.class;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object convert(Class type, String value)
/*    */   {
/* 30 */     if ((value != null) && 
/* 31 */       (type.equals(ScrollBarBehaviour.class))) {
/* 32 */       return ScrollBarBehaviour.valueOf(value.toUpperCase());
/*    */     }
/*    */     
/*    */ 
/* 36 */     return ScrollBarBehaviour.WHEN_NEEDED;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 45 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\ScrollBarBehaviourConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */