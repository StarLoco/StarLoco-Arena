ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

Sound.playSound(608,true)
-- Animation du lanceur
invoke(450, 1, "startAnim")
function startAnim()
Mobile.setMobileAnimation(startMobileId, "AnimBond2")

end

-- Affichage de l'effet (dans 850 ms)
invoke(450, 1, "displayEffect");

-- Fonctions

function displayEffect()
	-- Ajout du système de particule sur la destination (id de fichier = 10000)
	particleId = Particle.addParticleSystem(10646, startX, startY, startZ)
end


