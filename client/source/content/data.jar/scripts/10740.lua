-- [A]
-- Sort: Mot de regeneration (ID: 133)
-- Classe: Eniripsa 
--


function playSpellSound()
	Sound.playSound(702, true)
end

function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleId = Particle.addParticleSystem(10740, destX, destY, destZ)
end

function executeAction()
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

Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni02")

-- Animation du sort
invoke(250, 1, "displayEffect")
invoke(300, 1, "executeAction")
invoke(270, 1, "playSpellSound")