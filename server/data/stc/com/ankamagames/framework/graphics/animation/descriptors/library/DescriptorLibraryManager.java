/*     */ package com.ankamagames.framework.graphics.animation.descriptors.library;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.Renderer;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBufferManager;
/*     */ import com.ankamagames.framework.kernel.core.resource.ContextFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.SingleResourceManager;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DescriptorLibraryManager
/*     */   extends SingleResourceManager
/*     */ {
/*  25 */   private static final Logger m_logger = Logger.getLogger(DescriptorLibraryManager.class);
/*     */   
/*  27 */   private static final DescriptorLibraryManager m_instance = new DescriptorLibraryManager();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Renderer m_renderer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  40 */   private final HashMap<String, BaseDescriptorLibrary> m_descriptorLibraries = new HashMap(30);
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
/*     */   private DescriptorLibraryManager()
/*     */   {
/*  55 */     super(new ResourceFactory()new ContextFactory
/*     */     {
/*     */       public BaseDescriptorLibrary makeObject()
/*     */       {
/*  48 */         return new BaseDescriptorLibrary();
/*     */       }
/*     */       
/*  51 */     }, new ContextFactory() {
/*     */       public BaseDescriptorLibrary.BaseDescriptorLibraryContext makeObject() {
/*  53 */         return new BaseDescriptorLibrary.BaseDescriptorLibraryContext();
/*     */       }
/*  55 */     }, true);
/*     */     
/*  57 */     setMaxResourceAge(60);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static DescriptorLibraryManager getInstance()
/*     */   {
/*  64 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRenderer(Renderer renderer)
/*     */   {
/*  71 */     this.m_renderer = renderer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HashMap<String, BaseDescriptorLibrary> getDescriptorLibraries()
/*     */   {
/*  78 */     return this.m_descriptorLibraries;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseDescriptorLibrary getDescriptorLibrary(String fileName)
/*     */   {
/*  89 */     if (fileName == null) {
/*  90 */       int i = 0;
/*     */     }
/*     */     
/*  93 */     BaseDescriptorLibrary library = null;
/*  94 */     if (this.m_descriptorLibraries.containsKey(fileName)) {
/*  95 */       library = (BaseDescriptorLibrary)this.m_descriptorLibraries.get(fileName);
/*  96 */       if ((library.isManaged()) && (!isResourceHandled(library)))
/*     */       {
/*  98 */         this.m_descriptorLibraries.remove(fileName);
/*  99 */         library = loadLibrary(fileName);
/*     */       }
/*     */     }
/*     */     else {
/* 103 */       library = loadLibrary(fileName);
/*     */     }
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
/* 116 */     return library;
/*     */   }
/*     */   
/*     */   private BaseDescriptorLibrary loadLibrary(String fileName) {
/* 120 */     BaseDescriptorLibrary library = null;
/*     */     
/* 122 */     IndexedDefinitionTagBuffer indexedBuffer = IndexedDefinitionTagBufferManager.getInstance().getIndexedBuffer(fileName);
/* 123 */     if (indexedBuffer != null)
/*     */     {
/*     */ 
/*     */ 
/* 127 */       library = getManagedDescriptorLibrary(indexedBuffer, fileName);
/* 128 */       if (library != null) {
/* 129 */         this.m_descriptorLibraries.put(fileName, library);
/*     */       } else {
/* 131 */         System.err.println("library = null");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 137 */     return library;
/*     */   }
/*     */   
/*     */   public String getDescriptorLibraryName(BaseDescriptorLibrary library) {
/* 141 */     for (Map.Entry<String, BaseDescriptorLibrary> entry : this.m_descriptorLibraries.entrySet()) {
/* 142 */       if (entry.getValue() == library)
/* 143 */         return (String)entry.getKey();
/*     */     }
/* 145 */     return "<unknown library>";
/*     */   }
/*     */   
/*     */   public void releaseAllResources()
/*     */   {
/* 150 */     super.releaseAllResources();
/* 151 */     this.m_descriptorLibraries.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseDescriptorLibrary getManagedDescriptorLibrary(IndexedDefinitionTagBuffer indexedBuffer, String name)
/*     */   {
/* 169 */     BaseDescriptorLibrary.BaseDescriptorLibraryContext descriptorLibraryContext = (BaseDescriptorLibrary.BaseDescriptorLibraryContext)getNewResource();
/*     */     
/* 171 */     BaseDescriptorLibrary descriptorLibrary = (BaseDescriptorLibrary)descriptorLibraryContext.getResource();
/* 172 */     descriptorLibrary.setManaged(true);
/* 173 */     descriptorLibraryContext.setFileName(name);
/* 174 */     descriptorLibrary.create(indexedBuffer, name);
/* 175 */     descriptorLibraryContext.setResourceUnloaded(false);
/*     */     
/*     */ 
/*     */ 
/* 179 */     return descriptorLibrary;
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
/*     */   public BaseDescriptorLibrary getDescriptorLibrary(IndexedDefinitionTagBuffer indexedBuffer, String name)
/*     */   {
/* 194 */     BaseDescriptorLibrary descriptorLibrary = new BaseDescriptorLibrary();
/* 195 */     descriptorLibrary.setManaged(false);
/* 196 */     descriptorLibrary.create(indexedBuffer, name);
/* 197 */     if (this.m_renderer != null) {
/* 198 */       this.m_renderer.addResourceManagerToUpdate(descriptorLibrary);
/*     */     }
/* 200 */     this.m_descriptorLibraries.put(name, descriptorLibrary);
/*     */     
/* 202 */     return descriptorLibrary;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onUnloadResource(ResourceContext context)
/*     */   {
/* 210 */     super.onUnloadResource(context);
/* 211 */     BaseDescriptorLibrary.BaseDescriptorLibraryContext libContext = (BaseDescriptorLibrary.BaseDescriptorLibraryContext)context;
/* 212 */     BaseDescriptorLibrary library = (BaseDescriptorLibrary)this.m_descriptorLibraries.remove(libContext.getFileName());
/*     */     
/* 214 */     if ((library != null) && (this.m_renderer != null)) {
/* 215 */       this.m_renderer.removeResourceManager(library);
/*     */       
/* 217 */       library.releaseAllResources();
/*     */     }
/* 219 */     releaseResource(context);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void onResourceReloaded(ResourceContext context)
/*     */   {
/* 226 */     super.onResourceReloaded(context);
/*     */     
/* 228 */     BaseDescriptorLibrary.BaseDescriptorLibraryContext libContext = (BaseDescriptorLibrary.BaseDescriptorLibraryContext)context;
/* 229 */     BaseDescriptorLibrary library = (BaseDescriptorLibrary)libContext.getResource();
/* 230 */     this.m_descriptorLibraries.put(libContext.getFileName(), library);
/* 231 */     if (this.m_renderer != null) {
/* 232 */       this.m_renderer.addResourceManagerToUpdate(library);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean tagResourceInUse(ManageableResource resource) {
/* 237 */     BaseDescriptorLibrary library = (BaseDescriptorLibrary)resource;
/* 238 */     if (library == null) {
/* 239 */       m_logger.error("Impossible d'obtenir la resource depuis ce contexte.");
/* 240 */       return false;
/*     */     }
/* 242 */     if (library.isManaged()) {
/* 243 */       if (this.m_descriptorLibraries.containsKey(library.getName()))
/*     */       {
/* 245 */         boolean bTagSuccessful = super.tagResourceInUse(resource);
/*     */         
/*     */ 
/*     */ 
/* 249 */         return bTagSuccessful;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 254 */       BaseDescriptorLibrary.BaseDescriptorLibraryContext context = (BaseDescriptorLibrary.BaseDescriptorLibraryContext)getNewResource();
/* 255 */       synchronized (context.getMutex()) {
/* 256 */         context.setResource(library);
/* 257 */         context.setFileName(library.getName());
/* 258 */         context.setLastUseFrame(getFrameCounter());
/*     */         
/* 260 */         if (context.isResourceUnloaded()) {
/* 261 */           library.reloadResource(context);
/* 262 */           context.setResourceUnloaded(false);
/* 263 */           onResourceReloaded(context);
/*     */         }
/*     */         
/* 266 */         return true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 271 */     return true;
/*     */   }
/*     */   
/*     */   public void release(ManageableResource resource)
/*     */   {
/* 276 */     ResourceContext context = (ResourceContext)this.m_handlers.get(resource);
/* 277 */     if (context != null) {
/* 278 */       releaseResource(context);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeLibrary(String name) {
/* 283 */     this.m_descriptorLibraries.remove(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\library\DescriptorLibraryManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */