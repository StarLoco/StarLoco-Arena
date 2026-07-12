/*     */ package com.ankamagames.framework.graphics.animation.descriptors.library;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor.DisplayObjectDescriptorContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceListener;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import gnu.trove.TObjectIntHashMap;
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
/*  54 */   private boolean m_saveOldDefinitions = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ModifiableDescriptorLibrary(BaseDescriptorLibrary parent)
/*     */   {
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
/*     */   public void onUnloadResource(ResourceContext resourceContexts)
/*     */   {
/*  81 */     super.onUnloadResource(resourceContexts);
/*  82 */     this.m_oldDefinitions.remove(((DisplayObjectDescriptor.DisplayObjectDescriptorContext)resourceContexts).getIdentifier());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUnloadResourceContext(ResourceContext resourceContexts)
/*     */   {
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
/*     */   public void onResourceContextReloaded(ResourceContext resourceContexts) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseDescriptorLibrary getParent()
/*     */   {
/* 109 */     return this.m_parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TIntArrayList getIdentifiers()
/*     */   {
/* 116 */     return this.m_parent.getIndexedBuffer().getIdentifiers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Set<String> getLinkages()
/*     */   {
/* 123 */     return this.m_parent.getIndexedBuffer().getLinkages();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getChangeRevision()
/*     */   {
/* 130 */     return this.m_changeRevision;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isSaveOldDefinitions()
/*     */   {
/* 137 */     return this.m_saveOldDefinitions;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void ensureParentAvailability()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 233	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parent	Lcom/ankamagames/framework/graphics/animation/descriptors/library/BaseDescriptorLibrary;
/*     */     //   4: ifnonnull +149 -> 153
/*     */     //   7: aload_0
/*     */     //   8: invokestatic 262	com/ankamagames/framework/graphics/animation/descriptors/library/DescriptorLibraryManager:getInstance	()Lcom/ankamagames/framework/graphics/animation/descriptors/library/DescriptorLibraryManager;
/*     */     //   11: aload_0
/*     */     //   12: getfield 237	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parentDescriptorLibraryName	Ljava/lang/String;
/*     */     //   15: invokevirtual 264	com/ankamagames/framework/graphics/animation/descriptors/library/DescriptorLibraryManager:getDescriptorLibrary	(Ljava/lang/String;)Lcom/ankamagames/framework/graphics/animation/descriptors/library/BaseDescriptorLibrary;
/*     */     //   18: putfield 233	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parent	Lcom/ankamagames/framework/graphics/animation/descriptors/library/BaseDescriptorLibrary;
/*     */     //   21: invokestatic 262	com/ankamagames/framework/graphics/animation/descriptors/library/DescriptorLibraryManager:getInstance	()Lcom/ankamagames/framework/graphics/animation/descriptors/library/DescriptorLibraryManager;
/*     */     //   24: aload_0
/*     */     //   25: invokevirtual 263	com/ankamagames/framework/graphics/animation/descriptors/library/DescriptorLibraryManager:addListener	(Lcom/ankamagames/framework/kernel/core/resource/ResourceListener;)V
/*     */     //   28: goto +88 -> 116
/*     */     //   31: astore_1
/*     */     //   32: aload_1
/*     */     //   33: invokevirtual 282	java/lang/Exception:printStackTrace	()V
/*     */     //   36: aload_0
/*     */     //   37: getfield 233	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parent	Lcom/ankamagames/framework/graphics/animation/descriptors/library/BaseDescriptorLibrary;
/*     */     //   40: ifnonnull +113 -> 153
/*     */     //   43: getstatic 239	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   46: new 120	java/lang/StringBuilder
/*     */     //   49: dup
/*     */     //   50: ldc 3
/*     */     //   52: invokespecial 284	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   55: aload_0
/*     */     //   56: getfield 237	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parentDescriptorLibraryName	Ljava/lang/String;
/*     */     //   59: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   62: ldc 2
/*     */     //   64: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   67: invokevirtual 283	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   70: invokevirtual 291	org/apache/log4j/Logger:error	(Ljava/lang/Object;)V
/*     */     //   73: goto +80 -> 153
/*     */     //   76: astore_2
/*     */     //   77: aload_0
/*     */     //   78: getfield 233	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parent	Lcom/ankamagames/framework/graphics/animation/descriptors/library/BaseDescriptorLibrary;
/*     */     //   81: ifnonnull +33 -> 114
/*     */     //   84: getstatic 239	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   87: new 120	java/lang/StringBuilder
/*     */     //   90: dup
/*     */     //   91: ldc 3
/*     */     //   93: invokespecial 284	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   96: aload_0
/*     */     //   97: getfield 237	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parentDescriptorLibraryName	Ljava/lang/String;
/*     */     //   100: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   103: ldc 2
/*     */     //   105: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   108: invokevirtual 283	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   111: invokevirtual 291	org/apache/log4j/Logger:error	(Ljava/lang/Object;)V
/*     */     //   114: aload_2
/*     */     //   115: athrow
/*     */     //   116: aload_0
/*     */     //   117: getfield 233	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parent	Lcom/ankamagames/framework/graphics/animation/descriptors/library/BaseDescriptorLibrary;
/*     */     //   120: ifnonnull +33 -> 153
/*     */     //   123: getstatic 239	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_logger	Lorg/apache/log4j/Logger;
/*     */     //   126: new 120	java/lang/StringBuilder
/*     */     //   129: dup
/*     */     //   130: ldc 3
/*     */     //   132: invokespecial 284	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   135: aload_0
/*     */     //   136: getfield 237	com/ankamagames/framework/graphics/animation/descriptors/library/ModifiableDescriptorLibrary:m_parentDescriptorLibraryName	Ljava/lang/String;
/*     */     //   139: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   142: ldc 2
/*     */     //   144: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   147: invokevirtual 283	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   150: invokevirtual 291	org/apache/log4j/Logger:error	(Ljava/lang/Object;)V
/*     */     //   153: return
/*     */     // Line number table:
/*     */     //   Java source line #144	-> byte code offset #0
/*     */     //   Java source line #146	-> byte code offset #7
/*     */     //   Java source line #147	-> byte code offset #21
/*     */     //   Java source line #148	-> byte code offset #31
/*     */     //   Java source line #149	-> byte code offset #32
/*     */     //   Java source line #151	-> byte code offset #36
/*     */     //   Java source line #152	-> byte code offset #43
/*     */     //   Java source line #150	-> byte code offset #76
/*     */     //   Java source line #151	-> byte code offset #77
/*     */     //   Java source line #152	-> byte code offset #84
/*     */     //   Java source line #154	-> byte code offset #114
/*     */     //   Java source line #151	-> byte code offset #116
/*     */     //   Java source line #152	-> byte code offset #123
/*     */     //   Java source line #156	-> byte code offset #153
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	154	0	this	ModifiableDescriptorLibrary
/*     */     //   31	2	1	e	Exception
/*     */     //   76	39	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	28	31	java/lang/Exception
/*     */     //   7	36	76	finally
/*     */   }
/*     */   
/*     */   public void setSaveOldDefinitions(boolean saveOldDefinitions)
/*     */   {
/* 162 */     this.m_saveOldDefinitions = saveOldDefinitions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(String linkage)
/*     */   {
/* 172 */     boolean result = super.contains(linkage);
/* 173 */     if (!result) {
/* 174 */       ensureParentAvailability();
/* 175 */       return (this.m_parent != null) && (this.m_parent.contains(linkage));
/*     */     }
/* 177 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getIdFromLinkage(String linkage)
/*     */   {
/* 187 */     int id = super.getIdFromLinkage(linkage);
/* 188 */     if (id == 0) {
/* 189 */       ensureParentAvailability();
/* 190 */       return this.m_parent != null ? this.m_parent.getIdFromLinkage(linkage) : 0;
/*     */     }
/* 192 */     return id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObjectDescriptor getDescriptor(int id)
/*     */   {
/* 202 */     DisplayObjectDescriptor descriptor = super.getDescriptor(id);
/* 203 */     if (descriptor == null) {
/* 204 */       ensureParentAvailability();
/* 205 */       descriptor = this.m_parent != null ? this.m_parent.getDescriptor(id) : null;
/*     */     }
/* 207 */     return descriptor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObjectDescriptor getDescriptor(String linkage)
/*     */   {
/* 217 */     DisplayObjectDescriptor descriptor = super.getDescriptor(linkage);
/* 218 */     if (descriptor == null) {
/* 219 */       ensureParentAvailability();
/* 220 */       descriptor = this.m_parent != null ? this.m_parent.getDescriptor(linkage) : null;
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
/*     */   public DisplayObjectDescriptor setDescriptor(int id, DisplayObjectDescriptor srcDescriptor)
/*     */   {
/* 233 */     incContentChangeRevision();
/*     */     
/*     */ 
/*     */ 
/* 237 */     if (srcDescriptor == null) {
/* 238 */       if ((this.m_saveOldDefinitions) && (this.m_oldDefinitions.containsKey(id))) {
/* 239 */         this.m_definitions.put(id, (DisplayObjectDescriptor)this.m_oldDefinitions.remove(id));
/*     */       } else {
/* 241 */         this.m_definitions.remove(id);
/*     */       }
/*     */     }
/*     */     else {
/* 245 */       if ((this.m_saveOldDefinitions) && (this.m_definitions.containsKey(id))) {
/* 246 */         this.m_oldDefinitions.put(id, (DisplayObjectDescriptor)this.m_definitions.get(id));
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
/*     */   public DisplayObjectDescriptor setDescriptor(String linkage, DisplayObjectDescriptor srcDescriptor)
/*     */   {
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
/*     */   public ArrayList<DisplayObjectDescriptor> getDefinitions()
/*     */   {
/* 296 */     ensureParentAvailability();
/* 297 */     ArrayList<DisplayObjectDescriptor> definitions = new ArrayList();
/* 298 */     TIntObjectIterator<DisplayObjectDescriptor> iterator = this.m_parent.iterator();
/* 299 */     for (int i = this.m_parent.size(); i-- > 0;) {
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
/*     */   public void setMaterial(String linkage, Material material)
/*     */   {
/* 315 */     if (this.m_materialLinked == null) {
/* 316 */       this.m_materialLinked = new HashMap();
/*     */     }
/* 318 */     this.m_materialLinked.put(linkage, material);
/* 319 */     incContentChangeRevision();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Material getMaterial(String linkage)
/*     */   {
/*     */     try
/*     */     {
/* 330 */       return (Material)this.m_materialLinked.get(linkage);
/*     */     } catch (Exception ex) {}
/* 332 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void incContentChangeRevision()
/*     */   {
/* 340 */     if (this.m_changeRevision == Integer.MAX_VALUE) {
/* 341 */       this.m_changeRevision = 0;
/*     */     }
/* 343 */     this.m_changeRevision += 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\library\ModifiableDescriptorLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */