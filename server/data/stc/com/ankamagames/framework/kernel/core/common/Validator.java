/*    */ package com.ankamagames.framework.kernel.core.common;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Validator
/*    */ {
/*    */   protected Validable m_item;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected long m_itemId;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Validator()
/*    */   {
/* 24 */     reset();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setup(Validable item)
/*    */   {
/* 32 */     this.m_item = item;
/* 33 */     if (item != null) {
/* 34 */       this.m_itemId = item.getId();
/*    */     } else {
/* 36 */       this.m_itemId = 0L;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void reset()
/*    */   {
/* 43 */     this.m_item = null;
/* 44 */     this.m_itemId = 0L;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isItemValid()
/*    */   {
/* 54 */     if (this.m_item == null)
/* 55 */       return false;
/* 56 */     if (this.m_itemId == 0L)
/* 57 */       return false;
/* 58 */     return this.m_item.getId() == this.m_itemId;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Validable getItem()
/*    */   {
/* 66 */     return this.m_item;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getItemId()
/*    */   {
/* 74 */     return this.m_itemId;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\common\Validator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */