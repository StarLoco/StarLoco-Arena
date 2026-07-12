-- [A]--> le son n'est pas synchro
-- Fin du sort Tanière des roches (ID: 145)
-- Classe: Enutrof
--


function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10335, startX, startY, startZ)	
end

function Animation ()
	-- Animation du lanceur
	Mobile.setMobileAnimation(startMobileId, "AnimEnuTanniereFin")
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
invoke(2000,1,"Animation")

-- Affichage de l'effet (dans 850 ms)
Sound.playSound(300, true)
invoke(1, 1, "displayEffect");
