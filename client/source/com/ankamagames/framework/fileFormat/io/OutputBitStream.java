/*     */ package com.ankamagames.framework.fileFormat.io;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
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
/*     */ public class OutputBitStream
/*     */ {
/*     */   private OutputStream m_stream;
/*     */   private ByteArrayOutputStream m_memoryStream;
/*     */   private int m_bitBuffer;
/*     */   private int m_bitCursor;
/*     */   private boolean m_compressed = false;
/*     */   private long m_offset;
/*     */   private boolean m_isMemoryStream;
/*     */   
/*     */   public OutputBitStream(OutputStream stream) {
/*  42 */     this.m_stream = stream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputBitStream() {
/*  51 */     this.m_memoryStream = new ByteArrayOutputStream();
/*  52 */     this.m_stream = this.m_memoryStream;
/*  53 */     this.m_isMemoryStream = true;
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
/*     */   public byte[] getData() {
/*  65 */     if (!this.m_isMemoryStream) {
/*  66 */       throw new IllegalStateException("Use this method only with memory streams!");
/*     */     }
/*     */     try {
/*  69 */       this.m_stream.close();
/*  70 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/*  73 */     return this.m_memoryStream.toByteArray();
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
/*     */   public static int getFPBitsLength(double value) {
/*  87 */     if (value == 0.0D) {
/*  88 */       return 1;
/*     */     }
/*  90 */     long fpBits = (long)(value * 65536.0D);
/*  91 */     return getSignedBitsLength(fpBits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getOffset() {
/* 100 */     return this.m_offset;
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
/*     */   public static int getSignedBitsLength(long value) {
/*     */     int nBits;
/* 115 */     if (value == 0L) {
/* 116 */       nBits = 0;
/*     */     } else {
/*     */       
/* 119 */       nBits = (int)(Math.floor(Math.log(Math.abs(value)) / Math.log(2.0D)) + 2.0D);
/*     */     } 
/* 121 */     return nBits;
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
/*     */   public static int getUnsignedBitsLength(long value) {
/* 135 */     if (value < 1L) {
/* 136 */       return 0;
/*     */     }
/* 138 */     return (int)(Math.floor(Math.log(value) / Math.log(2.0D)) + 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void align() throws IOException {
/* 149 */     if (this.m_bitCursor > 0) {
/* 150 */       this.m_stream.write(this.m_bitBuffer);
/* 151 */       this.m_offset++;
/* 152 */       this.m_bitCursor = 0;
/* 153 */       this.m_bitBuffer = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 164 */     align();
/* 165 */     this.m_stream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableCompression() {
/* 172 */     if (!this.m_compressed) {
/* 173 */       this.m_stream = new BufferedOutputStream(new DeflaterOutputStream(this.m_stream, new Deflater(9)));
/* 174 */       this.m_compressed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 185 */     this.m_stream.flush();
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
/*     */   public void writeBooleanBit(boolean value) throws IOException {
/* 199 */     writeUnsignedBits((value ? 1L : 0L), 1);
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
/*     */   public void writeBytes(byte[] buffer) throws IOException {
/* 212 */     align();
/* 213 */     if (buffer == null) {
/*     */       return;
/*     */     }
/* 216 */     this.m_stream.write(buffer);
/* 217 */     this.m_offset += buffer.length;
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
/*     */   public void writeDouble(double value) throws IOException {
/* 230 */     long longBits = Double.doubleToLongBits(value);
/* 231 */     byte[] buffer = new byte[8];
/* 232 */     buffer[0] = (byte)(int)(longBits >> 32L);
/* 233 */     buffer[1] = (byte)(int)(longBits >> 40L);
/* 234 */     buffer[2] = (byte)(int)(longBits >> 48L);
/* 235 */     buffer[3] = (byte)(int)(longBits >> 56L);
/* 236 */     buffer[4] = (byte)(int)longBits;
/* 237 */     buffer[5] = (byte)(int)(longBits >> 8L);
/* 238 */     buffer[6] = (byte)(int)(longBits >> 16L);
/* 239 */     buffer[7] = (byte)(int)(longBits >> 24L);
/* 240 */     writeBytes(buffer);
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
/*     */   public void writeFP16(double value) throws IOException {
/* 253 */     writeSI16((short)(int)(value * 256.0D));
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
/*     */   public void writeFP32(double value) throws IOException {
/* 266 */     writeSI32((int)(value * 65536.0D));
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
/*     */   public void writeFPBits(double value, int nBits) throws IOException {
/* 282 */     long fpBits = (long)(value * 65536.0D);
/* 283 */     writeSignedBits(fpBits, nBits);
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
/*     */   public void writeFloat(float value) throws IOException {
/* 296 */     writeSI32(Float.floatToIntBits(value));
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
/*     */   public void writeFloat16(float value) throws IOException {
/* 310 */     int bits32 = Float.floatToIntBits(value);
/* 311 */     int sign = Math.abs((bits32 & Integer.MIN_VALUE) >> 31);
/* 312 */     int exponent32 = (bits32 & 0x7F800000) >> 23;
/* 313 */     int mantissa32 = bits32 & 0x7FFFFF;
/* 314 */     int exponent16 = 0;
/* 315 */     if (exponent32 != 0) {
/* 316 */       if (exponent32 == 255) {
/* 317 */         exponent16 = 31;
/*     */       } else {
/* 319 */         exponent16 = exponent32 - 127 + 15;
/*     */       } 
/*     */     }
/* 322 */     int mantissa16 = 0;
/* 323 */     if (exponent16 < 0) {
/* 324 */       exponent16 = 0;
/* 325 */     } else if (exponent16 > 31) {
/* 326 */       exponent16 = 31;
/*     */     } else {
/* 328 */       mantissa16 = mantissa32 >> 13;
/*     */     } 
/* 330 */     int bits16 = sign << 15;
/* 331 */     bits16 |= exponent16 << 10;
/* 332 */     bits16 |= mantissa16;
/* 333 */     writeUI16(bits16);
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
/*     */   public void writeSI16(short value) throws IOException {
/* 346 */     align();
/* 347 */     this.m_stream.write(value & 0xFF);
/* 348 */     this.m_stream.write(value >> 8);
/* 349 */     this.m_offset += 2L;
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
/*     */   public void writeSI32(int value) throws IOException {
/* 362 */     align();
/* 363 */     this.m_stream.write(value & 0xFF);
/* 364 */     this.m_stream.write(value >> 8);
/* 365 */     this.m_stream.write(value >> 16);
/* 366 */     this.m_stream.write(value >> 24);
/* 367 */     this.m_offset += 4L;
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
/*     */   public void writeSI8(byte value) throws IOException {
/* 380 */     align();
/* 381 */     this.m_stream.write(value);
/* 382 */     this.m_offset++;
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
/*     */   public void writeSignedBits(long value, int nBits) throws IOException {
/* 398 */     int bitsNeeded = getSignedBitsLength(value);
/* 399 */     if (nBits < bitsNeeded) {
/* 400 */       throw new IOException("At least " + bitsNeeded + " bits needed for representation of " + value);
/*     */     }
/* 402 */     writeInteger(value, nBits);
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
/*     */   public void writeString(String string) throws IOException {
/* 415 */     writeBytes(string.getBytes("UTF-8"));
/* 416 */     this.m_stream.write(0);
/* 417 */     this.m_offset++;
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
/*     */   public void writeUI16(int value) throws IOException {
/* 430 */     align();
/* 431 */     this.m_stream.write(value & 0xFF);
/* 432 */     this.m_stream.write(value >> 8);
/* 433 */     this.m_offset += 2L;
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
/*     */   public void writeUI32(long value) throws IOException {
/* 446 */     align();
/* 447 */     this.m_stream.write((int)(value & 0xFFL));
/* 448 */     this.m_stream.write((int)(value >> 8L));
/* 449 */     this.m_stream.write((int)(value >> 16L));
/* 450 */     this.m_stream.write((int)(value >> 24L));
/* 451 */     this.m_offset += 4L;
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
/*     */   public void writeUI8(short value) throws IOException {
/* 464 */     align();
/* 465 */     this.m_stream.write(value);
/* 466 */     this.m_offset++;
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
/*     */   public void writeUnsignedBits(long value, int nBits) throws IOException {
/* 482 */     int bitsNeeded = getUnsignedBitsLength(value);
/* 483 */     if (nBits < bitsNeeded) {
/* 484 */       throw new IOException("At least " + bitsNeeded + " bits needed for representation of " + value + ". Used bits: " + nBits);
/*     */     }
/* 486 */     writeInteger(value, nBits);
/*     */   }
/*     */   
/*     */   private void writeInteger(long value, int nBits) throws IOException {
/* 490 */     int bitsLeft = nBits;
/* 491 */     while (bitsLeft > 0) {
/* 492 */       this.m_bitCursor++;
/*     */       
/* 494 */       if ((1L << bitsLeft - 1 & value) != 0L) {
/* 495 */         this.m_bitBuffer |= 1 << 8 - this.m_bitCursor;
/*     */       }
/* 497 */       if (this.m_bitCursor == 8) {
/*     */         
/* 499 */         this.m_stream.write(this.m_bitBuffer);
/* 500 */         this.m_offset++;
/* 501 */         this.m_bitCursor = 0;
/* 502 */         this.m_bitBuffer = 0;
/*     */       } 
/* 504 */       bitsLeft--;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\io\OutputBitStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */