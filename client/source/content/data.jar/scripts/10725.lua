-- [A]
-- Sort: mot de sacrifice (ID: 24)
-- Classe: Eniripsa
--


function displayEffect()
	particleId = Particle.addParticleSystem(10726, destX, destY, destZ)	
	startMobileDirection = Mobile.getMobileDirection(startMobileId) 
	particleId2 = Particle.addParticleSystem(10725, startX, startY, startZ)	
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 2)
	ScriptedAction.executeFirstAction(3, 69)
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
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni01")

-- Animation du sort
invoke(450, 1, "displayEffect")
invoke(600, 1, "executeAction")
--Sound.playSound(706, true)
