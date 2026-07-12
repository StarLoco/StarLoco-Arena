package com.ankamagames.graphics.isometric;

public abstract interface RenderProcessHandler<HandlerScene extends IsoWorldScene>
{
  public abstract void process(HandlerScene paramHandlerScene, long paramLong, int paramInt);
  
  public abstract void prepareBeforeRendering(HandlerScene paramHandlerScene, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\RenderProcessHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */