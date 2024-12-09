package com.gabrielfreire.fitplanner.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabrielfreire.fitplanner.databinding.FragmentMoreOptionsBinding

class MoreOptionsFragment : Fragment() {

    private lateinit var moreOptionsBinding: FragmentMoreOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moreOptionsBinding = FragmentMoreOptionsBinding.inflate(inflater, container, false)
        return moreOptionsBinding.root
    }
}