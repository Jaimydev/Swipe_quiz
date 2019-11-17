package com.kotlinhva.swipequiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<QuizQuestion>()
    private val quizQuestionAdapter = QuizQuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        createQuestions()

        initViews()
    }

    private fun createQuestions()
    {
        addQuestion("Are the skies blue?", true)
        addQuestion("Is the sea blue?", true)
        addQuestion("Can dolphins fly?", false)
        addQuestion("Is the HvA located in Utrecht?", false)

    }

    private fun addQuestion(question: String, answer: Boolean) {
            questions.add(QuizQuestion(question, answer))
            quizQuestionAdapter.notifyDataSetChanged()

    }



    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = quizQuestionAdapter
        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                var question = questions.elementAt(position);
                if(direction == ItemTouchHelper.LEFT)
                {
                    if(question.answer){
                        Snackbar.make(rvQuestions, "Wrong! Answer was : ${question.answer}", Snackbar.LENGTH_SHORT).show()
                    }
                    if(!question.answer){
                        Snackbar.make(rvQuestions, "Correct! Answer was : ${question.answer}", Snackbar.LENGTH_SHORT).show()
                    }
                }

                if(direction == ItemTouchHelper.RIGHT)
                {
                    if(!question.answer){
                        Snackbar.make(rvQuestions, "Wrong! Answer was : ${question.answer}", Snackbar.LENGTH_SHORT).show()
                    }
                    if(question.answer){
                        Snackbar.make(rvQuestions, "Correct! Answer was : ${question.answer}", Snackbar.LENGTH_SHORT).show()
                    }
                }
                quizQuestionAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

}
