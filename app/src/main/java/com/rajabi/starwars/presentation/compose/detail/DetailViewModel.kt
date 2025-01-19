package com.rajabi.starwars.presentation.compose.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajabi.starwars.domain.usecase.CharacterDetailUseCase
import com.rajabi.starwars.domain.usecase.GetResourceByUrlUseCase
import com.rajabi.starwars.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: CharacterDetailUseCase,

    private val getResourceByUrlUseCase: GetResourceByUrlUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Idle)
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _events = MutableSharedFlow<DetailEvent>()
    val events: SharedFlow<DetailEvent> = _events


    fun processAction(action: DetailAction) {
        when (action) {
            is DetailAction.LoadCharacterDetails -> fetchCharacterDetails(action.id)
        }
    }

    private fun fetchCharacterDetails(url: String) {
        viewModelScope.launch {

            getResourceByUrlUseCase.execute(url)
                .onStart { _uiState.value = DetailUiState.Loading } // Emit loading state
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> _uiState.value = DetailUiState.Loading
                        is Resource.Success -> {

                            _uiState.value =
                                DetailUiState.Success(resource.data)
                        }

                        is Resource.Failure -> {
                            _uiState.value = DetailUiState.Error(resource.message)
                            _events.emit(DetailEvent.ShowError(resource.message))
                        }
                    }
                }
        }
    }
}
