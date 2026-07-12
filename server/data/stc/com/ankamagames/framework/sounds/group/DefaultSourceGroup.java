/*     */ package com.ankamagames.framework.sounds.group;
/*     */ 
/*     */ import com.ankamagames.framework.sounds.AudioSource;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class DefaultSourceGroup
/*     */   extends AudioSourceGroup
/*     */ {
/*  21 */   private final ArrayList<AudioSource> m_sources = new ArrayList();
/*  22 */   private final Object m_sourcesMutex = new Object();
/*     */   private String m_soundFilesBasePath;
/*     */   private String m_soundFilesExtension;
/*     */   
/*     */   public DefaultSourceGroup(String name)
/*     */   {
/*  28 */     super(name);
/*     */     
/*  30 */     setSourceProvider(SoundManager.getInstance());
/*  31 */     setBufferProvider(SoundManager.getInstance());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<AudioSource> getSources()
/*     */   {
/*  40 */     return this.m_sources;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addSource(String fileName, boolean bStreaming, boolean bStereo) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void playSound(String fileName, boolean bStreaming, boolean bStereo, boolean bLoop)
/*     */   {
/*  54 */     synchronized (this.m_sourcesMutex) {
/*  55 */       AudioSource s = new AudioSource(fileName, bStreaming, getSourceProvider(), getBufferProvider(), bStereo, bLoop);
/*     */       
/*  57 */       s.setMaxGain(getMaxGain());
/*  58 */       s.setLoop(bLoop);
/*  59 */       s.play();
/*     */       
/*  61 */       this.m_sources.add(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public void playSound(int fileId, boolean bStreaming, boolean bStereo, boolean bLoop)
/*     */   {
/*  67 */     byte[] ext = this.m_soundFilesExtension.getBytes();
/*     */     
/*  69 */     playSound(
/*  70 */       this.m_soundFilesBasePath + fileId + (
/*  71 */       ext[0] == 46 ? this.m_soundFilesExtension : new StringBuilder(".").append(this.m_soundFilesExtension).toString()), 
/*  72 */       bStreaming, bStereo, bLoop);
/*     */   }
/*     */   
/*     */   public void playSound(String fileName, boolean bLoop) {
/*  76 */     playSound(fileName, true, false, bLoop);
/*     */   }
/*     */   
/*     */   public void playSound(int fileId, boolean bLoop) {
/*  80 */     playSound(fileId, true, false, bLoop);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addSource(AudioSource source) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onGainChanged(float previousGain, float newGain)
/*     */   {
/*  98 */     if (previousGain != newGain) {
/*  99 */       synchronized (this.m_sourcesMutex) {
/* 100 */         for (AudioSource source : this.m_sources)
/* 101 */           source.setGain(newGain);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onMaxGainChanged(float previousMaxGain, float newMaxGain) {
/* 107 */     if (previousMaxGain != newMaxGain) {
/* 108 */       synchronized (this.m_sourcesMutex) {
/* 109 */         for (AudioSource source : this.m_sources)
/* 110 */           source.setMaxGain(newMaxGain);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onMuteChanged(boolean previousMute, boolean newMute) {
/* 116 */     if (previousMute != newMute) {
/* 117 */       synchronized (this.m_sourcesMutex) {
/* 118 */         float mg = 0.0F;
/* 119 */         if (!newMute)
/* 120 */           mg = getMaxGain();
/* 121 */         for (AudioSource source : this.m_sources) {
/* 122 */           source.setMaxGain(mg);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void update()
/*     */     throws Exception
/*     */   {
/* 132 */     synchronized (this.m_sourcesMutex)
/*     */     {
/* 134 */       Iterator<AudioSource> it = this.m_sources.iterator();
/*     */       
/* 136 */       while (it.hasNext()) {
/* 137 */         AudioSource source = (AudioSource)it.next();
/* 138 */         source.update();
/*     */         
/* 140 */         if (source.isFinished()) {
/* 141 */           source.release();
/* 142 */           it.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSoundFilesBasePath()
/*     */   {
/* 150 */     return this.m_soundFilesBasePath;
/*     */   }
/*     */   
/*     */   public void setSoundFilesBasePath(String soundFilesBasePath) {
/* 154 */     this.m_soundFilesBasePath = (soundFilesBasePath + "/");
/*     */   }
/*     */   
/*     */   public String getSoundFilesExtension() {
/* 158 */     return this.m_soundFilesExtension;
/*     */   }
/*     */   
/*     */   public void setSoundFilesExtension(String soundFilesExtension) {
/* 162 */     this.m_soundFilesExtension = soundFilesExtension;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\group\DefaultSourceGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */