package mx.araiza.superherotest.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import mx.araiza.superherotest.adapters.CustomXLA
import mx.araiza.superherotest.databinding.FragmentSuperheroDetailsBinding
import mx.araiza.superherotest.model.SuperheroModel
import mx.araiza.superherotest.viewmodel.MainActivityViewModel

class SuperheroDetailsFragment : Fragment() {
    val viewmodel :MainActivityViewModel by activityViewModels()
    var expandableAdapter : CustomXLA? = null
    lateinit var binding :FragmentSuperheroDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperheroDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.selectedHero.observe(this){superhero ->
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