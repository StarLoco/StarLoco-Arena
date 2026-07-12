-- epées

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

destX, destY, destZ = Cast.getPosition()
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

Mobile.setMobileAnimation(startMobileId, "AnimEquipement-01-01-00")

invoke(1000, 1, "waitBeforeNextAction")

function waitBeforeNextAction()
end

