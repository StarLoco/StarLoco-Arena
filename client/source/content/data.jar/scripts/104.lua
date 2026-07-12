-- Case bouclier

startMobileId = EffectArea.getTarget()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

Particle.addParticleSystem(13011, startX, startY, startZ)
