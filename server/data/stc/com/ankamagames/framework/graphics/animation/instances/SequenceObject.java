/*     */ package com.ankamagames.framework.graphics.animation.instances;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.SequenceDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
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
/*     */ public abstract class SequenceObject
/*     */   extends DisplayObject
/*     */ {
/*     */   private static final int LAST_FRAME_RESET = -1;
/*     */   protected boolean m_terminated;
/*  29 */   private int m_loopCount = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  34 */   protected int m_currentFrameIndex = 0;
/*  35 */   protected int m_lastFrameIndex = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   SequenceObject() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   SequenceObject(AbstractDescriptorLibrary descriptorLibrary, int descriptorId)
/*     */   {
/*  50 */     super(descriptorLibrary, descriptorId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCurrentFrameIndex()
/*     */   {
/*  60 */     return this.m_currentFrameIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCurrentFrameIndex(int frameIndex)
/*     */   {
/*  69 */     this.m_lastFrameIndex = this.m_currentFrameIndex;
/*  70 */     if (this.m_currentFrameIndex != frameIndex) {
/*  71 */       this.m_currentFrameIndex = frameIndex;
/*  72 */       SequenceDescriptor descriptor = getDescriptor();
/*  73 */       if (descriptor != null) {
/*  74 */         setCurrentTime(descriptor.getTime(this.m_currentFrameIndex));
/*     */       } else
/*  76 */         reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getLastFrameIndex() {
/*  81 */     return this.m_lastFrameIndex;
/*     */   }
/*     */   
/*     */   public boolean hasFrameChanged() {
/*  85 */     return this.m_currentFrameIndex != this.m_lastFrameIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/*  95 */     super.onCheckOut();
/*  96 */     reset();
/*  97 */     setAnimationSpeed(1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract SequenceDescriptor getDescriptor();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void incTime(int deltaTime)
/*     */   {
/* 114 */     if (!this.m_terminated) {
/* 115 */       super.incTime(deltaTime);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCurrentTime(long time)
/*     */   {
/* 125 */     super.setCurrentTime(time);
/*     */     
/*     */ 
/* 128 */     SequenceDescriptor descriptor = getDescriptor();
/* 129 */     int frameCount; if ((descriptor != null) && ((frameCount = descriptor.getFrameCount()) > 0)) {
/* 130 */       if (frameCount == 1) {
/* 131 */         this.m_lastFrameIndex = this.m_currentFrameIndex;
/* 132 */         this.m_currentFrameIndex = 0;
/*     */       }
/*     */       else {
/* 135 */         long totalTime = descriptor.getTotalTime();
/* 136 */         if (totalTime > 0L) {
/* 137 */           if (this.m_currentTime >= totalTime) {
/* 138 */             if (isLoop()) {
/* 139 */               this.m_currentTime %= totalTime;
/* 140 */               incLoop();
/*     */             }
/*     */             
/*     */           }
/* 144 */           else if ((this.m_currentTime < 0L) && 
/* 145 */             (isLoop())) {
/* 146 */             this.m_currentTime = (totalTime + this.m_currentTime % totalTime);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 152 */           this.m_terminated = ((this.m_currentTime < 0L) || (this.m_currentTime >= totalTime));
/*     */           
/* 154 */           if (!this.m_terminated) {
/* 155 */             this.m_lastFrameIndex = this.m_currentFrameIndex;
/* 156 */             this.m_currentFrameIndex = descriptor.getFrameIndex(this.m_currentTime);
/*     */           } else {
/* 158 */             this.m_lastFrameIndex = -1;
/* 159 */             this.m_currentFrameIndex = 0;
/*     */           }
/*     */           
/*     */         }
/* 163 */         else if (totalTime < 0L) {
/* 164 */           this.m_lastFrameIndex = this.m_currentFrameIndex;
/* 165 */           if (!descriptor.isInfiniteFrame(this.m_currentFrameIndex)) {
/* 166 */             this.m_currentFrameIndex = descriptor.getFrameIndex(this.m_currentTime);
/*     */           }
/*     */         }
/*     */         else {
/* 170 */           this.m_lastFrameIndex = -1;
/* 171 */           reset();
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 176 */       this.m_terminated = true;
/* 177 */       reset();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void incLoop() {
/* 182 */     this.m_lastFrameIndex = -1;
/* 183 */     this.m_loopCount += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean isLoop()
/*     */   {
/* 190 */     SequenceDescriptor descriptor = getDescriptor();
/* 191 */     if (descriptor != null) {
/* 192 */       int loopCount = descriptor.getLoopCount();
/* 193 */       return (loopCount == 0) || (this.m_loopCount < loopCount - 1);
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void swapPause()
/*     */   {
/* 203 */     setPause(!this.m_pause);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void incAnimationSpeed(int percent)
/*     */   {
/* 213 */     this.m_timeSpeed *= (percent + 100) / 100.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void decAnimationSpeed(int percent)
/*     */   {
/* 222 */     this.m_timeSpeed *= (100 - percent) / 100.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 229 */     this.m_mesh.removeAllChilds();
/* 230 */     this.m_currentFrameIndex = 0;
/* 231 */     this.m_lastFrameIndex = -1;
/* 232 */     this.m_currentTime = 0L;
/* 233 */     this.m_terminated = false;
/* 234 */     this.m_pause = false;
/* 235 */     this.m_loopCount = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAnimated()
/*     */   {
/* 244 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void nextFrame()
/*     */   {
/* 251 */     int frame = this.m_currentFrameIndex + 1;
/* 252 */     if (frame >= getDescriptor().getFrameCount())
/* 253 */       frame = getDescriptor().getFrameCount() - 1;
/* 254 */     setCurrentFrameIndex(frame);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void prevFrame()
/*     */   {
/* 261 */     int frame = this.m_currentFrameIndex - 1;
/* 262 */     if (frame < 0)
/* 263 */       frame = 0;
/* 264 */     setCurrentFrameIndex(frame);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\instances\SequenceObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */