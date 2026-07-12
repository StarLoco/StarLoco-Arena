-- [R]
-- Sort : Perception (ID : 158)
-- Classe : Ecaflip
--
function displayEffect()
	-- Ajout du système de particule sur la destination (id de fichier = 10610)
	particleId = Particle.addParticleSystem(10610, destX, destY, destZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 84)	
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
destX, destY, destZ = Cast.getPosition();
Mobile.setMobileLookAt(startMobileId, destX, destY, false);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast");
-- Affichage de l'effet
invoke(750, 1, "displayEffect");
invoke(850, 1, "applyEffect");
-- Joue le son
Sound.playSound(610, true)