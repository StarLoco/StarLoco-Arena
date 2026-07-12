/*     */ package com.ankamagames.framework.graphics.opengl;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.ManagedTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.ManagedTexture.ManagedTextureContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Texture;
/*     */ import com.ankamagames.framework.kernel.core.resource.ContextFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.FileLoader;
/*     */ import com.ankamagames.framework.kernel.core.resource.FileLoaderEventListener;
/*     */ import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
/*     */ import com.ankamagames.framework.kernel.core.resource.SingleResourceManager;
/*     */ import com.sun.opengl.util.BufferUtil;
/*     */ import com.sun.opengl.util.texture.TextureData;
/*     */ import com.sun.opengl.util.texture.TextureIO;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureManager
/*     */   extends SingleResourceManager
/*     */   implements FileLoader
/*     */ {
/*  32 */   private static Logger m_logger = Logger.getLogger(TextureManager.class);
/*     */   
/*  34 */   private static final TextureManager m_instance = new TextureManager();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayList<FileLoaderEventListener> m_fileLoaderListeners;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TextureManager()
/*     */   {
/*  53 */     super(new ResourceFactory()new ContextFactory
/*     */     {
/*     */       public ManagedTexture makeObject()
/*     */       {
/*  47 */         return new ManagedTexture();
/*     */       }
/*  49 */     }, new ContextFactory() {
/*     */       public ManagedTexture.ManagedTextureContext makeObject() {
/*  51 */         return new ManagedTexture.ManagedTextureContext();
/*     */       }
/*  53 */     }, false);
/*     */     
/*  55 */     this.m_fileLoaderListeners = new ArrayList();
/*  56 */     setMaxResourceAge(30);
/*     */   }
/*     */   
/*     */   public static TextureManager getInstance() {
/*  60 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int nearestPowOfTwo(int value)
/*     */   {
/*  69 */     value--;
/*  70 */     value |= value >> 1;
/*  71 */     value |= value >> 2;
/*  72 */     value |= value >> 4;
/*  73 */     value |= value >> 8;
/*  74 */     value |= value >> 16;
/*  75 */     value++;return value;
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
/*     */   public static Texture createTexture(int width, int height, boolean roundToNearestPowOfTwo, int format, boolean generateMipMaps)
/*     */   {
/*  89 */     int w = width;
/*  90 */     int h = height;
/*  91 */     if (roundToNearestPowOfTwo) {
/*  92 */       w = nearestPowOfTwo(w);
/*  93 */       h = nearestPowOfTwo(h);
/*     */     }
/*     */     
/*  96 */     ByteBuffer textureBuffer = BufferUtil.newByteBuffer(w * h * 4);
/*     */     
/*  98 */     return new Texture(3553, width, height, 
/*  99 */       textureBuffer, 6408, false, 
/* 100 */       9728, 9728, 
/* 101 */       10496, 10496);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BaseTexture createTexture(int width, int height, byte[] datas, int format)
/*     */   {
/* 120 */     Texture t = new Texture(
/* 121 */       3553, 
/* 122 */       width, height, 
/* 123 */       ByteBuffer.wrap(datas), 
/* 124 */       format, 
/* 125 */       false);
/*     */     
/*     */ 
/* 128 */     BaseTexture texture = new BaseTexture();
/* 129 */     texture.setTexture(t);
/* 130 */     return texture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BaseTexture createTexture(BufferedImage bufferedImage)
/*     */   {
/* 140 */     Texture t = new Texture(
/* 141 */       3553, 
/* 142 */       bufferedImage, 
/* 143 */       32768, 
/* 144 */       false);
/*     */     
/*     */ 
/* 147 */     BaseTexture texture = new BaseTexture();
/* 148 */     texture.setTexture(t);
/* 149 */     return texture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Texture createRawTextureFromFile(String fileName)
/*     */     throws Exception
/*     */   {
/* 161 */     InputStream stream = null;
/*     */     try
/*     */     {
/* 164 */       URL jarUrl = new URL(fileName);
/* 165 */       stream = jarUrl.openStream();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 169 */       File file = new File(fileName);
/*     */       try {
/* 171 */         stream = new FileInputStream(file);
/*     */       } catch (FileNotFoundException e1) {
/* 173 */         m_logger.error("Fichier introuvable : " + fileName);
/* 174 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 178 */     return createRawTextureFromFile(stream);
/*     */   }
/*     */   
/*     */   public static Texture createRawTextureFromFile(InputStream stream) throws Exception
/*     */   {
/* 183 */     TextureData td = TextureIO.newTextureData(stream, 32993, 6408, false, "tga");
/* 184 */     ByteBuffer buffer = (ByteBuffer)td.getBuffer();
/*     */     
/*     */ 
/* 187 */     if (buffer != null) {
/* 188 */       return new Texture(
/* 189 */         3553, 
/* 190 */         td.getWidth(), 
/* 191 */         td.getHeight(), 
/* 192 */         buffer, 
/* 193 */         32993, 
/* 194 */         true);
/*     */     }
/*     */     
/*     */ 
/* 198 */     m_logger.error("createRawTextureFromFile => null");
/*     */     
/* 200 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ManagedTexture createTextureFromFile(String fileName)
/*     */   {
/* 212 */     Texture t = null;
/*     */     try {
/* 214 */       t = createRawTextureFromFile(fileName);
/*     */     } catch (Exception e) {
/* 216 */       e.printStackTrace();
/*     */     }
/*     */     
/* 219 */     ManagedTexture.ManagedTextureContext context = (ManagedTexture.ManagedTextureContext)getInstance().getNewResource();
/* 220 */     ManagedTexture texture = (ManagedTexture)context.getResource();
/*     */     
/* 222 */     context.setFileName(fileName);
/* 223 */     texture.setTexture(t);
/*     */     
/*     */ 
/*     */ 
/* 227 */     return texture;
/*     */   }
/*     */   
/*     */   public static void alphaDemultiply(BufferedImage image)
/*     */   {
/* 232 */     if (image != null)
/*     */     {
/* 234 */       for (int ty = 0; ty < image.getHeight(); ty++) {
/* 235 */         int y = ty + image.getMinY();
/* 236 */         for (int tx = 0; tx < image.getWidth(); tx++) {
/* 237 */           int x = tx + image.getMinX();
/*     */           
/* 239 */           int c = image.getRGB(x, y);
/* 240 */           float a = (c >> 24 & 0xFF) / 255.0F;
/* 241 */           float r = (c >> 16 & 0xFF) / 255.0F;
/* 242 */           float g = (c >> 8 & 0xFF) / 255.0F;
/* 243 */           float b = (c & 0xFF) / 255.0F;
/*     */           
/* 245 */           r = Math.min(r / a, 1.0F);
/* 246 */           g = Math.min(g / a, 1.0F);
/* 247 */           b = Math.min(b / a, 1.0F);
/*     */           
/* 249 */           c = (int)(a * 255.0F) << 24 | (int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F);
/* 250 */           image.setRGB(x, y, c);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void alphaPremultiply(BufferedImage image)
/*     */   {
/* 258 */     if (image != null)
/*     */     {
/* 260 */       for (int ty = 0; ty < image.getHeight(); ty++) {
/* 261 */         int y = ty + image.getMinY();
/* 262 */         for (int tx = 0; tx < image.getWidth(); tx++) {
/* 263 */           int x = tx + image.getMinX();
/*     */           
/* 265 */           int c = image.getRGB(x, y);
/* 266 */           float a = (c >> 24 & 0xFF) / 255.0F;
/* 267 */           float r = (c >> 16 & 0xFF) / 255.0F * a;
/* 268 */           float g = (c >> 8 & 0xFF) / 255.0F * a;
/* 269 */           float b = (c & 0xFF) / 255.0F * a;
/*     */           
/* 271 */           image.setRGB(
/* 272 */             x, 
/* 273 */             y, 
/* 274 */             (int)(a * 255.0F) << 24 | (int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addFileLoaderEventListener(FileLoaderEventListener listener)
/*     */   {
/* 283 */     this.m_fileLoaderListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeFileLoaderEventLstener(FileLoaderEventListener listener) {
/* 287 */     this.m_fileLoaderListeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void fireOnLoadStartEvent(String fileName) {
/* 291 */     for (FileLoaderEventListener listener : this.m_fileLoaderListeners)
/* 292 */       listener.onLoadStart(fileName);
/*     */   }
/*     */   
/*     */   public void fireOnLoadCompleteEvent(String fileName) {
/* 296 */     for (FileLoaderEventListener listener : this.m_fileLoaderListeners)
/* 297 */       listener.onLoadComplete(fileName);
/*     */   }
/*     */   
/*     */   public void fireOnLoadErrorEvent(String fileName, String error) {
/* 301 */     for (FileLoaderEventListener listener : this.m_fileLoaderListeners) {
/* 302 */       listener.onLoadError(fileName, error);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update()
/*     */   {
/* 308 */     super.update();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\TextureManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */