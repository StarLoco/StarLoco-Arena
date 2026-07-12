-- [Y]
-- Sort : Brume (ID : 153)
-- Classe : Sram
--

function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	if startMobileDirection == 1 then
	 particleFileId = 10441
	elseif startMobileDirection == 3 then
	 particleFileId = 10440
	elseif startMobileDirection == 5 then
	 particleFileId = 10442
	elseif startMobileDirection == 7 then
	 particleFileId = 10443
	end	 

	particleId = Particle.addParticleSystem(particleFileId, startX, startY, startZ)
end

function playSpellSound()
	Sound.playSound(403, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimBrume")

-- Animation du sort
playSpellSound()
displayEffect()
