/*     */ package com.ankamagames.framework.kernel.core.sql;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import gnu.trove.TLinkable;
/*     */ import java.sql.SQLException;
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
/*     */ public abstract class SqlRequest
/*     */   implements TLinkable, Poolable
/*     */ {
/*  24 */   protected static final Logger m_logger = Logger.getLogger(SqlRequest.class);
/*     */   
/*     */   protected TLinkable m_next;
/*     */   
/*     */   protected TLinkable m_previous;
/*     */   
/*     */   protected ObjectPool m_pool;
/*     */   protected SqlRequestRecipientValidator m_validator;
/*     */   
/*     */   public SqlRequest()
/*     */   {
/*  35 */     this.m_next = (this.m_previous = null);
/*  36 */     this.m_pool = null;
/*  37 */     this.m_validator = null;
/*     */   }
/*     */   
/*     */   public TLinkable getNext() {
/*  41 */     return this.m_next;
/*     */   }
/*     */   
/*     */   public void setNext(TLinkable linkable) {
/*  45 */     this.m_next = linkable;
/*     */   }
/*     */   
/*     */   public TLinkable getPrevious() {
/*  49 */     return this.m_previous;
/*     */   }
/*     */   
/*     */   public void setPrevious(TLinkable linkable) {
/*  53 */     this.m_previous = linkable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPool(ObjectPool pool)
/*     */   {
/*  63 */     this.m_pool = pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRecipient(SqlRequestRecipient recipient)
/*     */   {
/*  71 */     this.m_validator = new SqlRequestRecipientValidator();
/*  72 */     this.m_validator.setup(recipient);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasRecipient()
/*     */   {
/*  80 */     return this.m_validator != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRecipientValid()
/*     */   {
/*  89 */     if (this.m_validator != null) {
/*  90 */       return this.m_validator.isItemValid();
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SqlRequestRecipient getRecipient()
/*     */   {
/* 100 */     if (this.m_validator == null)
/* 101 */       return null;
/* 102 */     return (SqlRequestRecipient)this.m_validator.getItem();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */     throws Exception
/*     */   {
/* 113 */     if (this.m_pool != null) {
/* 114 */       this.m_pool.returnObject(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract Message execute(SqlRequestChannel paramSqlRequestChannel)
/*     */     throws SQLException;
/*     */   
/*     */   public abstract int getPreferedChannel();
/*     */   
/*     */   public abstract int getId();
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\sql\SqlRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */