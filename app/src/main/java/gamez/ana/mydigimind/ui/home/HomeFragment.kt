package gamez.ana.mydigimind.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import gamez.ana.mydigimind.AdaptadorTareas
import gamez.ana.mydigimind.R
import gamez.ana.mydigimind.Task

class HomeFragment : Fragment() {


    private var adaptador: AdaptadorTareas? = null
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth
    lateinit var gridView: GridView

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

        tasks = ArrayList()
        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()
        fillTask()
        val gridView: GridView = root.findViewById(R.id.gridview)
        if(!tasks.isEmpty()){

            adaptador = AdaptadorTareas(root.context, tasks)
            gridView.adapter = adaptador
        }


        return root
    }

    fun fillTask(){
        storage.collection("actividades")
            .whereEqualTo("email", usuario.currentUser?.email)
            .get()
            .addOnSuccessListener {
                it.forEach{
                    var dias = ArrayList<String>()
                    if(it.getBoolean("lu") == true){
                        dias.add("Monday")
                    }
                    if(it.getBoolean("ma") == true){
                        dias.add("Tuesday")
                    }
                    if(it.getBoolean("mi") == true){
                        dias.add("Wednesday")
                    }
                    if(it.getBoolean("ju") == true){
                        dias.add("Thursday")
                    }
                    if(it.getBoolean("vi") == true){
                        dias.add("Friday")
                    }
                    if(it.getBoolean("sa") == true){
                        dias.add("Saturday")
                    }
                    if(it.getBoolean("do") == true){
                        dias.add("Sunday")
                    }
                    tasks!!.add(Task(it.getString("actividad")!!, dias, it.getString("tiempo")!!))
                }
                adaptador = AdaptadorTareas(requireContext(), tasks)
                gridView.adapter = adaptador
            }
            .addOnFailureListener{
                Toast.makeText(context, "Error: try again", Toast.LENGTH_SHORT)
            }

    }
}