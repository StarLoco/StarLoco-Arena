-- [A]
-- Sort: Tremblement (ID: 83)
-- Classe: Sadida
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleId = Particle.addParticleSystem(11005, startX, startY, startZ)
	particleIdCible = Particle.addParticleSystem(11005, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeAllAction(3, 3)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du lanceur
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
-- Recup des coordonnees dela cible
destX, destY, destZ = Cast.getPosition()
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimTremblement")

-- Animation du sort
displayEffect ()
invoke(1800,1,"executeAction")
Sound.playSound(1007, true)
