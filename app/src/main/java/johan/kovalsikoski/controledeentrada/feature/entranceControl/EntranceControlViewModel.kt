package johan.kovalsikoski.controledeentrada.feature.entranceControl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import johan.kovalsikoski.controledeentrada.data.local.entity.GuestEntity
import johan.kovalsikoski.controledeentrada.domain.repository.GuestsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class EntranceControlViewModel @Inject constructor(
    private val repository: GuestsRepository
): ViewModel() {

    private val _guestList = MutableStateFlow<List<GuestEntity>>(listOf())
    val guestList: StateFlow<List<GuestEntity>> = _guestList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _guestList.emitAll(repository.getGuestList())
        }
    }

    fun insertGuest(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGuest(GuestEntity(name = name))
        }
    }

    fun removeGuest(guestEntity: GuestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeGuest(guestEntity)
        }
    }

}