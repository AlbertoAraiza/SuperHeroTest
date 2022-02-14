package mx.araiza.superherotest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import mx.araiza.superherotest.databinding.ItemlistSuperheroBinding
import mx.araiza.superherotest.model.SuperheroModel

class SuperheroRVA(val superheroList :ArrayList<SuperheroModel>, val imageListener :(SuperheroModel) ->Unit) :RecyclerView.Adapter<SuperheroVH>() {
    lateinit var glide :RequestManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroVH {
        glide = Glide.with(parent.context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemlistSuperheroBinding.inflate(inflater, parent, false)
        return SuperheroVH(binding)
    }

    override fun onBindViewHolder(holder: SuperheroVH, position: Int) {
        val currentSuperHero = superheroList[position]
        holder.binding.let {
            it.ivSuperheroImage.setOnClickListener {
                imageListener(currentSuperHero)
            }
            glide.load(currentSuperHero.imageURL).centerCrop().into(it.ivSuperheroImage)
            it.tvSuperheroName.text = currentSuperHero.name
        }
    }

    override fun getItemCount(): Int = superheroList.size
}

class SuperheroVH(val binding :ItemlistSuperheroBinding) :RecyclerView.ViewHolder(binding.root)