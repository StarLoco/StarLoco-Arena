/*     */ package com.ankamagames.framework.graphics.sba;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagHeader;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
/*     */ import com.ankamagames.framework.graphics.sba.records.SBAHeader;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.CommonDefineTag;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.SBADefinitionTagDecoder;
/*     */ import com.ankamagames.framework.graphics.sba.records.tags.SBATagDecoder;
/*     */ import gnu.trove.TIntArrayList;
/*     */ import gnu.trove.TIntLongHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ public class IndexedDefinitionTagBuffer
/*     */ {
/*  36 */   private static Logger m_logger = Logger.getLogger(IndexedDefinitionTagBuffer.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private TIntLongHashMap m_definitionTagIndex;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  46 */   private final HashMap<String, Integer> m_linkageDictionary = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private TIntArrayList m_identifiers;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private InputBitStream m_bitStream;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  61 */   private static final TagDecoder m_lightDecoder = new SBADefinitionTagDecoder();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  66 */   private static final TagDecoder m_decoder = new SBATagDecoder();
/*     */   
/*  68 */   private short m_version = 3;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IndexedDefinitionTagBuffer(byte[] buffer)
/*     */     throws Exception
/*     */   {
/*  79 */     checkHeader(buffer);
/*  80 */     generateIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TIntArrayList getIdentifiers()
/*     */   {
/*  87 */     return this.m_identifiers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Set<String> getLinkages()
/*     */   {
/*  94 */     return this.m_linkageDictionary.keySet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(int identifier)
/*     */   {
/* 104 */     return this.m_definitionTagIndex.containsKey(identifier);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(String linkage)
/*     */   {
/* 114 */     return (linkage != null) && (this.m_linkageDictionary.containsKey(linkage));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getIdFromLinkage(String linkage)
/*     */   {
/* 122 */     if (linkage != null) {
/* 123 */       Integer value = (Integer)this.m_linkageDictionary.get(linkage);
/* 124 */       if (value != null)
/* 125 */         return value.intValue();
/*     */     }
/* 127 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefinitionTag getDefinitionTag(int identifier)
/*     */   {
/* 137 */     if (this.m_definitionTagIndex.containsKey(identifier)) {
/* 138 */       long offset = this.m_definitionTagIndex.get(identifier);
/* 139 */       return readTag(offset);
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefinitionTag getDefinitionTag(String linkage)
/*     */   {
/* 151 */     if (this.m_linkageDictionary.containsKey(linkage)) {
/* 152 */       return getDefinitionTag(((Integer)this.m_linkageDictionary.get(linkage)).intValue());
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void checkHeader(byte[] buffer)
/*     */     throws Exception
/*     */   {
/* 165 */     InputBitStream bitStream = new InputBitStream(buffer);
/*     */     
/*     */ 
/* 168 */     SBAHeader header = new SBAHeader();
/* 169 */     header.read(bitStream);
/*     */     
/* 171 */     this.m_version = header.getVersion();
/*     */     
/*     */ 
/* 174 */     if (this.m_version != 3)
/*     */     {
/* 176 */       m_logger.warn("Attention!! IndexedDefinitionTagBuffer version obsolète:" + this.m_version + "  courante:" + 3);
/*     */       
/* 178 */       if (!new SBADocument().isReadable(this.m_version)) {
/* 179 */         throw new Exception("La version lu est inconnue : " + this.m_version);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 184 */     if (header.isCompressed()) {
/* 185 */       bitStream.enableCompression();
/*     */     }
/*     */     
/* 188 */     byte[] tagsBuffer = bitStream.readBytes((int)header.getFileLength() - 8);
/* 189 */     this.m_bitStream = new InputBitStream(tagsBuffer);
/* 190 */     bitStream.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void generateIndex()
/*     */     throws Exception
/*     */   {
/* 200 */     this.m_definitionTagIndex = new TIntLongHashMap();
/* 201 */     this.m_identifiers = new TIntArrayList();
/*     */     
/*     */ 
/*     */     for (;;)
/*     */     {
/* 206 */       long offset = this.m_bitStream.getOffset();
/*     */       
/* 208 */       Tag tag = readTag(m_lightDecoder);
/* 209 */       if (tag.getCode() == 0) {
/*     */         break;
/*     */       }
/*     */       
/*     */ 
/* 214 */       if ((tag instanceof CommonDefineTag)) {
/* 215 */         CommonDefineTag commonDefineTag = (CommonDefineTag)tag;
/* 216 */         int identifier = commonDefineTag.getIdentifier();
/* 217 */         this.m_definitionTagIndex.put(identifier, offset);
/* 218 */         if (commonDefineTag.isLinked()) {
/* 219 */           this.m_linkageDictionary.put(commonDefineTag.getLinkage(), Integer.valueOf(identifier));
/*     */         }
/* 221 */         this.m_identifiers.add(identifier);
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
/*     */ 
/*     */   private Tag readTag(TagDecoder decoder)
/*     */     throws Exception
/*     */   {
/* 238 */     TagHeader tagHeader = TagReader.readTagHeader(this.m_bitStream);
/* 239 */     if (tagHeader.getLength() < 0)
/*     */     {
/* 241 */       throw new Exception("Longueur de Tag invalide : " + tagHeader.getLength());
/*     */     }
/*     */     
/*     */ 
/* 245 */     byte[] tagData = TagReader.readTagData(this.m_bitStream, tagHeader);
/* 246 */     Tag tag = TagReader.readTag(decoder, tagHeader, tagData, this.m_version);
/*     */     
/* 248 */     return tag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private DefinitionTag readTag(long offset)
/*     */   {
/*     */     try
/*     */     {
/* 259 */       this.m_bitStream.setOffset(offset);
/* 260 */       return (DefinitionTag)readTag(m_decoder);
/*     */     } catch (Exception e) {
/* 262 */       e.printStackTrace();
/*     */     }
/* 264 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\IndexedDefinitionTagBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */