/*    */ package com.ankamagames.framework.kernel.core.net;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*    */ import com.ankamagames.framework.kernel.core.common.Poolable;
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
/*    */ public class ConnectionPoolFactory
/*    */   extends ObjectFactory<Connection>
/*    */ {
/*    */   public Connection makeObject() {
/* 19 */     return new Connection();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionPoolFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */