-- [A]
-- Sort : Invocation Arbre (ID: 175)
-- Classe : Sadida
-- 


function displayEffect()
	particleId = Particle.addParticleSystem(10230, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 67)
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
Mobile.setMobileAnimation(startMobileId, "AnimArbre")

particleId2 = Particle.addParticleSystem(13000, startX, startY, startZ)

-- Animation du sort
invoke(700, 1, "displayEffect")
invoke(1000, 1, "executeAction")
Sound.playSound(206, true)
