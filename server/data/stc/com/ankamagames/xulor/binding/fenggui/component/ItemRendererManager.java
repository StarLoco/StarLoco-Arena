/*    */ package com.ankamagames.xulor.binding.fenggui.component;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IItemRenderable;
/*    */ import java.util.ArrayList;
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
/*    */ public class ItemRendererManager
/*    */ {
/*    */   public static final String DEFAULT_RENDERER = "default";
/*    */   public static final String EMPTY_RENDERER = "empty";
/*    */   protected ArrayList<ItemRenderer> m_renderers;
/* 22 */   protected ItemRenderer m_defaultRenderer = null;
/* 23 */   protected ItemRenderer m_emptyRenderer = new EmptyItemRenderer();
/*    */   
/*    */   public ItemRendererManager(ArrayList<ItemRenderer> renderers) {
/* 26 */     this.m_renderers = renderers;
/* 27 */     registerDefaultRenderer();
/*    */   }
/*    */   
/*    */   protected void registerDefaultRenderer() {
/* 31 */     this.m_defaultRenderer = null;
/*    */     
/* 33 */     if ((this.m_renderers == null) || (this.m_renderers.size() == 0)) {
/* 34 */       return;
/*    */     }
/*    */     
/* 37 */     if (this.m_defaultRenderer == null) {
/* 38 */       this.m_defaultRenderer = ((ItemRenderer)this.m_renderers.get(0));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean assign(IItemRenderable renderable)
/*    */   {
/* 44 */     if ((this.m_defaultRenderer == null) || (renderable == null)) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     for (ItemRenderer renderer : this.m_renderers) {
/* 49 */       if (renderer.isRenderableCompatible(renderable)) {
/* 50 */         if (renderable.getRenderer() != renderer) {
/* 51 */           renderable.setRenderer(renderer);
/* 52 */           return true;
/*    */         }
/* 54 */         return false;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 59 */     if (renderable.getRenderer() == null) {
/* 60 */       if (this.m_emptyRenderer.isRenderableCompatible(renderable)) {
/* 61 */         renderable.setRenderer(this.m_emptyRenderer);
/*    */       } else {
/* 63 */         renderable.setRenderer(this.m_defaultRenderer);
/*    */       }
/* 65 */       return true;
/*    */     }
/*    */     
/* 68 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ArrayList<ItemRenderer> getRenderers()
/*    */   {
/* 75 */     return this.m_renderers;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void setRenderers(ArrayList<ItemRenderer> renderers)
/*    */   {
/* 82 */     this.m_renderers = renderers;
/* 83 */     registerDefaultRenderer();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\ItemRendererManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */