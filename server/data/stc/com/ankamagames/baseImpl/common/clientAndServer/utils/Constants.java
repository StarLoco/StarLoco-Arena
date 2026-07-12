/*    */ package com.ankamagames.baseImpl.common.clientAndServer.utils;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.common.collections.iterators.TroveIntHashMapValueIterator;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Constants<O>
/*    */   implements Iterable<ConstantDefinition<O>>
/*    */ {
/* 15 */   private final TIntObjectHashMap<ConstantDefinition<O>> m_list = new TIntObjectHashMap();
/*    */   
/*    */   public void addConstantDefinition(ConstantDefinition<O> def) {
/* 18 */     this.m_list.put(def.getId(), def);
/*    */   }
/*    */   
/*    */   public ConstantDefinition<O> getConstantDefinition(int id) {
/* 22 */     ConstantDefinition<O> cd = (ConstantDefinition)this.m_list.get(id);
/* 23 */     if (cd != null)
/* 24 */       return (ConstantDefinition)this.m_list.get(id);
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public O getObjectFromId(int id)
/*    */   {
/* 30 */     ConstantDefinition<O> cd = (ConstantDefinition)this.m_list.get(id);
/* 31 */     if (cd != null)
/* 32 */       return (O)((ConstantDefinition)this.m_list.get(id)).getObject();
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   public Iterator<ConstantDefinition<O>> iterator() {
/* 37 */     return new TroveIntHashMapValueIterator(this.m_list);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\utils\Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */