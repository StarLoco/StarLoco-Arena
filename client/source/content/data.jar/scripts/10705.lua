-- [A]
-- Sort: Mot soignant (ID: 22)
-- Classe: Eniripsa
--


function displayEffect()
	particleId = Particle.addParticleSystem(10700, destX, destY, destZ)
end

function executeAction ()
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
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni01")

displayEffect()
invoke(600,1,"executeAction")
Sound.playSound(704, true)