/*     */ package com.ankamagames.framework.kernel.core.resource;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.message.ProcessScheduler;
/*     */ import gnu.trove.TIntIntHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseResourceManager
/*     */ {
/*     */   private static final boolean DEBUG = false;
/*  27 */   protected static Logger m_logger = Logger.getLogger(BaseResourceManager.class);
/*     */   
/*     */   protected ResourceFactoryDescriptor[] m_factories;
/*  30 */   protected TIntIntHashMap m_typeIdRelations = new TIntIntHashMap();
/*     */   
/*     */   protected MonitoredPool[] m_resourcePool;
/*     */   
/*     */   protected MonitoredPool[] m_contextPool;
/*  35 */   protected final Object m_poolMutex = new Object();
/*  36 */   protected final Object m_handlersMutex = new Object();
/*  37 */   protected final Object m_resourcesToReleaseMutex = new Object();
/*     */   
/*  39 */   protected HashMap<ManageableResource, ResourceContext> m_handlers = new HashMap<ManageableResource, ResourceContext>();
/*  40 */   protected ArrayList<ResourceContext> m_resourcesToRelease = new ArrayList<ResourceContext>();
/*     */   
/*     */   protected int m_frameCounter;
/*  43 */   protected int m_maxResourceAge = 5;
/*     */   
/*  45 */   protected final List<ResourceListener> m_listeners = new ArrayList<ResourceListener>();
/*  46 */   private final List<ResourceListener> m_listenersToRemove = new ArrayList<ResourceListener>();
/*  47 */   private final List<ResourceListener> m_listenersToAdd = new ArrayList<ResourceListener>();
/*     */ 
/*     */   
/*     */   public BaseResourceManager(ResourceFactoryDescriptor[] factories, boolean bUseClock) {
/*  51 */     if (factories == null) {
/*     */       return;
/*     */     }
/*  54 */     this.m_factories = factories;
/*     */     
/*  56 */     this.m_resourcePool = new MonitoredPool[this.m_factories.length];
/*  57 */     this.m_contextPool = new MonitoredPool[this.m_factories.length];
/*     */     
/*  59 */     for (int i = 0; i < factories.length; i++) {
/*  60 */       this.m_resourcePool[i] = new MonitoredPool(this.m_factories[i].getResourceFactory());
/*  61 */       this.m_contextPool[i] = new MonitoredPool(this.m_factories[i].getContextFactory());
/*  62 */       this.m_typeIdRelations.put(this.m_factories[i].getTypeId(), i);
/*     */     } 
/*     */     
/*  65 */     this.m_frameCounter = 0;
/*     */     
/*  67 */     if (bUseClock) {
/*  68 */       ProcessScheduler.getInstance().schedule(new Runnable() {
/*     */             public void run() {
/*  70 */               BaseResourceManager.this.update();
/*     */             }
/*  72 */           },  1000L, -1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceContext getNewResource(int typeId) {
/*  80 */     int typeRel = this.m_typeIdRelations.get(typeId);
/*     */     
/*  82 */     ManageableResource resource = checkResourceOut(typeRel);
/*  83 */     ResourceContext context = checkContextOut(typeRel);
/*     */     
/*  85 */     if (resource == null) {
/*  86 */       if (context != null) {
/*  87 */         checkContextIn(typeRel, context);
/*  88 */         context = null;
/*     */       }
/*     */     
/*  91 */     } else if (context == null) {
/*  92 */       checkResourceIn(typeRel, resource);
/*     */     } else {
/*  94 */       context.setResourceUnloaded(true);
/*  95 */       context.setResource(resource);
/*  96 */       context.setLastUseFrame(this.m_frameCounter);
/*  97 */       context.setTypeId(typeId);
/*  98 */       synchronized (this.m_handlersMutex) {
/*  99 */         this.m_handlers.put(resource, context);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isResourceHandled(ManageableResource resource) {
/* 112 */     synchronized (this.m_handlersMutex) {
/* 113 */       return this.m_handlers.containsKey(resource);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tagResourceInUse(ManageableResource resource) {
/* 123 */     ResourceContext context = this.m_handlers.get(resource);
/* 124 */     if (context != null && 
/* 125 */       context.getResource() == resource) {
/* 126 */       if (this.m_resourcesToRelease.contains(context)) {
/* 127 */         this.m_resourcesToRelease.remove(context);
/*     */       }
/* 129 */       synchronized (context.getMutex()) {
/* 130 */         context.setLastUseFrame(this.m_frameCounter);
/* 131 */         if (context.isResourceUnloaded()) {
/*     */           try {
/* 133 */             resource.reloadResource(context);
/* 134 */           } catch (Exception e) {
/* 135 */             e.printStackTrace();
/*     */           } 
/* 137 */           context.setResourceUnloaded(false);
/* 138 */           onResourceReloaded(context);
/*     */         } 
/* 140 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   public void releaseResource(ResourceContext context) {
/* 148 */     synchronized (this.m_resourcesToReleaseMutex) {
/* 149 */       this.m_resourcesToRelease.add(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseAllResources() {
/* 155 */     synchronized (this.m_resourcesToReleaseMutex) {
/* 156 */       synchronized (this.m_handlersMutex) {
/* 157 */         this.m_resourcesToRelease.clear();
/* 158 */         this.m_resourcesToRelease.addAll(this.m_handlers.values());
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
/*     */   private ManageableResource checkResourceOut(int typeId) {
/* 171 */     if (this.m_typeIdRelations.containsValue(typeId)) {
/* 172 */       synchronized (this.m_poolMutex) {
/*     */         try {
/* 174 */           return (ManageableResource)this.m_resourcePool[typeId].borrowObject();
/* 175 */         } catch (Exception e) {
/* 176 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResourceIn(int typeId, ManageableResource resource) {
/* 189 */     if (this.m_typeIdRelations.containsValue(typeId)) {
/* 190 */       synchronized (this.m_poolMutex) {
/*     */         try {
/* 192 */           this.m_resourcePool[typeId].returnObject(resource);
/* 193 */         } catch (Exception e) {
/* 194 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceContext checkContextOut(int typeId) {
/* 206 */     if (this.m_typeIdRelations.containsValue(typeId)) {
/* 207 */       synchronized (this.m_poolMutex) {
/*     */         try {
/* 209 */           return (ResourceContext)this.m_contextPool[typeId].borrowObject();
/* 210 */         } catch (Exception e) {
/* 211 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkContextIn(int typeId, ResourceContext resource) {
/* 224 */     if (this.m_typeIdRelations.containsValue(typeId)) {
/* 225 */       synchronized (this.m_poolMutex) {
/*     */         try {
/* 227 */           this.m_contextPool[typeId].returnObject(resource);
/* 228 */         } catch (Exception e) {
/* 229 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 240 */     this.m_frameCounter++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     if (!this.m_listenersToRemove.isEmpty()) {
/* 250 */       for (ResourceListener listener : this.m_listenersToRemove) {
/* 251 */         this.m_listeners.remove(listener);
/*     */       }
/* 253 */       this.m_listenersToRemove.clear();
/*     */     } 
/*     */     
/* 256 */     if (!this.m_listenersToAdd.isEmpty()) {
/* 257 */       for (ResourceListener listener : this.m_listenersToAdd) {
/* 258 */         if (!this.m_listeners.contains(listener))
/* 259 */           this.m_listeners.add(listener); 
/*     */       } 
/* 261 */       this.m_listenersToAdd.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     Object[] resourcesToRelease = (Object[])null;
/* 268 */     synchronized (this.m_resourcesToReleaseMutex) {
/* 269 */       resourcesToRelease = this.m_resourcesToRelease.toArray();
/* 270 */       this.m_resourcesToRelease.clear();
/*     */     } 
/*     */     
/* 273 */     if (resourcesToRelease != null) {
/* 274 */       byte b; int i; Object[] arrayOfObject; for (i = (arrayOfObject = resourcesToRelease).length, b = 0; b < i; ) { Object o = arrayOfObject[b];
/* 275 */         ResourceContext context = (ResourceContext)o;
/* 276 */         synchronized (context.getMutex()) {
/* 277 */           context.setLastUseFrame(-2 * this.m_maxResourceAge);
/* 278 */           context.setDestroyResource(true);
/*     */         } 
/*     */ 
/*     */         
/*     */         b++; }
/*     */     
/*     */     } 
/*     */     
/* 286 */     synchronized (this.m_handlersMutex) {
/* 287 */       byte b; int i; Object[] arrayOfObject; for (i = (arrayOfObject = this.m_handlers.values().toArray()).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/* 288 */         ResourceContext context = (ResourceContext)object;
/*     */         
/* 290 */         synchronized (context.getMutex()) {
/* 291 */           int age = this.m_frameCounter - context.getLastUseFrame();
/*     */           
/* 293 */           if (age >= this.m_maxResourceAge && context.isReleasable()) {
/*     */             
/* 295 */             if (!context.isResourceUnloaded()) {
/* 296 */               onUnloadResource(context);
/* 297 */               context.getResource().unloadResource(context);
/* 298 */               context.setResourceUnloaded(true);
/*     */             } 
/*     */             
/* 301 */             if (context.isDestroyResource()) {
/*     */               
/*     */               try {
/* 304 */                 int typeRel = this.m_typeIdRelations.get(context.getTypeId());
/* 305 */                 synchronized (this.m_poolMutex) {
/* 306 */                   synchronized (this.m_handlersMutex) {
/* 307 */                     ManageableResource resource = context.getResource();
/* 308 */                     this.m_resourcePool[typeRel].returnObject(resource);
/* 309 */                     this.m_contextPool[typeRel].returnObject(context);
/* 310 */                     this.m_handlers.remove(resource);
/*     */                   } 
/*     */                 } 
/* 313 */               } catch (Exception e) {
/* 314 */                 e.printStackTrace();
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxResourceAge() {
/* 331 */     return this.m_maxResourceAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxResourceAge(int maxResourceAge) {
/* 340 */     this.m_maxResourceAge = maxResourceAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatistics() {
/* 350 */     long totalBytes = 0L;
/* 351 */     for (ManageableResource resource : this.m_handlers.keySet()) {
/* 352 */       totalBytes += resource.estimateMemoryUsageInBytes();
/*     */     }
/* 354 */     StringBuffer buffer = new StringBuffer();
/*     */     
/* 356 */     buffer.append(getClass().getName()).append(" stats\n"); byte b; int i;
/*     */     ResourceFactoryDescriptor[] arrayOfResourceFactoryDescriptor;
/* 358 */     for (i = (arrayOfResourceFactoryDescriptor = this.m_factories).length, b = 0; b < i; ) { ResourceFactoryDescriptor desc = arrayOfResourceFactoryDescriptor[b];
/* 359 */       buffer.append("\tNb objects out  = ").append(this.m_resourcePool[desc.getTypeId()].getNumActive()).append("\n");
/* 360 */       buffer.append("\tNb objects in   = ").append(this.m_resourcePool[desc.getTypeId()].getNumIdle()).append("\n");
/* 361 */       buffer.append("\tNb contexts out = ").append(this.m_contextPool[desc.getTypeId()].getNumActive()).append("\n");
/* 362 */       buffer.append("\tNb contexts in  = ").append(this.m_contextPool[desc.getTypeId()].getNumIdle()).append("\n");
/* 363 */       buffer.append("\tMemory usage    = ").append((float)totalBytes / 1024000.0F).append(" MByte(s)"); b++; }
/*     */     
/* 365 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCounter() {
/* 374 */     return this.m_frameCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(ResourceListener listener) {
/* 381 */     this.m_listenersToAdd.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(ResourceListener listener) {
/* 388 */     this.m_listenersToRemove.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllListeners() {
/* 395 */     this.m_listenersToRemove.addAll(this.m_listeners);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onResourceReloaded(ResourceContext context) {
/* 400 */     for (ResourceListener listener : this.m_listeners) {
/* 401 */       listener.onResourceContextReloaded(context);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onUnloadResource(ResourceContext context) {
/* 406 */     for (ResourceListener listener : this.m_listeners)
/* 407 */       listener.onUnloadResourceContext(context); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\BaseResourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */