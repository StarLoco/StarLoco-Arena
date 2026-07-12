/*    */ package com.ankamagames.framework.graphics.opengl.base.impl;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.core.resource.ContextFactory;
/*    */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*    */ import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
/*    */ import com.ankamagames.framework.kernel.core.resource.SingleResourceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Mesh2DManager
/*    */   extends SingleResourceManager
/*    */ {
/* 19 */   private static final Mesh2DManager m_instance = new Mesh2DManager();
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
/*    */   private Mesh2DManager()
/*    */   {
/* 34 */     super(new ResourceFactory()new ContextFactory
/*    */     {
/*    */       public Mesh2D makeObject()
/*    */       {
/* 26 */         return new Mesh2D();
/*    */       }
/*    */       
/* 29 */     }, new ContextFactory() {
/*    */       public Mesh2D.Mesh2DResourceContext makeObject() {
/* 31 */         return new Mesh2D.Mesh2DResourceContext();
/*    */       }
/*    */       
/* 34 */     }, true);
/*    */   }
/*    */   
/*    */   public static Mesh2DManager getInstance()
/*    */   {
/* 39 */     return m_instance;
/*    */   }
/*    */   
/*    */   public Mesh2D getNewMesh() {
/* 43 */     ResourceContext context = getNewResource();
/* 44 */     if (context != null) {
/* 45 */       return (Mesh2D)context.getResource();
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\impl\Mesh2DManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */