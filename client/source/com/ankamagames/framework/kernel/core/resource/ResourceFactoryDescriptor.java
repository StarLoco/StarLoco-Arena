/*    */ package com.ankamagames.framework.kernel.core.resource;
/*    */ 
/*    */ import com.ankamagames.framework.annotations.NotNull;
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
/*    */ public class ResourceFactoryDescriptor
/*    */ {
/*    */   private int m_typeId;
/*    */   private ResourceFactory m_resourceFactory;
/*    */   private ContextFactory m_contextFactory;
/*    */   
/*    */   public ResourceFactoryDescriptor(int id, ResourceFactory resourceFactory, ContextFactory contextFactory) {
/* 21 */     this.m_typeId = id;
/* 22 */     this.m_resourceFactory = resourceFactory;
/* 23 */     this.m_contextFactory = contextFactory;
/*    */   }
/*    */   
/*    */   public int getTypeId() {
/* 27 */     return this.m_typeId;
/*    */   }
/*    */   
/*    */   public void setTypeId(int typeId) {
/* 31 */     this.m_typeId = typeId;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ResourceFactory getResourceFactory() {
/* 36 */     return this.m_resourceFactory;
/*    */   }
/*    */   
/*    */   public void setResourceFactory(ResourceFactory resourceFactory) {
/* 40 */     this.m_resourceFactory = resourceFactory;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ContextFactory getContextFactory() {
/* 45 */     return this.m_contextFactory;
/*    */   }
/*    */   
/*    */   public void setContextFactory(ContextFactory contextFactory) {
/* 49 */     this.m_contextFactory = contextFactory;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\ResourceFactoryDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */