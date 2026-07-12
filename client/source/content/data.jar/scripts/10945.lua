-- [A]
-- Sort: flèche de recul (ID: 168)
-- Classe: Cra
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	if startMobileDirection == 1 then
	 particleFileId = 10947
	elseif startMobileDirection == 3 then
	 particleFileId = 10948
	elseif startMobileDirection == 5 then
	 particleFileId = 10946
	elseif startMobileDirection == 7 then
	 particleFileId = 10945
	end
	 
	particleId = Particle.addParticleSystem(particleFileId, startX, startY, startZ)
	invoke(500, 1, "displayEffectCible")
end

function displayEffectCible()

	--perte pv
ScriptedAction.executeFirstAction(3, 5)
ScriptedAction.executeFirstAction(3, 37)
	
		if startMobileDirection == 1 then
	 particleFileId2 = 10951
	elseif startMobileDirection == 3 then
	 particleFileId2 = 10952
	elseif startMobileDirection == 5 then
	 particleFileId2 = 10950
	elseif startMobileDirection == 7 then
	 particleFileId2 = 10949
	end

	-- Ajout du système de particule sur la destination (id de fichier = 10000)
	particleId = Particle.addParticleSystem(particleFileId2, destX, destY, destZ)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimFlecheDeRecul")

-- Affichage de l'effet
invoke(300, 1, "displayEffect");
Sound.playSound(1002, true)