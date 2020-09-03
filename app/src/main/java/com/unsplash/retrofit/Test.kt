package com.unsplash.retrofit

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main()= runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(5000)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(3000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }

}

suspend fun work1(): Int {
    delay(1000)
    return 18

}
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { work1() }
    val two = async { work2() }
    one.await() + two.await()
}

suspend fun work2(): Int {
    delay(1000)
    return 18

}