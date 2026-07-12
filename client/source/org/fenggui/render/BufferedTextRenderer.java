/*     */ package org.fenggui.render;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
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
/*     */ public class BufferedTextRenderer
/*     */   implements ITextRenderer
/*     */ {
/*  39 */   private Font font = Font.getDefaultFont();
/*  40 */   private String text = null;
/*  41 */   private ArrayList<ArrayList<CharacterPixmap>> chars = new ArrayList<ArrayList<CharacterPixmap>>();
/*  42 */   private BufferedImage image = null;
/*  43 */   private int width = 0;
/*  44 */   private Pixmap completePixmap = null;
/*     */ 
/*     */   
/*     */   public Font getFont() {
/*  48 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  53 */     return this.chars.size() * this.font.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  58 */     return this.text;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  63 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font newFont) {
/*  68 */     this.font = newFont;
/*  69 */     setText(this.text);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  74 */     this.text = text;
/*     */     
/*  76 */     if (text == null || text.length() == 0)
/*     */       return; 
/*  78 */     if (this.font == null) throw new NullPointerException("getFont() == null! No font set!");
/*     */     
/*  80 */     this.chars.clear();
/*     */     
/*  82 */     String[] lines = text.split("\n");
/*     */     
/*  84 */     for (int i = 0; i < lines.length; i++) {
/*     */       
/*  86 */       String line = lines[i].trim();
/*     */       
/*  88 */       ArrayList<CharacterPixmap> linePixmaps = new ArrayList<CharacterPixmap>(line.length());
/*  89 */       int lineWidth = 0;
/*     */       
/*  91 */       for (int j = 0; j < line.length(); j++) {
/*     */         
/*  93 */         char c = line.charAt(j);
/*  94 */         if (c != '\t' && c != '\r' && c != '\f') {
/*     */           
/*  96 */           CharacterPixmap pixmap = this.font.getCharPixMap(c);
/*  97 */           if (pixmap != null) {
/*  98 */             linePixmaps.add(pixmap);
/*  99 */             lineWidth += pixmap.getCharWidth();
/*     */           } 
/*     */         } 
/* 102 */       }  this.width = Math.max(lineWidth, this.width);
/* 103 */       this.chars.add(linePixmaps);
/*     */     } 
/*     */     
/* 106 */     updateBufferedImage();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBufferedImage() {
/* 111 */     if (this.image == null || this.image.getWidth() < getWidth() || this.image.getHeight() < getHeight())
/*     */     {
/* 113 */       this.image = new BufferedImage(getWidth(), getHeight(), 2);
/*     */     }
/*     */     
/* 116 */     Graphics g = this.image.getGraphics();
/*     */ 
/*     */     
/* 119 */     BufferedImage fontImg = this.font.getImage();
/*     */     
/* 121 */     int x = 0, y = 0;
/*     */     
/* 123 */     for (int lineIndex = 0; lineIndex < this.chars.size(); lineIndex++) {
/*     */       
/* 125 */       ArrayList<CharacterPixmap> line = this.chars.get(lineIndex);
/*     */       
/* 127 */       for (CharacterPixmap pixmap : line) {
/*     */         
/* 129 */         BufferedImage charImg = fontImg.getSubimage(pixmap.getX(), pixmap.getY(), pixmap.getWidth(), pixmap.getHeight());
/* 130 */         g.drawImage(charImg, x, y, null);
/* 131 */         x += pixmap.getWidth();
/*     */       } 
/*     */       
/* 134 */       y += this.font.getHeight();
/* 135 */       x = 0;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static int get2Fold(int fold) {
/* 150 */     int ret = 2;
/* 151 */     while (ret < fold)
/*     */     {
/* 153 */       ret *= 2;
/*     */     }
/* 155 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int x, int y, Graphics g, IOpenGL gl) {
/* 160 */     if (this.image == null)
/*     */       return; 
/* 162 */     if (this.completePixmap == null)
/*     */     {
/* 164 */       this.completePixmap = new Pixmap(Binding.getInstance().getTexture(this.image));
/*     */     }
/*     */ 
/*     */     
/* 168 */     g.drawImage(this.completePixmap, x, y);
/*     */   }
/*     */   
/*     */   public void renderCarret(int x, int y, int charIndex, ICarretRenderer carret, Graphics g, IOpenGL gl) {}
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\BufferedTextRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */