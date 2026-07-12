-- [Y]
-- Sort : Attirance (ID : 27)
-- Classe : Sacrieur
--

function displayEffect()

	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	if startMobileDirection == 1 then
	 particleFileId = 11147
	elseif startMobileDirection == 3 then
	 particleFileId = 11145
	elseif startMobileDirection == 5 then
	 particleFileId = 11149
	elseif startMobileDirection == 7 then
	 particleFileId = 11151
	end	

	Particle.addParticleSystem(particleFileId, startX, startY, startZ)
end

function displayEffectCible()

	startMobileDirection = Mobile.getMobileDirection(startMobileId)

	if startMobileDirection == 1 then
	 particleFileId = 11148
	elseif startMobileDirection == 3 then
	 particleFileId = 11146
	elseif startMobileDirection == 5 then
	 particleFileId = 11150
	elseif startMobileDirection == 7 then
	 particleFileId = 11152
	end	

	Particle.addParticleSystem(particleFileId, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 38)
end

function playSpellSound()
	Sound.playSound(1003, true)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSacriAttirance01")

-- Animation du sort
displayEffect()
playSpellSound()
invoke(600, 1, "displayEffectCible")
invoke(600, 1, "executeAction")

