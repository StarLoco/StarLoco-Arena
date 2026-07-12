-- [A]
-- Sort: Pelle massacrante
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10315, destX, destY, destZ)
	Sound.playSound(304, true)
	Sound.playSound(2033, true)
end

function playVoixSound()
	Sound.playSound(3035, true)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 4)
end

--
--Exécution du script
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
Mobile.setMobileAnimation(startMobileId, "AnimEnuAcceleration")

-- Animation du sort
invoke(1000,1,"executeAction")
invoke(500, 1, "displayEffect");
invoke(100, 1, "playVoixSound");