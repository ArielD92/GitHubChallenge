package com.adsoft.githubchallenge.utils

interface RetrieveDataFromRequest<T> {
    fun onRetrieveLoadDataStart()
    fun onRetrieveLoadDataFinish()
    fun onRetrieveLoadDataError()
    fun onRetrieveLoadDataSuccess(results:T)
}