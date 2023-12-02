@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.android.app.justbarit.presentation.common.customviews.bottomnavigation

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.LayoutBottombarViewBinding
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Dp
import com.android.app.justbarit.presentation.common.ext.accentColor
import com.android.app.justbarit.presentation.common.ext.dp2Px
import com.android.app.justbarit.presentation.common.ext.resourceDrawable
import com.android.app.justbarit.presentation.common.ext.translateX
import com.android.app.justbarit.presentation.common.ext.visible

/**
 * AndroidBottomBarView is a lightweight bottom navigation view,
 * fully customizable with an indicator and animations.
 */
class AndroidBottomBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutBottombarViewBinding =
        LayoutBottombarViewBinding.inflate(LayoutInflater.from(context), this, true)
    val indicator: View
        get() = binding.indicator

    private var bottomBarFlavor: BottomBarFlavor = BottomBarFlavor.ICON
    private var bottomMenuItems: MutableList<BottomMenuItem> = mutableListOf()
    var bottomMenuItemViews: MutableList<BottomMenuItemView> = mutableListOf()

    var selectedIndex: Int = 0
        private set
    var selectedItemView: BottomMenuItemView? = null
        private set

    private var layoutWidth: Int = 0
    private var layoutHeight: Int = 0
    private var itemWidth: Int = 0

    private var menuAnimation: BottomMenuAnimation = BottomMenuAnimation.NORMAL
    private var animationDuration: Long = 300L

    @JvmField
    var onMenuItemSelectedListener: OnMenuItemSelectedListener? = null

    @JvmField
    var onBottomMenuInitializedListener: OnBottomMenuInitializedListener? = null

    private val onMenuItemClickListener: (BottomMenuItemViewConfig?, BottomMenuItemView) -> Unit = { config, view ->
        config?.let {
            if (it.index != selectedIndex) {
                selectedIndex = it.index
                animateBottomBarItem(view)
                animateIndicator(config)
                onMenuItemSelectedListener?.onMenuItemSelected(it.index, it.bottomMenuItem, true)
            }
        }
        bottomMenuItemViews.forEach {
            it.setIsActive(it == view)
        }
    }

    private var _visibleIndicator: Boolean = true

    private var _indicatorDrawable: Drawable? = null

    @ColorInt
    private var _indicatorColor: Int = context.accentColor()

    @Px
    private var _indicatorRadius: Float = dp2Px(3f)

    @Px
    private var _indicatorHeight: Int = dp2Px(4)

    @Px
    private var _indicatorPadding: Int = dp2Px(2)

    var visibleIndicator: Boolean
        get() = _visibleIndicator
        set(value) {
            _visibleIndicator = value
            initializeIndicator()
        }

    var indicatorColor: Int
        @ColorInt get() = _indicatorColor
        set(@ColorInt value) {
            _indicatorColor = value
            initializeIndicator()
        }

    var indicatorDrawable: Drawable?
        get() = _indicatorDrawable
        set(value) {
            _indicatorDrawable = value
            initializeIndicator()
        }

    var indicatorRadius: Float
        @Px get() = _indicatorRadius
        set(@Dp value) {
            _indicatorRadius = dp2Px(value)
            initializeIndicator()
        }

    var indicatorHeight: Int
        @Px get() = _indicatorHeight
        set(@Dp value) {
            _indicatorHeight = dp2Px(value)
            initializeIndicator()
        }

    var indicatorPadding
        @Px get() = _indicatorPadding
        set(@Dp value) {
            _indicatorPadding = dp2Px(value)
            initializeIndicator()
        }

    private var previousPosition: Float = 0f

    init {
        obtainStyledAttributes(attrs, defStyleAttr)
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AndroidBottomBarView,
            defStyleAttr,
            0
        )
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTypeArray(typedArray: TypedArray) {
        this.bottomBarFlavor =
            when (typedArray.getInteger(R.styleable.AndroidBottomBarView_bottomBar_flavor, 0)) {
                BottomBarFlavor.ICON.value -> BottomBarFlavor.ICON
                else -> BottomBarFlavor.ICON
            }

        this.menuAnimation =
            when (typedArray.getInteger(
                R.styleable.AndroidBottomBarView_bottomBar_menuAnimation,
                0
            )) {
                BottomMenuAnimation.NORMAL.value -> BottomMenuAnimation.NORMAL
                BottomMenuAnimation.ACCELERATE.value -> BottomMenuAnimation.ACCELERATE
                BottomMenuAnimation.BOUNCE.value -> BottomMenuAnimation.BOUNCE
                else -> BottomMenuAnimation.OVERSHOOT
            }

        this.selectedIndex = typedArray.getInteger(
            R.styleable.AndroidBottomBarView_bottomBar_selectedIndex,
            this.selectedIndex
        )

        this.animationDuration = typedArray.getInteger(
            R.styleable.AndroidBottomBarView_bottomBar_duration,
            this.animationDuration.toInt()
        ).toLong()

        this._visibleIndicator = typedArray.getBoolean(
            R.styleable.AndroidBottomBarView_bottomBar_indicator_visible,
            this._visibleIndicator
        )

        this._indicatorColor = typedArray.getColor(
            R.styleable.AndroidBottomBarView_bottomBar_indicator_color,
            this._indicatorColor
        )

        typedArray.getResourceId(
            R.styleable.AndroidBottomBarView_bottomBar_indicator_drawable,
            -1
        ).also {
            if (it != -1) {
                this._indicatorDrawable = context.resourceDrawable(it)
            }
        }

        this._indicatorRadius = typedArray.getDimensionPixelSize(
            R.styleable.AndroidBottomBarView_bottomBar_indicator_radius,
            this._indicatorRadius.toInt()
        ).toFloat()

        this._indicatorHeight = typedArray.getDimensionPixelSize(
            R.styleable.AndroidBottomBarView_bottomBar_indicator_height,
            this._indicatorHeight
        )

        this._indicatorPadding = typedArray.getDimensionPixelSize(
            R.styleable.AndroidBottomBarView_bottomBar_indicator_padding,
            this._indicatorPadding
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.layoutWidth = w
        this.layoutHeight = h
        this.itemWidth = w / bottomMenuItems.size

        post {
            initializeBottomBarItems()
            initializeIndicator()
        }
    }

    private fun initializeBottomBarItems() {
        this.binding.menuContainer.removeAllViews()
        for (index in 0 until bottomMenuItems.size) {
            this.binding.menuContainer.addView(
                BottomMenuItemView(context).apply {
                    layoutParams = LayoutParams(itemWidth, layoutHeight)
                    onMenuItemClickListener = this@AndroidBottomBarView.onMenuItemClickListener
                    config = BottomMenuItemViewConfig(
                        index = index,
                        bottomMenuItem = bottomMenuItems[index],
                        bottomBarFlavor = bottomBarFlavor,
                        animation = menuAnimation,
                        duration = animationDuration
                    )
                    bottomMenuItemViews.add(this)
                    if (index == selectedIndex) {
                        onMenuItemSelectedListener?.onMenuItemSelected(
                            selectedIndex,
                            requireNotNull(config?.bottomMenuItem),
                            false
                        )
                        animateBottomBarItem(this)
                        setIsActive(true)
                    }
                    if (index == bottomMenuItems.size - 1) {
                        onBottomMenuInitializedListener?.onInitialized()
                    }
                }
            )
        }
    }

    private fun initializeIndicator() {
        with(indicator) {
            if (visibleIndicator) {
                visible(true)
                x = (itemWidth * selectedIndex + indicatorPadding).toFloat()
                layoutParams.width = itemWidth - indicatorPadding * 2
                layoutParams.height = indicatorHeight
                setPadding(indicatorPadding, 0, indicatorPadding, 0)
                background = indicatorDrawable ?: GradientDrawable().apply {
                    setColor(this@AndroidBottomBarView.indicatorColor)
                    cornerRadius = this@AndroidBottomBarView.indicatorRadius
                }
            } else {
                visible(false)
            }
        }
    }

    /** adds an initial list of [BottomMenuItem]. */
    fun addBottomMenuItems(bottomMenuItems: List<BottomMenuItem>) {
        this.bottomMenuItems.addAll(bottomMenuItems)
    }

    /** gets a [BottomMenuItemView] by an index. */
    fun getBottomMenuItemView(index: Int): BottomMenuItemView {
        try {
            return bottomMenuItemViews[index]
        } catch (e: Exception) {
            throw Exception(
                "AndroidBottomBarView is not initialized completely yet. " +
                        "Use the function in the OnBottomMenuInitializedListener. "
            )
        }
    }

    /** sets an [OnMenuItemSelectedListener]. */
    fun setOnMenuItemSelectedListener(onMenuItemSelectedListener: OnMenuItemSelectedListener) {
        this.onMenuItemSelectedListener = onMenuItemSelectedListener
    }

    /** sets an [OnMenuItemSelectedListener] using an lambda. */
    @JvmSynthetic
    fun setOnMenuItemSelectedListener(block: (index: Int, bottomMenuItem: BottomMenuItem, fromUser: Boolean) -> Unit) {
        this.onMenuItemSelectedListener =
            OnMenuItemSelectedListener { index, bottomMenuItem, fromUser ->
                block(
                    index,
                    bottomMenuItem,
                    fromUser
                )
            }
    }

    /** sets an [OnBottomMenuInitializedListener]. */
    fun setOnBottomMenuInitializedListener(initializedListener: OnBottomMenuInitializedListener) {
        this.onBottomMenuInitializedListener = initializedListener
    }

    /** sets an [OnBottomMenuInitializedListener] using an lambda. */
    @JvmSynthetic
    fun setOnBottomMenuInitializedListener(block: () -> Unit) {
        this.onBottomMenuInitializedListener = OnBottomMenuInitializedListener { block() }
    }

    private fun animateBottomBarItem(bottomMenuItemView: BottomMenuItemView) {
        this.selectedItemView = bottomMenuItemView
    }

    private fun animateIndicator(config: BottomMenuItemViewConfig) {
        if (visibleIndicator) {
            with(indicator) {
                translateX(
                    from = x,
                    to = (itemWidth * config.index + indicatorPadding).toFloat(),
                    config = config
                )
            }
        }
    }

    @JvmSynthetic
    fun reRenderBottomNavigation(index: Int) {
        selectedIndex = index
        post {
            initializeBottomBarItems()
            initializeIndicator()
        }
    }
}
