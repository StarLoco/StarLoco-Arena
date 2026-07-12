/*    */ package com.ankamagames.framework.ai.criteria;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LexemDefinition
/*    */ {
/*    */   private final Class<? extends Lexem> m_lexemClass;
/*    */   private final boolean m_caseInsensitive;
/*    */   private final String[] m_tokens;
/*    */   
/*    */   public LexemDefinition(Class<? extends Lexem> lexem, String... tokens) {
/* 27 */     this(lexem, true, tokens);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LexemDefinition(Class<? extends Lexem> lexem, boolean caseInsensitive, String... tokens) {
/* 39 */     this.m_lexemClass = lexem;
/* 40 */     this.m_tokens = tokens;
/* 41 */     this.m_caseInsensitive = caseInsensitive;
/*    */   }
/*    */   
/*    */   public Lexem getLexem(String token) {
/* 45 */     Lexem lexem = null;
/*    */     try {
/* 47 */       lexem = this.m_lexemClass.newInstance();
/* 48 */     } catch (InstantiationException e) {
/* 49 */       e.printStackTrace();
/* 50 */     } catch (IllegalAccessException e) {
/* 51 */       e.printStackTrace();
/*    */     } 
/* 53 */     return lexem;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean match(String token) {
/*    */     byte b;
/*    */     int i;
/*    */     String[] arrayOfString;
/* 62 */     for (i = (arrayOfString = this.m_tokens).length, b = 0; b < i; ) { String t = arrayOfString[b];
/* 63 */       if ((this.m_caseInsensitive && t.equalsIgnoreCase(token)) || (!this.m_caseInsensitive && t.equals(token)))
/* 64 */         return true;  b++; }
/* 65 */      return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\criteria\LexemDefinition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */