package mailmaster.cedric.learntofly.customlistview;

import mailmaster.cedric.learntofly.game.Profile;

/**
 * Created by Cedric on 06.03.2018.
 * The DataModel class holds the info of a single entry in the row_item list
 * and is used by the DataAdapter to be successfully displayed.
 */

public class DataModel {

    private String name;
    private String type;
    private String version_number;
    private String feature;
    private String mass;
    private int profileID;
    private Profile profile;

    public DataModel(String name, String type, String version_number, String feature, String mass, int profileID, Profile profile) {
        this.name=name;
        this.type=type;
        this.version_number=version_number;
        this.feature=feature;
        this.mass = mass;
        this.profileID=profileID;
        this.profile=profile;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVersion_number() {
        return version_number;
    }

    public String getFeature() {
        return feature;
    }

    public String getMass() {
        return mass;
    }

    public Profile getProfile(){return profile;}


    public int getProfileID(){return profileID;}

}