-- [A]
-- Sort : Teleportation (ID : 140)
-- Classe : Feca
--

function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10135, startX, startY, startZ)

end

function displayEffectCible()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10135, destX, destY, destZ)
	
	Sound.playSound(108, true)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 39)	
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimArmureFeuVEnt")

invoke(0, 1, "displayEffect" )
invoke(1200, 1, "displayEffectCible" )
invoke(2000, 1, "executeAction")



