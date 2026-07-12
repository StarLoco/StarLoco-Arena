/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.Dimension;
/*    */ import com.ankamagames.xulor.util.Percentage;
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DimensionConverter
/*    */   implements Converter
/*    */ {
/* 19 */   public static final Class TEMPLATE = Dimension.class;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object convert(Class type, String value) {
/* 28 */     if (value != null) {
/* 29 */       StringTokenizer st = new StringTokenizer(value, ",");
/* 30 */       Dimension dim = new Dimension();
/* 31 */       if (st.hasMoreTokens()) {
/* 32 */         String width = st.nextToken().trim();
/* 33 */         if (width.endsWith("%")) {
/* 34 */           dim.setWidthPercentage(Percentage.valueOf(width));
/*    */         } else {
/* 36 */           dim.setWidth(Integer.parseInt(width));
/*    */         } 
/*    */       } 
/* 39 */       if (st.hasMoreTokens()) {
/* 40 */         String height = st.nextToken().trim();
/* 41 */         if (height.endsWith("%")) {
/* 42 */           dim.setHeightPercentage(Percentage.valueOf(height));
/*    */         } else {
/* 44 */           dim.setHeight(Integer.parseInt(height));
/*    */         } 
/*    */       } 
/* 47 */       return dim;
/*    */     } 
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class convertsTo() {
/* 58 */     return TEMPLATE;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\DimensionConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */