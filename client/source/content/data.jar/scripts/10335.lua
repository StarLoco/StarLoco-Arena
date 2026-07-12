-- [A]--> son pas synchro 
-- Sort: tanière des roches (ID: 145)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10335, startX, startY, startZ)	
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 57)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)


-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimEnuTanniere")

-- Affichage de l'effet 
invoke(1200, 1, "displayEffect");
invoke(2000,1,"executeAction")
Sound.playSound(300, true)