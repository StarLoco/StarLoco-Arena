/*     */ package com.ankamagames.framework.graphics.opengl.base.effects.shaders;
/*     */ 
/*     */ import com.sun.opengl.cg.CGcontext;
/*     */ import com.sun.opengl.cg.CGparameter;
/*     */ import com.sun.opengl.cg.CGprogram;
/*     */ import com.sun.opengl.cg.CgGL;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import javax.media.opengl.GL;
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
/*     */ public abstract class ShaderProgram
/*     */ {
/*     */   private String m_name;
/*     */   private String m_fileName;
/*     */   private CGcontext m_context;
/*     */   private int m_profile;
/*     */   protected CGprogram m_program;
/*     */   private int m_type;
/*     */   protected CGparameter m_modelViewMatrix;
/*     */   protected CGparameter m_projectionMatrix;
/*  37 */   private static final int[] PS_PROFILES = {
/*     */   
/*     */ 
/*  40 */     6162, 
/*  41 */     6161, 
/*  42 */     6160, 
/*  43 */     6159, 
/*  44 */     7000 };
/*     */   
/*  46 */   private static final int[] VS_PROFILES = {
/*     */   
/*     */ 
/*  49 */     6154, 
/*  50 */     6153, 
/*  51 */     6150 };
/*     */   
/*     */   public ShaderProgram(String name, String fileName, int type)
/*     */   {
/*  55 */     this.m_fileName = fileName;
/*  56 */     this.m_type = type;
/*  57 */     this.m_name = name;
/*     */   }
/*     */   
/*     */   public void setContext(CGcontext context) {
/*  61 */     this.m_context = context;
/*     */   }
/*     */   
/*     */   private String valueToString(int value) {
/*  65 */     String ret = "";
/*     */     
/*  67 */     switch (value) {
/*     */     case 7000: 
/*  69 */       ret = "CG_PROFILE_ARBFP1";
/*  70 */       break;
/*     */     case 6150: 
/*  72 */       ret = "CG_PROFILE_ARBVP1";
/*  73 */       break;
/*     */     case 6159: 
/*  75 */       ret = "CG_PROFILE_PS_1_1";
/*  76 */       break;
/*     */     case 6160: 
/*  78 */       ret = "CG_PROFILE_PS_1_2";
/*  79 */       break;
/*     */     case 6161: 
/*  81 */       ret = "CG_PROFILE_PS_1_3";
/*  82 */       break;
/*     */     case 6162: 
/*  84 */       ret = "CG_PROFILE_PS_2_0";
/*  85 */       break;
/*     */     case 6163: 
/*  87 */       ret = "CG_PROFILE_PS_2_X";
/*  88 */       break;
/*     */     case 6153: 
/*  90 */       ret = "CG_PROFILE_VS_1_1";
/*  91 */       break;
/*     */     case 6154: 
/*  93 */       ret = "CG_PROFILE_VS_2_0";
/*  94 */       break;
/*     */     case 6155: 
/*  96 */       ret = "CG_PROFILE_VS_2_X";
/*  97 */       break;
/*     */     case 6147: 
/*  99 */       ret = "CG_PROFILE_FP20";
/* 100 */       break;
/*     */     case 6149: 
/* 102 */       ret = "CG_PROFILE_FP30";
/* 103 */       break;
/*     */     case 6151: 
/* 105 */       ret = "CG_PROFILE_FP40";
/* 106 */       break;
/*     */     case 7100: 
/* 108 */       ret = "CG_PROFILE_MAX";
/* 109 */       break;
/*     */     case 6146: 
/* 111 */       ret = "CG_PROFILE_VP20";
/* 112 */       break;
/*     */     case 6148: 
/* 114 */       ret = "CG_PROFILE_VP30";
/* 115 */       break;
/*     */     case 7001: 
/* 117 */       ret = "CG_PROFILE_VP40";
/* 118 */       break;
/*     */     case 6145: 
/* 120 */       ret = "CG_PROFILE_UNKNOWN";
/*     */     }
/*     */     
/*     */     
/* 124 */     return ret;
/*     */   }
/*     */   
/*     */   public String getFileName()
/*     */   {
/* 129 */     return this.m_fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 133 */     this.m_fileName = fileName;
/*     */   }
/*     */   
/*     */   public CGcontext getContext() {
/* 137 */     return this.m_context;
/*     */   }
/*     */   
/*     */   public int getProfile() {
/* 141 */     return this.m_profile;
/*     */   }
/*     */   
/*     */   public CGprogram getProgram() {
/* 145 */     return this.m_program;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 149 */     return this.m_type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 154 */   public String getName() { return this.m_name; }
/*     */   
/*     */   public int getBestSupportedProfile() { int[] arrayOfInt;
/*     */     int j;
/* 158 */     int i; if (this.m_type == 9) {
/* 159 */       j = (arrayOfInt = PS_PROFILES).length; for (i = 0; i < j; i++) { int profile = arrayOfInt[i];
/* 160 */         if (CgGL.cgGLIsProfileSupported(profile))
/* 161 */           return profile;
/*     */       }
/* 163 */     } else { j = (arrayOfInt = VS_PROFILES).length; for (i = 0; i < j; i++) { int profile = arrayOfInt[i];
/* 164 */         if (CgGL.cgGLIsProfileSupported(profile))
/* 165 */           return profile;
/*     */       } }
/* 167 */     return 6145;
/*     */   }
/*     */   
/*     */   public void initialize() throws Exception
/*     */   {
/* 172 */     this.m_profile = getBestSupportedProfile();
/*     */     
/* 174 */     String type = this.m_type == 9 ? "pixel" : "vertex";
/*     */     
/* 176 */     if (this.m_profile != 6145)
/*     */     {
/*     */       InputStream stream;
/*     */       try
/*     */       {
/* 181 */         URL jarUrl = new URL(this.m_fileName);
/* 182 */         stream = jarUrl.openStream();
/*     */       }
/*     */       catch (Exception e) {
/*     */         InputStream stream;
/* 186 */         File file = new File(this.m_fileName);
/* 187 */         stream = new FileInputStream(file);
/*     */       }
/*     */       
/* 190 */       this.m_program = 
/* 191 */         CgGL.cgCreateProgramFromStream(this.m_context, 4112, stream, this.m_profile, null, null);
/*     */       
/* 193 */       if (this.m_program == null) {
/* 194 */         throw new Exception("cgCreateProgramFromFile error : " + CgGL.cgGetLastErrorString(null) + ", file=" + this.m_fileName);
/*     */       }
/* 196 */       CgGL.cgGLLoadProgram(this.m_program);
/*     */     } else {
/* 198 */       System.err.println("OpenGL SL : " + type + "-shaders : profile is unknown");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 203 */     if (this.m_type == 8) {
/* 204 */       this.m_projectionMatrix = CgGL.cgGetNamedParameter(this.m_program, "projectionMatrix");
/* 205 */       CgGL.cgGLEnableClientState(this.m_modelViewMatrix);
/* 206 */       this.m_modelViewMatrix = CgGL.cgGetNamedParameter(this.m_program, "modelViewMatrix");
/* 207 */       CgGL.cgGLEnableClientState(this.m_modelViewMatrix);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void bind()
/*     */   {
/* 214 */     enableProfile();
/* 215 */     CgGL.cgGLBindProgram(this.m_program);
/* 216 */     bindParameters();
/*     */   }
/*     */   
/*     */   public void enableProfile() {
/* 220 */     CgGL.cgGLEnableProfile(this.m_profile);
/*     */   }
/*     */   
/*     */   public void unbind()
/*     */   {
/* 225 */     unbindParameters();
/* 226 */     CgGL.cgGLUnbindProgram(this.m_profile);
/* 227 */     disableProfile();
/*     */   }
/*     */   
/*     */   public void disableProfile() {
/* 231 */     CgGL.cgGLDisableProfile(this.m_profile);
/*     */   }
/*     */   
/*     */   public abstract void bindParameters();
/*     */   
/*     */   public abstract void unbindParameters();
/*     */   
/*     */   public abstract void setup(GL paramGL);
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\shaders\ShaderProgram.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */