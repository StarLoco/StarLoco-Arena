/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import com.ankamagames.xulor.util.Spacing;
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
/*    */ public class SpacingConverter
/*    */   implements Converter
/*    */ {
/* 19 */   private Class TEMPLATE = Spacing.class;
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
/* 30 */       (type.equals(Spacing.class)))
/*    */     {
/* 32 */       StringTokenizer st = new StringTokenizer(value, ",");
/* 33 */       int top = 0;int bottom = 0;
/* 34 */       int left = 0;int right = 0;
/*    */       
/* 36 */       if (st.hasMoreTokens()) {
/* 37 */         top = Integer.parseInt(st.nextToken().trim());
/*    */       }
/* 39 */       if (st.hasMoreTokens()) {
/* 40 */         bottom = Integer.parseInt(st.nextToken().trim());
/*    */       }
/* 42 */       if (st.hasMoreTokens()) {
/* 43 */         left = Integer.parseInt(st.nextToken().trim());
/*    */       }
/* 45 */       if (st.hasMoreTokens()) {
/* 46 */         right = Integer.parseInt(st.nextToken().trim());
/*    */       }
/*    */       
/* 49 */       return new Spacing(top, bottom, left, right);
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\SpacingConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */