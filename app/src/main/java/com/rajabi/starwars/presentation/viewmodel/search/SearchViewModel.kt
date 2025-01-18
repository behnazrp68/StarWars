package com.rajabi.starwars.presentation.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajabi.starwars.domain.usecase.SearchCharacterUseCase
import com.rajabi.starwars.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharacterUseCase: SearchCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<SearchUiEvent>()
    val uiEvent: SharedFlow<SearchUiEvent> = _uiEvent

    fun processAction(action: SearchUiAction) {
        when (action) {
            is SearchUiAction.Search -> searchCharacters(action.query)
        }
    }

    private fun searchCharacters(query: String) {
        viewModelScope.launch {
            searchCharacterUseCase.execute(query)
                .onStart { _uiState.value = SearchUiState.Loading } // Emit loading state
                .catch { e ->
                    _uiState.value = SearchUiState.Error("Error: ${e.message}")
                    _uiEvent.emit(SearchUiEvent.ShowToast("An error occurred: ${e.message}"))
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> _uiState.value = SearchUiState.Loading
                        is Resource.Success -> _uiState.value = SearchUiState.Success(resource.data)
                        is Resource.Failure -> {
                            _uiState.value = SearchUiState.Error(resource.message)
                            _uiEvent.emit(SearchUiEvent.ShowToast(resource.message))
                        }
                    }
                }
        }
    }
}