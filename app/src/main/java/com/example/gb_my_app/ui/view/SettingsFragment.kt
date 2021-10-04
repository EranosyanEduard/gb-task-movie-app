package com.example.gb_my_app.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gb_my_app.SharedPreferencesCallbacks
import com.example.gb_my_app.databinding.SettingsFragmentBinding
import com.example.gb_my_app.view_model.SettingsViewModel

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    private var callbacks: SharedPreferencesCallbacks? = null

    // View binding
    private var viewBindingRef: SettingsFragmentBinding? = null

    private val viewBinding get() = viewBindingRef!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as SharedPreferencesCallbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBindingRef = SettingsFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.also { vb ->
            val isCheckedDefault = true

            // Установить флаг доступа к взрослому контенту в соответствующее значение
            // после создания view
            vb.settingsItemUseAdult.isChecked = callbacks
                ?.getBooleanPreference(ALLOW_ADULT_CONTENT, isCheckedDefault)
                ?: isCheckedDefault

            vb.settingsItemUseAdult.setOnCheckedChangeListener { _, isChecked ->
                callbacks?.editBooleanPreference(ALLOW_ADULT_CONTENT, isChecked)
            }
        }
    }
}
