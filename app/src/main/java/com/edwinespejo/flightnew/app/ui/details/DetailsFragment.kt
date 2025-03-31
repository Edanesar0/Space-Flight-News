package com.edwinespejo.flightnew.app.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.edwinespejo.flightnew.app.R
import com.edwinespejo.flightnew.app.adapter.ktx.DialogExtension.showCustomAlertDialog
import com.edwinespejo.flightnew.app.databinding.FragmentDetailNewsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailNewsBinding
    private val viewModel: DetailsViewModel by viewModels()
    private var loading: ConstraintLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val toolbar: Toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val args: DetailsFragmentArgs by navArgs()
        val id = args.id
        loadData(id)
        return view
    }

    private fun loadData(id: Int) {
        viewModel.getArticle(id, {
            Picasso.get()
                .load(it.imageUrl)
                .resize(600, 600)
                .centerInside()
                .into(binding.imgNew)

            binding.toolbarLayout.title = ""
            binding.txtTitle.text = it.title
            binding.txtDetails.text = it.summary
            binding.txtDate.text = it.publishedAt
            var authors = ""
            it.authors.forEach {
                authors += "${it.name}\n"
            }
            binding.txtAuthors.text = authors

        }, {
            showDialog()
        })

    }

    private fun showDialog() {
        activity?.showCustomAlertDialog(
            getString(R.string.error_connection),
            getString(R.string.error)
        ) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }
}