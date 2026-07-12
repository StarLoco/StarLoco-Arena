/*    */ package com.ankamagames.framework.kernel.core.common;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*    */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
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
/*    */ public class GUIDGenerator
/*    */   implements MessageHandler
/*    */ {
/*    */   private static final long HOUR_TO_MILLISEC = 3600000L;
/*    */   private int m_nextIntPart;
/* 29 */   private int m_serverId = -1;
/*    */   
/*    */   private long m_fixedPart;
/* 32 */   private static final GUIDGenerator m_instance = new GUIDGenerator();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void init(int serverId)
/*    */   {
/* 41 */     if ((serverId < 0) || (serverId > 255))
/* 42 */       throw new IllegalArgumentException("Le numéro de serveur doit être compris entre 0 et 255");
/* 43 */     m_instance.m_serverId = serverId;
/* 44 */     m_instance.m_nextIntPart = 0;
/* 45 */     m_instance.initFixedPart();
/* 46 */     MessageScheduler.getInstance().addClock(m_instance, 3600000L, 0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static long getGUID()
/*    */   {
/* 54 */     return m_instance.m_fixedPart + m_instance.m_nextIntPart++;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onMessage(Message message)
/*    */   {
/* 63 */     if (message.getId() == Integer.MIN_VALUE) {
/* 64 */       initFixedPart();
/* 65 */       this.m_nextIntPart = 0;
/* 66 */       return false;
/*    */     }
/* 68 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private void initFixedPart()
/*    */   {
/* 75 */     if ((this.m_serverId < 0) || (this.m_serverId > 255))
/* 76 */       throw new IllegalArgumentException("Impossible d'initialiser le GUIDGenerator : Le numéro de serveur doit être fixé par la méthode init");
/* 77 */     this.m_fixedPart = ((this.m_serverId & 0xFF) << 56);
/* 78 */     this.m_fixedPart |= (System.currentTimeMillis() / 3600000L & 0xFFFFFF) << 32;
/*    */   }
/*    */   
/*    */   public long getId() {
/* 82 */     return 0L;
/*    */   }
/*    */   
/*    */   public void setId(long id) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\GUIDGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */