/*     */ package com.ankamagames.framework.graphics.aps.records.tags;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.io.InputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagReader;
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.TagWriter;
/*     */ import com.ankamagames.framework.graphics.particlesystem.Emitter;
/*     */ import com.ankamagames.framework.graphics.particlesystem.ParticleModel;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.AttractionForce;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.ColorFader;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.Deformer;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.DirectionFollower;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.FrictionalForce;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.LinearForce;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.RotorForce;
/*     */ import com.ankamagames.framework.graphics.particlesystem.particles.ParticleBitmapModel;
/*     */ import com.ankamagames.framework.graphics.particlesystem.particles.ParticleSequenceModel;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefineEmitter
/*     */   extends Tag
/*     */ {
/*     */   private ArrayList<Tag> m_tags;
/*     */   private int m_spawnFrequency;
/*     */   private int m_maxParticlesCount;
/*     */   private int m_maxParticlesPerFrame;
/*     */   private int m_particleLifeTime;
/*     */   private float m_particleOffsetX;
/*     */   private float m_particleOffsetRandomX;
/*     */   private float m_particleOffsetY;
/*     */   private float m_particleOffsetRandomY;
/*     */   private float m_particleOffsetZ;
/*     */   private float m_particleOffsetRandomZ;
/*     */   private float m_particleVelocityX;
/*     */   private float m_particleVelocityRandomX;
/*     */   private float m_particleVelocityY;
/*     */   private float m_particleVelocityRandomY;
/*     */   private float m_particleVelocityZ;
/*     */   private float m_particleVelocityRandomZ;
/*     */   private int m_startSpawnTime;
/*     */   private int m_endSpawnTime;
/*     */   
/*     */   protected DefineEmitter() {}
/*     */   
/*     */   public DefineEmitter(Emitter emitter)
/*     */   {
/*  54 */     this.m_code = 2;
/*     */     
/*  56 */     this.m_spawnFrequency = emitter.getSpawnFrequency();
/*  57 */     this.m_maxParticlesCount = emitter.getMaxParticlesCount();
/*  58 */     this.m_maxParticlesPerFrame = emitter.getMaxParticlesPerSpawn();
/*  59 */     this.m_particleLifeTime = emitter.getParticleLifeTime();
/*  60 */     this.m_particleOffsetX = emitter.getParticleOffsetX();
/*  61 */     this.m_particleOffsetRandomX = emitter.getParticleOffsetRandomX();
/*  62 */     this.m_particleOffsetY = emitter.getParticleOffsetY();
/*  63 */     this.m_particleOffsetRandomY = emitter.getParticleOffsetRandomY();
/*  64 */     this.m_particleOffsetZ = emitter.getParticleOffsetZ();
/*  65 */     this.m_particleOffsetRandomZ = emitter.getParticleOffsetRandomZ();
/*  66 */     this.m_particleVelocityX = emitter.getParticleVelocityX();
/*  67 */     this.m_particleVelocityRandomX = emitter.getParticleVelocityRandomX();
/*  68 */     this.m_particleVelocityY = emitter.getParticleVelocityY();
/*  69 */     this.m_particleVelocityRandomY = emitter.getParticleVelocityRandomY();
/*  70 */     this.m_particleVelocityZ = emitter.getParticleVelocityZ();
/*  71 */     this.m_particleVelocityRandomZ = emitter.getParticleVelocityRandomZ();
/*  72 */     this.m_startSpawnTime = emitter.getStartSpawnTime();
/*  73 */     this.m_endSpawnTime = emitter.getEndSpawnTime();
/*     */     
/*  75 */     this.m_tags = new ArrayList();
/*  76 */     for (ParticleModel p : emitter.getParticlesModel())
/*     */     {
/*  78 */       if ((p instanceof ParticleBitmapModel))
/*     */       {
/*  80 */         this.m_tags.add(new DefineParticleBitmapModel((ParticleBitmapModel)p));
/*     */       }
/*  82 */       else if ((p instanceof ParticleSequenceModel))
/*     */       {
/*  84 */         this.m_tags.add(new DefineParticleSequenceModel((ParticleSequenceModel)p));
/*     */       }
/*     */     }
/*     */     
/*  88 */     for (BaseAffector affector : emitter.getAffectors())
/*     */     {
/*  90 */       if ((affector instanceof AttractionForce)) {
/*  91 */         this.m_tags.add(new DefineAttractionForce((AttractionForce)affector));
/*  92 */       } else if ((affector instanceof ColorFader)) {
/*  93 */         this.m_tags.add(new DefineColorFader((ColorFader)affector));
/*  94 */       } else if ((affector instanceof Deformer)) {
/*  95 */         this.m_tags.add(new DefineDeformer((Deformer)affector));
/*  96 */       } else if ((affector instanceof LinearForce)) {
/*  97 */         this.m_tags.add(new DefineLinearForce((LinearForce)affector));
/*  98 */       } else if ((affector instanceof FrictionalForce)) {
/*  99 */         this.m_tags.add(new DefineFrictionalForce((FrictionalForce)affector));
/* 100 */       } else if ((affector instanceof RotorForce)) {
/* 101 */         this.m_tags.add(new DefineRotorForce((RotorForce)affector));
/* 102 */       } else if ((affector instanceof DirectionFollower)) {
/* 103 */         this.m_tags.add(new DefineDirectionFollower((DirectionFollower)affector));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void initializeEmitter(Emitter emitter) {
/* 109 */     emitter.setSpawnFrequency(this.m_spawnFrequency);
/* 110 */     emitter.setMaxParticlesCount(this.m_maxParticlesCount);
/* 111 */     emitter.setMaxParticlesPerSpawn(this.m_maxParticlesPerFrame);
/* 112 */     emitter.setParticleLifeTime(this.m_particleLifeTime);
/* 113 */     emitter.setParticleOffsetX(this.m_particleOffsetX);
/* 114 */     emitter.setParticleOffsetRandomX(this.m_particleOffsetRandomX);
/* 115 */     emitter.setParticleOffsetY(this.m_particleOffsetY);
/* 116 */     emitter.setParticleOffsetRandomY(this.m_particleOffsetRandomY);
/* 117 */     emitter.setParticleOffsetZ(this.m_particleOffsetZ);
/* 118 */     emitter.setParticleOffsetRandomZ(this.m_particleOffsetRandomZ);
/* 119 */     emitter.setParticleVelocityX(this.m_particleVelocityX);
/* 120 */     emitter.setParticleVelocityRandomX(this.m_particleVelocityRandomX);
/* 121 */     emitter.setParticleVelocityY(this.m_particleVelocityY);
/* 122 */     emitter.setParticleVelocityRandomY(this.m_particleVelocityRandomY);
/* 123 */     emitter.setParticleVelocityZ(this.m_particleVelocityZ);
/* 124 */     emitter.setParticleVelocityRandomZ(this.m_particleVelocityRandomZ);
/* 125 */     emitter.setStartSpawnTime(this.m_startSpawnTime);
/* 126 */     emitter.setEndSpawnTime(this.m_endSpawnTime);
/*     */   }
/*     */   
/*     */   public ArrayList<Tag> getTags() {
/* 130 */     return this.m_tags;
/*     */   }
/*     */   
/*     */   public void setData(byte[] data, short version) throws IOException {
/* 134 */     InputBitStream inStream = new InputBitStream(data);
/*     */     
/* 136 */     this.m_spawnFrequency = inStream.readUI16();
/* 137 */     this.m_maxParticlesCount = inStream.readUI16();
/* 138 */     this.m_maxParticlesPerFrame = inStream.readUI16();
/* 139 */     this.m_particleLifeTime = inStream.readUI16();
/* 140 */     this.m_particleOffsetX = inStream.readFloat();
/* 141 */     this.m_particleOffsetRandomX = inStream.readFloat();
/* 142 */     this.m_particleOffsetY = inStream.readFloat();
/* 143 */     this.m_particleOffsetRandomY = inStream.readFloat();
/* 144 */     this.m_particleOffsetZ = inStream.readFloat();
/* 145 */     this.m_particleOffsetRandomZ = inStream.readFloat();
/* 146 */     this.m_particleVelocityX = inStream.readFloat();
/* 147 */     this.m_particleVelocityRandomX = inStream.readFloat();
/* 148 */     this.m_particleVelocityY = inStream.readFloat();
/* 149 */     this.m_particleVelocityRandomY = inStream.readFloat();
/* 150 */     this.m_particleVelocityZ = inStream.readFloat();
/* 151 */     this.m_particleVelocityRandomZ = inStream.readFloat();
/* 152 */     this.m_startSpawnTime = inStream.readUI16();
/* 153 */     this.m_endSpawnTime = inStream.readUI16();
/*     */     
/* 155 */     this.m_tags = new ArrayList();
/*     */     for (;;) {
/* 157 */       Tag tag = TagReader.readTag(APSTagDecoder.getInstance(), inStream, version);
/*     */       
/* 159 */       if (tag.getCode() == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 163 */       this.m_tags.add(tag);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeData(OutputBitStream outStream) throws IOException
/*     */   {
/* 169 */     outStream.writeUI16(this.m_spawnFrequency);
/* 170 */     outStream.writeUI16(this.m_maxParticlesCount);
/* 171 */     outStream.writeUI16(this.m_maxParticlesPerFrame);
/* 172 */     outStream.writeUI16(this.m_particleLifeTime);
/*     */     
/* 174 */     outStream.writeFloat(this.m_particleOffsetX);
/* 175 */     outStream.writeFloat(this.m_particleOffsetRandomX);
/* 176 */     outStream.writeFloat(this.m_particleOffsetY);
/* 177 */     outStream.writeFloat(this.m_particleOffsetRandomY);
/* 178 */     outStream.writeFloat(this.m_particleOffsetZ);
/* 179 */     outStream.writeFloat(this.m_particleOffsetRandomZ);
/*     */     
/* 181 */     outStream.writeFloat(this.m_particleVelocityX);
/* 182 */     outStream.writeFloat(this.m_particleVelocityRandomX);
/* 183 */     outStream.writeFloat(this.m_particleVelocityY);
/* 184 */     outStream.writeFloat(this.m_particleVelocityRandomY);
/* 185 */     outStream.writeFloat(this.m_particleVelocityZ);
/* 186 */     outStream.writeFloat(this.m_particleVelocityRandomZ);
/*     */     
/* 188 */     outStream.writeUI16(this.m_startSpawnTime);
/* 189 */     outStream.writeUI16(this.m_endSpawnTime);
/*     */     
/* 191 */     TagWriter.writeTags(outStream, this.m_tags);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\aps\records\tags\DefineEmitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */