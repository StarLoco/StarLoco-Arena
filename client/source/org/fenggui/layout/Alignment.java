/*     */ package org.fenggui.layout;
/*     */ 
/*     */ import org.fenggui.io.DefaultElementName;
/*     */ import org.fenggui.io.EncodingException;
/*     */ import org.fenggui.io.StorageFormat;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Point;
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
/*     */ @DefaultElementName("Alignment")
/*     */ public enum Alignment
/*     */ {
/*  39 */   TOP_LEFT(0.0D, 1.0D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/*  43 */       return new Point(o.getX(), o.getY() + s.getHeight() - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/*  48 */       return new Point(o.getX(), o.getY() + s.getHeight() - b.getHeight());
/*     */     }
/*     */   },
/*     */ 
/*     */   
/*  53 */   TOP(0.5D, 1.0D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/*  57 */       return new Point(o.getX() + (s.getWidth() - 1) / 2, o.getY() + s.getHeight() - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/*  62 */       return new Point(o.getX() + (s.getWidth() - b.getWidth()) / 2, o.getY() + s.getHeight() - b.getHeight());
/*     */     }
/*     */   },
/*     */   
/*  66 */   TOP_RIGHT(1.0D, 1.0D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/*  70 */       return new Point(o.getX() + s.getWidth() - 1, o.getY() + s.getHeight() - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/*  75 */       return new Point(o.getX() + s.getWidth() - b.getWidth(), o.getY() + s.getHeight() - b.getHeight());
/*     */     }
/*     */   },
/*     */   
/*  79 */   LEFT(0.0D, 0.5D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/*  83 */       return new Point(o.getX(), o.getY() + (s.getHeight() - 1) / 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/*  88 */       return new Point(o.getX(), o.getY() + (s.getHeight() - b.getHeight()) / 2);
/*     */     }
/*     */   },
/*     */   
/*  92 */   MIDDLE(0.5D, 0.5D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/*  96 */       return new Point(o.getX() + (s.getWidth() - 1) / 2, o.getY() + (s.getHeight() - 1) / 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/* 101 */       return new Point(o.getX() + (s.getWidth() - b.getWidth()) / 2, 
/* 102 */           o.getY() + (s.getHeight() - b.getHeight()) / 2);
/*     */     }
/*     */   },
/*     */   
/* 106 */   RIGHT(1.0D, 0.5D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/* 110 */       return new Point(o.getX() + s.getWidth() - 1, o.getY() + (s.getHeight() - 1) / 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/* 115 */       return new Point(o.getX() + s.getWidth() - b.getWidth(), o.getY() + (s.getHeight() - b.getHeight()) / 2);
/*     */     }
/*     */   },
/*     */   
/* 119 */   BOTTOM_LEFT(0.0D, 0.0D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/* 123 */       return new Point(o.getX(), o.getY());
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/* 128 */       return new Point(o.getX(), o.getY());
/*     */     }
/*     */   },
/*     */   
/* 132 */   BOTTOM(0.5D, 0.0D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/* 136 */       return new Point(o.getX() + (s.getWidth() - 1) / 2, o.getY());
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/* 141 */       return new Point(o.getX() + (s.getWidth() - b.getWidth()) / 2, o.getY());
/*     */     }
/*     */   },
/*     */   
/* 145 */   BOTTOM_RIGHT(1.0D, 0.0D)
/*     */   {
/*     */     public Point align(Point o, Dimension s)
/*     */     {
/* 149 */       return new Point(o.getX() + s.getWidth() - 1, o.getY());
/*     */     }
/*     */ 
/*     */     
/*     */     public Point alignBox(Point o, Dimension s, Dimension b) {
/* 154 */       return new Point(o.getX() + s.getWidth() - b.getWidth(), o.getY());
/*     */     } };
/*     */   
/*     */   static {
/* 158 */     STORAGE_FORMAT = 
/* 159 */       new StorageFormat<Alignment, String>()
/*     */       {
/*     */         public String encode(Alignment obj) throws EncodingException
/*     */         {
/* 163 */           switch (obj) {
/*     */             case LEFT:
/* 165 */               return "left";
/* 166 */             case RIGHT: return "right";
/* 167 */             case null: return "bottom";
/* 168 */             case TOP: return "top";
/* 169 */             case MIDDLE: return "middle";
/* 170 */             case TOP_LEFT: return "top left";
/* 171 */             case TOP_RIGHT: return "top right";
/* 172 */             case BOTTOM_LEFT: return "bottom left";
/* 173 */             case BOTTOM_RIGHT: return "bottom right";
/* 174 */           }  return "left";
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public Alignment decode(String encodedObj) throws EncodingException {
/* 180 */           if (encodedObj.equalsIgnoreCase("left")) return Alignment.LEFT; 
/* 181 */           if (encodedObj.equalsIgnoreCase("right")) return Alignment.RIGHT; 
/* 182 */           if (encodedObj.equalsIgnoreCase("bottom")) return Alignment.BOTTOM; 
/* 183 */           if (encodedObj.equalsIgnoreCase("top")) return Alignment.TOP; 
/* 184 */           if (encodedObj.equalsIgnoreCase("middle")) return Alignment.MIDDLE; 
/* 185 */           if (encodedObj.equalsIgnoreCase("top left")) return Alignment.TOP_LEFT; 
/* 186 */           if (encodedObj.equalsIgnoreCase("top right")) return Alignment.TOP_RIGHT; 
/* 187 */           if (encodedObj.equalsIgnoreCase("bottom left")) return Alignment.BOTTOM_LEFT; 
/* 188 */           return Alignment.BOTTOM_RIGHT;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final StorageFormat<Alignment, String> STORAGE_FORMAT;
/*     */ 
/*     */   
/*     */   private double along;
/*     */   
/*     */   private double up;
/*     */ 
/*     */   
/*     */   Alignment(double along, double up) {
/* 204 */     this.along = along;
/* 205 */     this.up = up;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double fromLeft() {
/* 216 */     return this.along;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double fromBottom() {
/* 227 */     return this.up;
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
/*     */   public int alignX(int availableWidth, int width) {
/* 245 */     return (int)(fromLeft() * availableWidth - fromLeft() * width);
/*     */   }
/*     */   
/*     */   public int alignY(int availableHeight, int height) {
/* 249 */     return (int)(fromBottom() * availableHeight - fromBottom() * height);
/*     */   }
/*     */   
/*     */   public abstract Point align(Point paramPoint, Dimension paramDimension);
/*     */   
/*     */   public abstract Point alignBox(Point paramPoint, Dimension paramDimension1, Dimension paramDimension2);
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\Alignment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */