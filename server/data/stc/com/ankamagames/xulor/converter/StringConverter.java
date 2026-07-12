/*    */ package com.ankamagames.xulor.converter;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Converter;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
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
/*    */ public class StringConverter
/*    */   implements Converter
/*    */ {
/* 20 */   public static final Class TEMPLATE = String.class;
/*    */   
/* 22 */   private static final Pattern TRANSLATION_PATTERN = Pattern.compile("(%([^%]*)%)");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object convert(Class type, String value)
/*    */   {
/* 33 */     Matcher matcher = TRANSLATION_PATTERN.matcher(value);
/* 34 */     String translatedValue = value.toString();
/* 35 */     while (matcher.find()) {
/* 36 */       translatedValue = translatedValue.replace(matcher.group(1), Xulor.getInstance().getTranslatedString(matcher.group(2)));
/*    */     }
/*    */     
/* 39 */     return translatedValue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class convertsTo()
/*    */   {
/* 48 */     return TEMPLATE;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\converter\StringConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */