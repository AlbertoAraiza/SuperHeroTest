package mx.araiza.superherotest.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.araiza.superherotest.R
import mx.araiza.superherotest.adapters.SuperheroRVA
import mx.araiza.superherotest.databinding.FragmentSuperHeroListBinding
import mx.araiza.superherotest.model.database.SuperheroDAO
import mx.araiza.superherotest.model.database.SuperheroDB
import mx.araiza.superherotest.viewmodel.SuperheroListViewModel

class SuperHeroListFragment : Fragment() {
    private val viewModel :SuperheroListViewModel by viewModels()
    private lateinit var binding :FragmentSuperHeroListBinding
    private lateinit var superheroesAdapter : SuperheroRVA
    private lateinit var superheroDAO :SuperheroDAO
    private var isLoading = false
    private val ctx by lazy { requireContext() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        superheroDAO = SuperheroDB.getDatabase(requireContext()).getSuperheroDAO()

        superheroesAdapter = SuperheroRVA(ArrayList()){
            val bundle = bundleOf("superhero" to it)
            findNavController().navigate(R.id.action_superHeroListFragment_to_superheroDetailsFragment, bundle)
        }

        superheroDAO.countSavedHeroes().observe(this){
            SuperheroDB.databaseWritter.execute {
                val initRange = superheroesAdapter.itemCount
                val newHeroes = superheroDAO.getRandomHeroes()
                val endRange = newHeroes.size

                superheroesAdapter.superheroList.addAll(newHeroes)
                requireActivity().runOnUiThread { superheroesAdapter.notifyItemRangeInserted(initRange,endRange) }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSuperHeroListBinding.inflate(layoutInflater)

        val gridLayoutManager = GridLayoutManager(ctx, 2)
        binding.rvSuperheroes.apply {
            layoutManager = gridLayoutManager
        }



        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSuperheroes.adapter = superheroesAdapter
        binding.rvSuperheroes.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !isLoading){
                    isLoading = true
                    binding.pbLoading.visibility = View.VISIBLE
                    SuperheroDB.databaseWritter.execute{
                        val newHeroes = superheroDAO.getRandomHeroes()
                        superheroesAdapter.superheroList.addAll(newHeroes)
                        requireActivity().runOnUiThread {
                            superheroesAdapter.notifyDataSetChanged()
                            binding.pbLoading.visibility = View.GONE
                            isLoading = false
                        }
                    }
                }
            }
        })
    }
}