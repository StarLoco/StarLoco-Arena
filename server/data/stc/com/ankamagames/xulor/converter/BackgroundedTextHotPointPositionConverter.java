/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.text.BackgroundedText.BackgroundedTextHotPointPosition;
/*    */ import com.ankamagames.xulor.core.Converter;
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
/*    */ public class BackgroundedTextHotPointPositionConverter
/*    */   implements Converter
/*    */ {
/* 19 */   private Class TEMPLATE = BackgroundedText.BackgroundedTextHotPointPosition.class;
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
/* 30 */       (type.equals(BackgroundedText.BackgroundedTextHotPointPosition.class))) {
/* 31 */       return BackgroundedText.BackgroundedTextHotPointPosition.valueOf(value.toUpperCase());
/*    */     }
/*    */     
/*    */ 
/* 35 */     return BackgroundedText.BackgroundedTextHotPointPosition.SOUTH_WEST;
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\BackgroundedTextHotPointPositionConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */