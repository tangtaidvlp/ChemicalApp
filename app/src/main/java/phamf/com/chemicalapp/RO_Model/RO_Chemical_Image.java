package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_Chemical_Image extends RealmObject {

    @PrimaryKey
    String link;

    byte [] byte_code_resouces;

    public RO_Chemical_Image(String link, byte[] byte_code_resouces) {
        this.link = link;
        this.byte_code_resouces = byte_code_resouces;
    }

    public RO_Chemical_Image() {

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getByte_code_resouces() {
        return byte_code_resouces;
    }

    public void setByte_code_resouces(byte[] byte_code_resouces) {
        this.byte_code_resouces = byte_code_resouces;
    }
}
