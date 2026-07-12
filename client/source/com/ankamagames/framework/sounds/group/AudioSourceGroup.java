/*     */ package com.ankamagames.framework.sounds.group;
/*     */ 
/*     */ import com.ankamagames.framework.sounds.AudioSource;
/*     */ import com.ankamagames.framework.sounds.BufferProvider;
/*     */ import com.ankamagames.framework.sounds.SoundManager;
/*     */ import com.ankamagames.framework.sounds.SourceProvider;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class AudioSourceGroup
/*     */ {
/*  21 */   protected static Logger m_logger = Logger.getLogger(AudioSourceGroup.class);
/*     */   
/*     */   private float m_gain;
/*     */   private float m_maxGain;
/*     */   private Boolean m_mute;
/*     */   private String m_name;
/*     */   private SourceProvider m_sourceProvider;
/*     */   private BufferProvider m_bufferProvider;
/*     */   private ObservedListener m_listener;
/*     */   
/*     */   protected AudioSourceGroup(String name) {
/*  32 */     this.m_gain = 1.0F;
/*  33 */     this.m_maxGain = 1.0F;
/*  34 */     this.m_mute = Boolean.valueOf(false);
/*  35 */     this.m_name = name;
/*  36 */     this.m_sourceProvider = (SourceProvider)SoundManager.getInstance();
/*  37 */     this.m_bufferProvider = (BufferProvider)SoundManager.getInstance();
/*  38 */     this.m_listener = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceProvider getSourceProvider() {
/*  45 */     return this.m_sourceProvider;
/*     */   }
/*     */   
/*     */   public void setSourceProvider(SourceProvider sourceProvider) {
/*  49 */     this.m_sourceProvider = sourceProvider;
/*     */   }
/*     */   
/*     */   public BufferProvider getBufferProvider() {
/*  53 */     return this.m_bufferProvider;
/*     */   }
/*     */   
/*     */   public void setBufferProvider(BufferProvider bufferProvider) {
/*  57 */     this.m_bufferProvider = bufferProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObservedListener getListener() {
/*  64 */     return this.m_listener;
/*     */   }
/*     */   
/*     */   public void setListener(ObservedListener listener) {
/*  68 */     this.m_listener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getGain() {
/*  76 */     return this.m_gain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setGain(float gain) {
/*  84 */     if (this.m_gain != gain) {
/*  85 */       onGainChanged(this.m_gain, gain);
/*  86 */       this.m_gain = gain;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void setMaxGain(float maxGain) {
/*  91 */     if (this.m_maxGain != maxGain) {
/*  92 */       onMaxGainChanged(this.m_maxGain, maxGain);
/*  93 */       this.m_maxGain = maxGain;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final float getMaxGain() {
/*  98 */     return this.m_maxGain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Boolean isMute() {
/* 106 */     return this.m_mute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMute(boolean mute) {
/* 114 */     if (this.m_mute.booleanValue() != mute) {
/* 115 */       onMuteChanged(this.m_mute.booleanValue(), mute);
/* 116 */       this.m_mute = Boolean.valueOf(mute);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getName() {
/* 121 */     return this.m_name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 125 */     this.m_name = name;
/*     */   }
/*     */   
/*     */   public abstract ArrayList<AudioSource> getSources();
/*     */   
/*     */   public abstract void addSource(String paramString, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public abstract void addSource(AudioSource paramAudioSource);
/*     */   
/*     */   public abstract void onGainChanged(float paramFloat1, float paramFloat2);
/*     */   
/*     */   public abstract void onMaxGainChanged(float paramFloat1, float paramFloat2);
/*     */   
/*     */   public abstract void onMuteChanged(boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   public abstract void update() throws Exception;
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\group\AudioSourceGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */