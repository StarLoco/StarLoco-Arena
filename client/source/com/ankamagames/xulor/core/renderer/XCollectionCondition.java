/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ public class XCollectionCondition
/*    */   extends XUnaryConditionOperator
/*    */ {
/*    */   public static final String TAG = "CollectionCondition";
/*    */   public static final String SIZE_KEY = "size";
/*    */   
/*    */   public String getTag() {
/* 27 */     return "CollectionCondition";
/*    */   }
/*    */   
/*    */   public boolean isValid(Object object) {
/* 31 */     if (this.m_comparedValueInit) {
/* 32 */       object = this.m_comparedValue;
/*    */     }
/* 34 */     if (this.m_key != null && 
/* 35 */       this.m_key.equalsIgnoreCase("size")) {
/* 36 */       if (object instanceof Collection)
/* 37 */         return this.m_condition.isValid(Integer.valueOf(((Collection)object).size())); 
/* 38 */       if (object instanceof Object[]) {
/* 39 */         return this.m_condition.isValid(Integer.valueOf(((Object[])object).length));
/*    */       }
/* 41 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 49 */     XCollectionCondition clone = new XCollectionCondition();
/* 50 */     copyConditionData(clone);
/* 51 */     return clone;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XCollectionCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */