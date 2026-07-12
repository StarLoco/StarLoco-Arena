/*     */ package com.ankamagames.framework.sounds.group.field;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3;
/*     */ import com.ankamagames.framework.sounds.AudioSource;
/*     */ import com.ankamagames.framework.sounds.group.AudioSourceGroup;
/*     */ import com.ankamagames.framework.sounds.group.ObservedListener;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class FieldGroup
/*     */   extends AudioSourceGroup
/*     */ {
/*  32 */   private final Object m_sourcesMutex = new Object();
/*     */   
/*     */   private TIntObjectHashMap<List<FieldSourceController>> m_sources;
/*     */   
/*     */   private ArrayList<FieldSourceController> m_renderList;
/*     */   
/*     */   private ArrayList<FieldSourceController> m_clearList;
/*     */   
/*     */   private int m_previousListenerHash;
/*     */   
/*     */   private TIntArrayList m_areas;
/*     */   
/*     */   private static final int SOURCES_MAP_WIDTH = 20;
/*     */   
/*     */   private static final int SOURCES_MAP_HEIGHT = 20;
/*     */   
/*     */   private static final int WIDTH_HEIGHT_MUTL = 65536;
/*     */   
/*     */   private static final int SIDE_LEFT = -65536;
/*     */   
/*     */   private static final int SIDE_RIGHT = 65536;
/*     */   private static final int SIDE_TOP = -1;
/*     */   private static final int SIDE_BOTTOM = 1;
/*     */   private AudioSource m_source;
/*     */   
/*     */   public FieldGroup(String name) {
/*  58 */     super(name);
/*     */     
/*  60 */     this.m_sources = new TIntObjectHashMap();
/*     */     
/*  62 */     this.m_renderList = new ArrayList<FieldSourceController>();
/*  63 */     this.m_clearList = new ArrayList<FieldSourceController>();
/*  64 */     this.m_areas = new TIntArrayList();
/*     */     
/*  66 */     this.m_previousListenerHash = -1;
/*     */     
/*  68 */     this.m_source = null;
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
/*     */   public void createReferences(String fileName, boolean bStreaming, boolean bStereo, float refDistance, float rollOffFactor) {
/*  81 */     this.m_source = new AudioSource(fileName, bStreaming, getSourceProvider(), getBufferProvider(), bStereo, true);
/*  82 */     this.m_source.setLoop(true);
/*  83 */     this.m_source.setReferenceDistance(refDistance);
/*  84 */     this.m_source.setMaxDistance(refDistance * 2.0F);
/*  85 */     this.m_source.setRolloffFactor(rollOffFactor);
/*  86 */     this.m_source.setMaxGain(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmitter(FieldSourceController e) {
/*  94 */     if (e != null) {
/*  95 */       int hash = getHashEntryFromCoordinates(e.getXf(), e.getYf());
/*  96 */       e.setCurrentHashValue(hash);
/*  97 */       addSourceToEntry(hash, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEmitter(FieldSourceController e) {
/* 106 */     int hash = getHashEntryFromCoordinates(e.getXf(), e.getYf());
/* 107 */     removeSourceFromEntry(hash, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getHashEntryFromCoordinates(float x, float y) {
/* 117 */     int ix = (int)Math.floor(x) / 20;
/* 118 */     int iy = (int)Math.floor(y) / 20;
/* 119 */     return iy * 65536 + ix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addSourceToEntry(int hash, FieldSourceController e) {
/* 128 */     List<FieldSourceController> sources = (List<FieldSourceController>)this.m_sources.get(hash);
/* 129 */     if (sources == null) {
/* 130 */       sources = new ArrayList<FieldSourceController>();
/* 131 */       this.m_sources.put(hash, sources);
/*     */     } 
/*     */     
/* 134 */     sources.add(e);
/* 135 */     e.setCurrentHashValue(hash);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removeSourceFromEntry(int hash, FieldSourceController e) {
/* 145 */     ArrayList<FieldSourceController> sources = (ArrayList<FieldSourceController>)this.m_sources.get(hash);
/* 146 */     if (sources == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     return sources.remove(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removeSourceFromItsEntry(FieldSourceController e) {
/* 158 */     ArrayList<FieldSourceController> sources = (ArrayList<FieldSourceController>)this.m_sources.get(e.getCurrentHashValue());
/* 159 */     if (sources == null) {
/* 160 */       return false;
/*     */     }
/* 162 */     return sources.remove(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<AudioSource> getSources() {
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
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
/*     */   
/*     */   public void onGainChanged(float previousGain, float newGain) {
/* 198 */     if (this.m_source != null)
/* 199 */       this.m_source.setGain(newGain); 
/*     */   }
/*     */   
/*     */   public void onMaxGainChanged(float previousMaxGain, float newMaxGain) {
/* 203 */     if (this.m_source != null) {
/* 204 */       this.m_source.setMaxGain(newMaxGain);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMuteChanged(boolean previousMute, boolean newMute) {
/* 214 */     if (newMute) {
/* 215 */       int i = 0;
/*     */     } else {
/* 217 */       boolean bool = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEmitterPositionChanged(FieldSourceController e) {
/* 226 */     int oldHash = e.getCurrentHashValue();
/* 227 */     int newHash = getHashEntryFromCoordinates(e.getXf(), e.getYf());
/*     */     
/* 229 */     if (oldHash != newHash) {
/* 230 */       removeSourceFromItsEntry(e);
/* 231 */       addSourceToEntry(newHash, e);
/* 232 */       e.setCurrentHashValue(newHash);
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
/*     */   public void update() throws Exception {
/* 244 */     ObservedListener listener = getListener();
/* 245 */     if (listener == null || this.m_source == null) {
/*     */       return;
/*     */     }
/*     */     
/* 249 */     float lx = listener.getListenerPositionX();
/* 250 */     float ly = listener.getListenerPositionY();
/*     */ 
/*     */ 
/*     */     
/* 254 */     Vector3 listenerPos = new Vector3(lx, ly, 0.0D);
/* 255 */     int listenerHash = getHashEntryFromCoordinates(lx, ly);
/*     */ 
/*     */     
/* 258 */     Vector3 leftPosition = new Vector3(0.0D, 0.0D, 0.0D);
/* 259 */     Vector3 rightPosition = new Vector3(0.0D, 0.0D, 0.0D);
/* 260 */     float leftDistance = Float.MAX_VALUE;
/* 261 */     float rightDistance = Float.MAX_VALUE;
/*     */     
/* 263 */     synchronized (this.m_sourcesMutex) {
/*     */ 
/*     */       
/* 266 */       if (listenerHash != this.m_previousListenerHash) {
/* 267 */         this.m_areas.clear();
/* 268 */         this.m_areas.add(listenerHash);
/*     */         
/* 270 */         this.m_areas.add(listenerHash + -1);
/* 271 */         this.m_areas.add(listenerHash + 1);
/* 272 */         this.m_areas.add(listenerHash + -65536);
/* 273 */         this.m_areas.add(listenerHash + 65536);
/*     */         
/* 275 */         this.m_areas.add(listenerHash + -1 + -65536);
/* 276 */         this.m_areas.add(listenerHash + -1 + 65536);
/* 277 */         this.m_areas.add(listenerHash + 1 + -65536);
/* 278 */         this.m_areas.add(listenerHash + 1 + 65536);
/*     */         
/* 280 */         for (int i = 0; i < this.m_areas.size(); i++) {
/*     */           
/* 282 */           ArrayList<FieldSourceController> sources = (ArrayList<FieldSourceController>)this.m_sources.get(this.m_areas.get(i));
/*     */           
/* 284 */           if (sources != null)
/* 285 */             for (FieldSourceController s : sources) {
/* 286 */               if (!this.m_renderList.contains(s))
/* 287 */                 this.m_renderList.add(s); 
/*     */             }  
/*     */         } 
/* 290 */         this.m_previousListenerHash = listenerHash;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 295 */       for (FieldSourceController s : this.m_renderList) {
/* 296 */         int sourceHash = s.getCurrentHashValue();
/*     */         
/* 298 */         if (!this.m_areas.contains(sourceHash)) {
/* 299 */           this.m_clearList.add(s);
/*     */           continue;
/*     */         } 
/* 302 */         Vector3 v = s.toIsometricListenerView(listenerPos);
/* 303 */         float d = (float)v.length();
/* 304 */         float x = v.getXf();
/*     */         
/* 306 */         if (x < 0.0F) {
/* 307 */           if (d <= leftDistance) {
/* 308 */             leftDistance = d;
/* 309 */             leftPosition = new Vector3(v);
/*     */           }  continue;
/* 311 */         }  if (x >= 0.0F && 
/* 312 */           d <= rightDistance) {
/* 313 */           rightDistance = d;
/* 314 */           rightPosition = new Vector3(v);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 321 */       if (!this.m_source.isPlaying()) {
/* 322 */         this.m_source.play();
/*     */       }
/* 324 */       if (this.m_source.needMoreUpdates()) {
/* 325 */         this.m_source.update();
/*     */       }
/* 327 */       this.m_source.setMaxGain(getMaxGain());
/*     */ 
/*     */ 
/*     */       
/* 331 */       if (leftDistance <= 20.0F && rightDistance <= 20.0F) {
/*     */         
/* 333 */         if (leftDistance > 1.0F && rightDistance > 1.0F) {
/* 334 */           Vector3 l = leftPosition.mul((1.0F / leftDistance));
/* 335 */           Vector3 r = rightPosition.mul((1.0F / rightDistance));
/* 336 */           Vector3 position = l.add(r).mul(0.5D);
/* 337 */           this.m_source.setPosition(position);
/*     */         } else {
/*     */           
/* 340 */           this.m_source.setPosition(new Vector3(0.0D, 0.0D, 0.0D));
/*     */         }
/*     */       
/* 343 */       } else if (leftDistance <= 20.0F) {
/* 344 */         this.m_source.setPosition(leftPosition);
/* 345 */       } else if (rightDistance <= 20.0F) {
/* 346 */         this.m_source.setPosition(rightPosition);
/*     */       } else {
/* 348 */         this.m_source.setMaxGain(0.0F);
/*     */       } 
/*     */ 
/*     */       
/* 352 */       for (FieldSourceController s : this.m_clearList) {
/* 353 */         this.m_renderList.remove(s);
/*     */       }
/* 355 */       this.m_clearList.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\group\field\FieldGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */