package com.jaywhitsitt.eggsandbakey.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = { "stepId", "recipeId" },
        foreignKeys = @ForeignKey(
                onDelete = CASCADE,
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId"
        ),
        indices = @Index(value = { "stepId", "recipeId" }, unique = true)
)
public class Step implements Serializable {

    @NonNull public Integer stepId;
    @NonNull public Integer recipeId;
    public String shortDescription;
    public String description;
    public String videoUrl;
    public String thumbnailUrl;

    public Step(Integer stepId, Integer recipeId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.stepId = stepId;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

}
