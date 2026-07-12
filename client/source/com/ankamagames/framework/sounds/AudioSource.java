/*     */ package com.ankamagames.framework.sounds;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.maths.Vector3;
/*     */ import com.ankamagames.framework.kernel.core.resource.FileLoader;
/*     */ import com.ankamagames.framework.kernel.core.resource.FileLoaderEventListener;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ import net.java.games.sound3d.Buffer;
/*     */ import net.java.games.sound3d.Source;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AudioSource
/*     */   implements FileLoader
/*     */ {
/*  38 */   protected static final Logger m_logger = Logger.getLogger(AudioSource.class);
/*     */   
/*     */   private static final int STREAM_BUFFERS_COUNT = 1;
/*     */   
/*     */   private static final int STREAM_BUFFER_SIZE = 65536;
/*     */   
/*     */   private static final int SOUND_TIMEOUT = 1486;
/*     */   
/*     */   protected boolean m_useStreaming;
/*     */   
/*     */   protected AudioFormat m_format;
/*     */   
/*     */   protected String m_sourceFileName;
/*     */   
/*     */   private float m_gainVariation;
/*     */   
/*     */   private Source m_source;
/*     */   
/*     */   private float m_sampleRate;
/*     */   
/*     */   private boolean m_loop;
/*     */   
/*     */   private boolean m_stereo;
/*     */   
/*     */   private SourceProvider m_sourceProvider;
/*     */   private BufferProvider m_bufferProvider;
/*     */   private float m_gain;
/*     */   private float m_maxGain;
/*     */   private float m_minGain;
/*     */   private Vector3 m_position;
/*     */   private Vector3 m_velocity;
/*     */   private float m_refDistance;
/*     */   private float m_maxDistance;
/*     */   private float m_rollOffFactor;
/*     */   private ArrayList<FileLoaderEventListener> m_fileLoaderEventListeners;
/*     */   private Buffer[] m_qBuffer;
/*     */   private boolean m_needMoreUpdates;
/*     */   private ArrayList<Buffer> m_soundBuffers;
/*     */   private int m_soundBufferPushIndex;
/*     */   private boolean m_streaming;
/*     */   private AudioInputStream m_streamSource;
/*     */   private boolean m_soundPlayingMustStart;
/*     */   private boolean m_soundPlayingFinished;
/*     */   private boolean m_soundPlayingInProgress;
/*     */   private SoundBankItem m_soundBankItem;
/*  83 */   private long m_startSoundPlayingTime = 0L;
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
/*     */   public AudioSource(String fileName, boolean bStreaming, SourceProvider sourceProvider, BufferProvider bufferProvider, boolean bStereo, boolean bLoop) {
/* 100 */     this.m_sourceFileName = fileName;
/* 101 */     this.m_gainVariation = 0.0F;
/* 102 */     this.m_loop = bLoop;
/* 103 */     this.m_useStreaming = bStreaming;
/* 104 */     this.m_sourceProvider = sourceProvider;
/* 105 */     this.m_bufferProvider = bufferProvider;
/* 106 */     this.m_stereo = bStereo;
/* 107 */     this.m_fileLoaderEventListeners = new ArrayList<FileLoaderEventListener>();
/*     */     
/* 109 */     this.m_minGain = 0.0F;
/* 110 */     this.m_maxGain = 1.0F;
/* 111 */     this.m_gain = this.m_maxGain;
/*     */     
/* 113 */     this.m_position = new Vector3(0.0D, 0.0D, 0.0D);
/* 114 */     this.m_velocity = new Vector3(0.0D, 0.0D, 0.0D);
/*     */     
/* 116 */     this.m_refDistance = 10.0F;
/* 117 */     this.m_maxDistance = 50.0F;
/* 118 */     this.m_rollOffFactor = 15.0F;
/*     */     
/* 120 */     this.m_qBuffer = new Buffer[1];
/*     */     
/* 122 */     this.m_needMoreUpdates = true;
/*     */     
/*     */     try {
/* 125 */       this.m_soundPlayingFinished = false;
/* 126 */       this.m_soundPlayingMustStart = false;
/*     */ 
/*     */       
/* 129 */       ArrayList<Buffer> soundBuffers = getSoundFileFromCache(this.m_sourceFileName, this.m_stereo);
/* 130 */       if (soundBuffers != null) {
/* 131 */         this.m_streaming = false;
/* 132 */         this.m_soundBuffers = soundBuffers;
/*     */       }
/*     */       else {
/*     */         
/* 136 */         this.m_streaming = bStreaming;
/* 137 */         if (this.m_streaming) {
/* 138 */           this.m_streamSource = openStreamFromFile(this.m_sourceFileName);
/* 139 */           this.m_soundBuffers = new ArrayList<Buffer>();
/* 140 */           this.m_soundBankItem = SoundManager.getInstance().createSoundBankItem(String.valueOf(this.m_sourceFileName) + "|" + this.m_stereo, this.m_soundBuffers);
/*     */         } else {
/* 142 */           this.m_soundBuffers = loadAndFormatSoundFile(this.m_sourceFileName, this.m_stereo);
/*     */         } 
/*     */       } 
/* 145 */       this.m_soundBufferPushIndex = 0;
/*     */     }
/* 147 */     catch (Exception e) {
/* 148 */       fireOnLoadErrorEvent(this.m_sourceFileName, "exception : " + e.getMessage());
/* 149 */       e.printStackTrace();
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
/*     */   private ArrayList<Buffer> loadAndFormatSoundFile(String fileName, boolean bStereo) throws Exception {
/* 162 */     ArrayList<Buffer> buffers = getSoundFileFromCache(fileName, bStereo);
/* 163 */     if (buffers != null) {
/* 164 */       return buffers;
/*     */     }
/* 166 */     AudioInputStream stream = openStreamFromFile(fileName);
/*     */     
/* 168 */     if (stream == null) {
/* 169 */       throw new Exception("Unable to obtain AudioInputStream");
/*     */     }
/* 171 */     buffers = new ArrayList<Buffer>(); do {
/*     */     
/* 173 */     } while (streamForward(stream, bStereo, buffers));
/*     */ 
/*     */     
/* 176 */     String referenceName = String.valueOf(fileName) + "|" + bStereo;
/* 177 */     this.m_soundBankItem = SoundManager.getInstance().createSoundBankItem(referenceName, buffers);
/*     */     
/* 179 */     return buffers;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean streamForward(AudioInputStream stream, boolean bStereo, ArrayList<Buffer> streamBuffers) throws Exception {
/* 184 */     if (stream == null) {
/* 185 */       return false;
/*     */     }
/* 187 */     byte[] streamBuffer = new byte[65536];
/*     */     
/* 189 */     if (stream.read(streamBuffer) > 0) {
/* 190 */       ByteBuffer dataBuffer = ByteBuffer.wrap(streamBuffer);
/* 191 */       Buffer buffer = this.m_bufferProvider.checkOutBuffer();
/*     */       
/* 193 */       if (buffer != null) {
/* 194 */         if (bStereo) {
/* 195 */           buffer.configure(dataBuffer, 4355, (int)Math.floor(this.m_sampleRate));
/*     */         } else {
/* 197 */           buffer.configure(dataBuffer, 4353, (int)Math.floor(this.m_sampleRate));
/*     */         } 
/* 199 */         streamBuffers.add(buffer);
/*     */       } 
/*     */     } else {
/* 202 */       return false;
/*     */     } 
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<Buffer> getSoundFileFromCache(String fileName, boolean bStereo) {
/* 215 */     String referenceName = String.valueOf(fileName) + "|" + bStereo;
/*     */     
/* 217 */     this.m_soundBankItem = SoundManager.getInstance().getSoundBankItem(referenceName);
/* 218 */     if (this.m_soundBankItem != null) {
/*     */       
/* 220 */       ArrayList<Buffer> buffers = this.m_soundBankItem.getBuffers();
/* 221 */       if (buffers != null) {
/* 222 */         return buffers;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AudioInputStream openStreamFromFile(String fileName) throws Exception {
/* 237 */     InputStream stream = null;
/*     */     
/*     */     try {
/* 240 */       URL jarUrl = new URL(fileName);
/* 241 */       stream = jarUrl.openStream();
/* 242 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 245 */       File file = new File(fileName);
/*     */       try {
/* 247 */         stream = new FileInputStream(file);
/* 248 */       } catch (FileNotFoundException e1) {
/* 249 */         m_logger.error("Impossible d'ouvrir le fichier audio : " + fileName);
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     if (stream != null) {
/* 254 */       return openStreamFromFile(stream);
/*     */     }
/* 256 */     return null;
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
/*     */   protected AudioInputStream openStreamFromFile(InputStream stream) throws Exception {
/* 270 */     AudioInputStream sourceStream = null;
/*     */     
/*     */     try {
/* 273 */       sourceStream = AudioSystem.getAudioInputStream(stream);
/*     */     }
/* 275 */     catch (UnsupportedAudioFileException e) {
/* 276 */       m_logger.error("Format de fichier audio non supporté", e);
/*     */     } 
/*     */     
/* 279 */     if (sourceStream != null) {
/* 280 */       AudioFormat baseFormat = sourceStream.getFormat();
/*     */       
/* 282 */       this.m_sampleRate = baseFormat.getSampleRate();
/*     */       
/* 284 */       AudioFormat decodedFormat = new AudioFormat(
/* 285 */           AudioFormat.Encoding.PCM_SIGNED, 
/* 286 */           this.m_sampleRate, 
/* 287 */           16, 
/* 288 */           this.m_stereo ? 2 : 1, (
/* 289 */           this.m_stereo ? 2 : 1) * 2, 
/* 290 */           this.m_sampleRate, 
/* 291 */           false);
/*     */       
/* 293 */       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, sourceStream);
/*     */       
/* 295 */       if (audioInputStream != null) {
/* 296 */         return audioInputStream;
/*     */       }
/*     */     } 
/* 299 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSourceFileName() {
/* 307 */     return this.m_sourceFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/* 316 */     this.m_soundPlayingMustStart = true;
/*     */     
/* 318 */     if (this.m_source == null && this.m_sourceProvider != null) {
/* 319 */       this.m_source = this.m_sourceProvider.checkOutSource();
/* 320 */       if (this.m_source != null) {
/* 321 */         setupSource();
/*     */       } else {
/* 323 */         this.m_soundPlayingMustStart = false;
/* 324 */         this.m_soundPlayingFinished = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 340 */     if (this.m_source != null) {
/* 341 */       this.m_source.pause();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 348 */     SoundManager.getInstance().releaseSource(this.m_source);
/* 349 */     SoundManager.getInstance().releaseSoundBankItem(this.m_soundBankItem);
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
/*     */   public synchronized void update() throws Exception {
/* 363 */     long now = System.currentTimeMillis();
/*     */     
/* 365 */     if (this.m_source == null) {
/*     */       return;
/*     */     }
/*     */     
/* 369 */     if (!this.m_loop && 
/* 370 */       this.m_soundBuffers != null && this.m_source.getBuffersProcessed() == this.m_soundBuffers.size()) {
/* 371 */       for (Buffer buffer : this.m_soundBuffers) {
/* 372 */         this.m_qBuffer[0] = buffer;
/* 373 */         this.m_source.unqueueBuffers(this.m_qBuffer);
/*     */       } 
/* 375 */       this.m_soundPlayingFinished = true;
/* 376 */       this.m_soundPlayingInProgress = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (this.m_streaming) {
/* 382 */       for (int i = 0; i < 1; i++) {
/* 383 */         if (!streamForward(this.m_streamSource, this.m_stereo, this.m_soundBuffers)) {
/* 384 */           this.m_streaming = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 391 */     if (this.m_soundBuffers != null) {
/* 392 */       for (int i = this.m_soundBufferPushIndex; i < this.m_soundBuffers.size(); i++) {
/* 393 */         this.m_qBuffer[0] = this.m_soundBuffers.get(i);
/* 394 */         this.m_source.queueBuffers(this.m_qBuffer);
/*     */       } 
/* 396 */       this.m_soundBufferPushIndex = this.m_soundBuffers.size();
/*     */     } 
/*     */     
/* 399 */     if (this.m_soundPlayingMustStart && 
/* 400 */       this.m_soundBufferPushIndex > 0) {
/* 401 */       this.m_source.setLooping(this.m_loop);
/* 402 */       this.m_source.play();
/* 403 */       this.m_soundPlayingMustStart = false;
/* 404 */       this.m_soundPlayingFinished = false;
/* 405 */       this.m_soundPlayingInProgress = true;
/* 406 */       this.m_startSoundPlayingTime = now;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 411 */     if (this.m_soundPlayingInProgress && 
/* 412 */       this.m_source.getBuffersProcessed() == 0) {
/* 413 */       long delay = now - this.m_startSoundPlayingTime;
/* 414 */       if (delay > 1486L) {
/* 415 */         this.m_soundPlayingInProgress = false;
/* 416 */         this.m_soundPlayingFinished = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     float minGain = getMinGain();
/* 425 */     float maxGain = getMaxGain();
/* 426 */     float gain = getGain();
/*     */     
/* 428 */     if (this.m_gainVariation < 0.0F) {
/* 429 */       if (this.m_gainVariation + gain <= minGain) {
/* 430 */         setGain(minGain);
/* 431 */         this.m_gainVariation = 0.0F;
/* 432 */         end();
/*     */       } else {
/* 434 */         setGain(this.m_gainVariation + gain);
/*     */       } 
/* 436 */     } else if (this.m_gainVariation > 0.0F) {
/* 437 */       if (this.m_gainVariation + gain >= maxGain) {
/* 438 */         setGain(maxGain);
/* 439 */         this.m_gainVariation = 0.0F;
/*     */       } else {
/* 441 */         setGain(this.m_gainVariation + gain);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 450 */     return this.m_soundPlayingFinished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fadeIn(float gainVariation) {
/* 458 */     this.m_gainVariation = Math.abs(gainVariation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fadeOut(float gainVariation) {
/* 466 */     this.m_gainVariation = -Math.abs(gainVariation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlaying() {
/* 474 */     return this.m_soundPlayingInProgress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGain(float gain) {
/* 489 */     this.m_gain = gain;
/* 490 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGain() {
/* 498 */     return this.m_gain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinGain() {
/* 506 */     return this.m_minGain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxGain(float maxGain) {
/* 514 */     this.m_maxGain = maxGain;
/* 515 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxGain() {
/* 523 */     if (this.m_source != null)
/* 524 */       return this.m_source.getMaxGain(); 
/* 525 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoop() {
/* 534 */     return this.m_loop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoop(boolean loop) {
/* 542 */     this.m_loop = loop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 getPosition() {
/* 550 */     return this.m_position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(Vector3 position) {
/* 558 */     this.m_position = position;
/* 559 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosX() {
/* 567 */     return this.m_position.getXf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosY() {
/* 575 */     return this.m_position.getYf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosZ() {
/* 583 */     return this.m_position.getZf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(float x, float y, float z) {
/* 593 */     this.m_position = new Vector3(x, y, z);
/* 594 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 getVelocity() {
/* 602 */     return this.m_velocity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(Vector3 velocity) {
/* 610 */     this.m_velocity = velocity;
/* 611 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(float vx, float vy, float vz) {
/* 621 */     this.m_velocity = new Vector3(vx, vy, vz);
/* 622 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxDistance(float maxDistance) {
/* 630 */     this.m_maxDistance = maxDistance;
/* 631 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReferenceDistance(float refDistance) {
/* 639 */     this.m_refDistance = refDistance;
/* 640 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRolloffFactor(float factor) {
/* 649 */     this.m_rollOffFactor = factor;
/* 650 */     setupSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupSource() {
/* 658 */     if (this.m_source != null) {
/* 659 */       if (this.m_position != null) {
/* 660 */         this.m_source.setPosition(this.m_position.getXf(), this.m_position.getYf(), this.m_position.getZf());
/*     */       } else {
/* 662 */         this.m_source.setPosition(0.0F, 0.0F, 0.0F);
/*     */       } 
/* 664 */       if (this.m_velocity != null) {
/* 665 */         this.m_source.setVelocity(this.m_velocity.getXf(), this.m_velocity.getYf(), this.m_velocity.getZf());
/*     */       } else {
/* 667 */         this.m_source.setVelocity(0.0F, 0.0F, 0.0F);
/*     */       } 
/* 669 */       if (this.m_position == null && this.m_velocity == null) {
/* 670 */         this.m_source.setSourceRelative(false);
/*     */       }
/* 672 */       this.m_source.setMinGain(this.m_minGain);
/* 673 */       this.m_source.setMaxGain(this.m_maxGain);
/* 674 */       this.m_source.setGain(this.m_gain);
/* 675 */       this.m_source.setReferenceDistance(this.m_refDistance);
/* 676 */       this.m_source.setMaxDistance(this.m_maxDistance);
/* 677 */       this.m_source.setRolloffFactor(this.m_rollOffFactor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source getSource() {
/* 687 */     return this.m_source;
/*     */   }
/*     */   
/*     */   public void addFileLoaderEventListener(FileLoaderEventListener listener) {
/* 691 */     this.m_fileLoaderEventListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeFileLoaderEventLstener(FileLoaderEventListener listener) {
/* 695 */     this.m_fileLoaderEventListeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void fireOnLoadStartEvent(String fileName) {
/* 699 */     for (FileLoaderEventListener listener : this.m_fileLoaderEventListeners) {
/* 700 */       listener.onLoadStart(fileName);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireOnLoadCompleteEvent(String fileName) {
/* 705 */     for (FileLoaderEventListener listener : this.m_fileLoaderEventListeners)
/* 706 */       listener.onLoadComplete(fileName); 
/*     */   }
/*     */   
/*     */   public void fireOnLoadErrorEvent(String fileName, String error) {
/* 710 */     for (FileLoaderEventListener listener : this.m_fileLoaderEventListeners) {
/* 711 */       listener.onLoadError(fileName, error);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needMoreUpdates() {
/* 719 */     return this.m_needMoreUpdates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNeedMoreUpdates(boolean needMoreUpdates) {
/* 727 */     this.m_needMoreUpdates = needMoreUpdates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceProvider getSourceProvider() {
/* 735 */     return this.m_sourceProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceProvider(SourceProvider sourceProvider) {
/* 743 */     this.m_sourceProvider = sourceProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferProvider getBufferProvider() {
/* 751 */     return this.m_bufferProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBufferProvider(BufferProvider bufferProvider) {
/* 759 */     this.m_bufferProvider = bufferProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSource(Source source) {
/* 767 */     this.m_source = source;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\AudioSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */