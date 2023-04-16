package algonquin.cst2335.assignment;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONObject;
import java.net.URLEncoder;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import algonquin.cst2335.assignment.databinding.ActivityNytBinding;


public class NYTActivity extends AppCompatActivity {

    protected RequestQueue queue = null;
    protected String articleName;
    ArticlesModel articlesModel;
    private ArrayList<Articles> arrayList;
    private RecyclerView recyclerViewSearch;

    RecyclerAdapter myAdapter;

    ArticlesDAO articlesDAO;

    ArticlesModel viewModel;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nyt_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId() )
        {
 /*           case R.id.kittenTool:
                Intent newApp = new Intent(this, PlaceKitten.class);
                startActivity(newApp);
                break;
            case R.id.weatherTool2:
                Intent newApp2 = new Intent(this, Weather.class);
                startActivity(newApp2);
                break;
            case R.id.toolbarNasa:
                Intent newApp3 = new Intent(this, NasaActivity.class);
                startActivity(newApp3);
                break;
    */    }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNytBinding binding = ActivityNytBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArticlesDatabase db = Room.databaseBuilder(getApplicationContext(),ArticlesDatabase.class,"NYTDatabase").build();
        articlesDAO = db.articlesDAO();

        viewModel = new ViewModelProvider(this).get(ArticlesModel.class);

        queue = Volley.newRequestQueue(this);

        articlesModel = new ViewModelProvider(this).get(ArticlesModel.class);

        arrayList = articlesModel.articles.getValue();

        if (arrayList == null) {
            articlesModel.articles.postValue(arrayList = new ArrayList<Articles>());
        }

        recyclerViewSearch = findViewById(R.id.recycleViewSearch);

        binding.favouritesButton.setOnClickListener(favouritesList -> {
        Intent intent = new Intent(NYTActivity.this, Favourites.class);
        startActivity(intent);
    });
        binding.searchButton.setOnClickListener(click -> {
            arrayList = null;
            articlesModel.articles.setValue(arrayList = new ArrayList<Articles>());

            articleName = binding.searchText.getText().toString();
            String stringURL = "";
            stringURL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="
                    + URLEncoder.encode(articleName)
                    + "&api-key=YX4n3MKT5p0XYGAJAe12uHSCs2kk9C7A";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,

                    (response) -> {

                        try {
                            ArrayList<Articles> arrayList = new ArrayList<>();
                            JSONObject responseObject = response.getJSONObject("response");

                            for (int i = 0; i < 10; i++) {
                                JSONObject docsObject = responseObject.getJSONArray("docs").getJSONObject(i);
                                String headlineArticle = docsObject.getJSONObject("headline").getString("main");
                                String abstractArticle = docsObject.getString("abstract");
                                String urlArticle = docsObject.getString("web_url");
                                String pubDate = docsObject.getString("pub_date");
                                arrayList.add(new Articles(headlineArticle, abstractArticle, urlArticle, pubDate));

                                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);
                                recyclerViewSearch.setAdapter(recyclerAdapter);
                                recyclerViewSearch.setLayoutManager(new LinearLayoutManager(NYTActivity.this));

                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
                                recyclerViewSearch.addItemDecoration(dividerItemDecoration);
                            }
                            runOnUiThread(() -> {
                                myAdapter.notifyItemInserted(arrayList.size() - 1);
                            });
                        } //end of try

                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    (error) -> {
                        Log.e("Error", "error");
                    });
            queue.add(request);
        });//end of button listener
        String about = getString(R.string.aboutHelp);
        String aboutTitle = getString(R.string.aboutString);
        setSupportActionBar(binding.NYTToolbar);
        binding.helpButton.setOnClickListener((click) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(about
                    );
            builder.setTitle(aboutTitle).
                    setPositiveButton("Ok", (dialog, click2) -> {}).create().show();
        });

    }//end of onCreate

}//end of main
