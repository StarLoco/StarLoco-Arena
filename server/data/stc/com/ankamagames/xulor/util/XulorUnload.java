/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XulorUnload
/*    */   implements XulorLoadUnload
/*    */ {
/*    */   private String m_id;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 17 */   private boolean m_all = false;
/*    */   private long m_startTime;
/*    */   private int m_duration;
/*    */   
/*    */   public XulorUnload(String id, int duration, long startTime) {
/* 22 */     this.m_id = id;
/* 23 */     this.m_duration = duration;
/* 24 */     this.m_startTime = startTime;
/*    */   }
/*    */   
/*    */   public XulorUnload(String id) {
/* 28 */     this(id, Integer.MAX_VALUE, 0L);
/*    */   }
/*    */   
/*    */   public XulorUnload(boolean all) {
/* 32 */     this.m_all = all;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getId()
/*    */   {
/* 39 */     return this.m_id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getDuration()
/*    */   {
/* 46 */     return this.m_duration;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public long getStartTime()
/*    */   {
/* 53 */     return this.m_startTime;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isAll()
/*    */   {
/* 60 */     return this.m_all;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\XulorUnload.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */