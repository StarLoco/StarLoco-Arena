/*     */ package com.ankamagames.framework.fileFormat.tag;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
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
/*     */ 
/*     */ public class TagDocument
/*     */ {
/*     */   protected TagDocumentHeader m_header;
/*  23 */   private ArrayList<Tag> m_tags = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public TagDocument()
/*     */   {
/*  29 */     createHeader();
/*  30 */     resetHeader();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void createHeader()
/*     */   {
/*  37 */     this.m_header = new TagDocumentHeader();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void resetHeader()
/*     */   {
/*  44 */     this.m_header.reset();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TagDocumentHeader getHeader()
/*     */   {
/*  51 */     return this.m_header;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVersion(short version)
/*     */   {
/*  60 */     this.m_header.setVersion(version);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getVersion()
/*     */   {
/*  69 */     return this.m_header.getVersion();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isReadable(short version)
/*     */   {
/*  79 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFileLength(long fileLength)
/*     */   {
/*  88 */     this.m_header.setFileLength(fileLength);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getFileLength()
/*     */   {
/*  95 */     return this.m_header.getFileLength();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCompressed(boolean compressed)
/*     */   {
/* 105 */     this.m_header.setCompressed(compressed);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCompressed()
/*     */   {
/* 114 */     return this.m_header.isCompressed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<Tag> getTags()
/*     */   {
/* 123 */     return this.m_tags;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addTag(Tag tag)
/*     */   {
/* 134 */     this.m_tags.add(tag);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addTags(ArrayList<Tag> tags)
/*     */   {
/* 145 */     this.m_tags.addAll(tags);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeTag(Tag tag)
/*     */   {
/* 156 */     return this.m_tags.remove(tag);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tag removeTag(int index)
/*     */   {
/* 167 */     return (Tag)this.m_tags.remove(index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 174 */     resetHeader();
/* 175 */     this.m_tags.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\TagDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */