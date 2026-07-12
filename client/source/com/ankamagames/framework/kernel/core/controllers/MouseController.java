package com.ankamagames.framework.kernel.core.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface MouseController {
  boolean mouseClicked(MouseEvent paramMouseEvent);
  
  boolean mousePressed(MouseEvent paramMouseEvent);
  
  boolean mouseReleased(MouseEvent paramMouseEvent);
  
  boolean mouseEntered(MouseEvent paramMouseEvent);
  
  boolean mouseExited(MouseEvent paramMouseEvent);
  
  boolean mouseDragged(MouseEvent paramMouseEvent);
  
  boolean mouseMoved(MouseEvent paramMouseEvent);
  
  boolean mouseWheelMoved(MouseWheelEvent paramMouseWheelEvent);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\controllers\MouseController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */