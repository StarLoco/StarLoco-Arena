-- [A]
-- Sort: Force de l'âge (ID: 49)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10330, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 82)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)


-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimEnuForcedelage")

-- Affichage de l'effet (dans 850 ms)
Sound.playSound(3038, true)
Sound.playSound(2004, true)
invoke(400, 1, "displayEffect");
invoke(1600,1,"executeAction")