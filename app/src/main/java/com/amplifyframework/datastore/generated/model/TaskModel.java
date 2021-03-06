package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TaskModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TaskModels", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTeam", fields = {"teamId","title"})
public final class TaskModel implements Model {
  public static final QueryField ID = field("TaskModel", "id");
  public static final QueryField TITLE = field("TaskModel", "title");
  public static final QueryField BODY = field("TaskModel", "body");
  public static final QueryField DATE_CREATED = field("TaskModel", "dateCreated");
  public static final QueryField STATE = field("TaskModel", "state");
  public static final QueryField TASK_IMAGE_KEY = field("TaskModel", "taskImageKey");
  public static final QueryField LAT = field("TaskModel", "Lat");
  public static final QueryField LON = field("TaskModel", "Lon");
  public static final QueryField TEAM = field("TaskModel", "teamId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime dateCreated;
  private final @ModelField(targetType="StateEnum") StateEnum state;
  private final @ModelField(targetType="String") String taskImageKey;
  private final @ModelField(targetType="String") String Lat;
  private final @ModelField(targetType="String") String Lon;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamId", type = Team.class) Team team;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public StateEnum getState() {
      return state;
  }
  
  public String getTaskImageKey() {
      return taskImageKey;
  }
  
  public String getLat() {
      return Lat;
  }
  
  public String getLon() {
      return Lon;
  }
  
  public Team getTeam() {
      return team;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TaskModel(String id, String title, String body, Temporal.DateTime dateCreated, StateEnum state, String taskImageKey, String Lat, String Lon, Team team) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.dateCreated = dateCreated;
    this.state = state;
    this.taskImageKey = taskImageKey;
    this.Lat = Lat;
    this.Lon = Lon;
    this.team = team;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TaskModel taskModel = (TaskModel) obj;
      return ObjectsCompat.equals(getId(), taskModel.getId()) &&
              ObjectsCompat.equals(getTitle(), taskModel.getTitle()) &&
              ObjectsCompat.equals(getBody(), taskModel.getBody()) &&
              ObjectsCompat.equals(getDateCreated(), taskModel.getDateCreated()) &&
              ObjectsCompat.equals(getState(), taskModel.getState()) &&
              ObjectsCompat.equals(getTaskImageKey(), taskModel.getTaskImageKey()) &&
              ObjectsCompat.equals(getLat(), taskModel.getLat()) &&
              ObjectsCompat.equals(getLon(), taskModel.getLon()) &&
              ObjectsCompat.equals(getTeam(), taskModel.getTeam()) &&
              ObjectsCompat.equals(getCreatedAt(), taskModel.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), taskModel.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getDateCreated())
      .append(getState())
      .append(getTaskImageKey())
      .append(getLat())
      .append(getLon())
      .append(getTeam())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TaskModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("taskImageKey=" + String.valueOf(getTaskImageKey()) + ", ")
      .append("Lat=" + String.valueOf(getLat()) + ", ")
      .append("Lon=" + String.valueOf(getLon()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static TaskModel justId(String id) {
    return new TaskModel(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      dateCreated,
      state,
      taskImageKey,
      Lat,
      Lon,
      team);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    TaskModel build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep dateCreated(Temporal.DateTime dateCreated);
    BuildStep state(StateEnum state);
    BuildStep taskImageKey(String taskImageKey);
    BuildStep lat(String lat);
    BuildStep lon(String lon);
    BuildStep team(Team team);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String body;
    private Temporal.DateTime dateCreated;
    private StateEnum state;
    private String taskImageKey;
    private String Lat;
    private String Lon;
    private Team team;
    @Override
     public TaskModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TaskModel(
          id,
          title,
          body,
          dateCreated,
          state,
          taskImageKey,
          Lat,
          Lon,
          team);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    @Override
     public BuildStep state(StateEnum state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep taskImageKey(String taskImageKey) {
        this.taskImageKey = taskImageKey;
        return this;
    }
    
    @Override
     public BuildStep lat(String lat) {
        this.Lat = lat;
        return this;
    }
    
    @Override
     public BuildStep lon(String lon) {
        this.Lon = lon;
        return this;
    }
    
    @Override
     public BuildStep team(Team team) {
        this.team = team;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, Temporal.DateTime dateCreated, StateEnum state, String taskImageKey, String lat, String lon, Team team) {
      super.id(id);
      super.title(title)
        .body(body)
        .dateCreated(dateCreated)
        .state(state)
        .taskImageKey(taskImageKey)
        .lat(lat)
        .lon(lon)
        .team(team);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder state(StateEnum state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder taskImageKey(String taskImageKey) {
      return (CopyOfBuilder) super.taskImageKey(taskImageKey);
    }
    
    @Override
     public CopyOfBuilder lat(String lat) {
      return (CopyOfBuilder) super.lat(lat);
    }
    
    @Override
     public CopyOfBuilder lon(String lon) {
      return (CopyOfBuilder) super.lon(lon);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
    }
  }
  
}
