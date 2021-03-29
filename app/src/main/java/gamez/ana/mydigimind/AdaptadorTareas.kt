package gamez.ana.mydigimind

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdaptadorTareas: BaseAdapter {
    var tasks = ArrayList<Task>()
    var contexto: Context? = null

    constructor(contexto: Context, tasks: ArrayList<Task>){
        this.contexto = contexto
        this.tasks = tasks
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var task = tasks[position]
        var inflator = LayoutInflater.from(contexto)
        var vista = inflator.inflate(R.layout.task_view, null)

        var tv_title: TextView = vista.findViewById(R.id.tv_title)
        var tv_time: TextView = vista.findViewById(R.id.tv_time)
        var tv_days: TextView = vista.findViewById(R.id.tv_days)

        tv_title.setText(task.title)
        tv_time.setText(task.time)
        tv_days.setText(task.days.toString())

        return vista


    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return  tasks.size
    }

}