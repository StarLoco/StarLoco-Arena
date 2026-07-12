-- [R]
-- Sort : Vitalité (ID : 7)
-- Classe : Iop
--
function removeParticle()
	Particle.removeParticleSystem(particleId)
end

function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10805, startX, startY, startZ)
	-- Effet HP Boost
	ScriptedAction.executeFirstAction(3, 11)

end

function playSpellSound()
	-- Joue un son en stéréo
	Sound.playSound(808, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimVitalite")

-- Affichage de l'effet
invoke(800, 1, "displayEffect");
playSpellSound();
