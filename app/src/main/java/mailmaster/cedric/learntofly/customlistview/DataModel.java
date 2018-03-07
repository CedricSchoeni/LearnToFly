package mailmaster.cedric.learntofly.customlistview;

import mailmaster.cedric.learntofly.game.Profile;

/**
 * Created by Cedric on 06.03.2018.
 */

public class DataModel {

    private String name;
    private String type;
    private String version_number;
    private String feature;
    private int profileID;
    private Profile profile;

    public DataModel(String name, String type, String version_number, String feature, int profileID, Profile profile) {
        this.name=name;
        this.type=type;
        this.version_number=version_number;
        this.feature=feature;
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

    public Profile getProfile(){return profile;}

    public int getProfileID(){return profileID;}

}