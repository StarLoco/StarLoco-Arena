/*    */ package com.ankamagames.framework.kernel.core.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
/*    */ import org.apache.commons.pool.impl.SoftReferenceObjectPool;
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
/*    */ public class MonitoredPool
/*    */   extends SoftReferenceObjectPool
/*    */ {
/* 21 */   private static final Object m_monitoredPoolsMutex = new Object();
/* 22 */   private static final HashMap<String, MonitoredPool> m_monitoredPools = new HashMap<String, MonitoredPool>();
/*    */   
/*    */   public static MonitoredPool getPool(String pooledObjectName) {
/* 25 */     synchronized (m_monitoredPoolsMutex) {
/* 26 */       return m_monitoredPools.get(pooledObjectName);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getPoolsCount() {
/* 31 */     synchronized (m_monitoredPoolsMutex) {
/* 32 */       return m_monitoredPools.size();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Iterable<String> getPools() {
/* 37 */     synchronized (m_monitoredPoolsMutex) {
/* 38 */       return m_monitoredPools.keySet();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void registerPool(String pooledObjectName, MonitoredPool pool) {
/* 43 */     synchronized (m_monitoredPoolsMutex) {
/* 44 */       int i = 0;
/* 45 */       String poolName = pooledObjectName;
/* 46 */       while (m_monitoredPools.containsKey(poolName))
/* 47 */         poolName = String.valueOf(pooledObjectName) + " #" + i++; 
/* 48 */       m_monitoredPools.put(poolName, pool);
/*    */     } 
/*    */   }
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
/*    */   public MonitoredPool(PoolableObjectFactory factory) {
/* 62 */     super(factory);
/*    */ 
/*    */     
/*    */     try {
/* 66 */       String pooledObjectName = factory.makeObject().getClass().getName();
/* 67 */       registerPool(pooledObjectName, this);
/* 68 */     } catch (Exception e) {
/* 69 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\MonitoredPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */