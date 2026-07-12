-- [A] 
-- Sort: Corruption (ID: 147) 
-- Classe: Enutrof
--


function displayEffect()
	-- Ajout du système de particule sur la destination (id de fichier = 10000)
	particleId = Particle.addParticleSystem(10325, destX, destY, destZ)
	invoke(150, 1, "nuage")
	invoke(1400, 1, "executeAction")
end

function nuage()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10230, destX, destY, destZ)

end

function playSpellSound()
	-- Joue un son en stéréo (id de fichier = 2)
	Sound.playSound(2031, true)
end

function playSpell2Sound()
	-- Joue un son en stéréo (id de fichier = 2)
	Sound.playSound(303, true)
end

function executeAction()
	ScriptedAction.executeAllAction(3, 98)
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
Mobile.setMobileAnimation(startMobileId, "AnimEnuLancer")

-- Affichage de l'effet (dans 850 ms)
invoke(50, 1, "playSpellSound")
invoke(1500, 1, "playSpell2Sound")
invoke(1500, 1, "displayEffect")