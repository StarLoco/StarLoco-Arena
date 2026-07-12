/*     */ package com.ankamagames.framework.fileFormat.io;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InputBitStream
/*     */ {
/*     */   private InputStream m_stream;
/*     */   private int m_bitBuffer;
/*  21 */   private int m_bitCursor = 8;
/*     */   
/*  23 */   private boolean m_compressed = false;
/*     */   
/*     */ 
/*     */ 
/*     */   private long m_offset;
/*     */   
/*     */ 
/*     */ 
/*     */   public InputBitStream(InputStream stream)
/*     */   {
/*  33 */     this.m_stream = stream;
/*  34 */     this.m_offset = 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputBitStream(byte[] buffer)
/*     */   {
/*  43 */     this(new ByteArrayInputStream(buffer));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getOffset()
/*     */   {
/*  52 */     return this.m_offset;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setOffset(long offset)
/*     */     throws IOException
/*     */   {
/*  59 */     this.m_offset = offset;
/*  60 */     this.m_stream.reset();
/*  61 */     long i = this.m_stream.skip(this.m_offset);
/*  62 */     if (i != this.m_offset) {
/*  63 */       throw new IOException("InputBitStream : skip() a échoué à ignorer le bon nombre d'octets : m_offset=" + this.m_offset + ", saut effectif=" + i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void align()
/*     */   {
/*  71 */     this.m_bitCursor = 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/*  84 */     if (this.m_compressed) {
/*  85 */       throw new IllegalStateException("Impossible sur un flux compressé !");
/*     */     }
/*  87 */     return this.m_stream.available();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  96 */     this.m_stream.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void enableCompression()
/*     */   {
/* 103 */     if (!this.m_compressed) {
/* 104 */       this.m_stream = new BufferedInputStream(new InflaterInputStream(this.m_stream));
/*     */     }
/* 106 */     this.m_compressed = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void move(long delta)
/*     */     throws IOException
/*     */   {
/* 117 */     this.m_offset += delta;
/* 118 */     this.m_stream.reset();
/* 119 */     long i = this.m_stream.skip(this.m_offset);
/* 120 */     if (i != this.m_offset) {
/* 121 */       throw new IOException("InputBitStream : skip() a échoué à ignorer le bon nombre d'octets : m_offset=" + this.m_offset + ", saut effectif=" + i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean readBooleanBit()
/*     */     throws IOException
/*     */   {
/* 132 */     return readUnsignedBits(1) == 1L;
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
/*     */   public byte[] readBytes(int length)
/*     */     throws IOException
/*     */   {
/* 146 */     if (length > 0) {
/* 147 */       byte[] result = new byte[length];
/* 148 */       int totalRead = 0;
/* 149 */       while (totalRead < length) {
/* 150 */         int read = this.m_stream.read(result, totalRead, length - totalRead);
/* 151 */         if (read < 0) {
/* 152 */           endReached();
/* 153 */           return null;
/*     */         }
/* 155 */         totalRead += read;
/*     */       }
/*     */     } else {
/* 158 */       return new byte[0]; }
/*     */     byte[] result;
/* 160 */     this.m_offset += length;
/* 161 */     align();
/* 162 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double readDouble()
/*     */     throws IOException
/*     */   {
/* 173 */     byte[] buffer = readBytes(8);
/* 174 */     long longBits = (buffer[3] << 56) + ((buffer[2] & 0xFF) << 48) + ((buffer[1] & 0xFF) << 40) + (
/* 175 */       (buffer[0] & 0xFF) << 32) + ((buffer[7] & 0xFF) << 24) + ((buffer[6] & 0xFF) << 16) + (
/* 176 */       (buffer[5] & 0xFF) << 8) + ((buffer[4] & 0xFF) << 0);
/* 177 */     return Double.longBitsToDouble(longBits);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double readFP16()
/*     */     throws IOException
/*     */   {
/* 188 */     short value = readSI16();
/* 189 */     return value / 256.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double readFP32()
/*     */     throws IOException
/*     */   {
/* 200 */     int value = readSI32();
/* 201 */     return value / 65536.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double readFPBits(int numBits)
/*     */     throws IOException
/*     */   {
/* 214 */     long longNumber = readSignedBits(numBits);
/* 215 */     return longNumber / 65536.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float readFloat()
/*     */     throws IOException
/*     */   {
/* 226 */     return Float.intBitsToFloat(readSI32());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float readFloat16()
/*     */     throws IOException
/*     */   {
/* 238 */     int bits16 = readUI16();
/* 239 */     int sign = (bits16 & 0x8000) >> 15;
/* 240 */     int exponent16 = (bits16 & 0x7C00) >> 10;
/* 241 */     int mantissa16 = bits16 & 0x3FF;
/* 242 */     int exponent32 = 0;
/* 243 */     if (exponent16 != 0) {
/* 244 */       if (exponent16 == 31) {
/* 245 */         exponent32 = 255;
/*     */       } else {
/* 247 */         exponent32 = exponent16 - 15 + 127;
/*     */       }
/*     */     }
/* 250 */     int mantissa32 = mantissa16 << 13;
/* 251 */     int bits32 = sign << 31;
/* 252 */     bits32 |= exponent32 << 23;
/* 253 */     bits32 |= mantissa32;
/* 254 */     return Float.intBitsToFloat(bits32);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short readSI16()
/*     */     throws IOException
/*     */   {
/* 265 */     return (short)readUI16();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int readSI32()
/*     */     throws IOException
/*     */   {
/* 276 */     return (int)readUI32();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte readSI8()
/*     */     throws IOException
/*     */   {
/* 287 */     return (byte)readUI8();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long readSignedBits(int numBits)
/*     */     throws IOException
/*     */   {
/* 300 */     long result = readUnsignedBits(numBits);
/* 301 */     if ((result & 1L << numBits - 1) != 0L) {
/* 302 */       result |= -1L << numBits;
/*     */     }
/* 304 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String readString()
/*     */     throws IOException
/*     */   {
/* 316 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 317 */     fillBitBuffer();
/* 318 */     while (this.m_bitBuffer != 0) {
/* 319 */       baos.write(this.m_bitBuffer);
/* 320 */       fillBitBuffer();
/*     */     }
/* 322 */     byte[] buffer = baos.toByteArray();
/*     */     
/* 324 */     String encoding = "UTF-8";
/* 325 */     return new String(buffer, encoding);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int readUI16()
/*     */     throws IOException
/*     */   {
/* 336 */     fillBitBuffer();
/* 337 */     int result = this.m_bitBuffer;
/* 338 */     fillBitBuffer();
/* 339 */     result |= this.m_bitBuffer << 8;
/* 340 */     align();
/* 341 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long readUI32()
/*     */     throws IOException
/*     */   {
/* 352 */     fillBitBuffer();
/* 353 */     long result = this.m_bitBuffer;
/* 354 */     fillBitBuffer();
/* 355 */     result |= this.m_bitBuffer << 8;
/* 356 */     fillBitBuffer();
/* 357 */     result |= this.m_bitBuffer << 16;
/* 358 */     fillBitBuffer();
/* 359 */     result |= this.m_bitBuffer << 24;
/* 360 */     align();
/* 361 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short readUI8()
/*     */     throws IOException
/*     */   {
/* 372 */     fillBitBuffer();
/* 373 */     short result = (short)this.m_bitBuffer;
/* 374 */     align();
/* 375 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long readUnsignedBits(int numBits)
/*     */     throws IOException
/*     */   {
/* 388 */     if (numBits == 0) {
/* 389 */       return 0L;
/*     */     }
/* 391 */     int bitsLeft = numBits;
/* 392 */     long result = 0L;
/* 393 */     while (bitsLeft > 0) {
/* 394 */       if (this.m_bitCursor == 8)
/*     */       {
/* 396 */         fillBitBuffer();
/*     */       }
/*     */       
/*     */ 
/* 400 */       if ((this.m_bitBuffer & 1 << 7 - this.m_bitCursor) != 0)
/*     */       {
/* 402 */         result |= 1L << bitsLeft - 1;
/*     */       }
/* 404 */       this.m_bitCursor += 1;
/* 405 */       bitsLeft--;
/*     */     }
/* 407 */     return result;
/*     */   }
/*     */   
/*     */   private void endReached() throws IOException {
/* 411 */     throw new IOException("Fin inattendu de flux");
/*     */   }
/*     */   
/*     */   private void fillBitBuffer() throws IOException {
/* 415 */     this.m_bitBuffer = this.m_stream.read();
/* 416 */     this.m_offset += 1L;
/* 417 */     if (this.m_bitBuffer < 0) {
/* 418 */       endReached();
/*     */     }
/* 420 */     this.m_bitCursor = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\io\InputBitStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */