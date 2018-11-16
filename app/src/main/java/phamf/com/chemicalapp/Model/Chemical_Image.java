package phamf.com.chemicalapp.Model;

public class Chemical_Image {

    String link;

    byte [] byte_code_resouces;

    public Chemical_Image(String link, byte[] byte_code_resouces) {
        this.link = link;
        this.byte_code_resouces = byte_code_resouces;
    }


    public Chemical_Image() {
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
