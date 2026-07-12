-- [R]
-- Sort : Bond (ID : 6)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10820, startX, startY, startZ);
	invoke(80, 1, "bondStart");
	invoke(700, 1, "bondEnd");	
end

function bondStart()
	particleId = Particle.addParticleSystem(10821, destX, destY, destZ);
end

function bondEnd()
	-- Effet de teleportation
	ScriptedAction.executeFirstAction(3, 39);
	Mobile.setMobileAnimation(startMobileId, "AnimBond2");
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition();
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimBond");
-- Affichage de l'effet
invoke(530, 1, "displayEffect");
-- Joue un son en stéréo
Sound.playSound(804, true);

