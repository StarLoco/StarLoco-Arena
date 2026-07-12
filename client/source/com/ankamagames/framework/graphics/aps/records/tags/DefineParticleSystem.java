/*     */ package com.ankamagames.framework.graphics.aps.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
/*     */ import com.ankamagames.framework.graphics.image.AlphaBitmapData;
/*     */ import com.ankamagames.framework.graphics.particlesystem.Emitter;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleSystem;
/*     */ import gnu.trove.TIntObjectIterator;
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
/*     */ public class DefineParticleSystem
/*     */   extends Tag
/*     */ {
/*     */   private ArrayList<Tag> m_tags;
/*     */   private String m_systemName;
/*     */   private int m_duration;
/*     */   private boolean m_geocentric;
/*     */   
/*     */   protected DefineParticleSystem() {}
/*     */   
/*     */   public DefineParticleSystem(ParticleSystem particleSystem) {
/*  38 */     this.m_code = 1;
/*     */     
/*  40 */     this.m_geocentric = particleSystem.isGeocentric();
/*  41 */     this.m_duration = particleSystem.getSystemDuration();
/*  42 */     this.m_systemName = particleSystem.getSystemName();
/*     */     
/*  44 */     this.m_tags = new ArrayList<Tag>();
/*     */     
/*  46 */     for (Emitter e : particleSystem.getEmitters())
/*     */     {
/*  48 */       this.m_tags.add(new DefineEmitter(e));
/*     */     }
/*     */     
/*  51 */     for (TIntObjectIterator<AlphaBitmapData> tIntObjectIterator = particleSystem.getBitmapLibrary().iterator(); tIntObjectIterator.hasNext(); ) {
/*     */       
/*  53 */       tIntObjectIterator.advance();
/*  54 */       this.m_tags.add(new DefineBitmap(tIntObjectIterator.key(), (AlphaBitmapData)tIntObjectIterator.value()));
/*     */     } 
/*     */     
/*  57 */     for (TIntObjectIterator<byte[]> it = particleSystem.getSequenceLibrary().iterator(); it.hasNext(); ) {
/*     */       
/*  59 */       it.advance();
/*  60 */       this.m_tags.add(new DefineSequence(it.key(), (byte[])it.value()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeParticleSystem(ParticleSystem particleSystem) {
/*  66 */     particleSystem.setSystemName(this.m_systemName);
/*  67 */     particleSystem.setDuration(this.m_duration);
/*  68 */     particleSystem.setGeocentric(this.m_geocentric);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException {
/*  73 */     outStream.writeString(this.m_systemName);
/*  74 */     outStream.align();
/*  75 */     outStream.writeBooleanBit(this.m_geocentric);
/*  76 */     outStream.writeUI16(this.m_duration);
/*     */     
/*  78 */     TagWriter.writeTags(outStream, this.m_tags);
/*     */   }
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/*  82 */     InputBitStream inStream = new InputBitStream(data);
/*     */     
/*  84 */     this.m_systemName = inStream.readString();
/*  85 */     inStream.align();
/*  86 */     this.m_geocentric = inStream.readBooleanBit();
/*  87 */     this.m_duration = inStream.readUI16();
/*     */     
/*  89 */     this.m_tags = new ArrayList<Tag>();
/*     */     while (true) {
/*  91 */       Tag tag = TagReader.readTag(APSTagDecoder.getInstance(), inStream, version);
/*     */       
/*  93 */       if (tag.getCode() == 0) {
/*     */         break;
/*     */       }
/*     */       
/*  97 */       this.m_tags.add(tag);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<Tag> getTags() {
/* 103 */     return this.m_tags;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */