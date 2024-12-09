package com.gabrielfreire.fitplanner.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabrielfreire.fitplanner.databinding.FragmentWorkoutsBinding

class WorkoutsFragment : Fragment() {

    private lateinit var workoutsBinding: FragmentWorkoutsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        workoutsBinding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        return workoutsBinding.root
    }
}