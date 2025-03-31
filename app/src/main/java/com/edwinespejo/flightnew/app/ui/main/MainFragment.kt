package com.edwinespejo.flightnew.app.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinespejo.flightnew.app.R
import com.edwinespejo.flightnew.app.adapter.ktx.DialogExtension.showCustomAlertDialog
import com.edwinespejo.flightnew.app.core.entities.news.ResultsItem
import com.edwinespejo.flightnew.app.databinding.FragmentMainBinding
import com.edwinespejo.flightnew.app.ui.adapter.GenericAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var isSearching: Boolean = false
    private var loading: ConstraintLayout? = null
    private lateinit var view: View
    private lateinit var data: List<ResultsItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        view = binding.root

        binding.search.setOnClickListener {
            toggleTextInputLayoutVisibility(binding.contSearch, isSearching)
            isSearching = !isSearching
        }
        binding.toolbarLayout.title = "Flight News "

        loading = binding.loading
        loading?.visibility = View.VISIBLE


        binding.rcvNews.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        val adapter = GenericAdapter<ResultsItem>(R.layout.item_news)

        viewModel.getFlightNews({ itemConfigData ->
            data = itemConfigData
            loadItemsAdapter(adapter, itemConfigData, true)
        }, {
            loading?.visibility = View.GONE
            println(it)
            showDialog()
        })


        binding.edtSearch.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val finds = data.filter {
                    it.title.lowercase(Locale.getDefault()).contains(s.toString())
                }
                loadItemsAdapter(adapter, finds, true)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        return view
    }

    private fun loadItemsAdapter(
        adapter: GenericAdapter<ResultsItem>,
        itemConfigData: List<ResultsItem>,
        isFirstLoad: Boolean = false
    ) {
        when {
            isFirstLoad -> adapter.addItems(itemConfigData)
            else -> adapter.addMoreItems(itemConfigData)
        }
        adapter.setOnListItemViewClickListener { _, position ->
            val productSelected = adapter.getItemForPosition(position)
            Toast.makeText(context, " ${productSelected.id}", Toast.LENGTH_SHORT).show()
            val action =
                MainFragmentDirections.actionNavigationHomeToDetailFragment(productSelected.id)
            view.findNavController().navigate(action)
        }

        binding.rcvNews.adapter = adapter
        binding.nestedRV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loading?.visibility = View.VISIBLE
                viewModel.getMoreFlightNews(
                    { itemConfigData ->
                        loadItemsAdapter(adapter, itemConfigData)
                    }, {
                        loading?.visibility = View.GONE
                        println(it)
                        showDialog()
                    }
                )
            }
        })
        loading?.visibility = View.GONE
    }

    private fun showDialog() {
        activity?.showCustomAlertDialog(
            getString(R.string.error_connection),
            getString(R.string.error)
        ) {
            activity?.finish()
        }
    }

    private fun toggleTextInputLayoutVisibility(textInputLayout: View, show: Boolean) {
        val animator = ObjectAnimator.ofFloat(
            textInputLayout,
            "alpha",
            if (!show) 0f else 1f,
            if (!show) 1f else 0f
        )
        animator.duration = 300
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                if (!show) {
                    textInputLayout.visibility = View.VISIBLE
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                textInputLayout.visibility = if (!show) View.VISIBLE else View.GONE
            }
        })
        animator.start()
    }

}