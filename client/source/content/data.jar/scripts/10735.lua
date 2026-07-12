-- [A]
-- Sort: Mot d'effacement (ID: 161)
-- Classe: Eniripsa
--


function displayEffectCible()
	particleId = Particle.addParticleSystem(10735, destX, destY, destZ)	
end

function displayEffectLanceur()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 57)
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
Mobile.setMobileAnimation(startMobileId, "AnimSrtMotEni03")

-- Animation du sort
invoke(450, 1, "displayEffectCible")
invoke(400, 1, "displayEffectLanceur")
invoke(1200,1,"executeAction")
Sound.playSound(708, true)
