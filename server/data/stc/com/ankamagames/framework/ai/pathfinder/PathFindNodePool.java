/*    */ package com.ankamagames.framework.ai.pathfinder;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import org.apache.commons.pool.ObjectPool;
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
/*    */ class PathFindNodePool
/*    */ {
/* 19 */   private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory() {
/* 20 */     public PathFindNode makeObject() { return new PathFindNode(); }
/* 19 */   });
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PathFindNode checkOut(PathFindCell cell, short z)
/*    */   {
/* 30 */     PathFindNode node = null;
/*    */     try {
/* 32 */       node = (PathFindNode)m_staticPool.borrowObject();
/* 33 */       node.m_cell = cell;
/* 34 */       node.m_z = z;
/*    */     } catch (Exception e) {
/* 36 */       e.printStackTrace();
/*    */     }
/* 38 */     return node;
/*    */   }
/*    */   
/*    */   public void release(PathFindNode node) {
/*    */     try {
/* 43 */       m_staticPool.returnObject(node);
/*    */     } catch (Exception e) {
/* 45 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\pathfinder\PathFindNodePool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */