package com.android.app.justbarit.presentation.feature_calendar.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.app.justbarit.databinding.DialogReservationBottomSheetBinding
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.propagationAnimation
import com.android.app.justbarit.presentation.common.ext.toDp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogReservationBottomSheetBinding
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
    }

    private fun attachListeners() {
        binding.apply {
            finalizeReservation.clickToAction {
                dismiss()
            }

            closeBottomSheet.clickToAction {
                dismiss()
            }

            includedReservationLayout.reservationDecreaseImageView.clickToAction {
                decreaseReservation()
            }

            includedReservationLayout.reservationIncreaseImageView.clickToAction {
                increaseReservation()
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
        binding.includedReservationLayout.reservationCounterTextView.text = currentReservationCount.toString()
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
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheetInternal!!)

            behavior.peekHeight = desiredHeight
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


}