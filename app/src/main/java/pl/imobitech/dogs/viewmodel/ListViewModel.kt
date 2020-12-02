package pl.imobitech.dogs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pl.imobitech.dogs.model.DogBreed
import pl.imobitech.dogs.model.DogsApiService

class ListViewModel : ViewModel() {


    private val TAG = "ErrorOnDownload"
    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
       // fetchFromRemote()
        fetchFromRemote()
       // val dog1 = DogBreed("1","222",",2222","2222","2222","2222","332131")
       // val dogsList = arrayListOf(dog1,dog1)

       // dogs.value = dogsList
        //dogs.value = dogs.value.orEmpty() + dogsList

       // dogsLoadError.value = false
       // loading.value = false

    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>() {

                    override fun onSuccess(dogList1: List<DogBreed>) {
                        dogs.value = dogList1
                        dogsLoadError.value = false
                        loading.value = false
                        Log.d(TAG,"${dogs.value}")
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                        Log.d(TAG, "onError: ${e.printStackTrace().toString()}" )
                    }

                })


        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}