/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.ComponentType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentTypeConverter
/*    */   implements Converter
/*    */ {
/* 17 */   private Class TEMPLATE = ComponentType.class;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object convert(Class type, String value) {
/* 27 */     if (value != null && 
/* 28 */       type.equals(ComponentType.class)) {
/* 29 */       return ComponentType.valueOf(value.toUpperCase());
/*    */     }
/*    */ 
/*    */     
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 42 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\ComponentTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */