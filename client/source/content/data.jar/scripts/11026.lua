-- [A]
-- Mort des poupées
-- 
--


function displayEffect()
	particleId = Particle.addParticleSystem(11026, startX, startY, startZ)	
	invoke(1700, 1, "removeEffect")
end

function removeEffect()
	Particle.removeParticleSystem(particleId)
end

function playSpellSound()
	Sound.playSound(1007, true)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du lanceur
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimMort")

-- Affichage de l'effet sur lanceur
invoke(300, 1, "displayEffect")
invoke(600, 1, "playSpellSound")
