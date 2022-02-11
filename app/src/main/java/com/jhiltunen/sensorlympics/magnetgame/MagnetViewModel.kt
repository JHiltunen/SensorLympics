import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorViewModel : ViewModel() {
    private val _value: MutableLiveData<String> = MutableLiveData()
    val value: LiveData<String> = _value

    private val _xX: MutableLiveData<Float> = MutableLiveData()
    val xX: LiveData<Float> = _xX

    private val _yY: MutableLiveData<Float> = MutableLiveData()
    val yY: LiveData<Float> = _yY

    private val _degree: MutableLiveData<Float> = MutableLiveData()
    val degree: LiveData<Float> = _degree

    private val _win: MutableLiveData<Int> = MutableLiveData()
    val win: LiveData<Int> = _win

    private val _chosen: MutableLiveData<Int> = MutableLiveData()
    val chosen: LiveData<Int> = _chosen

    fun updateValue(value: String, xValue: Float, yValue: Float) {
        _value.value = value
        _xX.value = xValue
        _yY.value = yValue
    }

    fun upDateDegree(degree: Float) {
        _degree.value = degree
    }

    fun upDateWin(win: Int) {
        _win.value = win
    }
    fun upDateChosen(chosen: Int) {
        _chosen.value = chosen
    }
}
