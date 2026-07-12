/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryCapacityReachedException
/*    */   extends Exception
/*    */ {
/*    */   public InventoryCapacityReachedException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InventoryCapacityReachedException(String message)
/*    */   {
/* 21 */     super(message);
/*    */   }
/*    */   
/*    */   public InventoryCapacityReachedException(String message, Throwable cause) {
/* 25 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public InventoryCapacityReachedException(Throwable cause) {
/* 29 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\exception\InventoryCapacityReachedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */