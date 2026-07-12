/*     */ package com.ankamagames.framework.fileFormat.tag.records;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TagDocumentHeader
/*     */ {
/*     */   public static final int HEADER_LENGTH = 8;
/*  22 */   private String m_uncompressedSignature = "TAG";
/*  23 */   private String m_compressedSignature = "tag";
/*     */   
/*     */ 
/*     */ 
/*     */   private short m_version;
/*     */   
/*     */ 
/*     */ 
/*     */   private long m_fileLength;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean m_compressed;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSignature(String signature)
/*     */   {
/*  42 */     if (signature == null) {
/*  43 */       System.err.println("Signature inexistante = null");
/*  44 */       return;
/*     */     }
/*     */     
/*  47 */     if (!Pattern.matches("[a-zA-Z]{3}", signature)) {
/*  48 */       System.err.println("Signature '" + signature + "'invalide. Seules les signatures de trois lettres sont autorisées");
/*     */     }
/*  50 */     this.m_uncompressedSignature = signature.toLowerCase();
/*  51 */     this.m_compressedSignature = signature.toUpperCase();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/*  58 */     this.m_version = 1;
/*  59 */     this.m_fileLength = 0L;
/*  60 */     this.m_compressed = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isCompressed()
/*     */   {
/*  67 */     return this.m_compressed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCompressed(boolean compressed)
/*     */   {
/*  75 */     this.m_compressed = compressed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getFileLength()
/*     */   {
/*  82 */     return this.m_fileLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFileLength(long fileLength)
/*     */   {
/*  90 */     this.m_fileLength = fileLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public short getVersion()
/*     */   {
/*  97 */     return this.m_version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVersion(short version)
/*     */   {
/* 105 */     this.m_version = version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(InputBitStream stream)
/*     */     throws IOException
/*     */   {
/* 116 */     String signature = new String(stream.readBytes(3));
/* 117 */     if (signature.equals(this.m_uncompressedSignature)) {
/* 118 */       this.m_compressed = false;
/* 119 */     } else if (signature.equals(this.m_compressedSignature)) {
/* 120 */       this.m_compressed = true;
/*     */     } else {
/* 122 */       throw new IOException("La signature '" + signature + "' du document est invalide!");
/*     */     }
/*     */     
/* 125 */     this.m_version = ((byte)stream.readUI8());
/* 126 */     this.m_fileLength = stream.readUI32();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(OutputBitStream outStream)
/*     */     throws IOException
/*     */   {
/* 136 */     if (isCompressed()) {
/* 137 */       outStream.writeBytes(this.m_compressedSignature.getBytes());
/*     */     } else {
/* 139 */       outStream.writeBytes(this.m_uncompressedSignature.getBytes());
/*     */     }
/*     */     
/* 142 */     outStream.writeUI8(this.m_version);
/* 143 */     outStream.writeUI32(this.m_fileLength);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\records\TagDocumentHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */