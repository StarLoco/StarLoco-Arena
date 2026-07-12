/*    */ package com.ankamagames.framework.kernel.core.resource;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*    */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*    */ public abstract class ResourceFactory<E extends ManageableResource>
/*    */   implements PoolableObjectFactory
/*    */ {
/*    */   public void activateObject(Object obj) {
/* 23 */     ((Poolable)obj).onCheckOut();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void passivateObject(Object obj) {
/*    */     try {
/* 30 */       ((Poolable)obj).onCheckIn();
/* 31 */     } catch (Exception e) {
/* 32 */       e.printStackTrace();
/* 33 */       throw new RuntimeException("passivateObject exception");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroyObject(Object obj) {}
/*    */ 
/*    */   
/*    */   public boolean validateObject(Object obj) {
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public abstract E makeObject();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\ResourceFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */