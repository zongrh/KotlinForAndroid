package cn.my.jetpack.coroutines

import kotlinx.coroutines.*

/**
 *
 * FileName: CoroutinesTest
 * Author: nanzong
 * Date: 2023/3/7 22:33
 * Description:
 * History:
 *
 */
class CoroutinesTest {
    fun launchTest(){
        println("start")
        GlobalScope.launch {
            println("GlobalScope.launch")
        }
        println("end")
    }

    fun launchTest2() {
        print("start")
        //创建一个全局作用域协程，不会阻塞当前线程，生命周期与应用程序一致
      val job=  CoroutineScope(Dispatchers.IO).launch {
            //在这1000毫秒内该协程所处的线程不会阻塞
            //协程将线程的执行权交出去，该线程继续干它要干的事情，到时间后会恢复至此继续向下执行
            delay(1000)//1秒无阻塞延迟（默认单位为毫秒）
            print("GlobalScope.launch")
        }
        print("end")//主线程继续，而协程被延迟
    }

}

fun main() {
    val coroutinesTest = CoroutinesTest()
    coroutinesTest.launchTest()

}