package aarhus.mobileApp.FoodieFinder.ui.components.events.participants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import androidx.compose.runtime.MutableState

fun setUserStatus(eventFound: EventFB, userEntered: UserFB,
                  isOwner: MutableState<Boolean>,
                  thisUserAlreadyVoted: MutableState<Boolean>,
                  thisUserAlreadyPosted: MutableState<Boolean>) {
    if (eventFound.ownerId == userEntered.id)
        isOwner.value = true
    if (userEntered.id in eventFound.participants_already_voted)
        thisUserAlreadyVoted.value = true
    if (userEntered.id in eventFound.participants_already_posted)
        thisUserAlreadyPosted.value = true
}