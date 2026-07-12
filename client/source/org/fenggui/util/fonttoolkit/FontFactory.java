/*     */ package org.fenggui.util.fonttoolkit;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.util.Alphabet;
/*     */ import org.fenggui.util.CharacterPixmap;
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
/*     */ public class FontFactory
/*     */ {
/*  50 */   private Alphabet alphabet = null;
/*  51 */   private Font font = null;
/*  52 */   private FontMetrics fontMetrics = null;
/*  53 */   private int squarePixel = 0;
/*  54 */   final int safetyMargin = 4;
/*  55 */   private AssemblyLine assemblyLine = new AssemblyLine();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssemblyLine getAssemblyLine() {
/*  63 */     return this.assemblyLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontFactory(Alphabet alphabet, Font font) {
/*  74 */     this.alphabet = alphabet;
/*  75 */     this.font = font;
/*  76 */     createFontMetrics();
/*     */   }
/*     */ 
/*     */   
/*     */   private void createFontMetrics() {
/*  81 */     BufferedImage baseImage = new BufferedImage(1, 1, 
/*  82 */         2);
/*  83 */     Graphics2D g = (Graphics2D)baseImage.getGraphics();
/*  84 */     this.fontMetrics = g.getFontMetrics(this.font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font createFont() {
/*  94 */     ArrayList<BufferedImage> charImages = new ArrayList<BufferedImage>();
/*     */     
/*  96 */     BufferedImage bi = null; byte b; int i;
/*     */     char[] arrayOfChar;
/*  98 */     for (arrayOfChar = this.alphabet.getAlphabet(), b = 0, i = arrayOfChar.length; b < i; ) { char c = arrayOfChar[b];
/*     */       
/* 100 */       if (c != ' ') {
/*     */ 
/*     */         
/* 103 */         bi = new BufferedImage(
/* 104 */             this.fontMetrics.charWidth(c) + 4 * this.font.getSize() / 2, 
/* 105 */             this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent() + 4, 
/* 106 */             2);
/*     */       }
/*     */       else {
/*     */         
/* 110 */         bi = new BufferedImage(this.fontMetrics.stringWidth(" "), this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent(), 2);
/*     */       } 
/*     */       
/* 113 */       this.assemblyLine.execute(this.fontMetrics, bi, c, 4);
/*     */       
/* 115 */       if (c != ' ')
/*     */       {
/* 117 */         bi = cropImage(bi, c);
/*     */       }
/*     */       
/* 120 */       this.squarePixel += bi.getWidth() * bi.getHeight();
/*     */       
/* 122 */       charImages.add(bi);
/*     */       b++; }
/*     */     
/* 125 */     Font font = buildCharTexture(charImages);
/*     */     
/* 127 */     return font;
/*     */   }
/*     */ 
/*     */   
/*     */   private BufferedImage cropImage(BufferedImage bi, char c) {
/* 132 */     ColorModel cm = bi.getColorModel();
/*     */     
/* 134 */     int startX = 0;
/* 135 */     int endX = this.fontMetrics.charWidth(c);
/*     */ 
/*     */     
/* 138 */     for (int x = this.fontMetrics.charWidth(c); x < bi.getWidth(); x++) {
/*     */       
/* 140 */       boolean hasAlpha = false;
/*     */       
/* 142 */       for (int y = 0; y < bi.getHeight(); y++) {
/*     */         
/* 144 */         if (cm.getAlpha(bi.getRGB(x, y)) != 0) {
/*     */           
/* 146 */           hasAlpha = true;
/* 147 */           endX = x + 1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 152 */       if (!hasAlpha) {
/*     */         break;
/*     */       }
/*     */     } 
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
/* 170 */     BufferedImage cropped = new BufferedImage(endX - startX, this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent(), 
/* 171 */         bi.getType());
/* 172 */     cropped.getGraphics().drawImage(bi, -startX, 0, null);
/*     */     
/* 174 */     return cropped;
/*     */   }
/*     */ 
/*     */   
/*     */   private Font buildCharTexture(ArrayList<BufferedImage> charImages) {
/* 179 */     Hashtable<Character, CharacterPixmap> hashtable = new Hashtable<Character, CharacterPixmap>();
/*     */     
/* 181 */     int length = getLengthThatMakesTheNextPowerOfTwo(this.squarePixel);
/*     */     
/* 183 */     BufferedImage bi = new BufferedImage(length, length, 
/* 184 */         2);
/*     */     
/* 186 */     Graphics2D g = bi.createGraphics();
/*     */     
/* 188 */     Clear.clear(g, bi.getWidth(), bi.getHeight());
/*     */     
/* 190 */     int x = 0;
/* 191 */     int y = 0;
/*     */     
/* 193 */     int counter = 0;
/*     */     
/* 195 */     for (BufferedImage charImage : charImages) {
/*     */       
/* 197 */       if (charImage.getWidth() + x > bi.getWidth()) {
/*     */         
/* 199 */         y += this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent();
/* 200 */         x = 0;
/*     */       } 
/*     */       
/* 203 */       int xValue = x;
/* 204 */       int yValue = y;
/*     */       
/* 206 */       CharacterPixmap cp = new CharacterPixmap(
/* 207 */           null, xValue, yValue, 
/* 208 */           charImage.getWidth(), this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent(), 
/* 209 */           this.alphabet.getAlphabet()[counter], this.fontMetrics
/* 210 */           .stringWidth(this.alphabet.getAlphabet()[counter]));
/*     */       
/* 212 */       hashtable.put(Character.valueOf(cp.getCharacter()), cp);
/*     */       
/* 214 */       g.drawImage(charImage, xValue, yValue, (ImageObserver)null);
/*     */       
/* 216 */       x += charImage.getWidth();
/* 217 */       counter++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     return new Font(bi, hashtable, this.fontMetrics.getMaxAscent() + this.fontMetrics.getMaxDescent());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getLengthThatMakesTheNextPowerOfTwo(int minSquare) {
/* 228 */     int length = 2;
/* 229 */     while (minSquare >= length * length)
/* 230 */       length *= 2; 
/* 231 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveImageToDisk(BufferedImage bi, String filename) {
/* 241 */     String ending = filename.substring(filename.length() - 3, 
/* 242 */         filename.length());
/*     */     
/*     */     try {
/* 245 */       ImageIO.write(bi, ending, new File(filename));
/*     */     }
/* 247 */     catch (IOException e) {
/*     */       
/* 249 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Font renderStandardFont(Font awtFont) {
/* 260 */     return renderStandardFont(awtFont, Alphabet.getDefaultAlphabet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Font renderStandardFont(Font awtFont, Alphabet alphabet) {
/* 271 */     return renderStandardFont(awtFont, false, alphabet);
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
/*     */   public static Font renderStandardFont(Font awtFont, boolean antiAliasing, Alphabet alphabet) {
/* 284 */     FontFactory ff = new FontFactory(alphabet, awtFont);
/* 285 */     AssemblyLine line = ff.getAssemblyLine();
/*     */     
/* 287 */     line.addStage(new Clear());
/* 288 */     line.addStage(new DrawCharacter(Color.WHITE, antiAliasing));
/*     */     
/* 290 */     Font f = ff.createFont();
/*     */     
/* 292 */     return f;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\fonttoolkit\FontFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */