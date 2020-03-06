package com.salvaperez.maaps.utils

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.kotlintest.IsolationMode
import io.kotlintest.Spec
import io.kotlintest.specs.BehaviorSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

open class BaseTest: BehaviorSpec() {

	override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

	override fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		Dispatchers.setMain(Dispatchers.Unconfined)
		ArchTaskExecutor.getInstance().setDelegate(InstantTaskExecutor)
	}

	override fun afterSpec(spec: Spec) {
		super.afterSpec(spec)
		ArchTaskExecutor.getInstance().setDelegate(null)
		Dispatchers.resetMain()
	}

}

object InstantTaskExecutor : TaskExecutor() {
	override fun executeOnDiskIO(runnable: Runnable) {
		runnable.run()
	}

	override fun isMainThread(): Boolean = true

	override fun postToMainThread(runnable: Runnable) {
		runnable.run()
	}
}
