-- []
-- Sort: 
-- Classe: Osamoda
--


function displayEffect()
	particleId = Particle.addParticleSystem(10230, destX, destY, destZ)	
	invoke(3000, 1, "removeParticle", particleId)
end

function removeParticle(particleId)
	Particle.removeParticleSystem(particleId)
end

function playSpellSound()
	Sound.playSound(206, true)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimInvocationtofu")
particleId2 = Particle.addParticleSystem(13000, startX, startY, startZ)
invoke(800, 1, "removeParticle", particleId2)

-- Animation du sort
invoke(1000, 1, "displayEffect")
invoke(600, 1, "playSpellSound")