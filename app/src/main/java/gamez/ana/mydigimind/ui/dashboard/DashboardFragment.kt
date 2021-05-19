package gamez.ana.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import gamez.ana.mydigimind.R
import gamez.ana.mydigimind.Task
import gamez.ana.mydigimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var storage: FirebaseFirestore
    private lateinit var usuario: FirebaseAuth


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()


        val btn_time: Button = root.findViewById(R.id.btn_time)
        val btn_save: Button = root.findViewById(R.id.btn_save)
        val et_task: EditText = root.findViewById(R.id.et_task)
        val cbMonday: CheckBox = root.findViewById(R.id.checkMonday)
        val cbTuesday: CheckBox = root.findViewById(R.id.checkTuesday)
        val cbWednesday: CheckBox = root.findViewById(R.id.checkWednesday)
        val cbThursday: CheckBox = root.findViewById(R.id.checkThursday)
        val cbFriday: CheckBox = root.findViewById(R.id.checkFriday)
        val cbSaturday: CheckBox = root.findViewById(R.id.checkSatuday)
        val cbSunday: CheckBox = root.findViewById(R.id.checkSunday)


        btn_time.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                btn_time.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        btn_save.setOnClickListener{
            var title = et_task.text.toString()
            var time = btn_time.text.toString()
            var days = ArrayList<String>()

            val actividad = hashMapOf(
                    "actividad" to et_task.text.toString(),
                    "email" to usuario.currentUser?.email.toString(),
                    "do" to cbSunday.isChecked,
                    "lu" to cbMonday.isChecked,
                    "ma" to cbTuesday.isChecked,
                    "mi" to cbWednesday.isChecked,
                    "ju" to cbThursday.isChecked,
                    "vi" to cbFriday.isChecked,
                    "sa" to cbSaturday.isChecked,
                    "do" to cbSunday.isChecked,
                    "tiempo" to btn_time.toString()
            )



            storage.collection("actividades")
                    .add(actividad)
                    .addOnSuccessListener {
                        Toast.makeText(root.context, "new task added", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(root.context, "Error: try again", Toast.LENGTH_SHORT).show()
                    }




        }

        return root
    }
}