package com.cryptoassignment.ui.demo.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.cryptoassignment.R
import com.cryptoassignment.base.BaseFragment
import com.cryptoassignment.databinding.FragmentDemoControlPanel2Binding
import com.cryptoassignment.ui.demo.DemoActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DemoControlPanel2Fragment : BaseFragment<FragmentDemoControlPanel2Binding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDemoControlPanel2Binding
        get() = FragmentDemoControlPanel2Binding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                MaterialTheme {
                    DemoControlPanelScreen((activity as DemoActivity).findNavController(R.id.nav_host_fragment))
                }
            }
        }
    }
}