-- [A]
-- Sort: Cri de l'Ours (ID: 144)
-- Classe: Osamoda
--


function displayEffect()
	particleId = Particle.addParticleSystem(10200, destX, destY, destZ)
	invoke(1500, 1, "removeParticle")
end

function removeParticle()
	Particle.removeParticleSystem(particleId)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 82)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast")
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du sort
invoke(200, 1, "displayEffect")
invoke(1500,1,"executeAction")
Sound.playSound(205, true)
