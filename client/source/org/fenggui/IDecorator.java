package org.fenggui;

import org.fenggui.io.IOStreamSaveable;
import org.fenggui.render.Graphics;

public interface IDecorator extends IOStreamSaveable {
  boolean isEnabled();
  
  String getLabel();
  
  Span getSpan();
  
  void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void setEnabled(boolean paramBoolean);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\IDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */