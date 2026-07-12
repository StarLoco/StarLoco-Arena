/*    */ package com.ankamagames.graphics.isometric.lines;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*    */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*    */ import com.ankamagames.graphics.isometric.highlight.HighLightedElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinesManager
/*    */   implements RenderProcessHandler
/*    */ {
/* 20 */   private List<LinesLayer> m_layers = new ArrayList();
/*    */   
/*    */ 
/* 23 */   public static LinesManager getInstance() { return m_instance; }
/* 24 */   private static LinesManager m_instance = new LinesManager();
/*    */   
/*    */ 
/*    */ 
/*    */   public void clear()
/*    */   {
/* 30 */     this.m_layers.clear();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public LinesLayer createLineLayer()
/*    */   {
/* 38 */     LinesLayer linesLayer = new LinesLayer();
/* 39 */     this.m_layers.add(linesLayer);
/* 40 */     return linesLayer;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addLineLayer(LinesLayer layer)
/*    */   {
/* 48 */     this.m_layers.add(layer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeLineLayer(LinesLayer layer)
/*    */   {
/* 56 */     this.m_layers.remove(layer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void prepareElementBeforeRendering(IsoWorldScene scene, HighLightedElement displayedElement)
/*    */   {
/* 68 */     for (LinesLayer lines : this.m_layers) {
/* 69 */       lines.prepareElementBeforeRendering(scene, displayedElement);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void prepareBeforeRendering(IsoWorldScene isoWorldScene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {}
/*    */   
/*    */   public void process(IsoWorldScene isoWorldScene, long realTime, int frameCount)
/*    */   {
/* 78 */     for (LinesLayer layer : this.m_layers) {
/* 79 */       layer.resetMeshIterator();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\lines\LinesManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */