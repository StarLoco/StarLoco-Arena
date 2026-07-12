-- [R]
-- Sort : Guide de Bravoure (ID : 8)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10801, startX, startY, startZ)
end

function displayEffectCible()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10802, destX, destY, destZ)	
	-- Boosts de dégats
	ScriptedAction.executeFirstAction(3, 40)
	ScriptedAction.executeFirstAction(3, 42)
	ScriptedAction.executeFirstAction(3, 44)
	ScriptedAction.executeFirstAction(3, 46)
end

function playSpellSound()
	-- Joue un son en stéréo
	Sound.playSound(811, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimVitalite")

-- Affichage de l'effet
invoke(850, 1, "displayEffect");
invoke(950, 1, "displayEffectCible");
invoke(250, 1, "playSpellSound");
