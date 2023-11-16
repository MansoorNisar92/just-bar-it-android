package com.android.app.justbarit.presentation.feature_calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.app.justbarit.databinding.DialogReservationBottomSheetBinding
import com.android.app.justbarit.presentation.common.customviews.calendar.CalendarAdapter
import com.android.app.justbarit.presentation.common.customviews.calendar.TimeAdapter
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.propagationAnimation
import com.android.app.justbarit.presentation.feature_calendar.viewmodel.ReservationBottomSheetViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class ReservationBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogReservationBottomSheetBinding
    private val viewModel: ReservationBottomSheetViewModel by viewModels()
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var timeAdapter: TimeAdapter
    private var bottomSheetInternal: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogReservationBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBottomSheetHeight()
        init()
    }

    private fun init() {
        handDecreaseReservationButton(true)
        attachListeners()
        updateDisplayedMonth()
        initDays(calendar)
        initTime()
    }

    private fun initTime() {
        val timeList = generateTime()
        timeAdapter = TimeAdapter(timeList)
        binding.includedTimeLayout.calendarTimeRecyclerView.adapter = timeAdapter
    }

    private fun initDays(calendar: Calendar) {
        val yourDataForThisMonth = generateDataForMonth(calendar) // Replace with your logic
        calendarAdapter = CalendarAdapter(yourDataForThisMonth)
        binding.includedDateLayout.calendarDayRecyclerView.adapter = calendarAdapter
    }

    private fun generateTime(): List<String> {
        return viewModel.fetchTime()
    }

    private fun generateDataForMonth(month: Calendar): List<Pair<String, String>> {
        return viewModel.fetchDataForMonth(month)
    }

    private fun attachListeners() {
        binding.apply {
            finalizeReservation.clickToAction {
                dismissBottomSheet()
            }

            closeBottomSheet.clickToAction {
                dismissBottomSheet()
            }

            includedReservationLayout.reservationDecreaseImageView.clickToAction {
                decreaseReservation()
            }

            includedReservationLayout.reservationIncreaseImageView.clickToAction {
                increaseReservation()
            }

            includedDateLayout.calendarLeftButton.clickToAction {
                navigateToPreviousMonth()
            }

            includedDateLayout.calendarRightButton.clickToAction {
                navigateToNextMonth()
            }
        }
    }


    private fun increaseReservation() {
        handleReservationCount(1)
    }

    private fun decreaseReservation() {
        handleReservationCount(-1)
    }

    private fun handleReservationCount(incomingCount: Int) {
        var currentReservationCount =
            binding.includedReservationLayout.reservationCounterTextView.text.toString().toInt()
        currentReservationCount += incomingCount

        if (currentReservationCount <= 0) {
            handDecreaseReservationButton(true)
            return
        } else if (currentReservationCount == 1) {
            handDecreaseReservationButton(true)
        } else {
            handDecreaseReservationButton(false)
        }
        binding.includedReservationLayout.reservationCounterTextView.text =
            currentReservationCount.toString()
        binding.includedReservationLayout.reservationCounterTextView.propagationAnimation()
    }

    private fun handDecreaseReservationButton(reachedThreshold: Boolean) {
        var alpha = 1f
        var enabled = true
        if (reachedThreshold) {
            alpha = .5f
            enabled = false
        }
        binding.includedReservationLayout.reservationDecreaseImageView.isEnabled = enabled
        binding.includedReservationLayout.reservationDecreaseImageView.alpha = alpha
    }

    private fun handleBottomSheetHeight() {
        val screenHeight = resources.displayMetrics.heightPixels
        val desiredHeight = (screenHeight * 0.75).toInt() // Adjust as needed

        // Set the fixed height for your layout
        val layoutParams = binding.bottomSheetLayout.layoutParams
        layoutParams.height = desiredHeight
        binding.bottomSheetLayout.layoutParams = layoutParams

        // Offset the starting position of the bottom sheet
        dialog?.setOnShowListener {
            val d = dialog as BottomSheetDialog
            bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheetInternal!!)

            behavior.peekHeight = desiredHeight
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    // Method to dismiss the BottomSheet
    private fun dismissBottomSheet() {
        val behavior = BottomSheetBehavior.from(bottomSheetInternal!!)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private var calendar = Calendar.getInstance()

    private fun navigateToPreviousMonth() {
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        if (currentMonth == Calendar.JANUARY) {
            calendar.set(Calendar.YEAR, currentYear - 1)
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        } else {
            calendar.set(Calendar.MONTH, currentMonth - 1)
        }

        updateDisplayedMonth()
        initDays(calendar)
    }

    private fun navigateToNextMonth() {
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.YEAR, currentYear)
        updateDisplayedMonth()
        initDays(calendar) // Update RecyclerView for the new month
    }

    private fun updateDisplayedMonth() {
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val formattedMonthYear = monthYearFormat.format(calendar.time)
        binding.includedDateLayout.monthAndYearTextView.text = formattedMonthYear
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissBottomSheet()
    }

}