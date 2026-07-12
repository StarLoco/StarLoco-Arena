-- [A]
-- Sort: Puissance sylvestre
-- Classe: Sadida
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	if startMobileDirection == 1 then
	 particleFileId = 11021
	elseif startMobileDirection == 3 then
	 particleFileId = 11020
	elseif startMobileDirection == 5 then
	 particleFileId = 11022
	elseif startMobileDirection == 7 then
	 particleFileId = 11023
	end
	 
	particleId = Particle.addParticleSystem(particleFileId, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 69)
	ScriptedAction.executeFirstAction(3, 80)
	ScriptedAction.executeFirstAction(3, 96)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimPuissanceSylvestre")

-- Animation du script
displayEffect ()
invoke(1500,1,"executeAction")
Sound.playSound(1002, true)