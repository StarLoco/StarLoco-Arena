/*     */ package com.ankamagames.baseImpl.graphicalClient.ui.protocol.message;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractUIMessage
/*     */   extends Message
/*     */ {
/*     */   private int m_id;
/*     */   private byte m_byteValue;
/*     */   private short m_shortValue;
/*     */   private int m_intValue;
/*     */   private long m_longValue;
/*     */   private String m_stringValue;
/*     */   
/*     */   public AbstractUIMessage(MessageHandler handler) {
/*  36 */     setHandler(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/*  56 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  66 */     this.m_id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  76 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByteValue() {
/*  99 */     return this.m_byteValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteValue(byte byteValue) {
/* 106 */     this.m_byteValue = byteValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntValue() {
/* 113 */     return this.m_intValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntValue(int intValue) {
/* 120 */     this.m_intValue = intValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLongValue() {
/* 127 */     return this.m_longValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLongValue(long longValue) {
/* 134 */     this.m_longValue = longValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getShortValue() {
/* 141 */     return this.m_shortValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShortValue(short shortValue) {
/* 148 */     this.m_shortValue = shortValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringValue() {
/* 155 */     return this.m_stringValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStringValue(String stringValue) {
/* 162 */     this.m_stringValue = stringValue;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClien\\ui\protocol\message\AbstractUIMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */