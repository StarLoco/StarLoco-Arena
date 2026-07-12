/*     */ package com.ankamagames.framework.graphics.sba.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
/*     */ import java.io.IOException;
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
/*     */ public class DefineMovieClip
/*     */   extends DefineSequence
/*     */ {
/*     */   private int m_frameCount;
/*     */   private ArrayList<Tag> m_tags;
/*     */   
/*     */   public DefineMovieClip(int identifier) {
/*  31 */     this.m_code = 4;
/*  32 */     this.m_identifier = identifier;
/*  33 */     setLoopCount((short)0);
/*  34 */     this.m_tags = new ArrayList<Tag>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefineMovieClip() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Tag> getTags() {
/*  46 */     return this.m_tags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/*  53 */     return this.m_frameCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTag(Tag tag) {
/*  62 */     if (tag instanceof DefinitionTag) {
/*     */       return;
/*     */     }
/*  65 */     this.m_tags.add(tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTags(ArrayList<Tag> tags) {
/*  75 */     for (Tag tag : tags)
/*     */     {
/*  77 */       addTag(tag);
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
/*     */   public boolean removeTag(Tag tag) {
/*  89 */     return this.m_tags.remove(tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag removeTag(int index) {
/*  99 */     return this.m_tags.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/* 109 */     InputBitStream inStream = readDefinitionSequenceTagHeader(data);
/* 110 */     this.m_tags = new ArrayList<Tag>();
/*     */     while (true) {
/* 112 */       Tag tag = TagReader.readTag(SBATagDecoder.getInstance(), inStream, version);
/* 113 */       if (tag.getCode() == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 117 */       if (tag.getCode() == 1) {
/* 118 */         this.m_frameCount++;
/*     */       }
/* 120 */       this.m_tags.add(tag);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException {
/* 131 */     super.writeData(outStream);
/* 132 */     TagWriter.writeTags(outStream, this.m_tags);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\sba\records\tags\DefineMovieClip.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */