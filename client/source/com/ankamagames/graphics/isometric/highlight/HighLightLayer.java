/*     */ package com.ankamagames.graphics.isometric.highlight;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import gnu.trove.TLongObjectHashMap;
/*     */ import gnu.trove.TLongObjectIterator;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HighLightLayer
/*     */ {
/*  24 */   private static final ObjectPool m_pool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<HighLightMesh>() {
/*     */         public HighLightMesh makeObject() {
/*  26 */           return new HighLightMesh();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String m_name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Material m_material;
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final TLongObjectHashMap<HighLightMesh> m_meshs = new TLongObjectHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_visible = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private BaseTexture m_texture;
/*     */ 
/*     */ 
/*     */   
/*     */   public HighLightLayer(String name, BaseTexture texture) {
/*  56 */     this.m_name = name;
/*  57 */     this.m_texture = texture;
/*  58 */     this.m_material = new Material();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  65 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(Material material) {
/*  73 */     this.m_material = material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  80 */     return this.m_material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseTexture getTexture() {
/*  87 */     return this.m_texture;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/*  91 */     return this.m_visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  95 */     this.m_visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HighLightMesh getMesh(long handle) {
/* 103 */     return (HighLightMesh)this.m_meshs.get(handle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseMeshs() {
/* 110 */     TLongObjectIterator<HighLightMesh> iterator = this.m_meshs.iterator();
/* 111 */     for (int i = this.m_meshs.size(); --i >= 0; ) {
/* 112 */       iterator.advance();
/* 113 */       ((HighLightMesh)iterator.value()).release();
/*     */     } 
/* 115 */     this.m_meshs.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldElementAdded(long handle) {
/* 122 */     if (!this.m_meshs.contains(handle)) {
/*     */       HighLightMesh mesh;
/*     */       try {
/* 125 */         mesh = (HighLightMesh)m_pool.borrowObject();
/* 126 */         mesh.setPool(m_pool);
/* 127 */       } catch (Exception e) {
/* 128 */         mesh = new HighLightMesh();
/*     */       } 
/* 130 */       mesh.setTexture(this.m_texture);
/* 131 */       mesh.setMaterial(this.m_material);
/* 132 */       this.m_meshs.put(handle, mesh);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldElementRemoved(long handle) {
/* 140 */     if (!this.m_meshs.isEmpty()) {
/* 141 */       HighLightMesh mesh = (HighLightMesh)this.m_meshs.remove(handle);
/* 142 */       if (mesh != null) {
/* 143 */         mesh.setMaterial(null);
/* 144 */         mesh.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightLayer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */