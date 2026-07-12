-- [A]
-- Invocation poupée 
-- Classe: Sadida
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
end

function displayEffectCible()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleIdCible = Particle.addParticleSystem(11025, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 67)
end

function playSpellSound()
	Sound.playSound(206, true)
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
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimInvocPoupe")

-- Animation du sort
invoke(100, 1, "displayEffect")
invoke(2100, 1, "displayEffectCible")
invoke(2100, 1, "executeAction")
invoke(1500, 1, "playSpellSound")