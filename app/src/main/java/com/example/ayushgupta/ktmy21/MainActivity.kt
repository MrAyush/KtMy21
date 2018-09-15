package com.example.ayushgupta.ktmy21

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.ayushgupta.ktmy21.Model.GetGitClient
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI
import java.net.URL
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Log = Logger.getLogger(MainActivity::class.java.name)

        val apolloClient = GetGitClient().getApolloClient()
        button.setOnClickListener {
            apolloClient.query(ForGitQuery.builder().owner("MrAyush").name("PP2").build())
                    .enqueue(object : ApolloCall.Callback<ForGitQuery.Data>() {
                        override fun onFailure(e: ApolloException) {
                            e.printStackTrace()
                            Log.info("Error msg " + e.message)
                            //Toast.makeText(this@MainActivity, "Failed to load your query", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(response: Response<ForGitQuery.Data>) {
                            Log.info("Response by git: ${response.data()}")
                            Log.info("URL ${(response.data()?.repository?.url as URI)}")
                        }

                    })
        }

    }
}
