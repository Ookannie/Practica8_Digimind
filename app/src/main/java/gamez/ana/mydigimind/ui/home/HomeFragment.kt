package gamez.ana.mydigimind.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gamez.ana.mydigimind.AdaptadorTareas
import gamez.ana.mydigimind.R
import gamez.ana.mydigimind.Task

class HomeFragment : Fragment() {


    private var adaptador: AdaptadorTareas? = null
    private lateinit var homeViewModel: HomeViewModel

    companion object{
        var tasks = ArrayList<Task>()
        var first = true
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        if(first){
        fillTask()
            first = false
        }

        adaptador = AdaptadorTareas(root.context, tasks)


        val gridView: GridView = root.findViewById(R.id.gridview)

        gridView.adapter = adaptador


        return root
    }

    fun fillTask(){
        tasks.add(Task("Practice 01", arrayListOf("Monday", "Sunday"), "17:30"))
        tasks.add(Task("Practice 02", arrayListOf("Tuesday"), "17:40"))
        tasks.add(Task("Practice 03", arrayListOf("Wednesday"), "14:30"))
        tasks.add(Task("Practice 04", arrayListOf("Saturday"), "10:45"))
        tasks.add(Task("Practice 05", arrayListOf("Friday"), "9:30"))
        tasks.add(Task("Practice 06", arrayListOf("Thursday"), "8:00"))
        tasks.add(Task("Practice 07", arrayListOf("Monday"), "8:45"))
    }
}