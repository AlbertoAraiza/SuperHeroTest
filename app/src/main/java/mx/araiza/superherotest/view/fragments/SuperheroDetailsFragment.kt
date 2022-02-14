package mx.araiza.superherotest.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import mx.araiza.superherotest.R
import mx.araiza.superherotest.adapters.CustomXLA
import mx.araiza.superherotest.databinding.FragmentSuperheroDetailsBinding
import mx.araiza.superherotest.model.SuperheroModel
import mx.araiza.superherotest.viewmodel.SuperheroDetailsViewmodel

class SuperheroDetailsFragment : Fragment() {
    var selectedHero :SuperheroModel? = null
    var expandableAdapter : CustomXLA? = null
    lateinit var binding :FragmentSuperheroDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { extras ->
            selectedHero = extras["superhero"] as SuperheroModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperheroDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedHero?.let{superhero ->
            binding.tvSuperheroName.text =  superhero.name
            Glide.with(requireContext()).load(superhero.imageURL).into(binding.ivSuperheroImage)
            if (expandableAdapter == null){
                expandableAdapter = CustomXLA(superhero)
                binding.xlvData.setAdapter(expandableAdapter)
            }else{
                expandableAdapter!!.superhero = superhero
                expandableAdapter!!.notifyDataSetChanged()
            }
        }


    }
}