/*     */ package com.ankamagames.framework.graphics.opengl.base.material;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Material
/*     */   implements BaseMaterial
/*     */ {
/*  12 */   public static final float[] WhiteColor = { 1.0F, 1.0F, 1.0F, 1.0F };
/*  13 */   public static final float[] BlackColor = { 0.0F, 0.0F, 0.0F, 0.0F };
/*     */   
/*  15 */   protected float[] m_ambient = { 0.2F, 0.2F, 0.2F, 0.2F };
/*  16 */   protected float[] m_specular = { 0.0F, 0.0F, 0.0F, 0.0F };
/*  17 */   protected float[] m_emission = { 0.0F, 0.0F, 0.0F, 0.0F };
/*     */   
/*  19 */   protected float[] m_diffuseTopLeft = { 1.0F, 1.0F, 1.0F, 1.0F };
/*  20 */   protected float[] m_diffuseTopRight = { 0.5F, 0.5F, 0.5F, 1.0F };
/*  21 */   protected float[] m_diffuseBottomLeft = { 0.5F, 0.5F, 0.5F, 1.0F };
/*  22 */   protected float[] m_diffuseBottomRight = { 0.5F, 0.5F, 0.5F, 1.0F };
/*     */   
/*     */   protected boolean m_useAmbient;
/*     */   
/*     */   protected boolean m_useDiffuse;
/*     */   
/*     */   protected boolean m_useSpecular;
/*     */   
/*     */   protected boolean m_useEmission;
/*     */   
/*  32 */   protected boolean m_isDefault = true;
/*     */   
/*  34 */   protected boolean m_ambientChanged = true;
/*  35 */   protected boolean m_diffuseChanged = true;
/*  36 */   protected boolean m_specularChanged = true;
/*  37 */   protected boolean m_emissionChanged = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/*  46 */     if (!this.m_isDefault) {
/*  47 */       this.m_useAmbient = false;
/*  48 */       this.m_useDiffuse = false;
/*  49 */       this.m_useSpecular = false;
/*  50 */       this.m_useEmission = false;
/*     */       
/*  52 */       this.m_ambientChanged = true;
/*  53 */       this.m_diffuseChanged = true;
/*  54 */       this.m_specularChanged = true;
/*  55 */       this.m_emissionChanged = true;
/*     */       
/*  57 */       this.m_ambient[0] = (this.m_ambient[1] = this.m_ambient[2] = this.m_ambient[3] = 0.2F);
/*  58 */       this.m_specular[0] = (this.m_specular[1] = this.m_specular[2] = this.m_specular[3] = 0.0F);
/*  59 */       this.m_emission[0] = (this.m_emission[1] = this.m_emission[2] = this.m_emission[3] = 0.0F);
/*     */       
/*  61 */       this.m_diffuseTopLeft[0] = (this.m_diffuseTopLeft[1] = this.m_diffuseTopLeft[2] = 1.0F);this.m_diffuseTopLeft[3] = 1.0F;
/*  62 */       this.m_diffuseTopRight[0] = (this.m_diffuseTopRight[1] = this.m_diffuseTopRight[2] = 1.0F);this.m_diffuseTopRight[3] = 1.0F;
/*  63 */       this.m_diffuseBottomRight[0] = (this.m_diffuseBottomRight[1] = this.m_diffuseBottomRight[2] = 1.0F);this.m_diffuseBottomRight[3] = 1.0F;
/*  64 */       this.m_diffuseBottomLeft[0] = (this.m_diffuseBottomLeft[1] = this.m_diffuseBottomLeft[2] = 1.0F);this.m_diffuseBottomLeft[3] = 1.0F;
/*     */       
/*  66 */       this.m_isDefault = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasDefaultValue()
/*     */   {
/*  76 */     return this.m_isDefault;
/*     */   }
/*     */   
/*     */   public boolean useAmbient() {
/*  80 */     return this.m_useAmbient;
/*     */   }
/*     */   
/*     */   public boolean useDiffuse() {
/*  84 */     return this.m_useDiffuse;
/*     */   }
/*     */   
/*     */   public boolean useSpecular() {
/*  88 */     return this.m_useSpecular;
/*     */   }
/*     */   
/*     */   public boolean useEmission() {
/*  92 */     return this.m_useEmission;
/*     */   }
/*     */   
/*     */   public void setUseAmbient(boolean useAmbient) {
/*  96 */     this.m_useAmbient = useAmbient;
/*  97 */     this.m_ambientChanged = true;
/*  98 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setUseDiffuse(boolean useDiffuse) {
/* 102 */     this.m_useDiffuse = useDiffuse;
/* 103 */     this.m_diffuseChanged = true;
/* 104 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setUseSpecular(boolean useSpecular) {
/* 108 */     this.m_useSpecular = useSpecular;
/* 109 */     this.m_specularChanged = true;
/* 110 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setUseEmission(boolean useEmission) {
/* 114 */     this.m_useEmission = useEmission;
/* 115 */     this.m_emissionChanged = true;
/* 116 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setDefault(boolean value) {
/* 120 */     this.m_isDefault = true;
/*     */   }
/*     */   
/* 123 */   public float[] getAmbient() { return this.m_ambient; }
/*     */   
/*     */   public float[] getDiffuse()
/*     */   {
/* 127 */     return this.m_diffuseTopLeft;
/*     */   }
/*     */   
/*     */   public float[] getDiffuseTopLeft() {
/* 131 */     return this.m_diffuseTopLeft;
/*     */   }
/*     */   
/*     */   public float[] getDiffuseTopRight() {
/* 135 */     return this.m_diffuseTopRight;
/*     */   }
/*     */   
/*     */   public float[] getDiffuseBottomLeft() {
/* 139 */     return this.m_diffuseBottomLeft;
/*     */   }
/*     */   
/*     */   public float[] getDiffuseBottomRight() {
/* 143 */     return this.m_diffuseBottomRight;
/*     */   }
/*     */   
/*     */   public float[] getEmission() {
/* 147 */     return this.m_emission;
/*     */   }
/*     */   
/*     */   public float[] getSpecular() {
/* 151 */     return this.m_specular;
/*     */   }
/*     */   
/*     */   public void setAmbient(float r, float g, float b, float a) {
/* 155 */     this.m_ambient[0] = r;
/* 156 */     this.m_ambient[1] = g;
/* 157 */     this.m_ambient[2] = b;
/* 158 */     this.m_ambient[3] = a;
/* 159 */     this.m_ambientChanged = true;
/* 160 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setDiffuse(float r, float g, float b, float a) {
/* 164 */     this.m_diffuseTopLeft[0] = r;
/* 165 */     this.m_diffuseTopLeft[1] = g;
/* 166 */     this.m_diffuseTopLeft[2] = b;
/* 167 */     this.m_diffuseTopLeft[3] = a;
/*     */     
/* 169 */     this.m_diffuseBottomLeft[0] = r;
/* 170 */     this.m_diffuseBottomLeft[1] = g;
/* 171 */     this.m_diffuseBottomLeft[2] = b;
/* 172 */     this.m_diffuseBottomLeft[3] = a;
/*     */     
/* 174 */     this.m_diffuseTopRight[0] = r;
/* 175 */     this.m_diffuseTopRight[1] = g;
/* 176 */     this.m_diffuseTopRight[2] = b;
/* 177 */     this.m_diffuseTopRight[3] = a;
/*     */     
/* 179 */     this.m_diffuseBottomRight[0] = r;
/* 180 */     this.m_diffuseBottomRight[1] = g;
/* 181 */     this.m_diffuseBottomRight[2] = b;
/* 182 */     this.m_diffuseBottomRight[3] = a;
/*     */     
/* 184 */     this.m_diffuseChanged = true;
/* 185 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setEmission(float r, float g, float b, float a) {
/* 189 */     this.m_emission[0] = r;
/* 190 */     this.m_emission[1] = g;
/* 191 */     this.m_emission[2] = b;
/* 192 */     this.m_emission[3] = a;
/* 193 */     this.m_emissionChanged = true;
/* 194 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void setSpecular(float r, float g, float b, float a) {
/* 198 */     this.m_specular[0] = r;
/* 199 */     this.m_specular[1] = g;
/* 200 */     this.m_specular[2] = b;
/* 201 */     this.m_specular[3] = a;
/* 202 */     this.m_specularChanged = true;
/* 203 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAmbient(float[] ambient)
/*     */   {
/* 211 */     this.m_ambient = ambient;
/* 212 */     this.m_ambientChanged = true;
/* 213 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDiffuse(float[] diffuse)
/*     */   {
/* 221 */     this.m_diffuseTopLeft = diffuse;
/* 222 */     this.m_diffuseTopRight = diffuse;
/* 223 */     this.m_diffuseBottomLeft = diffuse;
/* 224 */     this.m_diffuseBottomRight = diffuse;
/* 225 */     this.m_diffuseChanged = true;
/* 226 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEmission(float[] emission)
/*     */   {
/* 234 */     this.m_emission = emission;
/* 235 */     this.m_emissionChanged = true;
/* 236 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSpecular(float[] specular)
/*     */   {
/* 244 */     this.m_specular = specular;
/* 245 */     this.m_specularChanged = true;
/* 246 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */   public void addToAmbient(float r, float g, float b, float a) {
/* 250 */     if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 251 */       this.m_ambientChanged = true;
/* 252 */       this.m_ambient[0] += r;
/* 253 */       this.m_ambient[1] += g;
/* 254 */       this.m_ambient[2] += b;
/* 255 */       this.m_ambient[3] += a;
/* 256 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToDiffuseTopLeft(float r, float g, float b, float a) {
/* 261 */     if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 262 */       this.m_diffuseChanged = true;
/* 263 */       this.m_diffuseTopLeft[0] += r;
/* 264 */       this.m_diffuseTopLeft[1] += g;
/* 265 */       this.m_diffuseTopLeft[2] += b;
/* 266 */       this.m_diffuseTopLeft[3] += a;
/* 267 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToDiffuseBottomLeft(float r, float g, float b, float a) {
/* 272 */     if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 273 */       this.m_diffuseChanged = true;
/* 274 */       this.m_diffuseBottomLeft[0] += r;
/* 275 */       this.m_diffuseBottomLeft[1] += g;
/* 276 */       this.m_diffuseBottomLeft[2] += b;
/* 277 */       this.m_diffuseBottomLeft[3] += a;
/* 278 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToDiffuseTopRight(float r, float g, float b, float a) {
/* 283 */     if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 284 */       this.m_diffuseChanged = true;
/* 285 */       this.m_diffuseTopRight[0] += r;
/* 286 */       this.m_diffuseTopRight[1] += g;
/* 287 */       this.m_diffuseTopRight[2] += b;
/* 288 */       this.m_diffuseTopRight[3] += a;
/* 289 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToDiffuseBottomRight(float r, float g, float b, float a) {
/* 294 */     if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 295 */       this.m_diffuseChanged = true;
/* 296 */       this.m_diffuseBottomRight[0] += r;
/* 297 */       this.m_diffuseBottomRight[1] += g;
/* 298 */       this.m_diffuseBottomRight[2] += b;
/* 299 */       this.m_diffuseBottomRight[3] += a;
/* 300 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToSpecular(float r, float g, float b, float a) {
/* 305 */     if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 306 */       this.m_specularChanged = true;
/* 307 */       this.m_specular[0] += r;
/* 308 */       this.m_specular[1] += g;
/* 309 */       this.m_specular[2] += b;
/* 310 */       this.m_specular[3] += a;
/* 311 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/* 315 */   public void addToEmission(float r, float g, float b, float a) { if ((r != 0.0F) || (g != 0.0F) || (b != 0.0F) || (a != 0.0F)) {
/* 316 */       this.m_emissionChanged = true;
/* 317 */       this.m_emission[0] += r;
/* 318 */       this.m_emission[1] += g;
/* 319 */       this.m_emission[2] += b;
/* 320 */       this.m_emission[3] += a;
/* 321 */       this.m_isDefault = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAmbientChanged()
/*     */   {
/* 330 */     return this.m_ambientChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAmbientChanged(boolean ambientChanged)
/*     */   {
/* 337 */     this.m_ambientChanged = ambientChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEmissionChanged()
/*     */   {
/* 344 */     return this.m_emissionChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setEmissionChanged(boolean emissionChanged)
/*     */   {
/* 351 */     this.m_emissionChanged = emissionChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isSpecularChanged()
/*     */   {
/* 358 */     return this.m_specularChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSpecularChanged(boolean specularChanged)
/*     */   {
/* 365 */     this.m_specularChanged = specularChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isDiffuseChanged()
/*     */   {
/* 372 */     return this.m_diffuseChanged;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDiffuseChanged(boolean diffuseChanged)
/*     */   {
/* 379 */     this.m_diffuseChanged = diffuseChanged;
/*     */   }
/*     */   
/*     */   public boolean hasChanged()
/*     */   {
/* 384 */     return (isDiffuseChanged()) || (isAmbientChanged()) || (isEmissionChanged()) || (isSpecularChanged());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(Material material)
/*     */   {
/* 393 */     setUseAmbient(material.useAmbient());
/* 394 */     setUseDiffuse(material.useDiffuse());
/* 395 */     setUseSpecular(material.useSpecular());
/* 396 */     setUseEmission(material.useEmission());
/*     */     
/* 398 */     if (useAmbient())
/* 399 */       setAmbient((float[])material.getAmbient().clone());
/* 400 */     if (useDiffuse())
/* 401 */       setDiffuse((float[])material.getDiffuse().clone());
/* 402 */     if (useSpecular())
/* 403 */       setSpecular((float[])material.getSpecular().clone());
/* 404 */     if (useEmission()) {
/* 405 */       setEmission((float[])material.getEmission().clone());
/*     */     }
/* 407 */     this.m_isDefault = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Material duplicate()
/*     */   {
/* 414 */     Material material = new Material();
/* 415 */     material.set(this);
/*     */     
/* 417 */     return material;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 421 */     return String.format("ambient  = [%f, %f, %f, %f]  use = %b\ndiffuse  = [%f, %f, %f, %f]  use = %b\nspecular = [%f, %f, %f, %f]  use = %b\nemission = [%f, %f, %f, %f]  use = %b", new Object[] {
/*     */     
/*     */ 
/*     */ 
/* 425 */       Float.valueOf(this.m_ambient[0]), Float.valueOf(this.m_ambient[1]), Float.valueOf(this.m_ambient[2]), Float.valueOf(this.m_ambient[3]), Boolean.valueOf(this.m_useAmbient), 
/* 426 */       Float.valueOf(this.m_diffuseTopLeft[0]), Float.valueOf(this.m_diffuseTopLeft[1]), Float.valueOf(this.m_diffuseTopLeft[2]), Float.valueOf(this.m_diffuseTopLeft[3]), Boolean.valueOf(this.m_useDiffuse), 
/* 427 */       Float.valueOf(this.m_specular[0]), Float.valueOf(this.m_specular[1]), Float.valueOf(this.m_specular[2]), Float.valueOf(this.m_specular[3]), Boolean.valueOf(this.m_useSpecular), 
/* 428 */       Float.valueOf(this.m_emission[0]), Float.valueOf(this.m_emission[1]), Float.valueOf(this.m_emission[2]), Float.valueOf(this.m_emission[3]), Boolean.valueOf(this.m_useEmission) });
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\material\Material.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */