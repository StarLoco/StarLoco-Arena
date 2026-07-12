/*    */ package com.ankamagames.framework.ai.criteria;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class LexemDictionary
/*    */   implements Iterable<LexemDefinition>
/*    */ {
/* 19 */   private final List<LexemDefinition> m_lexemDefinitions = new ArrayList();
/*    */   
/*    */   public void add(LexemDefinition def) {
/* 22 */     this.m_lexemDefinitions.add(def);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Iterator<LexemDefinition> iterator()
/*    */   {
/* 31 */     return this.m_lexemDefinitions.iterator();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Lexem getLexem(String token)
/*    */   {
/* 40 */     for (LexemDefinition def : this.m_lexemDefinitions) {
/* 41 */       if (def.match(token))
/* 42 */         return def.getLexem(token);
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\criteria\LexemDictionary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */