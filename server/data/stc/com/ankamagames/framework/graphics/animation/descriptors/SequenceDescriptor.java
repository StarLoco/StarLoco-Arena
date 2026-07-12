/*     */ package com.ankamagames.framework.graphics.animation.descriptors;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefineSequence;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
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
/*     */ public abstract class SequenceDescriptor
/*     */   extends DisplayObjectDescriptor
/*     */ {
/*     */   public static final int INVALID_FRAME = -1;
/*     */   public static final int INFINITE_LOOP = 0;
/*  25 */   protected int m_frameCount = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int[] m_frameTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int m_loopCount;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SequenceDescriptor()
/*     */   {
/*  42 */     super(-1, null);
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
/*     */   public SequenceDescriptor(int id, String linkage, boolean virtual, BaseDescriptorLibrary library, int frameCount, int loopCount)
/*     */   {
/*  56 */     this(id, linkage, virtual, library, frameCount, loopCount, new int[frameCount]);
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
/*     */   public SequenceDescriptor(int id, String linkage, boolean virtual, BaseDescriptorLibrary library, int frameCount, int loopCount, int[] frameTime)
/*     */   {
/*  71 */     super(id, linkage, virtual, library);
/*  72 */     this.m_frameCount = frameCount;
/*  73 */     this.m_loopCount = loopCount;
/*  74 */     this.m_frameTime = frameTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initializeFromTag(DefinitionTag tag)
/*     */   {
/*  84 */     super.initializeFromTag(tag);
/*  85 */     if ((tag instanceof DefineSequence)) {
/*  86 */       DefineSequence sequenceTag = (DefineSequence)tag;
/*  87 */       this.m_frameCount = sequenceTag.getFrameCount();
/*  88 */       this.m_loopCount = sequenceTag.getLoopCount();
/*  89 */       this.m_frameTime = new int[this.m_frameCount];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFrameCount()
/*     */   {
/*  97 */     return this.m_frameCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getLoopCount()
/*     */   {
/* 104 */     return this.m_loopCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTotalTime()
/*     */   {
/* 111 */     if (this.m_frameCount == 0)
/* 112 */       return 0;
/* 113 */     return this.m_frameTime[(this.m_frameCount - 1)];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getFrameIndex(long time)
/*     */   {
/* 123 */     int frameIndex = 0;
/*     */     
/* 125 */     if ((this.m_frameTime != null) && (this.m_frameTime.length != 0)) {
/* 126 */       while (time >= this.m_frameTime[frameIndex]) {
/* 127 */         frameIndex++;
/*     */         
/* 129 */         if (frameIndex > this.m_frameCount - 1) {
/* 130 */           if (this.m_frameTime[(this.m_frameCount - 1)] == -1) {
/* 131 */             return this.m_frameCount - 1;
/*     */           }
/* 133 */           return 0;
/*     */         }
/*     */         
/*     */       }
/*     */     } else {
/* 138 */       frameIndex = -1;
/*     */     }
/*     */     
/* 141 */     return frameIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getTime(int frameIndex)
/*     */   {
/* 150 */     if (frameIndex == 0) {
/* 151 */       return 0L;
/*     */     }
/* 153 */     if ((frameIndex < 0) || (frameIndex >= this.m_frameCount)) {
/* 154 */       return 0L;
/*     */     }
/* 156 */     return this.m_frameTime[(frameIndex - 1)];
/*     */   }
/*     */   
/*     */   public boolean isInfiniteFrame(int currentFrameIndex) {
/* 160 */     return this.m_frameTime[currentFrameIndex] == -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\descriptors\SequenceDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */