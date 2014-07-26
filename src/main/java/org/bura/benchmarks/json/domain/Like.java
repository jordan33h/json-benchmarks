package org.bura.benchmarks.json.domain;

/**
 * Like data.
 * 
 * @author Andrey Bloschetsov
 */
public class Like {

    private String id;
    private String date;

    public Like() {
    }

    public Like(String id, String date) {
        super();
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
