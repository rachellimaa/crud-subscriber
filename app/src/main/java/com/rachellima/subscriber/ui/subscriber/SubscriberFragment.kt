package com.rachellima.subscriber.ui.subscriber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rachellima.subscriber.R
import com.rachellima.subscriber.data.db.AppDatabase
import com.rachellima.subscriber.data.db.dao.SubscriberDao
import com.rachellima.subscriber.extension.hideKeyboard
import com.rachellima.subscriber.repository.DatabaseDataSource
import com.rachellima.subscriber.repository.SubscribeRepository
import kotlinx.android.synthetic.main.subscriber_fragment.*

class SubscriberFragment : Fragment(R.layout.subscriber_fragment) {

    private val viewModel: SubscriberViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDao: SubscriberDao =
                    AppDatabase.getInstance(requireContext()).subscriberDao
                val repository: SubscribeRepository = DatabaseDataSource(subscriberDao)
                return SubscriberViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) { subscriberState ->
            when (subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted -> {
                    clearFields()
                    hideKeyboard()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResID ->
            Snackbar.make(requireView(), stringResID, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        input_layout_email.editText?.text?.clear()
        input_layout_name.editText?.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        button_add.setOnClickListener {
            val name = input_layout_name.editText?.text.toString()
            val email = input_layout_email.editText?.text.toString()

            viewModel.addSubscriber(name, email)
        }
    }

}