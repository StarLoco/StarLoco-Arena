-- [A]
-- Sort : Invocation de Craqueleur
-- Classe : Osamodas (ID: 143)
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

-- Cordonnée de l'impact et du lanceur
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimInvocationBouftout")

-- Animation du sort
invoke(700, 1, "displayEffect")
invoke(1040, 1, "executeAction")	
invoke(400, 1, "playSpellSound")