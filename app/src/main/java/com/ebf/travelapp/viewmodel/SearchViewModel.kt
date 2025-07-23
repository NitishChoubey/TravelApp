package com.ebf.travelapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebf.travelapp.data.Place
import com.ebf.travelapp.data.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredPlaces = MutableStateFlow<List<Place>>(emptyList())
    val filteredPlaces: StateFlow<List<Place>> = _filteredPlaces

    init {
        loadPlaces()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterPlaces(query)
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            _filteredPlaces.value = placeRepository.getPlaces("")
        }
    }

    private fun filterPlaces(query: String) {
        viewModelScope.launch {
            _filteredPlaces.value = placeRepository.getPlaces(query)
        }
    }
}