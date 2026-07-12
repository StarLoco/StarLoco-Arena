/*     */ package com.ankamagames.framework.graphics.particlesystem;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineAttractionForce;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineBitmap;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineColorFader;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineDeformer;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineDirectionFollower;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineEmitter;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineFrictionalForce;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineLifeCondition;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineLinearForce;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineParticleBitmapModel;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineParticleSequenceModel;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineParticleSystem;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineRotorForce;
/*     */ import com.ankamagames.framework.graphics.aps.records.tags.DefineSequence;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.BaseAffector;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.ColorFader;
/*     */ import com.ankamagames.framework.graphics.particlesystem.affectors.RotorForce;
/*     */ 
/*     */ public abstract class ParticleSystemFactory<P extends ParticleSystem>
/*     */ {
/*     */   public abstract P getFreeParticleSystem(String paramString);
/*     */   
/*     */   protected void createParticleSystemFromTag(Tag tag, Object parent)
/*     */   {
/*  28 */     switch (tag.getCode())
/*     */     {
/*     */     case 1: 
/*  31 */       DefineParticleSystem defineParticleSystem = (DefineParticleSystem)tag;
/*     */       
/*  33 */       defineParticleSystem.initializeParticleSystem((ParticleSystem)parent);
/*     */       
/*  35 */       for (Tag childTag : defineParticleSystem.getTags())
/*     */       {
/*  37 */         createParticleSystemFromTag(childTag, parent);
/*     */       }
/*     */       
/*  40 */       break;
/*     */     
/*     */     case 3: 
/*  43 */       ParticleSystem particleSystem = (ParticleSystem)parent;
/*  44 */       DefineBitmap defineBitmap = (DefineBitmap)tag;
/*     */       
/*  46 */       particleSystem.addBitmap(defineBitmap.getBitmapId(), defineBitmap.getAlphaBitmap());
/*     */       
/*  48 */       break;
/*     */     
/*     */     case 12: 
/*  51 */       ParticleSystem particleSystem = (ParticleSystem)parent;
/*  52 */       DefineSequence defineSequence = (DefineSequence)tag;
/*     */       
/*  54 */       particleSystem.addSequence(defineSequence.getSequenceId(), defineSequence.getSequenceBuffer());
/*     */       
/*  56 */       break;
/*     */     
/*     */     case 2: 
/*  59 */       ParticleSystem particleSystem = (ParticleSystem)parent;
/*  60 */       DefineEmitter defineEmitter = (DefineEmitter)tag;
/*     */       
/*  62 */       Emitter emitter = new Emitter(particleSystem);
/*  63 */       defineEmitter.initializeEmitter(emitter);
/*     */       
/*  65 */       particleSystem.addEmitter(emitter);
/*     */       
/*  67 */       for (Tag childTag : defineEmitter.getTags()) {
/*  68 */         createParticleSystemFromTag(childTag, emitter);
/*     */       }
/*     */       
/*  71 */       break;
/*     */     case 4: 
/*  73 */       Emitter emitter = (Emitter)parent;
/*  74 */       DefineParticleBitmapModel defineParticleBitmapModel = (DefineParticleBitmapModel)tag;
/*     */       
/*  76 */       com.ankamagames.framework.graphics.particlesystem.particles.ParticleBitmapModel particle = new com.ankamagames.framework.graphics.particlesystem.particles.ParticleBitmapModel();
/*  77 */       defineParticleBitmapModel.initializeParticle(particle);
/*     */       
/*  79 */       emitter.addParticleModel(particle);
/*     */       
/*  81 */       break;
/*     */     case 13: 
/*  83 */       Emitter emitter = (Emitter)parent;
/*  84 */       DefineParticleSequenceModel defineParticleSequenceModel = (DefineParticleSequenceModel)tag;
/*     */       
/*  86 */       com.ankamagames.framework.graphics.particlesystem.particles.ParticleSequenceModel particle = new com.ankamagames.framework.graphics.particlesystem.particles.ParticleSequenceModel();
/*  87 */       defineParticleSequenceModel.initializeParticle(particle);
/*     */       
/*  89 */       emitter.addParticleModel(particle);
/*     */       
/*  91 */       break;
/*     */     case 5: 
/*  93 */       Emitter emitter = (Emitter)parent;
/*  94 */       DefineColorFader defineAffector = (DefineColorFader)tag;
/*     */       
/*  96 */       ColorFader colorFader = new ColorFader();
/*  97 */       defineAffector.initializeAffector(colorFader);
/*     */       
/*  99 */       emitter.addAffector(colorFader);
/*     */       
/* 101 */       for (Tag childTag : defineAffector.getTags()) {
/* 102 */         createParticleSystemFromTag(childTag, colorFader);
/*     */       }
/*     */       
/* 105 */       break;
/*     */     case 14: 
/* 107 */       Emitter emitter = (Emitter)parent;
/* 108 */       DefineDirectionFollower defineAffector = (DefineDirectionFollower)tag;
/*     */       
/* 110 */       com.ankamagames.framework.graphics.particlesystem.affectors.DirectionFollower directionFollower = new com.ankamagames.framework.graphics.particlesystem.affectors.DirectionFollower();
/* 111 */       defineAffector.initializeAffector(directionFollower);
/*     */       
/* 113 */       emitter.addAffector(directionFollower);
/*     */       
/* 115 */       for (Tag childTag : defineAffector.getTags()) {
/* 116 */         createParticleSystemFromTag(childTag, directionFollower);
/*     */       }
/*     */       
/* 119 */       break;
/*     */     case 6: 
/* 121 */       Emitter emitter = (Emitter)parent;
/* 122 */       DefineAttractionForce defineAffector = (DefineAttractionForce)tag;
/*     */       
/* 124 */       com.ankamagames.framework.graphics.particlesystem.affectors.AttractionForce attractionForce = new com.ankamagames.framework.graphics.particlesystem.affectors.AttractionForce();
/* 125 */       defineAffector.initializeAffector(attractionForce);
/*     */       
/* 127 */       emitter.addAffector(attractionForce);
/*     */       
/* 129 */       for (Tag childTag : defineAffector.getTags()) {
/* 130 */         createParticleSystemFromTag(childTag, attractionForce);
/*     */       }
/*     */       
/* 133 */       break;
/*     */     case 8: 
/* 135 */       Emitter emitter = (Emitter)parent;
/* 136 */       DefineRotorForce defineAffector = (DefineRotorForce)tag;
/*     */       
/* 138 */       RotorForce rotorForce = new RotorForce();
/* 139 */       defineAffector.initializeAffector(rotorForce);
/*     */       
/* 141 */       emitter.addAffector(rotorForce);
/*     */       
/* 143 */       for (Tag childTag : defineAffector.getTags()) {
/* 144 */         createParticleSystemFromTag(childTag, rotorForce);
/*     */       }
/*     */       
/* 147 */       break;
/*     */     case 7: 
/* 149 */       Emitter emitter = (Emitter)parent;
/* 150 */       DefineLinearForce defineAffector = (DefineLinearForce)tag;
/*     */       
/* 152 */       com.ankamagames.framework.graphics.particlesystem.affectors.LinearForce linearForce = new com.ankamagames.framework.graphics.particlesystem.affectors.LinearForce();
/* 153 */       defineAffector.initializeAffector(linearForce);
/*     */       
/* 155 */       emitter.addAffector(linearForce);
/*     */       
/* 157 */       for (Tag childTag : defineAffector.getTags()) {
/* 158 */         createParticleSystemFromTag(childTag, linearForce);
/*     */       }
/*     */       
/* 161 */       break;
/*     */     case 9: 
/* 163 */       Emitter emitter = (Emitter)parent;
/* 164 */       DefineFrictionalForce defineAffector = (DefineFrictionalForce)tag;
/*     */       
/* 166 */       com.ankamagames.framework.graphics.particlesystem.affectors.FrictionalForce frictionalForce = new com.ankamagames.framework.graphics.particlesystem.affectors.FrictionalForce();
/* 167 */       defineAffector.initializeAffector(frictionalForce);
/*     */       
/* 169 */       emitter.addAffector(frictionalForce);
/*     */       
/* 171 */       for (Tag childTag : defineAffector.getTags()) {
/* 172 */         createParticleSystemFromTag(childTag, frictionalForce);
/*     */       }
/*     */       
/* 175 */       break;
/*     */     case 10: 
/* 177 */       Emitter emitter = (Emitter)parent;
/* 178 */       DefineDeformer defineAffector = (DefineDeformer)tag;
/*     */       
/* 180 */       com.ankamagames.framework.graphics.particlesystem.affectors.Deformer deformer = new com.ankamagames.framework.graphics.particlesystem.affectors.Deformer();
/* 181 */       defineAffector.initializeAffector(deformer);
/*     */       
/* 183 */       emitter.addAffector(deformer);
/*     */       
/* 185 */       for (Tag childTag : defineAffector.getTags()) {
/* 186 */         createParticleSystemFromTag(childTag, deformer);
/*     */       }
/*     */       
/* 189 */       break;
/*     */     case 11: 
/* 191 */       BaseAffector emitter = (BaseAffector)parent;
/* 192 */       DefineLifeCondition defineParticle = (DefineLifeCondition)tag;
/*     */       
/* 194 */       com.ankamagames.framework.graphics.particlesystem.conditions.LifeCondition condition = new com.ankamagames.framework.graphics.particlesystem.conditions.LifeCondition();
/* 195 */       defineParticle.initializeCondition(condition);
/*     */       
/* 197 */       emitter.addCondition(condition);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\ParticleSystemFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */