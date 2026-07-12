/*     */ package com.ankamagames.framework.graphics.animation.descriptors.library;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapSequenceDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.MovieClipDescriptor;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBufferManager;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
/*     */ import com.ankamagames.framework.kernel.core.resource.ContextFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceFactoryDescriptor;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseDescriptorLibrary
/*     */   extends AbstractDescriptorLibrary
/*     */   implements ManageableResource
/*     */ {
/*     */   public static class BaseDescriptorLibraryContext
/*     */     extends ResourceContext
/*     */   {
/*  30 */     private String m_fileName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getFileName() {
/*  36 */       return this.m_fileName;
/*     */     }
/*     */     
/*     */     public void setFileName(String fileName) {
/*  40 */       this.m_fileName = fileName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onCheckOut() {
/*  46 */       super.onCheckOut();
/*  47 */       this.m_fileName = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onCheckIn() {
/*  52 */       super.onCheckIn();
/*  53 */       this.m_fileName = null;
/*     */     }
/*     */     
/*     */     public boolean isReleasable() {
/*  57 */       return true;
/*     */     }
/*     */   }
/*     */   
/*  61 */   private static Logger m_logger = Logger.getLogger(BaseDescriptorLibrary.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IndexedDefinitionTagBuffer m_indexedBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private String m_name = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_managed = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseDescriptorLibrary() {
/* 115 */     super(new ResourceFactoryDescriptor[] { new ResourceFactoryDescriptor(2, new ResourceFactory<BitmapDescriptor>() { public BitmapDescriptor makeObject() { return new BitmapDescriptor(); } }, new ContextFactory<DisplayObjectDescriptor.DisplayObjectDescriptorContext>() { public DisplayObjectDescriptor.DisplayObjectDescriptorContext makeObject() { return new DisplayObjectDescriptor.DisplayObjectDescriptorContext(); } }), new ResourceFactoryDescriptor(3, new ResourceFactory<BitmapSequenceDescriptor>() { public BitmapSequenceDescriptor makeObject() { return new BitmapSequenceDescriptor(); } }, new ContextFactory<DisplayObjectDescriptor.DisplayObjectDescriptorContext>() { public DisplayObjectDescriptor.DisplayObjectDescriptorContext makeObject() { return new DisplayObjectDescriptor.DisplayObjectDescriptorContext(); } }), new ResourceFactoryDescriptor(4, new ResourceFactory<MovieClipDescriptor>() { public MovieClipDescriptor makeObject() { return new MovieClipDescriptor(); } }, new ContextFactory<DisplayObjectDescriptor.DisplayObjectDescriptorContext>() { public DisplayObjectDescriptor.DisplayObjectDescriptorContext makeObject() { return new DisplayObjectDescriptor.DisplayObjectDescriptorContext(); } }) }false);
/*     */     
/* 117 */     setMaxResourceAge(15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void create(IndexedDefinitionTagBuffer indexedBuffer, String name) {
/* 128 */     this.m_indexedBuffer = indexedBuffer;
/* 129 */     this.m_name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManaged() {
/* 137 */     return this.m_managed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setManaged(boolean managed) {
/* 145 */     this.m_managed = managed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 152 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexedDefinitionTagBuffer getIndexedBuffer() {
/* 159 */     return this.m_indexedBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int id) {
/* 170 */     if (!DescriptorLibraryManager.getInstance().tagResourceInUse(this)) {
/* 171 */       return false;
/*     */     }
/*     */     
/* 174 */     boolean result = super.contains(id);
/* 175 */     if (!result) {
/* 176 */       return this.m_indexedBuffer.contains(id);
/*     */     }
/* 178 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String linkage) {
/* 188 */     if (!DescriptorLibraryManager.getInstance().tagResourceInUse(this)) {
/* 189 */       return false;
/*     */     }
/*     */     
/* 192 */     boolean result = super.contains(linkage);
/* 193 */     if (!result) {
/* 194 */       return this.m_indexedBuffer.contains(linkage);
/*     */     }
/* 196 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdFromLinkage(String linkage) {
/* 207 */     if (!DescriptorLibraryManager.getInstance().tagResourceInUse(this)) {
/* 208 */       return 0;
/*     */     }
/* 210 */     int id = super.getIdFromLinkage(linkage);
/* 211 */     if (id == 0) {
/* 212 */       return this.m_indexedBuffer.getIdFromLinkage(linkage);
/*     */     }
/* 214 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayObjectDescriptor getDescriptor(int id) {
/* 225 */     if (!DescriptorLibraryManager.getInstance().tagResourceInUse(this)) {
/* 226 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 230 */     DisplayObjectDescriptor descriptor = super.getDescriptor(id);
/*     */     
/* 232 */     if (this.m_indexedBuffer == null)
/*     */     {
/* 234 */       return null;
/*     */     }
/*     */     
/* 237 */     if (descriptor == null && this.m_indexedBuffer.contains(id)) {
/*     */       
/* 239 */       DefinitionTag tag = this.m_indexedBuffer.getDefinitionTag(id);
/* 240 */       if (tag != null) {
/* 241 */         descriptor = createDescriptor((Tag)tag);
/* 242 */         if (descriptor.getLibraryName() == null) {
/* 243 */           boolean bool = false;
/*     */         }
/*     */       } else {
/* 246 */         m_logger.error("Tag illisible pour (id=" + id + ")");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (descriptor != null) {
/* 253 */       tagResourceInUse((ManageableResource)descriptor);
/*     */     }
/* 255 */     return descriptor;
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
/*     */   public DisplayObjectDescriptor getDescriptor(String linkage) {
/* 267 */     if (!DescriptorLibraryManager.getInstance().tagResourceInUse(this)) {
/* 268 */       return null;
/*     */     }
/*     */     
/* 271 */     DisplayObjectDescriptor descriptor = super.getDescriptor(linkage);
/* 272 */     if (descriptor == null && this.m_indexedBuffer.contains(linkage)) {
/* 273 */       DefinitionTag tag = this.m_indexedBuffer.getDefinitionTag(linkage);
/* 274 */       if (tag != null) {
/* 275 */         descriptor = createDescriptor((Tag)tag);
/*     */       } else {
/* 277 */         m_logger.error("Tag illisible pour (linkage=" + linkage + ")");
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     if (descriptor != null) {
/* 282 */       tagResourceInUse((ManageableResource)descriptor);
/*     */     }
/* 284 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createAllDescriptors() {
/* 292 */     if (!DescriptorLibraryManager.getInstance().tagResourceInUse(this)) {
/*     */       return;
/*     */     }
/*     */     
/* 296 */     TIntArrayList identifiers = this.m_indexedBuffer.getIdentifiers();
/* 297 */     for (int i = 0; i < identifiers.size(); i++) {
/* 298 */       int identifier = identifiers.get(i);
/* 299 */       DefinitionTag tag = this.m_indexedBuffer.getDefinitionTag(identifier);
/* 300 */       if (tag != null) {
/* 301 */         createDescriptor((Tag)tag);
/*     */       } else {
/* 303 */         m_logger.error("Tag illisible pour (id=" + identifier + ")");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DisplayObjectDescriptor createDescriptor(Tag tag) {
/* 314 */     DisplayObjectDescriptor descriptor = null;
/* 315 */     int identifier = 0;
/*     */     
/* 317 */     DisplayObjectDescriptor.DisplayObjectDescriptorContext context = (DisplayObjectDescriptor.DisplayObjectDescriptorContext)getNewResource(tag.getCode());
/* 318 */     if (context != null) {
/*     */ 
/*     */       
/* 321 */       descriptor = (DisplayObjectDescriptor)context.getResource();
/*     */ 
/*     */ 
/*     */       
/* 325 */       descriptor.setLibrary(this);
/*     */ 
/*     */       
/* 328 */       identifier = ((DefinitionTag)tag).getIdentifier();
/* 329 */       context.setIdentifier(identifier);
/*     */     } 
/*     */     
/* 332 */     if (descriptor != null) {
/* 333 */       this.m_definitions.put(identifier, descriptor);
/* 334 */       descriptor.setId(identifier);
/* 335 */       if (descriptor.hasLinkage()) {
/* 336 */         this.m_linkageDictionary.put(descriptor.getLinkage(), identifier);
/*     */       }
/*     */     } 
/*     */     
/* 340 */     return descriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public long estimateMemoryUsageInBytes() {
/* 345 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadResource(ResourceContext resourceContext) {
/* 350 */     long before = System.currentTimeMillis();
/*     */     
/* 352 */     BaseDescriptorLibraryContext descriptorLibraryContext = (BaseDescriptorLibraryContext)resourceContext;
/* 353 */     BaseDescriptorLibrary library = (BaseDescriptorLibrary)resourceContext.getResource();
/*     */     
/* 355 */     IndexedDefinitionTagBuffer indexedBuffer = IndexedDefinitionTagBufferManager.getInstance().getIndexedBuffer(descriptorLibraryContext.getFileName());
/* 356 */     if (indexedBuffer != null) {
/*     */ 
/*     */       
/* 359 */       library.create(indexedBuffer, descriptorLibraryContext.getFileName());
/*     */     } else {
/* 361 */       System.err.println("indexedBuffer = null");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadResource(ResourceContext resourceContext) {
/* 370 */     BaseDescriptorLibrary library = (BaseDescriptorLibrary)resourceContext.getResource();
/*     */ 
/*     */ 
/*     */     
/* 374 */     library.m_indexedBuffer = null;
/* 375 */     library.releaseAllResources();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void reset() {
/* 381 */     this.m_definitions.clear();
/* 382 */     this.m_linkageDictionary.clear();
/* 383 */     this.m_displayObjectListeners = null;
/* 384 */     this.m_indexedBuffer = null;
/*     */   }
/*     */   
/*     */   public void onCheckIn() {
/* 388 */     reset();
/*     */   }
/*     */   
/*     */   public void onCheckOut() {
/* 392 */     reset();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 396 */     return "BaseDescriptorLibrary: " + this.m_name + " @" + Integer.toHexString(hashCode());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\library\BaseDescriptorLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */