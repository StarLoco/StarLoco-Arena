-- [A]
-- Sort: Flèche enflammée (ID: 18)
-- Classe: Cra
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	if startMobileDirection == 1 then
	 particleFileId = 10927
	elseif startMobileDirection == 3 then
	 particleFileId = 10928
	elseif startMobileDirection == 5 then
	 particleFileId = 10926
	elseif startMobileDirection == 7 then
	 particleFileId = 10925
	end
	 
	particleId = Particle.addParticleSystem(particleFileId, startX, startY, startZ)		
end

function executeAction ()
	ScriptedAction.executeAllAction(3, 2)
	particleId = Particle.addParticleSystem(10929, destX, destY, destZ)	
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso 
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimFlecheEnflammee")

-- Animation du sort
invoke(200, 1, "displayEffect");
invoke(1600, 1, "executeAction")
Sound.playSound(1002, true)