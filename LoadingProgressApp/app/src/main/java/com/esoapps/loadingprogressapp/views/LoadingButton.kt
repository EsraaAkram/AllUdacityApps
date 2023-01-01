/*
 * *
 *  * Created by Esraa Akram on 10/2022
 *  * Copyright (c) 2022 . All rights reserved.
 *  * https://github.com/EsraaAkram
 *
 */

package com.esoapps.loadingprogressapp.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.esoapps.loadingprogressapp.R
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //SIZES:
    private var widthSize = 0
    private var heightSize = 0

    private var textWidthSize = 0f


    //RECTANGLES:
    private var buttonRec = RectF()
    private var animationRec = RectF()
    private var textRec = Rect()

    //ANIMATIONS:
    private val animationDuration = 2000L
    private var startProgressValue = 0f


    //TXT WILL CHANGE ACCORDING TO ANIMATION VALUE:
    private var txt = context.getString(R.string.btn_txt_default)


    //ALL PAINTS://INSTEAD OF WRITE THE SAME COUPLE OF LINES IN ALL PAINT I CREATED EXTENSION FUNCTION
    private fun Paint.createPaint(paintColor: Int): Paint {
        this
            .apply {
                color = paintColor
                style = Paint.Style.FILL
            }
        return this
    }

    private val buttonEssentialPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .createPaint(
            ContextCompat.getColor(context, R.color.colorPrimary)
        )


    private val animationPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .createPaint(
            ContextCompat.getColor(context, R.color.colorPrimaryDark)
        )


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .createPaint(ContextCompat.getColor(context, R.color.white))
        .apply {
            textAlign = Paint.Align.LEFT
            textSize = 50f
            typeface = Typeface.DEFAULT_BOLD
        }

    private val smallCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .createPaint(ContextCompat.getColor(context, R.color.colorAccent))


    //ANIMATOR:
    private val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        .apply {

            duration = animationDuration

            //SOME DOWNLOADS TAKE LONG ANONYMOUS TIME SO REVERSE AND KEEP ANIMATION RELOADING
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE

            interpolator = LinearInterpolator()
            addUpdateListener {
                startProgressValue = it.animatedValue as Float
                //REFRESH SCREEN:
                invalidate()
            }

            //TO ENABLE AND DISABLE BUTTON STATE (COLORS AND ANIMATIONS)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    buttonState = ButtonState.Loading
                    this@LoadingButton.isEnabled = false
                }

                override fun onAnimationEnd(animation: Animator) {
                    buttonState = ButtonState.Completed
                    this@LoadingButton.isEnabled = true
                }
            })
        }


    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed)
    { property, oldValue, newValue ->

        when (newValue) {
            ButtonState.Loading -> {
                txt = context.getString(R.string.btn_txt_clicked)
                //REFRESH SCREEN:
                invalidate()

            }
            ButtonState.Clicked -> {
                valueAnimator.start()
                //REFRESH SCREEN:
                invalidate()
            }

            ButtonState.Completed -> {
                //BACK TO DEFAULT:
                valueAnimator.cancel()
                txt = context.getString(R.string.btn_txt_default)


                //REFRESH SCREEN:
                invalidate()
            }
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        if (buttonState == ButtonState.Loading) {

            drawLoading(canvas = canvas)


        } else {

            drawDefault(canvas = canvas)

        }


    }


    private fun drawLoading(canvas: Canvas) {

        //SET BUTTON RC POSITION AND DRAW:
        buttonRec.set(0f, 0f, widthSize.toFloat(), heightSize.toFloat())
        canvas.drawRect(buttonRec, buttonEssentialPaint)


        //ANIMATION
        animationRec.set(
            0f, 0f,
            widthSize * startProgressValue,
            heightSize.toFloat()
        )
        canvas.drawRect(animationRec, animationPaint)


        //DRAW TXT:
        drawTxt(canvas = canvas)

        //LOCATE CIRCLE ACCORDING TO TXT AND DRAW IT:
        textPaint.getTextBounds(txt, 0, txt.length, textRec)
        val txtRadius = textRec.height().toFloat()

        canvas.translate(
            (widthSize + textWidthSize + txtRadius) / 2f,
            heightSize / 2f - txtRadius / 2
        )

        canvas.drawArc(
            0f,
            0f,
            txtRadius,
            txtRadius,
            0f,
            360f * startProgressValue,
            true,
            smallCirclePaint
        )

    }


    private fun drawDefault(canvas: Canvas) {

        buttonRec.set(0f, 0f, widthSize.toFloat(), heightSize.toFloat())
        canvas.drawRect(
            buttonRec,
            buttonEssentialPaint
        )

        //DRAW TXT:
        drawTxt(canvas = canvas)

    }


    private fun drawTxt(canvas: Canvas) {

        textWidthSize = textPaint.measureText(txt)
        canvas.drawText(
            txt,
            (widthSize - textWidthSize) / 2f,
            (heightSize - (textPaint.ascent() + textPaint.descent())) / 2f,
            textPaint
        )
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //HELP MEASURING SCREEN AND IT'S STUFF:
        val minWidth: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val width: Int = resolveSizeAndState(minWidth, widthMeasureSpec, 1)
        val height: Int = resolveSizeAndState(
            MeasureSpec.getSize(width),
            heightMeasureSpec,
            0
        )
        widthSize = width
        heightSize = height
        setMeasuredDimension(width, height)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }
}