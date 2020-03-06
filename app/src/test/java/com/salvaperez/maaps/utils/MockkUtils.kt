package com.salvaperez.maaps.utils

import com.salvaperez.maaps.presentation.util.Resource
import io.mockk.MockKVerificationScope

fun <T>MockKVerificationScope.checkResourceLoading(): Resource<T> {
	return match {
		it.status == Resource.Status.LOADING
	}
}

fun <T>MockKVerificationScope.checkResourceError(): Resource<T> {
	return match {
		it.status == Resource.Status.ERROR
	}
}

fun <T,Z>MockKVerificationScope.checkResourceSuccess(data: Z): Resource<T> {
	return match {
		it.status == Resource.Status.SUCCESS &&
		it.data == data
	}
}

fun <T>MockKVerificationScope.checkAnyResourceSuccess(): Resource<T> {
	return match {
		it.status == Resource.Status.SUCCESS
	}
}

fun <T>MockKVerificationScope.checkAnyResource(): Resource<T> {
	return any()
}
