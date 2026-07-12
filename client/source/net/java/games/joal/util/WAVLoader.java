/*     */ package net.java.games.joal.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ import net.java.games.joal.ALConstants;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WAVLoader
/*     */   implements ALConstants
/*     */ {
/*     */   private static final int BUFFER_SIZE = 128000;
/*     */   
/*     */   public static WAVData loadFromFile(String paramString) throws UnsupportedAudioFileException, IOException {
/*  66 */     Object object = null;
/*  67 */     File file = new File(paramString);
/*  68 */     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
/*  69 */     return readFromStream(audioInputStream);
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
/*     */   public static WAVData loadFromStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*  86 */     Object object = null;
/*  87 */     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(paramInputStream);
/*  88 */     return readFromStream(audioInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static WAVData readFromStream(AudioInputStream paramAudioInputStream) throws UnsupportedAudioFileException, IOException {
/*  94 */     ReadableByteChannel readableByteChannel = Channels.newChannel(paramAudioInputStream);
/*  95 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*  96 */     int i = audioFormat.getChannels();
/*  97 */     int j = audioFormat.getSampleSizeInBits();
/*  98 */     char c = 'ᄀ';
/*     */     
/* 100 */     if (j == 8 && i == 1) {
/* 101 */       c = 'ᄀ';
/* 102 */     } else if (j == 16 && i == 1) {
/* 103 */       c = 'ᄁ';
/* 104 */     } else if (j == 8 && i == 2) {
/* 105 */       c = 'ᄂ';
/* 106 */     } else if (j == 16 && i == 2) {
/* 107 */       c = 'ᄃ';
/*     */     } 
/*     */     
/* 110 */     int k = Math.round(audioFormat.getSampleRate());
/* 111 */     int m = paramAudioInputStream.available();
/* 112 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(m);
/* 113 */     while (byteBuffer.remaining() > 0) {
/* 114 */       readableByteChannel.read(byteBuffer);
/*     */     }
/* 116 */     byteBuffer.rewind();
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (j == 16 && ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
/* 121 */       int n = byteBuffer.remaining();
/* 122 */       for (byte b = 0; b < n; b += 2) {
/* 123 */         byte b1 = byteBuffer.get(b);
/* 124 */         byte b2 = byteBuffer.get(b + 1);
/* 125 */         byteBuffer.put(b, b2);
/* 126 */         byteBuffer.put(b + 1, b1);
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     WAVData wAVData = new WAVData(byteBuffer, c, m, k, false);
/* 131 */     paramAudioInputStream.close();
/*     */     
/* 133 */     return wAVData;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joa\\util\WAVLoader.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */