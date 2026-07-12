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
/*    */ public abstract class ContextFactory<E extends ResourceContext>
/*    */   implements PoolableObjectFactory
/*    */ {
/*    */   public void activateObject(Object obj) {
/* 23 */     ((Poolable)obj).onCheckOut();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void passivateObject(Object obj) {
/* 29 */     ((Poolable)obj).onCheckIn();
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroyObject(Object obj) {}
/*    */ 
/*    */   
/*    */   public boolean validateObject(Object obj) {
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   public abstract E makeObject();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\ContextFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */