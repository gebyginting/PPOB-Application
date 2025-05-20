
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

fun RecyclerView.autoScroll(
    scope: CoroutineScope,
    interval: Long = 4000,
    getItemCount: () -> Int
): Job {
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(this)

    val layoutManager = layoutManager as? LinearLayoutManager
        ?: throw IllegalStateException("RecyclerView must use LinearLayoutManager")

    var currentPosition = 0

    // Update currentPosition saat user scroll manual
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                val view = snapHelper.findSnapView(layoutManager)
                view?.let {
                    currentPosition = layoutManager.getPosition(it)
                }
            }
        }
    })

//    return scope.launch {
//        while (isActive) {
//            delay(interval)
//
//            val nextPosition = if (currentPosition == getItemCount() - 1) 0 else currentPosition + 1
//
//            val smoothScroller = object : LinearSmoothScroller(context) {
//                override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
//
//                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
//                    return 100f / displayMetrics.densityDpi  // Semakin tinggi nilainya, semakin lambat scroll-nya
//                }
//            }
//
//            smoothScroller.targetPosition = nextPosition
//            layoutManager.startSmoothScroll(smoothScroller)
//
//            currentPosition = nextPosition
//        }
//    }
    return scope.launch {
        while (isActive) {
            delay(interval)
            val nextPosition = if (currentPosition == getItemCount() - 1) 0 else currentPosition + 1
            this@autoScroll.smoothScrollToPosition(nextPosition)
            currentPosition = nextPosition
        }
    }
}