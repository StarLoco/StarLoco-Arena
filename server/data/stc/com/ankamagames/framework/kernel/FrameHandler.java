/*     */ package com.ankamagames.framework.kernel;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class FrameHandler
/*     */   implements MessageHandler
/*     */ {
/*  22 */   protected static final Logger m_logger = Logger.getLogger(FrameHandler.class);
/*     */   
/*  24 */   private final ArrayList<MessageFrame> m_frames = new ArrayList();
/*  25 */   private final ArrayList<MessageFrame> m_framesToRemoveBefore = new ArrayList();
/*  26 */   private final ArrayList<MessageFrame> m_framesToAddBefore = new ArrayList();
/*  27 */   private final ArrayList<MessageFrame> m_framesToRemoveAfter = new ArrayList();
/*  28 */   private final ArrayList<MessageFrame> m_framesToAddAfter = new ArrayList();
/*     */   
/*     */   private boolean m_runningFrame;
/*     */   
/*     */   private long m_id;
/*     */   
/*     */ 
/*     */   public FrameHandler()
/*     */   {
/*  37 */     this.m_id = 0L;
/*  38 */     this.m_runningFrame = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/*  47 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id)
/*     */   {
/*  56 */     this.m_id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  67 */     boolean retFlag = true;
/*  68 */     preFrameExecutionSetup();
/*     */     
/*  70 */     synchronized (this.m_frames) {
/*  71 */       this.m_runningFrame = true;
/*  72 */       for (MessageFrame frame : this.m_frames) {
/*  73 */         if (frame != null) {
/*     */           try {
/*  75 */             retFlag = frame.onMessage(message);
/*     */           } catch (Exception e) {
/*  77 */             e.printStackTrace();
/*     */           }
/*  79 */           if (!retFlag)
/*     */             break;
/*     */         }
/*     */       }
/*  83 */       this.m_runningFrame = false;
/*     */     }
/*  85 */     if (retFlag) {
/*  86 */       m_logger.error("Message (" + message.getClass().toString() + ") non traité, de type " + message.getId());
/*     */     }
/*  88 */     postFrameExecutionSetup();
/*     */     
/*  90 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void preFrameExecutionSetup()
/*     */   {
/*  99 */     synchronized (this.m_frames) {
/* 100 */       synchronized (this.m_framesToAddBefore) {
/* 101 */         for (MessageFrame frame : this.m_framesToAddBefore) {
/* 102 */           if (!this.m_frames.contains(frame)) {
/* 103 */             this.m_frames.add(0, frame);
/* 104 */             frame.onFrameAdd(this, false);
/*     */           }
/*     */         }
/* 107 */         this.m_framesToAddBefore.clear();
/*     */       }
/* 109 */       synchronized (this.m_framesToRemoveBefore) {
/* 110 */         for (MessageFrame frame : this.m_framesToRemoveBefore) {
/* 111 */           if (this.m_frames.remove(frame))
/* 112 */             frame.onFrameRemove(this, false);
/*     */         }
/* 114 */         this.m_framesToRemoveBefore.clear();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void postFrameExecutionSetup()
/*     */   {
/* 125 */     synchronized (this.m_frames) {
/* 126 */       synchronized (this.m_framesToAddAfter) {
/* 127 */         for (MessageFrame frame : this.m_framesToAddAfter) {
/* 128 */           if (!this.m_frames.contains(frame)) {
/* 129 */             this.m_frames.add(0, frame);
/* 130 */             frame.onFrameAdd(this, false);
/*     */           }
/*     */         }
/* 133 */         this.m_framesToAddAfter.clear();
/*     */       }
/* 135 */       synchronized (this.m_framesToRemoveAfter) {
/* 136 */         for (MessageFrame frame : this.m_framesToRemoveAfter) {
/* 137 */           if (this.m_frames.remove(frame))
/* 138 */             frame.onFrameRemove(this, false);
/*     */         }
/* 140 */         this.m_framesToRemoveAfter.clear();
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
/*     */   public void pushFrame(MessageFrame frame)
/*     */   {
/* 153 */     if (this.m_runningFrame)
/*     */     {
/*     */       boolean bRemoveAfter;
/* 156 */       synchronized (this.m_framesToRemoveAfter) {
/* 157 */         bRemoveAfter = this.m_framesToRemoveAfter.contains(frame);
/*     */       }
/*     */       
/*     */       boolean bRemoveAfter;
/* 161 */       if (bRemoveAfter) {
/* 162 */         synchronized (this.m_framesToAddBefore) {
/* 163 */           if (!this.m_framesToAddBefore.contains(frame))
/* 164 */             this.m_framesToAddBefore.add(frame);
/* 165 */           frame.onFrameAdd(this, true);
/*     */         }
/* 167 */         synchronized (this.m_framesToRemoveBefore) {
/* 168 */           this.m_framesToRemoveBefore.remove(frame);
/*     */         }
/*     */       }
/* 171 */       synchronized (this.m_framesToAddAfter) {
/* 172 */         this.m_framesToAddAfter.add(frame);
/* 173 */         frame.onFrameAdd(this, true);
/*     */       }
/*     */     }
/*     */     
/* 177 */     synchronized (this.m_frames) {
/* 178 */       this.m_frames.add(0, frame);
/* 179 */       frame.onFrameAdd(this, false);
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
/*     */   public void removeFrame(MessageFrame frame)
/*     */   {
/* 192 */     if (this.m_runningFrame)
/*     */     {
/*     */       boolean bAddAfter;
/* 195 */       synchronized (this.m_framesToAddAfter) {
/* 196 */         bAddAfter = this.m_framesToAddAfter.contains(frame);
/*     */       }
/*     */       boolean bAddAfter;
/* 199 */       if (bAddAfter) {
/* 200 */         synchronized (this.m_framesToRemoveBefore) {
/* 201 */           if (!this.m_framesToRemoveBefore.contains(frame))
/* 202 */             this.m_framesToRemoveBefore.add(frame);
/*     */         }
/* 204 */         synchronized (this.m_framesToAddBefore) {
/* 205 */           if (this.m_framesToAddBefore.remove(frame))
/* 206 */             frame.onFrameRemove(this, false);
/*     */         }
/*     */       }
/* 209 */       synchronized (this.m_framesToRemoveAfter) {
/* 210 */         this.m_framesToRemoveAfter.add(frame);
/*     */       }
/*     */     }
/*     */     
/* 214 */     synchronized (this.m_frames) {
/* 215 */       if (this.m_frames.remove(frame)) {
/* 216 */         frame.onFrameRemove(this, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeAllFrames()
/*     */   {
/*     */     Object frameObject;
/*     */     
/* 226 */     if (this.m_runningFrame) {
/* 227 */       Object[] frames = (Object[])null;
/* 228 */       synchronized (this.m_frames) {
/* 229 */         frames = this.m_frames.toArray();
/*     */       }
/* 231 */       if (frames != null) {
/* 232 */         synchronized (this.m_framesToRemoveAfter) { Object[] arrayOfObject1;
/* 233 */           int j = (arrayOfObject1 = frames).length; for (int i = 0; i < j; i++) { frameObject = arrayOfObject1[i];
/* 234 */             MessageFrame frame = (MessageFrame)frameObject;
/* 235 */             if (!this.m_framesToRemoveAfter.contains(frame)) {
/* 236 */               this.m_framesToRemoveAfter.add(frame);
/* 237 */               frame.onFrameRemove(this, true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     } else {
/* 243 */       synchronized (this.m_frames) {
/* 244 */         for (frameObject = this.m_frames.iterator(); ((Iterator)frameObject).hasNext();) { MessageFrame frame = (MessageFrame)((Iterator)frameObject).next();
/* 245 */           frame.onFrameRemove(this, true); }
/* 246 */         this.m_frames.clear();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasFrame(MessageFrame frame)
/*     */   {
/*     */     boolean bHashFrame;
/*     */     
/*     */     boolean bHashFrame;
/*     */     
/* 258 */     if (this.m_runningFrame) {
/* 259 */       bHashFrame = this.m_frames.contains(frame);
/*     */     } else { boolean bHashFrame;
/* 261 */       synchronized (this.m_frames) {
/* 262 */         bHashFrame = this.m_frames.contains(frame);
/*     */       }
/* 264 */       synchronized (this.m_framesToAddBefore) {
/* 265 */         bHashFrame &= this.m_framesToAddBefore.contains(frame);
/*     */       }
/* 267 */       synchronized (this.m_framesToAddAfter) {
/* 268 */         bHashFrame &= this.m_framesToAddAfter.contains(frame);
/*     */       }
/*     */     }
/*     */     
/* 272 */     return bHashFrame;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isRunningFrame()
/*     */   {
/* 279 */     return this.m_runningFrame;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRunningFrame(boolean runningFrame)
/*     */   {
/* 286 */     this.m_runningFrame = runningFrame;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<MessageFrame> getFrames()
/*     */   {
/* 293 */     return this.m_frames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<MessageFrame> getFramesToRemoveBefore()
/*     */   {
/* 300 */     return this.m_framesToRemoveBefore;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<MessageFrame> getFramesToAddBefore()
/*     */   {
/* 307 */     return this.m_framesToAddBefore;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<MessageFrame> getFramesToRemoveAfter()
/*     */   {
/* 314 */     return this.m_framesToRemoveAfter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<MessageFrame> getFramesToAddAfter()
/*     */   {
/* 321 */     return this.m_framesToAddAfter;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\FrameHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */