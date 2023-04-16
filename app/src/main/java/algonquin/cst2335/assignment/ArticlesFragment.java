package algonquin.cst2335.assignment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.assignment.databinding.ArticlesFragmentBinding;

public class ArticlesFragment extends Fragment {

Articles articles;
ArticlesDAO articlesDao;
    public ArticlesFragment(Articles article) {
        articles = article;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ArticlesFragmentBinding binding = ArticlesFragmentBinding.inflate(getLayoutInflater());
        ArticlesDatabase db = Room.databaseBuilder(getActivity(),ArticlesDatabase.class,"articlesDatabase").build();
        articlesDao=db.articlesDAO();

        String addedString = getString(R.string.addedFavourites);
        binding.addFavourites.setOnClickListener((click) -> {
            Toast.makeText(getActivity(), addedString, Toast.LENGTH_SHORT).show();
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->{
                articlesDao.insertArticle(articles);
            });
        });

        binding.headlineArticle.setText(articles.getHeadlineArticle());
        binding.abstractArticle.setText(articles.getAbstractArticle());
        binding.pubDate.setText(articles.getPubDate());
        binding.urlArticle.setText(articles.getUrlArticle());
        return binding.getRoot();
    }
}