-- [R]
-- Sort : Epée du Destin (ID : 5)
-- Classe : Iop
--
function removeParticle()
	Particle.removeParticleSystem(particleId)
end

function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10810, destX, destY, destZ)
	-- Joue un son en stéréo 
	Sound.playSound(800, true)
	-- Appel de la fin de l'animation
	invoke(600, 1, "executeAction")
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 2)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimVitalite")
-- Affichage de l'effet
invoke(600, 1, "displayEffect");