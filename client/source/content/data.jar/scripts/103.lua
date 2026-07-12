-- Case oeil de lynx

startMobileId = EffectArea.getTarget()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

Particle.addParticleSystem(13012, startX, startY, startZ)
