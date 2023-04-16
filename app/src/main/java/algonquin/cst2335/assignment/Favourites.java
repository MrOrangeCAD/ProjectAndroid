package algonquin.cst2335.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.assignment.databinding.ActivityFavouritesBinding;



public class Favourites extends AppCompatActivity {

    ArticlesDAO articlesDAO;
    private ArrayList<Articles> arrayList;
    ArticlesModel articlesModel;

    RecyclerView.Adapter myAdapter;
    RecyclerView recyclerViewFavourites;

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

        ActivityFavouritesBinding binding = ActivityFavouritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArticlesDatabase db = Room.databaseBuilder(getApplicationContext(), ArticlesDatabase.class, "articlesDatabase").build();
        articlesDAO = db.articlesDAO();

        articlesModel = new ViewModelProvider(this).get(ArticlesModel.class);

        arrayList = articlesModel.articles.getValue();
        articlesModel.articles.postValue(arrayList = new ArrayList<Articles>());
        //recyclerView = binding.recycleViewFav;

        recyclerViewFavourites = findViewById(R.id.recycleViewFavourites);

        if (arrayList == null) {
            articlesModel.articles.postValue(arrayList = new ArrayList<Articles>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                arrayList.addAll(articlesDAO.getAllArticles());
            });
            recyclerViewFavourites.setAdapter(myAdapter);
        }
        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView headlineArticle;
            TextView abstractArticle;
            TextView urlArticle;
            TextView pubDate;

            public MyRowHolder(@NonNull View itemView) {
                super(itemView);

                headlineArticle = itemView.findViewById(R.id.headlineArticle);
                abstractArticle = itemView.findViewById(R.id.abstractArticle);
                urlArticle = itemView.findViewById(R.id.urlArticle);
                pubDate = itemView.findViewById(R.id.pubDate);
                itemView.setOnClickListener((clk) -> {
                    int position = getAbsoluteAdapterPosition();
                    Articles selected = arrayList.get(position);
                    articlesModel.selectedArticle.postValue(selected);
                });
            }

        }

        binding.recycleViewFavourites.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleViewFavourites.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View inflate = layoutInflater.inflate(R.layout.article_row_fav,null);
                MyRowHolder viewHolder = new MyRowHolder(inflate);
                return viewHolder;
            }

             @Override
              public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                  Articles articles = arrayList.get(position);
                  holder.headlineArticle.setText(articles.getHeadlineArticle());
                  holder.abstractArticle.setText(articles.getAbstractArticle());
                  holder.urlArticle.setText(articles.getUrlArticle());
                  holder.pubDate.setText(articles.getPubDate());



                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                        /* //Favourites fav = articles.get(position);

                         AlertDialog.Builder builder = new AlertDialog.Builder( Favourites.this );
                         builder.setMessage("Do you want to delete this message? " + articles.getHeadlineArticle())
                                 .setTitle("Warning")
                                 .setPositiveButton("Ok", (dialog, which) -> {
                                     Executor thread_1 = Executors.newSingleThreadExecutor();
                                     thread_1.execute(() -> {
                                     articlesDAO.deleteArticle(fav);
                                     articles.remove(position);
                                 });*/
                                     }
                 });

              }


            @Override
            public int getItemCount() {
                return arrayList.size();
            }


       });




        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            List<Articles> listFavourites = articlesDAO.getAllArticles();
            arrayList.addAll(listFavourites);

            RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(arrayList);
            binding.recycleViewFavourites.setAdapter(recyclerAdapter2);
            binding.recycleViewFavourites.setLayoutManager(new LinearLayoutManager(Favourites.this));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
            binding.recycleViewFavourites.addItemDecoration(dividerItemDecoration);

            runOnUiThread( () -> {
                binding.recycleViewFavourites.setAdapter(myAdapter);
            });

        });

        binding.backButton.setOnClickListener(favouritesList -> {
            Intent backButton = new Intent(this, NYTActivity.class);
            startActivity(backButton);
        });

    }

}

