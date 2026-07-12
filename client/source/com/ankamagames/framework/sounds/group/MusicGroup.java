/*     */ package com.ankamagames.framework.sounds.group;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentEntry;
/*     */ import com.ankamagames.framework.fileFormat.xml.XMLDocumentAccessor;
/*     */ import com.ankamagames.framework.sounds.AudioSource;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
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
/*     */ public class MusicGroup
/*     */   extends AudioSourceGroup
/*     */ {
/*     */   private AudioSource m_mainMusic;
/*     */   private AudioSource m_crossMusic;
/*  31 */   private final Object m_audioSourcesMutex = new Object();
/*     */   
/*  33 */   private final ArrayList<String> m_playList = new ArrayList<String>();
/*     */   
/*     */   public MusicGroup(String name) {
/*  36 */     super(name);
/*  37 */     this.m_mainMusic = null;
/*  38 */     this.m_crossMusic = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioSource getMainMusic() {
/*  45 */     return this.m_mainMusic;
/*     */   }
/*     */   
/*     */   public void setMainMusic(String fileName, boolean bStream, boolean bStereo) {
/*     */     try {
/*  50 */       synchronized (this.m_audioSourcesMutex)
/*     */       {
/*  52 */         AudioSource s = new AudioSource(fileName, bStream, getSourceProvider(), getBufferProvider(), bStereo, true);
/*  53 */         if (this.m_mainMusic != null) {
/*  54 */           this.m_mainMusic.stop();
/*  55 */           this.m_mainMusic.end();
/*     */         } 
/*     */         
/*  58 */         this.m_mainMusic = s;
/*  59 */         s.play();
/*  60 */         s.setLoop(true);
/*  61 */         s.setMaxGain(getMaxGain());
/*     */       }
/*     */     
/*     */     }
/*  65 */     catch (Exception e) {
/*  66 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void crossFade(String fileName, boolean bStream, boolean bStereo) {
/*  72 */     synchronized (this.m_audioSourcesMutex) {
/*     */       
/*  74 */       if (this.m_mainMusic != null) {
/*  75 */         if (this.m_mainMusic.getSourceFileName().equals(fileName))
/*     */           return; 
/*  77 */       } else if (this.m_crossMusic != null && 
/*  78 */         this.m_crossMusic.getSourceFileName().equals(fileName)) {
/*     */         return;
/*     */       } 
/*     */       
/*  82 */       AudioSource s = new AudioSource(fileName, bStream, getSourceProvider(), getBufferProvider(), bStereo, true);
/*     */       
/*  84 */       s.setGain(s.getMinGain());
/*  85 */       crossFade(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void crossFade(AudioSource crossMusic) {
/*  92 */     if (crossMusic == null) {
/*     */       return;
/*     */     }
/*  95 */     crossMusic.setGain(crossMusic.getMinGain());
/*     */     
/*     */     try {
/*  98 */       if (this.m_crossMusic != null) {
/*  99 */         this.m_crossMusic.stop();
/* 100 */         this.m_crossMusic.end();
/*     */       } 
/* 102 */     } catch (Exception ex) {
/* 103 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 106 */     this.m_crossMusic = crossMusic;
/* 107 */     this.m_crossMusic.setMaxGain(getMaxGain());
/*     */     
/* 109 */     if (this.m_mainMusic != null) {
/* 110 */       this.m_mainMusic.fadeOut(0.02F);
/*     */     }
/* 112 */     if (this.m_crossMusic != null) {
/* 113 */       this.m_crossMusic.play();
/* 114 */       this.m_crossMusic.fadeIn(0.1F);
/* 115 */       this.m_crossMusic.setLoop(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 124 */     synchronized (this.m_audioSourcesMutex) {
/*     */       
/* 126 */       ObservedListener listener = getListener();
/* 127 */       float x = 0.0F;
/* 128 */       float y = 0.0F;
/* 129 */       float z = 0.0F;
/*     */       
/* 131 */       if (listener != null) {
/* 132 */         x = listener.getListenerPositionX();
/* 133 */         y = listener.getListenerPositionY();
/* 134 */         z = listener.getListenerPositionZ();
/*     */       } 
/*     */       
/*     */       try {
/* 138 */         if (this.m_mainMusic != null) {
/* 139 */           this.m_mainMusic.setPosition(x, y, z);
/* 140 */           this.m_mainMusic.update();
/*     */         } 
/*     */         
/* 143 */         if (this.m_crossMusic != null) {
/* 144 */           this.m_crossMusic.setPosition(x, y, z);
/* 145 */           this.m_crossMusic.update();
/*     */         } 
/* 147 */       } catch (Exception e) {
/* 148 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 151 */       if (this.m_crossMusic != null && ((
/* 152 */         this.m_mainMusic != null && !this.m_mainMusic.isPlaying()) || this.m_mainMusic == null)) {
/* 153 */         this.m_mainMusic = this.m_crossMusic;
/* 154 */         this.m_crossMusic = null;
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
/*     */ 
/*     */   
/*     */   public ArrayList<AudioSource> getSources() {
/* 168 */     return null;
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
/* 196 */     if (this.m_mainMusic != null)
/* 197 */       this.m_mainMusic.setGain(newGain); 
/* 198 */     if (this.m_crossMusic != null)
/* 199 */       this.m_crossMusic.setGain(newGain); 
/*     */   }
/*     */   
/*     */   public void onMaxGainChanged(float previousMaxGain, float newMaxGain) {
/* 203 */     if (this.m_mainMusic != null)
/* 204 */       this.m_mainMusic.setMaxGain(newMaxGain); 
/* 205 */     if (this.m_crossMusic != null) {
/* 206 */       this.m_crossMusic.setMaxGain(newMaxGain);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMuteChanged(boolean previousMute, boolean newMute) {
/* 216 */     if (newMute) {
/* 217 */       if (this.m_mainMusic != null)
/* 218 */         this.m_mainMusic.setMaxGain(0.0F); 
/* 219 */       if (this.m_crossMusic != null)
/* 220 */         this.m_crossMusic.setMaxGain(0.0F); 
/*     */     } else {
/* 222 */       if (this.m_mainMusic != null)
/* 223 */         this.m_mainMusic.setMaxGain(getMaxGain()); 
/* 224 */       if (this.m_crossMusic != null)
/* 225 */         this.m_crossMusic.setMaxGain(getMaxGain()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadPlayList(String xmlPlaylistFileName) {
/* 230 */     InputStream stream = null;
/*     */     
/*     */     try {
/* 233 */       URL jarUrl = new URL(xmlPlaylistFileName);
/* 234 */       stream = jarUrl.openStream();
/* 235 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 238 */       File file = new File(xmlPlaylistFileName);
/*     */       try {
/* 240 */         stream = new FileInputStream(file);
/* 241 */       } catch (FileNotFoundException e1) {
/* 242 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 246 */     loadPlayList(stream, xmlPlaylistFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadPlayList(InputStream stream, String xmlPlaylistFileName) {
/*     */     try {
/* 256 */       XMLDocumentAccessor accessor = XMLDocumentAccessor.getInstance();
/* 257 */       DocumentContainer document = accessor.getNewDocumentContainer();
/* 258 */       accessor.open(stream);
/* 259 */       accessor.read(document);
/* 260 */       accessor.close();
/*     */ 
/*     */       
/* 263 */       DocumentEntry playlist = document.getEntryByName("playlist");
/* 264 */       if (playlist != null) {
/*     */         
/* 266 */         ArrayList<DocumentEntry> musics = playlist.getDirectChildrenByName("music");
/* 267 */         if (musics != null) {
/*     */           
/* 269 */           for (DocumentEntry music : musics) {
/* 270 */             DocumentEntry fileNameParam = music.getParameterByName("fileName");
/*     */             
/* 272 */             if (fileNameParam != null) {
/*     */               
/* 274 */               String fileName = fileNameParam.getStringValue();
/* 275 */               boolean bLoaded = false;
/*     */               try {
/* 277 */                 URL musicUrl = new URL(fileName);
/* 278 */                 musicUrl.openStream();
/* 279 */                 addPlaylistEntry(fileNameParam.getStringValue());
/* 280 */                 bLoaded = true;
/* 281 */               } catch (Exception exception) {}
/*     */               
/* 283 */               if (!bLoaded) {
/* 284 */                 File musicFile = new File(fileName);
/* 285 */                 if (musicFile.exists()) {
/* 286 */                   addPlaylistEntry(fileNameParam.getStringValue()); continue;
/*     */                 } 
/* 288 */                 m_logger.error("Le chemin vers le fichier de musique " + fileName + " n'existe pas");
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 294 */           m_logger.warn("Pas d'entrée 'music' dans la playlist " + xmlPlaylistFileName);
/*     */         } 
/*     */       } else {
/* 297 */         m_logger.error("Impossible de trouver l'entrée 'playlist' dans le fichier de configuration " + xmlPlaylistFileName);
/*     */       } 
/* 299 */     } catch (Exception e) {
/* 300 */       m_logger.error("Exception levée lors du chargement du fichier de playlist : " + xmlPlaylistFileName + "(" + e.getMessage() + ")");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPlaylistEntry(String fileName) {
/* 308 */     this.m_playList.add(fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playRandomMusic(boolean bCrossFade) {
/* 316 */     if (this.m_playList.isEmpty()) {
/*     */       return;
/*     */     }
/* 319 */     double range = (this.m_playList.size() - 1);
/* 320 */     int track = (int)Math.round(Math.random() * range);
/* 321 */     String fileName = this.m_playList.get(track);
/*     */     
/* 323 */     if (bCrossFade) {
/* 324 */       crossFade(fileName, true, true);
/*     */     } else {
/* 326 */       setMainMusic(fileName, true, true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\sounds\group\MusicGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */