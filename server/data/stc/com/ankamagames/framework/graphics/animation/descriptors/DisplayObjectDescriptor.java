/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
/*     */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DisplayObjectDescriptor
/*     */   implements ManageableResource
/*     */ {
/*  26 */   protected static final Logger m_logger = Logger.getLogger(DisplayObjectDescriptor.class);
/*     */   protected int m_id;
/*     */   protected String m_linkage;
/*     */   
/*     */   public static class DisplayObjectDescriptorContext
/*     */     extends ResourceContext
/*     */   {
/*     */     private int m_identifier;
/*     */     
/*     */     public int getIdentifier()
/*     */     {
/*  37 */       return this.m_identifier;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void setIdentifier(int identifier)
/*     */     {
/*  44 */       this.m_identifier = identifier;
/*     */     }
/*     */     
/*     */     public void onCheckIn()
/*     */     {
/*  49 */       super.onCheckIn();
/*  50 */       this.m_identifier = -1;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isReleasable()
/*     */     {
/*  56 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum DescriptorType
/*     */   {
/*  64 */     MOVIE_CLIP,  BITMAP,  BITMAP_SEQUENCE;
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
/*  80 */   protected boolean m_virtual = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */   protected String m_libraryName = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ModifiableDescriptorLibrary m_virtualLibrary;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected DisplayObjectDescriptor(int id, String linkage)
/*     */   {
/* 102 */     this.m_id = id;
/* 103 */     this.m_linkage = linkage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected DisplayObjectDescriptor(int id, String linkage, boolean virtual, BaseDescriptorLibrary library)
/*     */   {
/* 115 */     this(id, linkage);
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
/*     */   public void initializeFromTag(DefinitionTag tag)
/*     */   {
/* 128 */     setId(tag.getIdentifier());
/* 129 */     setLinkage(tag.getLinkage());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DescriptorType getType();
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasLinkage()
/*     */   {
/* 141 */     return (this.m_linkage != null) && (!this.m_linkage.equals(""));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLinkage(String linkage)
/*     */   {
/* 148 */     this.m_linkage = linkage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLinkage()
/*     */   {
/* 155 */     if (this.m_linkage == null) {
/* 156 */       return "";
/*     */     }
/* 158 */     return this.m_linkage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/* 165 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(int id)
/*     */   {
/* 175 */     this.m_id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isVirtual()
/*     */   {
/* 182 */     return this.m_virtual;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVirtual(boolean virtuel)
/*     */   {
/* 189 */     this.m_virtual = virtuel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLibrary(BaseDescriptorLibrary library)
/*     */   {
/* 196 */     if ((this.m_virtual) || (this.m_libraryName == null)) {
/* 197 */       this.m_libraryName = library.getName();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalTime()
/*     */   {
/* 204 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setVirtualLibrary(ModifiableDescriptorLibrary virtualLibrary)
/*     */   {
/* 211 */     if (this.m_virtual) {
/* 212 */       this.m_virtualLibrary = virtualLibrary;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BaseDescriptorLibrary getLibrary()
/*     */   {
/* 220 */     return DescriptorLibraryManager.getInstance().getDescriptorLibrary(this.m_libraryName);
/*     */   }
/*     */   
/*     */   public String getLibraryName()
/*     */   {
/* 225 */     return this.m_libraryName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Material getMaterial()
/*     */   {
/* 232 */     if (this.m_virtual) {
/* 233 */       return this.m_virtualLibrary.getMaterial(this.m_linkage);
/*     */     }
/* 235 */     return getLibrary().getMaterial(this.m_linkage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMaterial(Material material)
/*     */   {
/* 242 */     getLibrary().setMaterial(this.m_linkage, material);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DisplayObject createInstance(AbstractDescriptorLibrary paramAbstractDescriptorLibrary);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract DisplayObjectDescriptor duplicate();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 261 */     return String.format("LINK='%s' ID=%d", new Object[] { hasLinkage() ? this.m_linkage : "", Integer.valueOf(this.m_id) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long estimateMemoryUsageInBytes()
/*     */   {
/* 270 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reloadResource(ResourceContext resourceContext)
/*     */   {
/* 279 */     if (resourceContext.isResourceUnloaded()) {
/* 280 */       DisplayObjectDescriptorContext context = (DisplayObjectDescriptorContext)resourceContext;
/* 281 */       BaseDescriptorLibrary library = ((DisplayObjectDescriptor)context.getResource()).getLibrary();
/* 282 */       if (library == null) {
/* 283 */         System.err.println("DisplayObjectDescriptor.reloadResource : library = null");
/*     */       }
/* 285 */       else if (library.getIndexedBuffer() == null) {
/* 286 */         System.err.println("DisplayObjectDescriptor.reloadResource : library.indexedBuffer = null");
/*     */       } else {
/* 288 */         initializeFromTag(library.getIndexedBuffer().getDefinitionTag(context.getIdentifier()));
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
/*     */   public void unloadResource(ResourceContext resourceContext) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 311 */     this.m_id = 0;
/* 312 */     this.m_linkage = null;
/* 313 */     this.m_libraryName = null;
/* 314 */     this.m_virtualLibrary = null;
/* 315 */     this.m_virtual = false;
/*     */   }
/*     */   
/*     */   public void onCheckOut() {}
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\DisplayObjectDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */