-- []
-- Sort : Invocation de Bouftou
-- Classe : Osamodas (ID: 110)
-- 


function displayEffect()
	particleId = Particle.addParticleSystem(10230, destX, destY, destZ)
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
-- Coordonnées du lanceur
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Coordonnées de l'impact
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimInvocationBouftout")
particleId2 = Particle.addParticleSystem(13000, startX, startY, startZ)

-- Animation du sort
invoke(700, 1, "displayEffect")
invoke(1040, 1, "executeAction")
invoke(300, 1, "playSpellSound")