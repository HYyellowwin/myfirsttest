package bacsta;

public class Movie {
    private String title;
    private String director;
    private String leadingRole;
    private String synopsis;
    private String duration;

    public Movie(String title,String director,String leadingRole,String synopsis,String duration){
        this.setTitle(title);
        this.setDirector(director);
        this.setLeadingRole(leadingRole);
        this.setSynopsis(synopsis);
        this.setDuration(duration);
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return this.title;
    }
    
    public void setDirector(String director){
        this.director=director;
    }

    public String getDirector(){
        return this.director;
    }

    public void setLeadingRole(String leadingRole){
        this.leadingRole=leadingRole;
    }

    public String getLeadingRole(){
        return this.leadingRole;
    }

    public void setSynopsis(String synopsis){
        this.synopsis=synopsis;
    }

    public String getSynopsis(){
        return this.synopsis;
    }


    public void setDuration(String duration){
        this.duration=duration;
    }

    public String getDuration(){
        return this.duration;
    }
}