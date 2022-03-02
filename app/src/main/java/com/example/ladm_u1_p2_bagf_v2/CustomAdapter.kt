package com.example.ladm_u1_p2_bagf_v2

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.ladm_u1_p2_bagf_v2.ui.gallery.GalleryFragment
import com.example.ladm_u1_p2_bagf_v2.ui.home.HomeFragment
import com.example.ladm_u1_p2_bagf_v2.ui.home.HomeViewModel
import com.example.ladm_u1_p2_bagf_v2.ui.slideshow.SlideshowFragment
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class CustomAdapter : Adapter<CustomAdapter.ViewHolder>() {

    companion object {
        var tasks = mutableListOf<String>()
        var details = mutableListOf<String>()
        var index: Int = 0
    }

    fun leer(context: Context) {
        try {
            tasks.clear()
            details.clear()

            val archivo = BufferedReader(InputStreamReader(context.openFileInput("datos.txt")))
            val listaContenido = archivo.readLines()

            for (item in listaContenido) {
                // println(item)
                val aux = item.split(",")
                tasks.add(aux[0])
                details.add(aux[1])
            }

        } catch (e: Exception) {
            AlertDialog.Builder(context)
                .setMessage(e.message).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lista_tareas, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item_task.text = tasks[holder.adapterPosition]
        holder.item_detalles.text = details[holder.adapterPosition]

        holder.item_eliminar.setOnClickListener {
            // Borrar del recyclerView el item
            tasks.removeAt(holder.adapterPosition)
            details.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            notifyItemRangeChanged(holder.adapterPosition, tasks.size)

            // Procesar cadena para ser escrita en txt
            var cad = ""
            for (i in 0 until tasks.size) {
                println("for $i")
                val t = tasks[i]
                val d = details[i]
                cad += t + ", " + d + "\n"
            }
            println(cad)

            // Guardar la cadena en txt
            try {
                val outputStreamWriter =
                    OutputStreamWriter(
                        holder.item_detalles.context.openFileOutput(
                            "datos.txt",
                            Context.MODE_PRIVATE
                        )
                    )
                outputStreamWriter.write(cad + "\n")
                outputStreamWriter.flush()
                outputStreamWriter.close()
            } catch (e: IOException) {
                println(e.message)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_task: TextView = itemView.findViewById(R.id.titulo_tarea)
        var item_detalles: TextView = itemView.findViewById(R.id.descripcion_tarea)
        var item_eliminar: CheckBox = itemView.findViewById(R.id.eliminarTarea)
    }
}