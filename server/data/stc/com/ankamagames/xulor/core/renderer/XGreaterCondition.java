/*    */ package com.ankamagames.xulor.core.renderer;
/*    */ 
/*    */ import com.ankamagames.xulor.util.PrimitiveConverter;
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
/*    */ public class XGreaterCondition
/*    */   extends XCondition
/*    */ {
/*    */   public static final String TAG = "isGreater";
/*    */   
/*    */   public String getTag()
/*    */   {
/* 22 */     return "isGreater";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid(Object object)
/*    */   {
/* 30 */     if (this.m_comparedValueInit)
/* 31 */       object = this.m_comparedValue;
/* 32 */     if ((object instanceof Integer))
/* 33 */       return PrimitiveConverter.getInteger(object) > PrimitiveConverter.getInteger(this.m_value);
/* 34 */     if ((object instanceof Float))
/* 35 */       return PrimitiveConverter.getFloat(object) > PrimitiveConverter.getFloat(this.m_value);
/* 36 */     if ((object instanceof Double))
/* 37 */       return PrimitiveConverter.getDouble(object) > PrimitiveConverter.getDouble(this.m_value);
/* 38 */     if ((object instanceof Short))
/* 39 */       return PrimitiveConverter.getShort(object) > PrimitiveConverter.getShort(this.m_value);
/* 40 */     if ((object instanceof Long)) {
/* 41 */       return PrimitiveConverter.getLong(object) > PrimitiveConverter.getLong(this.m_value);
/*    */     }
/*    */     
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   public XGreaterCondition cloneCondition() {
/* 48 */     XGreaterCondition clone = new XGreaterCondition();
/* 49 */     copyConditionData(clone);
/* 50 */     return clone;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XGreaterCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */