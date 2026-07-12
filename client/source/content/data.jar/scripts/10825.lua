-- [R]
-- Sort : Mutilation (ID : 9)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10825, startX, startY, startZ)	
	-- Appel le son d'explosion
	invoke(0, 1, "playSpellSound");
	invoke(200, 1, "playSpellSound");
end

function hit()
	ScriptedAction.executeFirstAction(3, 1)
	ScriptedAction.executeFirstAction(3, 82)
end

function playSpellSound()
	-- Joue un son en stéréo
	Sound.playSound(810, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimMutilation");
-- Affichage de l'effet
invoke(0, 1, "displayEffect");
invoke(1200, 1, "hit");