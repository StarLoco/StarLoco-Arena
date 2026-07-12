/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.component.MapNavigator.MapShape;
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapShapeConverter
/*    */   implements Converter
/*    */ {
/* 14 */   private Class TEMPLATE = MapNavigator.MapShape.class;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object convert(Class type, String value)
/*    */   {
/* 24 */     if ((value != null) && 
/* 25 */       (type.equals(MapNavigator.MapShape.class))) {
/* 26 */       return MapNavigator.MapShape.valueOf(value.toUpperCase());
/*    */     }
/*    */     
/* 29 */     return MapNavigator.MapShape.RECTANGLE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 38 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\MapShapeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */