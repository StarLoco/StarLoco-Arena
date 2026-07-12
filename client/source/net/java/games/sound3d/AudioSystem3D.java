/*     */ package net.java.games.sound3d;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ import net.java.games.joal.AL;
/*     */ import net.java.games.joal.ALC;
/*     */ import net.java.games.joal.ALCcontext;
/*     */ import net.java.games.joal.ALCdevice;
/*     */ import net.java.games.joal.ALException;
/*     */ import net.java.games.joal.ALFactory;
/*     */ import net.java.games.joal.util.WAVData;
/*     */ import net.java.games.joal.util.WAVLoader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AudioSystem3D
/*     */ {
/*     */   private static AL al;
/*     */   private static ALC alc;
/*     */   private static Listener listener;
/*     */   
/*     */   public static void init() throws ALException {
/*  61 */     al = ALFactory.getAL();
/*  62 */     alc = ALFactory.getALC();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Context createContext(Device paramDevice) {
/*  73 */     Context context = null;
/*  74 */     ALCcontext aLCcontext = alc.alcCreateContext(paramDevice.realDevice, null);
/*  75 */     context = new Context(alc, aLCcontext, paramDevice);
/*  76 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeContextCurrent(Context paramContext) {
/*  85 */     ALCcontext aLCcontext = null;
/*     */     
/*  87 */     if (paramContext != null) {
/*  88 */       aLCcontext = paramContext.realContext;
/*     */     }
/*     */     
/*  91 */     alc.alcMakeContextCurrent(aLCcontext);
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
/*     */   public static Device openDevice(String paramString) {
/* 104 */     Device device = null;
/* 105 */     ALCdevice aLCdevice = alc.alcOpenDevice(paramString);
/* 106 */     device = new Device(alc, aLCdevice);
/*     */     
/* 108 */     return device;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Buffer[] generateBuffers(int paramInt) {
/* 119 */     Buffer[] arrayOfBuffer = new Buffer[paramInt];
/* 120 */     int[] arrayOfInt = new int[paramInt];
/* 121 */     al.alGenBuffers(paramInt, arrayOfInt, 0);
/*     */     
/* 123 */     for (byte b = 0; b < paramInt; b++) {
/* 124 */       arrayOfBuffer[b] = new Buffer(al, arrayOfInt[b]);
/*     */     }
/*     */     
/* 127 */     return arrayOfBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Buffer loadBuffer(String paramString) throws IOException, UnsupportedAudioFileException {
/* 146 */     Buffer[] arrayOfBuffer = generateBuffers(1);
/* 147 */     Buffer buffer = arrayOfBuffer[0];
/*     */     
/* 149 */     WAVData wAVData = WAVLoader.loadFromFile(paramString);
/* 150 */     buffer.configure(wAVData.data, wAVData.format, wAVData.freq);
/*     */     
/* 152 */     return buffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Source loadSource(String paramString) throws IOException, UnsupportedAudioFileException {
/* 171 */     Buffer buffer = loadBuffer(paramString);
/*     */     
/* 173 */     return generateSource(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Source[] generateSources(int paramInt) {
/* 184 */     Source[] arrayOfSource = new Source[paramInt];
/* 185 */     int[] arrayOfInt = new int[paramInt];
/* 186 */     al.alGenSources(paramInt, arrayOfInt, 0);
/*     */     
/* 188 */     for (byte b = 0; b < paramInt; b++) {
/* 189 */       arrayOfSource[b] = new Source(al, arrayOfInt[b]);
/*     */     }
/*     */     
/* 192 */     return arrayOfSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Source generateSource(Buffer paramBuffer) {
/* 203 */     Source source = null;
/* 204 */     Source[] arrayOfSource = generateSources(1);
/* 205 */     source = arrayOfSource[0];
/* 206 */     source.setBuffer(paramBuffer);
/*     */     
/* 208 */     return source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Listener getListener() {
/* 217 */     if (listener == null) {
/* 218 */       listener = new Listener(al);
/*     */     }
/*     */     
/* 221 */     return listener;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\sound3d\AudioSystem3D.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */