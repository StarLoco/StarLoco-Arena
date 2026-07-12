/*    */ package com.ankamagames.framework.kernel.core.common;
/*    */ 
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
/*    */ public abstract class ObjectFactory<E extends Poolable>
/*    */   implements PoolableObjectFactory
/*    */ {
/*    */   public void activateObject(Object obj) {
/* 21 */     ((Poolable)obj).onCheckOut();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void passivateObject(Object obj) {
/*    */     try {
/* 28 */       ((Poolable)obj).onCheckIn();
/* 29 */     } catch (Exception e) {
/* 30 */       e.printStackTrace();
/* 31 */       throw new RuntimeException(e.toString());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroyObject(Object obj) {}
/*    */ 
/*    */   
/*    */   public boolean validateObject(Object obj) {
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   public abstract E makeObject();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */