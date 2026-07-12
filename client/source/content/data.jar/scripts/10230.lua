-- [A]
-- Sort : Invocation de Tofu (ID: 51)
-- Classe : Osamodas 
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
startMobileId = Cast.getCaster()

-- Coordonnées du lanceur et de l'impact
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimInvocationtofu")
particleId2 = Particle.addParticleSystem(13000, startX, startY, startZ)

-- Animation du sort
invoke(400, 1, "displayEffect")
invoke(740, 1, "executeAction")
invoke(300, 1, "playSpellSound")