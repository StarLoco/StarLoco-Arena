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
/* 24 */   POINT((short)1, (Class)PointAOE.class),
/*    */ 
/*    */   
/* 27 */   CIRCLE((short)2, (Class)CircleAOE.class),
/*    */ 
/*    */ 
/*    */   
/* 31 */   CROSS((short)3, (Class)CrossAOE.class),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   T((short)4, (Class)TAOE.class),
/*    */ 
/*    */   
/* 44 */   EMPTY('翿', (Class)EmptyAOE.class); private static final Logger m_logger;
/*    */   private short m_index;
/*    */   private Class<? extends AreaOfEffect> m_class;
/*    */   
/*    */   static {
/* 49 */     m_logger = Logger.getLogger(AreaOfEffectEnum.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   AreaOfEffectEnum(short index, Class<? extends AreaOfEffect> c) {
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
/* 69 */       AreaOfEffect aoe = this.m_class.newInstance();
/* 70 */       aoe.initialize(params);
/* 71 */       return aoe;
/* 72 */     } catch (InstantiationException e) {
/* 73 */       m_logger.error(ExceptionFormatter.toString(e));
/* 74 */     } catch (IllegalAccessException e) {
/* 75 */       m_logger.error(ExceptionFormatter.toString(e));
/*    */     } 
/* 77 */     return null; } public static AreaOfEffect newInstance(int aoeId, int[] params) throws IllegalArgumentException {
/*    */     byte b;
/*    */     int i;
/*    */     AreaOfEffectEnum[] arrayOfAreaOfEffectEnum;
/* 81 */     for (i = (arrayOfAreaOfEffectEnum = values()).length, b = 0; b < i; ) { AreaOfEffectEnum aoe = arrayOfAreaOfEffectEnum[b];
/* 82 */       if (aoe.getIndex() == aoeId)
/* 83 */         return aoe.newInstance(params); 
/*    */       b++; }
/*    */     
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


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\targetfinder\aoe\AreaOfEffectEnum.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */