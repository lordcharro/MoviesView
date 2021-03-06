package com.charroapps.moviesview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.charroapps.moviesview.Models.Result
import com.charroapps.moviesview.Network.GetDataService
import com.charroapps.moviesview.Network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.charroapps.moviesview.Adapters.MovieAdapter
import com.charroapps.moviesview.Models.Movies
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_movie_list.*
import android.view.Menu
import com.charroapps.moviesview.Utilities.EXTRA_MOVIE
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.charroapps.moviesview.Utilities.KEY_RECYCLER_STATE


class MovieList : AppCompatActivity() {

    lateinit var adapter : MovieAdapter
    lateinit var service : GetDataService
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    lateinit var mGoogleApiClient : GoogleApiClient
    lateinit var mLayoutManager : LinearLayoutManager
    var results : Result? = null


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        //Saved the results list in case of screen rotation, move away from activity...
        outState?.putParcelable(KEY_RECYCLER_STATE, results)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        //The activity is rebuild, so lets get the previously state
        if(savedInstanceState != null){
            results = savedInstanceState.getParcelable(KEY_RECYCLER_STATE)
            results?.results?.let { generateDataList(it) }
        }
    }

    override fun onStart() {
        super.onStart()

        //initialize a firebase listener to receive the change in the user state
        mAuth.addAuthStateListener(mAuthListener)

        //Get the Google user connected
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        mGoogleApiClient.connect()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        /*Create handle for the RetrofitInstance interface*/
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService::class.java)

        //Verify if there isn't any user connected and then transit back to the login
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                startActivity(Intent(this@MovieList,LoginActivity::class.java))
                finish()
            }
        }
        mLayoutManager = LinearLayoutManager(this@MovieList)

    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private fun generateDataList(movieList: List<Movies>) {

        adapter = MovieAdapter(this, movieList){movies ->
            //A item in the list was clicked, lets pass to the next activity and pass the info as a parcel
            val mdetailIntent = Intent(this@MovieList, DetailActivity::class.java)
            mdetailIntent.putExtra(EXTRA_MOVIE, movies)
            startActivity(mdetailIntent)
        }

        movieRecView.setLayoutManager(mLayoutManager)
        movieRecView.setAdapter(adapter)
    }

    fun onSearchClick(view : View){

        val searchTxt = movieSearch.text.toString()
        movieProgressbar.visibility = View.VISIBLE

        //get the complete URL with the search field for the call
        val call = service.getMovies(getString(R.string.api_key), searchTxt)

        /*Make the call to the Rest API of tmdb
        * Wait for the response, in case of positive, get the response to the Result object
        * In case of negative, display a toast*/
        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                movieProgressbar.visibility = View.GONE
                results = response?.body()
                results?.results?.let { generateDataList(it) }
            }

            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                movieProgressbar.visibility = View.GONE
                Toast.makeText(this@MovieList, "Something went wrong...Please try again later!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun signOut() {
        mAuth.signOut()
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
    }

    /*onCreateOptionsMenu and onOptionsItemSelected, two functions for the tool menu
     * and have the option for logout */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            signOut()
            true
        }
        R.id.action_history -> {
            val mHistoryIntent = Intent(this@MovieList, HistoryListActivity::class.java)
            startActivity(mHistoryIntent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
