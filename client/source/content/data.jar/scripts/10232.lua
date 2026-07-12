-- [A]
-- Sort : Invocation de Prespic (ID: 111)
-- Classe : Osamodas
-- 


function displayEffect()
	particleId = Particle.addParticleSystem(10230, destX, destY, destZ)	
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 67)
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
Mobile.setMobileAnimation(startMobileId, "AnimInvocationPrespic")
particleId2 = Particle.addParticleSystem(13000, startX, startY, startZ)

-- Animation du sort
invoke(400, 1, "displayEffect")
invoke(740, 1, "executeAction")
Sound.playSound(206, true)