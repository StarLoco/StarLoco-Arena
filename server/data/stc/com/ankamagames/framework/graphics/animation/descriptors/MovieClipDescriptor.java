/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.instances.MovieClip;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.ActionFlag;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefineMovieClip;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.PlaceObject;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.RemoveObject;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.ShowFrame;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import java.util.List;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovieClipDescriptor
/*     */   extends SequenceDescriptor
/*     */ {
/*  27 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*     */     public MovieClip makeObject() {
/*  29 */       return new MovieClip();
/*     */     }
/*  27 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private FrameDescriptor[] m_framesData;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean m_hasActions;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovieClipDescriptor() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovieClipDescriptor(BaseDescriptorLibrary library, DefineMovieClip tag)
/*     */   {
/*  50 */     super(tag.getIdentifier(), tag.getLinkage(), false, library, tag.getFrameCount(), tag.getLoopCount());
/*  51 */     initializeFrames(tag.getTags());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initializeFromTag(DefinitionTag tag)
/*     */   {
/*  61 */     super.initializeFromTag(tag);
/*  62 */     if ((tag instanceof DefineMovieClip)) {
/*  63 */       initializeFrames(((DefineMovieClip)tag).getTags());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initializeFrames(List<Tag> tags)
/*     */   {
/*  74 */     this.m_framesData = new FrameDescriptor[this.m_frameCount];
/*  75 */     for (int i = 0; i < this.m_frameCount; i++) {
/*  76 */       this.m_framesData[i] = new FrameDescriptor();
/*     */     }
/*     */     
/*  79 */     int frame = 0;
/*  80 */     int currentTime = 0;
/*     */     
/*  82 */     for (Tag tag : tags)
/*     */     {
/*  84 */       switch (tag.getCode())
/*     */       {
/*     */       case 1: 
/*  87 */         short duration = (short)((ShowFrame)tag).getDuration();
/*  88 */         if (duration != -1) {
/*  89 */           currentTime += duration;
/*     */         }
/*     */         else {
/*  92 */           currentTime = duration;
/*     */         }
/*     */         
/*  95 */         this.m_frameTime[frame] = currentTime;
/*  96 */         frame++;
/*     */         
/*  98 */         if (frame < this.m_frameCount) {
/*  99 */           this.m_framesData[frame].copy(this.m_framesData[(frame - 1)]);
/*     */         }
/* 101 */         break;
/*     */       
/*     */ 
/*     */       case 5: 
/* 105 */         PlaceObject obj = (PlaceObject)tag;
/* 106 */         int depth = obj.getDepth();
/* 107 */         FrameDataDescriptor oldFrameDataDescr = null;
/* 108 */         if (this.m_framesData[frame].containsDataAt(depth))
/*     */         {
/*     */ 
/* 111 */           oldFrameDataDescr = this.m_framesData[(frame - 1)].getDataAt(depth);
/*     */         }
/* 113 */         this.m_framesData[frame].setAt(depth, new FrameDataDescriptor(obj, oldFrameDataDescr));
/* 114 */         break;
/*     */       
/*     */ 
/*     */       case 6: 
/* 118 */         RemoveObject obj = (RemoveObject)tag;
/* 119 */         this.m_framesData[frame].removeAt(obj.getDepth());
/* 120 */         break;
/*     */       
/*     */ 
/*     */       case 7: 
/* 124 */         this.m_hasActions = true;
/* 125 */         ActionFlag flag = (ActionFlag)tag;
/* 126 */         this.m_framesData[frame].addAction(flag.getAction());
/* 127 */         break;
/*     */       case 2: case 3: 
/*     */       case 4: 
/*     */       default: 
/* 131 */         m_logger.error("Tag inconnu : " + tag.getCode());
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObjectDescriptor.DescriptorType getType()
/*     */   {
/* 144 */     return DisplayObjectDescriptor.DescriptorType.MOVIE_CLIP;
/*     */   }
/*     */   
/*     */   public boolean hasActions() {
/* 148 */     return this.m_hasActions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MovieClip createInstance(AbstractDescriptorLibrary library)
/*     */   {
/*     */     MovieClip movieClip;
/*     */     
/*     */     try
/*     */     {
/* 159 */       MovieClip movieClip = (MovieClip)m_pool.borrowObject();
/* 160 */       movieClip.initialize(m_pool, library, this.m_id, this.m_linkage);
/*     */     } catch (Exception e) {
/* 162 */       movieClip = new MovieClip(library, this.m_id);
/*     */     }
/*     */     
/* 165 */     return movieClip;
/*     */   }
/*     */   
/*     */   public FrameDescriptor getFrameAtIndex(int index) {
/* 169 */     if ((this.m_framesData == null) || (index >= this.m_framesData.length)) {
/* 170 */       return null;
/*     */     }
/* 172 */     return this.m_framesData[index];
/*     */   }
/*     */   
/*     */   public List<String> getActionsAt(int frameIndex) {
/* 176 */     return this.m_framesData[frameIndex].getActions();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovieClipDescriptor duplicate()
/*     */   {
/* 186 */     DefinitionTag definitionTag = getLibrary().getIndexedBuffer().getDefinitionTag(getId());
/* 187 */     if ((definitionTag instanceof DefineMovieClip)) {
/* 188 */       return new MovieClipDescriptor(getLibrary(), (DefineMovieClip)definitionTag);
/*     */     }
/*     */     
/* 191 */     m_logger.trace("duplicate MovieClipDescriptor ne devrait pas arriver " + definitionTag);
/* 192 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 201 */     return String.format("%s %s", new Object[] { "MovieClip", super.toString() });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reloadResource(ResourceContext resourceContext)
/*     */   {
/* 211 */     super.reloadResource(resourceContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unloadResource(ResourceContext resourceContext)
/*     */   {
/* 221 */     super.unloadResource(resourceContext);
/*     */     
/* 223 */     if (this.m_framesData != null) { FrameDescriptor[] arrayOfFrameDescriptor;
/* 224 */       int j = (arrayOfFrameDescriptor = this.m_framesData).length; for (int i = 0; i < j; i++) { FrameDescriptor frame = arrayOfFrameDescriptor[i];
/* 225 */         frame.dispose();
/*     */       }
/* 227 */       this.m_framesData = null;
/* 228 */       this.m_frameTime = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 233 */   static int count = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 242 */     super.onCheckIn();
/* 243 */     this.m_framesData = null;
/* 244 */     this.m_hasActions = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 255 */     super.onCheckOut();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\MovieClipDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */