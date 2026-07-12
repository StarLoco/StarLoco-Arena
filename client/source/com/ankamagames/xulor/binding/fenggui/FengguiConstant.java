/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.event.ActivationEvent;
/*     */ import com.ankamagames.xulor.event.FocusEvent;
/*     */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*     */ import com.ankamagames.xulor.event.KeyReleasedEvent;
/*     */ import com.ankamagames.xulor.event.MouseButtons;
/*     */ import com.ankamagames.xulor.event.MouseDraggedEvent;
/*     */ import com.ankamagames.xulor.event.MouseEnteredEvent;
/*     */ import com.ankamagames.xulor.event.MouseExitedEvent;
/*     */ import com.ankamagames.xulor.event.MouseMovedEvent;
/*     */ import com.ankamagames.xulor.event.MouseReleasedEvent;
/*     */ import com.ankamagames.xulor.event.MouseWheelEvent;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import com.ankamagames.xulor.util.Font;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import com.ankamagames.xulor.util.ScrollBarBehaviour;
/*     */ import com.ankamagames.xulor.util.Spacing;
/*     */ import com.ankamagames.xulor.util.Span;
/*     */ import org.fenggui.ScrollBar;
/*     */ import org.fenggui.Span;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.mouse.MouseButton;
/*     */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MouseMovedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
/*     */ import org.fenggui.event.mouse.MouseWheelEvent;
/*     */ import org.fenggui.layout.Alignment;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.ITexture;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Spacing;
/*     */ 
/*     */ public class FengguiConstant {
/*  46 */   private static PrintWriter writer = null;
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
/*     */   public static ScrollBar.ScrollBarBehaviour toFengguiScrollBarBehaviour(ScrollBarBehaviour sbb) {
/*  63 */     if (sbb != null) {
/*  64 */       if (sbb.equals(ScrollBarBehaviour.FORCE_DISPLAY))
/*  65 */         return ScrollBar.ScrollBarBehaviour.FORCE_DISPLAY; 
/*  66 */       if (sbb.equals(ScrollBarBehaviour.FORCE_HIDE)) {
/*  67 */         return ScrollBar.ScrollBarBehaviour.FORCE_HIDE;
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return ScrollBar.ScrollBarBehaviour.WHEN_NEEDED;
/*     */   }
/*     */   
/*     */   public static Font toFengguiFont(Font font) {
/*  75 */     if (font == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     if (font.getRenderedFont() != null) {
/*  79 */       return (Font)font.getRenderedFont();
/*     */     }
/*     */     
/*  82 */     font.setRenderedFont(FontFactory.renderStandardFont(font.getAWTFont(), font.isAntialiased(), Alphabet.getDefaultAlphabet()));
/*  83 */     return (Font)font.getRenderedFont();
/*     */   }
/*     */   
/*     */   public static Pixmap toFengguiPixmap(Pixmap pixmap) {
/*  87 */     if (pixmap == null || pixmap.getTexture() == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     if (pixmap.getInstanciatedPixmap() == null || pixmap.needReinstanciation()) {
/*  91 */       ITexture texture = (ITexture)pixmap.getTexture().getInstanciatedTexture();
/*  92 */       if (texture == null) {
/*  93 */         texture = Binding.getInstance().getTexture(pixmap.getTexture().getImage());
/*  94 */         if (texture != null) {
/*  95 */           pixmap.getTexture().releaseImage();
/*     */         }
/*  97 */         pixmap.getTexture().setInstanciatedTexture(texture);
/*     */       } 
/*  99 */       pixmap.setInstanciatedPixmap(new Pixmap(texture, pixmap.getX(), pixmap.getY(), pixmap.getWidth(), pixmap.getHeight()));
/* 100 */       pixmap.setNeedReinstanciation(false);
/*     */     } 
/* 102 */     return (Pixmap)pixmap.getInstanciatedPixmap();
/*     */   }
/*     */   
/*     */   public static Spacing toFengguiSpacing(Spacing spacing) {
/* 106 */     if (spacing == null) {
/* 107 */       return null;
/*     */     }
/* 109 */     return new Spacing(spacing.getTop(), spacing.getLeft(), 
/* 110 */         spacing.getRight(), spacing.getBottom());
/*     */   }
/*     */   
/*     */   public static Dimension toFengguiDimension(Dimension dim) {
/* 114 */     if (dim == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     return new Dimension(dim.getWidth(), dim.getHeight());
/*     */   }
/*     */   
/*     */   public static Color toFengguiColor(Color c) {
/* 121 */     if (c == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     return new Color((float)c.getRed(), (float)c.getGreen(), 
/* 125 */         (float)c.getBlue(), (float)c.getAlpha());
/*     */   }
/*     */   
/*     */   public static Span toFengguiSpan(Span span) {
/* 129 */     if (span == null) {
/* 130 */       return Span.BORDER;
/*     */     }
/* 132 */     if (span.equals(Span.BORDER))
/* 133 */       return Span.BORDER; 
/* 134 */     if (span.equals(Span.MARGIN))
/* 135 */       return Span.MARGIN; 
/* 136 */     if (span.equals(Span.PADDING)) {
/* 137 */       return Span.PADDING;
/*     */     }
/* 139 */     return Span.BORDER;
/*     */   }
/*     */   
/*     */   public static Alignment toFengguiAlignment(Alignment position) {
/* 143 */     if (position == null) {
/* 144 */       return Alignment.MIDDLE;
/*     */     }
/* 146 */     if (position.equals(Alignment.NORTH_WEST))
/* 147 */       return Alignment.TOP_LEFT; 
/* 148 */     if (position.equals(Alignment.NORTH))
/* 149 */       return Alignment.TOP; 
/* 150 */     if (position.equals(Alignment.NORTH_EAST))
/* 151 */       return Alignment.TOP_RIGHT; 
/* 152 */     if (position.equals(Alignment.WEST))
/* 153 */       return Alignment.LEFT; 
/* 154 */     if (position.equals(Alignment.CENTER))
/* 155 */       return Alignment.MIDDLE; 
/* 156 */     if (position.equals(Alignment.EAST))
/* 157 */       return Alignment.RIGHT; 
/* 158 */     if (position.equals(Alignment.SOUTH_WEST))
/* 159 */       return Alignment.BOTTOM_LEFT; 
/* 160 */     if (position.equals(Alignment.SOUTH))
/* 161 */       return Alignment.BOTTOM; 
/* 162 */     if (position.equals(Alignment.SOUTH_EAST)) {
/* 163 */       return Alignment.BOTTOM_RIGHT;
/*     */     }
/* 165 */     return Alignment.MIDDLE;
/*     */   }
/*     */   
/*     */   public static MouseButtons toXulorMouseButtons(MouseButton button) {
/* 169 */     if (button.equals(MouseButton.LEFT))
/* 170 */       return MouseButtons.BUTTON1; 
/* 171 */     if (button.equals(MouseButton.RIGHT))
/* 172 */       return MouseButtons.BUTTON3; 
/* 173 */     if (button.equals(MouseButton.MIDDLE))
/* 174 */       return MouseButtons.BUTTON2; 
/* 175 */     if (button.equals(MouseButton.WHEEL)) {
/* 176 */       return MouseButtons.WHEEL;
/*     */     }
/* 178 */     return MouseButtons.BUTTON1;
/*     */   }
/*     */   
/*     */   public static ActivationEvent toXulorActivationEvent(ActivationEvent event) {
/* 182 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 183 */     return new ActivationEvent(c, event.isEnabled());
/*     */   }
/*     */   
/*     */   public static FocusEvent toXulorFocusEvent(FocusEvent event) {
/* 187 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 188 */     return new FocusEvent(c, event.isFocusGained());
/*     */   }
/*     */   
/*     */   public static KeyPressedEvent toXulorKeyPressedEvent(KeyPressedEvent event) {
/* 192 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 193 */     return new KeyPressedEvent(c, event.getKey(), Key.valueOf(event.getKeyClass().toString()));
/*     */   }
/*     */   
/*     */   public static KeyReleasedEvent toXulorKeyReleasedEvent(KeyReleasedEvent event) {
/* 197 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 198 */     return new KeyReleasedEvent(c, event.getKey());
/*     */   }
/*     */   
/*     */   public static MouseDraggedEvent toXulorMouseDraggedEvent(MouseDraggedEvent event) {
/* 202 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 203 */     return new MouseDraggedEvent(c, event.getDisplayX(), event.getDisplayY(), toXulorMouseButtons(event.getButton()));
/*     */   }
/*     */   
/*     */   public static MouseEnteredEvent toXulorMouseEnteredEvent(MouseEnteredEvent event) {
/* 207 */     IComponent entered = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getEntered());
/* 208 */     IComponent exited = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getExited());
/*     */     
/* 210 */     return new MouseEnteredEvent(entered, exited);
/*     */   }
/*     */   
/*     */   public static MouseExitedEvent toXulorMouseExitedEvent(MouseExitedEvent event) {
/* 214 */     IComponent entered = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getEntered());
/* 215 */     IComponent exited = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getExited());
/*     */     
/* 217 */     return new MouseExitedEvent(entered, exited);
/*     */   }
/*     */   
/*     */   public static MouseMovedEvent toXulorMouseMovedEvent(MouseMovedEvent event) {
/* 221 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 222 */     return new MouseMovedEvent(c, event.getDisplayX(), event.getDisplayY());
/*     */   }
/*     */   
/*     */   public static MousePressedEvent toXulorMousePressedEvent(MousePressedEvent event) {
/* 226 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 227 */     return new MousePressedEvent(c, event.getDisplayX(), event.getDisplayY(), toXulorMouseButtons(event.getButton()));
/*     */   }
/*     */   
/*     */   public static MouseReleasedEvent toXulorMouseReleasedEvent(MouseReleasedEvent event) {
/* 231 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 232 */     return new MouseReleasedEvent(c, event.getDisplayX(), event.getDisplayY(), toXulorMouseButtons(event.getButton()));
/*     */   }
/*     */   
/*     */   public static MouseWheelEvent toXulorMouseWheelEvent(MouseWheelEvent event) {
/* 236 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 237 */     return new MouseWheelEvent(c, event.wheeledUp(), event.getRotations());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiConstant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */