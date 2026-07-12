/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.chat;
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
/*     */ public class ChatMessage
/*     */   implements Comparable<ChatMessage>
/*     */ {
/*     */   private int m_pipeDestination;
/*     */   private long m_sourceId;
/*     */   private String m_sourceName;
/*     */   private String m_message;
/*     */   private final long m_time;
/*     */   
/*     */   public ChatMessage(String sourceName, String message) {
/*  28 */     this.m_sourceName = sourceName;
/*  29 */     this.m_message = message;
/*  30 */     this.m_time = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatMessage(String message) {
/*  39 */     this(null, message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatMessage(String sourceName, long sourceId, String message) {
/*  49 */     this(sourceName, message);
/*  50 */     this.m_sourceId = sourceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPipeDestination() {
/*  57 */     return this.m_pipeDestination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPipeDestination(int pipeDestination) {
/*  64 */     this.m_pipeDestination = pipeDestination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSourceId() {
/*  71 */     return this.m_sourceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceName() {
/*  78 */     return this.m_sourceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceName(String sourceName) {
/*  85 */     this.m_sourceName = sourceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  92 */     return this.m_message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/*  99 */     this.m_message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTime() {
/* 106 */     return this.m_time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(ChatMessage chatMessage) {
/* 115 */     if (chatMessage.getTime() == getTime()) {
/* 116 */       return 0;
/*     */     }
/* 118 */     return (chatMessage.getTime() > getTime()) ? -1 : 1;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\chat\ChatMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */