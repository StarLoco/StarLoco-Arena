package org.fenggui.render;

public interface ITextRenderer {
  void setText(String paramString);
  
  String getText();
  
  void setFont(Font paramFont);
  
  Font getFont();
  
  void render(int paramInt1, int paramInt2, Graphics paramGraphics, IOpenGL paramIOpenGL);
  
  void renderCarret(int paramInt1, int paramInt2, int paramInt3, ICarretRenderer paramICarretRenderer, Graphics paramGraphics, IOpenGL paramIOpenGL);
  
  int getWidth();
  
  int getHeight();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\ITextRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */