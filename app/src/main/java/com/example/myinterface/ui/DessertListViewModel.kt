package com.example.myinterface.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.myinterface.Model.Dessert
import com.example.myinterface.Model.DessertDao
import com.example.myinterface.R
import com.example.myinterface.base.BaseViewModel
import com.example.myinterface.ui.adapter.DessertListAdapter
import com.example.myinterface.networking.IDessertApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DessertListViewModel(private val dessertDao: DessertDao) : BaseViewModel() {

    @Inject
    lateinit var postApi: IDessertApi
    private val postListAdapter: DessertListAdapter = DessertListAdapter()

    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    internal val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    private lateinit var subscription: Disposable

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts() {
        subscription = Observable.fromCallable { dessertDao.all }
            .concatMap { dbPostList ->
                if (dbPostList.isEmpty())
                    postApi.getList(1).concatMap { apiPostList ->
                        dessertDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Dessert>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.abc_capital_on
    }
}