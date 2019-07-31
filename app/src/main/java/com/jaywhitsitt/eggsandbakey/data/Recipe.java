package com.jaywhitsitt.eggsandbakey.data;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.jaywhitsitt.eggsandbakey.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe implements Serializable, LifecycleOwner {

    @PrimaryKey
    public final Integer id;
    public final String name;
//    private List<Ingredient> ingredients;
    @Ignore
    public List<Step> steps;
    public int servings;
    public String imageUrlString;

    @Ignore
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public Recipe(Integer id, String name, int servings, String imageUrlString) {
        this(id, name, null, servings, imageUrlString);
    }

    @Ignore
    public Recipe(Integer id, String name, List<Step> steps, int servings, String imageUrlString) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrlString = imageUrlString;

        this.steps = steps;

        if (steps == null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance().stepDao().getStepsForRecipe(Recipe.this.id).observe(Recipe.this, new Observer<List<Step>>() {
                        @Override
                        public void onChanged(List<Step> steps) {
                            Recipe.this.steps = steps;
                        }
                    });
                }
            });
        }

        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void finalize() throws Throwable {
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        super.finalize();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

}
