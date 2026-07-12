/*     */ package com.ankamagames.framework.graphics.animation.descriptors.library;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.kernel.core.resource.BaseResourceManager;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceFactoryDescriptor;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import gnu.trove.TObjectIntHashMap;
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
/*     */ public class AbstractDescriptorLibrary
/*     */   extends BaseResourceManager
/*     */ {
/*  28 */   protected TIntObjectHashMap<DisplayObjectDescriptor> m_definitions = new TIntObjectHashMap();
/*     */ 
/*     */   
/*  31 */   protected TObjectIntHashMap<String> m_linkageDictionary = new TObjectIntHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   protected TIntObjectHashMap<DisplayObjectListener> m_displayObjectListeners;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractDescriptorLibrary(ResourceFactoryDescriptor[] factories, boolean useClock) {
/*  41 */     super(factories, useClock);
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
/*     */   public DisplayObjectDescriptor getDescriptor(int id) {
/*  55 */     return (DisplayObjectDescriptor)this.m_definitions.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObjectDescriptor getDescriptor(String linkage) {
/*  66 */     if (contains(linkage)) {
/*  67 */       int id = getIdFromLinkage(linkage);
/*  68 */       return (DisplayObjectDescriptor)this.m_definitions.get(id);
/*     */     } 
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int id) {
/*  78 */     return this.m_definitions.containsKey(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String linkage) {
/*  86 */     return this.m_linkageDictionary.containsKey(linkage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdFromLinkage(String linkage) {
/*  94 */     return this.m_linkageDictionary.get(linkage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntObjectIterator<DisplayObjectDescriptor> iterator() {
/* 103 */     return this.m_definitions.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 112 */     return this.m_definitions.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayObjectListener(String linkageName, DisplayObjectListener listener) {
/* 122 */     if (contains(linkageName)) {
/* 123 */       setDisplayObjectListener(getIdFromLinkage(linkageName), listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayObjectListener(int id, DisplayObjectListener listener) {
/* 131 */     if (this.m_displayObjectListeners == null) {
/* 132 */       this.m_displayObjectListeners = new TIntObjectHashMap();
/*     */     }
/* 134 */     this.m_displayObjectListeners.put(id, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDisplayObjectListener(String linkageName) {
/* 142 */     if (contains(linkageName)) {
/* 143 */       removeDisplayObjectListener(getIdFromLinkage(linkageName));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDisplayObjectListener(int id) {
/* 150 */     this.m_displayObjectListeners.remove(id);
/*     */   }
/*     */   
/*     */   protected DisplayObjectListener getDisplayObjectListener(int id) {
/* 154 */     return (this.m_displayObjectListeners != null) ? (DisplayObjectListener)this.m_displayObjectListeners.get(id) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObject createInstanceOf(int id) {
/* 165 */     DisplayObject displayObject = null;
/*     */     
/* 167 */     DisplayObjectDescriptor displayObjectDescriptor = getDescriptor(id);
/* 168 */     if (displayObjectDescriptor != null) {
/* 169 */       displayObject = displayObjectDescriptor.createInstance(this);
/* 170 */       DisplayObjectListener listener = getDisplayObjectListener(id);
/* 171 */       if (listener != null) {
/* 172 */         displayObject.addListener(listener);
/* 173 */         listener.onCreated(displayObject);
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return displayObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(String linkage, Material material) {
/* 188 */     System.err.println("seul un ModifiableDescriptorLibrary peut avoir un material");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial(String linkage) {
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onResourceReloaded(ResourceContext context) {
/* 204 */     super.onResourceReloaded(context);
/*     */     
/* 206 */     DisplayObjectDescriptor.DisplayObjectDescriptorContext dcontext = (DisplayObjectDescriptor.DisplayObjectDescriptorContext)context;
/* 207 */     DisplayObjectDescriptor displayObjectDescriptor = (DisplayObjectDescriptor)dcontext.getResource();
/* 208 */     this.m_definitions.put(displayObjectDescriptor.getId(), displayObjectDescriptor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnloadResource(ResourceContext context) {
/* 213 */     super.onUnloadResource(context);
/*     */     
/* 215 */     DisplayObjectDescriptor.DisplayObjectDescriptorContext dcontext = (DisplayObjectDescriptor.DisplayObjectDescriptorContext)context;
/* 216 */     this.m_definitions.remove(dcontext.getIdentifier());
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
/*     */   public String toString() {
/* 228 */     return "DescriptorLibrary definition.size()=" + this.m_definitions.size() + "  @" + Integer.toHexString(hashCode());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\library\AbstractDescriptorLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */