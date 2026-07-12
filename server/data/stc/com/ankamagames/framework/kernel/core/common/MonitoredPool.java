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
/* 22 */   private static final HashMap<String, MonitoredPool> m_monitoredPools = new HashMap();
/*    */   
/*    */   /* Error */
/*    */   public static MonitoredPool getPool(String pooledObjectName)
/*    */   {
/*    */     // Byte code:
/*    */     //   0: getstatic 95	com/ankamagames/framework/kernel/core/common/MonitoredPool:m_monitoredPoolsMutex	Ljava/lang/Object;
/*    */     //   3: dup
/*    */     //   4: astore_1
/*    */     //   5: monitorenter
/*    */     //   6: getstatic 96	com/ankamagames/framework/kernel/core/common/MonitoredPool:m_monitoredPools	Ljava/util/HashMap;
/*    */     //   9: aload_0
/*    */     //   10: invokevirtual 111	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   13: checkcast 43	com/ankamagames/framework/kernel/core/common/MonitoredPool
/*    */     //   16: aload_1
/*    */     //   17: monitorexit
/*    */     //   18: areturn
/*    */     //   19: aload_1
/*    */     //   20: monitorexit
/*    */     //   21: athrow
/*    */     // Line number table:
/*    */     //   Java source line #25	-> byte code offset #0
/*    */     //   Java source line #26	-> byte code offset #6
/*    */     //   Java source line #25	-> byte code offset #19
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	22	0	pooledObjectName	String
/*    */     //   4	16	1	Ljava/lang/Object;	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   6	18	19	finally
/*    */     //   19	21	19	finally
/*    */   }
/*    */   
/*    */   /* Error */
/*    */   public static int getPoolsCount()
/*    */   {
/*    */     // Byte code:
/*    */     //   0: getstatic 95	com/ankamagames/framework/kernel/core/common/MonitoredPool:m_monitoredPoolsMutex	Ljava/lang/Object;
/*    */     //   3: dup
/*    */     //   4: astore_0
/*    */     //   5: monitorenter
/*    */     //   6: getstatic 96	com/ankamagames/framework/kernel/core/common/MonitoredPool:m_monitoredPools	Ljava/util/HashMap;
/*    */     //   9: invokevirtual 107	java/util/HashMap:size	()I
/*    */     //   12: aload_0
/*    */     //   13: monitorexit
/*    */     //   14: ireturn
/*    */     //   15: aload_0
/*    */     //   16: monitorexit
/*    */     //   17: athrow
/*    */     // Line number table:
/*    */     //   Java source line #31	-> byte code offset #0
/*    */     //   Java source line #32	-> byte code offset #6
/*    */     //   Java source line #31	-> byte code offset #15
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   4	12	0	Ljava/lang/Object;	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   6	14	15	finally
/*    */     //   15	17	15	finally
/*    */   }
/*    */   
/*    */   /* Error */
/*    */   public static Iterable<String> getPools()
/*    */   {
/*    */     // Byte code:
/*    */     //   0: getstatic 95	com/ankamagames/framework/kernel/core/common/MonitoredPool:m_monitoredPoolsMutex	Ljava/lang/Object;
/*    */     //   3: dup
/*    */     //   4: astore_0
/*    */     //   5: monitorenter
/*    */     //   6: getstatic 96	com/ankamagames/framework/kernel/core/common/MonitoredPool:m_monitoredPools	Ljava/util/HashMap;
/*    */     //   9: invokevirtual 110	java/util/HashMap:keySet	()Ljava/util/Set;
/*    */     //   12: checkcast 46	java/lang/Iterable
/*    */     //   15: aload_0
/*    */     //   16: monitorexit
/*    */     //   17: areturn
/*    */     //   18: aload_0
/*    */     //   19: monitorexit
/*    */     //   20: athrow
/*    */     // Line number table:
/*    */     //   Java source line #37	-> byte code offset #0
/*    */     //   Java source line #38	-> byte code offset #6
/*    */     //   Java source line #37	-> byte code offset #18
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   4	15	0	Ljava/lang/Object;	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   6	17	18	finally
/*    */     //   18	20	18	finally
/*    */   }
/*    */   
/*    */   public static void registerPool(String pooledObjectName, MonitoredPool pool)
/*    */   {
/* 43 */     synchronized (m_monitoredPoolsMutex) {
/* 44 */       int i = 0;
/* 45 */       String poolName = pooledObjectName;
/* 46 */       while (m_monitoredPools.containsKey(poolName))
/* 47 */         poolName = pooledObjectName + " #" + i++;
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
/*    */   public MonitoredPool(PoolableObjectFactory factory)
/*    */   {
/* 62 */     super(factory);
/*    */     
/*    */     try
/*    */     {
/* 66 */       String pooledObjectName = factory.makeObject().getClass().getName();
/* 67 */       registerPool(pooledObjectName, this);
/*    */     } catch (Exception e) {
/* 69 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\MonitoredPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */