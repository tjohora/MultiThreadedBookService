/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Objects;

/**
 *
 * @author D00186562
 */
public class Book {
    private String Title;
    private String Author;
    private String firstLine;

    public Book(String Title, String Author, String firstLine) {
        this.Title = Title;
        this.Author = Author;
        this.firstLine = firstLine;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.Title);
        hash = 83 * hash + Objects.hashCode(this.Author);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.Title, other.Title)) {
            return false;
        }
        if (!Objects.equals(this.Author, other.Author)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "book{" + "Title=" + Title + ", Author=" + Author + ", firstLine=" + firstLine + '}';
    }
}
