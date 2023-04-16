package algonquin.cst2335.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Articles> arrayList;

    public RecyclerAdapter(ArrayList<Articles> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.article_row,null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Articles articles = arrayList.get(position);
        holder.headlineArticle.setText(articles.getHeadlineArticle());
        holder.abstractArticle.setText(articles.getAbstractArticle());
        holder.urlArticle.setText(articles.getUrlArticle());
        holder.pubDate.setText(articles.getPubDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                ArticlesFragment articlesFragment = new ArticlesFragment(articles);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainerSearch,articlesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView headlineArticle;
        TextView abstractArticle;
        TextView urlArticle;
        TextView pubDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headlineArticle = itemView.findViewById(R.id.headlineArticle);
            abstractArticle = itemView.findViewById(R.id.abstractArticle);
            urlArticle = itemView.findViewById(R.id.urlArticle);
            pubDate = itemView.findViewById(R.id.pubDate);

            }
        }
    }