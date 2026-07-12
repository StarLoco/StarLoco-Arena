/*     */ package org.fenggui.border;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.util.Color;
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
/*     */ public class PixmapBorder16
/*     */   extends Border
/*     */ {
/*  66 */   private Pixmap[] tex = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private Color modulationColor = Color.WHITE;
/*     */   
/*     */   public Color getModulationColor() {
/*  75 */     return this.modulationColor;
/*     */   }
/*     */   
/*     */   public void setModulationColor(Color modulationColor) {
/*  79 */     this.modulationColor = modulationColor;
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
/*     */   public PixmapBorder16(Pixmap[] array) throws IllegalArgumentException {
/*  95 */     if (array.length == 16) {
/*     */       
/*  97 */       setSpacing(array[2].getHeight(), array[7].getWidth(), array[8].getWidth(), array[13].getHeight());
/*     */     } else {
/*  99 */       throw new IllegalArgumentException(
/* 100 */           "Wrong numbers of Pixmaps! Either 8 or 16 Pixmaps can be specified, not " + array.length);
/*     */     } 
/*     */     
/* 103 */     this.tex = array;
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
/*     */   public PixmapBorder16(List<Pixmap> list) throws IllegalArgumentException {
/* 116 */     this(list.<Pixmap>toArray(new Pixmap[list.size()]));
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
/*     */   public PixmapBorder16() {}
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
/*     */   public PixmapBorder16(Pixmap left, Pixmap right, Pixmap top, Pixmap bottom, Pixmap topleft, Pixmap topright, Pixmap bottomleft, Pixmap bottomright) {
/* 141 */     this(new Pixmap[] { topleft, top, topright, left, right, bottomleft, bottom, bottomright });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 147 */     Pixmap left = null;
/* 148 */     Pixmap right = null;
/* 149 */     Pixmap top = null;
/* 150 */     Pixmap bottom = null;
/* 151 */     Pixmap topleft = null;
/* 152 */     Pixmap topright = null;
/* 153 */     Pixmap bottomleft = null;
/* 154 */     Pixmap bottomright = null;
/*     */     
/* 156 */     g.setColor(this.modulationColor);
/*     */ 
/*     */     
/* 159 */     topleft = this.tex[0];
/* 160 */     Pixmap topJunctionLeft = this.tex[1];
/* 161 */     top = this.tex[2];
/* 162 */     Pixmap topJunctionRight = this.tex[3];
/* 163 */     topright = this.tex[4];
/* 164 */     Pixmap leftJunctionTop = this.tex[5];
/* 165 */     Pixmap rightJunctionTop = this.tex[6];
/* 166 */     left = this.tex[7];
/* 167 */     right = this.tex[8];
/* 168 */     Pixmap leftJunctionBottom = this.tex[9];
/* 169 */     Pixmap rightJunctionBottom = this.tex[10];
/* 170 */     bottomleft = this.tex[11];
/* 171 */     Pixmap bottomJunctionLeft = this.tex[12];
/* 172 */     bottom = this.tex[13];
/* 173 */     Pixmap bottomJunctionRight = this.tex[14];
/* 174 */     bottomright = this.tex[15];
/*     */     
/* 176 */     g.drawImage(topJunctionLeft, 
/* 177 */         localX + getLeft(), localY + height - getTop());
/*     */     
/* 179 */     g.drawScaledImage(top, 
/* 180 */         localX + getLeft() + topJunctionLeft.getWidth(), localY + height - getTop(), 
/* 181 */         width - getLeft() - getRight() - topJunctionLeft.getWidth() - topJunctionRight.getWidth(), getTop());
/*     */     
/* 183 */     g.drawImage(topJunctionRight, 
/* 184 */         localX + width - getRight() - topJunctionRight.getWidth(), localY + height - getTop());
/*     */     
/* 186 */     g.drawImage(leftJunctionTop, 
/* 187 */         localX, localY + height - getTop() - leftJunctionTop.getHeight());
/*     */     
/* 189 */     g.drawImage(rightJunctionTop, 
/* 190 */         localX + width - getRight(), localY + height - getTop() - rightJunctionTop.getHeight());
/*     */     
/* 192 */     g.drawScaledImage(left, 
/* 193 */         localX, localY + getBottom() + leftJunctionBottom.getHeight(), 
/* 194 */         getLeft(), height - getBottom() - getTop() - leftJunctionTop.getHeight() - leftJunctionBottom.getHeight());
/*     */     
/* 196 */     g.drawScaledImage(right, 
/* 197 */         localX + width - getRight(), localY + getBottom() + rightJunctionBottom.getHeight(), 
/* 198 */         getRight(), height - getBottom() - getTop() - rightJunctionBottom.getHeight() - rightJunctionTop.getHeight());
/*     */     
/* 200 */     g.drawImage(leftJunctionBottom, 
/* 201 */         localX, localY + getBottom());
/*     */     
/* 203 */     g.drawImage(rightJunctionBottom, 
/* 204 */         localX + width - getRight(), localY + getBottom());
/*     */ 
/*     */ 
/*     */     
/* 208 */     g.drawImage(bottomJunctionLeft, 
/* 209 */         localX + getLeft(), localY);
/*     */ 
/*     */     
/* 212 */     g.drawScaledImage(bottom, 
/* 213 */         localX + getLeft() + bottomJunctionLeft.getWidth(), localY, 
/* 214 */         width - getLeft() - getRight() - bottomJunctionLeft.getWidth() - bottomJunctionRight.getWidth(), getBottom());
/*     */     
/* 216 */     g.drawImage(bottomJunctionRight, 
/* 217 */         localX - getRight() + width - bottomJunctionRight.getWidth(), localY);
/*     */ 
/*     */ 
/*     */     
/* 221 */     g.drawImage(topleft, localX, localY + height - getTop());
/* 222 */     g.drawImage(bottomleft, localX, localY);
/* 223 */     g.drawImage(topright, localX + width - getRight(), localY + height - getTop());
/* 224 */     g.drawImage(bottomright, localX + width - getRight(), localY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 233 */     super.process(stream);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\PixmapBorder16.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */