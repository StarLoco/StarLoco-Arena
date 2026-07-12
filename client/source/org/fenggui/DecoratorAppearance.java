/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Spacing;
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
/*     */ public abstract class DecoratorAppearance
/*     */   extends SpacingAppearance
/*     */ {
/*  45 */   private ArrayList<IDecorator> decorators = new ArrayList<IDecorator>();
/*  46 */   private ArrayList<Switch> switches = new ArrayList<Switch>();
/*     */ 
/*     */   
/*     */   public void add(String label, Background background, Span spanType) {
/*  50 */     background.setLabel(label);
/*  51 */     background.setSpan(spanType);
/*  52 */     this.decorators.add(background);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Background background) {
/*  57 */     add("default", background, Span.PADDING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String label, Background background) {
/*  62 */     add(label, background, Span.PADDING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String label, Border border, boolean setAsBorderSpacing) {
/*  67 */     border.setLabel(label);
/*  68 */     this.decorators.add(border);
/*     */ 
/*     */ 
/*     */     
/*  72 */     if (setAsBorderSpacing) setBorder(new Spacing(border.getTop(), border.getLeft(), border.getRight(), border.getBottom()));
/*     */   
/*     */   }
/*     */   
/*     */   public void add(Border border) {
/*  77 */     add("default", border, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String label, Border border) {
/*  82 */     add(label, border, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Switch sw) {
/*  87 */     this.switches.add(sw);
/*     */   }
/*     */ 
/*     */   
/*     */   public DecoratorAppearance(IWidget w) {
/*  92 */     super(w);
/*     */   }
/*     */ 
/*     */   
/*     */   public DecoratorAppearance(IWidget w, InputOnlyStream stream) throws IOException, IOStreamException {
/*  97 */     super(w);
/*  98 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void paint(Graphics g, IOpenGL gl) {
/* 104 */     for (int i = 0; i < this.decorators.size(); i++) {
/*     */       
/* 106 */       int width = getWidget().getSize().getWidth();
/* 107 */       int height = getWidget().getSize().getHeight();
/*     */       
/* 109 */       paintDecorator(this.decorators.get(i), g, gl, this, width, height);
/*     */     } 
/*     */     
/* 112 */     super.paint(g, gl);
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
/*     */   private void paintDecorator(IDecorator d, Graphics g, IOpenGL gl, SpacingAppearance app, int widgetWidth, int widgetHeight) {
/* 128 */     if (!d.isEnabled())
/*     */       return; 
/* 130 */     int x = 0;
/* 131 */     int y = 0;
/*     */     
/* 133 */     if (d.getSpan() == Span.PADDING) {
/*     */       
/* 135 */       Spacing m = app.getMargin();
/* 136 */       Spacing b = app.getBorder();
/*     */       
/* 138 */       x += m.getLeft() + b.getLeft();
/* 139 */       y += m.getBottom() + b.getBottom();
/*     */       
/* 141 */       widgetWidth -= x + m.getRight() + b.getRight();
/* 142 */       widgetHeight -= y + m.getTop() + b.getTop();
/*     */     }
/* 144 */     else if (d.getSpan() == Span.BORDER) {
/*     */       
/* 146 */       Spacing m = app.getMargin();
/*     */       
/* 148 */       x += m.getLeft();
/* 149 */       y += m.getBottom();
/*     */       
/* 151 */       widgetWidth -= x + m.getRight();
/* 152 */       widgetHeight -= y + m.getTop();
/*     */     } 
/*     */     
/* 155 */     d.paint(g, x, y, widgetWidth, widgetHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(String label, boolean enable) {
/* 160 */     for (IDecorator wrapper : this.decorators) {
/*     */       
/* 162 */       if (wrapper.getLabel().equals(label))
/*     */       {
/* 164 */         wrapper.setEnabled(enable);
/*     */       }
/*     */     } 
/*     */     
/* 168 */     for (Switch sw : this.switches) {
/*     */       
/* 170 */       if (sw.getLabel().equals(label))
/*     */       {
/* 172 */         sw.setEnabled(enable);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     ArrayList<Class<? extends Switch>> switchTypes = new ArrayList<Class<? extends Switch>>();
/* 177 */     for (int i = this.switches.size() - 1; i >= 0; i--) {
/* 178 */       Switch sw = this.switches.get(i);
/* 179 */       if (sw.getLabel().equals(label))
/*     */       {
/* 181 */         sw.setEnabled(enable);
/*     */       }
/* 183 */       if (!switchTypes.contains(sw.getClass()) && sw.isEnabled()) {
/* 184 */         sw.setup(getWidget());
/* 185 */         switchTypes.add(sw.getClass());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 194 */     String s = "";
/*     */     
/* 196 */     for (IDecorator wrapper : this.decorators)
/*     */     {
/* 198 */       s = String.valueOf(s) + "\n- " + wrapper.toString();
/*     */     }
/*     */     
/* 201 */     return String.valueOf(super.toString()) + s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 210 */     this.decorators.clear();
/* 211 */     this.switches.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 217 */     super.process(stream);
/*     */     
/* 219 */     if (stream.startSubcontext("decorators")) {
/*     */       
/* 221 */       if (stream.processAttribute("clear", false, false)) {
/* 222 */         this.decorators.clear();
/*     */       }
/* 224 */       stream.processChildren(this.decorators, FengGUI.TYPE_REGISTRY);
/*     */       
/* 226 */       stream.endSubcontext();
/*     */     } 
/*     */     
/* 229 */     if (stream.startSubcontext("switches")) {
/*     */       
/* 231 */       if (stream.processAttribute("clear", false, false)) {
/* 232 */         this.switches.clear();
/*     */       }
/* 234 */       stream.processChildren(this.switches, FengGUI.TYPE_REGISTRY);
/*     */       
/* 236 */       stream.endSubcontext();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\DecoratorAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */