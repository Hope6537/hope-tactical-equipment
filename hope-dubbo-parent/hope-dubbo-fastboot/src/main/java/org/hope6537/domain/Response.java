package org.hope6537.domain;

import java.util.List;

/**
 * Created by hope6537 on 16/1/27.
 */
public class Response {

    private Integer total;

    private List<Comic> comics;

    public Response() {

    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
}
