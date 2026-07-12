-- [A]
-- Sort: Connaissance des poupées (ID: 79)
-- Classe: Sadida
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	if startMobileDirection == 1 then
	 particleFileId = 11011
	elseif startMobileDirection == 3 then
	 particleFileId = 11010
	elseif startMobileDirection == 5 then
	 particleFileId = 11012
	elseif startMobileDirection == 7 then
	 particleFileId = 11013
	end

	particleId = Particle.addParticleSystem(particleFileId, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 74)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimConnaissancePoupe")

-- Affichage de l'effet (dans 850 ms)

invoke(1500,1,"executeAction")
displayEffect()
Sound.playSound(1006, true)