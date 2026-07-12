/*    */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ConstantDefinition<E>
/*    */ {
/*    */   private int m_id;
/*    */   private E m_object;
/*    */   
/*    */   protected ConstantDefinition() {}
/*    */   
/*    */   public ConstantDefinition(int id, E object, Constants<E> constants) {
/* 18 */     this.m_id = id;
/* 19 */     this.m_object = object;
/* 20 */     constants.addConstantDefinition(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 24 */     return this.m_id;
/*    */   }
/*    */   
/*    */   public E getObject() {
/* 28 */     return this.m_object;
/*    */   }
/*    */   
/*    */   public abstract String getAdminDescription();
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServe\\utils\ConstantDefinition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */