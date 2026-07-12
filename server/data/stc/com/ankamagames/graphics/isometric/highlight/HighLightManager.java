/*     */ package com.ankamagames.graphics.isometric.highlight;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*     */ import gnu.trove.TLongObjectHashMap;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HighLightManager
/*     */   implements RenderProcessHandler
/*     */ {
/*  27 */   private static final HighLightManager m_instance = new HighLightManager();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private HashMap<String, HighLightLayer> m_layers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private TLongObjectHashMap<ArrayList<HighLightLayer>> m_layerReferenceElements;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private BaseTexture m_defaultTexture;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HighLightManager()
/*     */   {
/*  49 */     this.m_layerReferenceElements = new TLongObjectHashMap();
/*  50 */     this.m_layers = new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static HighLightManager getInstance()
/*     */   {
/*  57 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected BaseTexture getDefaultTexture()
/*     */   {
/*  64 */     return this.m_defaultTexture;
/*     */   }
/*     */   
/*     */   public void setDefaultTexture(BaseTexture defaultTexture)
/*     */   {
/*  69 */     this.m_defaultTexture = defaultTexture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HighLightLayer createLayer(String name)
/*     */     throws Exception
/*     */   {
/*  80 */     if (this.m_defaultTexture != null) {
/*  81 */       return createLayer(name, this.m_defaultTexture);
/*     */     }
/*  83 */     throw new Exception("Aucune texture par défaut n'est définie !");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HighLightLayer createLayer(String name, BaseTexture texture)
/*     */     throws Exception
/*     */   {
/*  95 */     if (this.m_layers.containsKey(name)) {
/*  96 */       throw new Exception("Un layer " + name + " existe déjà !");
/*     */     }
/*  98 */     HighLightLayer layer = new HighLightLayer(name, texture);
/*  99 */     this.m_layers.put(name, layer);
/* 100 */     return layer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeLayer(String name)
/*     */   {
/* 109 */     if (this.m_layers.containsKey(name)) {
/* 110 */       clearLayer(name);
/* 111 */       this.m_layers.remove(name);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HighLightLayer getLayer(String name)
/*     */   {
/* 123 */     if (this.m_layers.containsKey(name)) {
/* 124 */       return (HighLightLayer)this.m_layers.get(name);
/*     */     }
/* 126 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearLayer(String layerName)
/*     */   {
/* 136 */     HighLightLayer layer = getLayer(layerName);
/* 137 */     if (layer != null) {
/* 138 */       Object[] allLayers = this.m_layerReferenceElements.getValues();
/*     */       Object[] arrayOfObject1;
/* 140 */       int j = (arrayOfObject1 = allLayers).length; for (int i = 0; i < j; i++) { Object obj = arrayOfObject1[i];
/* 141 */         ArrayList<HighLightLayer> layers = (ArrayList)obj;
/* 142 */         if (layers.contains(layer)) {
/* 143 */           layers.remove(layer);
/*     */         }
/*     */       }
/* 146 */       layer.releaseMeshs();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 154 */     this.m_layerReferenceElements.clear();
/* 155 */     for (HighLightLayer layer : this.m_layers.values()) {
/* 156 */       layer.releaseMeshs();
/*     */     }
/*     */   }
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
/*     */ 
/*     */   public boolean add(UniqueHandleReference highLightLayerReference, String layerName)
/*     */   {
/* 172 */     if (highLightLayerReference == null) {
/* 173 */       return false;
/*     */     }
/*     */     
/* 176 */     ArrayList<HighLightLayer> layers = (ArrayList)this.m_layerReferenceElements.get(highLightLayerReference.getHandle());
/* 177 */     if (layers == null) {
/* 178 */       layers = new ArrayList();
/* 179 */       this.m_layerReferenceElements.put(highLightLayerReference.getHandle(), layers);
/*     */     }
/*     */     
/*     */ 
/* 183 */     HighLightLayer layer = getLayer(layerName);
/* 184 */     if (layer == null)
/*     */     {
/* 186 */       System.err.println("Le calque " + layerName + " n'existe pas !");
/*     */     }
/*     */     else {
/* 189 */       boolean added = false;
/*     */       
/* 191 */       if (!layers.contains(layer)) {
/* 192 */         layers.add(layer);
/* 193 */         added = true;
/*     */       }
/*     */       
/*     */ 
/* 197 */       layer.onWorldElementAdded(highLightLayerReference.getHandle());
/*     */       
/*     */ 
/* 200 */       return added;
/*     */     }
/* 202 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAll(List<? extends UniqueHandleReference> highLightLayerReferences, String layerName)
/*     */   {
/* 212 */     int size = highLightLayerReferences.size();
/* 213 */     for (int i = 0; i < size; i++) {
/* 214 */       if (!add((UniqueHandleReference)highLightLayerReferences.get(i), layerName)) {
/* 215 */         highLightLayerReferences.remove(i);
/* 216 */         i--;
/* 217 */         size--;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(UniqueHandleReference worldElement, String layerName)
/*     */   {
/* 231 */     ArrayList<HighLightLayer> layers = (ArrayList)this.m_layerReferenceElements.get(worldElement.getHandle());
/* 232 */     if (layers != null)
/*     */     {
/*     */ 
/* 235 */       HighLightLayer layer = getLayer(layerName);
/* 236 */       if (layer != null) {
/* 237 */         layers.remove(layer);
/*     */         
/*     */ 
/* 240 */         layer.onWorldElementRemoved(worldElement.getHandle());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(List<? extends UniqueHandleReference> worldElements, String layerName)
/*     */   {
/* 253 */     for (UniqueHandleReference worldElement : worldElements) {
/* 254 */       remove(worldElement, layerName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prepareElementBeforeRendering(IsoWorldScene scene, HighLightedElement displayedElement)
/*     */   {
/* 267 */     long handle = displayedElement.getLayerReference().getHandle();
/* 268 */     ArrayList<HighLightLayer> layers = (ArrayList)this.m_layerReferenceElements.get(handle);
/* 269 */     if ((layers != null) && (!layers.isEmpty()))
/*     */     {
/* 271 */       HighLightLayer[] arrayLayers = new HighLightLayer[layers.size()];
/* 272 */       layers.toArray(arrayLayers);
/*     */       HighLightLayer[] arrayOfHighLightLayer1;
/* 274 */       int j = (arrayOfHighLightLayer1 = arrayLayers).length; for (int i = 0; i < j; i++) { HighLightLayer layer = arrayOfHighLightLayer1[i];
/* 275 */         if (layer.isVisible()) {
/* 276 */           HighLightMesh highLightMesh = layer.getMesh(handle);
/* 277 */           if (highLightMesh != null) {
/* 278 */             displayedElement.transformHighLightMesh(highLightMesh, (int)Math.floor(scene.getElevationUnit()));
/* 279 */             scene.addChild(highLightMesh);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void process(IsoWorldScene scene, long realTime, int frameCount) {}
/*     */   
/*     */   public void prepareBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */