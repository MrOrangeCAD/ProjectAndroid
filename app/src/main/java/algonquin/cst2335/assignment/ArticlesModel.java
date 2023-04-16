package algonquin.cst2335.assignment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ArticlesModel extends ViewModel {

    public MutableLiveData<ArrayList<Articles>> articles = new MutableLiveData<>();

    public MutableLiveData<Articles> selectedArticle = new MutableLiveData<>();

}
