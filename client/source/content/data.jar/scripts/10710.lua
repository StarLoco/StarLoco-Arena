-- [A]
-- Sort: Mot revitalisant (ID: 25)
-- Classe: Eniripsa
--


function displayEffect()
	particleId = Particle.addParticleSystem(10710, destX, destY, destZ)
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
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni02")

-- Animation du sort
displayEffect();
invoke(400,1,"executeAction")
Sound.playSound(705, true)