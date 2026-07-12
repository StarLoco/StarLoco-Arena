/*     */ package com.ankamagames.framework.sounds;
/*     */ 
/*     */ import com.ankamagames.framework.sounds.group.AudioSourceGroup;
/*     */ import com.ankamagames.framework.sounds.group.DefaultSourceGroup;
/*     */ import com.ankamagames.framework.sounds.group.ObservedListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.java.games.sound3d.AudioSystem3D;
/*     */ import net.java.games.sound3d.Buffer;
/*     */ import net.java.games.sound3d.Context;
/*     */ import net.java.games.sound3d.Device;
/*     */ import net.java.games.sound3d.Listener;
/*     */ import net.java.games.sound3d.Source;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PropertyConfigurator;
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
/*     */ public final class SoundManager
/*     */   extends Thread
/*     */   implements SourceProvider, BufferProvider
/*     */ {
/*  30 */   protected static final Logger m_logger = Logger.getLogger(SoundManager.class);
/*  31 */   private static final SoundManager m_instance = new SoundManager();
/*     */   
/*     */   private boolean m_running;
/*     */   
/*     */   private ArrayList<AudioSourceGroup> m_groups;
/*  36 */   private final Object m_groupsMutex = new Object();
/*  37 */   private final Object m_sleepObject = new Object();
/*     */   
/*     */   private int m_nbSources;
/*     */   
/*     */   private int m_nbBuffers;
/*     */   
/*     */   private Device m_device;
/*     */   private Context m_context;
/*     */   private ArrayList<Source> m_sourcePoolIn;
/*     */   private ArrayList<Source> m_sourcePoolOut;
/*     */   private ArrayList<Buffer> m_bufferPoolIn;
/*     */   private ArrayList<Buffer> m_bufferPoolOut;
/*     */   private Listener m_soundListener;
/*  50 */   private float[] m_listenerOrientation = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0F };
/*     */   
/*     */   private ObservedListener m_listener;
/*     */   
/*     */   private boolean m_initialized;
/*  55 */   private final HashMap<String, SoundBankItem> m_soundBank = new HashMap<String, SoundBankItem>();
/*  56 */   private final Object m_soundBankMutex = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SoundManager() {
/*  62 */     super("SoundManager");
/*  63 */     this.m_running = false;
/*  64 */     this.m_groups = new ArrayList<AudioSourceGroup>();
/*  65 */     this.m_listener = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SoundManager getInstance() {
/*  74 */     return m_instance;
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
/*     */   public void initialize(String deviceName, int nbSources, int nbBuffers) {
/*  88 */     if (this.m_initialized) {
/*  89 */       m_logger.warn("SoundManager already initialized");
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     this.m_nbSources = nbSources;
/*  94 */     this.m_nbBuffers = nbBuffers;
/*  95 */     this.m_initialized = false;
/*     */     
/*     */     try {
/*  98 */       AudioSystem3D.init();
/*     */       
/* 100 */       this.m_device = AudioSystem3D.openDevice(deviceName);
/*     */       
/* 102 */       if (this.m_device == null) {
/* 103 */         m_logger.error("openDevice(DirectSound3D) failed");
/* 104 */         this.m_device = AudioSystem3D.openDevice("Generic Hardware");
/*     */       } 
/*     */       
/* 107 */       if (this.m_device == null) {
/* 108 */         m_logger.error("openDevice(Generic Hardware) failed");
/* 109 */         this.m_device = AudioSystem3D.openDevice("Generic Software");
/*     */       } 
/*     */       
/* 112 */       if (this.m_device == null) {
/* 113 */         m_logger.error("openDevice(Generic Software) failed => no sound");
/*     */         
/*     */         return;
/*     */       } 
/* 117 */       this.m_context = AudioSystem3D.createContext(this.m_device);
/*     */       
/* 119 */       if (this.m_context == null) {
/* 120 */         m_logger.error("Unable to create context for Device => no sound");
/*     */         
/*     */         return;
/*     */       } 
/* 124 */       AudioSystem3D.makeContextCurrent(this.m_context);
/*     */       
/* 126 */       this.m_sourcePoolIn = new ArrayList<Source>();
/* 127 */       this.m_sourcePoolOut = new ArrayList<Source>();
/*     */       int i;
/* 129 */       for (i = 0; i < this.m_nbSources; i++) {
/* 130 */         Source[] qSource = AudioSystem3D.generateSources(1);
/* 131 */         Source source = qSource[0];
/*     */         
/* 133 */         source.setPosition(0.0F, 0.0F, 0.0F);
/* 134 */         source.setVelocity(0.0F, 0.0F, 0.0F);
/* 135 */         source.setMaxDistance(100.0F);
/* 136 */         source.setLooping(true);
/* 137 */         this.m_sourcePoolIn.add(source);
/*     */       } 
/*     */       
/* 140 */       this.m_bufferPoolIn = new ArrayList<Buffer>();
/* 141 */       this.m_bufferPoolOut = new ArrayList<Buffer>();
/*     */       
/* 143 */       for (i = 0; i < this.m_nbBuffers; i++) {
/* 144 */         Buffer[] qBuffer = AudioSystem3D.generateBuffers(1);
/* 145 */         this.m_bufferPoolIn.add(qBuffer[0]);
/*     */       } 
/*     */       
/* 148 */       this.m_soundListener = AudioSystem3D.getListener();
/* 149 */       this.m_soundListener.setPosition(0.0F, 0.0F, 0.0F);
/* 150 */       this.m_soundListener.setOrientation(this.m_listenerOrientation);
/*     */       
/* 152 */       this.m_initialized = true;
/*     */     }
/* 154 */     catch (Throwable e) {
/* 155 */       m_logger.error("Error during initialisation (=> no sound): ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGroup(AudioSourceGroup group) {
/* 166 */     if (group == null) {
/*     */       return;
/*     */     }
/* 169 */     synchronized (this.m_groupsMutex) {
/* 170 */       if (!this.m_groups.contains(group)) {
/* 171 */         this.m_groups.add(group);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeGroup(AudioSourceGroup group) {
/* 182 */     if (group == null)
/*     */       return; 
/* 184 */     synchronized (this.m_groupsMutex) {
/* 185 */       this.m_groups.remove(group);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioSourceGroup getGroupByName(String name) {
/* 196 */     if (name == null) {
/* 197 */       return null;
/*     */     }
/* 199 */     synchronized (this.m_groupsMutex) {
/* 200 */       for (AudioSourceGroup group : this.m_groups) {
/* 201 */         if (group.getName().equals(name))
/* 202 */           return group; 
/*     */       } 
/*     */     } 
/* 205 */     return null;
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
/*     */   public Source checkOutSource() {
/* 217 */     if (this.m_sourcePoolIn.isEmpty()) {
/* 218 */       return null;
/*     */     }
/* 220 */     Source source = this.m_sourcePoolIn.remove(0);
/* 221 */     this.m_sourcePoolOut.add(source);
/*     */     
/* 223 */     return source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseSource(Source source) {
/* 233 */     if (source != null && 
/* 234 */       this.m_sourcePoolOut.remove(source)) {
/* 235 */       this.m_sourcePoolIn.add(source);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Buffer checkOutBuffer() {
/* 250 */     if (this.m_bufferPoolIn.isEmpty()) {
/* 251 */       return null;
/*     */     }
/* 253 */     Buffer buffer = this.m_bufferPoolIn.remove(0);
/* 254 */     this.m_bufferPoolOut.add(buffer);
/*     */     
/* 256 */     return buffer;
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
/*     */   public void releaseBuffer(Buffer buffer) {
/* 268 */     if (buffer != null && 
/* 269 */       this.m_bufferPoolOut.remove(buffer)) {
/* 270 */       this.m_bufferPoolIn.add(buffer);
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
/*     */   
/*     */   public ObservedListener getListener() {
/* 283 */     return this.m_listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(ObservedListener listener) {
/* 292 */     this.m_listener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 300 */     if (this.m_running) {
/* 301 */       m_logger.warn("SoundManager already running");
/*     */     }
/* 303 */     else if (this.m_initialized) {
/* 304 */       this.m_running = true;
/* 305 */       super.start();
/*     */     } else {
/* 307 */       m_logger.error("Cant start uninitialized SoundManager");
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
/*     */   public void run() {
/* 319 */     if (this.m_initialized) {
/* 320 */       m_logger.info("SoundManager running");
/*     */     } else {
/* 322 */       m_logger.error("SoundManager not initialized or failed to initialize");
/*     */       
/*     */       return;
/*     */     } 
/* 326 */     synchronized (this.m_sleepObject) {
/*     */ 
/*     */       
/*     */       try {
/* 330 */         while (this.m_running) {
/*     */           
/* 332 */           this.m_sleepObject.wait(10L);
/*     */           
/* 334 */           if (this.m_listener != null) {
/* 335 */             float x = this.m_listener.getListenerPositionX();
/* 336 */             float y = this.m_listener.getListenerPositionY();
/* 337 */             float z = this.m_listener.getListenerPositionZ();
/*     */             
/* 339 */             this.m_listenerOrientation[3] = this.m_listener.getListenerOrientationX();
/* 340 */             this.m_listenerOrientation[4] = this.m_listener.getListenerOrientationY();
/* 341 */             this.m_listenerOrientation[5] = this.m_listener.getListenerOrientationZ();
/*     */           } 
/*     */           
/* 344 */           synchronized (this.m_groupsMutex) {
/*     */ 
/*     */             
/* 347 */             for (int i = 0; i < this.m_groups.size(); i++) {
/* 348 */               AudioSourceGroup group = this.m_groups.get(i);
/*     */               try {
/* 350 */                 group.setListener(this.m_listener);
/* 351 */                 group.update();
/* 352 */               } catch (Exception e) {
/* 353 */                 e.printStackTrace();
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/* 360 */       } catch (InterruptedException e) {
/* 361 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SoundBankItem createSoundBankItem(String referenceName, ArrayList<Buffer> buffers) {
/* 367 */     synchronized (this.m_soundBankMutex) {
/*     */       
/* 369 */       SoundBankItem item = new SoundBankItem(referenceName, buffers);
/* 370 */       this.m_soundBank.put(referenceName, item);
/* 371 */       item.addUserReference();
/* 372 */       return item;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SoundBankItem getSoundBankItem(String referenceName) {
/* 377 */     synchronized (this.m_soundBankMutex) {
/*     */       
/* 379 */       SoundBankItem item = this.m_soundBank.get(referenceName);
/* 380 */       if (item != null)
/* 381 */         item.addUserReference(); 
/* 382 */       return item;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseSoundBankItem(SoundBankItem item) {
/* 387 */     synchronized (this.m_soundBankMutex) {
/* 388 */       if (item != null) {
/* 389 */         item.release();
/* 390 */         if (item.getUserReferenceCount() == 0) {
/* 391 */           this.m_soundBank.remove(item.getReferenceName());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 399 */     PropertyConfigurator.configure("log4j.properties");
/*     */     
/* 401 */     getInstance().initialize("DirectSound3D", 32, 1000);
/* 402 */     getInstance().start();
/*     */     
/* 404 */     DefaultSourceGroup group = new DefaultSourceGroup("default");
/* 405 */     getInstance().addGroup((AudioSourceGroup)group);
/*     */     
/* 407 */     group.setMaxGain(0.9F);
/*     */     
/* 409 */     group.playSound("contents/snd/aspiration.ogg", false, true, false);
/*     */ 
/*     */     
/* 412 */     m_logger.info("Playing sound ... ");
/*     */     while (true) {
/*     */       try {
/*     */         while (true)
/* 416 */           Thread.sleep(1000L);  break;
/* 417 */       } catch (InterruptedException e) {
/* 418 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\SoundManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */