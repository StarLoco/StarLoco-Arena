/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.Color;
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
/*    */ 
/*    */ public class ColorConverter
/*    */   implements Converter
/*    */ {
/* 19 */   private Class TEMPLATE = Color.class;
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
/* 30 */       (type.equals(Color.class)))
/*    */     {
/* 32 */       StringTokenizer st = new StringTokenizer(value, ",");
/* 33 */       double r = 0.0D;double g = 0.0D;
/* 34 */       double b = 0.0D;double a = 1.0D;
/*    */       
/* 36 */       if (st.hasMoreTokens()) {
/* 37 */         r = Double.parseDouble(st.nextToken().trim());
/*    */       }
/* 39 */       if (st.hasMoreTokens()) {
/* 40 */         g = Double.parseDouble(st.nextToken().trim());
/*    */       }
/* 42 */       if (st.hasMoreTokens()) {
/* 43 */         b = Double.parseDouble(st.nextToken().trim());
/*    */       }
/* 45 */       if (st.hasMoreTokens()) {
/* 46 */         a = Double.parseDouble(st.nextToken().trim());
/*    */       }
/*    */       
/* 49 */       return new Color(r, g, b, a);
/*    */     }
/*    */     
/*    */ 
/* 53 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 62 */     return this.TEMPLATE;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\ColorConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */