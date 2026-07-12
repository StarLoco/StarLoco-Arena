/*     */ package com.ankamagames.framework.graphics.animation.descriptors.library;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceListener;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifiableDescriptorLibrary
/*     */   extends AbstractDescriptorLibrary
/*     */   implements ResourceListener
/*     */ {
/*  27 */   private static Logger m_logger = Logger.getLogger(ModifiableDescriptorLibrary.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private BaseDescriptorLibrary m_parent = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String m_parentDescriptorLibraryName;
/*     */ 
/*     */ 
/*     */   
/*     */   private HashMap<String, Material> m_materialLinked;
/*     */ 
/*     */ 
/*     */   
/*  44 */   private int m_changeRevision = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected TIntObjectHashMap<DisplayObjectDescriptor> m_oldDefinitions = new TIntObjectHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_saveOldDefinitions = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModifiableDescriptorLibrary(BaseDescriptorLibrary parent) {
/*  62 */     super(null, false);
/*     */     
/*  64 */     this.m_parent = parent;
/*  65 */     if (this.m_parent != null) {
/*  66 */       this.m_parentDescriptorLibraryName = parent.getName();
/*     */     } else {
/*  68 */       this.m_parentDescriptorLibraryName = null;
/*     */     } 
/*     */     
/*  71 */     DescriptorLibraryManager.getInstance().addListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloadResource(ResourceContext resourceContexts) {
/*  81 */     super.onUnloadResource(resourceContexts);
/*  82 */     this.m_oldDefinitions.remove(((DisplayObjectDescriptor.DisplayObjectDescriptorContext)resourceContexts).getIdentifier());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloadResourceContext(ResourceContext resourceContexts) {
/*  91 */     if (resourceContexts.getResource() == this.m_parent) {
/*  92 */       DescriptorLibraryManager.getInstance().addListener(this);
/*  93 */       this.m_parent = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResourceContextReloaded(ResourceContext resourceContexts) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseDescriptorLibrary getParent() {
/* 109 */     return this.m_parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntArrayList getIdentifiers() {
/* 116 */     return this.m_parent.getIndexedBuffer().getIdentifiers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getLinkages() {
/* 123 */     return this.m_parent.getIndexedBuffer().getLinkages();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChangeRevision() {
/* 130 */     return this.m_changeRevision;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSaveOldDefinitions() {
/* 137 */     return this.m_saveOldDefinitions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureParentAvailability() {
/* 144 */     if (this.m_parent == null) {
/*     */       try {
/* 146 */         this.m_parent = DescriptorLibraryManager.getInstance().getDescriptorLibrary(this.m_parentDescriptorLibraryName);
/* 147 */         DescriptorLibraryManager.getInstance().addListener(this);
/* 148 */       } catch (Exception e) {
/* 149 */         e.printStackTrace();
/*     */       } finally {
/* 151 */         if (this.m_parent == null) {
/* 152 */           m_logger.error("ensureParentAvailability() : Impossible de re-créér la BaseDescriptorLibrary (" + this.m_parentDescriptorLibraryName + ")");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaveOldDefinitions(boolean saveOldDefinitions) {
/* 162 */     this.m_saveOldDefinitions = saveOldDefinitions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String linkage) {
/* 172 */     boolean result = super.contains(linkage);
/* 173 */     if (!result) {
/* 174 */       ensureParentAvailability();
/* 175 */       return (this.m_parent != null && this.m_parent.contains(linkage));
/*     */     } 
/* 177 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdFromLinkage(String linkage) {
/* 187 */     int id = super.getIdFromLinkage(linkage);
/* 188 */     if (id == 0) {
/* 189 */       ensureParentAvailability();
/* 190 */       return (this.m_parent != null) ? this.m_parent.getIdFromLinkage(linkage) : 0;
/*     */     } 
/* 192 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObjectDescriptor getDescriptor(int id) {
/* 202 */     DisplayObjectDescriptor descriptor = super.getDescriptor(id);
/* 203 */     if (descriptor == null) {
/* 204 */       ensureParentAvailability();
/* 205 */       descriptor = (this.m_parent != null) ? this.m_parent.getDescriptor(id) : null;
/*     */     } 
/* 207 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObjectDescriptor getDescriptor(String linkage) {
/* 217 */     DisplayObjectDescriptor descriptor = super.getDescriptor(linkage);
/* 218 */     if (descriptor == null) {
/* 219 */       ensureParentAvailability();
/* 220 */       descriptor = (this.m_parent != null) ? this.m_parent.getDescriptor(linkage) : null;
/*     */     } 
/* 222 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObjectDescriptor setDescriptor(int id, DisplayObjectDescriptor srcDescriptor) {
/* 233 */     incContentChangeRevision();
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (srcDescriptor == null) {
/* 238 */       if (this.m_saveOldDefinitions && this.m_oldDefinitions.containsKey(id)) {
/* 239 */         this.m_definitions.put(id, this.m_oldDefinitions.remove(id));
/*     */       } else {
/* 241 */         this.m_definitions.remove(id);
/*     */       } 
/*     */     } else {
/*     */       
/* 245 */       if (this.m_saveOldDefinitions && this.m_definitions.containsKey(id)) {
/* 246 */         this.m_oldDefinitions.put(id, this.m_definitions.get(id));
/*     */       }
/*     */ 
/*     */       
/* 250 */       String previousLinkage = null;
/* 251 */       DisplayObjectDescriptor previousDescriptor = getDescriptor(id);
/* 252 */       if (previousDescriptor != null) {
/* 253 */         previousLinkage = previousDescriptor.getLinkage();
/*     */       }
/*     */ 
/*     */       
/* 257 */       DisplayObjectDescriptor descriptor = srcDescriptor.duplicate();
/* 258 */       if (descriptor != null) {
/* 259 */         descriptor.setVirtual(true);
/* 260 */         descriptor.setId(id);
/* 261 */         descriptor.setLinkage(previousLinkage);
/* 262 */         descriptor.setVirtualLibrary(this);
/*     */ 
/*     */         
/* 265 */         this.m_definitions.put(id, descriptor);
/* 266 */         this.m_linkageDictionary.put(previousLinkage, id);
/*     */       } 
/*     */       
/* 269 */       return descriptor;
/*     */     } 
/*     */     
/* 272 */     return null;
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
/*     */   public DisplayObjectDescriptor setDescriptor(String linkage, DisplayObjectDescriptor srcDescriptor) {
/* 284 */     if (contains(linkage)) {
/* 285 */       return setDescriptor(getIdFromLinkage(linkage), srcDescriptor);
/*     */     }
/*     */     
/* 288 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<DisplayObjectDescriptor> getDefinitions() {
/* 296 */     ensureParentAvailability();
/* 297 */     ArrayList<DisplayObjectDescriptor> definitions = new ArrayList<DisplayObjectDescriptor>();
/* 298 */     TIntObjectIterator<DisplayObjectDescriptor> iterator = this.m_parent.iterator();
/* 299 */     for (int i = this.m_parent.size(); i-- > 0; ) {
/* 300 */       iterator.advance();
/* 301 */       DisplayObjectDescriptor descriptor = (DisplayObjectDescriptor)iterator.value();
/* 302 */       definitions.add(getDescriptor(descriptor.getId()));
/*     */     } 
/* 304 */     return definitions;
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
/* 315 */     if (this.m_materialLinked == null) {
/* 316 */       this.m_materialLinked = new HashMap<String, Material>();
/*     */     }
/* 318 */     this.m_materialLinked.put(linkage, material);
/* 319 */     incContentChangeRevision();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial(String linkage) {
/*     */     try {
/* 330 */       return this.m_materialLinked.get(linkage);
/* 331 */     } catch (Exception ex) {
/* 332 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void incContentChangeRevision() {
/* 340 */     if (this.m_changeRevision == Integer.MAX_VALUE) {
/* 341 */       this.m_changeRevision = 0;
/*     */     }
/* 343 */     this.m_changeRevision++;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\library\ModifiableDescriptorLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */