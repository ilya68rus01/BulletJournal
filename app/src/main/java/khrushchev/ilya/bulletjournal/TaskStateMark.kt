package khrushchev.ilya.bulletjournal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TaskStateMark : View {

    var taskState: TaskState = TaskState.UNKNOWN
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int, taskState: TaskState): super(
        context,
        attrs
    ) {
        init(attrs, defStyle)
        this.taskState = taskState
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val attributes = context.obtainStyledAttributes(
            attrs, R.styleable.TaskStateMark, defStyle, 0
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        drawCurrentState(canvas, contentWidth, contentHeight)
    }

    private fun drawCurrentState(
        canvas: Canvas,
        contentWidth: Int,
        contentHeight: Int,
    ) {
        when(taskState) {
            TaskState.COMPLETED -> drawXMark(canvas, contentWidth, contentHeight)
            TaskState.CREATED -> drawDotMark(canvas, contentWidth, contentHeight)
            TaskState.IN_WORK -> drawPartXMark(canvas, contentWidth, contentHeight)
            else -> {}
        }
    }

    private fun drawDotMark(
        canvas: Canvas,
        contentWidth: Int,
        contentHeight: Int,
    ) {
        val paint = Paint()

        canvas.drawCircle(
            (contentWidth / 2).toFloat(),
            (contentHeight / 2).toFloat(),
            8.0f,
            paint
        )
    }

    private fun drawPartXMark(
        canvas: Canvas,
        contentWidth: Int,
        contentHeight: Int,
    ) {
        val paint = Paint()
        paint.strokeWidth = 10f

        canvas.drawLine(
            0f,
            0f,
            contentWidth / 2f + 3,
            contentHeight / 2f + 3,
            paint
        )

        canvas.drawLine(
            contentWidth.toFloat(),
            0f,
            contentWidth / 2f - 3,
            contentHeight / 2f + 3,
            paint
        )
    }

    private fun drawXMark(
        canvas: Canvas,
        contentWidth: Int,
        contentHeight: Int,
    ) {
        val paint = Paint()
        paint.strokeWidth = 10f

        canvas.drawLine(
            0f,
            0f,
            contentWidth.toFloat(),
            contentHeight.toFloat(),
            paint
        )

        canvas.drawLine(
            contentWidth.toFloat(),
            0f,
            0f,
            contentHeight.toFloat(),
            paint
        )
    }

}