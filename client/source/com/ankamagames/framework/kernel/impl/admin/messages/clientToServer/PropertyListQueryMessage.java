/*    */ package com.ankamagames.framework.kernel.impl.admin.messages.clientToServer;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.impl.admin.messages.SecureMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropertyListQueryMessage
/*    */   extends SecureMessage
/*    */ {
/*    */   public byte[] encode() {
/* 25 */     return crypt(new byte[1]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean decode(byte[] rawDatas) {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 43 */     return 10;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\impl\admin\messages\clientToServer\PropertyListQueryMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */