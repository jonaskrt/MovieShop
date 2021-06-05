package ch.bzz.movieshop.model;


/**
 * MovieShop
 *
 * @author Jonas Rhbary
 */
public class Film {
    private String filmUUID;
    private String title;
    private String Regisseur;
    private Publisher publisher;
    private int fsk;
    private int jahrgang;

    public Film() {
        setFilmUUID(null);
        setTitle(null);
        setRegisseur(null);
        setPublisher(null);
        setFSK(null);
        setJahr(null);
    }
    /**
     * Gets the filmUUID
     *
     * @return value of filmUUID
     */
    public String getFilmUUID() {
        return filmUUID;
    }

    /**
     * Sets the filmUUID
     *
     * @param filmUUID the value to set
     */

    public void setFilmUUID(String filmUUID) {
        this.filmUUID = filmUUID;
    }

    /**
     * Gets the title
     *
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     *
     * @param title the value to set
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author
     *
     * @return value of author
     */
    public String getRegisseur() {
        return Regisseur;
    }

    /**
     * Sets the author
     *
     * @param author the value to set
     */

    public void setRegisseur(String author) {
        this.Regisseur = Regisseur;
    }

    /**
     * Gets the publisher
     *
     * @return value of publisher
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher
     *
     * @param publisher the value to set
     */

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the fsk
     *
     * @return value of fsk
     */
    public int getFSK() {
        return fsk;
    }

    /**
     * Sets the fsk
     *
     * @param fsk the value to set
     */

    public void setFSK(Integer fsk) {
        this.fsk = fsk;
    }

    /**
     * Gets the jahrgang
     *
     * @return value of jahrgang
     */
    public int getJahr() {
        return jahrgang;
    }

    /**
     * Sets the jahrgang
     *
     * @param jahrgang the value to set
     */

    public void setJahr(Integer jahrgang) {
        this.jahrgang = jahrgang;
    }
}