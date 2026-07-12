-- [A]
-- Sort: Pétrification (ID: 148)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10340, destX, destY, destZ)	
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 1)
	ScriptedAction.executeFirstAction(3, 4)
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
Mobile.setMobileAnimation(startMobileId, "AnimEnuCorruption")

-- Animation du sort
invoke(100, 1, "displayEffect")
invoke(1000, 1, "executeAction")
Sound.playSound(603, true)
Sound.playSound(3036, true)