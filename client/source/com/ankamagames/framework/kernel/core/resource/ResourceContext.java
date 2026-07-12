/*    */ package com.ankamagames.framework.kernel.core.resource;
/*    */ 
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
/*    */ public abstract class ResourceContext
/*    */   implements Poolable
/*    */ {
/*    */   private ManageableResource m_resource;
/*    */   private int m_lastUseFrame;
/*    */   private boolean m_resourceUnloaded;
/*    */   private boolean m_destroyResource;
/* 20 */   private final Object m_mutex = new Object();
/*    */   private int m_typeId;
/*    */   
/*    */   protected ResourceContext() {
/* 24 */     this.m_resourceUnloaded = false;
/* 25 */     this.m_lastUseFrame = 0;
/* 26 */     this.m_resource = null;
/*    */   }
/*    */   
/*    */   public ManageableResource getResource() {
/* 30 */     return this.m_resource;
/*    */   }
/*    */   
/*    */   public void setResource(ManageableResource resource) {
/* 34 */     this.m_resource = resource;
/*    */   }
/*    */   
/*    */   public int getLastUseFrame() {
/* 38 */     return this.m_lastUseFrame;
/*    */   }
/*    */   
/*    */   public void setLastUseFrame(int lastUseFrame) {
/* 42 */     this.m_lastUseFrame = lastUseFrame;
/*    */   }
/*    */   
/*    */   public boolean isResourceUnloaded() {
/* 46 */     return this.m_resourceUnloaded;
/*    */   }
/*    */   
/*    */   public void setResourceUnloaded(boolean resourceUnloaded) {
/* 50 */     this.m_resourceUnloaded = resourceUnloaded;
/*    */   }
/*    */   
/*    */   public Object getMutex() {
/* 54 */     return this.m_mutex;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTypeId() {
/* 59 */     return this.m_typeId;
/*    */   }
/*    */   
/*    */   public void setTypeId(int typeId) {
/* 63 */     this.m_typeId = typeId;
/*    */   }
/*    */   
/*    */   public boolean isReleasable() {
/* 67 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isDestroyResource() {
/* 71 */     return this.m_destroyResource;
/*    */   }
/*    */   
/*    */   public void setDestroyResource(boolean destroyResource) {
/* 75 */     this.m_destroyResource = destroyResource;
/*    */   }
/*    */   
/*    */   private void reset() {
/* 79 */     this.m_resource = null;
/* 80 */     this.m_lastUseFrame = 0;
/* 81 */     this.m_resourceUnloaded = true;
/* 82 */     this.m_destroyResource = false;
/* 83 */     this.m_typeId = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckOut() {
/* 90 */     reset();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCheckIn() {
/* 97 */     reset();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\ResourceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */