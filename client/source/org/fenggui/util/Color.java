/*     */ package org.fenggui.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.io.MalformedElementException;
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
/*     */ 
/*     */ public class Color
/*     */   implements IOStreamSaveable
/*     */ {
/*  49 */   private static final NumberFormat PERCENTAGE_FORMAT = new DecimalFormat("##%");
/*     */   
/*     */   private float red;
/*     */   private float green;
/*     */   private float blue;
/*  54 */   private float alpha = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final Color OPAQUE = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final Color WHITE = new Color(1.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final Color BLACK = new Color(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final Color BLACK_HALF_OPAQUE = new Color(0.0F, 0.0F, 0.0F, 0.5F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final Color GREEN = new Color(0, 128, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final Color DARK_GREEN = new Color(0, 100, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final Color LIGHT_GREEN = new Color(144, 238, 144);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final Color RED = new Color(1.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final Color LIGHT_RED = new Color(1.0F, 0.5F, 0.5F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final Color DARK_RED = new Color(139, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final Color BLUE = new Color(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final Color LIGHT_BLUE = new Color(173, 216, 230);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final Color DARK_BLUE = new Color(0, 0, 139);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final Color YELLOW = new Color(1.0F, 1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static final Color LIGHT_YELLOW = new Color(255, 255, 224);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static final Color DARK_YELLOW = new Color(0.5F, 0.5F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static final Color MAGENTA = new Color(1.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final Color LIGHT_MAGENTA = new Color(1.0F, 0.5F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static final Color DARK_MAGENTA = new Color(139, 0, 139);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public static final Color CYAN = new Color(0.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final Color LIGHT_CYUAN = new Color(224, 255, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static final Color DARK_CYUAN = new Color(0, 139, 139);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public static final Color GRAY = new Color(0.5F, 0.5F, 0.5F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public static final Color LIGHT_GRAY = new Color(211, 211, 211);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   public static final Color DARK_GRAY = new Color(169, 169, 169);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public static final Color WHITE_HALF_OPAQUE = new Color(1.0F, 1.0F, 1.0F, 0.5F);
/*     */ 
/*     */   
/* 187 */   public static Map<String, Color> colorMap = new HashMap<String, Color>();
/*     */ 
/*     */   
/*     */   static {
/* 191 */     colorMap.put("blue", BLUE);
/* 192 */     colorMap.put("green", GREEN);
/* 193 */     colorMap.put("red", RED);
/* 194 */     colorMap.put("yellow", YELLOW);
/* 195 */     colorMap.put("magenta", MAGENTA);
/* 196 */     colorMap.put("white", WHITE);
/* 197 */     colorMap.put("black", BLACK);
/* 198 */     colorMap.put("gray", GRAY);
/* 199 */     colorMap.put("light blue", LIGHT_BLUE);
/* 200 */     colorMap.put("light yellow", LIGHT_YELLOW);
/* 201 */     colorMap.put("light magneta", LIGHT_MAGENTA);
/* 202 */     colorMap.put("light gray", LIGHT_GRAY);
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
/*     */   public Color(float red, float green, float blue) {
/* 214 */     this.red = red;
/* 215 */     this.green = green;
/* 216 */     this.blue = blue;
/* 217 */     checkDomain();
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
/*     */   public Color(float red, float green, float blue, float alpha) {
/* 230 */     this.red = red;
/* 231 */     this.green = green;
/* 232 */     this.blue = blue;
/* 233 */     this.alpha = alpha;
/* 234 */     checkDomain();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(Color value) {
/* 244 */     this.red = value.red;
/* 245 */     this.green = value.green;
/* 246 */     this.blue = value.blue;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color(InputOnlyStream stream) throws IOException, IOStreamException {
/* 251 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(int red, int green, int blue, int alpha) {
/* 262 */     this.red = red / 255.0F;
/* 263 */     this.green = green / 255.0F;
/* 264 */     this.blue = blue / 255.0F;
/* 265 */     this.alpha = alpha / 255.0F;
/* 266 */     checkDomain();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color(int red, int green, int blue) {
/* 276 */     this.red = red / 255.0F;
/* 277 */     this.green = green / 255.0F;
/* 278 */     this.blue = blue / 255.0F;
/* 279 */     checkDomain();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color random() {
/* 288 */     return new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkDomain() {
/* 298 */     if (this.red > 1.0F) this.red = 1.0F; 
/* 299 */     if (this.green > 1.0F) this.green = 1.0F; 
/* 300 */     if (this.blue > 1.0F) this.blue = 1.0F; 
/* 301 */     if (this.alpha > 1.0F) this.alpha = 1.0F;
/*     */     
/* 303 */     if (this.red < 0.0F) this.red = 0.0F; 
/* 304 */     if (this.green < 0.0F) this.green = 0.0F; 
/* 305 */     if (this.blue < 0.0F) this.blue = 0.0F; 
/* 306 */     if (this.alpha < 0.0F) this.alpha = 0.0F;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color darker() {
/* 317 */     return darker(0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color brighter() {
/* 328 */     return brighter(0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color darker(float step) {
/* 339 */     return new Color(this.red - step, this.green - step, this.blue - step, this.alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color brighter(float step) {
/* 350 */     return new Color(this.red + step, this.green + step, this.blue + step, this.alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRed() {
/* 360 */     return this.red;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGreen() {
/* 370 */     return this.green;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlue() {
/* 380 */     return this.blue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Color c) {
/* 385 */     return (this.alpha == c.alpha && this.blue == c.blue && this.red == c.red && this.green == c.green);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAlpha() {
/* 395 */     return this.alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   private void set(Color c) {
/* 400 */     this.red = c.red;
/* 401 */     this.blue = c.blue;
/* 402 */     this.green = c.green;
/* 403 */     this.alpha = c.alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 410 */     if (stream.isInputStream()) {
/*     */ 
/*     */       
/* 413 */       String rgbaStr = stream.processAttribute("rgba", "", null);
/* 414 */       if (rgbaStr != null && !rgbaStr.equals("")) {
/*     */         
/* 416 */         String[] s = rgbaStr.split(",");
/*     */         
/* 418 */         if (s.length != 4) throw new MalformedElementException("the rgba attribute requires 4 elements, not " + s.length + "!");
/*     */         
/* 420 */         this.red = Integer.parseInt(s[0].trim()) / 255.0F;
/* 421 */         this.green = Integer.parseInt(s[1].trim()) / 255.0F;
/* 422 */         this.blue = Integer.parseInt(s[2].trim()) / 255.0F;
/* 423 */         this.alpha = Integer.parseInt(s[3].trim()) / 255.0F;
/*     */         
/*     */         return;
/*     */       } 
/* 427 */       String value = stream.processAttribute("value", "blue", "no value");
/*     */       
/* 429 */       if (!value.equals("no value")) {
/*     */         
/* 431 */         Color color = colorMap.get(value);
/*     */         
/* 433 */         if (color == null) {
/* 434 */           throw new MalformedElementException("Color value " + value + 
/* 435 */               " not recognized!");
/*     */         }
/*     */         
/* 438 */         set(color);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } else {
/* 444 */       for (String key : colorMap.keySet()) {
/*     */         
/* 446 */         if (equals(colorMap.get(key))) {
/*     */           
/* 448 */           stream.processAttribute("value", key);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 455 */     this.red = stream.processAttribute("red", this.red);
/* 456 */     this.green = stream.processAttribute("green", this.green);
/* 457 */     this.blue = stream.processAttribute("blue", this.blue);
/* 458 */     this.alpha = stream.processAttribute("alpha", this.alpha, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 463 */     return String.valueOf(this.red) + ", " + this.green + ", " + this.blue + ", " + this.alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreAlpha(Color c) {
/* 468 */     return (this.blue == c.blue && this.red == c.red && this.green == c.green);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 475 */     for (String key : colorMap.keySet()) {
/*     */       
/* 477 */       Color c = colorMap.get(key);
/*     */       
/* 479 */       if (equals(c))
/*     */       {
/* 481 */         return null;
/*     */       }
/*     */       
/* 484 */       if (equalsIgnoreAlpha(c))
/*     */       {
/* 486 */         return String.valueOf(PERCENTAGE_FORMAT.format(this.alpha)) + " opaque " + 
/* 487 */           key;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 493 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\Color.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */