/*     */ package org.postgresql.core;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.net.Socket;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
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
/*     */ public class PGStream
/*     */ {
/*     */   private final String host;
/*     */   private final int port;
/*     */   private Socket connection;
/*     */   private InputStream pg_input;
/*     */   private OutputStream pg_output;
/*     */   private byte[] streamBuffer;
/*     */   private Encoding encoding;
/*     */   private Writer encodingWriter;
/*     */   private byte[] byte_buf;
/*     */   
/*     */   public String getHost() {
/*     */     return this.host;
/*     */   }
/*     */   
/*     */   public int getPort() {
/*     */     return this.port;
/*     */   }
/*     */   
/*     */   public Socket getSocket() {
/*     */     return this.connection;
/*     */   }
/*     */   
/*     */   public boolean hasMessagePending() throws IOException {
/*     */     return (this.pg_input.available() > 0 || this.connection.getInputStream().available() > 0);
/*     */   }
/*     */   
/*     */   public void changeSocket(Socket socket) throws IOException {
/*     */     this.connection = socket;
/*     */     this.connection.setTcpNoDelay(true);
/*     */     this.pg_input = new BufferedInputStream(this.connection.getInputStream(), 8192);
/*     */     this.pg_output = new BufferedOutputStream(this.connection.getOutputStream(), 8192);
/*     */     if (this.encoding != null)
/*     */       setEncoding(this.encoding); 
/*     */   }
/*     */   
/*     */   public Encoding getEncoding() {
/*     */     return this.encoding;
/*     */   }
/*     */   
/*     */   public void setEncoding(Encoding encoding) throws IOException {
/*     */     if (this.encodingWriter != null)
/*     */       this.encodingWriter.close(); 
/*     */     this.encoding = encoding;
/*     */     OutputStream interceptor = new FilterOutputStream(this, this.pg_output)
/*     */       {
/*     */         private final PGStream this$0;
/*     */         
/*     */         public void flush() throws IOException {}
/*     */         
/*     */         public void close() throws IOException {
/*     */           super.flush();
/*     */         }
/*     */       };
/*     */     this.encodingWriter = encoding.getEncodingWriter(interceptor);
/*     */   }
/*     */   
/*     */   public PGStream(String host, int port) throws IOException {
/* 293 */     this.byte_buf = new byte[8192];
/*     */     this.host = host;
/*     */     this.port = port;
/*     */     changeSocket(new Socket(host, port));
/*     */     setEncoding(Encoding.getJVMEncoding("US-ASCII")); } public Writer getEncodingWriter() throws IOException {
/*     */     if (this.encodingWriter == null)
/*     */       throw new IOException("No encoding has been set on this connection"); 
/*     */     return this.encodingWriter;
/*     */   } public String ReceiveString(int len) throws IOException {
/* 302 */     if (len > this.byte_buf.length) {
/* 303 */       this.byte_buf = new byte[len];
/*     */     }
/* 305 */     Receive(this.byte_buf, 0, len);
/* 306 */     return this.encoding.decode(this.byte_buf, 0, len);
/*     */   }
/*     */   
/*     */   public void SendChar(int val) throws IOException {
/*     */     this.pg_output.write((byte)val);
/*     */   }
/*     */   public void SendInteger4(int val) throws IOException {
/*     */     SendChar(val >> 24 & 0xFF);
/*     */     SendChar(val >> 16 & 0xFF);
/*     */     SendChar(val >> 8 & 0xFF);
/*     */     SendChar(val & 0xFF);
/*     */   }
/* 318 */   public String ReceiveString() throws IOException { int i = 0;
/* 319 */     byte[] rst = this.byte_buf;
/* 320 */     int buflen = rst.length;
/*     */ 
/*     */     
/*     */     while (true) {
/* 324 */       int c = this.pg_input.read();
/*     */       
/* 326 */       if (c < 0) {
/* 327 */         throw new EOFException();
/*     */       }
/* 329 */       if (c == 0) {
/*     */         break;
/*     */       }
/* 332 */       if (i == buflen) {
/*     */ 
/*     */         
/* 335 */         buflen *= 2;
/* 336 */         if (buflen <= 0) {
/* 337 */           throw new IOException("Impossibly long string");
/*     */         }
/* 339 */         byte[] newrst = new byte[buflen];
/* 340 */         System.arraycopy(rst, 0, newrst, 0, i);
/* 341 */         rst = newrst;
/*     */       } 
/*     */       
/* 344 */       rst[i++] = (byte)c;
/*     */     } 
/*     */     
/* 347 */     return this.encoding.decode(rst, 0, i); } public void SendInteger2(int val) throws IOException {
/*     */     if (val < -32768 || val > 32767)
/*     */       throw new IOException("Tried to send an out-of-range integer as a 2-byte value: " + val); 
/*     */     SendChar(val >> 8 & 0xFF);
/*     */     SendChar(val & 0xFF);
/*     */   } public void Send(byte[] buf) throws IOException {
/*     */     this.pg_output.write(buf);
/*     */   } public void Send(byte[] buf, int siz) throws IOException {
/*     */     Send(buf, 0, siz);
/*     */   } public void Send(byte[] buf, int off, int siz) throws IOException {
/*     */     this.pg_output.write(buf, off, (buf.length - off < siz) ? (buf.length - off) : siz);
/*     */     if (buf.length - off < siz)
/*     */       for (int i = buf.length - off; i < siz; i++)
/*     */         this.pg_output.write(0);  
/*     */   } public byte[][] ReceiveTupleV3() throws IOException {
/* 362 */     int l_msgSize = ReceiveIntegerR(4);
/*     */     
/* 364 */     int l_nf = ReceiveIntegerR(2);
/* 365 */     byte[][] answer = new byte[l_nf][];
/*     */     
/* 367 */     for (int i = 0; i < l_nf; i++) {
/*     */       
/* 369 */       int l_size = ReceiveIntegerR(4);
/* 370 */       if (l_size != -1) {
/* 371 */         answer[i] = Receive(l_size);
/*     */       }
/*     */     } 
/* 374 */     return answer;
/*     */   } public int ReceiveChar() throws IOException { int c = this.pg_input.read(); if (c < 0)
/*     */       throw new EOFException();  return c; } public int ReceiveIntegerR(int siz) throws IOException { int n = 0;
/*     */     for (int i = 0; i < siz; i++) {
/*     */       int b = this.pg_input.read();
/*     */       if (b < 0)
/*     */         throw new EOFException(); 
/*     */       n = b | n << 8;
/*     */     } 
/*     */     switch (siz) {
/*     */       case 1:
/*     */         return (byte)n;
/*     */       case 2:
/*     */         return (short)n;
/*     */     } 
/*     */     return n; }
/* 390 */   public byte[][] ReceiveTupleV2(int nf, boolean bin) throws IOException { int bim = (nf + 7) / 8;
/* 391 */     byte[] bitmask = Receive(bim);
/* 392 */     byte[][] answer = new byte[nf][];
/*     */     
/* 394 */     int whichbit = 128;
/* 395 */     int whichbyte = 0;
/*     */     
/* 397 */     for (int i = 0; i < nf; i++) {
/*     */       
/* 399 */       boolean isNull = ((bitmask[whichbyte] & whichbit) == 0);
/* 400 */       whichbit >>= 1;
/* 401 */       if (whichbit == 0) {
/*     */         
/* 403 */         whichbyte++;
/* 404 */         whichbit = 128;
/*     */       } 
/* 406 */       if (!isNull) {
/*     */         
/* 408 */         int len = ReceiveIntegerR(4);
/* 409 */         if (!bin)
/* 410 */           len -= 4; 
/* 411 */         if (len < 0)
/* 412 */           len = 0; 
/* 413 */         answer[i] = Receive(len);
/*     */       } 
/*     */     } 
/* 416 */     return answer; }
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
/*     */   public byte[] Receive(int siz) throws IOException {
/* 428 */     byte[] answer = new byte[siz];
/* 429 */     Receive(answer, 0, siz);
/* 430 */     return answer;
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
/*     */   public void Receive(byte[] buf, int off, int siz) throws IOException {
/* 443 */     int s = 0;
/*     */     
/* 445 */     while (s < siz) {
/*     */       
/* 447 */       int w = this.pg_input.read(buf, off + s, siz - s);
/* 448 */       if (w < 0)
/* 449 */         throw new EOFException(); 
/* 450 */       s += w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void SendStream(InputStream inStream, int remaining) throws IOException {
/* 461 */     int expectedLength = remaining;
/* 462 */     if (this.streamBuffer == null) {
/* 463 */       this.streamBuffer = new byte[8192];
/*     */     }
/* 465 */     while (remaining > 0) {
/*     */       
/* 467 */       int readCount, count = (remaining > this.streamBuffer.length) ? this.streamBuffer.length : remaining;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 472 */         readCount = inStream.read(this.streamBuffer, 0, count);
/* 473 */         if (readCount < 0) {
/* 474 */           throw new EOFException(GT.tr("Premature end of input stream, expected {0} bytes, but only read {1}.", new Object[] { new Integer(expectedLength), new Integer(expectedLength - remaining) }));
/*     */         }
/*     */       } catch (IOException ioe) {
/*     */         
/* 478 */         while (remaining > 0) {
/*     */           
/* 480 */           Send(this.streamBuffer, count);
/* 481 */           remaining -= count;
/* 482 */           count = (remaining > this.streamBuffer.length) ? this.streamBuffer.length : remaining;
/*     */         } 
/* 484 */         throw new PGBindException(ioe);
/*     */       } 
/*     */       
/* 487 */       Send(this.streamBuffer, readCount);
/* 488 */       remaining -= readCount;
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
/*     */   public void flush() throws IOException {
/* 500 */     if (this.encodingWriter != null)
/* 501 */       this.encodingWriter.flush(); 
/* 502 */     this.pg_output.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ReceiveEOF() throws SQLException, IOException {
/* 511 */     int c = this.pg_input.read();
/* 512 */     if (c < 0)
/*     */       return; 
/* 514 */     throw new PSQLException(GT.tr("Expected an EOF from server, got: {0}", new Integer(c)), PSQLState.COMMUNICATION_ERROR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 524 */     if (this.encodingWriter != null) {
/* 525 */       this.encodingWriter.close();
/*     */     }
/* 527 */     this.pg_output.close();
/* 528 */     this.pg_input.close();
/* 529 */     this.connection.close();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\PGStream.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */