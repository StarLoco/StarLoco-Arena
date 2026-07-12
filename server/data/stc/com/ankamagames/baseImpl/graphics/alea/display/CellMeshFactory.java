/*    */ package com.ankamagames.baseImpl.graphics.alea.display;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*    */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2DManager;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CellMeshFactory
/*    */ {
/* 19 */   protected static Logger m_logger = Logger.getLogger(CellMeshFactory.class);
/*    */   
/* 21 */   private static final CellMeshFactory m_instance = new CellMeshFactory();
/*    */   
/*    */   private Mesh2D[] m_cellsMesh;
/*    */   
/* 25 */   private int meshCount = 0;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private CellMeshFactory()
/*    */   {
/* 32 */     this.m_cellsMesh = new Mesh2D['ஸ'];
/*    */     
/* 34 */     for (int i = 0; i < this.m_cellsMesh.length; i++) {
/* 35 */       this.m_cellsMesh[i] = Mesh2DManager.getInstance().getNewMesh();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static CellMeshFactory getInstance()
/*    */   {
/* 44 */     return m_instance;
/*    */   }
/*    */   
/*    */   public Mesh2D getMeshFromGraphicalElement(GraphicalElement graphicalElement, int state) {
/* 48 */     Mesh2D mesh = this.m_cellsMesh[(this.meshCount++)];
/*    */     
/* 50 */     if (!mesh.isInitialized()) {
/* 51 */       mesh.initialize();
/*    */     }
/* 53 */     Mesh2DManager.getInstance().tagResourceInUse(mesh);
/*    */     
/*    */ 
/* 56 */     float x = graphicalElement.getStateProperties(state).getOriginX();
/* 57 */     float y = graphicalElement.getStateProperties(state).getOriginY();
/* 58 */     mesh.setHotPoint(x, y);
/*    */     
/*    */ 
/* 61 */     mesh.setTexture(AleaTextureManager.getInstance().getTextureForCell(graphicalElement.getStateProperties(state).getGfxId()));
/*    */     
/* 63 */     if (mesh.getTexture() == null) {
/* 64 */       m_logger.error("Impossible de charger la texture " + graphicalElement.getStateProperties(state).getGfxId());
/*    */     }
/* 66 */     mesh.setFlip(graphicalElement.getStateProperties(state).isFlip());
/*    */     
/* 68 */     mesh.computeTextureCoordinate();
/*    */     
/* 70 */     return mesh;
/*    */   }
/*    */   
/*    */   public void rewind() {
/* 74 */     this.meshCount = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\CellMeshFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */