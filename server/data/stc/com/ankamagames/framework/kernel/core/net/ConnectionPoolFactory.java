/*    */ package com.ankamagames.framework.kernel.core.net;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
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
/*    */   public Connection makeObject()
/*    */   {
/* 19 */     return new Connection();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionPoolFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */