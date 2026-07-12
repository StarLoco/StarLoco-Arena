/*    */ package com.ankamagames.framework.kernel.core.common.message.scheduler;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
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
/*    */ public class ClockMessage
/*    */   extends Message
/*    */ {
/*    */   public static final int ID = Integer.MIN_VALUE;
/*    */   private long m_clockId;
/*    */   private int m_subId;
/*    */   private long m_timeStamp;
/*    */   
/*    */   public ClockMessage()
/*    */   {
/* 29 */     this.m_subId = 0;
/*    */   }
/*    */   
/*    */   public long getClockId() {
/* 33 */     return this.m_clockId;
/*    */   }
/*    */   
/*    */   public void setClockId(long clockId) {
/* 37 */     this.m_clockId = clockId;
/*    */   }
/*    */   
/*    */   public void setSubId(int id) {
/* 41 */     this.m_subId = id;
/*    */   }
/*    */   
/*    */   public int getSubId() {
/* 45 */     return this.m_subId;
/*    */   }
/*    */   
/*    */   public byte[] encode() {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 53 */     return true;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 57 */     return Integer.MIN_VALUE;
/*    */   }
/*    */   
/*    */ 
/*    */   public void setId(int id) {}
/*    */   
/*    */ 
/*    */   public void onCheckOut() {}
/*    */   
/*    */   public void onCheckIn() {}
/*    */   
/*    */   public long getTimeStamp()
/*    */   {
/* 70 */     return this.m_timeStamp;
/*    */   }
/*    */   
/*    */   public void setTimeStamp(long timeStamp) {
/* 74 */     this.m_timeStamp = timeStamp;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 78 */     return getClass().getName() + '@' + Integer.toHexString(hashCode()) + " listener : " + getHandler();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\scheduler\ClockMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */