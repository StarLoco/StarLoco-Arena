/*     */ package com.ankamagames.framework.kernel.core.common.message;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import gnu.trove.TLinkable;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class Message
/*     */   implements TLinkable, Poolable
/*     */ {
/*  25 */   protected static final Logger m_logger = Logger.getLogger(Message.class);
/*     */   
/*     */   private static final boolean DEBUG_TIMES = false;
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   
/*     */   protected TLinkable m_previous;
/*     */   
/*     */   protected TLinkable m_next;
/*     */   
/*     */   protected long m_pushTime;
/*     */   
/*     */   protected long m_decodeTime;
/*     */   
/*     */   protected long m_executionStartTime;
/*     */   
/*     */   protected MessageHandlerValidator m_handlerValidator;
/*     */   
/*     */   public TLinkable getNext() {
/*  44 */     return this.m_next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNext(TLinkable linkable) {
/*  51 */     this.m_next = linkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLinkable getPrevious() {
/*  58 */     return this.m_previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrevious(TLinkable linkable) {
/*  65 */     this.m_previous = linkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageHandler getHandler() {
/*  75 */     if (this.m_handlerValidator != null) {
/*  76 */       return (MessageHandler)this.m_handlerValidator.getItem();
/*     */     }
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHandler(MessageHandler handler) {
/*  87 */     this.m_handlerValidator = new MessageHandlerValidator();
/*  88 */     this.m_handlerValidator.setup(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHandlerValid() {
/*  98 */     if (this.m_handlerValidator != null) {
/*  99 */       return this.m_handlerValidator.isItemValid();
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPool(ObjectPool pool) {
/* 112 */     this.m_pool = pool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 121 */     if (this.m_pool != null) {
/*     */       try {
/* 123 */         this.m_pool.returnObject(this);
/* 124 */       } catch (Exception e) {
/* 125 */         e.printStackTrace();
/*     */       } 
/* 127 */       this.m_pool = null;
/*     */     } else {
/* 129 */       onCheckIn();
/*     */     } 
/* 131 */     this.m_handlerValidator = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() {
/* 140 */     if (this.m_handlerValidator != null) {
/* 141 */       MessageHandler handler = (MessageHandler)this.m_handlerValidator.getItem();
/* 142 */       if (handler != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         handler.onMessage(this);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 155 */         m_logger.warn("Le message de type " + getClass().getSimpleName() + " n'a pas de destinataire.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] encode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean decode(byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final byte[] readString(ByteBuffer buffer) {
/* 199 */     if (buffer.remaining() >= 1) {
/* 200 */       int strLen = buffer.get() & 0xFF;
/* 201 */       if (buffer.remaining() < strLen)
/* 202 */         return null; 
/* 203 */       byte[] string = new byte[strLen];
/* 204 */       buffer.get(string);
/* 205 */       return string;
/*     */     } 
/* 207 */     return null;
/*     */   }
/*     */   
/*     */   public boolean checkMessageSize(int size, int expectedSize, boolean bExactSize) {
/* 211 */     if (bExactSize) {
/* 212 */       if (size != expectedSize) {
/* 213 */         m_logger.error("****************************** Message de longueur incorrecte : reçu=" + size + " octet(s), attendu=" + expectedSize + " octet(s), type : " + getClass().getName());
/* 214 */         return false;
/*     */       }
/*     */     
/* 217 */     } else if (size < expectedSize) {
/* 218 */       m_logger.error("****************************** Message de longueur incorrecte : reçu=" + size + " octet(s), attendu >= " + expectedSize + " octet(s), type : " + getClass().getName());
/* 219 */       return false;
/*     */     } 
/*     */     
/* 222 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\message\Message.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */