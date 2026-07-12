-- [A]
-- Sort: Mot de jouvence (ID: 162)
-- Classe: Eniripsa
--


function displayEffect()
	particleId = Particle.addParticleSystem(10720, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 62)	
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
invoke(400, 1, "executeAction")
displayEffect()
Sound.playSound(703, true)
