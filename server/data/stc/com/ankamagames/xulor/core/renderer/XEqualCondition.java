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
/*    */ public class XEqualCondition
/*    */   extends XCondition
/*    */ {
/*    */   public static final String TAG = "isEqual";
/*    */   
/*    */   public String getTag()
/*    */   {
/* 22 */     return "isEqual";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid(Object object)
/*    */   {
/* 30 */     if (this.m_comparedValueInit)
/* 31 */       object = this.m_comparedValue;
/* 32 */     if ((object == null) && (this.m_value.equals("null"))) {
/* 33 */       return true;
/*    */     }
/*    */     
/* 36 */     if ((object instanceof String)) {
/* 37 */       String val = (String)object;
/* 38 */       return val.equalsIgnoreCase((String)this.m_value); }
/* 39 */     if ((object instanceof Integer))
/* 40 */       return PrimitiveConverter.getInteger(object) == PrimitiveConverter.getInteger(this.m_value);
/* 41 */     if ((object instanceof Float))
/* 42 */       return PrimitiveConverter.getFloat(object) == PrimitiveConverter.getFloat(this.m_value);
/* 43 */     if ((object instanceof Double))
/* 44 */       return PrimitiveConverter.getDouble(object) == PrimitiveConverter.getDouble(this.m_value);
/* 45 */     if ((object instanceof Short))
/* 46 */       return PrimitiveConverter.getShort(object) == PrimitiveConverter.getShort(this.m_value);
/* 47 */     if ((object instanceof Long)) {
/* 48 */       return PrimitiveConverter.getLong(object) == PrimitiveConverter.getLong(this.m_value);
/*    */     }
/*    */     
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public XCondition cloneCondition() {
/* 55 */     XEqualCondition clone = new XEqualCondition();
/* 56 */     copyConditionData(clone);
/* 57 */     return clone;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\XEqualCondition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */