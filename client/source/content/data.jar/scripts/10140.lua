-- [A]
-- Sort : Bouclier Feca (ID : 36)
-- Classe : Feca
--

function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10140, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 80)
end


--
-- Execution du script
--

ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimBouclierFeca")

particleId = Particle.addParticleSystem(13000, startX, startY, startZ)

Sound.playSound(103, true)
invoke(2000, 1, "displayEffect");
invoke(2000,1,"executeAction")
