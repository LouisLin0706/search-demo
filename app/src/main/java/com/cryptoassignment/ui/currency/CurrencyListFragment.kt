package com.cryptoassignment.ui.currency

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cryptoassignment.R
import com.cryptoassignment.base.BaseFragment
import com.cryptoassignment.base.observe
import com.cryptoassignment.databinding.FragmentCurrencyListBinding
import com.cryptoassignment.local.currency.Type
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import java.util.Date

@AndroidEntryPoint
class CurrencyListFragment : BaseFragment<FragmentCurrencyListBinding>() {

    @Parcelize
    data class Params(
        val types: List<Type>,
    ) : Parcelable

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrencyListBinding
        get() = FragmentCurrencyListBinding::inflate

    private val viewModel: CurrencyListViewModel by viewModels()

    private val searchIcon by lazy {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_vector_search)
            ?.apply { setBounds(0, 0, intrinsicWidth, intrinsicHeight) }
    }
    private val backIcon by lazy {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_vector_back)
            ?.apply { setBounds(0, 0, intrinsicWidth, intrinsicHeight) }
    }
    private val clearIcon by lazy {
        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_vector_clear_text)
            ?.apply { setBounds(0, 0, intrinsicWidth, intrinsicHeight) }
    }

    private fun getStartIcon(): Drawable? {
        return if (binding.edSearch.hasFocus()) {
            backIcon
        } else {
            null
        }
    }

    private fun getEndIcon(): Drawable? {
        return if (binding.edSearch.text.isEmpty()) {
            searchIcon
        } else {
            clearIcon
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CurrencyListAdapter()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        viewModel.currencyList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.edSearch.setOnFocusChangeListener { _, b ->
            setInputFieldIcons()
        }
        binding.edSearch.addTextChangedListener {
            setInputFieldIcons()
        }

        binding.edSearch.setOnTouchListener { view, motionEvent ->
            if (startDrawableClicked(
                    motionEvent,
                    getStartIcon(),
                    view as TextView
                )
            ) {
                binding.edSearch.clearFocus()
                val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
                return@setOnTouchListener true
            }
            if (shouldShowEndIcon() && endDrawableClicked(
                    motionEvent,
                    getEndIcon(),
                    view as TextView
                )
            ) {
                binding.edSearch.text.clear()
                return@setOnTouchListener true
            }
            false
        }
        setInputFieldIcons()
    }

    private fun setInputFieldIcons() {
        binding.edSearch.setCompoundDrawablesRelative(
            getStartIcon(), null, getEndIcon(),
            null
        )
    }

    private fun shouldShowEndIcon() = binding.edSearch.text.isNotEmpty()

    private fun endDrawableClicked(event: MotionEvent, icon: Drawable?, view: TextView): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (icon != null) {
                return if (isRTL(view.resources)) {
                    event.x <= getLeftTextViewDrawableClickBound(icon, view)
                } else {
                    event.x >= getRightTextViewDrawableClickBound(icon, view)
                }
            }
        }
        return false
    }

    @Suppress("CollapsibleIfStatements")
    private fun startDrawableClicked(event: MotionEvent, icon: Drawable?, view: TextView): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (icon != null) {
                return if (isRTL(view.resources)) {
                    event.x <= getRightTextViewDrawableClickBound(icon, view)
                } else {
                    event.x <= getLeftTextViewDrawableClickBound(icon, view)
                }
            }
        }
        return false
    }


    private fun isRTL(resources: Resources): Boolean {
        return resources.configuration.layoutDirection == AppCompatEditText.LAYOUT_DIRECTION_RTL
    }

    private fun getLeftTextViewDrawableClickBound(icon: Drawable, view: TextView): Int {
        return icon.intrinsicWidth + view.paddingLeft + view.compoundDrawablePadding
    }

    private fun getRightTextViewDrawableClickBound(icon: Drawable, view: TextView): Int {
        return view.width - icon.intrinsicWidth - view.paddingRight - view.compoundDrawablePadding
    }
}