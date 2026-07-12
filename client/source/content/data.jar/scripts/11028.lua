-- [A]
-- Sort: Agacement (ID: 107)
-- Classe: Monster
--

function displayEffectCible()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)		
	particleIdCible = Particle.addParticleSystem(11028, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 14)
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
Mobile.setMobileAnimation(startMobileId, "AnimCast")

-- Animation du sort
invoke(200, 1, "displayEffectCible")
invoke(1000,1,"executeAction")
Sound.playSound(1005, true)