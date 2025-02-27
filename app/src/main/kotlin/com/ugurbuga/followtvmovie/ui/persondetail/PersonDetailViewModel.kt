package com.ugurbuga.followtvmovie.ui.persondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ugurbuga.followtvmovie.base.FTMBaseViewModel
import com.ugurbuga.followtvmovie.common.Argument
import com.ugurbuga.followtvmovie.common.Util
import com.ugurbuga.followtvmovie.domain.person.usecase.GetPersonCastsUseCase
import com.ugurbuga.followtvmovie.domain.person.usecase.GetPersonDetailUseCase
import com.ugurbuga.followtvmovie.domain.person.usecase.GetPersonImagesUseCase
import com.ugurbuga.followtvmovie.extensions.doOnStatusChanged
import com.ugurbuga.followtvmovie.extensions.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPersonDetailUseCase: GetPersonDetailUseCase,
    private val getPersonImagesUseCase: GetPersonImagesUseCase,
    private val getPersonCastsUseCase: GetPersonCastsUseCase
) : FTMBaseViewModel() {

    private var personId: String = savedStateHandle[Argument.PERSON_ID] ?: Util.EMPTY_STRING

    private val _personDetailViewState = MutableStateFlow(PersonDetailViewState())
    val personDetailViewState: StateFlow<PersonDetailViewState> get() = _personDetailViewState

    private var _personDetailViewEvent = MutableSharedFlow<PersonDetailViewEvent>()
    val personDetailViewEvent: SharedFlow<PersonDetailViewEvent> get() = _personDetailViewEvent

    init {
        getPersonDetail()
        getPersonImages()
        getPersonCasts()
    }

    private fun getPersonDetail() {
        getPersonDetailUseCase(GetPersonDetailUseCase.PersonDetailParam(personId))
            .doOnStatusChanged {
                initStatusState(
                    it, isShowLoading = false
                )
            }.doOnSuccess {
                _personDetailViewState.value = personDetailViewState.value.copy(personDetail = it)
            }.launchIn(viewModelScope)
    }

    private fun getPersonImages() {
        getPersonImagesUseCase(GetPersonImagesUseCase.PersonImagesParam(personId))
            .doOnStatusChanged {
                initStatusState(
                    it, isShowLoading = false
                )
            }.doOnSuccess {
                _personDetailViewState.value = personDetailViewState.value.copy(images = it)
            }.launchIn(viewModelScope)
    }


    private fun getPersonCasts() {
        getPersonCastsUseCase(GetPersonCastsUseCase.PersonCastParams(personId))
            .doOnStatusChanged {
                initStatusState(
                    it, isShowLoading = false
                )
            }.doOnSuccess {
                _personDetailViewState.value = personDetailViewState.value.copy(casts = it)
            }.launchIn(viewModelScope)
    }

    fun imageClicked(position: Int) {
        _personDetailViewEvent.emitSuspending(
            PersonDetailViewEvent.NavigateToImages(
                imageList = personDetailViewState.value.images, position = position
            )
        )
    }

}