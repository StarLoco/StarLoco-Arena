/*    */ package com.ankamagames.dofusarena.common.game.ai;
/*    */ 
/*    */ import com.ankamagames.framework.ai.criteria.Criterion;
/*    */ import com.ankamagames.framework.ai.criteria.LexemDictionary;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public final class CriteriaCompiler
/*    */ {
/* 22 */   protected static final Logger m_logger = Logger.getLogger(CriteriaCompiler.class);
/*    */   
/* 24 */   private static String m_lastError = "";
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getLastError()
/*    */   {
/* 31 */     return m_lastError;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static List<Criterion> compile(LexemDictionary dictionary, String s)
/*    */   {
/* 42 */     if ((s == null) || (s.length() == 0))
/* 43 */       return null;
/* 44 */     List<Criterion> criterions = new ArrayList();
/*    */     
/* 46 */     String[] sCriterions = s.split(";");
/*    */     String[] arrayOfString1;
/* 48 */     int j = (arrayOfString1 = sCriterions).length; for (int i = 0; i < j; i++) { String criterion = arrayOfString1[i];
/*    */       
/* 50 */       if ("canSummon".equalsIgnoreCase(criterion)) {
/* 51 */         criterions.add(new CanSummonCriterion());
/*    */ 
/*    */ 
/*    */       }
/* 55 */       else if ("canCastWhenCarrying".equalsIgnoreCase(criterion)) {
/* 56 */         criterions.add(new CanCastWhenCarryCriterion(true));
/*    */ 
/*    */ 
/*    */       }
/* 60 */       else if ("cantCastWhenCarrying".equalsIgnoreCase(criterion)) {
/* 61 */         criterions.add(new CanCastWhenCarryCriterion(false));
/*    */ 
/*    */       }
/* 64 */       else if ("cantCastWhenCarried".equalsIgnoreCase(criterion)) {
/* 65 */         criterions.add(new CantCastWhenCarriedCriterion());
/*    */       }
/*    */       else
/*    */       {
/* 69 */         m_logger.error("Critère invalide : '" + criterion + "'"); }
/*    */     }
/* 71 */     return criterions;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\ai\CriteriaCompiler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */