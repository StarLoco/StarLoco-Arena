/*     */ package org.fenggui.tree;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Dimension;
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
/*     */ public class TreeAppearance<E>
/*     */   extends DecoratorAppearance
/*     */ {
/*     */   private Tree<E> tree;
/*     */   private Pixmap minusIcon;
/*     */   private Pixmap plusIcon;
/*  41 */   private Color textColor = Color.BLACK;
/*  42 */   private Color selectionColor = Color.LIGHT_BLUE;
/*  43 */   private Font font = Font.getDefaultFont();
/*     */   public static final int ICON_OFFSET = 15;
/*     */   public static final int OFFSET = 15;
/*  46 */   private int counter = 0;
/*     */ 
/*     */   
/*     */   public TreeAppearance(Tree<E> w) {
/*  50 */     super((IWidget)w);
/*  51 */     this.tree = w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  57 */     Record<E> root = this.tree.getRoot();
/*  58 */     ITreeModel<E> model = this.tree.getModel();
/*     */     
/*  60 */     if (root == null) return new Dimension(0, 0); 
/*  61 */     ArrayList<Record<E>> stack = new ArrayList<Record<E>>();
/*  62 */     ArrayList<Integer> offsetStack = new ArrayList<Integer>();
/*  63 */     stack.add(root);
/*  64 */     offsetStack.add(Integer.valueOf(0));
/*  65 */     int width = 0;
/*  66 */     int height = 0;
/*     */     
/*  68 */     while (!stack.isEmpty()) {
/*     */       
/*  70 */       Record<E> r = stack.remove(0);
/*  71 */       int offset = ((Integer)offsetStack.remove(0)).intValue();
/*  72 */       int recordWidth = offset + 15 + 3 + this.font.getWidth(model.getText(r.getNode()));
/*  73 */       if (width < recordWidth) width = recordWidth;
/*     */       
/*  75 */       height += this.font.getHeight();
/*     */       
/*  77 */       for (Record<E> p : r.getChildren()) {
/*     */         
/*  79 */         stack.add(p);
/*  80 */         offsetStack.add(Integer.valueOf(offset + 15));
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     return new Dimension(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/*  90 */     if (this.tree.getRoot() == null)
/*  91 */       return;  this.counter = 0;
/*  92 */     g.setColor(this.textColor);
/*  93 */     paintRecord(g, this.tree.getRoot(), true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getMinusIcon() {
/*  99 */     return this.minusIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinusIcon(Pixmap minusIcon) {
/* 105 */     this.minusIcon = minusIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pixmap getPlusIcon() {
/* 111 */     return this.plusIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlusIcon(Pixmap plusIcon) {
/* 117 */     this.plusIcon = plusIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getSelectionColor() {
/* 124 */     return this.selectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionColor(Color selectionColor) {
/* 130 */     this.selectionColor = selectionColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/* 136 */     return this.textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/* 142 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 147 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintRecord(Graphics g, Record<E> node, boolean isLastOne, boolean hasSisters) {
/* 152 */     node.row = this.counter;
/* 153 */     this.counter++;
/* 154 */     ITreeModel<E> model = this.tree.getModel();
/* 155 */     int y = getContentHeight() - this.counter * this.font.getHeight();
/*     */     
/* 157 */     if (node.isSelected()) {
/*     */       
/* 159 */       g.setColor(this.selectionColor);
/* 160 */       g.drawFilledRectangle(node.getOffset() + 15 + 1, y, this.font.getWidth(model.getText(node.getNode())) + 3, 
/* 161 */           this.font.getHeight());
/* 162 */       g.setColor(Color.WHITE);
/*     */     } else {
/* 164 */       g.setColor(this.textColor);
/*     */     } 
/* 166 */     g.drawString(model.getText(node.getNode()), node.getOffset() + 15 + 3, y);
/* 167 */     g.setColor(Color.LIGHT_GRAY);
/* 168 */     g.drawLine(node.getOffset() + this.plusIcon.getWidth() / 2, y + this.font.getHeight() / 2, node.getOffset() + 15, y + 
/* 169 */         this.font.getHeight() / 2);
/*     */ 
/*     */     
/* 172 */     if (node.getNumberOfChildren() > 0)
/*     */     {
/* 174 */       g.drawLine(node.getOffset() + 15 + this.plusIcon.getWidth() / 2, y + this.font.getHeight() / 2, node.getOffset() + 15 + 
/* 175 */           this.plusIcon.getWidth() / 2, y - node.getNumberOfChildren() * this.font.getHeight() + this.font.getHeight() / 2);
/*     */     }
/*     */ 
/*     */     
/* 179 */     for (int i = 0; i < node.getNumberOfChildren(); i++)
/*     */     {
/* 181 */       paintRecord(g, node.getChild(i), (i + 1 >= node.getNumberOfChildren()), false);
/*     */     }
/*     */     
/* 184 */     if (!isLastOne) {
/*     */       
/* 186 */       g.setColor(Color.LIGHT_GRAY);
/* 187 */       g.drawLine(node.getOffset() + this.plusIcon.getWidth() / 2, y + this.font.getHeight() + 
/* 188 */           this.font.getHeight() / 2 - this.minusIcon.getHeight() / 2, node.getOffset() + this.plusIcon.getWidth() / 2, 
/* 189 */           getContentHeight() - this.counter * this.font.getHeight() - 
/* 190 */           this.font.getHeight() / 2 - this.plusIcon.getHeight() / 2);
/*     */     } 
/*     */     
/* 193 */     g.setColor(Color.WHITE);
/*     */     
/* 195 */     if (node.getNumberOfChildren() > 0) {
/*     */       
/* 197 */       g.drawImage(this.minusIcon, node.getOffset(), y + this.font.getHeight() / 2 - this.minusIcon.getHeight() / 2);
/*     */     }
/* 199 */     else if (node.isExpandable()) {
/*     */       
/* 201 */       g.drawImage(this.plusIcon, node.getOffset(), y + this.font.getHeight() / 2 - this.plusIcon.getHeight() / 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 208 */     super.process(stream);
/*     */     
/* 210 */     this.minusIcon = (Pixmap)stream.processChild("MinusIconPixmap", (IOStreamSaveable)this.minusIcon, Pixmap.class);
/* 211 */     this.plusIcon = (Pixmap)stream.processChild("PlusIconPixmap", (IOStreamSaveable)this.plusIcon, Pixmap.class);
/* 212 */     this.textColor = (Color)stream.processChild("TextColor", (IOStreamSaveable)this.textColor, (IOStreamSaveable)Color.BLACK, Color.class);
/* 213 */     this.selectionColor = (Color)stream.processChild("SelectionColor", (IOStreamSaveable)this.selectionColor, (IOStreamSaveable)Color.LIGHT_BLUE, Color.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 218 */     this.font = font;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\tree\TreeAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */