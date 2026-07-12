/*     */ package org.fenggui.xml.theme;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.CheckBox;
/*     */ import org.fenggui.ComboBox;
/*     */ import org.fenggui.Container;
/*     */ import org.fenggui.IDecorator;
/*     */ import org.fenggui.IWidget;
/*     */ import org.fenggui.Label;
/*     */ import org.fenggui.List;
/*     */ import org.fenggui.ProgressBar;
/*     */ import org.fenggui.RadioButton;
/*     */ import org.fenggui.ScrollBar;
/*     */ import org.fenggui.ScrollContainer;
/*     */ import org.fenggui.Slider;
/*     */ import org.fenggui.Span;
/*     */ import org.fenggui.SplitContainer;
/*     */ import org.fenggui.Switch;
/*     */ import org.fenggui.TabItemLabel;
/*     */ import org.fenggui.TextEditor;
/*     */ import org.fenggui.VerticalList;
/*     */ import org.fenggui.background.Background;
/*     */ import org.fenggui.background.GradientBackground;
/*     */ import org.fenggui.background.PlainBackground;
/*     */ import org.fenggui.border.BevelBorder;
/*     */ import org.fenggui.border.Border;
/*     */ import org.fenggui.border.PlainBorder;
/*     */ import org.fenggui.composites.Window;
/*     */ import org.fenggui.console.Console;
/*     */ import org.fenggui.layout.Alignment;
/*     */ import org.fenggui.menu.Menu;
/*     */ import org.fenggui.menu.MenuBar;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.ITexture;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.switches.SetPixmapSwitch;
/*     */ import org.fenggui.switches.SetTextColorSwitch;
/*     */ import org.fenggui.table.Table;
/*     */ import org.fenggui.tree.Tree;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Spacing;
/*     */ 
/*     */ 
/*     */ public class DefaultTheme
/*     */   extends StandardTheme
/*     */ {
/*     */   public void setUp(Button b) {
/*  51 */     b.getAppearance().setTextColor(Color.BLACK);
/*  52 */     b.getAppearance().setAlignment(Alignment.MIDDLE);
/*  53 */     b.getAppearance().setPadding(new Spacing(0, 2, 2, 1));
/*  54 */     b.getAppearance().setMargin(new Spacing(1, 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     b.getAppearance().add("default", (Background)new GradientBackground(Color.GRAY, Color.OPAQUE));
/*  60 */     b.getAppearance().add("mouseHover", (Background)new PlainBackground(Color.BLUE));
/*     */     
/*  62 */     b.getAppearance().add("default", (Border)new PlainBorder(Color.GRAY));
/*  63 */     b.getAppearance().add("mouseHover", (Border)new BevelBorder(Color.DARK_GRAY, Color.LIGHT_GRAY));
/*  64 */     b.getAppearance().add("mouseHover", (Background)new GradientBackground(Color.GRAY, Color.OPAQUE));
/*  65 */     b.getAppearance().add("pressed", (Border)new BevelBorder(Color.LIGHT_GRAY, Color.DARK_GRAY));
/*  66 */     b.getAppearance().add("focused", (Border)new PlainBorder(1, 2, 2, 1, 
/*  67 */           new Color(200.0F, 0.0F, 0.0F, 0.9F), false, Span.PADDING), false);
/*     */     
/*  69 */     b.getAppearance().setTextColor(Color.WHITE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(CheckBox b) {
/*  75 */     b.getAppearance().setTextColor(Color.BLACK);
/*     */     
/*  77 */     BufferedImage bi = new BufferedImage(10, 10, 2);
/*  78 */     Graphics g = bi.getGraphics();
/*  79 */     g.setColor(Color.BLACK);
/*  80 */     g.drawRect(0, 0, 9, 9);
/*     */     
/*  82 */     BufferedImage bi1 = new BufferedImage(10, 10, 2);
/*  83 */     g = bi1.getGraphics();
/*  84 */     g.setColor(Color.BLACK);
/*  85 */     g.drawRect(0, 0, 9, 9);
/*  86 */     g.drawLine(0, 0, 9, 9);
/*  87 */     g.drawLine(0, 9, 9, 0);
/*     */     
/*  89 */     b.getAppearance().add((Switch)new SetPixmapSwitch("default", new Pixmap(Binding.getInstance().getTexture(bi))));
/*  90 */     b.getAppearance().add((Switch)new SetPixmapSwitch("selected", new Pixmap(Binding.getInstance().getTexture(bi1))));
/*     */     
/*  92 */     b.getAppearance().setGap(5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(RadioButton b) {
/*  98 */     b.getAppearance().setGap(5);
/*  99 */     b.getAppearance().setTextColor(Color.BLACK);
/* 100 */     b.getAppearance().setAlignment(Alignment.LEFT);
/*     */     
/* 102 */     BufferedImage bi = new BufferedImage(10, 10, 2);
/* 103 */     Graphics g = bi.getGraphics();
/* 104 */     g.setColor(Color.BLACK);
/* 105 */     g.drawOval(0, 0, 9, 9);
/*     */     
/* 107 */     BufferedImage bi1 = new BufferedImage(10, 10, 2);
/* 108 */     g = bi1.getGraphics();
/* 109 */     g.setColor(Color.GREEN);
/* 110 */     g.fillOval(0, 0, 9, 9);
/* 111 */     g.setColor(Color.BLACK);
/* 112 */     g.drawOval(0, 0, 9, 9);
/*     */     
/* 114 */     b.setPixmap(new Pixmap(Binding.getInstance().getTexture(bi)));
/* 115 */     b.getAppearance().add((Switch)new SetPixmapSwitch("default", b.getPixmap()));
/* 116 */     b.getAppearance().add((Switch)new SetPixmapSwitch("selected", new Pixmap(Binding.getInstance().getTexture(bi1))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(TextEditor te) {
/* 122 */     te.getAppearance().add((Background)new PlainBackground(Color.WHITE));
/* 123 */     te.getAppearance().add((Border)new PlainBorder(Color.DARK_GRAY));
/* 124 */     te.getAppearance().setTextColor(Color.BLACK);
/* 125 */     te.getAppearance().getCursorPainter().setCursorColor(Color.BLACK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Tree l) {
/* 131 */     l.getAppearance().add((Background)new PlainBackground(Color.WHITE));
/*     */     
/* 133 */     BufferedImage bi = new BufferedImage(9, 9, 2);
/* 134 */     Graphics g = bi.getGraphics();
/* 135 */     g.setColor(Color.WHITE);
/* 136 */     g.fillRect(0, 0, 9, 9);
/* 137 */     g.setColor(Color.GRAY);
/* 138 */     g.drawRect(0, 0, 8, 8);
/* 139 */     g.setColor(Color.BLACK);
/* 140 */     g.drawLine(4, 2, 4, 6);
/* 141 */     g.drawLine(2, 4, 6, 4);
/* 142 */     l.getAppearance().setPlusIcon(new Pixmap(Binding.getInstance().getTexture(bi)));
/*     */     
/* 144 */     bi = new BufferedImage(9, 9, 2);
/* 145 */     g = bi.getGraphics();
/* 146 */     g.setColor(Color.WHITE);
/* 147 */     g.fillRect(0, 0, 9, 9);
/* 148 */     g.setColor(Color.GRAY);
/* 149 */     g.drawRect(0, 0, 8, 8);
/* 150 */     g.setColor(Color.BLACK);
/*     */     
/* 152 */     g.drawLine(2, 4, 6, 4);
/* 153 */     l.getAppearance().setMinusIcon(new Pixmap(Binding.getInstance().getTexture(bi)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Table w) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(ComboBox b) {
/* 165 */     b.getAppearance().add((Border)new PlainBorder(Color.DARK_GRAY));
/*     */     
/* 167 */     BufferedImage bi = new BufferedImage(10, 10, 2);
/* 168 */     Graphics g = bi.getGraphics();
/* 169 */     g.setColor(Color.RED);
/* 170 */     g.drawString("\\/", 2, 10);
/*     */     
/* 172 */     ITexture tex = Binding.getInstance().getTexture(bi);
/*     */     
/* 174 */     b.setPixmap(new Pixmap(tex));
/*     */     
/* 176 */     b.getList().getAppearance().add((Background)new GradientBackground(Color.WHITE, Color.WHITE_HALF_OPAQUE, Color.WHITE_HALF_OPAQUE, Color.WHITE));
/* 177 */     b.getPopupContainer().getAppearance().add((Border)new PlainBorder(Color.GRAY));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(ScrollBar l) {
/* 183 */     if (l.isHorizontal()) {
/*     */       
/* 185 */       l.getIncreaseButton().setText(">>");
/* 186 */       l.getDecreaseButton().setText("<<");
/*     */     }
/*     */     else {
/*     */       
/* 190 */       l.getIncreaseButton().setText(" /\\ ");
/* 191 */       l.getDecreaseButton().setText(" \\/ ");
/*     */     } 
/*     */     
/* 194 */     l.getIncreaseButton().getAppearance().setMargin(Spacing.ZERO_SPACING);
/* 195 */     l.getDecreaseButton().getAppearance().setMargin(Spacing.ZERO_SPACING);
/*     */     
/* 197 */     l.getIncreaseButton().getAppearance().removeAll();
/* 198 */     l.getDecreaseButton().getAppearance().removeAll();
/*     */     
/* 200 */     l.getIncreaseButton().getAppearance().add("default", (Border)new PlainBorder(Color.GRAY));
/* 201 */     l.getDecreaseButton().getAppearance().add("default", (Border)new PlainBorder(Color.GRAY));
/*     */     
/* 203 */     l.getIncreaseButton().getAppearance().add("mouseHover", (Border)new PlainBorder(Color.RED));
/* 204 */     l.getDecreaseButton().getAppearance().add("mouseHover", (Border)new PlainBorder(Color.RED));
/*     */ 
/*     */     
/* 207 */     l.getIncreaseButton().getAppearance().add(
/* 208 */         (Switch)new SetTextColorSwitch("mouseHover", Color.RED));
/* 209 */     l.getDecreaseButton().getAppearance().add(
/* 210 */         (Switch)new SetTextColorSwitch("mouseHover", Color.RED));
/*     */     
/* 212 */     l.getIncreaseButton().getAppearance().add(
/* 213 */         (Switch)new SetTextColorSwitch("default", Color.BLACK));
/* 214 */     l.getDecreaseButton().getAppearance().add(
/* 215 */         (Switch)new SetTextColorSwitch("default", Color.BLACK));
/*     */     
/* 217 */     l.getSlider().getSliderButton().getAppearance().add("mouseHover", (Border)new PlainBorder(Color.RED, false));
/*     */     
/* 219 */     l.getIncreaseButton().getAppearance().setEnabled("mouseHover", false);
/* 220 */     l.getDecreaseButton().getAppearance().setEnabled("mouseHover", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Label l) {
/* 227 */     l.getAppearance().setTextColor(Color.BLACK);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Window w) {
/* 233 */     w.getAppearance().add((Background)new PlainBackground(Color.WHITE_HALF_OPAQUE));
/* 234 */     w.getAppearance().add((Border)new PlainBorder(Color.BLUE, 3));
/*     */     
/* 236 */     if (w.getCloseButton() != null) {
/*     */       
/* 238 */       w.getCloseButton().setText("X");
/* 239 */       w.getCloseButton().getAppearance().setMargin(new Spacing(2, 2));
/* 240 */       w.getCloseButton().updateMinSize();
/* 241 */       w.getCloseButton().setSizeToMinSize();
/* 242 */       w.getCloseButton().setShrinkable(false);
/* 243 */       w.getCloseButton().setExpandable(false);
/*     */     } 
/*     */     
/* 246 */     if (w.getMaximizeButton() != null) {
/*     */       
/* 248 */       w.getMaximizeButton().setText("O");
/* 249 */       w.getMaximizeButton().getAppearance().setMargin(new Spacing(2, 2));
/* 250 */       w.getMaximizeButton().updateMinSize();
/* 251 */       w.getMaximizeButton().setSizeToMinSize();
/* 252 */       w.getMaximizeButton().setShrinkable(false);
/* 253 */       w.getMaximizeButton().setExpandable(false);
/*     */     } 
/*     */     
/* 256 */     if (w.getMinimizeButton() != null) {
/*     */       
/* 258 */       w.getMinimizeButton().setText("_");
/* 259 */       w.getMinimizeButton().getAppearance().setMargin(new Spacing(2, 2));
/* 260 */       w.getMinimizeButton().updateMinSize();
/* 261 */       w.getMinimizeButton().setSizeToMinSize();
/* 262 */       w.getMinimizeButton().setShrinkable(false);
/* 263 */       w.getMinimizeButton().setExpandable(false);
/*     */     } 
/*     */ 
/*     */     
/* 267 */     w.getTitleLabel().getAppearance().setPadding(new Spacing(0, 5, 0, 0));
/* 268 */     w.getTitleLabel().getAppearance().setTextColor(Color.WHITE);
/*     */     
/* 270 */     w.getTitleBar().getAppearance().add((Background)new GradientBackground(Color.BLUE, Color.LIGHT_BLUE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Slider l) {
/* 277 */     PlainBackground plainBackground1 = new PlainBackground(Color.WHITE_HALF_OPAQUE);
/* 278 */     PlainBackground plainBackground2 = new PlainBackground(Color.BLACK_HALF_OPAQUE);
/*     */     
/* 280 */     l.getSliderButton().getAppearance().removeAll();
/* 281 */     l.getSliderButton().getAppearance().setMargin(Spacing.ZERO_SPACING);
/* 282 */     l.getSliderButton().getAppearance().add((Background)plainBackground1);
/* 283 */     l.getSliderButton().getAppearance().add((Border)new PlainBorder(Color.LIGHT_BLUE));
/*     */     
/* 285 */     l.getAppearance().add("disabled", (Background)plainBackground2);
/*     */     
/* 287 */     if (l.isHorizontal()) {
/*     */       
/* 289 */       PlainBorder b = new PlainBorder(1, 0, 0, 1, Color.GRAY, true, Span.BORDER);
/* 290 */       b.setSpan(Span.PADDING);
/* 291 */       l.getAppearance().add((Border)b);
/*     */     }
/*     */     else {
/*     */       
/* 295 */       PlainBorder b = new PlainBorder(0, 1, 1, 0, Color.GRAY, true, Span.BORDER);
/* 296 */       b.setSpan(Span.PADDING);
/* 297 */       l.getAppearance().add((Border)b);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(ScrollContainer w) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(ProgressBar l) {
/* 310 */     l.getAppearance().setTextColor(Color.BLACK);
/* 311 */     l.getAppearance().setBorder((Spacing)new PlainBorder(Color.GRAY));
/* 312 */     l.getAppearance().setProgressBarColor(Color.LIGHT_BLUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Container w) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUpUnknown(IWidget w) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Menu m) {
/* 328 */     m.getAppearance().add((Background)new GradientBackground(Color.WHITE, Color.WHITE_HALF_OPAQUE, Color.WHITE_HALF_OPAQUE, Color.WHITE));
/* 329 */     m.getAppearance().add((Border)new PlainBorder(Color.GRAY));
/* 330 */     m.getAppearance().setTextColor(Color.BLACK);
/* 331 */     m.getAppearance().setDisabledColor(Color.GRAY);
/* 332 */     m.getAppearance().setTextSelectionColor(Color.BLACK);
/* 333 */     m.getAppearance().getSelectionUnderlay().add((IDecorator)new GradientBackground(Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE_HALF_OPAQUE, Color.WHITE));
/* 334 */     m.getAppearance().getSelectionUnderlay().add((IDecorator)new PlainBorder(1, 0, 0, 1, Color.LIGHT_GRAY, true, Span.BORDER));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(MenuBar mn) {
/* 340 */     mn.getAppearance().setBackground((Background)new GradientBackground(Color.LIGHT_GRAY, Color.WHITE_HALF_OPAQUE, Color.WHITE_HALF_OPAQUE, Color.WHITE));
/* 341 */     mn.getAppearance().setTextColor(Color.BLACK);
/* 342 */     mn.getAppearance().setSelectionTextColor(Color.WHITE);
/* 343 */     mn.getAppearance().getSelectionUnderlay().add((IDecorator)new PlainBackground(Color.LIGHT_BLUE));
/* 344 */     mn.getAppearance().getSelectionUnderlay().add((IDecorator)new PlainBorder(0, 1, 1, 0, Color.GRAY, true, Span.BORDER));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(List l) {
/* 350 */     l.getAppearance().setTextColor(Color.BLACK);
/* 351 */     l.getAppearance().getMouseHoverUnderlay().add((IDecorator)new GradientBackground(Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE_HALF_OPAQUE, Color.WHITE));
/* 352 */     l.getAppearance().getMouseHoverUnderlay().add((IDecorator)new PlainBorder(1, 0, 0, 1, Color.LIGHT_GRAY, true, Span.BORDER));
/* 353 */     l.getAppearance().getSelectionUnderlay().add((IDecorator)new PlainBackground(Color.LIGHT_BLUE));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(VerticalList b) {
/* 359 */     b.getAppearance().add((Border)new PlainBorder(Color.BLACK));
/* 360 */     b.getAppearance().add((Background)new PlainBackground(Color.WHITE));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(TabItemLabel b) {
/* 366 */     b.getAppearance().add((Border)new PlainBorder(Color.BLACK));
/* 367 */     b.getAppearance().setTextColor(Color.BLACK);
/* 368 */     b.getAppearance().setPadding(new Spacing(1, 5));
/*     */     
/* 370 */     b.getAppearance().add("default", (Background)new PlainBackground(Color.LIGHT_GRAY));
/* 371 */     b.getAppearance().add("mouseHover", (Background)new PlainBackground(Color.LIGHT_BLUE));
/* 372 */     b.getAppearance().add("active", (Background)new PlainBackground(Color.WHITE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(SplitContainer w) {
/* 379 */     w.getAppearance().getBarDecorator().add((IDecorator)new PlainBackground(Color.LIGHT_GRAY));
/* 380 */     w.getAppearance().getBarDecorator().add((IDecorator)new PlainBorder(Color.GRAY));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUp(Console w) {
/* 386 */     w.getAppearance().add((Background)new PlainBackground(Color.WHITE));
/* 387 */     w.getAppearance().add((Border)new PlainBorder(Color.BLACK));
/* 388 */     w.getAppearance().setPadding(new Spacing(5, 5, 5, 5));
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\xml\theme\DefaultTheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */