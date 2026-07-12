/*    */ package com.ankamagames.baseImpl.graphics.alea;
/*    */ 
/*    */ import gnu.trove.TIntArrayList;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGroup
/*    */ {
/*    */   private int m_id;
/* 17 */   private TIntObjectHashMap<TIntArrayList> m_layers = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   public WorldGroup(int id) {
/* 21 */     this.m_id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 26 */     return this.m_id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addVisibleLayer(int layerId, int visibleLayerId) {
/* 36 */     TIntArrayList visibleLayers = (TIntArrayList)this.m_layers.get(layerId);
/*    */     
/* 38 */     if (visibleLayers == null) {
/*    */       
/* 40 */       visibleLayers = new TIntArrayList();
/* 41 */       this.m_layers.put(layerId, visibleLayers);
/*    */     } 
/*    */     
/* 44 */     visibleLayers.add(visibleLayerId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TIntArrayList getMaskedLayers(int layerId) {
/* 52 */     return (TIntArrayList)this.m_layers.get(layerId);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\WorldGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */