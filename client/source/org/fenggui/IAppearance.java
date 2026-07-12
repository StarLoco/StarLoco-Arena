package org.fenggui;

import org.fenggui.render.Graphics;
import org.fenggui.render.IOpenGL;
import org.fenggui.util.Dimension;

public interface IAppearance {
  void paint(Graphics paramGraphics, IOpenGL paramIOpenGL);
  
  Dimension getMinSizeHint();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\IAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */