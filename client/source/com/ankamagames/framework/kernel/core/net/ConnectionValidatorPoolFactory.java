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
/*    */ 
/*    */ public class ConnectionValidatorPoolFactory
/*    */   extends ObjectFactory<ConnectionValidator>
/*    */ {
/*    */   public ConnectionValidator makeObject() {
/* 20 */     return new ConnectionValidator();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\net\ConnectionValidatorPoolFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */