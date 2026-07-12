/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.instances.BitmapSequence;
/*     */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*     */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*     */ import com.ankamagames.framework.graphics.sba.records.BitmapFrame;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefineBitmapSequence;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BitmapSequenceDescriptor
/*     */   extends SequenceDescriptor
/*     */ {
/*  28 */   private static final ObjectPool m_pool = new MonitoredPool(new ObjectFactory() {
/*     */     public BitmapSequence makeObject() {
/*  30 */       return new BitmapSequence();
/*     */     }
/*  28 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private BitmapDescriptor[] m_framesData;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  38 */   private float m_invertScalingValue = 1.0F;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapSequenceDescriptor() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapSequenceDescriptor(BaseDescriptorLibrary library, DefineBitmapSequence tag)
/*     */   {
/*  54 */     super(tag.getIdentifier(), tag.getLinkage(), false, library, tag.getFrameCount(), tag.getLoopCount());
/*  55 */     initializeFrames(tag.getBitmapFrames(), tag.getInvertScalingValue());
/*  56 */     this.m_invertScalingValue = tag.getInvertScalingValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initializeFromTag(DefinitionTag tag)
/*     */   {
/*  66 */     super.initializeFromTag(tag);
/*  67 */     if ((tag instanceof DefineBitmapSequence)) {
/*  68 */       initializeFrames(((DefineBitmapSequence)tag).getBitmapFrames(), ((DefineBitmapSequence)tag).getInvertScalingValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initializeFrames(ArrayList<BitmapFrame> frames, float invertScalingValue)
/*     */   {
/*  78 */     this.m_framesData = new BitmapDescriptor[this.m_frameCount];
/*  79 */     this.m_invertScalingValue = invertScalingValue;
/*  80 */     int i = 0;
/*  81 */     for (BitmapFrame currentFrame : frames) {
/*  82 */       int duration = currentFrame.getDuration();
/*  83 */       this.m_frameTime[i] = duration;
/*  84 */       if (duration != -1) {
/*  85 */         this.m_frameTime[i] += (i == 0 ? 0 : this.m_frameTime[(i - 1)]);
/*     */       }
/*  87 */       this.m_framesData[i] = new BitmapDescriptor(currentFrame, this.m_invertScalingValue);
/*  88 */       i++;
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
/*  99 */     return DisplayObjectDescriptor.DescriptorType.BITMAP_SEQUENCE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapDescriptor getFrame(int frameIndex)
/*     */   {
/* 108 */     if (this.m_framesData == null)
/* 109 */       return null;
/* 110 */     return this.m_framesData[frameIndex];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getInvertScalingValue()
/*     */   {
/* 117 */     return this.m_invertScalingValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DisplayObject createInstance(AbstractDescriptorLibrary library)
/*     */   {
/*     */     BitmapSequence bitmapSequence;
/*     */     
/*     */     try
/*     */     {
/* 128 */       BitmapSequence bitmapSequence = (BitmapSequence)m_pool.borrowObject();
/* 129 */       bitmapSequence.initialize(m_pool, library, this.m_id, this.m_linkage);
/*     */     } catch (Exception e) {
/* 131 */       bitmapSequence = new BitmapSequence(library, this.m_id);
/*     */     }
/* 133 */     return bitmapSequence;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitmapSequenceDescriptor duplicate()
/*     */   {
/* 143 */     DefinitionTag definitionTag = getLibrary().getIndexedBuffer().getDefinitionTag(getId());
/* 144 */     if ((definitionTag instanceof DefineBitmapSequence)) {
/* 145 */       return new BitmapSequenceDescriptor(getLibrary(), (DefineBitmapSequence)definitionTag);
/*     */     }
/* 147 */     m_logger.trace("duplicate BitmapSequenceDescriptor ne devrait pas arriver " + definitionTag);
/* 148 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 157 */     return String.format("%s %s", new Object[] { "BitmapSequence", super.toString() });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unloadResource(ResourceContext resourceContext)
/*     */   {
/* 166 */     super.unloadResource(resourceContext);
/* 167 */     if (this.m_framesData != null) { BitmapDescriptor[] arrayOfBitmapDescriptor;
/* 168 */       int j = (arrayOfBitmapDescriptor = this.m_framesData).length; for (int i = 0; i < j; i++) { BitmapDescriptor frameData = arrayOfBitmapDescriptor[i];
/* 169 */         frameData.unloadResource(null);
/*     */       }
/* 171 */       this.m_framesData = null;
/* 172 */       this.m_frameTime = null;
/* 173 */       this.m_frameCount = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/* 185 */     super.onCheckIn();
/* 186 */     if (this.m_framesData != null) { BitmapDescriptor[] arrayOfBitmapDescriptor;
/* 187 */       int j = (arrayOfBitmapDescriptor = this.m_framesData).length; for (int i = 0; i < j; i++) { BitmapDescriptor frameData = arrayOfBitmapDescriptor[i];
/* 188 */         frameData.unloadResource(null);
/*     */       }
/* 190 */       this.m_framesData = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 199 */     super.onCheckOut();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\BitmapSequenceDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */