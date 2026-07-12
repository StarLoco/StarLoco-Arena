-- [R]
-- Sort : Bond fu Félin (ID : 12)
-- Classe : Ecaflip
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10645, startX, startY, startZ)	
	-- Appel le son du sort
	invoke(10, 1, "playSpellSound")
end

function displayEffectFin()
	Mobile.setMobileAnimation(startMobileId, "AnimBond2")
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10646, destX, destY, destZ)	
	-- Appel de la fin de l'animation
	invoke(0, 1, "executeAction")	
	-- Appel le son du sort
	invoke(10, 1, "playSpellSound")
end

function executeAction()
	--teleport
	ScriptedAction.executeFirstAction(3, 39)
end

function playSpellSound()
	-- Joue un son en stéréo
	Sound.playSound(607, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimBond")

-- Affichage de l'effet
invoke(250, 1, "displayEffect")
invoke(800, 1, "displayEffectFin")

