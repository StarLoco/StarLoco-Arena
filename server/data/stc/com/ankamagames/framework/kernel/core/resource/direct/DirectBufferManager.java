/*     */ package com.ankamagames.framework.kernel.core.resource.direct;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import org.apache.commons.pool.KeyedObjectPool;
/*     */ import org.apache.commons.pool.impl.StackKeyedObjectPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectBufferManager
/*     */   implements MessageHandler
/*     */ {
/*  23 */   private static final DirectBufferManager m_instance = new DirectBufferManager();
/*  24 */   private final Object m_mutex = new Object();
/*     */   private KeyedObjectPool m_pool;
/*     */   
/*     */   private DirectBufferManager()
/*     */   {
/*  29 */     this.m_pool = new StackKeyedObjectPool(new DirectBufferFactory());
/*  30 */     MessageScheduler.getInstance().addClock(this, 10000L, 123085382);
/*     */   }
/*     */   
/*     */   public static DirectBufferManager getInstance() {
/*  34 */     return m_instance;
/*     */   }
/*     */   
/*     */   public ByteBuffer getByteBuffer(int size) {
/*  38 */     synchronized (this.m_mutex) {
/*  39 */       int key = 0x1000000 | size;
/*     */       try {
/*  41 */         return (ByteBuffer)this.m_pool.borrowObject(Integer.valueOf(key));
/*     */       } catch (Exception e) {
/*  43 */         e.printStackTrace();
/*  44 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ShortBuffer getShortBuffer(int size) {
/*  50 */     synchronized (this.m_mutex) {
/*  51 */       int key = 0x2000000 | size;
/*     */       try {
/*  53 */         return (ShortBuffer)this.m_pool.borrowObject(Integer.valueOf(key));
/*     */       } catch (Exception e) {
/*  55 */         e.printStackTrace();
/*  56 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IntBuffer getIntBuffer(int size) {
/*  62 */     synchronized (this.m_mutex) {
/*  63 */       int key = 0x3000000 | size;
/*     */       try {
/*  65 */         return (IntBuffer)this.m_pool.borrowObject(Integer.valueOf(key));
/*     */       } catch (Exception e) {
/*  67 */         e.printStackTrace();
/*  68 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public FloatBuffer getFloatBuffer(int size) {
/*  74 */     synchronized (this.m_mutex) {
/*  75 */       int key = 0x4000000 | size;
/*     */       try {
/*  77 */         return (FloatBuffer)this.m_pool.borrowObject(Integer.valueOf(key));
/*     */       } catch (Exception e) {
/*  79 */         e.printStackTrace();
/*  80 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public DoubleBuffer getDoubleBuffer(int size) {
/*  86 */     synchronized (this.m_mutex) {
/*  87 */       int key = 0x5000000 | size;
/*     */       try {
/*  89 */         return (DoubleBuffer)this.m_pool.borrowObject(Integer.valueOf(key));
/*     */       } catch (Exception e) {
/*  91 */         e.printStackTrace();
/*  92 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseBuffer(ByteBuffer buffer) {
/*  98 */     synchronized (this.m_mutex) {
/*  99 */       int key = 0x1000000 | buffer.limit();
/*     */       try {
/* 101 */         this.m_pool.returnObject(Integer.valueOf(key), buffer);
/*     */       } catch (Exception e) {
/* 103 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseBuffer(ShortBuffer buffer) {
/* 109 */     synchronized (this.m_mutex) {
/* 110 */       int key = 0x2000000 | buffer.limit();
/*     */       try {
/* 112 */         this.m_pool.returnObject(Integer.valueOf(key), buffer);
/*     */       } catch (Exception e) {
/* 114 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseBuffer(IntBuffer buffer) {
/* 120 */     synchronized (this.m_mutex) {
/* 121 */       int key = 0x3000000 | buffer.limit();
/*     */       try {
/* 123 */         this.m_pool.returnObject(Integer.valueOf(key), buffer);
/*     */       } catch (Exception e) {
/* 125 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseBuffer(FloatBuffer buffer) {
/* 131 */     synchronized (this.m_mutex) {
/* 132 */       int key = 0x4000000 | buffer.limit();
/*     */       try {
/* 134 */         this.m_pool.returnObject(Integer.valueOf(key), buffer);
/*     */       } catch (Exception e) {
/* 136 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseBuffer(DoubleBuffer buffer) {
/* 142 */     synchronized (this.m_mutex) {
/* 143 */       int key = 0x5000000 | buffer.limit();
/*     */       try {
/* 145 */         this.m_pool.returnObject(Integer.valueOf(key), buffer);
/*     */       } catch (Exception e) {
/* 147 */         e.printStackTrace();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getStatistics()
/*     */   {
/* 168 */     synchronized (this.m_mutex) {
/* 169 */       String stats = getClass().getName() + " statistics : \n";
/* 170 */       int totalBytes = 0;
/*     */       
/* 172 */       stats = stats + "\tout=" + this.m_pool.getNumActive() + ", in=" + this.m_pool.getNumIdle();
/*     */       
/*     */ 
/* 175 */       return stats;
/*     */     }
/*     */   }
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
/*     */   public boolean onMessage(Message message)
/*     */   {
/* 191 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 200 */     return 1L;
/*     */   }
/*     */   
/*     */   public void setId(long id) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\direct\DirectBufferManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */