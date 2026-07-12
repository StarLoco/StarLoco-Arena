/*    */ package com.ankamagames.framework.ai.targetfinder.aoe;
/*    */ 
/*    */ import com.ankamagames.framework.external.ExportableEnum;
/*    */ import com.ankamagames.framework.kernel.utils.ExceptionFormatter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum AreaOfEffectEnum
/*    */   implements ExportableEnum
/*    */ {
/* 24 */   POINT((short)1, PointAOE.class), 
/*    */   
/*    */ 
/* 27 */   CIRCLE((short)2, CircleAOE.class), 
/*    */   
/*    */ 
/*    */ 
/* 31 */   CROSS((short)3, CrossAOE.class), 
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 41 */   T((short)4, TAOE.class), 
/*    */   
/*    */ 
/* 44 */   EMPTY((short)Short.MAX_VALUE, EmptyAOE.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 49 */   private static final Logger m_logger = Logger.getLogger(AreaOfEffectEnum.class);
/*    */   
/*    */ 
/*    */   private short m_index;
/*    */   
/*    */   private Class<? extends AreaOfEffect> m_class;
/*    */   
/*    */ 
/*    */   private AreaOfEffectEnum(short index, Class<? extends AreaOfEffect> c)
/*    */   {
/* 59 */     this.m_index = index;
/* 60 */     this.m_class = c;
/*    */   }
/*    */   
/*    */   public short getIndex() {
/* 64 */     return this.m_index;
/*    */   }
/*    */   
/*    */   public AreaOfEffect newInstance(int[] params) throws IllegalArgumentException {
/*    */     try {
/* 69 */       AreaOfEffect aoe = (AreaOfEffect)this.m_class.newInstance();
/* 70 */       aoe.initialize(params);
/* 71 */       return aoe;
/*    */     } catch (InstantiationException e) {
/* 73 */       m_logger.error(ExceptionFormatter.toString(e));
/*    */     } catch (IllegalAccessException e) {
/* 75 */       m_logger.error(ExceptionFormatter.toString(e));
/*    */     }
/* 77 */     return null;
/*    */   }
/*    */   
/*    */   public static AreaOfEffect newInstance(int aoeId, int[] params) throws IllegalArgumentException { AreaOfEffectEnum[] arrayOfAreaOfEffectEnum;
/* 81 */     int j = (arrayOfAreaOfEffectEnum = values()).length; for (int i = 0; i < j; i++) { AreaOfEffectEnum aoe = arrayOfAreaOfEffectEnum[i];
/* 82 */       if (aoe.getIndex() == aoeId) {
/* 83 */         return aoe.newInstance(params);
/*    */       }
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */   
/*    */   public String getEnumId() {
/* 90 */     return Short.valueOf(this.m_index).toString();
/*    */   }
/*    */   
/*    */   public String getEnumLabel() {
/* 94 */     return toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\AreaOfEffectEnum.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */