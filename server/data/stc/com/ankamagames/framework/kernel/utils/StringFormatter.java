/*     */ package com.ankamagames.framework.kernel.utils;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringFormatter
/*     */ {
/*  20 */   public static final Logger m_logger = Logger.getLogger(StringFormatter.class);
/*     */   
/*     */ 
/*  23 */   private static Pattern CONDITION_GLOBAL_PATTERN = Pattern.compile("\\{((\\[[^\\[\\]{}?:]*\\])+)\\?([^\\}]*):([^\\}]*)\\}");
/*     */   
/*     */ 
/*  26 */   private static Pattern CONDITION_LOCAL_PATTERN = Pattern.compile("\\[([\\~\\*\\>])([^\\[\\]]+)\\]");
/*     */   
/*     */ 
/*  29 */   private static Pattern REPLACE_BY_ARGS_PATTERN = Pattern.compile("\\[\\#([0-9]+)\\]");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String format(String string, Object... args)
/*     */   {
/*  38 */     StringBuffer formattedString = new StringBuffer();
/*     */     
/*     */ 
/*  41 */     Matcher matcher = CONDITION_GLOBAL_PATTERN.matcher(string);
/*  42 */     while (matcher.find()) {
/*  43 */       boolean conditionResult = false;
/*     */       
/*     */ 
/*  46 */       String conditionGroup = matcher.group(1);
/*  47 */       Matcher localMatcher = CONDITION_LOCAL_PATTERN.matcher(conditionGroup);
/*  48 */       while (localMatcher.find()) {
/*  49 */         char conditionChar = localMatcher.group(1).charAt(0);
/*  50 */         int conditionArg = Integer.parseInt(localMatcher.group(2));
/*     */         
/*  52 */         switch (conditionChar)
/*     */         {
/*     */         case '>': 
/*  55 */           if (args.length >= conditionArg) {
/*  56 */             conditionResult = isPlural(args[(conditionArg - 1)]);
/*     */           }
/*  58 */           break;
/*     */         
/*     */ 
/*     */         case '~': 
/*  62 */           conditionResult = (args.length >= conditionArg) && (args[(conditionArg - 1)] != null) && (isSuperiorAt(args[(conditionArg - 1)], 0));
/*  63 */           break;
/*     */         
/*     */         case '*': 
/*     */           break;
/*     */         
/*     */ 
/*     */         default: 
/*  70 */           m_logger.error("Impossible de formatter l'expression : " + string);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*  75 */       if (conditionResult) {
/*  76 */         matcher.appendReplacement(formattedString, matcher.group(3));
/*     */       } else
/*  78 */         matcher.appendReplacement(formattedString, matcher.group(4));
/*     */     }
/*  80 */     matcher.appendTail(formattedString);
/*     */     
/*     */ 
/*  83 */     matcher = REPLACE_BY_ARGS_PATTERN.matcher(formattedString.toString());
/*     */     
/*  85 */     formattedString = new StringBuffer();
/*  86 */     while (matcher.find()) {
/*  87 */       int argIndex = Integer.parseInt(matcher.group(1)) - 1;
/*     */       
/*  89 */       if (args.length > argIndex) {
/*  90 */         matcher.appendReplacement(formattedString, args[argIndex].toString());
/*     */       } else
/*  92 */         matcher.appendReplacement(formattedString, "");
/*     */     }
/*  94 */     matcher.appendTail(formattedString);
/*     */     
/*  96 */     return formattedString.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isPlural(Object object)
/*     */   {
/* 105 */     return isSuperiorAt(object, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isSuperiorAt(Object object, int value)
/*     */   {
/* 114 */     if ((object instanceof Integer))
/* 115 */       return ((Integer)object).intValue() > value;
/* 116 */     if ((object instanceof Float))
/* 117 */       return ((Float)object).floatValue() > value;
/* 118 */     if ((object instanceof Double))
/* 119 */       return ((Double)object).doubleValue() > value;
/* 120 */     if ((object instanceof Short))
/* 121 */       return ((Short)object).shortValue() > value;
/* 122 */     if ((object instanceof Byte))
/* 123 */       return ((Byte)object).byteValue() > value;
/* 124 */     if ((object instanceof String)) {
/* 125 */       return Double.parseDouble((String)object) > value;
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\utils\StringFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */