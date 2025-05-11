package vn.mobile.wallet.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EScrollView : NestedScrollView {
    private var job: Job? = null
    private var initialPosition: Int = 0
    private var listener:ScrollViewListener?=null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        listener?.onScrollChanged(this,l,t,oldl,oldt)
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            if (initialPosition == scrollY) {
                listener?.onScrollStopped()
            }
            initialPosition = scrollY
        }

    }

    fun scrollTop(){
        post { scrollTo(0,top) }
    }

    fun scrollToPosition(view: View) {
        post { scrollTo(view.x.toInt(), view.y.toInt()) }
    }

    fun scrollBottom(){
        post { fullScroll(FOCUS_DOWN) }
    }

    interface ScrollViewListener{
        fun onScrollChanged(eScrollView: EScrollView,x:Int,y:Int,oldX:Int,oldY:Int)
        fun onScrollStopped()
    }

}