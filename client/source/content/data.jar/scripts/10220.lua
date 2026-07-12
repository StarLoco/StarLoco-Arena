-- [A]
-- Sort: Bénédiction animale
-- Classe: Osamoda (ID: 55)
--


function displayEffect()
	particleId = Particle.addParticleSystem(10220, startX, startY, startZ)	
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 74)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimBenedictionAnimale")

-- Animation du sort
invoke(100, 1, "displayEffect")
invoke(2000,1,"executeAction")
Sound.playSound(203, true)