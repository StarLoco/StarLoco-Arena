/*    */ package com.ankamagames.baseImpl.graphics.alea;
/*    */ 
/*    */ import com.ankamagames.alea.AleaWorldCell;
/*    */ import com.ankamagames.alea.AleaWorldManager;
/*    */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindCell;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*    */ import com.ankamagames.framework.kernel.core.resource.ContextFactory;
/*    */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*    */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*    */ import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WorldManager
/*    */   extends AleaWorldManager
/*    */   implements CellInformationProvider
/*    */ {
/* 24 */   private static final WorldManager m_instance = new WorldManager();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private WorldManager() {
/* 33 */     super(new ResourceFactory<WorldMap>() { public WorldMap makeObject() { return new WorldMap(); } }, new ContextFactory<WorldMapContext>() { public WorldMapContext makeObject() { return new WorldMapContext(); } }, true, new WorldMapDocumentAccessor());
/*    */ 
/*    */     
/* 36 */     setMaxResourceAge(60);
/* 37 */     setMapSize(18);
/*    */   }
/*    */   
/*    */   public static WorldManager getInstance() {
/* 41 */     return m_instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getCellValidity(int x, int y, short z) {
/* 46 */     return (getWorldCell(x, y) != null);
/*    */   }
/*    */   
/*    */   public boolean getLineOfSightValidity(int x, int y, short z, Direction8 direction) {
/* 50 */     if (!getCellValidity(x, y, z))
/* 51 */       return false; 
/* 52 */     AleaWorldCell cell = getWorldCell(x, y);
/* 53 */     return cell.isLineOfSightValid(z, direction);
/*    */   }
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
/*    */   public boolean getLineOfSightEndValidity(int x, int y, short z) {
/* 67 */     if (!getCellValidity(x, y, z)) {
/* 68 */       return false;
/*    */     }
/* 70 */     AleaWorldCell cell = getWorldCell(x, y);
/*    */     
/* 72 */     return cell.isLineOfSightEndValid(z);
/*    */   }
/*    */   
/*    */   public PathFindCell getPathFindCell(int x, int y, short z) {
/* 76 */     if (!getCellValidity(x, y, z))
/* 77 */       return null; 
/* 78 */     return (PathFindCell)getWorldCell(x, y);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */