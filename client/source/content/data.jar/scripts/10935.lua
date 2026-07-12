-- [A]
-- Sort: Flèche magique (ID: 16)
-- Classe: Cra
--


function displayEffect()
	particleId, time = Particle.addTweenParticleSystem(10960, startX, startY, startZ, destX, destY, destZ, 60, 1, 3)
	particleId2, time = Particle.addTweenParticleSystem(10937, startX, startY, startZ, destX, destY, destZ, 60, 0, 3)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 2)
	particleId3 = Particle.addParticleSystem(10935, destX, destY, destZ)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimFlecheMagique")
particleId4 = Particle.addParticleSystem(10936, startX, startY, startZ)

-- Animation du sort
invoke(2000, 1, "displayEffect")
invoke(2100, 1, "executeAction")