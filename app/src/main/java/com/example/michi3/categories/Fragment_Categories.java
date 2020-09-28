package com.example.michi3.categories;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.michi3.Activity_add;
import com.example.michi3.Activity_modify;
import com.example.michi3.Database.MyViewModel;
import com.example.michi3.Interfaces.OnItemListener;
import com.example.michi3.R;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ClickableViewAccessibility")
public class Fragment_Categories extends Fragment implements OnItemListener {
    //FIELDS
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    MyViewModel categoryViewModel;
    private ImageButton fab;
    private Activity activity;
    private View tutorial;
    //CONSTRUCTOR
    public Fragment_Categories() {

    }

    //ONCREATE
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        categoryViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        activity = getActivity();
        bindGraphic(view);

        onClickFab(fab, activity);

        if(categoryViewModel.getCategories().getValue() != null) {
            adapter = new CategoryAdapter(categoryViewModel.getCategories().getValue(), getContext(), this);
            recyclerView.setAdapter(adapter);
        }


        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                List<Category> list = new ArrayList<>();
                for (Category category : categories) {
                    list.add(category);
                }
                if(list.isEmpty()){
                    tutorial.setVisibility(View.VISIBLE);
                } else {
                    tutorial.setVisibility(View.INVISIBLE);
                    updateRecyclerView();
                }
            }
        });



        return view;
    }

    private void updateRecyclerView(){
        adapter = new CategoryAdapter(categoryViewModel.getCategories().getValue(), getContext(),this);
        recyclerView.setAdapter(adapter);
    }

    //comportamento del tasto "nuovo account"
    private void onClickFab(final ImageButton fab, final Activity activity){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //CAMBIO COLORE
                fab.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_categories_outline_24dp)); //placeholder icon
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        fab.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_categories_add_24dp));
                        fab.setBackgroundTintList(null);
                        fab.setBackgroundResource(R.drawable.rounded_tl);
                    }
                }, 50);
                //FUNZIONAMENTO EFFETTIVO
                Intent addIntent = new Intent(activity, Activity_add.class);
                String addCategories = "addCategories";
                addIntent.putExtra("fragment",addCategories);
                startActivity(addIntent);
            }
        });
    }

    //comportamento dell'interfaccia "OnItemListener" : modifica di un account
    @Override
    public void onItemClick(int adapterPosition) {
        FragmentActivity activity = getActivity();
        Intent addIntent = new Intent(activity, Activity_modify.class);
        String modifyCategories = "modifyCategories";
        addIntent.putExtra("fragment",modifyCategories);
        Category categorySelected = adapter.getCategoryAdapter(adapterPosition);
        Category selectedCategory = categoryViewModel.getCategory(categorySelected);
        addIntent.putExtra("categoryID", selectedCategory.getId());
        startActivity(addIntent);
    }

    private void bindGraphic(View view){
        fab = view.findViewById(R.id.imageButtonCategoriesAdd);
        tutorial = view.findViewById(R.id.tutorial_category);
        recyclerView = view.findViewById(R.id.recycler_view_categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}



