package com.example.final_pro;

public class dataholder {

    String ctype, category, description,email;

    public dataholder() {
    }

    public dataholder(String complaint_type, String category, String description, String email) {
        this.ctype = ctype;
        this.category = category;
        this.description = description;
        this.email = email;
    }

    public String getComplaint_type() {
        return ctype;
    }

    public void setComplaint_type(String complaint_type) {
        this.ctype = complaint_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
