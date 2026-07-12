/*     */ package com.ankamagames.xulor.binding.fenggui;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.MouseButtons;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.util.ScrollBarBehaviour;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
/*     */ import java.io.PrintWriter;
/*     */ import org.fenggui.ScrollBar.ScrollBarBehaviour;
/*     */ import org.fenggui.event.mouse.MouseButton;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.ITexture;
/*     */ import org.fenggui.util.Alphabet;
/*     */ import org.fenggui.util.fonttoolkit.FontFactory;
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
/*     */ public class FengguiConstant
/*     */ {
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
/*     */   public static ScrollBar.ScrollBarBehaviour toFengguiScrollBarBehaviour(ScrollBarBehaviour sbb)
/*     */   {
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
/*     */   public static org.fenggui.render.Font toFengguiFont(com.ankamagames.xulor.util.Font font) {
/*  75 */     if (font == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     if (font.getRenderedFont() != null) {
/*  79 */       return (org.fenggui.render.Font)font.getRenderedFont();
/*     */     }
/*     */     
/*  82 */     font.setRenderedFont(FontFactory.renderStandardFont(font.getAWTFont(), font.isAntialiased(), Alphabet.getDefaultAlphabet()));
/*  83 */     return (org.fenggui.render.Font)font.getRenderedFont();
/*     */   }
/*     */   
/*     */   public static org.fenggui.render.Pixmap toFengguiPixmap(com.ankamagames.xulor.util.Pixmap pixmap) {
/*  87 */     if ((pixmap == null) || (pixmap.getTexture() == null)) {
/*  88 */       return null;
/*     */     }
/*  90 */     if ((pixmap.getInstanciatedPixmap() == null) || (pixmap.needReinstanciation())) {
/*  91 */       ITexture texture = (ITexture)pixmap.getTexture().getInstanciatedTexture();
/*  92 */       if (texture == null) {
/*  93 */         texture = Binding.getInstance().getTexture(pixmap.getTexture().getImage());
/*  94 */         if (texture != null) {
/*  95 */           pixmap.getTexture().releaseImage();
/*     */         }
/*  97 */         pixmap.getTexture().setInstanciatedTexture(texture);
/*     */       }
/*  99 */       pixmap.setInstanciatedPixmap(new org.fenggui.render.Pixmap(texture, pixmap.getX(), pixmap.getY(), pixmap.getWidth(), pixmap.getHeight()));
/* 100 */       pixmap.setNeedReinstanciation(false);
/*     */     }
/* 102 */     return (org.fenggui.render.Pixmap)pixmap.getInstanciatedPixmap();
/*     */   }
/*     */   
/*     */   public static org.fenggui.util.Spacing toFengguiSpacing(com.ankamagames.xulor.util.Spacing spacing) {
/* 106 */     if (spacing == null) {
/* 107 */       return null;
/*     */     }
/* 109 */     return new org.fenggui.util.Spacing(spacing.getTop(), spacing.getLeft(), 
/* 110 */       spacing.getRight(), spacing.getBottom());
/*     */   }
/*     */   
/*     */   public static org.fenggui.util.Dimension toFengguiDimension(com.ankamagames.xulor.util.Dimension dim) {
/* 114 */     if (dim == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     return new org.fenggui.util.Dimension(dim.getWidth(), dim.getHeight());
/*     */   }
/*     */   
/*     */   public static org.fenggui.util.Color toFengguiColor(com.ankamagames.xulor.util.Color c) {
/* 121 */     if (c == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     return new org.fenggui.util.Color((float)c.getRed(), (float)c.getGreen(), 
/* 125 */       (float)c.getBlue(), (float)c.getAlpha());
/*     */   }
/*     */   
/*     */   public static org.fenggui.Span toFengguiSpan(com.ankamagames.xulor.util.Span span) {
/* 129 */     if (span == null) {
/* 130 */       return org.fenggui.Span.BORDER;
/*     */     }
/* 132 */     if (span.equals(com.ankamagames.xulor.util.Span.BORDER))
/* 133 */       return org.fenggui.Span.BORDER;
/* 134 */     if (span.equals(com.ankamagames.xulor.util.Span.MARGIN))
/* 135 */       return org.fenggui.Span.MARGIN;
/* 136 */     if (span.equals(com.ankamagames.xulor.util.Span.PADDING)) {
/* 137 */       return org.fenggui.Span.PADDING;
/*     */     }
/* 139 */     return org.fenggui.Span.BORDER;
/*     */   }
/*     */   
/*     */   public static org.fenggui.layout.Alignment toFengguiAlignment(com.ankamagames.xulor.util.Alignment position) {
/* 143 */     if (position == null) {
/* 144 */       return org.fenggui.layout.Alignment.MIDDLE;
/*     */     }
/* 146 */     if (position.equals(com.ankamagames.xulor.util.Alignment.NORTH_WEST))
/* 147 */       return org.fenggui.layout.Alignment.TOP_LEFT;
/* 148 */     if (position.equals(com.ankamagames.xulor.util.Alignment.NORTH))
/* 149 */       return org.fenggui.layout.Alignment.TOP;
/* 150 */     if (position.equals(com.ankamagames.xulor.util.Alignment.NORTH_EAST))
/* 151 */       return org.fenggui.layout.Alignment.TOP_RIGHT;
/* 152 */     if (position.equals(com.ankamagames.xulor.util.Alignment.WEST))
/* 153 */       return org.fenggui.layout.Alignment.LEFT;
/* 154 */     if (position.equals(com.ankamagames.xulor.util.Alignment.CENTER))
/* 155 */       return org.fenggui.layout.Alignment.MIDDLE;
/* 156 */     if (position.equals(com.ankamagames.xulor.util.Alignment.EAST))
/* 157 */       return org.fenggui.layout.Alignment.RIGHT;
/* 158 */     if (position.equals(com.ankamagames.xulor.util.Alignment.SOUTH_WEST))
/* 159 */       return org.fenggui.layout.Alignment.BOTTOM_LEFT;
/* 160 */     if (position.equals(com.ankamagames.xulor.util.Alignment.SOUTH))
/* 161 */       return org.fenggui.layout.Alignment.BOTTOM;
/* 162 */     if (position.equals(com.ankamagames.xulor.util.Alignment.SOUTH_EAST)) {
/* 163 */       return org.fenggui.layout.Alignment.BOTTOM_RIGHT;
/*     */     }
/* 165 */     return org.fenggui.layout.Alignment.MIDDLE;
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
/*     */   public static com.ankamagames.xulor.event.ActivationEvent toXulorActivationEvent(org.fenggui.event.ActivationEvent event) {
/* 182 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 183 */     return new com.ankamagames.xulor.event.ActivationEvent(c, event.isEnabled());
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.FocusEvent toXulorFocusEvent(org.fenggui.event.FocusEvent event) {
/* 187 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 188 */     return new com.ankamagames.xulor.event.FocusEvent(c, event.isFocusGained());
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.KeyPressedEvent toXulorKeyPressedEvent(org.fenggui.event.KeyPressedEvent event) {
/* 192 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 193 */     return new com.ankamagames.xulor.event.KeyPressedEvent(c, event.getKey(), com.ankamagames.xulor.event.Key.valueOf(event.getKeyClass().toString()));
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.KeyReleasedEvent toXulorKeyReleasedEvent(org.fenggui.event.KeyReleasedEvent event) {
/* 197 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 198 */     return new com.ankamagames.xulor.event.KeyReleasedEvent(c, event.getKey());
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MouseDraggedEvent toXulorMouseDraggedEvent(org.fenggui.event.mouse.MouseDraggedEvent event) {
/* 202 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 203 */     return new com.ankamagames.xulor.event.MouseDraggedEvent(c, event.getDisplayX(), event.getDisplayY(), toXulorMouseButtons(event.getButton()));
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MouseEnteredEvent toXulorMouseEnteredEvent(org.fenggui.event.mouse.MouseEnteredEvent event) {
/* 207 */     IComponent entered = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getEntered());
/* 208 */     IComponent exited = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getExited());
/*     */     
/* 210 */     return new com.ankamagames.xulor.event.MouseEnteredEvent(entered, exited);
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MouseExitedEvent toXulorMouseExitedEvent(org.fenggui.event.mouse.MouseExitedEvent event) {
/* 214 */     IComponent entered = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getEntered());
/* 215 */     IComponent exited = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getExited());
/*     */     
/* 217 */     return new com.ankamagames.xulor.event.MouseExitedEvent(entered, exited);
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MouseMovedEvent toXulorMouseMovedEvent(org.fenggui.event.mouse.MouseMovedEvent event) {
/* 221 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 222 */     return new com.ankamagames.xulor.event.MouseMovedEvent(c, event.getDisplayX(), event.getDisplayY());
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MousePressedEvent toXulorMousePressedEvent(org.fenggui.event.mouse.MousePressedEvent event) {
/* 226 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 227 */     return new com.ankamagames.xulor.event.MousePressedEvent(c, event.getDisplayX(), event.getDisplayY(), toXulorMouseButtons(event.getButton()));
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MouseReleasedEvent toXulorMouseReleasedEvent(org.fenggui.event.mouse.MouseReleasedEvent event) {
/* 231 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 232 */     return new com.ankamagames.xulor.event.MouseReleasedEvent(c, event.getDisplayX(), event.getDisplayY(), toXulorMouseButtons(event.getButton()));
/*     */   }
/*     */   
/*     */   public static com.ankamagames.xulor.event.MouseWheelEvent toXulorMouseWheelEvent(org.fenggui.event.mouse.MouseWheelEvent event) {
/* 236 */     IComponent c = (IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(event.getSource());
/* 237 */     return new com.ankamagames.xulor.event.MouseWheelEvent(c, event.wheeledUp(), event.getRotations());
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\FengguiConstant.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */