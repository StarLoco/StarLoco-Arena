/*    */ package com.ankamagames.framework.kernel.core.resource;
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
/*    */ public abstract class SingleResourceManager
/*    */   extends BaseResourceManager
/*    */ {
/*    */   protected SingleResourceManager(ResourceFactory resFactory, ContextFactory ctxFactory, boolean bUseClock)
/*    */   {
/* 23 */     super(new ResourceFactoryDescriptor[] {new ResourceFactoryDescriptor(0, resFactory, ctxFactory) }, bUseClock);
/*    */   }
/*    */   
/*    */ 
/*    */   public ResourceContext getNewResource()
/*    */   {
/* 29 */     return getNewResource(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\SingleResourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */