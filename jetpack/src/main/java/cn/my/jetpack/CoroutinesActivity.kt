package cn.my.jetpack

import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import cn.my.jetpack.databinding.ActivityCoroutinesBinding
import cn.my.mylibrary.base.BaseBindingActivity
import cn.my.mylibrary.utils.LogUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.system.measureTimeMillis

/**
 * Kotlin协程的取消
 */
class CoroutinesActivity :
    BaseBindingActivity<ActivityCoroutinesBinding>(R.layout.activity_coroutines) {

    private lateinit var mJob: Job

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CoroutinesActivity::class.java)
        }

    }

    override fun setupViews() {
        mBinding.apply {
            btnStart.setOnClickListener {
                mJob = lifecycleScope.launch(Dispatchers.Main) {
                    LogUtil.d("AAAAAAAA")
                    repeat(100) {
                        LogUtil.d("第${it + 1}次运行协程")
                        delay(1000)
                    }
                }
                LogUtil.d("BBBBBBBBBB")
            }
            btnCancel.setOnClickListener {
                mJob.cancel()
            }
        }
    }

    /**
     * 使用官方库的 MainScope()获取一个协程作用域用于创建协程
     */
    private val mScope = MainScope()

    override fun initData() {
        testJob1()
        testJob2()
        // 模拟网络请求 数据的流程
        testGetUserInfo()
        // CoroutineScope.async()
        // async主要用于获取返回值和并发，直接上代码：
        asyncTest()
        // 上面的代码主要展示async的返回值功能，需要与await()挂起函数结合使用
        // 下面展示async的并发能力：
        asyncTest2()
        // 这种情况有的时候并不是我们想要的，
        // 我们更希望一个协程在产生异常时，不影响其他协程的执行
        exceptionCoroutineTest()

        // 几个协程一块执行，某一个崩溃了，或者取消了，不影响其它协程的执行，崩溃需要捕获处理
        //  CoroutineScope(Job() + Dispatchers.Default)
        coroutineScopeTest()

        // 这里模拟一个无限循环的协程，当协程是活跃状态时每秒钟打印两次消息，1.2秒后取消协程：
        jobTest()

        // MainScope作用域的好处就是方便地绑定到UI组件的声明周期上，
        // 在Activity销毁的时候mainScope.cancel()取消其作用域。
        scopeTest()

        // 因为Activity 实现了LifecycleOwner这个接口，
        // 而lifecycleScope则正是它的拓展成员，可以在Activity中直接使用lifecycleScope协程实例：
        lifecycleScopeTest()

        // 调度器切换
        dispatchersTest()
//        test2()
    }
//    private suspend fun test() {
//        val flow = flow {
//            // 网络连接
//            connectToNet()
//            try {
//                while (true) {
//                    // 获取数据并发射
//                    emit(getDataFromNet())
//                }
//            } finally {
//                // 断开连接
//                disconnectFromNet()
//            }
//        }
//
//        // 不使用shareIn方法
//        // 10次网络连接
//        for (i in 0..10)
//            launch {
//                flow.collect {
//                    Log.d("liduo", "test: $it")
//                }
//            }
//
//        // 使用shareIn方法
//        val sharedFlow = flow.shareIn(GlobalScope, SharingStarted.Eagerly, 1)
//
//        // 1次网络连接
//        for (i in 0..10)
//            launch {
//                sharedFlow.collect {
//                    Log.d("liduo", "test1: $it")
//                }
//            }
//    }
//
//    suspend fun testBuffer3() {
//        var flow = flow {
//            (1..3).forEach {
//                delay(1000)
//                println("emit $it")
//                emit(it)
//            }
//        }.flowOn(Dispatchers.IO)
//
//        var time = measureTimeMillis {
//            flow.collect {
//                delay(2000)
//                println("collect:$it")
//            }
//        }
//        println("use time:${time} ms")
//    }
//
//    fun test2() {
//        runBlocking {
//            //构造热流
//            val flow = MutableSharedFlow<String>()
//
//            //开启协程
//            GlobalScope.launch {
//                //接收数据(消费者)
//                flow.collect {
//                    println("collect: $it")
//                }
//            }
//
//            //发送数据(生产者)
//            delay(200)//保证消费者已经注册上
//            flow.emit("hello world")
//        }
//    }

    fun dispatchersTest() {
        //创建一个在主线程执行的协程作用域
        val mainScope = MainScope()
        mainScope.launch {
            launch(Dispatchers.Main) {//在协程上下参数中指定调度器
                LogUtil.e("dispatchersTest", "主线程调度器")
            }
            launch(Dispatchers.Default) {
                LogUtil.e("dispatchersTest", "默认调度器")
            }
            launch(Dispatchers.Unconfined) {
                LogUtil.e("dispatchersTest", "任意调度器")
            }
            launch(Dispatchers.IO) {
                LogUtil.e("dispatchersTest", "IO调度器")
            }
        }
    }

    private fun lifecycleScopeTest() {

        lifecycleScope.launch(Dispatchers.Main) {
            whenCreated {
                LogUtil.e("lifecycleScopeTest", "lifecycleScop:   whenCreated ")

            }
            whenStarted {
                LogUtil.e("lifecycleScopeTest", "lifecycleScop:   whenStarted ")

            }

            whenResumed {
                LogUtil.e("lifecycleScopeTest", "lifecycleScop:   whenResumed ")

            }

            withStarted {
                LogUtil.e("lifecycleScopeTest", "lifecycleScop:   withStarted ")
            }

            withResumed {
                LogUtil.e("lifecycleScopeTest", "lifecycleScop:   withResumed ")

            }
            withCreated {
                LogUtil.e("lifecycleScopeTest", "lifecycleScop:   withCreated ")

            }

        }
    }

    fun scopeTest() {
        //创建一个根协程
        GlobalScope.launch {//父协程
            launch {//子协程
                print("GlobalScope的子协程")
            }
            launch {//第二个子协程
                print("GlobalScope的第二个子协程")
            }
        }

        //为UI组件创建主作用域
        val mainScope = MainScope()
        mainScope.launch {//启动协程
            //todo
        }
    }

    fun jobTest() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0

            while (isActive) {//当job是活跃状态继续执行
                if (System.currentTimeMillis() >= nextPrintTime) {//每秒钟打印两次消息
                    LogUtil.e("jobTest", "job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }

        delay(1200)//延迟1.2s
        LogUtil.e("jobTest", "等待1.2秒后")


        //job.join()
        //job.cancel()
        job.cancelAndJoin()//取消任务并等待任务完成
        LogUtil.e("jobTest", "协程被取消并等待完成")
    }

    private val TAG = "CoroutineScopeTest"

    private fun coroutineScopeTest() {

        val scope = CoroutineScope(Job() + Dispatchers.Default)

        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            LogUtil.e(TAG, "CoroutineExceptionHandler: $throwable")
        }) {
            supervisorScope {
                launch {
                    delay(500)
                    LogUtil.e(TAG, "Child 1 ")
                }
                launch {
                    delay(1000)
                    LogUtil.e(TAG, "Child 2 ")
                    throw RuntimeException("--> RuntimeException <--")
                }
                launch {
                    delay(1500)
                    LogUtil.e(TAG, "Child 3 ")
                }
            }
        }
    }

    private fun exceptionCoroutineTest() {
        mScope.launch(Dispatchers.Default) {
            delay(500)
            LogUtil.e("Child 1")
        }

        // 在Child 2的上下文添加了异常处理
        mScope.launch(Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable ->
            LogUtil.e("CoroutineExceptionHandler: $throwable")
        }) {
            delay(1000)
            LogUtil.e("Child 2")
            throw RuntimeException("--> RuntimeException <--")
        }

        mScope.launch(Dispatchers.Default) {
            delay(1500)
            LogUtil.e("Child 3")
        }

    }

    /**
     * 这就是最基本的协程使用，关于作用域，更推荐的是在UI组件中使用LifecycleOwner.lifecycleScope，
     * 在ViewModel中使用ViewModel.viewModelScope
     */
    fun asyncTest2() {
        mScope.launch {
            // 此处有一个需求  同时请求5个接口  并且将返回值拼接起来

            val job1 = async {
                // 请求1
                delay(5000)
                "1"
            }
            val job2 = async {
                // 请求2
                delay(5000)
                "2"
            }
            val job3 = async {
                // 请求3
                delay(5000)
                "3"
            }
            val job4 = async {
                // 请求4
                delay(5000)
                "4"
            }
            val job5 = async {
                // 请求5
                delay(5000)
                "5"
            }

            // 代码执行到此处时  5个请求已经同时在执行了
            // 等待各job执行完 将结果合并
            LogUtil.d(
                "asyncTest2: ${job1.await()} ${job2.await()} ${job3.await()} ${job4.await()} ${job5.await()}"
            )

            // 因为我们设置的模拟时间都是5000毫秒  所以当job1执行完时  其他job也均执行完成
        }
    }

    private fun asyncTest() {
        mScope.launch {
            // 开启一个模拟IO模式的线程并返回一个Deferred，Deferred可以用来获取返回值
            // 代码执行到此处会开启一个新的协程，然后去执行 协程体，父协程里的代码会接着往下走
            val deferred = async(Dispatchers.IO) {
                // 模拟耗时操作
                delay(2000)
                "return: android "
            }

            // 等待async执行完成获取返回值，此处并不会阻塞线程，而是挂起，将线程的执行权交出去
            // 等到async的协程体执行完之后，会恢复协程继续往下执行
            val data = deferred.await()
            mBinding.tvShow.text = data

        }

    }

    private fun testGetUserInfo() {

        //下边看一个例子:从网路中获取数据，并用于更新UI
        // 该例子不会阻塞主线程
        mScope.launch(Dispatchers.IO) {
            // 执行getUserInfo 方法时会将该线程切换至IO去执行
            val userInfo = getUserInfo()
            // 获取完数据后，切换至Main线程更新UI
            withContext(Dispatchers.Main) {
                mBinding.tvShow.text = userInfo
            }
        }
    }

    private fun testJob2() {
        // 创建一个指定了调用模式的协程，该协程的运行线程为IO线程
        val job2 = mScope.launch(Dispatchers.IO) {
            // 此处是 IO 线程模式

            // 切线程 将写成所在的线程环境切换至指定的模式为Main
            withContext(Dispatchers.Main) {
                // 现在这里就是Main线程了 ， 可以在此进行UI操作了
            }
        }
    }

    private fun testJob1() {
        // 创建一个默认参数的协程，其默认的调度模式为Main 也就是说该协程的线程环境是Main线程
        val job1 = mScope.launch {
            // 这里就是协程体

            // 延迟1000毫秒  delay是一个挂起函数
            // 在这1000毫秒内该协程所处的线程不会阻塞
            // 协程将线程的执行权交出去，该线程该干嘛干嘛，到时间后会恢复至此继续向下执行
            LogUtil.e("mScope start")
            delay(1000)
            LogUtil.e("mScope end")
        }
    }

    /**
     * 获取用户信息， 该函数模拟IO操作获取数据
     */
    private suspend fun getUserInfo(): String {
        return withContext(Dispatchers.IO) {
            delay(2000)
            "kOTlin developer"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消协程，防止协程泄漏，如果使用lifecycleScope则不需要手动取消
        mScope.cancel()
    }


}